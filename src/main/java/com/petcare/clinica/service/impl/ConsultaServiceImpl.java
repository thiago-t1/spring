package com.petcare.clinica.service.impl;

import com.petcare.clinica.dto.ConsultaRequest;
import com.petcare.clinica.dto.ConsultaResponse;
import com.petcare.clinica.entity.Animal;
import com.petcare.clinica.entity.Consulta;
import com.petcare.clinica.entity.StatusConsulta;
import com.petcare.clinica.entity.Veterinario;
import com.petcare.clinica.exception.ConflitoHorarioException;
import com.petcare.clinica.exception.EspecieNaoHabilitadaException;
import com.petcare.clinica.exception.ResourceNotFoundException;
import com.petcare.clinica.repository.AnimalRepository;
import com.petcare.clinica.repository.ConsultaRepository;
import com.petcare.clinica.repository.VeterinarioRepository;
import com.petcare.clinica.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Regras de negocio da clinica:
 * - nao permite agendar dois atendimentos do mesmo veterinario no mesmo horario (conflito/sobreposicao);
 * - nao permite agendar consulta com veterinario nao habilitado para a especie do animal;
 * - cancelamento apenas muda o status, nunca deleta o registro.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ConsultaServiceImpl implements ConsultaService {

    // Duracao padrao considerada para cada consulta, usada para checar sobreposicao de horarios.
    private static final Duration DURACAO_CONSULTA = Duration.ofMinutes(60);

    private final ConsultaRepository consultaRepository;
    private final AnimalRepository animalRepository;
    private final VeterinarioRepository veterinarioRepository;

    @Override
    public ConsultaResponse agendar(ConsultaRequest request) {
        Animal animal = animalRepository.findById(request.animalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal nao encontrado com id " + request.animalId()));

        Veterinario veterinario = veterinarioRepository.findById(request.veterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinario nao encontrado com id " + request.veterinarioId()));

        if (!veterinario.getEspeciesAtendidas().contains(animal.getEspecie())) {
            throw new EspecieNaoHabilitadaException(
                    "Veterinario " + veterinario.getNome() + " nao esta habilitado para atender a especie " + animal.getEspecie());
        }

        validarConflitoDeHorario(veterinario.getId(), request.dataHora(), null);

        Consulta consulta = Consulta.builder()
                .animal(animal)
                .veterinario(veterinario)
                .local(request.local())
                .dataHora(request.dataHora())
                .status(StatusConsulta.AGENDADA)
                .build();

        return toResponse(consultaRepository.save(consulta));
    }

    private void validarConflitoDeHorario(Long veterinarioId, LocalDateTime dataHora, Long consultaIdIgnorar) {
        LocalDateTime inicioJanela = dataHora.minus(DURACAO_CONSULTA).plusSeconds(1);
        LocalDateTime fimJanela = dataHora.plus(DURACAO_CONSULTA).minusSeconds(1);

        List<Consulta> conflitantes = consultaRepository.findByVeterinarioEIntervalo(veterinarioId, inicioJanela, fimJanela);

        boolean existeConflito = conflitantes.stream()
                .anyMatch(c -> !c.getId().equals(consultaIdIgnorar));

        if (existeConflito) {
            throw new ConflitoHorarioException(
                    "O veterinario ja possui outro atendimento agendado em horario conflitante com " + dataHora);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ConsultaResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarTodas() {
        return consultaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorAnimal(Long animalId) {
        return consultaRepository.findByAnimalId(animalId).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorAnimalEStatus(Long animalId, StatusConsulta status) {
        return consultaRepository.findByAnimalIdAndStatus(animalId, status).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorVeterinario(Long veterinarioId) {
        return consultaRepository.findByVeterinarioId(veterinarioId).stream().map(this::toResponse).toList();
    }

    @Override
    public ConsultaResponse cancelar(Long id) {
        Consulta consulta = buscarEntidade(id);
        consulta.setStatus(StatusConsulta.CANCELADA);
        return toResponse(consultaRepository.save(consulta));
    }

    @Override
    public ConsultaResponse marcarComoRealizada(Long id) {
        Consulta consulta = buscarEntidade(id);
        consulta.setStatus(StatusConsulta.REALIZADA);
        return toResponse(consultaRepository.save(consulta));
    }

    private Consulta buscarEntidade(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta nao encontrada com id " + id));
    }

    private ConsultaResponse toResponse(Consulta consulta) {
        return new ConsultaResponse(
                consulta.getId(),
                consulta.getAnimal().getId(),
                consulta.getAnimal().getNome(),
                consulta.getVeterinario().getId(),
                consulta.getVeterinario().getNome(),
                consulta.getLocal(),
                consulta.getDataHora(),
                consulta.getStatus()
        );
    }
}

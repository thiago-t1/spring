package com.petcare.clinica.service.impl;

import com.petcare.clinica.dto.AtendimentoRequest;
import com.petcare.clinica.dto.AtendimentoResponse;
import com.petcare.clinica.entity.Atendimento;
import com.petcare.clinica.entity.Consulta;
import com.petcare.clinica.exception.ResourceNotFoundException;
import com.petcare.clinica.repository.AtendimentoRepository;
import com.petcare.clinica.repository.ConsultaRepository;
import com.petcare.clinica.service.AtendimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AtendimentoServiceImpl implements AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;
    private final ConsultaRepository consultaRepository;

    @Override
    public AtendimentoResponse criar(AtendimentoRequest request) {
        Consulta consulta = consultaRepository.findById(request.consultaId())
                .orElseThrow(() -> new ResourceNotFoundException("Consulta nao encontrada com id " + request.consultaId()));

        Atendimento atendimento = Atendimento.builder()
                .consulta(consulta)
                .animal(consulta.getAnimal())
                .diagnostico(request.diagnostico())
                .observacoes(request.observacoes())
                .orientacoes(request.orientacoes())
                .data(request.data() != null ? request.data() : LocalDateTime.now())
                .build();

        return toResponse(atendimentoRepository.save(atendimento));
    }

    @Override
    @Transactional(readOnly = true)
    public AtendimentoResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AtendimentoResponse> listarTodos() {
        return atendimentoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AtendimentoResponse> listarPorAnimal(Long animalId) {
        return atendimentoRepository.findByAnimalIdOrderByDataDesc(animalId).stream().map(this::toResponse).toList();
    }

    @Override
    public AtendimentoResponse atualizar(Long id, AtendimentoRequest request) {
        Atendimento atendimento = buscarEntidade(id);

        Consulta consulta = consultaRepository.findById(request.consultaId())
                .orElseThrow(() -> new ResourceNotFoundException("Consulta nao encontrada com id " + request.consultaId()));

        atendimento.setConsulta(consulta);
        atendimento.setAnimal(consulta.getAnimal());
        atendimento.setDiagnostico(request.diagnostico());
        atendimento.setObservacoes(request.observacoes());
        atendimento.setOrientacoes(request.orientacoes());
        if (request.data() != null) {
            atendimento.setData(request.data());
        }

        return toResponse(atendimentoRepository.save(atendimento));
    }

    @Override
    public void deletar(Long id) {
        Atendimento atendimento = buscarEntidade(id);
        atendimentoRepository.delete(atendimento);
    }

    private Atendimento buscarEntidade(Long id) {
        return atendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atendimento nao encontrado com id " + id));
    }

    private AtendimentoResponse toResponse(Atendimento atendimento) {
        return new AtendimentoResponse(
                atendimento.getId(),
                atendimento.getConsulta().getId(),
                atendimento.getAnimal().getId(),
                atendimento.getAnimal().getNome(),
                atendimento.getDiagnostico(),
                atendimento.getObservacoes(),
                atendimento.getOrientacoes(),
                atendimento.getData()
        );
    }
}

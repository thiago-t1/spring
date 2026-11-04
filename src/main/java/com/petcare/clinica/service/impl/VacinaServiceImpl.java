package com.petcare.clinica.service.impl;

import com.petcare.clinica.dto.VacinaRequest;
import com.petcare.clinica.dto.VacinaResponse;
import com.petcare.clinica.entity.Animal;
import com.petcare.clinica.entity.Atendimento;
import com.petcare.clinica.entity.Vacina;
import com.petcare.clinica.exception.ResourceNotFoundException;
import com.petcare.clinica.repository.AnimalRepository;
import com.petcare.clinica.repository.AtendimentoRepository;
import com.petcare.clinica.repository.VacinaRepository;
import com.petcare.clinica.service.VacinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VacinaServiceImpl implements VacinaService {

    private final VacinaRepository vacinaRepository;
    private final AnimalRepository animalRepository;
    private final AtendimentoRepository atendimentoRepository;

    @Override
    public VacinaResponse criar(VacinaRequest request) {
        Animal animal = animalRepository.findById(request.animalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal nao encontrado com id " + request.animalId()));

        Atendimento atendimento = null;
        if (request.atendimentoId() != null) {
            atendimento = atendimentoRepository.findById(request.atendimentoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Atendimento nao encontrado com id " + request.atendimentoId()));
        }

        Vacina vacina = Vacina.builder()
                .nome(request.nome())
                .dataAplicacao(request.dataAplicacao())
                .lote(request.lote())
                .animal(animal)
                .atendimento(atendimento)
                .build();

        return toResponse(vacinaRepository.save(vacina));
    }

    @Override
    @Transactional(readOnly = true)
    public VacinaResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VacinaResponse> listarTodas() {
        return vacinaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VacinaResponse> listarPorAnimal(Long animalId) {
        return vacinaRepository.findByAnimalIdOrderByDataAplicacaoDesc(animalId).stream().map(this::toResponse).toList();
    }

    @Override
    public VacinaResponse atualizar(Long id, VacinaRequest request) {
        Vacina vacina = buscarEntidade(id);

        Animal animal = animalRepository.findById(request.animalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal nao encontrado com id " + request.animalId()));

        Atendimento atendimento = null;
        if (request.atendimentoId() != null) {
            atendimento = atendimentoRepository.findById(request.atendimentoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Atendimento nao encontrado com id " + request.atendimentoId()));
        }

        vacina.setNome(request.nome());
        vacina.setDataAplicacao(request.dataAplicacao());
        vacina.setLote(request.lote());
        vacina.setAnimal(animal);
        vacina.setAtendimento(atendimento);

        return toResponse(vacinaRepository.save(vacina));
    }

    @Override
    public void deletar(Long id) {
        Vacina vacina = buscarEntidade(id);
        vacinaRepository.delete(vacina);
    }

    private Vacina buscarEntidade(Long id) {
        return vacinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacina nao encontrada com id " + id));
    }

    private VacinaResponse toResponse(Vacina vacina) {
        return new VacinaResponse(
                vacina.getId(),
                vacina.getNome(),
                vacina.getDataAplicacao(),
                vacina.getLote(),
                vacina.getAnimal().getId(),
                vacina.getAtendimento() != null ? vacina.getAtendimento().getId() : null
        );
    }
}

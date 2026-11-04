package com.petcare.clinica.service.impl;

import com.petcare.clinica.dto.AtendimentoResponse;
import com.petcare.clinica.dto.HistoricoAnimalResponse;
import com.petcare.clinica.dto.VacinaResponse;
import com.petcare.clinica.entity.Animal;
import com.petcare.clinica.entity.Atendimento;
import com.petcare.clinica.entity.Vacina;
import com.petcare.clinica.exception.ResourceNotFoundException;
import com.petcare.clinica.repository.AnimalRepository;
import com.petcare.clinica.repository.AtendimentoRepository;
import com.petcare.clinica.repository.VacinaRepository;
import com.petcare.clinica.service.HistoricoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoricoServiceImpl implements HistoricoService {

    private final AnimalRepository animalRepository;
    private final AtendimentoRepository atendimentoRepository;
    private final VacinaRepository vacinaRepository;

    @Override
    public HistoricoAnimalResponse buscarHistoricoCompleto(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new ResourceNotFoundException("Animal nao encontrado com id " + animalId));

        var atendimentos = atendimentoRepository.findByAnimalIdOrderByDataDesc(animalId).stream()
                .map(this::toAtendimentoResponse)
                .toList();

        var vacinas = vacinaRepository.findByAnimalIdOrderByDataAplicacaoDesc(animalId).stream()
                .map(this::toVacinaResponse)
                .toList();

        return new HistoricoAnimalResponse(
                animal.getId(),
                animal.getNome(),
                animal.getTutor().getNome(),
                atendimentos,
                vacinas
        );
    }

    private AtendimentoResponse toAtendimentoResponse(Atendimento a) {
        return new AtendimentoResponse(
                a.getId(),
                a.getConsulta().getId(),
                a.getAnimal().getId(),
                a.getAnimal().getNome(),
                a.getDiagnostico(),
                a.getObservacoes(),
                a.getOrientacoes(),
                a.getData()
        );
    }

    private VacinaResponse toVacinaResponse(Vacina v) {
        return new VacinaResponse(
                v.getId(),
                v.getNome(),
                v.getDataAplicacao(),
                v.getLote(),
                v.getAnimal().getId(),
                v.getAtendimento() != null ? v.getAtendimento().getId() : null
        );
    }
}

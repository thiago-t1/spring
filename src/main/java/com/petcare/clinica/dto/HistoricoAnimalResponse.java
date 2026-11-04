package com.petcare.clinica.dto;

import java.util.List;

public record HistoricoAnimalResponse(
        Long animalId,
        String animalNome,
        String tutorNome,
        List<AtendimentoResponse> atendimentos,
        List<VacinaResponse> vacinas
) {
}

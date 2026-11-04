package com.petcare.clinica.dto;

import java.time.LocalDate;

public record VacinaResponse(
        Long id,
        String nome,
        LocalDate dataAplicacao,
        String lote,
        Long animalId,
        Long atendimentoId
) {
}

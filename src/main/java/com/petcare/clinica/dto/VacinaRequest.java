package com.petcare.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record VacinaRequest(
        @NotBlank(message = "nome e obrigatorio") String nome,
        @NotNull(message = "dataAplicacao e obrigatoria") LocalDate dataAplicacao,
        String lote,
        @NotNull(message = "animalId e obrigatorio") Long animalId,
        Long atendimentoId
) {
}

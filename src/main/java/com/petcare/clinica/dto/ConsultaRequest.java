package com.petcare.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaRequest(
        @NotNull(message = "animalId e obrigatorio") Long animalId,
        @NotNull(message = "veterinarioId e obrigatorio") Long veterinarioId,
        @NotBlank(message = "local e obrigatorio") String local,
        @NotNull(message = "dataHora e obrigatoria") LocalDateTime dataHora
) {
}

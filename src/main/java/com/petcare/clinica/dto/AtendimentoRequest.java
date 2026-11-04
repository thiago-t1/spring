package com.petcare.clinica.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AtendimentoRequest(
        @NotNull(message = "consultaId e obrigatorio") Long consultaId,
        String diagnostico,
        String observacoes,
        String orientacoes,
        LocalDateTime data
) {
}

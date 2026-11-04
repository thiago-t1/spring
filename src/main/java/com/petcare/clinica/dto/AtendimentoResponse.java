package com.petcare.clinica.dto;

import java.time.LocalDateTime;

public record AtendimentoResponse(
        Long id,
        Long consultaId,
        Long animalId,
        String animalNome,
        String diagnostico,
        String observacoes,
        String orientacoes,
        LocalDateTime data
) {
}

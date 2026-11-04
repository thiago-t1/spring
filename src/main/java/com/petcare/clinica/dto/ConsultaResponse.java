package com.petcare.clinica.dto;

import com.petcare.clinica.entity.StatusConsulta;

import java.time.LocalDateTime;

public record ConsultaResponse(
        Long id,
        Long animalId,
        String animalNome,
        Long veterinarioId,
        String veterinarioNome,
        String local,
        LocalDateTime dataHora,
        StatusConsulta status
) {
}

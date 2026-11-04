package com.petcare.clinica.dto;

import com.petcare.clinica.entity.Especie;

import java.util.Set;

public record VeterinarioResponse(
        Long id,
        String nome,
        String crmv,
        Set<Especie> especiesAtendidas
) {
}

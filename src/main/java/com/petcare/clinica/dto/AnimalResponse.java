package com.petcare.clinica.dto;

import com.petcare.clinica.entity.Especie;

import java.time.LocalDate;

public record AnimalResponse(
        Long id,
        String nome,
        Especie especie,
        String raca,
        LocalDate dataNascimento,
        Long tutorId,
        String tutorNome
) {
}

package com.petcare.clinica.dto;

import com.petcare.clinica.entity.Especie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AnimalRequest(
        @NotBlank(message = "nome e obrigatorio") String nome,
        @NotNull(message = "especie e obrigatoria") Especie especie,
        String raca,
        LocalDate dataNascimento,
        @NotNull(message = "tutorId e obrigatorio") Long tutorId
) {
}

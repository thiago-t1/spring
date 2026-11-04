package com.petcare.clinica.dto;

import jakarta.validation.constraints.NotBlank;

public record TutorRequest(
        @NotBlank(message = "nome e obrigatorio") String nome,
        @NotBlank(message = "cpf e obrigatorio") String cpf,
        String telefone,
        String endereco
) {
}

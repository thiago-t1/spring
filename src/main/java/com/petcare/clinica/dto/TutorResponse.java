package com.petcare.clinica.dto;

public record TutorResponse(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String endereco
) {
}

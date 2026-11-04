package com.petcare.clinica.dto;

import com.petcare.clinica.entity.Especie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record VeterinarioRequest(
        @NotBlank(message = "nome e obrigatorio") String nome,
        @NotBlank(message = "crmv e obrigatorio") String crmv,
        @NotEmpty(message = "informe pelo menos uma especie que o veterinario atende") Set<Especie> especiesAtendidas
) {
}

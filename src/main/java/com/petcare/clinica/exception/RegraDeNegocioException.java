package com.petcare.clinica.exception;

/**
 * Excecao generica para violacoes de regra de negocio (ex: CPF/CRMV duplicado)
 * que devem retornar HTTP 409 (Conflict) ou 400 (Bad Request) conforme o caso.
 */
public class RegraDeNegocioException extends RuntimeException {
    public RegraDeNegocioException(String message) {
        super(message);
    }
}

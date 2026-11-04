package com.petcare.clinica.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ApiError erro = new ApiError(HttpStatus.NOT_FOUND.value(), "Nao encontrado", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(ConflitoHorarioException.class)
    public ResponseEntity<ApiError> handleConflitoHorario(ConflitoHorarioException ex, HttpServletRequest request) {
        ApiError erro = new ApiError(HttpStatus.CONFLICT.value(), "Conflito de horario", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(EspecieNaoHabilitadaException.class)
    public ResponseEntity<ApiError> handleEspecieNaoHabilitada(EspecieNaoHabilitadaException ex, HttpServletRequest request) {
        ApiError erro = new ApiError(HttpStatus.BAD_REQUEST.value(), "Especie nao habilitada", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ApiError> handleRegraDeNegocio(RegraDeNegocioException ex, HttpServletRequest request) {
        ApiError erro = new ApiError(HttpStatus.CONFLICT.value(), "Regra de negocio violada", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();
        ApiError erro = new ApiError(HttpStatus.BAD_REQUEST.value(), "Dados invalidos", "Erro de validacao nos campos enviados", request.getRequestURI(), detalhes);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ApiError erro = new ApiError(HttpStatus.BAD_REQUEST.value(), "Requisicao invalida", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
        ApiError erro = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}

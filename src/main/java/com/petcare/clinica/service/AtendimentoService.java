package com.petcare.clinica.service;

import com.petcare.clinica.dto.AtendimentoRequest;
import com.petcare.clinica.dto.AtendimentoResponse;

import java.util.List;

public interface AtendimentoService {

    AtendimentoResponse criar(AtendimentoRequest request);

    AtendimentoResponse buscarPorId(Long id);

    List<AtendimentoResponse> listarTodos();

    List<AtendimentoResponse> listarPorAnimal(Long animalId);

    AtendimentoResponse atualizar(Long id, AtendimentoRequest request);

    void deletar(Long id);
}

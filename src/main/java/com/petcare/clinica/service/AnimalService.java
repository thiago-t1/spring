package com.petcare.clinica.service;

import com.petcare.clinica.dto.AnimalRequest;
import com.petcare.clinica.dto.AnimalResponse;

import java.util.List;

public interface AnimalService {

    AnimalResponse criar(AnimalRequest request);

    AnimalResponse buscarPorId(Long id);

    List<AnimalResponse> listarTodos();

    List<AnimalResponse> listarPorTutor(Long tutorId);

    AnimalResponse atualizar(Long id, AnimalRequest request);

    void deletar(Long id);
}

package com.petcare.clinica.service;

import com.petcare.clinica.dto.TutorRequest;
import com.petcare.clinica.dto.TutorResponse;

import java.util.List;

public interface TutorService {

    TutorResponse criar(TutorRequest request);

    TutorResponse buscarPorId(Long id);

    List<TutorResponse> listarTodos();

    TutorResponse atualizar(Long id, TutorRequest request);

    void deletar(Long id);
}

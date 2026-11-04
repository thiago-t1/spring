package com.petcare.clinica.service;

import com.petcare.clinica.dto.VacinaRequest;
import com.petcare.clinica.dto.VacinaResponse;

import java.util.List;

public interface VacinaService {

    VacinaResponse criar(VacinaRequest request);

    VacinaResponse buscarPorId(Long id);

    List<VacinaResponse> listarTodas();

    List<VacinaResponse> listarPorAnimal(Long animalId);

    VacinaResponse atualizar(Long id, VacinaRequest request);

    void deletar(Long id);
}

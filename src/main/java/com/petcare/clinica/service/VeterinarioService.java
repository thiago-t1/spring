package com.petcare.clinica.service;

import com.petcare.clinica.dto.VeterinarioRequest;
import com.petcare.clinica.dto.VeterinarioResponse;
import com.petcare.clinica.entity.Especie;

import java.util.List;

public interface VeterinarioService {

    VeterinarioResponse criar(VeterinarioRequest request);

    VeterinarioResponse buscarPorId(Long id);

    List<VeterinarioResponse> listarTodos();

    List<VeterinarioResponse> listarAptosParaEspecie(Especie especie);

    VeterinarioResponse atualizar(Long id, VeterinarioRequest request);

    void deletar(Long id);
}

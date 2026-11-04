package com.petcare.clinica.service;

import com.petcare.clinica.dto.ConsultaRequest;
import com.petcare.clinica.dto.ConsultaResponse;
import com.petcare.clinica.entity.StatusConsulta;

import java.util.List;

public interface ConsultaService {

    ConsultaResponse agendar(ConsultaRequest request);

    ConsultaResponse buscarPorId(Long id);

    List<ConsultaResponse> listarTodas();

    List<ConsultaResponse> listarPorAnimal(Long animalId);

    List<ConsultaResponse> listarPorAnimalEStatus(Long animalId, StatusConsulta status);

    List<ConsultaResponse> listarPorVeterinario(Long veterinarioId);

    ConsultaResponse cancelar(Long id);

    ConsultaResponse marcarComoRealizada(Long id);
}

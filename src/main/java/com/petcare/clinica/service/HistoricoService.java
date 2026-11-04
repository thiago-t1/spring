package com.petcare.clinica.service;

import com.petcare.clinica.dto.HistoricoAnimalResponse;

public interface HistoricoService {

    HistoricoAnimalResponse buscarHistoricoCompleto(Long animalId);
}

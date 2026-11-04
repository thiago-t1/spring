package com.petcare.clinica.repository;

import com.petcare.clinica.entity.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacinaRepository extends JpaRepository<Vacina, Long> {

    // Busca vacinas de um animal ordenadas por data de aplicacao
    List<Vacina> findByAnimalIdOrderByDataAplicacaoDesc(Long animalId);
}

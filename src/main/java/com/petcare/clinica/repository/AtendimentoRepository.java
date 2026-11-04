package com.petcare.clinica.repository;

import com.petcare.clinica.entity.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    // Busca historico de atendimentos de um animal, ordenado por data
    List<Atendimento> findByAnimalIdOrderByDataDesc(Long animalId);

    List<Atendimento> findByConsultaId(Long consultaId);
}

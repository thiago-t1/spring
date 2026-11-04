package com.petcare.clinica.repository;

import com.petcare.clinica.entity.Consulta;
import com.petcare.clinica.entity.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // Busca consultas de um veterinario em um intervalo de data/hora (usado para checar conflito)
    @Query("select c from Consulta c where c.veterinario.id = :veterinarioId " +
            "and c.status <> com.petcare.clinica.entity.StatusConsulta.CANCELADA " +
            "and c.dataHora between :inicio and :fim")
    List<Consulta> findByVeterinarioEIntervalo(@Param("veterinarioId") Long veterinarioId,
                                                @Param("inicio") LocalDateTime inicio,
                                                @Param("fim") LocalDateTime fim);

    // Busca consultas por animal e status
    List<Consulta> findByAnimalIdAndStatus(Long animalId, StatusConsulta status);

    // Busca consultas por animal
    List<Consulta> findByAnimalId(Long animalId);

    // Busca consultas por veterinario
    List<Consulta> findByVeterinarioId(Long veterinarioId);
}

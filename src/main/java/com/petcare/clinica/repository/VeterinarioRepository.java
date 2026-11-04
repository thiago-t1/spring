package com.petcare.clinica.repository;

import com.petcare.clinica.entity.Especie;
import com.petcare.clinica.entity.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

    Optional<Veterinario> findByCrmv(String crmv);

    boolean existsByCrmv(String crmv);

    // Busca veterinarios aptos para uma determinada especie
    @Query("select v from Veterinario v join v.especiesAtendidas e where e = :especie")
    List<Veterinario> findAptosParaEspecie(@Param("especie") Especie especie);
}

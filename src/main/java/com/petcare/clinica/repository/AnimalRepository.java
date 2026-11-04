package com.petcare.clinica.repository;

import com.petcare.clinica.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    // Busca animais por tutor
    List<Animal> findByTutorId(Long tutorId);

    @Query("select a from Animal a where lower(a.nome) like lower(concat('%', :nome, '%'))")
    List<Animal> buscarPorNomeAproximado(@Param("nome") String nome);
}

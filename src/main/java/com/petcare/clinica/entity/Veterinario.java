package com.petcare.clinica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "veterinario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 20)
    private String crmv;

    @Builder.Default
    @ElementCollection(targetClass = Especie.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "veterinario_especie", joinColumns = @JoinColumn(name = "veterinario_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "especie", length = 20)
    private Set<Especie> especiesAtendidas = new HashSet<>();
}

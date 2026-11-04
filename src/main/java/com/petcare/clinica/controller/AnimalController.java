package com.petcare.clinica.controller;

import com.petcare.clinica.dto.AnimalRequest;
import com.petcare.clinica.dto.AnimalResponse;
import com.petcare.clinica.dto.HistoricoAnimalResponse;
import com.petcare.clinica.service.AnimalService;
import com.petcare.clinica.service.HistoricoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/animais")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final HistoricoService historicoService;

    @PostMapping
    public ResponseEntity<AnimalResponse> criar(@Valid @RequestBody AnimalRequest request) {
        AnimalResponse response = animalService.criar(request);
        return ResponseEntity.created(URI.create("/api/animais/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AnimalResponse>> listarTodos(@RequestParam(required = false) Long tutorId) {
        if (tutorId != null) {
            return ResponseEntity.ok(animalService.listarPorTutor(tutorId));
        }
        return ResponseEntity.ok(animalService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.buscarPorId(id));
    }

    @GetMapping("/{id}/historico")
    public ResponseEntity<HistoricoAnimalResponse> historico(@PathVariable Long id) {
        return ResponseEntity.ok(historicoService.buscarHistoricoCompleto(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalResponse> atualizar(@PathVariable Long id, @Valid @RequestBody AnimalRequest request) {
        return ResponseEntity.ok(animalService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        animalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

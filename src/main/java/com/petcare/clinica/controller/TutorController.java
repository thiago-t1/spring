package com.petcare.clinica.controller;

import com.petcare.clinica.dto.TutorRequest;
import com.petcare.clinica.dto.TutorResponse;
import com.petcare.clinica.service.TutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tutores")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService tutorService;

    @PostMapping
    public ResponseEntity<TutorResponse> criar(@Valid @RequestBody TutorRequest request) {
        TutorResponse response = tutorService.criar(request);
        return ResponseEntity.created(URI.create("/api/tutores/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TutorResponse>> listarTodos() {
        return ResponseEntity.ok(tutorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tutorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorResponse> atualizar(@PathVariable Long id, @Valid @RequestBody TutorRequest request) {
        return ResponseEntity.ok(tutorService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tutorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

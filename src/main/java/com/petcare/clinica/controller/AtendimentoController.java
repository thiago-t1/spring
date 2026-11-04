package com.petcare.clinica.controller;

import com.petcare.clinica.dto.AtendimentoRequest;
import com.petcare.clinica.dto.AtendimentoResponse;
import com.petcare.clinica.service.AtendimentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/atendimentos")
@RequiredArgsConstructor
public class AtendimentoController {

    private final AtendimentoService atendimentoService;

    @PostMapping
    public ResponseEntity<AtendimentoResponse> criar(@Valid @RequestBody AtendimentoRequest request) {
        AtendimentoResponse response = atendimentoService.criar(request);
        return ResponseEntity.created(URI.create("/api/atendimentos/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AtendimentoResponse>> listar(@RequestParam(required = false) Long animalId) {
        if (animalId != null) {
            return ResponseEntity.ok(atendimentoService.listarPorAnimal(animalId));
        }
        return ResponseEntity.ok(atendimentoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtendimentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(atendimentoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtendimentoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody AtendimentoRequest request) {
        return ResponseEntity.ok(atendimentoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        atendimentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

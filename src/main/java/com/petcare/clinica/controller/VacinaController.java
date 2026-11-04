package com.petcare.clinica.controller;

import com.petcare.clinica.dto.VacinaRequest;
import com.petcare.clinica.dto.VacinaResponse;
import com.petcare.clinica.service.VacinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/vacinas")
@RequiredArgsConstructor
public class VacinaController {

    private final VacinaService vacinaService;

    @PostMapping
    public ResponseEntity<VacinaResponse> criar(@Valid @RequestBody VacinaRequest request) {
        VacinaResponse response = vacinaService.criar(request);
        return ResponseEntity.created(URI.create("/api/vacinas/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<VacinaResponse>> listar(@RequestParam(required = false) Long animalId) {
        if (animalId != null) {
            return ResponseEntity.ok(vacinaService.listarPorAnimal(animalId));
        }
        return ResponseEntity.ok(vacinaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacinaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vacinaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VacinaResponse> atualizar(@PathVariable Long id, @Valid @RequestBody VacinaRequest request) {
        return ResponseEntity.ok(vacinaService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vacinaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

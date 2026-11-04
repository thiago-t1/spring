package com.petcare.clinica.controller;

import com.petcare.clinica.dto.VeterinarioRequest;
import com.petcare.clinica.dto.VeterinarioResponse;
import com.petcare.clinica.entity.Especie;
import com.petcare.clinica.service.VeterinarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    @PostMapping
    public ResponseEntity<VeterinarioResponse> criar(@Valid @RequestBody VeterinarioRequest request) {
        VeterinarioResponse response = veterinarioService.criar(request);
        return ResponseEntity.created(URI.create("/api/veterinarios/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<VeterinarioResponse>> listarTodos(@RequestParam(required = false) Especie especie) {
        if (especie != null) {
            return ResponseEntity.ok(veterinarioService.listarAptosParaEspecie(especie));
        }
        return ResponseEntity.ok(veterinarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(veterinarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeterinarioResponse> atualizar(@PathVariable Long id, @Valid @RequestBody VeterinarioRequest request) {
        return ResponseEntity.ok(veterinarioService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veterinarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

package com.petcare.clinica.controller;

import com.petcare.clinica.dto.ConsultaRequest;
import com.petcare.clinica.dto.ConsultaResponse;
import com.petcare.clinica.entity.StatusConsulta;
import com.petcare.clinica.service.ConsultaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<ConsultaResponse> agendar(@Valid @RequestBody ConsultaRequest request) {
        ConsultaResponse response = consultaService.agendar(request);
        return ResponseEntity.created(URI.create("/api/consultas/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> listar(
            @RequestParam(required = false) Long animalId,
            @RequestParam(required = false) Long veterinarioId,
            @RequestParam(required = false) StatusConsulta status) {

        if (animalId != null && status != null) {
            return ResponseEntity.ok(consultaService.listarPorAnimalEStatus(animalId, status));
        }
        if (animalId != null) {
            return ResponseEntity.ok(consultaService.listarPorAnimal(animalId));
        }
        if (veterinarioId != null) {
            return ResponseEntity.ok(consultaService.listarPorVeterinario(veterinarioId));
        }
        return ResponseEntity.ok(consultaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ConsultaResponse> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.cancelar(id));
    }

    @PatchMapping("/{id}/realizar")
    public ResponseEntity<ConsultaResponse> marcarComoRealizada(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.marcarComoRealizada(id));
    }
}

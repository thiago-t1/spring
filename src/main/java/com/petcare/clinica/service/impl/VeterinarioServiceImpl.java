package com.petcare.clinica.service.impl;

import com.petcare.clinica.dto.VeterinarioRequest;
import com.petcare.clinica.dto.VeterinarioResponse;
import com.petcare.clinica.entity.Especie;
import com.petcare.clinica.entity.Veterinario;
import com.petcare.clinica.exception.RegraDeNegocioException;
import com.petcare.clinica.exception.ResourceNotFoundException;
import com.petcare.clinica.repository.VeterinarioRepository;
import com.petcare.clinica.service.VeterinarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    @Override
    public VeterinarioResponse criar(VeterinarioRequest request) {
        if (veterinarioRepository.existsByCrmv(request.crmv())) {
            throw new RegraDeNegocioException("Ja existe um veterinario cadastrado com o CRMV " + request.crmv());
        }
        Veterinario veterinario = Veterinario.builder()
                .nome(request.nome())
                .crmv(request.crmv())
                .especiesAtendidas(new HashSet<>(request.especiesAtendidas()))
                .build();
        return toResponse(veterinarioRepository.save(veterinario));
    }

    @Override
    @Transactional(readOnly = true)
    public VeterinarioResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VeterinarioResponse> listarTodos() {
        return veterinarioRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VeterinarioResponse> listarAptosParaEspecie(Especie especie) {
        return veterinarioRepository.findAptosParaEspecie(especie).stream().map(this::toResponse).toList();
    }

    @Override
    public VeterinarioResponse atualizar(Long id, VeterinarioRequest request) {
        Veterinario veterinario = buscarEntidade(id);

        if (!veterinario.getCrmv().equals(request.crmv()) && veterinarioRepository.existsByCrmv(request.crmv())) {
            throw new RegraDeNegocioException("Ja existe um veterinario cadastrado com o CRMV " + request.crmv());
        }

        veterinario.setNome(request.nome());
        veterinario.setCrmv(request.crmv());
        veterinario.setEspeciesAtendidas(new HashSet<>(request.especiesAtendidas()));

        return toResponse(veterinarioRepository.save(veterinario));
    }

    @Override
    public void deletar(Long id) {
        Veterinario veterinario = buscarEntidade(id);
        veterinarioRepository.delete(veterinario);
    }

    private Veterinario buscarEntidade(Long id) {
        return veterinarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinario nao encontrado com id " + id));
    }

    private VeterinarioResponse toResponse(Veterinario veterinario) {
        return new VeterinarioResponse(veterinario.getId(), veterinario.getNome(), veterinario.getCrmv(), veterinario.getEspeciesAtendidas());
    }
}

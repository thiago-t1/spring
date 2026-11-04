package com.petcare.clinica.service.impl;

import com.petcare.clinica.dto.TutorRequest;
import com.petcare.clinica.dto.TutorResponse;
import com.petcare.clinica.entity.Tutor;
import com.petcare.clinica.exception.RegraDeNegocioException;
import com.petcare.clinica.exception.ResourceNotFoundException;
import com.petcare.clinica.repository.TutorRepository;
import com.petcare.clinica.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepository;

    @Override
    public TutorResponse criar(TutorRequest request) {
        if (tutorRepository.existsByCpf(request.cpf())) {
            throw new RegraDeNegocioException("Ja existe um tutor cadastrado com o CPF " + request.cpf());
        }
        Tutor tutor = Tutor.builder()
                .nome(request.nome())
                .cpf(request.cpf())
                .telefone(request.telefone())
                .endereco(request.endereco())
                .build();
        tutor = tutorRepository.save(tutor);
        return toResponse(tutor);
    }

    @Override
    @Transactional(readOnly = true)
    public TutorResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TutorResponse> listarTodos() {
        return tutorRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public TutorResponse atualizar(Long id, TutorRequest request) {
        Tutor tutor = buscarEntidade(id);

        if (!tutor.getCpf().equals(request.cpf()) && tutorRepository.existsByCpf(request.cpf())) {
            throw new RegraDeNegocioException("Ja existe um tutor cadastrado com o CPF " + request.cpf());
        }

        tutor.setNome(request.nome());
        tutor.setCpf(request.cpf());
        tutor.setTelefone(request.telefone());
        tutor.setEndereco(request.endereco());

        return toResponse(tutorRepository.save(tutor));
    }

    @Override
    public void deletar(Long id) {
        Tutor tutor = buscarEntidade(id);
        tutorRepository.delete(tutor);
    }

    private Tutor buscarEntidade(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor nao encontrado com id " + id));
    }

    private TutorResponse toResponse(Tutor tutor) {
        return new TutorResponse(tutor.getId(), tutor.getNome(), tutor.getCpf(), tutor.getTelefone(), tutor.getEndereco());
    }
}

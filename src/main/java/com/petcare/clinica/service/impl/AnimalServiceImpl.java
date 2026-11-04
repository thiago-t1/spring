package com.petcare.clinica.service.impl;

import com.petcare.clinica.dto.AnimalRequest;
import com.petcare.clinica.dto.AnimalResponse;
import com.petcare.clinica.entity.Animal;
import com.petcare.clinica.entity.Tutor;
import com.petcare.clinica.exception.ResourceNotFoundException;
import com.petcare.clinica.repository.AnimalRepository;
import com.petcare.clinica.repository.TutorRepository;
import com.petcare.clinica.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final TutorRepository tutorRepository;

    @Override
    public AnimalResponse criar(AnimalRequest request) {
        Tutor tutor = tutorRepository.findById(request.tutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Tutor nao encontrado com id " + request.tutorId()));

        Animal animal = Animal.builder()
                .nome(request.nome())
                .especie(request.especie())
                .raca(request.raca())
                .dataNascimento(request.dataNascimento())
                .tutor(tutor)
                .build();

        return toResponse(animalRepository.save(animal));
    }

    @Override
    @Transactional(readOnly = true)
    public AnimalResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnimalResponse> listarTodos() {
        return animalRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnimalResponse> listarPorTutor(Long tutorId) {
        return animalRepository.findByTutorId(tutorId).stream().map(this::toResponse).toList();
    }

    @Override
    public AnimalResponse atualizar(Long id, AnimalRequest request) {
        Animal animal = buscarEntidade(id);

        Tutor tutor = tutorRepository.findById(request.tutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Tutor nao encontrado com id " + request.tutorId()));

        animal.setNome(request.nome());
        animal.setEspecie(request.especie());
        animal.setRaca(request.raca());
        animal.setDataNascimento(request.dataNascimento());
        animal.setTutor(tutor);

        return toResponse(animalRepository.save(animal));
    }

    @Override
    public void deletar(Long id) {
        Animal animal = buscarEntidade(id);
        animalRepository.delete(animal);
    }

    private Animal buscarEntidade(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal nao encontrado com id " + id));
    }

    private AnimalResponse toResponse(Animal animal) {
        return new AnimalResponse(
                animal.getId(),
                animal.getNome(),
                animal.getEspecie(),
                animal.getRaca(),
                animal.getDataNascimento(),
                animal.getTutor().getId(),
                animal.getTutor().getNome()
        );
    }
}

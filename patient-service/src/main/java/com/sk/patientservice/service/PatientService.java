package com.sk.patientservice.service;

import com.sk.patientservice.dto.PatientRequestDto;
import com.sk.patientservice.dto.PatientResponseDto;
import com.sk.patientservice.exception.EmailAlreadyExistException;
import com.sk.patientservice.exception.PatientNotFoundException;
import com.sk.patientservice.mapper.PatientMapper;
import com.sk.patientservice.model.Patient;
import com.sk.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDto> getPatients(){
        List<Patient> list = patientRepository.findAll();
        return list.stream().map(PatientMapper::toDto).toList();
    }

    public PatientResponseDto getPatientById(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient not found with id: " + id)
                );

        return PatientMapper.toDto(patient);
    }

    public PatientResponseDto createPatient(PatientRequestDto reqDto) {

        if(patientRepository.existsByEmail(reqDto.getEmail())) {
            throw new EmailAlreadyExistException("A patient with this email already exist.");
        }

        Patient patient = PatientMapper.toModel(reqDto);
        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.toDto(savedPatient);
    }


    public PatientResponseDto updatePatient(UUID id, PatientRequestDto reqDto) {
        if(patientRepository.existsByEmail(reqDto.getEmail())) {
            throw new EmailAlreadyExistException("A patient with this email already exist.");
        }

        // find model by id...
        Patient model = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient not found with id: " + id)
                );

        // map dto data with model...
        model.setName(reqDto.getName());
        model.setAddress(reqDto.getAddress());
        model.setEmail(reqDto.getEmail());
        model.setDateOfBirth(LocalDate.parse(reqDto.getDateOfBirth()));

        // persist changes...
        Patient updatedPatient = patientRepository.save(model);

        return PatientMapper.toDto(updatedPatient);
    }


}

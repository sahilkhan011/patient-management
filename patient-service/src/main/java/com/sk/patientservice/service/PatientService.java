package com.sk.patientservice.service;

import com.sk.patientservice.dto.PatientRequestDto;
import com.sk.patientservice.dto.PatientResponseDto;
import com.sk.patientservice.exception.EmailAlreadyExistException;
import com.sk.patientservice.exception.PatientNotFoundException;
import com.sk.patientservice.grpc.BillingServiceGrpcClient;
import com.sk.patientservice.kafka.KafkaProducer;
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
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
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

        Patient savedPatient = patientRepository.save(PatientMapper.toModel(reqDto));

        // calling grpc service... using grpc client
        billingServiceGrpcClient.createBillingAccount(savedPatient.getId().toString(),
                savedPatient.getName(), savedPatient.getEmail());

        // sending kafka event using producer
        kafkaProducer.sendEvent(savedPatient);

        return PatientMapper.toDto(savedPatient);
    }


    public PatientResponseDto updatePatient(UUID id, PatientRequestDto reqDto) {


        // find model by id...
        Patient model = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient not found with id: " + id)
                );


        if(patientRepository.existsByEmailAndIdNot(reqDto.getEmail(),id)) {
            throw new EmailAlreadyExistException("A patient with this email already exist: "+reqDto.getEmail());
        }

        // map dto data with model...
        model.setName(reqDto.getName());
        model.setAddress(reqDto.getAddress());
        model.setEmail(reqDto.getEmail());
        model.setDateOfBirth(LocalDate.parse(reqDto.getDateOfBirth()));

        // persist changes...
        Patient updatedPatient = patientRepository.save(model);

        return PatientMapper.toDto(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

}

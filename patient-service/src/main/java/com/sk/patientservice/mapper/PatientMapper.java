package com.sk.patientservice.mapper;

import com.sk.patientservice.dto.PatientRequestDto;
import com.sk.patientservice.dto.PatientResponseDto;
import com.sk.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {

    public static PatientResponseDto toDto(Patient model){
        PatientResponseDto dto = new PatientResponseDto();
        dto.setId(model.getId().toString());
        dto.setName(model.getName());
        dto.setEmail(model.getEmail());
        dto.setAddress(model.getAddress());
        dto.setDateOfBirth(model.getDateOfBirth().toString());
        dto.setRegisteredDate(model.getRegisteredDate().toString());
        return dto;
    }

    public static Patient toModel(PatientRequestDto reqDto) {
        Patient model = new Patient();
        DtoToModel(reqDto, model);
        return model;
    }

    public static void DtoToModel(PatientRequestDto dto,Patient model){
        model.setName(dto.getName());
        model.setAddress(dto.getAddress());
        model.setEmail(dto.getEmail());
        model.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        model.setRegisteredDate(LocalDate.parse(dto.getRegisteredDate()));
    }

    public static void ModelToDto(Patient model,PatientResponseDto dto){
        dto.setId(model.getId().toString());
        dto.setName(model.getName());
        dto.setAddress(model.getAddress());
        dto.setEmail(model.getEmail());
        dto.setDateOfBirth(model.getDateOfBirth().toString());
        dto.setRegisteredDate(model.getRegisteredDate().toString());
    }
}

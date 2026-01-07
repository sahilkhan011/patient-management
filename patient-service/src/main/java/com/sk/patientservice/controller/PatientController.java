package com.sk.patientservice.controller;

import com.sk.patientservice.dto.PatientRequestDto;
import com.sk.patientservice.dto.PatientResponseDto;
import com.sk.patientservice.dto.validator.CreatePatientValidationGroup;
import com.sk.patientservice.service.PatientService;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/patients")
//  @Tag(name = "Patient",description = "API for managing patient.")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
//    @Operation(summary = "Get all patients.")
    public ResponseEntity<List<PatientResponseDto>> getPatients() {
        return ResponseEntity.ok().body(patientService.getPatients());
    }

    @GetMapping("/{id}")
//    @Operation(summary = "Get patient by id.")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(patientService.getPatientById(id));
    }


    @PostMapping
//    @Operation(summary = "Create new patient.")
    public ResponseEntity<PatientResponseDto> create(
            @Validated({Default.class,CreatePatientValidationGroup.class}) @RequestBody PatientRequestDto reqDto) {

        PatientResponseDto response = patientService.createPatient(reqDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @PutMapping("/{id}")
//    @Operation(summary = "Update existing patient.")
    public ResponseEntity<PatientResponseDto> update(
            @PathVariable UUID id,
            @Validated({Default.class}) @RequestBody PatientRequestDto reqDto) {

        PatientResponseDto response = patientService.updatePatient(id, reqDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
//    @Operation(summary = "Delete patient by id")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}

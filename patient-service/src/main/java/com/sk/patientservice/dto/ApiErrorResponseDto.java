package com.sk.patientservice.dto;

public class ApiErrorResponseDto {
    private String message;

    public ApiErrorResponseDto(String message) {
        this.message = message;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

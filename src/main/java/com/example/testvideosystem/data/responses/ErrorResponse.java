package com.example.testvideosystem.data.responses;

import lombok.Data;

@Data
public class ErrorResponse {
    private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }
}

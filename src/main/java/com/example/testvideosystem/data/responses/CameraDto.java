package com.example.testvideosystem.data.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraDto {
    private Integer id;
    private String sourceDataUrl;
    private String tokenDataUrl;
}

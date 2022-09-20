package com.example.testvideosystem.data.responses;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDto {
    private Integer id;
    private UrlType urlType;
    private String videoUrl;
    private String value;
    private Integer ttl;

    public ResponseDto(Integer id, CameraSourceData sourceData, CameraTokenData tokenData) {
        this.id = id;
        this.urlType = sourceData.getUrlType();
        this.videoUrl = sourceData.getVideoUrl();
        this.value = tokenData.getValue();
        this.ttl = tokenData.getTtl();
    }
}

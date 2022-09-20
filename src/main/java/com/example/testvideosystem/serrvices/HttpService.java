package com.example.testvideosystem.serrvices;

import com.example.testvideosystem.data.responses.CameraDto;
import com.example.testvideosystem.data.responses.CameraSourceData;
import com.example.testvideosystem.data.responses.CameraTokenData;
import com.example.testvideosystem.data.responses.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class HttpService {

    @Value("${mocky.all-cameras-url}")
    private String allCamerasUrl;

    private final WebClient webClient;

    @Autowired
    public HttpService(WebClient.Builder webclientBuilder) {
        this.webClient = webclientBuilder.build();
    }

    public List<CameraDto> getAllCameras() {
        return this.webClient.get().uri(allCamerasUrl).retrieve().bodyToMono(new ParameterizedTypeReference<List<CameraDto>>() {
        }).block();
    }

    public CameraSourceData getSourceDataByUrl(String url) {
        return this.webClient.get().uri(url).retrieve().bodyToMono(CameraSourceData.class).block();
    }

    public CameraTokenData getTokenDataByUrl(String url) {
        return this.webClient.get().uri(url).retrieve().bodyToMono(CameraTokenData.class).block();
    }
}


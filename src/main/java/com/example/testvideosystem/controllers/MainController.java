package com.example.testvideosystem.controllers;

import com.example.testvideosystem.data.responses.CameraDto;
import com.example.testvideosystem.data.responses.CameraSourceData;
import com.example.testvideosystem.data.responses.CameraTokenData;
import com.example.testvideosystem.data.responses.ResponseDto;
import com.example.testvideosystem.serrvices.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private final HttpService httpService;

    @Autowired
    public MainController(HttpService httpService) {
        this.httpService = httpService;
    }


    @GetMapping("/test")
    @ResponseBody
    public List<ResponseDto> handleTest() {
        List<ResponseDto> response = new ArrayList<>();
        List<CameraDto> allCameras = this.httpService.getAllCameras();
        for (CameraDto camera : allCameras) {
            CameraSourceData sourceData = httpService.getSourceDataByUrl(camera.getSourceDataUrl());
            CameraTokenData tokenData = httpService.getTokenDataByUrl(camera.getTokenDataUrl());
            response.add(new ResponseDto(camera.getId(), sourceData, tokenData));
        }
        return response;
    }
}

package com.example.testvideosystem.unit_tests;

import com.example.testvideosystem.data.responses.CameraDto;
import com.example.testvideosystem.data.responses.CameraSourceData;
import com.example.testvideosystem.data.responses.CameraTokenData;
import com.example.testvideosystem.data.responses.UrlType;
import com.example.testvideosystem.serrvices.HttpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpServiceTests {

    private final HttpService httpService;
    private MockWebServer mockBackEnd;
    ObjectMapper objectMapper;
    private String mockServerUrl;



    @Autowired
    public HttpServiceTests(HttpService httpService) {
        this.httpService = httpService;
    }

    @BeforeAll
    void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
        mockServerUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        ReflectionTestUtils.setField(httpService, "allCamerasUrl", mockServerUrl);
        objectMapper = new ObjectMapper();
    }

    @AfterAll
    void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    public void getAllCamerasTest() throws JsonProcessingException {
        String sourceDataUrl = "sourceTestUrl";
        String tokenDataUrl = "tokenTestUrl";
        List<CameraDto> camerasMock = new ArrayList<>();
        camerasMock.add(new CameraDto(1, sourceDataUrl, tokenDataUrl));
        ObjectMapper objectMapper = new ObjectMapper();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(camerasMock))
                .addHeader("Content-Type", "application/json"));
        List<CameraDto> allCameras = httpService.getAllCameras();
        assertEquals(allCameras.size(), 1);
        assertEquals(allCameras.get(0).getId(), 1);
        assertEquals(allCameras.get(0).getSourceDataUrl(), sourceDataUrl);
        assertEquals(allCameras.get(0).getTokenDataUrl(), tokenDataUrl);
    }

    @Test
    public void getSourceDataByUrlTest() throws JsonProcessingException {
        String videoUrlMock = "videoUrl";
        CameraSourceData sourceDataMock = new CameraSourceData();
        sourceDataMock.setUrlType(UrlType.LIVE);
        sourceDataMock.setVideoUrl(videoUrlMock);
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(sourceDataMock))
                .addHeader("Content-Type", "application/json"));
        CameraSourceData sourceData = httpService.getSourceDataByUrl(mockServerUrl);
        assertEquals(sourceData.getUrlType(), UrlType.LIVE);
        assertEquals(sourceData.getVideoUrl(), videoUrlMock);
    }

    @Test
    public void getTokenDataByUrlTest() throws JsonProcessingException {
        String mockValue = "value";
        int mockTtl = 212;
        CameraTokenData tokenDataMock = new CameraTokenData();
        tokenDataMock.setTtl(mockTtl);
        tokenDataMock.setValue(mockValue);
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(tokenDataMock))
                .addHeader("Content-Type", "application/json"));
        CameraTokenData tokenData = httpService.getTokenDataByUrl(mockServerUrl);
        assertEquals(tokenData.getTtl(), mockTtl);
        assertEquals(tokenData.getValue(), mockValue);
    }
}

package com.rapidminer.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rapidminer.service.MedianApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class MedianApiClientImpl implements MedianApiClient {

    private final ObjectMapper objectMapper;
    private final String medianWebServiceUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public MedianApiClientImpl(ObjectMapper objectMapper, @Value("${medianWebService.url}") String medianWebServiceUrl) {
        this.objectMapper = objectMapper;
        this.medianWebServiceUrl = medianWebServiceUrl;
    }

    @Override
    public List<Map<String, Object>> getMedian(List<Map<String, Object>> records) throws IOException {
        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(medianWebServiceUrl, records, byte[].class);
        byte[] body = responseEntity.getBody();

        final List<Map<String, Object>> resultColumntToRecord =
                objectMapper.readValue(body, new TypeReference<List<Map<String, Object>>>() {
                });
        return resultColumntToRecord;
    }
}

package com.attilapalf.exceptional.businessLogic;

import com.attilapalf.exceptional.wrappers.ExampleData;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by 212461305 on 2015.06.30..
 */
@Service
public class GcmConnector {

    private final String url = "https://android.googleapis.com/gcm/send";
    private final String apiKey = "AIzaSyCSwgwKHOuqBozM-JhhKYp6xnwFKs8xJrU";
    private final String projectNumber = "947608408958";

    public void postToGcm(String regId, ExampleData data) {
        ExampleData exampleData = new ExampleData("lolol", "super", 2);
        HttpEntity<ExampleData> requestData = new HttpEntity<>(exampleData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "key=" + apiKey);

        RestTemplate restTemplate = new RestTemplate();
        // restTemplate.exchange(url, HttpMethod.POST, requestData, String.class,
        String response = restTemplate.exchange(url, HttpMethod.POST, requestData, String.class, new HashMap<String, Object>());
    }
}

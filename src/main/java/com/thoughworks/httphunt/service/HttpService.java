/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thoughworks.httphunt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mithilesh
 */
@Service
public class HttpService {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    
    private RestTemplate restTemplate;
    
    public String getSecondInputFromServer() throws JsonProcessingException{
        restTemplate = restTemplateBuilder.build();
        
        //get input from /input endpoint
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", "alL1go0MB");
        HttpEntity getEntity = new HttpEntity(headers);
        
//        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/input", HttpMethod.GET, entity, String.class);
        ResponseEntity<String> response = restTemplate.exchange("https://http-hunt.thoughtworks-labs.net/challenge/input", HttpMethod.GET, getEntity, String.class);
        String input = response.getBody();
        System.out.println("input -"+ input);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode inputNode = root.path("text");
        System.out.println("text - "+ inputNode.asText());
        String textToBeTested = inputNode.asText();
        return textToBeTested;
    }
    
    public String postTheOutput(ObjectNode node){
        String response = "";
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("userId", "alL1go0MB");
            HttpEntity<JsonNode> postEntity = new HttpEntity<>(node, headers);
            String twURL = "https://http-hunt.thoughtworks-labs.net/challenge/output";
            String localURL = "http://localhost:8990/output";
            ResponseEntity<String> opResponseEntity = restTemplate.exchange(twURL, HttpMethod.POST, postEntity, String.class);
            response = opResponseEntity.getBody();
//            System.out.println("op -"+ opResponseEntity.getBody());
        }catch(Exception ex){
            System.out.println("ex occured -"+ex);
        }
        return response;
    }
}

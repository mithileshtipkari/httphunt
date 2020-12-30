/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thoughworks.httphunt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mithilesh
 */
@RestController
public class GetChallenges {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    
    private RestTemplate restTemplate;
    
    @GetMapping("/getChallenges")
    public String getChallenges(){
        restTemplate = restTemplateBuilder.build();
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", "alL1go0MB");
        HttpEntity entity = new HttpEntity(headers);
        
        String getChallengeURL = "https://http-hunt.thoughtworks-labs.net/challenge";
        
        ResponseEntity<String> response = restTemplate.exchange(getChallengeURL, HttpMethod.GET, entity, String.class);
        System.out.println("response - "+ response.getBody());
        return response.getBody();
    }
}

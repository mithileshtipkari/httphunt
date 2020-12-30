/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thoughworks.httphunt.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author mith1
 */
@RestController
public class HttpHuntChallenger {
    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    
   
    
//    {
//        System.out.println("initializer");
//        restTemplate = restTemplateBuilder.build();
//    }
    
    @GetMapping("test1st")
    public String test1stPart(){
        System.out.println("testing part 1");
         RestTemplate restTemplate = restTemplateBuilder.build();
//        String input = restTemplate.getForObject("http://localhost:8080/input", String.class); //local test
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", "alL1go0MB");
        HttpEntity entity = new HttpEntity(headers);
        
//        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/input", HttpMethod.GET, entity, String.class);
        ResponseEntity<String> response = restTemplate.exchange("https://http-hunt.thoughtworks-labs.net/challenge/input", HttpMethod.GET, entity, String.class);
        String input = response.getBody();
        if(input != null){
            System.out.println("len -"+ input.length());
//            String opJson = "{count:" + input.length() +"}";


            //new try
//            String url = "endpoint url";
            
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);

//            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
//            String answer = restTemplate.postForObject(url, entity, String.class);
//            System.out.println(answer);


//            String opJson = "{\"count\":\""+input.length()+"\"}";
            String opJson = "{\"count\":"+ input.length() +"}";
//            Map<String, Integer> mapOk = new HashMap<>();
//            mapOk.put("count", input.length());
            System.out.println("opjson -"+ opJson);
            HttpHeaders headerForPost = new HttpHeaders();
            headerForPost.setContentType(MediaType.APPLICATION_JSON);
//            headerForPost.add("content-type", "application/json");
            headerForPost.add("userId", "alL1go0MB");
            
            HttpEntity<String> diffentity = new HttpEntity<String>(opJson,headers);
            String answer = restTemplate.postForObject("https://http-hunt.thoughtworks-labs.net/challenge/output", diffentity, String.class);
            System.out.println(answer);
//            Gson g = new Gson();
//            JsonElement element = new JsonObject();
//            String okk = "" + input.length();
//            element.add("count", okk);
            //vreate json here 
//            JSONobject personJsonObject = new JSONObject();
            
            HttpEntity entityForPost = new HttpEntity(headerForPost);
//            ResponseEntity<String> anotherResponse = restTemplate.exchange("https://http-hunt.thoughtworks-labs.net/challenge/output", HttpMethod.POST, entityForPost, String.class, mapOk);
String opResponse  = restTemplate.postForObject("https://http-hunt.thoughtworks-labs.net/challenge/output", opJson, String.class);
//            ResponseEntity<String> anotherResponse = restTemplate.exchange("http://localhost:8080/output", HttpMethod.POST, entityForPost, String.class, opJson);
//            String  opnow = anotherResponse.getBody();
//            System.out.println("anotherResponse -"+ anotherResponse);
//            System.out.println("opnow - "+ opnow);
//            System.out.println("op response -"+ mapp);
        }
        return input;
    }
}

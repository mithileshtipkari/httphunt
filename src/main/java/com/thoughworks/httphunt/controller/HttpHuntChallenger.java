/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thoughworks.httphunt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.thoughworks.httphunt.service.HttpService;
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
    
    @Autowired
    private HttpService httpService;
    
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
    
    @GetMapping("/testFirst")
    public String testFirstUseCase() throws JsonProcessingException{
        RestTemplate restTemplate = restTemplateBuilder.build();
        
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
        JsonNode text = root.path("text");
        System.out.println("text - "+ text.asText());
        String textToBeTested = text.asText();
        if(textToBeTested != null){
            int lenOfChars = textToBeTested.length();
            System.out.println("len -"+ lenOfChars);
            
            //now do post request to /output endpoint with Json as {"count" : <actual count>}
//            JsonNode countNode = 
            ObjectNode countRoot = mapper.createObjectNode();
            countRoot.put("count", lenOfChars);
            
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(countRoot);
            System.out.println("jsonString -"+ jsonString);
            
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<JsonNode> postEntity = new HttpEntity<>(countRoot, headers);
            String twURL = "https://http-hunt.thoughtworks-labs.net/challenge/output";
            String localURL = "http://localhost:8990/output";
            ResponseEntity<String> opResponseEntity = restTemplate.exchange(twURL, HttpMethod.POST, postEntity, String.class);
            System.out.println("op -"+ opResponseEntity.getBody());
            //op -{"message":"The answer is right! You can proceed to the next challenge by calling GET /challenge again!"}
        }
        return "mith";
    }
    
    @GetMapping("/secondTest")
    public String secondTest(){
        String responseFromServer = "";
        try{
            //get input
            String textToBeTested = httpService.getSecondInputFromServer();
            System.out.println("textToBeTested ="+ textToBeTested);
            
            //do processing
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode opNode = mapper.createObjectNode();
            int countOfWords = textToBeTested.split(" ").length;
            opNode.put("wordCount", countOfWords);
            
            //post it to output
            responseFromServer = httpService.postTheOutput(opNode);
            System.out.println("Response from server -"+ responseFromServer);
            //{"message":"The answer is right! You can proceed to the next challenge by calling GET /challenge again!"}
            
        } catch(JsonProcessingException ex){
            System.out.println("JsonProcessingException occured -"+ ex);
        }
        return responseFromServer;
    }
    
    @GetMapping("/thirdTest")
    public String thirdTest(){
        String responseFromServer = "";
        try{
            //get input
            String textToBeTested = httpService.getSecondInputFromServer();
//            String textToBeTested = "How often do you find yourself using an interrogation point in your everyday writing? What about an eroteme? You might be surprised to know that both of these appeared in the last two sentences. These terms might be unfamiliar, but you may know this punctuation mark by its more common name: the question mark. The question mark has a very simple function in writing—it indicates a question. If a sentence ends with a question mark, then it is asking a question, just as the name suggests.";
            System.out.println("textToBeTested -"+ textToBeTested);
            
            //do processing - find no.of sentenses, sentense can end with ? . !, all sentenses except last are delimated by a space
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode opNode = mapper.createObjectNode();
            char[] charsToSearch = {'.', '?', '!'};
            long sentenceCount = 0;
            for(char c: charsToSearch){
                sentenceCount += textToBeTested.chars().filter(t -> t == c).count();
            }
            System.out.println("len -"+ sentenceCount);
            opNode.put("sentenceCount", sentenceCount);
            
            //post it to output
            responseFromServer = httpService.postTheOutput(opNode);
            System.out.println("Response from server -"+ responseFromServer);
            //{"message":"The answer is right! You can proceed to the next challenge by calling GET /challenge again!"}
            
        } catch(Exception ex){
            System.out.println("JsonProcessingException occured -"+ ex);
        }
        return responseFromServer;
    }
    
    @GetMapping("/fourthTest")
    public String fourthTest(){
        String responseFromServer = "";
        try{
            //get input
            String textToBeTested = httpService.getSecondInputFromServer();
//            String textToBeTested = "How often do you find yourself using an interrogation point in your everyday writing? What about an eroteme? You might be surprised to know that both of these appeared in the last two sentences. These terms might be unfamiliar, but you may know this punctuation mark by its more common name: the question mark. The question mark has a very simple function in writing—it indicates a question. If a sentence ends with a question mark, then it is asking a question, just as the name suggests.";
            System.out.println("textToBeTested -"+ textToBeTested);
            
            //do processing - find no.of vowels
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode opNode = mapper.createObjectNode();
            char[] charsToSearch = {'a', 'e', 'i', 'o', 'u'};
            
            for(char c: charsToSearch){
                long vowelCount = 0;
                vowelCount += textToBeTested.chars().filter(t -> t == c || t == Character.toUpperCase(c)).count();
                
                System.out.println("vowel - "+ c + "--" + vowelCount);
                opNode.put(Character.toString(c), vowelCount);
            }
            
            
            //post it to output
            responseFromServer = httpService.postTheOutput(opNode);
            System.out.println("Response from server -"+ responseFromServer);
            //{"message":"Congratulations!! You have finished all the stages!"}
            
        } catch(Exception ex){
            System.out.println("JsonProcessingException occured -"+ ex);
        }
        return responseFromServer;
    }
}

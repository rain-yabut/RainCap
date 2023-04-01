package com.kenzie.app;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class CustomHttpClient {

    //TODO: Write sendGET method that takes URL and returns response
    public static String sendGET(String URLString) {
        //** Start of GET request algorithm
        //setting up the query/call
        HttpClient client = HttpClient.newHttpClient();
        //HttpClient connects us to the internet
        URI uri = URI.create(URLString);
        //URI is an umbrella term for URL
        HttpRequest request = HttpRequest.newBuilder()
                //this object holds the data about the query
                .uri(uri)
                //holds the URI
                .header("Accept", "application/json")
                //holds the meta-data(information about data)
                //saying you want it in JSON and you want to accept it
                .GET()
                //getting information; receiving stuff on the internet
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            //the MAGIC (^-^)
            int statusCode = httpResponse.statusCode();
            //a secret code that tells you how it went
            //in general 200s are good; 400-500s are bad
            if (statusCode == 200) { //if everything went OK
                return httpResponse.body();
            } else {
                // String.format is fun! Worth a Google if you're interested
                return String.format("GET request failed: %d status code received", statusCode);
            }
        } catch (IOException | InterruptedException e) {
            return e.getMessage();
        }
    } //ends sendGET

    public static List<Clues> getCluesList(String httpResponseBody){
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Clues>> dataType = new TypeReference<>(){};
        try {
            List<Clues> clues = mapper.readValue(httpResponseBody, dataType);
            return clues;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ArrayList<Clues>();
        }
    }

} //ends HttpClient


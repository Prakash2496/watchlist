package com.openclassrooms.watchlist.service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieRatingService {
    String apiUrl = "http://www.omdbapi.com/?i=tt3896198&apikey=3337d9b7&t=";
	
	public String getMovieRating(String title) {
		
		try {
			RestTemplate template = new RestTemplate();
			
			ResponseEntity<ObjectNode> response = 
					template.getForEntity(apiUrl + title, ObjectNode.class);
			
			ObjectNode jsonObject = response.getBody();
			
			return jsonObject.path("imdbRating").asText();
		} catch (Exception e) {
			System.out.println("Something went wront while calling OMDb API" + e.getMessage());
			return null;
		}
	}
}

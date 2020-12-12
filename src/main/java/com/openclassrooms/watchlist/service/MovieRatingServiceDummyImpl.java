package com.openclassrooms.watchlist.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(name = "app.environment", havingValue = "dev")
@Service
public class MovieRatingServiceDummyImpl implements MovieRatingService{
    String apiUrl = "http://www.omdbapi.com/?i=tt3896198&apikey=3337d9b7&t=";
    
    @Override
	public String getMovieRating(String title) {
		
		return "9.99";
	}   
}

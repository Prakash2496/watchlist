package com.openclassrooms.watchlist.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"dev"})
@Service("omdbService")
public class MovieRatingServiceDummyImpl implements MovieRatingService{
    String apiUrl = "http://www.omdbapi.com/?i=tt3896198&apikey=3337d9b7&t=";
    
    @Override
	public String getMovieRating(String title) {
		
		return "9.99";
	}   
}

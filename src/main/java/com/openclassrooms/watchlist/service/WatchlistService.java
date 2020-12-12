package com.openclassrooms.watchlist.service;

import java.util.List;
import java.util.Optional;

import com.openclassrooms.watchlist.domain.WatchlistItem;
import com.openclassrooms.watchlist.exception.DuplicateTitleException;
import com.openclassrooms.watchlist.repository.WatchlistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchlistService {
	private WatchlistRepository watchlistRepository;
	private MovieRatingService movieRatingService;

	@Autowired
	public WatchlistService(WatchlistRepository watchlistRepository, MovieRatingService movieRatingService) {
		super();
		this.watchlistRepository = watchlistRepository;
		this.movieRatingService = movieRatingService;
	}

    public List<WatchlistItem> getWatchlistItems() {
        List<WatchlistItem> watchlistItems = watchlistRepository.getList();
		for (WatchlistItem watchlistItem : watchlistItems) {
		    
			String rating = movieRatingService.getMovieRating(watchlistItem.getTitle()); 
			
			if (!rating.isEmpty()) {
				watchlistItem.setRating(rating);
			}
		}
		return watchlistItems;
    }

    public int getWatchlistItemsSize() {
        return watchlistRepository.getList().size();
    }

    public Optional<WatchlistItem> findWatchlistItemById(Integer id) {
		return watchlistRepository.findById(id);
	}
	
	public void addOrUpdateWatchlistItem(WatchlistItem watchlistItem) throws DuplicateTitleException {
		
		Optional<WatchlistItem> existingItem = findWatchlistItemById(watchlistItem.getId());
		
		if (existingItem.isEmpty()) {
			if (watchlistRepository.findByTitle(watchlistItem.getTitle()).isPresent()) {
				throw new DuplicateTitleException();
			}
			watchlistItem.setPriority(watchlistItem.getPriority().toUpperCase());
			watchlistRepository.addItem(watchlistItem);
		} else {
			existingItem.get().setComment(watchlistItem.getComment());
			existingItem.get().setPriority(watchlistItem.getPriority().toUpperCase());
			existingItem.get().setRating(watchlistItem.getRating());
			existingItem.get().setTitle(watchlistItem.getTitle());  
		}
	}
}

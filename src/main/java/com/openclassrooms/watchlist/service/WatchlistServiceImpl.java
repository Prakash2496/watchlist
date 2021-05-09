package com.openclassrooms.watchlist.service;

import java.util.List;
import java.util.Optional;

import com.openclassrooms.watchlist.exception.DuplicateTitleException;
import com.openclassrooms.watchlist.model.WatchlistItem;
import com.openclassrooms.watchlist.repository.WatchlistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class WatchlistServiceImpl implements WatchlistService{

	@Autowired
	private WatchlistRepository watchlistRepository;

	@Autowired
	private MovieRatingService movieRatingService;

	@Autowired
	public WatchlistServiceImpl(WatchlistRepository watchlistRepository, MovieRatingService movieRatingService) {
		super();
		this.watchlistRepository = watchlistRepository;
		this.movieRatingService = movieRatingService;
	}

	@Override
    public List<WatchlistItem> getWatchlistItems() {
		List<WatchlistItem> watchlistItems = watchlistRepository.findAll();

		for (WatchlistItem watchlistItem : watchlistItems) {
		    
			String rating = movieRatingService.getMovieRating(watchlistItem.getTitle()); 
			
			if (!rating.isEmpty()) {
				watchlistItem.setRating(rating);
			}
		}
		return watchlistItems;
    }

	@Override
    public int getWatchlistItemsSize() {
        return watchlistRepository.findAll().size();
    }

	@Override
	public Optional<WatchlistItem> findWatchlistItemById(String id) {
		return watchlistRepository.findById(id);
	}
	
	@Override
	public void addOrUpdateWatchlistItem(WatchlistItem watchlistItem) throws DuplicateTitleException {
		
		Optional<WatchlistItem> existingItem = findWatchlistItemById(watchlistItem.getId());
		
		if (existingItem.isEmpty()) {
			if (watchlistRepository.findByTitle(watchlistItem.getTitle()).isPresent()) {
				throw new DuplicateTitleException();
			}
			watchlistItem.setPriority(watchlistItem.getPriority().toUpperCase());
			watchlistRepository.save(watchlistItem);
		} else {
			existingItem.get().setComment(watchlistItem.getComment());
			existingItem.get().setPriority(watchlistItem.getPriority().toUpperCase());
			existingItem.get().setRating(watchlistItem.getRating());
			existingItem.get().setTitle(watchlistItem.getTitle());  
			watchlistRepository.save(watchlistItem);
		}
	}
}

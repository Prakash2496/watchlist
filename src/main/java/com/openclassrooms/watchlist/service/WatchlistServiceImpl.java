package com.openclassrooms.watchlist.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.openclassrooms.watchlist.exception.DuplicateTitleException;
import com.openclassrooms.watchlist.model.WatchlistItem;
import com.openclassrooms.watchlist.repository.WatchlistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class WatchlistServiceImpl implements WatchlistService{

	@Autowired
	private WatchlistRepository watchlistRepository;

	@Autowired
	@Qualifier("omdbService")
	private MovieRatingService movieRatingService;

	@Autowired
	public WatchlistServiceImpl(WatchlistRepository watchlistRepository, MovieRatingService movieRatingService) {
		super();
		this.watchlistRepository = watchlistRepository;
		this.movieRatingService = movieRatingService;
	}

	@Override
    public List<WatchlistItem> getWatchlistItems(String userId) {
		List<WatchlistItem> watchlistItems = watchlistRepository.findByUserId(userId);

		for (WatchlistItem watchlistItem : watchlistItems) {
		    
			String rating = movieRatingService.getMovieRating(watchlistItem.getTitle()); 
			
			if (!rating.isEmpty() && !rating.equals("N/A")) {
				watchlistItem.setRating(rating);
			}
		}
		return watchlistItems;
    }

	@Override
	public List<WatchlistItem> getWatchlistItems() {
		List<WatchlistItem> watchlistItems = watchlistRepository.findAdminWatchlist();
		for (WatchlistItem watchlistItem : watchlistItems) {
		    
			String rating = movieRatingService.getMovieRating(watchlistItem.getTitle()); 
			
			if (!rating.isEmpty() && !rating.equals("N/A")) {
				watchlistItem.setRating(rating);
			}
		}
		return watchlistItems;
	}

	@Override
    public int getWatchlistItemsSize(String userId) {
        return watchlistRepository.findByUserId(userId).size();
    }

	@Override
	public Optional<WatchlistItem> findWatchlistItemByIdAndUserId(String id, String userId) {
		return watchlistRepository.findByIdAndUserId(id, userId);
	}
	
	@Override
	public void addOrUpdateWatchlistItem(WatchlistItem watchlistItem, String userId) throws DuplicateTitleException {
		
		Optional<WatchlistItem> existingItem = findWatchlistItemByIdAndUserId(watchlistItem.getId(), userId);
		
		if (existingItem.isEmpty()) {
			if (watchlistRepository.findByTitleAndUserId(watchlistItem.getTitle(), userId).isPresent()) {
				throw new DuplicateTitleException();
			}
			watchlistItem.setPriority(watchlistItem.getPriority().toUpperCase());
			watchlistItem.setDateCreated(Calendar.getInstance().getTime());
			watchlistItem.setUserId(userId);
			watchlistRepository.save(watchlistItem);
		} else {
			WatchlistItem itemUpdated = existingItem.get();
			itemUpdated.setComment(watchlistItem.getComment());
			itemUpdated.setPriority(watchlistItem.getPriority().toUpperCase());
			itemUpdated.setRating(watchlistItem.getRating());
			itemUpdated.setTitle(watchlistItem.getTitle());  
			itemUpdated.setLastEditDate(Calendar.getInstance().getTime());
			watchlistRepository.save(itemUpdated);
		}
	}
}

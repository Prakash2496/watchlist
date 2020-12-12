package com.openclassrooms.watchlist.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.openclassrooms.watchlist.domain.WatchlistItem;

import org.springframework.stereotype.Service;

@Service
public class WatchlistRepository {

    private List<WatchlistItem> watchlistItems = new ArrayList<WatchlistItem>();
    private static int index = 1;

    public List<WatchlistItem> getList() {
        return watchlistItems;
    }

    public void addItem(WatchlistItem watchlistItem) {
        watchlistItem.setId(index++);
        watchlistItems.add(watchlistItem);
    }

    public Optional<WatchlistItem> findById(Integer id) {
		return watchlistItems.stream().filter(wis -> wis.getId().equals(id)).findFirst();
	}
	
	public Optional<WatchlistItem> findByTitle(String title) {
		return watchlistItems.stream().filter(wis -> wis.getTitle().trim().equalsIgnoreCase(title.trim())).findFirst();
	}
}

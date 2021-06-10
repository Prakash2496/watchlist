package com.openclassrooms.watchlist.repository;


import java.util.List;
import java.util.Optional;

import com.openclassrooms.watchlist.model.WatchlistItem;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepository extends MongoRepository<WatchlistItem, String>{


    Optional<WatchlistItem> findByTitle(String title);
    Optional<WatchlistItem> findByTitleAndUserId(String title, String userId);
    List<WatchlistItem> findByUserId(String userId);
    Optional<WatchlistItem> findByIdAndUserId(String id, String userId);

    @Query("{ 'userId' : { $exists: false } }")
    List<WatchlistItem> findAdminWatchlist();
    // private List<WatchlistItem> watchlistItems = new ArrayList<WatchlistItem>();
    // private static int index = 1;

    // public List<WatchlistItem> getList() {
    //     return watchlistItems;
    // }

    // public void addItem(WatchlistItem watchlistItem) {
    //     watchlistItem.setId(index++);
    //     watchlistItems.add(watchlistItem);
    // }

    // public Optional<WatchlistItem> findById(Integer id) {
	// 	return watchlistItems.stream().filter(wis -> wis.getId().equals(id)).findFirst();
	// }
	
	// public Optional<WatchlistItem> findByTitle(String title) {
	// 	return watchlistItems.stream().filter(wis -> wis.getTitle().trim().equalsIgnoreCase(title.trim())).findFirst();
	// }
}

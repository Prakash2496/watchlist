package com.openclassrooms.watchlist.service;

import java.util.List;
import java.util.Optional;

import com.openclassrooms.watchlist.exception.DuplicateTitleException;
import com.openclassrooms.watchlist.model.WatchlistItem;

public interface WatchlistService {
    public List<WatchlistItem> getWatchlistItems(String userId);
    public int getWatchlistItemsSize(String userId);
    public Optional<WatchlistItem> findWatchlistItemByIdAndUserId(String id, String userId);
    public void addOrUpdateWatchlistItem(WatchlistItem watchlistItem, String userId) throws DuplicateTitleException;
}

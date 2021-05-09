package com.openclassrooms.watchlist.service;

import java.util.List;
import java.util.Optional;

import com.openclassrooms.watchlist.exception.DuplicateTitleException;
import com.openclassrooms.watchlist.model.WatchlistItem;

public interface WatchlistService {
    public List<WatchlistItem> getWatchlistItems();
    public int getWatchlistItemsSize();
    public Optional<WatchlistItem> findWatchlistItemById(String id);
    public void addOrUpdateWatchlistItem(WatchlistItem watchlistItem) throws DuplicateTitleException;
}

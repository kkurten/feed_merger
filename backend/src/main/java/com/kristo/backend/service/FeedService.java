package com.kristo.backend.service;

import java.util.List;

import com.sun.syndication.feed.rss.Item;

/**
 * Service for RSS feeds. Fetches all {@link Item}s from multiple RSS feeds and merges them into a single {@link List}.
 * The list is cached for 15 minutes.
 * 
 * @author kkurten
 * 
 */
public interface FeedService {

    /**
     * Connects to all feeds that are configured in feed.properties, merges them and sorts feed items by date.
     * 
     * @return a merged and sorted list of feed items
     */
    List<Item> getMergedFeed();

}

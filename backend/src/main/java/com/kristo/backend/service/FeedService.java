package com.kristo.backend.service;

import java.util.List;

import com.sun.syndication.feed.rss.Item;

/**
 * Service for RSS feeds.
 * 
 * @author kkurten
 * 
 */
public interface FeedService {

    /**
     * Fetches all {@link Item}s from multiple RSS feeds and merges them into a single {@link List}. Items are sorted by
     * published date. The list is cached for 15 minutes.
     * 
     * @return a merged and sorted list of feed items
     */
    List<Item> getMergedFeed();

}

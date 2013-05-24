package com.kristo.backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.kristo.backend.conf.PropertyManager;
import com.kristo.backend.exception.FeedServiceException;
import com.kristo.backend.rss.FeedReader;
import com.kristo.backend.rss.ItemBuilder;
import com.kristo.backend.rss.ItemComparator;
import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.feed.synd.SyndEntry;

/**
 * 
 * @author kkurten
 * 
 */
@Service
public class FeedServiceImpl implements FeedService {
    private final Logger logger = LoggerFactory.getLogger(FeedServiceImpl.class);
    private static final int ESTIMATED_FEED_SIZE = 100;
    private static final String FEED_ITEMS_CACHE_KEY = "feedItems";
    @Resource
    private PropertyManager propertyManager;
    @Resource
    private FeedReader feedReader;
    @Resource
    private Cache<String, List<Item>> feedItemCache;

    @Override
    public List<Item> getMergedFeed() {
        List<Item> feedItems = buildMergedFeed();
        sortFeedItemsByPublishedDate(feedItems);

        return feedItems;
    }

    private List<Item> buildMergedFeed() {
        try {
            return feedItemCache.get(FEED_ITEMS_CACHE_KEY, new Callable<List<Item>>() {

                @Override
                public List<Item> call() throws Exception {
                    return getFeedItemsFromAllFeeds();
                }

            });
        } catch (ExecutionException e) {
            throw new FeedServiceException("Unable to fetch feed items", e);
        }
    }

    private List<Item> getFeedItemsFromAllFeeds() {
        List<Item> feedItems = new ArrayList<Item>(ESTIMATED_FEED_SIZE);

        for (String feedURL : propertyManager.getFeedURLs()) {
            for (SyndEntry syndEntry : feedReader.readSyndEntriesFromFeed(feedURL)) {
                feedItems.add(ItemBuilder.build(syndEntry));
            }
        }
        logger.info("Created and cached {} feed items", feedItems.size());

        return feedItems;
    }

    private void sortFeedItemsByPublishedDate(List<Item> feedItems) {
        Collections.sort(feedItems, new ItemComparator());
    }

    void setFeedItemCache(Cache<String, List<Item>> feedItemCache) {
        this.feedItemCache = feedItemCache;
    }

}

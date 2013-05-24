/**
 * 
 */
package com.kristo.backend.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.cache.Cache;
import com.kristo.backend.conf.BackendConfigurator;
import com.kristo.backend.conf.PropertyManager;
import com.kristo.backend.rss.FeedReader;
import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * @author kkurten
 * 
 */
public class FeedServiceImplTest {
    private static final String URL1 = "url1";
    private static final String URL2 = "url2";
    private static final String URL3 = "url3";
    private static final int NUMBER_OF_EXPECTED_ITEMS = 3;
    @Mock
    private PropertyManager propertyManager;
    @Mock
    private FeedReader feedReader;
    private Cache<String, List<Item>> feedItemCache;
    @InjectMocks
    private FeedServiceImpl feedService;
    private SyndEntry firstSyndEntry;
    private SyndEntry secondSyndEntry;
    private SyndEntry thirdSyndEntry;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);

        createMocks();
    }

    private void createMocks() throws Exception {
        feedItemCache = new BackendConfigurator().feedItemCache();
        feedService.setFeedItemCache(feedItemCache);
        when(propertyManager.getFeedURLs()).thenReturn(new String[] { URL1, URL2, URL3 });
        createSingleSyndEntryMockForEachFeed();
    }

    private void createSingleSyndEntryMockForEachFeed() {
        createSyndEntriesWithDifferentPublishedDates();
        when(feedReader.readSyndEntriesFromFeed(eq(URL1))).thenReturn(createSyndEntriesForFeed(firstSyndEntry));
        when(feedReader.readSyndEntriesFromFeed(eq(URL2))).thenReturn(createSyndEntriesForFeed(secondSyndEntry));
        when(feedReader.readSyndEntriesFromFeed(eq(URL3))).thenReturn(createSyndEntriesForFeed(thirdSyndEntry));
    }

    private void createSyndEntriesWithDifferentPublishedDates() {
        firstSyndEntry = new SyndEntryImpl();
        firstSyndEntry.setDescription(new SyndContentImpl());
        firstSyndEntry.setPublishedDate(new Date(3L));

        secondSyndEntry = new SyndEntryImpl();
        secondSyndEntry.setDescription(new SyndContentImpl());
        secondSyndEntry.setPublishedDate(new Date(2L));

        thirdSyndEntry = new SyndEntryImpl();
        thirdSyndEntry.setDescription(new SyndContentImpl());
        thirdSyndEntry.setPublishedDate(new Date(1L));
    }

    private List<SyndEntry> createSyndEntriesForFeed(SyndEntry syndEntry) {
        List<SyndEntry> syndEntries = new ArrayList<SyndEntry>();
        syndEntries.add(syndEntry);
        return syndEntries;
    }

    @Test
    public void getMergedFeed_correctNumberOfItemsIsReturned() {
        List<Item> itemResults = feedService.getMergedFeed();

        assertEquals(NUMBER_OF_EXPECTED_ITEMS, itemResults.size());
    }

    @Test
    public void getMergedFeed_itemsWereReturnedInCorrectOrder() {
        List<Item> itemResults = feedService.getMergedFeed();

        assertItemWereReturnedInCorrectOrder(itemResults);
    }

    private void assertItemWereReturnedInCorrectOrder(List<Item> itemResults) {
        assertEquals(firstSyndEntry.getPublishedDate(), itemResults.get(0).getPubDate());
        assertEquals(secondSyndEntry.getPublishedDate(), itemResults.get(1).getPubDate());
        assertEquals(thirdSyndEntry.getPublishedDate(), itemResults.get(2).getPubDate());
    }

}

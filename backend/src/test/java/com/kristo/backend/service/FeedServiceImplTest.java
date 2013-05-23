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
    private SyndEntry newestSyndEntry;
    private SyndEntry secondNewestSyndEntry;
    private SyndEntry oldestSyndEntry;

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
        createSyndEntries();
        when(feedReader.readSyndEntriesFromFeed(eq(URL1))).thenReturn(createSyndEntriesForFeed(newestSyndEntry));
        when(feedReader.readSyndEntriesFromFeed(eq(URL2))).thenReturn(createSyndEntriesForFeed(secondNewestSyndEntry));
        when(feedReader.readSyndEntriesFromFeed(eq(URL3))).thenReturn(createSyndEntriesForFeed(oldestSyndEntry));
    }

    private void createSyndEntries() {
        newestSyndEntry = new SyndEntryImpl();
        newestSyndEntry.setDescription(new SyndContentImpl());
        newestSyndEntry.setPublishedDate(new Date(3L));

        secondNewestSyndEntry = new SyndEntryImpl();
        secondNewestSyndEntry.setDescription(new SyndContentImpl());
        secondNewestSyndEntry.setPublishedDate(new Date(2L));

        oldestSyndEntry = new SyndEntryImpl();
        oldestSyndEntry.setDescription(new SyndContentImpl());
        oldestSyndEntry.setPublishedDate(new Date(1L));
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
        assertEquals(newestSyndEntry.getPublishedDate(), itemResults.get(0).getPubDate());
        assertEquals(secondNewestSyndEntry.getPublishedDate(), itemResults.get(1).getPubDate());
        assertEquals(oldestSyndEntry.getPublishedDate(), itemResults.get(2).getPubDate());
    }

    @Test
    public void foo() throws Exception {
        feedService.getMergedFeed();
    }

}

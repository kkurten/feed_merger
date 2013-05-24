/**
 * 
 */
package com.kristo.backend.rss;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * @author kkurten
 * 
 */
public class ItemBuilderTest {
    private SyndEntry syndEntry;
    private Item item;

    @Before
    public void init() {
        createSyndEntry();
    }

    private void createSyndEntry() {
        syndEntry = new SyndEntryImpl();
        syndEntry.setTitle("Test title");
        syndEntry.setLink("www.test.com/rss");

        SyndContentImpl description = new SyndContentImpl();
        description.setType("text/plain");
        description.setValue("Test description");
        syndEntry.setDescription(description);

        syndEntry.setPublishedDate(new Date());
    }

    @Test
    public void build_itemsAreCreatedCorrectly() {
        item = ItemBuilder.build(syndEntry);

        assertItemWasCreatedCorrectly();
    }

    private void assertItemWasCreatedCorrectly() {
        assertEquals(syndEntry.getTitle(), item.getTitle());
        assertEquals(syndEntry.getLink(), item.getLink());
        assertEquals(syndEntry.getLink(), item.getGuid().getValue());
        assertTrue(item.getGuid().isPermaLink());
        assertEquals(syndEntry.getDescription().getType(), item.getDescription().getType());
        assertEquals(syndEntry.getDescription().getValue(), item.getDescription().getValue());
        assertEquals(syndEntry.getPublishedDate(), item.getPubDate());
    }
}

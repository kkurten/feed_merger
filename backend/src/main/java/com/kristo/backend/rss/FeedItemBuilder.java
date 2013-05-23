/**
 * 
 */
package com.kristo.backend.rss;

import org.springframework.stereotype.Component;

import com.sun.syndication.feed.rss.Description;
import com.sun.syndication.feed.rss.Guid;
import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.feed.synd.SyndEntry;

/**
 * @author kkurten
 * 
 */
@Component
public final class FeedItemBuilder {

    public static Item build(SyndEntry syndEntry) {
        Item item = new Item();

        item.setTitle(syndEntry.getTitle());
        item.setLink(syndEntry.getLink());

        Guid guid = new Guid();
        guid.setValue(syndEntry.getLink());
        guid.setPermaLink(true);
        item.setGuid(guid);

        Description description = new Description();
        description.setType(syndEntry.getDescription().getType());
        description.setValue(syndEntry.getDescription().getValue());
        item.setDescription(description);

        item.setPubDate(syndEntry.getPublishedDate());

        return item;
    }

}

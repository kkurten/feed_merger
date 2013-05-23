package com.kristo.backend.rss;

import java.util.Comparator;

import com.sun.syndication.feed.rss.Item;

/**
 * Compares {@link Item} objects in reverse order.
 * 
 * @author kkurten
 * 
 */
public class ItemComparator implements Comparator<Item> {

    @Override
    public int compare(Item item1, Item item2) {
        return item2.getPubDate().compareTo(item1.getPubDate());
    }

}

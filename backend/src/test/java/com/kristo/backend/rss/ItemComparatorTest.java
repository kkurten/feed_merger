/**
 * 
 */
package com.kristo.backend.rss;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.sun.syndication.feed.rss.Item;

/**
 * @author kkurten
 * 
 */
public class ItemComparatorTest {
    private static final int SMALLER = -1;
    private static final int EQUAL = 0;
    private static final int GREATER = 1;
    private ItemComparator itemComparator;
    private Item newItem;
    private Item oldItem;
    private Item newItemDuplicate;

    @Before
    public void init() {
        itemComparator = new ItemComparator();
        createItems();
    }

    private void createItems() {
        newItem = new Item();
        newItem.setPubDate(new Date(2));

        newItemDuplicate = new Item();
        newItemDuplicate.setPubDate(new Date(2));

        oldItem = new Item();
        oldItem.setPubDate(new Date(1));
    }

    @Test
    public void compare_newItemIsSmallerThanOldItem() {
        assertEquals(itemComparator.compare(newItem, oldItem), SMALLER);
    }

    @Test
    public void compare_newItemsAreEqual() {
        assertEquals(itemComparator.compare(newItem, newItemDuplicate), EQUAL);
    }

    @Test
    public void compare_oldItemIsGreaterThanNewItem() {
        assertEquals(itemComparator.compare(oldItem, newItem), GREATER);
    }
}

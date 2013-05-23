/**
 * 
 */
package com.kristo.backend.pojo;

import java.util.Date;

/**
 * @author kkurten
 * 
 */
public class FeedItem {

    private final String title;
    private final String link;
    private final String description;
    private final Date pubDate;

    public FeedItem(String title, String link, String description, Date pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the pubDate
     */
    public Date getPubDate() {
        return pubDate;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

}

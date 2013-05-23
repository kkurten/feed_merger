/**
 * 
 */
package com.kristo.backend.rss;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kristo.backend.exception.FeedReaderException;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * @author kkurten
 * 
 */
@Component
public class FeedReader {
    private final Logger logger = LoggerFactory.getLogger(FeedReader.class);

    /**
     * Connects to given feed URL, reads the whole feed and returns all {@link SyndEntry} objects from feed.
     * 
     * @param feedURL
     * @return all {@link SyndEntry} objects from feed
     */
    @SuppressWarnings("unchecked")
    public List<SyndEntry> readSyndEntriesFromFeed(String feedURL) {
        XmlReader xmlReader = null;
        try {
            return connectToSyndFeed(xmlReader, feedURL).getEntries();
        } catch (IOException e) {
            throw new FeedReaderException("Unable to read feed url %s", e, feedURL);
        } catch (IllegalArgumentException e) {
            throw new FeedReaderException(e);
        } catch (FeedException e) {
            throw new FeedReaderException(e);
        } finally {
            closeXmlReader(xmlReader);
        }
    }

    private SyndFeed connectToSyndFeed(XmlReader xmlReader, String feedURL) throws MalformedURLException, IOException,
            FeedException {
        URL url = new URL(feedURL);
        xmlReader = new XmlReader(url);
        SyndFeed feed = new SyndFeedInput().build(xmlReader);

        return feed;
    }

    private void closeXmlReader(XmlReader xmlReader) {
        if (xmlReader != null) {
            try {
                xmlReader.close();
            } catch (IOException e) {
                logger.warn("Unable to close XmlReader", e);
            }
        }
    }
}

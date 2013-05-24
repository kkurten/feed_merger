/**
 * 
 */
package com.kristo.web.rss;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import com.kristo.backend.service.FeedService;
import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Item;

/**
 * Displays a merged RSS feed from multiple feeds, sorted by publish date in (newest first).
 * 
 * @author kkurten
 * 
 */
@Component("rssFeedView")
public class FeedMergerRSSViewer extends AbstractRssFeedView {
    private static final String UTF8 = "UTF-8";
    @Resource
    private FeedService feedService;

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
        setFeedMetadata(feed);
        super.buildFeedMetadata(model, feed, request);
    }

    private void setFeedMetadata(Channel feed) {
        feed.setTitle("Kauppalehden uutisia");
        feed.setDescription("Yhdistelmä neljästä eri Kauppalehden RSS syötteestä");
        feed.setLink("http://www.kauppalehti.fi");
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setCharacterEncoding(UTF8);
        return feedService.getMergedFeed();
    }

}

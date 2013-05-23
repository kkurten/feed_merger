package com.kristo.backend.conf;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sun.syndication.feed.rss.Item;

/**
 * @author kkurten
 * 
 */
@Configuration
@PropertySource(value = "classpath:feed.properties")
public class BackendConfigurator {

    @Bean
    public Cache<String, List<Item>> feedItemCache() {
        Cache<String, List<Item>> cache = CacheBuilder.newBuilder().maximumSize(1024)
                .expireAfterWrite(15, TimeUnit.MINUTES).build();
        return cache;
    }

}

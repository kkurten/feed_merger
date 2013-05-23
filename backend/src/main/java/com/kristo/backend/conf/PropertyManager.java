/**
 * 
 */
package com.kristo.backend.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author kkurten
 * 
 */
@Component
public class PropertyManager {
    @Autowired
    private Environment environment;

    public String[] getFeedURLs() {
        return environment.getProperty("feed.urls").split(",");
    }

}

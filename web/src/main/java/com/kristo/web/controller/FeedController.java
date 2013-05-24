/**
 * 
 */
package com.kristo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Forwards the user to RSS view.
 * 
 * @author kkurten
 * 
 */
@Controller
public class FeedController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(ModelMap modelMap) {
        return new ModelAndView("rssFeedView");
    }

}

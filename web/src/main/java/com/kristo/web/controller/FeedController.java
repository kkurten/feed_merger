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
 * @author kkurten
 * 
 */
@Controller
public class FeedController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(ModelMap modelMap) throws Exception {
        return new ModelAndView("rssFeedView");
    }

}

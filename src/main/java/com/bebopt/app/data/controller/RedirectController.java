package com.bebopt.app.data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectController {
    public static String url;

    
    public static void setUrl(String url) {
        RedirectController.url = url;
    }

    @RequestMapping("spotifyRedirect")
    public RedirectView redirectToUrl() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url);
        return redirectView;
    }

}

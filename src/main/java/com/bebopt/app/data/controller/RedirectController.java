package com.bebopt.app.data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.vaadin.flow.component.UI;

@Controller
public class RedirectController {
    public static String redirect_url;

    public static void setUrl(String url) {
        RedirectController.redirect_url = url;
    }

    // reroute for get mapping
    public static void redirect(String redirect_url) {
        UI.getCurrent().getPage().setLocation(redirect_url);
    }
    
    // redirect to external URL (for spotify login redirect)
    @RequestMapping("page-redirect")
    public RedirectView redirectToUrl() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirect_url);
        return redirectView;
    }

}

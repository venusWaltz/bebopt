package com.bebopt.app.data.spotify;

import com.vaadin.flow.component.UI;

public class Redirect {
 
    // reroute for get mapping
    public static void redirect(String redirect_url) {
        UI.getCurrent().getPage().setLocation(redirect_url);
    }
    
}

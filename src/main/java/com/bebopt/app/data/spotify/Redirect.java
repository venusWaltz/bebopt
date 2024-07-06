package com.bebopt.app.data.spotify;

import com.vaadin.flow.component.UI;

/**
 * The {@code Redirect} class is a utility class for redirecting the current UI page to
 * a specified URL.
 */
public class Redirect {
 
    /**
     * Redirect the current UI page to the specified URL.
     * 
     * @param redirect_url The URL to which the page should be redirected.
     */
    public static void redirect(String redirect_url) {
        UI.getCurrent().getPage().setLocation(redirect_url);
    }   
}
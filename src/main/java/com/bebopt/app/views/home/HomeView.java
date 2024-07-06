package com.bebopt.app.views.home;

import com.bebopt.app.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

/**
 * The {@code HomeView} class represents the home view of the application.
 */
@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    public static Paragraph paragraph = new Paragraph();
    
    /**
     * Constructor for the {@code HomeView} class.
     * Initializes the view components.
     */
    public HomeView() {
        Image image = new Image();
        image.setSrc("https://storage.googleapis.com/pr-newsroom-wp/1/2018/11/Spotify_Logo_RGB_Black.png");
        image.setHeight("35px");
        H2 header = new H2("Welcome!");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.NONE);
        add(header);
        add(new Paragraph("View your Spotify statistics and manage your playlists here."));
        add(image);
        setMargin(true);
    }
}
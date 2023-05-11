package com.bebopt.app.views.home;

import com.bebopt.app.data.controller.AuthController;
import com.bebopt.app.data.controller.RedirectController;
import com.bebopt.app.data.controller.SpotifyService;
import com.bebopt.app.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.User;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    public static Paragraph paragraph = new Paragraph();

    public HomeView() {
                
        Image image = new Image();
        image.setSrc("https://storage.googleapis.com/pr-newsroom-wp/1/2018/11/Spotify_Logo_RGB_Black.png");
        image.setHeight("35px");
        H2 header = new H2("Welcome!");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.NONE);
        add(header);
        add(new Paragraph("View your Spotify statistics and manage your playlists here."));
        add(image);

        // test button for Spotify login
        Button a = new Button("Login");
        a.addClickListener(e -> {
            SpotifyService.handleUserLogin();
            }
        );

        Paragraph p = new Paragraph();
        Button b = new Button("Display access token");
        b.addClickListener(e -> {
            p.setText(AuthController.getAccessToken());  
        });

        add(a, b, p);
        
        setMargin(true);
    }
    
}

package com.bebopt.app.views.playlists;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

/**
 * The {@code PlaylistsViewCard} class represents a UI component for displaying a single playlist.
 * It includes a playlist image, playlist name, and a context menu for additional options.
 */
public class PlaylistsViewCard extends ListItem {

    private PlaylistSimplified playlist;
    /**
     * Constructor for the {@code PlaylistsViewCard} class.
     * Initializes the UI component for a playlist card.
     * 
     * @param playlist A {@code PlaylistSimplified} object representing a Spotify playlist.
     */
    public PlaylistsViewCard(PlaylistSimplified playlist) {
        this.playlist = playlist;
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, 
                Padding.SMALL, BorderRadius.SMALL);

        Div div = new Div();
        div.setHeight("132px");
        div.setWidth("132px");
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.SMALL, Overflow.HIDDEN, BorderRadius.SMALL, Width.FULL);

        Image image = new Image();
        image.setWidth("100%");
        if (this.playlist.getImages().length != 0) { image.setSrc(this.playlist.getImages()[0].getUrl()); }
        else { image.setSrc("images/empty-plant.png"); }
        
        div.add(image);
        Span header = new Span();
        header.addClassNames(FontSize.MEDIUM, FontWeight.SEMIBOLD);
        header.setText(this.playlist.getName());

        this.addClickListener(e -> PlaylistsView.onPlaylistSelect(this.playlist));
        add(div, header);
    }
}
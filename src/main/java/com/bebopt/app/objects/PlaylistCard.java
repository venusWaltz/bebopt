package com.bebopt.app.objects;

import com.bebopt.app.views.PlaylistsView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

/**
 * The {@code PlaylistsViewCard} class represents a UI component for displaying a single playlist.
 * It includes a playlist image, playlist name, and a context menu for additional options.
 */
public class PlaylistCard extends ListItem {

    private PlaylistSimplified playlist;

    /**
     * Constructor for the {@code PlaylistsViewCard} class.
     * 
     * @param playlist A {@code PlaylistSimplified} object representing a Spotify playlist.
     */
    public PlaylistCard(PlaylistSimplified playlist) {
        this.playlist = playlist;
        initializeCard();
    }

    /**
     * Initializes the UI component for a playlist card.
     */
    private void initializeCard() {
        addClassNames("card-square", "playlist");
        Div div = new Div();

        Image image = new Image();
        image.setWidth("100%");
        if (playlist.getImages().length != 0) { image.setSrc(this.playlist.getImages()[0].getUrl()); }
        else { image.setSrc("images/empty-plant.png"); }
        
        Span header = new Span();
        header.addClassNames("playlist-name");
        header.setText(this.playlist.getName());
        
        this.addClickListener(e -> PlaylistsView.onPlaylistSelect(playlist));
        div.add(image);
        add(div, header);
    }
}

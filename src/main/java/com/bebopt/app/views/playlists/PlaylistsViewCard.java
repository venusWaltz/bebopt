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

public class PlaylistsViewCard extends ListItem {

    public PlaylistsViewCard(PlaylistSimplified playlist) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.SMALL,
                BorderRadius.SMALL);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.SMALL, Overflow.HIDDEN, BorderRadius.SMALL, Width.FULL);
        div.setHeight("132px");
        div.setWidth("132px");

        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(playlist.getImages()[0].getUrl());
        image.setAlt("image for playlist titled: " + playlist.getName());

        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.MEDIUM, FontWeight.SEMIBOLD);
        header.setText(playlist.getName());

        this.addClickListener(e -> {
            PlaylistsView.resetMergePlaylistSelection();
            PlaylistsView.openDialog(playlist.getId());
        });

        add(div, header);
    }
}
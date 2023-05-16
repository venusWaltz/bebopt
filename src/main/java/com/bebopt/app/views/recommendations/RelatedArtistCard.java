package com.bebopt.app.views.recommendations;

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

import se.michaelthelin.spotify.model_objects.specification.Artist;

public class RelatedArtistCard extends ListItem {

    public RelatedArtistCard(Artist artist) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.SMALL,
                BorderRadius.SMALL, Margin.Bottom.MEDIUM);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.XSMALL, Overflow.HIDDEN, BorderRadius.SMALL, Width.AUTO);
        div.setHeight("160px");
        div.setWidth("160px");

        Image image = new Image();
        if (artist.getImages()[0].getHeight() < artist.getImages()[0].getWidth())
            image.setHeight("100%");
        else
            image.setWidth("100%");
        image.setSrc(artist.getImages()[0].getUrl());

        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.MEDIUM, FontWeight.SEMIBOLD);
        header.setText(artist.getName());

        add(div, header);
    }
}

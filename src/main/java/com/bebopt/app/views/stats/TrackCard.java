package com.bebopt.app.views.stats;

import com.bebopt.app.data.entity.Track;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

public class TrackCard extends HorizontalLayout {
    
    public TrackCard(Track track, int index) {
        String height = "60px";

        Span num = new Span(String.valueOf(index + 1));
        num.setWidth("15px");
        addClassNames(AlignItems.CENTER);
        
        Div div = new Div();
        div.addClassNames(Background.CONTRAST_5, Display.GRID, AlignItems.CENTER, JustifyContent.START,
                Margin.Top.SMALL, Margin.Bottom.SMALL, Overflow.HIDDEN, BorderRadius.SMALL, Width.AUTO);
        div.setSizeFull();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addClassName("header");
        horizontalLayout.setSpacing(false);
        horizontalLayout.setWidth(100f, Unit.PERCENTAGE);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addClassName("card");
        verticalLayout.setSpacing(false);
        verticalLayout.setHeight(height);
        verticalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
    
        Image image = new Image();
        image.setWidth(height);
        image.setHeight(height);
        image.setSrc(track.getImage());

        Span title = new Span(track.getTrack());
        title.addClassNames(FontSize.SMALL, FontWeight.SEMIBOLD);
        title.addClassName("title");
        Span artist = new Span(track.getArtist());
        artist.addClassNames(FontSize.XSMALL, TextColor.SECONDARY);
        artist.addClassName("artist");
        
        add(num);
        verticalLayout.add(title, artist);
        horizontalLayout.add(image, verticalLayout);
        div.add(horizontalLayout);
        add(div);
    }
}
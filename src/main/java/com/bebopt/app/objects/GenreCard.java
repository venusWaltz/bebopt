package com.bebopt.app.objects;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

/**
 * The {@code GenreCard} class represents a UI component to display genre information.
 */
public class GenreCard extends HorizontalLayout{
    
    /**
     * Constructor for the {@code GenreCard} class.
     * Initializes the UI component for a genre card.
     * 
     * @param name The name of the genre.
     * @param percent The percentage of total listening this genre makes up.
     * @param index The index of the genre in the full list of genres.
     */
    public GenreCard(String name, int percent, int index) {
        HorizontalLayout layout = new HorizontalLayout();
        addClassNames(AlignItems.CENTER, Margin.Bottom.MEDIUM);

        Span num = new Span(String.valueOf(index + 1));
        num.setWidth("15px");
        
        Span text = new Span(name.toString());
        text.addClassNames(FontSize.LARGE, FontWeight.SEMIBOLD);
        Span perc = new Span(Integer.toString(percent) + "%");
        perc.addClassNames(FontSize.SMALL, FontWeight.SEMIBOLD);

        layout.add(text, perc);
        layout.setVerticalComponentAlignment(Alignment.CENTER, text, perc);
        add(num, layout);
        setVerticalComponentAlignment(Alignment.CENTER,num);
    }
}

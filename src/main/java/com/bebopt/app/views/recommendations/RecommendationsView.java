package com.bebopt.app.views.recommendations;

import com.bebopt.app.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.html.OrderedList;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import com.bebopt.app.data.controller.SpotifyService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Grid.Column;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.component.html.Main;
//import se.michaelthelin.spotify.model_objects.specification.RecommendationsSeed;

import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;

@PageTitle("Recommendations")
@Route(value = "Recommendations", layout = MainLayout.class)
@PermitAll
public class RecommendationsView extends Main {
    //Recommendations recommended = SpotifyService.getRec();
    //Track[] rlist = 
    //OrderedList t = RecommendedTracks();
    //RecommendedUI();
    Recommendations recommended = SpotifyService.getRecommendations();
    OrderedList trackContainerRecommended = RecommendedTracks();
    
    
    private TabSheet tabsheet;

    private OrderedList RecommendedTracks() {
        
        // get user's top tracks
        //int i = 0;
        OrderedList trackContainer = new OrderedList();
        for(int i = 0; i < recommended.getTracks().length; i++){
            trackContainer.add(new RecommendationsCard(recommended.getTracks()[i], i));
        }
        RecommendedUI(trackContainer);
        //System.out.println(recommended.getTracks().length);
        /*for(TrackSimplified track : recommended.getTracks()) {
            System.out.println(track.getName());
            System.out.println(track.getArtists());
            System.out.println(track.getExternalUrls());
            //System.out.println(track.getPreviewUrl());//not all have preview
            System.out.println(track.getUri());
            System.out.println(track.getUri());
        }*/
        //for(TrackSimplified track : recommended.getTracks()) {
            //trackContainer.add(new RecommendationsCard(track, i++));
        //}
        return trackContainer;
    }

    /*private void RecommendedUI() {
        addClassNames("recommendation-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Recommendations for you");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Select a playlist to view more options");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        headerContainer.add(header, description);
        //System.out.println("HEY MADE IT HERE");
    }*/
    private void RecommendedUI(OrderedList r) {
        tabsheet = new TabSheet();
        Div div = new Div();
        addClassNames("recommended-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Recommendations for you");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);

        Div trackDiv = new Div();
        trackDiv.add(r);
        container.add(headerContainer);
        div.add(container, trackDiv);
        tabsheet.add("Recommended", div);
        add(tabsheet);
        tabsheet.addThemeVariants(TabSheetVariant.LUMO_TABS_EQUAL_WIDTH_TABS);
        
    }
    
    
}
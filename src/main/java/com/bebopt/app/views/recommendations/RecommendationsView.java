package com.bebopt.app.views.recommendations;

import com.bebopt.app.views.MainLayout;
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
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import se.michaelthelin.spotify.model_objects.specification.Artist;

import se.michaelthelin.spotify.model_objects.specification.Track;

@PageTitle("Recommendations")
@Route(value = "Recommendations", layout = MainLayout.class)
@PermitAll
public class RecommendationsView extends Div {
    private Div RTrackTab;
    private Div RArtistTab;
    private TabSheet tabsheet;

    Recommendations recommended = SpotifyService.getRecommendations();
    OrderedList trackContainerRecommended = RecommendedTracks();
    Artist[] RelatedArtist = SpotifyService.getRelatedArtists();
    OrderedList ArtistsContainer = RelatedArtist();

    public RecommendationsView() {
        tabsheet = new TabSheet();
        RTrackTab = RTracks();
        RArtistTab = RArtists();

        tabsheet.add("Recommended Tracks", RTrackTab);
        tabsheet.add("Related Artists", RArtistTab);
        add(tabsheet);
        tabsheet.addThemeVariants(TabSheetVariant.LUMO_TABS_EQUAL_WIDTH_TABS);
        
    }

    private OrderedList RecommendedTracks() {
        
        // get user's top tracks
        //int i = 0;
        OrderedList trackContainer = new OrderedList();
        String id[] = new String[recommended.getTracks().length];
        for(int i = 0; i < recommended.getTracks().length; i++){
            id[i] = recommended.getTracks()[i].getId();
            //trackContainer.add(new RecommendationsCard(recommended.getTracks()[i], i));
        }
        String ids = String.join(",", id);
        Track[] tracks = SpotifyService.getSeveralTrack(ids);
        for(int i = 0; i < recommended.getTracks().length; i++){
            trackContainer.add(new RecommendationsCard(tracks[i], i));
        }

        //RecommendedUI(trackContainer);
        //RecommendedUI();
        return trackContainer;
    }

    private OrderedList RelatedArtist() {
        OrderedList trackContainer = new OrderedList();
        Artist[] RArtist = SpotifyService.getRelatedArtists();
        for(int i = 0; i < recommended.getTracks().length; i++){
            trackContainer.add(new RelatedArtistCard(RArtist[i]));
        }

        //RecommendedUI();
        return trackContainer;
    }

    private Div RTracks() {
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
        trackDiv.add(trackContainerRecommended);
        container.add(headerContainer);
        div.add(container, trackDiv);

        return div;
    }

    private Div RArtists() {
        Div div = new Div();
        addClassNames("Related Artists");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
        
        
        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Related Artists");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);

        container.add(headerContainer);
        Div artistDiv = new Div();
        artistDiv.add(ArtistsContainer);
        div.add(container, artistDiv);
        return div;
    }
    
}
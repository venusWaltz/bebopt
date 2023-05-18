package com.bebopt.app.views.recommendations;

import com.bebopt.app.views.MainLayout;
import com.bebopt.app.views.stats.TrackCard;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
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
import com.vaadin.flow.theme.lumo.LumoUtility.Grid.Column;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;


import se.michaelthelin.spotify.model_objects.specification.Track;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("Recommendations")
@Route(value = "Recommendations", layout = MainLayout.class)
public class RecommendationsView extends Div {
    private Div recommendedTracksTab;
    private Div recommendedArtistsTab;
    private TabSheet tabsheet;

    String trackSeed = SpotifyService.getTop5tids();
    String artistSeed = SpotifyService.getTopArtistID();
    Recommendations recommended = SpotifyService.getRecommendations(trackSeed);
    OrderedList trackContainerRecommended = RecommendedTracks();
    Artist[] relatedArtist = SpotifyService.getRelatedArtists(artistSeed);
    OrderedList artistsContainer = RelatedArtist(); 


    public RecommendationsView() {
        tabsheet = new TabSheet();
        recommendedTracksTab = createTracksTab();
        recommendedArtistsTab = createArtistsTab();

        tabsheet.add("Recommended Tracks", recommendedTracksTab);
        tabsheet.add("Related Artists", recommendedArtistsTab);

        tabsheet.addThemeVariants(TabSheetVariant.LUMO_TABS_EQUAL_WIDTH_TABS);
        add(tabsheet);
    }

    private OrderedList RecommendedTracks() {
        // get user's top tracks
        OrderedList trackContainer = new OrderedList();
        String id[] = new String[recommended.getTracks().length];
        for(int i = 0; i < recommended.getTracks().length; i++){
            id[i] = recommended.getTracks()[i].getId();
        }
        String ids = String.join(",", id);
        Track[] tracks = SpotifyService.getSeveralTrack(ids);
        for(int i = 0; i < recommended.getTracks().length; i++){
            trackContainer.add(new TrackCard(tracks[i], i));
        }

        return trackContainer;
    }

    private OrderedList RelatedArtist() {
        OrderedList trackContainer = new OrderedList();
        for (Artist artist : relatedArtist) 
            trackContainer.add(new RelatedArtistCard(artist));

        return trackContainer;
    }

    private Div createTracksTab() {
        Div div = new Div();
        addClassNames("recommended-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Recommendations");

        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);

        Div trackDiv = new Div();
        trackDiv.add(trackContainerRecommended);
        container.add(headerContainer);
        div.add(container, trackDiv);

        return div;
    }

    private Div createArtistsTab() {
        addClassNames("artist-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        Div div = new Div();
        
        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Related Artists");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);

        OrderedList AartistContainer = new OrderedList();
        AartistContainer = RelatedArtist();
        AartistContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE, JustifyContent.BETWEEN, Column.COLUMNS_5);

        container.add(headerContainer);
        Div artistDiv = new Div();
        artistDiv.add(AartistContainer);
        div.add(container, artistDiv);

        return div;
    }
    
}

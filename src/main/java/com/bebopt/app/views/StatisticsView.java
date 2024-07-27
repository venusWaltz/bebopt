package com.bebopt.app.views;

import com.bebopt.app.api.SpotifyService;
import com.bebopt.app.objects.ArtistCard;
import com.bebopt.app.objects.TrackCard;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;

import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * The {@code StatsView} class represents the statistics view of the application.
 * It displays the user's top tracks and artists over different time frames.
 */
@AnonymousAllowed
@PageTitle("Statistics")
@Route(value = "statistics", layout = MainLayout.class)
public class StatisticsView extends Div {

    private TabSheet tabsheet;
    private Div trackTab;
    private Div artistTab;

    private OrderedList genreContainer;
    Select<String> sortBy;

    private String shortTerm = "short_term";
    private String mediumTerm = "medium_term";
    private String longTerm = "long_term";

    OrderedList trackContainerShortTerm;
    OrderedList trackContainerMediumTerm;
    OrderedList trackContainerLongTerm;
    OrderedList artistContainerShortTerm;
    OrderedList artistContainerMediumTerm;
    OrderedList artistContainerLongTerm;

    /**
     * Constructor for the {@code StatsView} class.
     * Initializes the view components and loads user data from Spotify.
     */
    public StatisticsView() {
        loadStats();
        constructUI();
        add(tabsheet);
    }
    
    /**
     * Load statistics data.
     */
    private void loadStats() {
        trackContainerShortTerm = getTopTracks(shortTerm);
        trackContainerMediumTerm = getTopTracks(mediumTerm);
        trackContainerLongTerm = getTopTracks(longTerm);
        artistContainerShortTerm = getTopArtists(shortTerm);
        artistContainerMediumTerm = getTopArtists(mediumTerm);
        artistContainerLongTerm = getTopArtists(longTerm);
    }
    
    /**
     * Create the TabSheet and tabs.
     */
    private void constructUI() {
        addClassNames("page-view", "stats-view");
        tabsheet = new TabSheet();
        trackTab = createTopTracks();
        artistTab = createTopArtists();
        tabsheet.add("Top Tracks", trackTab);
        tabsheet.add("Top Artists", artistTab);
        tabsheet.addThemeVariants(TabSheetVariant.LUMO_TABS_EQUAL_WIDTH_TABS);
    }

// ------------------------------------------- Get data -------------------------------------------

    /**
     * Loads top tracks into an ordered list based on the specified term.
     * 
     * @param term The time range for top tracks (short, medium, long).
     * @return The OrderedList of top tracks.
     */
    private OrderedList getTopTracks(String term) {
        Track[] userTracks = SpotifyService.getTopTracks(term);
        int i = 0;
        OrderedList trackContainer = new OrderedList();
        for(Track track : userTracks) { trackContainer.add(new TrackCard(track, i++)); }
        return trackContainer;
    }

    /**
     * Loads top artists into an ordered list based on the specified term.
     * 
     * @param term The time range for top artists (short, medium, long).
     * @return The OrderedList of top artists.
     */
    private OrderedList getTopArtists(String term) {
        Artist[] userArtists = SpotifyService.getTopArtists(term);
        OrderedList artistContainer = new OrderedList();
        for(Artist artist : userArtists) { artistContainer.add(new ArtistCard(artist)); }
        return artistContainer;
    }

// ------------------------------------------- Tabsheet -------------------------------------------

    /**
     * Creates the "Top Tracks" tab.
     * 
     * @return The Div containing the "Top Tracks" tab content.
     */
    private Div createTopTracks() {
        Div div = new Div();

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames("header-container");

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Your Top Tracks");
        headerContainer.add(header);

        Select<String> sortBy = new Select<>();
        sortBy.setItems("Last four weeks", "Last six months", "All data");
        sortBy.setValue("Last four weeks");

        Div trackDiv = new Div();
        trackDiv.add(trackContainerShortTerm);
        container.add(headerContainer, sortBy);
        div.add(container, trackDiv);

        setChangeListener(sortBy, trackDiv, trackContainerShortTerm, trackContainerMediumTerm, trackContainerLongTerm);
        return div;
    }

    /**
     * Creates the "Top Artists" tab.
     * 
     * @return The Div containing the "Top Artists" tab content.
     */
    private Div createTopArtists() {
        Div div = new Div();
        div.addClassNames("artist-view");
        
        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames("header-container");

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Your Top Artists");
        headerContainer.add(header);

        Select<String> sortBy = new Select<>();
        sortBy.setItems("Last four weeks", "Last six months", "All data");
        sortBy.setValue("Last four weeks");

        Div artistDiv = new Div();
        artistDiv.add(artistContainerShortTerm);
        container.add(headerContainer, sortBy);
        div.add(container, artistDiv);

        setChangeListener(sortBy, artistDiv, artistContainerShortTerm, artistContainerMediumTerm, artistContainerLongTerm);
        return div;
    }

    /**
     * Creates the "Top Genres" tab.
     * 
     * @return The Div containing the "Top Genres" tab content.
     */
    private Div createTopGenres() {
        Div div = new Div();
        addClassNames("genre-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
        
        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Your Top Genres");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);
        
        genreContainer = new OrderedList();
        div.add(headerContainer, genreContainer);
        return div;
    }

    /**
     * Sets a value change listener on the given Select component to update the content
     * of the given Div based on the selected time frame.
     * 
     * @param sortBy The Select component to set the listener on.
     * @param contentDiv The Div to update based on the selected time frame.
     * @param shortTerm The container for short term data.
     * @param mediumTerm The container for medium term data.
     * @param longTerm The container for long term data.
     */
    private void setChangeListener(Select<String> sortBy, Div contentDiv, OrderedList shortTerm, 
                                   OrderedList mediumTerm, OrderedList longTerm) {
        sortBy.addValueChangeListener(e -> {
            contentDiv.removeAll();
            switch (sortBy.getValue()) {
                case "Last four weeks": contentDiv.add(shortTerm); break;
                case "Last six months": contentDiv.add(mediumTerm); break;
                case "All data": contentDiv.add(longTerm); break;
            }
        });
    }
}

package com.bebopt.app.views.stats;

import java.util.List;

import com.bebopt.app.data.entity.Track;

import com.bebopt.app.data.entity.Artist;
import com.bebopt.app.views.MainLayout;
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

import jakarta.annotation.security.PermitAll;

@PageTitle("Stats")
@Route(value = "stats", layout = MainLayout.class)
@PermitAll
public class StatsView extends Div {

    private Div trackTab;
    private Div artistTab;
    private Div genreTab;
    private TabSheet tabsheet;

    private OrderedList artistContainer;
    private OrderedList trackContainer;
    private OrderedList genreContainer;
    List<Track> tracks;
    Select<String> sortBy;
    
    public StatsView() {
        tabsheet = new TabSheet();

        // create tab page layouts
        trackTab = createTopTracks();
        artistTab = createTopArtists();
        genreTab = createTopGenres();
 
        // add tabs to tabsheet
        tabsheet.add("Top Tracks", trackTab);
        tabsheet.add("Top Artists", artistTab);
        tabsheet.add("Top Genres", genreTab);
        add(tabsheet);
        
        // distribute tabs evenly
        tabsheet.addThemeVariants(TabSheetVariant.LUMO_TABS_EQUAL_WIDTH_TABS);

        int i = 0;
        trackContainer.add(new TrackCard(new Track("(Sittin' On) the Dock of the Bay", "Otis Redding", "https://i.scdn.co/image/ab67616d0000b273b050a4bba4ad96ea639d75b3"),i++));
        trackContainer.add(new TrackCard(new Track("Soul Love", "David Bowie", "https://i.scdn.co/image/ab67616d0000b273c41f4e1133b0e6c5fcf58680"), i++));
        trackContainer.add(new TrackCard(new Track("Heat of the Moment", "Asia", "https://i.scdn.co/image/ab67616d0000b2732323f86e757c3436b3cc38af"),i++));
        trackContainer.add(new TrackCard(new Track("Mr. Blue Sky", "Electric Light Orchestra", "https://i.scdn.co/image/ab67616d0000b2738c4e95986c803791125e8991"),i++));
        trackContainer.add(new TrackCard(new Track("Time in a Bottle", "Jim Croce", "https://i.scdn.co/image/ab67616d0000b27397e92e26bae020503bdff4fb"),i++));
        trackContainer.add(new TrackCard(new Track("(Sittin' On) the Dock of the Bay", "Otis Redding", "https://i.scdn.co/image/ab67616d0000b273b050a4bba4ad96ea639d75b3"),i++));
        trackContainer.add(new TrackCard(new Track("Soul Love", "David Bowie", "https://i.scdn.co/image/ab67616d0000b273c41f4e1133b0e6c5fcf58680"), i++));
        trackContainer.add(new TrackCard(new Track("Heat of the Moment", "Asia", "https://i.scdn.co/image/ab67616d0000b2732323f86e757c3436b3cc38af"),i++));
        trackContainer.add(new TrackCard(new Track("Mr. Blue Sky", "Electric Light Orchestra", "https://i.scdn.co/image/ab67616d0000b2738c4e95986c803791125e8991"),i++));
        trackContainer.add(new TrackCard(new Track("Time in a Bottle", "Jim Croce", "https://i.scdn.co/image/ab67616d0000b27397e92e26bae020503bdff4fb"),i++));
        trackContainer.add(new TrackCard(new Track("(Sittin' On) the Dock of the Bay", "Otis Redding", "https://i.scdn.co/image/ab67616d0000b273b050a4bba4ad96ea639d75b3"),i++));
        trackContainer.add(new TrackCard(new Track("Soul Love", "David Bowie", "https://i.scdn.co/image/ab67616d0000b273c41f4e1133b0e6c5fcf58680"), i++));
        trackContainer.add(new TrackCard(new Track("Heat of the Moment", "Asia", "https://i.scdn.co/image/ab67616d0000b2732323f86e757c3436b3cc38af"),i++));
        trackContainer.add(new TrackCard(new Track("Mr. Blue Sky", "Electric Light Orchestra", "https://i.scdn.co/image/ab67616d0000b2738c4e95986c803791125e8991"),i++));
        trackContainer.add(new TrackCard(new Track("Time in a Bottle", "Jim Croce", "https://i.scdn.co/image/ab67616d0000b27397e92e26bae020503bdff4fb"),i++));

        artistContainer.add(new ArtistCard(new Artist("Queen", "https://i.scdn.co/image/af2b8e57f6d7b5d43a616bd1e27ba552cd8bfd42")));
        artistContainer.add(new ArtistCard(new Artist("The Jam", "https://i.scdn.co/image/443c4fb3b00e2205618c74b243612e36fd21d378")));
        artistContainer.add(new ArtistCard(new Artist("Pink Floyd", "https://i.scdn.co/image/d011c95081cd9a329e506abd7ded47535d524a07")));
        artistContainer.add(new ArtistCard(new Artist("Jimi Hendrix", "https://i.scdn.co/image/ab6761610000e5eb31f6ab67e6025de876475814")));
        artistContainer.add(new ArtistCard(new Artist("The Beatles", "https://i.scdn.co/image/ab6761610000e5ebe9348cc01ff5d55971b22433")));
        artistContainer.add(new ArtistCard(new Artist("David Bowie", "https://i.scdn.co/image/ab6761610000e5ebb78f77c5583ae99472dd4a49")));
        artistContainer.add(new ArtistCard(new Artist("Otis Redding", "https://i.scdn.co/image/a5897eff01844c42d894a586e618ebc4aa0b9d2f")));
        artistContainer.add(new ArtistCard(new Artist("Diana Ross", "https://i.scdn.co/image/ab67616d00001e029a2b94aa80fdbdf671f14f9e")));
        artistContainer.add(new ArtistCard(new Artist("Billie Holiday", "https://i.scdn.co/image/11f685f3011fd163c386ce1afe7ce543ad814817")));
        artistContainer.add(new ArtistCard(new Artist("Queen", "https://i.scdn.co/image/af2b8e57f6d7b5d43a616bd1e27ba552cd8bfd42")));
        artistContainer.add(new ArtistCard(new Artist("The Jam", "https://i.scdn.co/image/443c4fb3b00e2205618c74b243612e36fd21d378")));
        artistContainer.add(new ArtistCard(new Artist("Pink Floyd", "https://i.scdn.co/image/d011c95081cd9a329e506abd7ded47535d524a07")));
        artistContainer.add(new ArtistCard(new Artist("Jimi Hendrix", "https://i.scdn.co/image/ab6761610000e5eb31f6ab67e6025de876475814")));
        artistContainer.add(new ArtistCard(new Artist("The Beatles", "https://i.scdn.co/image/ab6761610000e5ebe9348cc01ff5d55971b22433")));
        artistContainer.add(new ArtistCard(new Artist("David Bowie", "https://i.scdn.co/image/ab6761610000e5ebb78f77c5583ae99472dd4a49")));
        artistContainer.add(new ArtistCard(new Artist("Otis Redding", "https://i.scdn.co/image/a5897eff01844c42d894a586e618ebc4aa0b9d2f")));
        artistContainer.add(new ArtistCard(new Artist("Diana Ross", "https://i.scdn.co/image/ab67616d00001e029a2b94aa80fdbdf671f14f9e")));
        artistContainer.add(new ArtistCard(new Artist("Billie Holiday", "https://i.scdn.co/image/11f685f3011fd163c386ce1afe7ce543ad814817")));
        artistContainer.add(new ArtistCard(new Artist("Queen", "https://i.scdn.co/image/af2b8e57f6d7b5d43a616bd1e27ba552cd8bfd42")));
        artistContainer.add(new ArtistCard(new Artist("The Jam", "https://i.scdn.co/image/443c4fb3b00e2205618c74b243612e36fd21d378")));
        artistContainer.add(new ArtistCard(new Artist("Pink Floyd", "https://i.scdn.co/image/d011c95081cd9a329e506abd7ded47535d524a07")));
        artistContainer.add(new ArtistCard(new Artist("Jimi Hendrix", "https://i.scdn.co/image/ab6761610000e5eb31f6ab67e6025de876475814")));
        artistContainer.add(new ArtistCard(new Artist("The Beatles", "https://i.scdn.co/image/ab6761610000e5ebe9348cc01ff5d55971b22433")));
        artistContainer.add(new ArtistCard(new Artist("David Bowie", "https://i.scdn.co/image/ab6761610000e5ebb78f77c5583ae99472dd4a49")));
        artistContainer.add(new ArtistCard(new Artist("Otis Redding", "https://i.scdn.co/image/a5897eff01844c42d894a586e618ebc4aa0b9d2f")));
        artistContainer.add(new ArtistCard(new Artist("Diana Ross", "https://i.scdn.co/image/ab67616d00001e029a2b94aa80fdbdf671f14f9e")));
        artistContainer.add(new ArtistCard(new Artist("Billie Holiday", "https://i.scdn.co/image/11f685f3011fd163c386ce1afe7ce543ad814817")));

        int j = 0;
        genreContainer.add(new GenreCard("Rock", 50, j++));
        genreContainer.add(new GenreCard("Pop", 35, j++));
        genreContainer.add(new GenreCard("Blues", 15, j++));
    }

    private Div createTopTracks() {
        Div div = new Div();
        addClassNames("track-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Your Top Tracks");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);

        Select<String> sortBy = new Select<>();
        sortBy.setItems("Recent", "Last four weeks", "Last six months", "All data");
        sortBy.setValue("Recent");

        trackContainer = new OrderedList();

        container.add(headerContainer, sortBy);
        div.add(container, trackContainer);

        return div;
    }

    private Div createTopArtists() {
        addClassNames("artist-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        Div div = new Div();
        
        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Your Top Artists");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);

        Select<String> sortBy = new Select<>();
        sortBy.setItems("Recent", "Last four weeks", "Last six months", "All data");
        sortBy.setValue("Recent");

        artistContainer = new OrderedList();
        artistContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE, JustifyContent.BETWEEN, Column.COLUMNS_5);

        container.add(headerContainer, sortBy);
        div.add(container, artistContainer);
        return div;
    }
    private Div createTopGenres() {
        addClassNames("genre-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
        
        Div div = new Div();
        
        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Your Top Genres");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);
        
        genreContainer = new OrderedList();
        div.add(headerContainer, genreContainer);
        return div;
    }
}
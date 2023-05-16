package com.bebopt.app.views.playlists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bebopt.app.data.controller.SpotifyService;
import com.bebopt.app.views.MainLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.DialogVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
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
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Grid.Column;

import jakarta.annotation.security.PermitAll;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

@PageTitle("Playlists")
@Route(value = "playlists", layout = MainLayout.class)
@PermitAll
public class PlaylistsView extends Main {

    private static String currentlySelectedPlaylist;

    // container of playlist cards
    private OrderedList playlistsContainer;

    // tabsheet components
    private static TabSheet tabsheet;
    private static VerticalLayout sortTab;
    private static VerticalLayout filterTab;
    private static VerticalLayout mergeTab;

    // radio button groups
    private static RadioButtonGroup<String> radioGroupSort;
    private static RadioButtonGroup<String> radioGroupFilter;
    private static RadioButtonGroup<String> radioGroupFilterDecades;
    private static ListBox<PlaylistSimplified> listBoxMerge;

    // selected options
    private static String selectedSort;
    private static String selectedFilter;
    private static Div decadesContainer;
    private static List<String> decadeKeysStr;
    private static Integer selectedDecade;
    static String selectedPlaylistToMerge;

    public static Dialog dialog;

    public PlaylistsView() throws Exception {
        constructUI();

        // load playlist data from spotify
        PlaylistSimplified[] playlists = SpotifyService.getPlaylists();

        // save playlists into image container
        for(PlaylistSimplified playlist : playlists) {
                playlistsContainer.add(new PlaylistsViewCard(playlist));
        }
    }

// ----------------------------------------- Construct UI and tabsheet -----------------------------------------

    private void constructUI() {
        addClassNames("playlists-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Your Playlists");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Select a playlist to view more options");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        headerContainer.add(header, description);

        playlistsContainer = new OrderedList();
        playlistsContainer.addClassNames(Gap.SMALL, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE, JustifyContent.BETWEEN, Column.COLUMNS_6);
        
        // playlist action dialog - appears when user selects a playlist
        Dialog dialog = createDialog();
        container.add(headerContainer);
        add(container, playlistsContainer, dialog);
    }

    // create tabsheet for dialog window
    private static TabSheet createTabsheet() {
        tabsheet = new TabSheet();      // create tabsheet container

        sortTab = sortTab();            // create sort tab
        filterTab = filterTab();        // create filter tab
        mergeTab = mergeTab();          // create merge tab

        // add tabs to tabsheet
        tabsheet.add("Sort", sortTab);
        tabsheet.add("Filter", filterTab);
        tabsheet.add("Merge", mergeTab);

        // space tabs equally
        tabsheet.addThemeVariants(TabSheetVariant.LUMO_TABS_EQUAL_WIDTH_TABS);

        return tabsheet;
    }    

    // create sort tab for dialog window tabsheet
    private static VerticalLayout sortTab() {
        VerticalLayout sortLayout = new VerticalLayout();

        // list of radio button group items (sort options)
        List<String> items = Arrays.asList("Release date", "Duration", "Popularity", "Acousticness",
        "Danceability", "Energy", "Instrumentalness", "Loudness", "Speechiness", "Tempo", "Valence");  

        // create button group of sort options
        radioGroupSort = new RadioButtonGroup<>();
        radioGroupSort.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroupSort.setLabel("Sort by: ");
        radioGroupSort.setItems(items);
        radioGroupSort.setValue("Release date");
        sortLayout.add(radioGroupSort);

        // save currently selected button
        radioGroupSort.addValueChangeListener(e -> { selectedSort = e.getValue(); });

        return sortLayout;
    }

    // create filter tab for dialog window tabsheet
    private static VerticalLayout filterTab() {
        VerticalLayout filterLayout = new VerticalLayout();
        
        // list of radio group button items (filter options)
        List<String> items = Arrays.asList("Release decade");      

        // create button group of filter options
        radioGroupFilter = new RadioButtonGroup<>();
        radioGroupFilter.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroupFilter.setLabel("Filter by: ");
        radioGroupFilter.setItems(items);
        createDecadeSelectRadioGroup();
        decadesContainer = new Div();
        decadesContainer.setVisible(false);
        decadesContainer.addClassName("indented-radio-group-container");
        decadesContainer.add(radioGroupFilterDecades);
        filterLayout.add(radioGroupFilter, decadesContainer);

        // save currently selected button
        radioGroupFilter.addValueChangeListener(e -> { 
            selectedFilter = e.getValue(); 

            if (selectedFilter.equals("Release decade")) {
                if (radioGroupFilterDecades.isEmpty()) {
                    // create hash map and get keys
                    List<String> decadeKeysStr = getDecadeKeys();
                    Collections.sort(decadeKeysStr);
                    // add decade options to view
                    radioGroupFilterDecades.setItems(decadeKeysStr);
                    decadesContainer.add(radioGroupFilterDecades);
                    decadesContainer.setVisible(true);
                }
                else
                    decadesContainer.setVisible(true);
            }
            else
                decadesContainer.setVisible(false);
        });

        return filterLayout;
    }

    public static List<String> getDecadeKeys() {
        Set<Integer> decadeMapKeys = PlaylistActions.filterGetKeys(currentlySelectedPlaylist);
        // display available decades below radio button in new radio group
        decadeKeysStr = decadeMapKeys.stream().map(key->key+"s").collect(Collectors.toList());
        return decadeKeysStr;
    }

    public static void createDecadeSelectRadioGroup() {
        radioGroupFilterDecades = new RadioButtonGroup<>();
        radioGroupFilterDecades.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroupFilterDecades.addClassName("indented-radio-group");
        radioGroupFilterDecades.addValueChangeListener(event -> {
            selectedDecade = Integer.valueOf(event.getValue().substring(0, 4));
        });
    }

    // create merge tab for dialog window tabsheet
    private static VerticalLayout mergeTab() {
        VerticalLayout mergeLayout = new VerticalLayout();

        // list of other playlists to merge current playlist with
        PlaylistSimplified[] playlists = SpotifyService.getPlaylists();

        List<PlaylistSimplified> items = Arrays.asList(playlists);
        
        // create list of playlists
        listBoxMerge = new ListBox<>();
        listBoxMerge.setItems(items);
        Label label = new Label("Select a second playlist: ");
        label.addClassNames(FontSize.SMALL, Margin.Top.MEDIUM);

        // list box renderer (to display playlist image)
        listBoxMerge.setRenderer(new ComponentRenderer<>(item -> {
            HorizontalLayout row = new HorizontalLayout();
            row.addClassNames(AlignItems.CENTER);
            
            // add playlist cover image and name
            Avatar a = new Avatar();
            a.setName("Image");
            a.setImage(item.getImages()[0].getUrl());
            Span name = new Span(item.getName());
            
            row.add(a, name);
            row.getStyle().set("line-height", "var(--lumo-line-height-m)");
            return row;
        }));

        listBoxMerge.addValueChangeListener(e -> selectedPlaylistToMerge = listBoxMerge.getValue().getId().toString());
        mergeLayout.add(label, listBoxMerge);

        return mergeLayout;
    }

// ----------------------------------------- Confirm dialog -----------------------------------------

    // create dialog window
    public static Dialog createDialog() {
        // create dialog
        dialog = new Dialog();
        dialog.setHeaderTitle("Playlist Options");
        dialog.setHeight("800px");
        dialog.setWidth("650px");
        dialog.addThemeVariants(DialogVariant.LUMO_NO_PADDING);
        dialog.add(createTabsheet());

        // add close button at top right of dialog header
        Button closeButton = new Button(new Icon("lumo", "cross"), e -> dialog.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getHeader().add(closeButton);

        // button to make changes directly to current playlist
        Button edit = new Button("Edit Playlist", e -> {
            dialogChooseActionEvent();
        });
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);      // button style
        dialog.getFooter().add(edit);                           // add button to footer

        // button to add songs to new empty playlist
        Button newPlaylist = new Button("New Playlist", e -> {
            dialogChooseActionEvent();
        });
        newPlaylist.addThemeVariants(ButtonVariant.LUMO_PRIMARY);       // button style
        dialog.getFooter().add(newPlaylist);                            // add button to footer
        
        return dialog;
    }

    // prompt user to confirm selected actions
    public static void dialogChooseActionEvent() {
        Tab t = tabsheet.getSelectedTab();  
        if (t == tabsheet.getTabAt(0)) {
            confirmDialog("Sort", selectedSort);
        }
        else if (t == tabsheet.getTabAt(1)) {
            confirmDialog("Filter", selectedFilter);
        }
        else if (t == tabsheet.getTabAt(2)) {
            confirmDialog("Merge", selectedPlaylistToMerge);
        }
    }

    // save first selected playlist and open dialog for other options
    public static void openDialog(String selected) {
        currentlySelectedPlaylist = selected;
        dialog.open();
    }

    // create confirmation dialog
    private static void confirmDialog(String action, String selectedAction) {
        // create dialog
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setWidth("550px");
        confirmDialog.setHeader("Confirm");

        if (selectedAction == null) {
            errorDialog();
            return;
        }

        // confirmation message
        if (action == "Merge") {
            confirmDialog.setText("Are you sure you want to merge the following playlists:\n" 
                + (SpotifyService.getPlaylistById(currentlySelectedPlaylist).getName()) + " and " 
                + SpotifyService.getPlaylistById(selectedPlaylistToMerge).getName() + "?");
        }
        else {
            confirmDialog.setText("Are you sure you want to " + action.toLowerCase() + 
                " this playlist by " + selectedAction.toLowerCase() + "?");
        }
        
        confirmDialog.setCancelable(true);
        confirmDialog.setConfirmText(action);

        // check if user has confirmed action
        confirmDialog.addConfirmListener(e -> {
            if (action == "Sort") {
                PlaylistActions.sortPlaylist(currentlySelectedPlaylist, selectedSort);
            }
            else if (action == "Filter") {
                PlaylistActions.filterPlaylist(currentlySelectedPlaylist, selectedFilter, selectedDecade);
            }
            else if (action == "Merge") {
                PlaylistActions.mergePlaylists(currentlySelectedPlaylist, selectedPlaylistToMerge);
            }
        });

        confirmDialog.open();   // open dialog
    }

    // reset values of second selected playlist when a new first playlist is selected
    public static void resetMergePlaylistSelection() {
        radioGroupSort.setValue(null);
        radioGroupFilter.setValue(null);
        listBoxMerge.setValue(null);
        decadesContainer.setVisible(false);
        radioGroupFilterDecades.clear();
        decadeKeysStr = null;
        selectedDecade = null;
        selectedSort = selectedFilter = selectedPlaylistToMerge = null;
    }

// ---------------------------------------- Error dialog ----------------------------------------

    // display error dialog when second playlist has not been selected
    private static void errorDialog() {
        // create dialog
        ConfirmDialog errorDialog = new ConfirmDialog();
        errorDialog.setWidth("550px");
        errorDialog.setHeader("Error");
        errorDialog.setText("Please make a selection.");
        errorDialog.setConfirmText("Continue");
        errorDialog.open();
    }
}

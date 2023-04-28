package com.bebopt.app.views.playlists;

import java.util.Arrays;
import java.util.List;

import com.bebopt.app.views.MainLayout;
import com.vaadin.flow.component.Text;
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
import com.vaadin.flow.component.notification.Notification;
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

@PageTitle("Playlists")
@Route(value = "playlists", layout = MainLayout.class)
@PermitAll
public class PlaylistsView extends Main {

    private OrderedList imageContainer;
    public static Dialog dialog;
    private static TabSheet tabsheet;
    private Tab t;
    private static VerticalLayout sortTab;
    private static VerticalLayout filterTab;
    private static VerticalLayout mergeTab;
    private static String sort;
    private static String filter;
    private static String merge;

    public PlaylistsView() {
        constructUI();

        imageContainer.add(new PlaylistsViewCard("Snow mountains under stars",
                "https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        imageContainer.add(new PlaylistsViewCard("Snow covered mountain",
                "https://images.unsplash.com/photo-1512273222628-4daea6e55abb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        imageContainer.add(new PlaylistsViewCard("River between mountains",
                "https://images.unsplash.com/photo-1536048810607-3dc7f86981cb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=375&q=80"));
        imageContainer.add(new PlaylistsViewCard("Milky way on mountains",
                "https://images.unsplash.com/photo-1515705576963-95cad62945b6?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=750&q=80"));
        imageContainer.add(new PlaylistsViewCard("Mountain with fog",
                "https://images.unsplash.com/photo-1513147122760-ad1d5bf68cdb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=80"));
        imageContainer.add(new PlaylistsViewCard("Mountain at night",
                "https://images.unsplash.com/photo-1562832135-14a35d25edef?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=815&q=80"));
        imageContainer.add(new PlaylistsViewCard("Snow mountains under stars",
                "https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        imageContainer.add(new PlaylistsViewCard("Snow covered mountain",
                "https://images.unsplash.com/photo-1512273222628-4daea6e55abb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        imageContainer.add(new PlaylistsViewCard("River between mountains",
                "https://images.unsplash.com/photo-1536048810607-3dc7f86981cb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=375&q=80"));
        imageContainer.add(new PlaylistsViewCard("Milky way on mountains",
                "https://images.unsplash.com/photo-1515705576963-95cad62945b6?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=750&q=80"));
        imageContainer.add(new PlaylistsViewCard("Mountain with fog",
                "https://images.unsplash.com/photo-1513147122760-ad1d5bf68cdb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=80"));
        imageContainer.add(new PlaylistsViewCard("Mountain at night",
                "https://images.unsplash.com/photo-1562832135-14a35d25edef?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=815&q=80"));

    }

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

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.SMALL, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE, JustifyContent.BETWEEN, Column.COLUMNS_6);
        
        Dialog dialog = createDialog();
        container.add(headerContainer);
        add(container, imageContainer, dialog);
    }

    public static Dialog createDialog() {
        // create dialog window for playlist options
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
                Tab t = tabsheet.getSelectedTab();  
                if (t == tabsheet.getTabAt(0)) {
                        confirmDialog("Sort", sort);
                }
                else if (t == tabsheet.getTabAt(1)) {
                        confirmDialog("Filter", filter);
                }
                else if (t == tabsheet.getTabAt(2)) {
                        confirmDialog("Merge", merge);
                }
        });
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);      // button style
        dialog.getFooter().add(edit);                           // add button to footer

        // button to add songs to new empty playlist
        Button newPlaylist = new Button("New Playlist", e -> {
                Tab t = tabsheet.getSelectedTab(); 
                if (t == tabsheet.getTabAt(0)) {
                        confirmDialog("Sort", sort);
                }
                else if (t == tabsheet.getTabAt(1)) {
                        confirmDialog( "Filter", filter);
                }
                else if (t == tabsheet.getTabAt(2)) {
                        confirmDialog( "Merge", merge);
                }
        });
        newPlaylist.addThemeVariants(ButtonVariant.LUMO_PRIMARY);       // button style
        dialog.getFooter().add(newPlaylist);                            // add button to footer
        
        // temp
        // Button button = new Button("button", e -> dialog.open());
        
        return dialog;
    }

    // create tabsheet for dialog window
    private static TabSheet createTabsheet() {
        tabsheet = new TabSheet();

        sortTab = sortTab();            // create sort tab
        filterTab = filterTab();        // create filter tab
        mergeTab = mergeTab();          // create merge tab

        // add tabs to tabsheet
        tabsheet.add("Sort", sortTab);
        tabsheet.add("Filter", filterTab);
        tabsheet.add("Merge", mergeTab);

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
        RadioButtonGroup<String> radioGroupSort = new RadioButtonGroup<>();
        radioGroupSort.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroupSort.setLabel("Sort by: ");
        radioGroupSort.setItems(items);
        radioGroupSort.setValue("Release date");
        sort = "Release date"; // set first option to be selected by default
        sortLayout.add(radioGroupSort);

        // save currently selected button
        radioGroupSort.addValueChangeListener(e -> { sort = e.getValue(); });

        return sortLayout;
    }

    // create filter tab for dialog window tabsheet
    private static VerticalLayout filterTab() {
        VerticalLayout filterLayout = new VerticalLayout();
        
        // list of radio group button items (filter options)
        List<String> items = Arrays.asList("Release date", "Audio quality", "Popularity");      

        // create button group of filter options
        RadioButtonGroup<String> radioGroupFilter = new RadioButtonGroup<>();
        radioGroupFilter.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroupFilter.setLabel("Filter by: ");;
        radioGroupFilter.setItems(items);
        radioGroupFilter.setValue("Release date");
        filter = "Release date"; // set first option to be selected by default
        filterLayout.add(radioGroupFilter);

        // save currently selected button
        radioGroupFilter.addValueChangeListener(e -> { filter = e.getValue(); });

        return filterLayout;
    }

    // create merge tab for dialog window tabsheet
    private static VerticalLayout mergeTab() {
        VerticalLayout mergeLayout = new VerticalLayout();

        // list of other playlists to merge current playlist with
        List<String> items = Arrays.asList("Playlist A", "Playlist B", "Playlist C");   
        
        // create list of playlists
        ListBox<String> listBox = new ListBox<>();
        listBox.setItems(items);
        Label label = new Label("Select a second playlist: ");
        label.addClassNames(FontSize.SMALL, Margin.Top.MEDIUM);

        // list box renderer (to display playlist image)
        listBox.setRenderer(new ComponentRenderer<>(item -> {
                HorizontalLayout row = new HorizontalLayout();
                row.addClassNames(AlignItems.CENTER);
                
                Avatar a = new Avatar();
                a.setName("Image");
                a.setImage("https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80");
                
                Span name = new Span(item);
                
                row.add(a, name);
                row.getStyle().set("line-height", "var(--lumo-line-height-m)");
                return row;
        }));
        merge = "New Playlist";
        listBox.addValueChangeListener(e -> { merge = e.getValue(); Notification.show(merge.toString()); });
        mergeLayout.add(label, listBox);

        return mergeLayout;
    }

    // create confirmation dialog
    private static Boolean confirmDialog(String action, String selectedAction) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setWidth("550px");
        confirmDialog.setHeader("Confirm");
        Div div = new Div(new Text("Are you sure you want to " + action.toLowerCase() + 
                (action == "Merge" ? " these playlists" : " this playlist by " + selectedAction.toLowerCase()) + "?"));
        confirmDialog.add(div);
        Boolean con = false;
        confirmDialog.setCancelable(true);
        confirmDialog.setConfirmText(action);
        confirmDialog.addConfirmListener(e -> setConfirm(con, true));
        confirmDialog.open();
        return con;
    }

    private static void setConfirm(Boolean a, Boolean b) {
        a = b;
    }

}

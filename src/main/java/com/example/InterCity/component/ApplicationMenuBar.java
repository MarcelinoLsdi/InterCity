package com.example.InterCity.component;

import com.example.InterCity.service.MedService;
import com.example.InterCity.view.IndexUI;
import com.example.InterCity.view.IndexView;
import com.example.InterCity.view.MapView;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;



public class ApplicationMenuBar extends MenuBar {

    public ApplicationMenuBar(MedService medService, Panel contentPanel, IndexUI indexUI){

        MenuItem resourcesMenuItem = addItem("View", null, null);
            resourcesMenuItem.addItem("Farmacias", null, c -> {
            contentPanel.setContent(new IndexView(medService, indexUI));
        });
        MenuItem mapItem = addItem("Map", null, null);
        mapItem.addItem("Map View", null, c -> {
            contentPanel.setContent(new MapView(medService, indexUI));
        });

        setSizeFull();
    }
}

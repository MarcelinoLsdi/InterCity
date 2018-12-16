package com.example.InterCity.view;

import com.example.InterCity.component.AppGson;
import com.example.InterCity.model.Resource;
import com.example.InterCity.service.MedService;
import com.google.gson.Gson;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import lombok.val;

public class IndexView extends VerticalLayout {

    private final MedService medService;

    Gson gson = AppGson.get();

    public IndexView(MedService medService, IndexUI indexUI){
        this.medService = medService;
        VerticalLayout map = createPanel2();

        GridLayout grid = new GridLayout(2,1);
        grid.setSizeFull();
        grid.setColumnExpandRatio(0,1);
        grid.addComponent(map);
        addComponent(grid);

        setHeight("100%");
        setWidth("100%");
    }

    private VerticalLayout createPanel() {

        VerticalLayout vl = new VerticalLayout();

        Label resourcesLabel = new Label("Resources: ");
        vl.addComponents(resourcesLabel);

        return vl;
    }

    private VerticalLayout createPanel2() {

        VerticalLayout vl = new VerticalLayout();

        Label resourcesLabel = new Label("Farmacias: ");
        resourcesLabel.addStyleName("h2");
        val nr = medService.buscarFarmaciaPeloMedicamento("formet");

        Grid<Resource> grid = new Grid<>();
        grid.setItems(nr);
        grid.addColumn(Resource::getDescription).setCaption("Nome");
        grid.addColumn(Resource::getUuid).setCaption("UUID");
        grid.addColumn(Resource::getLat).setCaption("Lat");
        grid.addColumn(Resource::getLon).setCaption("Lon");



        Label numberResources = new Label(nr.size()+"");


        vl.addComponents(
                resourcesLabel,numberResources,
                grid
        );

        return vl;
    }

}

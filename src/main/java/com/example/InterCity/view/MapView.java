package com.example.InterCity.view;

import com.example.InterCity.model.Resource;
import com.example.InterCity.service.MedService;
import com.vaadin.annotations.Widgetset;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import lombok.val;
import org.vaadin.addon.leaflet.LMap;
import org.vaadin.addon.leaflet.LMarker;
import org.vaadin.addon.leaflet.LOpenStreetMapLayer;
import org.vaadin.addon.leaflet.markercluster.LMarkerClusterGroup;
import org.vaadin.addon.leaflet.shared.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Widgetset("com.example.InterCity.LeafletWidgetset")
public class MapView extends HorizontalLayout {

    private final MedService medService;

    LMap map;
    LMarkerClusterGroup cluster;

    double zoom =  15.0;

    public MapView(MedService medService, IndexUI indexUI){
        this.medService = medService;
        map = new LMap();
        map.setCenter(-2.476825, -44.181216);
        map.setZoomLevel(zoom);
        map.setSizeFull();

        LMarker lMarker1 = new LMarker(-2.476825, -44.181216);
        LMarker lMarker2 = new LMarker(-2.476825, -44.581216);

        cluster = new LMarkerClusterGroup();

        LOpenStreetMapLayer layer = new LOpenStreetMapLayer();
        map.addBaseLayer(layer, "OSM");

        map.addLayer(cluster);

        VerticalLayout form = createForm();

        GridLayout grid = new GridLayout(2,1);
        grid.setSizeFull();
        grid.setMargin(false);
        grid.setSpacing(false);
        grid.setColumnExpandRatio(0, 0.6f);
        grid.setColumnExpandRatio(1, 0.4f);
        grid.addComponent(map);
        grid.addComponent(form);

        addComponent(grid);

        //setSizeFull();
        setWidth("100%");
        setHeight("100%");



    }

    public VerticalLayout createForm(){
        VerticalLayout vl = new VerticalLayout();
        //vl.setMargin(false);
        vl.setSpacing(false);

        Label searchLabel = new Label("Farmácias");
        searchLabel.addStyleName("h2");

        VerticalLayout resTab = new VerticalLayout();
        resTab.setWidth("100%");
        VerticalLayout dataTab = new VerticalLayout();
        dataTab.setWidth("100%");
        TabSheet tabs =  new TabSheet();
        tabs.setSizeFull();

        tabs.addTab(resTab).setCaption("Buscar Medicamentos");
        //tabs.addTab(dataTab).setCaption("Context Data");

        vl.addComponent(searchLabel);
        vl.addComponent(tabs);


        TextField capabilityTextField = new TextField("Medicamento");
        capabilityTextField.setSizeFull();

        val button = new Button("Buscar");

        val line1 = new HorizontalLayout(capabilityTextField);
        line1.setSizeFull();
        resTab.addComponent(line1);

        resTab.addComponent(button);

        button.addClickListener(e -> {
            val request = new Resource();
            String[] capa = {capabilityTextField.getValue()};
            if (!capabilityTextField.getValue().isEmpty()) request.setCapabilities(capa);
            List<Resource> resources = new ArrayList<>(medService.buscarFarmaciaPeloMedicamento(request.getCapabilities()[0].toUpperCase().trim()));

            resources.forEach(r -> {
                LMarker marker = new LMarker(r.getLat(), r.getLon());
                marker.setTitle(r.getDescription());
                marker.setPopup(createPopoup(r));
                cluster.addComponent(marker);
            });

            if (resources.size() > 0) {

                map.flyTo(resources.stream().map(res -> new Point(res.getLat(), res.getLon())).findAny().get(), zoom);
            }

        });

        return vl;
    }

    private String createPopoup(Resource resource) {
        String popup = "<p>";
        popup += "<b>UUID:</b> " + resource.getUuid() + "<br/>";
        popup += "<b>Description:</b> " + resource.getDescription() + "<br/>";
        popup += "<b>Latitude:</b> " + resource.getLat() + "<br/>";
        popup += "<b>Longitude:</b> " + resource.getLon() + "<br/>";

        popup += "</p>";
        return popup;
    }

}


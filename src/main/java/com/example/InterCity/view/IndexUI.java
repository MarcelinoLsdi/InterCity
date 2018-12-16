package com.example.InterCity.view;

import com.example.InterCity.component.ApplicationMenuBar;
import com.example.InterCity.service.MedService;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import lombok.Getter;


@SpringUI(path = "/")
@Theme("valo")
@Widgetset("com.example.InterCity.LeafletWidgetset")
public class IndexUI extends UI {

    @Getter
    private Panel contentPanel;

    private MedService medService;

    public IndexUI(MedService medService){
        this.medService = medService;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        contentPanel = new Panel();

        contentPanel.setSizeFull();

        contentPanel.setContent(new IndexView(medService, this));
        ApplicationMenuBar applicationMenuBar = new ApplicationMenuBar(medService, contentPanel,this);
        applicationMenuBar.setHeightUndefined();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(false);
        verticalLayout.setSpacing(false);
        verticalLayout.setWidth("100%");
        verticalLayout.setHeight("100%");
        verticalLayout.addComponent(applicationMenuBar);
        verticalLayout.addComponent(contentPanel);
        verticalLayout.setExpandRatio(contentPanel,1);

        setContent(verticalLayout);

    }
}

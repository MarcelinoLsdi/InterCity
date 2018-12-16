package com.example.InterCity.model;

import lombok.Data;

@Data
public class Context {
    private String capability;
    private Double qtde;


    public Context(){

    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
    }

    public Double getQtde() {
        return this.qtde;
    }

    public void setQtde(Double qtde) {
        this.qtde = qtde;
    }
}

package com.example.InterCity.model;

import java.util.ArrayList;
import java.util.List;


public class Data {
    List<CapabilityDataAuxiliar> data;


    public List<CapabilityDataAuxiliar> getData() {
        if(data == null){
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<CapabilityDataAuxiliar> data) {
        this.data = data;
    }



}

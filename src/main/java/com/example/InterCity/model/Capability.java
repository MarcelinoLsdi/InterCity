package com.example.InterCity.model;


import lombok.Data;

@Data
public class Capability {
    Long id;
    String name;
    String function;
    String description;
    String capabilityType;

    public Capability(String name){
        this.name = name;
    }

    public String toString(){
        return name + " : " + description;
    }
}

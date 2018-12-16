package com.example.InterCity.model;

import lombok.Data;

import java.util.Map;

@Data
public class CapabilityDataAuxiliar {
    private Double value;
    private String timestamp;
    private Resource resource;
    private Double lat;
    private Double lon;
    private String description;


    public CapabilityDataAuxiliar(Map<String, Object> capability) {
            this.value = (Double) capability.get("qtde");

           this.timestamp = (String) capability.get("date");
        this.lat = (Double) capability.get("lat");
        this.lon = (Double) capability.get("lon");
        Map<String, Object> resourceMap = (Map<String, Object>) capability.get("resource");

        Resource resource = new Resource();

        if (resourceMap != null) {
            for (String key : resourceMap.keySet()) {
                resource.setUuid((String) resourceMap.get("uuid"));
                resource.setDescription((String) resourceMap.get("description"));
                resource.setLat((Double) resourceMap.get("lat"));
                resource.setLon((Double) resourceMap.get("lon"));
            }
        }

        this.resource = resource;

    }


    public CapabilityDataAuxiliar() {
    }
}

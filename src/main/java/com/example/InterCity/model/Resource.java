package com.example.InterCity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Resource {
    String uuid;
    String description;
    String [] capabilities;
    String status;
    Double lat;
    Double lon;
    String country;
    String state;
    String city;
    String neighborhood;
    String postalCode;
    @JsonFormat(pattern = "YYYY-mm-dd")
    LocalDate createdAt;
    @JsonFormat(pattern = "YYYY-mm-dd")
    LocalDate updatedAt;
    Long id;
    Long collectInterval;
    String uri;
    Double distance; // pesquisa o raio em metros, retorna em quilometros
    String bearing;

    //public List<String> getCapabilitiesAsList() {
     //   return Arrays.asList(capabilities);
    //}


}

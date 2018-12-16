package com.example.InterCity.service;


import com.example.InterCity.component.AppGson;
import com.example.InterCity.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import com.mashape.unirest.http.Unirest;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

@Service
public class MedService {

    String baseUrl = "http://cidadesinteligentes.lsdi.ufma.br/";

    Gson gson = AppGson.get();
    Gson getGson = new Gson();

    public Capability buscarMedicamentos(String name) {
        try {
            val url = baseUrl + "catalog/capabilities/{name}";
            val json = Unirest.get(url)
                    .header("accept", "application/json")
                    .asJson().getBody().toString();
            val capability = gson.fromJson(json, Capability.class);
            return capability;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Capability> buscarTodosMedicamentos(){
        try{
            val url = baseUrl + "catalog/capabilities";
            val json = Unirest.get(url)
                    .header("accept","application/json")
                    .asJson().getBody().toString();
            val _capabilities = gson.fromJson(json, Capabilities.class);
            val capabilities = _capabilities.getCapabilities();
            return capabilities;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public  void getDataResource(Data data,String uuid, String capability){

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Data> request = new HttpEntity<>(data);

        ResponseEntity<Data> response = restTemplate
                .exchange(baseUrl+ "adaptor/resources/"+uuid + "/data/" + capability,
                        HttpMethod.POST, request, Data.class);

        if(response.getStatusCode().equals(HttpStatus.OK)){
            data = response.getBody();
        }
    }



    public List<Resource> buscarFarmaciaPeloMedicamento(String Medicamento){
        try {
            val url = baseUrl + "catalog/resources/search?capability=" + Medicamento.toUpperCase().trim();
            val json = Unirest.get(url)
                    .header("accept", "application/json")
                    .asJson().getBody().toString();
            val _resources = gson.fromJson(json, Resources.class);
            val recursos = _resources.getResources();
            return recursos;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Resource> buscarFarmaciaPeloMedicamentoEPosicao(Map<String, String> params){
        try {
            val lst = new ArrayList<String>();
            for(val entry : params.entrySet()){
                lst.add(entry.getKey() + "=" + entry.getValue());
            }

            val p = String.join("&", lst);
            val url = baseUrl + "catalog/resources/search?" + p;

            val json = Unirest.get(url)
                    .header("accept","application/json")
                    .asJson().getBody().toString();
            val _resources = gson.fromJson(json, Resources.class);
            val resources = _resources.getResources();

            return resources;

        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<Resource> searchResources(Resource request) {

        try {

            val lst = new ArrayList<String>();

            if (request.getCapabilities() != null) lst.add("capability="+request.getCapabilities());
            if (request.getLat() != null) lst.add("lat="+request.getLat());
            if (request.getLon() != null) lst.add("lon="+request.getLon());
            if (request.getDistance() != null) lst.add("radius="+request.getDistance());
            if (request.getUuid() != null) lst.add("uuid="+request.getUuid());
            if (request.getDescription() != null) lst.add("description="+request.getDescription());
            if (request.getStatus() != null) lst.add("status="+request.getStatus());
            if (request.getCountry() != null) lst.add("country="+request.getCountry());
            if (request.getState() != null) lst.add("state="+request.getState());
            if (request.getCity() != null) lst.add("city="+request.getCity());
            if (request.getNeighborhood() != null) lst.add("neightborhood="+request.getNeighborhood());
            if (request.getPostalCode() != null) lst.add("postal_code="+request.getPostalCode());

            val queryString = String.join("&", lst);

            val url = baseUrl + "catalog/resources/search?" + queryString;

            val list = new ArrayList<Resource>();

            val response = Unirest.get(url).header("accept", "application/json")
                        //.header("content-type", "application/json")
                        .asJson().getBody().toString();
                val resources = gson.fromJson(response, Resources.class);

                list.addAll(resources.getResources());

            return list;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}


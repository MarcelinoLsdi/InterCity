package com.example.InterCity.component;

import com.example.InterCity.gson.DateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.time.LocalDateTime;

public abstract class AppGson {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new DateSerializer())
            .create();

    public static Gson get() {
        return gson;
    }
}

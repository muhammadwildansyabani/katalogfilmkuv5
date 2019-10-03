package com.muhammadwildansyabani.katalogfilmkuv5.main.movie.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Genre {
    private String name;

    public Genre(JSONObject genreObject){
        try {
            int id = genreObject.getInt("id");
            this.name = genreObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }
}

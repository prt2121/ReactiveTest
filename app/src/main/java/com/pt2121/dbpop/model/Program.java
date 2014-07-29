package com.pt2121.dbpop.model;

import android.util.Pair;

/**
 * Created by pt2121 on 7/27/14.
 * <p/>
 * A data class representing a bike share program or city.
 */
public class Program {
    // use final to get rid of setter and getter.
    /**
     * Our auto generated id from database.
     */
    public final int id;
    public final String name;
    /**
     * Latitude of the center of the program.
     */
    public final float lat;
    /**
     * Longitude of the center of the program.
     */
    public final float lng;
    /**
     * The id from provider such as b-cycle.
     */
    public final long externalId;
    /**
     * API url.
     */
    public final String url;
    /**
     * API key used in RequestProperty.
     */
    public final Pair<String, String> apiKey;

    public Program(int id, String name, float lat, float lng, long externalId, String url, Pair<String, String> apiKey) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.externalId = externalId;
        this.url = url;
        this.apiKey = apiKey;
    }
}

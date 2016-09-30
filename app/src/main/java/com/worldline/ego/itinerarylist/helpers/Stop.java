package com.worldline.ego.itinerarylist.helpers;

/**
 * Created by a143210 on 29/09/2016.
 */

public class Stop {
    private final int id;
    private final String name;
    private final float latitude;
    private final float longitude;

    public Stop(int id, String name, float latitude, float longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return name;
    }
}

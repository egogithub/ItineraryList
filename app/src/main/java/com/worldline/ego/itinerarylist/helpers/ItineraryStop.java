package com.worldline.ego.itinerarylist.helpers;

/**
 * Created by a143210 on 29/09/2016.
 */

public class ItineraryStop extends Stop {
    private final boolean present;

    public ItineraryStop(int id, String name, float latitude, float longitude, Boolean present) {
        super(id, name, latitude, longitude);
        this.present = present;
    }

    public Boolean isPresent() {
        return present;
    }
}

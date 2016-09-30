package com.worldline.ego.itinerarylist.interfaces;

import com.worldline.ego.itinerarylist.helpers.ItineraryStop;

import java.util.List;

/**
 * Created by a143210 on 29/09/2016.
 */

public interface ItineraryUpdateListener {
    public void onItineraryUpdate(List<ItineraryStop> stopslist);
}

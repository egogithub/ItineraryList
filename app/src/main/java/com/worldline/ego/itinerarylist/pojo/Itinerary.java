package com.worldline.ego.itinerarylist.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by a143210 on 28/09/2016.
 * list for itinerary consisting of <image, Stop name (String)>
 */


public class Itinerary {

    @SerializedName("stop_picture")
    public String stopPict;

    @SerializedName("stop_name")
    public String stopName;
}

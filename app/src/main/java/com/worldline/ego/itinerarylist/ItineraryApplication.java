package com.worldline.ego.itinerarylist;

import android.app.Application;
import android.content.Context;

/**
 * Created by a143210 on 11/10/2016.
 */

public class ItineraryApplication extends Application {
    private static Context sContext;

    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext(){
        return sContext;
    }
}

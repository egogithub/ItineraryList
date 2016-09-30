package com.worldline.ego.itinerarylist.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.worldline.ego.itinerarylist.async.GetItineraryAsyncTask;
import com.worldline.ego.itinerarylist.helpers.ItineraryStop;
import com.worldline.ego.itinerarylist.interfaces.ItineraryUpdateListener;

import java.util.List;

/**
 * Created by a143210 on 30/09/2016.
 */

public class ItineraryService extends Service implements ItineraryUpdateListener {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String lineNumber = intent.getStringExtra("lineNumber");
        String direction = intent.getStringExtra("direction");
        new GetItineraryAsyncTask(this).execute(lineNumber, direction);
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onItineraryUpdate(List<ItineraryStop> stopslist) {

    }
}

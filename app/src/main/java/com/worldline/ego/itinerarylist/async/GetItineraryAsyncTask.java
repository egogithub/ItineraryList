package com.worldline.ego.itinerarylist.async;

import android.os.AsyncTask;
import android.util.Log;

import com.worldline.ego.itinerarylist.helpers.ItineraryHelper;
import com.worldline.ego.itinerarylist.helpers.ItineraryStop;
import com.worldline.ego.itinerarylist.interfaces.ItineraryUpdateListener;

import java.util.List;

/**
 * Created by a143210 on 28/09/2016.
 */

public class GetItineraryAsyncTask extends AsyncTask<String, Void, List<ItineraryStop>>{

    private ItineraryUpdateListener mListener;

    public GetItineraryAsyncTask(ItineraryUpdateListener listener) {
        this.mListener = listener;
    }

    @Override
    protected List<ItineraryStop> doInBackground(String... params) {
        if ((null != params) && (params.length > 0)) {
            return ItineraryHelper.getItinerary(params[0], params[1]);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<ItineraryStop> result) {
        Log.d("GetItineraryAsyncTask ", "Finished");
        if (null != mListener) {
            mListener.onItineraryUpdate(result);
        }
    }
}

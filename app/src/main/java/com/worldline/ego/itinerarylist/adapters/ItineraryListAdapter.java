package com.worldline.ego.itinerarylist.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.worldline.ego.itinerarylist.R;
import com.worldline.ego.itinerarylist.helpers.ItineraryStop;
import com.worldline.ego.itinerarylist.pojo.Itinerary;

import java.util.List;

/**
 * Created by a143210 on 29/09/2016.
 */

public class ItineraryListAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<ItineraryStop> mStopList;
    private TextView stopPict;
    private TextView stopName;

    public ItineraryListAdapter (final Activity activity, List<ItineraryStop> stopsList) {
        super();
        this.mStopList=stopsList;
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
        if (null != this.mStopList) {
            return this.mStopList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != this.mStopList) {
            return this.mStopList.get(position);
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        Log.d("ItineraryAdapter", "Updating view");
        LayoutInflater inflater = mActivity.getLayoutInflater();

        if (convertView==null) {
            convertView=inflater.inflate(R.layout.column_row, null);

            stopPict = (TextView)convertView.findViewById(R.id.stopPict);
            stopName = (TextView)convertView.findViewById(R.id.stopName);
        }

        ItineraryStop stop = mStopList.get(position);
        if (stop.isPresent()) {
            stopPict.setText("(X)");
        } else {
            stopPict.setText("( )");
        }
        stopName.setText(stop.getName());

        return convertView;
    }

    public List<ItineraryStop> getData() {
        return this.mStopList;
    }

    public Activity getActivity() {
        return this.mActivity;
    }
}

package com.worldline.ego.itinerarylist.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.worldline.ego.itinerarylist.ItineraryApplication;
import com.worldline.ego.itinerarylist.R;
import com.worldline.ego.itinerarylist.helpers.ItineraryStop;
import com.worldline.ego.itinerarylist.pojo.Itinerary;

import java.util.List;

/**
 * Created by a143210 on 29/09/2016.
 */

public class ItineraryListAdapter extends BaseAdapter {
    private List<ItineraryStop> mStopList;
    private LayoutInflater mInflater;
    private Context context;

    public ItineraryListAdapter (Context context, List<ItineraryStop> stopsList) {
        mStopList=stopsList;
        this.context=context;
        mInflater = LayoutInflater.from(this.context);
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView stopPict;
        TextView stopName;

        if (convertView==null) {
            convertView=mInflater.inflate(R.layout.column_row, null);
        }

        stopPict = (TextView)convertView.findViewById(R.id.stopPict);
        stopName = (TextView)convertView.findViewById(R.id.stopName);

        ItineraryStop stop = mStopList.get(position);
        if (stop.isPresent()) {
            stopPict.setText("(X)");
        } else {
            stopPict.setText("( )");
        }
        stopName.setText(stop.getName());

        return convertView;
    }

    public void updateResults(List<ItineraryStop> stopsList) {
        this.mStopList = stopsList;
        notifyDataSetChanged();
    }
    public List<ItineraryStop> getData() {
        return this.mStopList;
    }
}

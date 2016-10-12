package com.worldline.ego.itinerarylist;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.worldline.ego.itinerarylist.adapters.ItineraryListAdapter;
import com.worldline.ego.itinerarylist.async.GetItineraryAsyncTask;
import com.worldline.ego.itinerarylist.helpers.ItineraryStop;
import com.worldline.ego.itinerarylist.interfaces.ItineraryUpdateListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItineraryUpdateListener {
    final String lineNumber = "4";
    final String direction = "1"; // 1 or 2
    private ListView mainListView;
    private ItineraryListAdapter listAdapter;
    public static final long REFRESH_TIME = 10 * 1000; // 10 seconds
    Context context = MainActivity.this;
    private Handler taskHandler;
    ItineraryUpdateListener itineraryListener = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ItineraryStop> stopslist = new ArrayList();
        mainListView = (ListView) findViewById(R.id.list);
        listAdapter = new ItineraryListAdapter(context, stopslist);
        mainListView.setAdapter(listAdapter);
        ImageButton refreshButton = (ImageButton)findViewById(R.id.imageButton);
        taskHandler = new Handler();
        refreshButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onRefreshClick(view);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskHandler.postDelayed(refreshTask, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        taskHandler.removeCallbacks(refreshTask);
    }

    public Runnable refreshTask = new Runnable() {
        @Override
        public void run() {
            new GetItineraryAsyncTask(itineraryListener).execute(lineNumber, direction);
            taskHandler.postDelayed(this, REFRESH_TIME);
        }
    };

    public void onRefreshClick(View view) {
        Log.d("MainActivity", "Refreshing list");
        new GetItineraryAsyncTask(this).execute(lineNumber, direction);
    }

    public void onItineraryUpdate(List<ItineraryStop> stopslist) {
        Log.d("MainActivity", "List contains " + stopslist.size() + " entries");
        dumpList(stopslist);
        listAdapter.updateResults(stopslist);
    }

    private void dumpList(List<ItineraryStop> stops) {
        for (ItineraryStop stop : stops) {
            Log.d(stop.getName(), "is "+stop.isPresent());
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
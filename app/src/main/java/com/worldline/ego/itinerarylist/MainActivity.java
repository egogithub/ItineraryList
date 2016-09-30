package com.worldline.ego.itinerarylist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.worldline.ego.itinerarylist.adapters.ItineraryListAdapter;
import com.worldline.ego.itinerarylist.async.GetItineraryAsyncTask;
import com.worldline.ego.itinerarylist.helpers.ItineraryStop;
import com.worldline.ego.itinerarylist.interfaces.ItineraryUpdateListener;
import com.worldline.ego.itinerarylist.services.ItineraryService;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItineraryUpdateListener {
    final String lineNumber = "4";
    final String direction = "1"; // 1 or 2
    public static final String ACTION_NEW_ITI = "newItiAction";
    private ListView mainListView;
    private ItineraryListAdapter listAdapter;
    private NewItiReceiver itiUpdateReceiver;
    private PendingIntent mServicePendingintent;
    public static final long REFRESH_TIME = 10 * 1000; // 10 seconds
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        //Here get and populate the list
        new GetItineraryAsyncTask(this).execute(lineNumber, direction);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set Repeating interval to refresh the itinerary

        // 1. register receiver
        itiUpdateReceiver = new NewItiReceiver();
        registerReceiver(itiUpdateReceiver, new IntentFilter(ACTION_NEW_ITI));

        // 2. Schedule service to run every REFRESH_TIME milliseconds using alarmManger of Android
        final Calendar cal = Calendar.getInstance(); // used to get the current time in milliseconds
        // Create intent the alarmManager will broadcast when the time expires
        final Intent serviceIntent = new Intent(this, ItineraryService.class);
        serviceIntent.putExtra("lineNumber", lineNumber);
        serviceIntent.putExtra("direction", direction);
        mServicePendingintent = PendingIntent.getService(this, 0, serviceIntent, 0); // Pneding intent to give alarmService permission to send this intent (this)
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), REFRESH_TIME, mServicePendingintent);
    }

    @Override
    protected void onPause() {
        // unregister receiver
        unregisterReceiver(itiUpdateReceiver);
        itiUpdateReceiver = null;

        // Cancel alarmService repeat
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(mServicePendingintent);
    }

    public void onItineraryUpdate(List<ItineraryStop> stopslist) {
        Log.d("MainActivity", "List contains " + stopslist.size() + " entries");
        mainListView = (ListView) findViewById(R.id.list);
        listAdapter = new ItineraryListAdapter(this, stopslist);
        mainListView.setAdapter(listAdapter);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class NewItiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
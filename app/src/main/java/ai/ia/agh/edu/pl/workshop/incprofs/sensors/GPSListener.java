package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.aware.providers.Locations_Provider;

/**
 * Cogito ergo sum
 * Created by Rael on 04.02.2016.
 */
public class GPSListener extends BroadcastReceiver {

    public void onReceive(Context c, Intent intent) {
        // Be sure to keep the work short inside onReceive(). Broadcasts need to return under 15 seconds, otherwise Android will interrupt it with ANR (Android Not Responding) messages.
        double latitude = 0.0;
        double longitude = 0.0;
        double timestamp = 0.0;

        Cursor gps = c.getContentResolver().query(Locations_Provider.Locations_Data.CONTENT_URI, null, null, null,
                Locations_Provider.Locations_Data.TIMESTAMP + " DESC LIMIT 1");
        if (gps != null && gps.moveToFirst()) {
            latitude = gps.getDouble(gps.getColumnIndex("double_latitude"));
            longitude = gps.getDouble(gps.getColumnIndex("double_longitude"));
            timestamp = gps.getDouble(gps.getColumnIndex("timestamp"));
            Log.d("AWARE", "CHANGES IN GPS DB: (latitude)" + gps.getString(gps.getColumnIndex("double_latitude")) + " longtitude: " + gps.getString(gps.getColumnIndex("double_longitude")));
        }

        assert gps != null;
        gps.close();
        // extras jest nullem
        //double latitude =  intent.getDoubleExtra("double_latitude", 180.0);; // // TODO: 04.02.2016  nie wiadomo czy tak się to nazywa
        Log.d("SENSORS", "Latitude = !" + latitude + ", longitude: " + longitude + ", time: " + timestamp);
    }
}


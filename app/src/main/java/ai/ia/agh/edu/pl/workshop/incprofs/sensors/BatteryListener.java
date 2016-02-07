package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.aware.providers.Battery_Provider;

/**
 * Created by Rael on 04.02.2016.
 */


public class BatteryListener extends BroadcastReceiver {
    public void onReceive(Context c, Intent intent) {
        // Be sure to keep the work short inside onReceive(). Broadcasts need to return under 15 seconds, otherwise Android will interrupt it with ANR (Android Not Responding) messages.
        Uri sensorURI = Battery_Provider.Battery_Data.CONTENT_URI;

        //todo: to powinien być argument tej funkcji!
        Cursor battery = c.getContentResolver().query(Battery_Provider.Battery_Data.CONTENT_URI, null, null, null,
                Battery_Provider.Battery_Data.TIMESTAMP + " DESC LIMIT 1");

        // Cursor battery  = c.getContentResolver().query(Battery_Provider.Battery_Data.CONTENT_URI, null, null, null, "_id ASC");


        if (battery != null && battery.moveToFirst()) {
            String timestamp = battery.getString(battery.getColumnIndex("timestamp"));
            // int level =  battery.getInt(battery.getColumnIndex("battery_level")); // // TODO: 04.02.2016  nie wiadomo czy tak się to nazywa
            //Caused by: android.database.CursorIndexOutOfBoundsException: Index -1 requested, with a size of 1
            Log.d("SENSORS", "Yay the battery did sth, level = !" + timestamp);
        }


    }
}
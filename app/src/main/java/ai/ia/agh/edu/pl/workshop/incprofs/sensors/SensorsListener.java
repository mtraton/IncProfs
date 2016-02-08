package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.aware.providers.Applications_Provider;
import com.aware.providers.Battery_Provider;
import com.aware.providers.Locations_Provider;
import com.aware.providers.WiFi_Provider;

import java.util.HashMap;

/**
 * Cogito ergo sum
 * Created by Rael on 07.02.2016.
 */
public class SensorsListener extends BroadcastReceiver {


    HashMap<String, Object> sensorDataInstance = new HashMap<>(); // przechowujemy pojedynczą instację danych z sensorów

    //Cursors todo: wrzucić w jakąś strukturę danych
    //todo : zrobić domyślne dane jak dla danego sensoru nie będziemy mieli danych na początku?
    // todo: musimy pamiętać dwie próbki wstecz! DESC 2?
    // todo: dodać wykrywanie z których sensorów możemy skorzystać

    // todo : porównanie timestampów żeby określić, od kogo dostaliśmy najnowsze dane?

    Cursor activityCursor;
    String activity_name; // unknown, tilting, on_foot, in_vehicle, on_bicycle, running, walking

    Cursor appCursor;
    String application_name;
    String package_name;

    Cursor batteryCursor;
    int battery_level = 0;
    int battery_scale = 100;

    Cursor gpsCursor;
    double double_latitude = 0.0; // wartość w stopniach
    double double_longitude = 0.0;

    Cursor wifiCursor;
    String ssid;

    double timestamp = 0.0; // wspólny dla wszystkich kursorów


    // pomysł - wszystko w jednym receiverze

    public void startCursors(Context c) {
        appCursor = c.getContentResolver().query(Applications_Provider.Applications_Foreground.CONTENT_URI, null, null, null,
                Applications_Provider.Applications_Foreground.TIMESTAMP + " DESC LIMIT 1");

        batteryCursor = c.getContentResolver().query(Battery_Provider.Battery_Data.CONTENT_URI, null, null, null,
                Battery_Provider.Battery_Data.TIMESTAMP + " DESC LIMIT 1");

        gpsCursor = c.getContentResolver().query(Locations_Provider.Locations_Data.CONTENT_URI, null, null, null,
                Locations_Provider.Locations_Data.TIMESTAMP + " DESC LIMIT 1");

        wifiCursor = c.getContentResolver().query(WiFi_Provider.WiFi_Sensor.CONTENT_URI, null, null, null,
                WiFi_Provider.WiFi_Sensor.TIMESTAMP + " DESC LIMIT 1");

    }

    public void getAppData() {
        if (appCursor != null && appCursor.moveToFirst()) {
            application_name = appCursor.getString(appCursor.getColumnIndex("application_name"));
            package_name = appCursor.getString(appCursor.getColumnIndex("package_name"));
            timestamp = appCursor.getLong(appCursor.getColumnIndex("timestamp"));
            Log.d("sensors", "Applications: " + package_name + " :  " + application_name + " ( " + timestamp + " ) ");
        }

    }

    public void getBatteryData() {
        if (batteryCursor != null && batteryCursor.moveToFirst()) {
            timestamp = batteryCursor.getDouble(batteryCursor.getColumnIndex("timestamp"));
            battery_level = batteryCursor.getInt(batteryCursor.getColumnIndex("battery_level")); // // TODO: 04.02.2016  nie wiadomo czy tak się to nazywa
            battery_scale = batteryCursor.getInt(batteryCursor.getColumnIndex("battery_scale"));
            //Caused by: android.database.CursorIndexOutOfBoundsException: Index -1 requested, with a size of 1
            Log.d("sensors", "Battery: " + battery_level + " /  " + battery_scale + " ( " + timestamp + " ) ");
        }

    }

    public void getGPSData() {
        if (gpsCursor != null && gpsCursor.moveToFirst()) {
            double_latitude = gpsCursor.getDouble(gpsCursor.getColumnIndex("double_latitude"));
            double_longitude = gpsCursor.getDouble(gpsCursor.getColumnIndex("double_longitude"));
            timestamp = gpsCursor.getDouble(gpsCursor.getColumnIndex("timestamp"));
            Log.d("sensors", "GPS: latitude: " + double_latitude + ", longitude:  " + double_longitude + " ( " + timestamp + " ) ");
        }

    }

    public void getWiFiData() {
        if (wifiCursor != null && wifiCursor.moveToFirst()) {
            ssid = wifiCursor.getString(wifiCursor.getColumnIndex("ssid"));
            timestamp = wifiCursor.getDouble(wifiCursor.getColumnIndex("timestamp"));
            Log.d("sensors", "WiFi: " + ssid + " ( " + timestamp + " ) ");
        }

    }

    public void stopCursors() {
        appCursor.close();
        batteryCursor.close();
        gpsCursor.close();
        wifiCursor.close();
    }


    public void onReceive(Context c, Intent intent) {
        // Be sure to keep the work short inside onReceive(). Broadcasts need to return under 15 seconds, otherwise Android will interrupt it with ANR (Android Not Responding) messages.

        Log.d("test", "Sensors Listener onReceive");
        startCursors(c);

        getAppData();
        getBatteryData();
        getGPSData();
        getWiFiData();

        saveDataToHashMap();
        stopCursors();
    }

    private void saveDataToHashMap() {


    }
}



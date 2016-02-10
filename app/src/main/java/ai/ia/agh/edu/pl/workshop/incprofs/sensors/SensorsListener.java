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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import ai.ia.agh.edu.pl.workshop.incprofs.learning.DataToInstances;
import ai.ia.agh.edu.pl.workshop.incprofs.learning.SaveInstanceToFile;
import weka.core.Instances;

/**
 * Cogito ergo sum
 * Created by Rael on 07.02.2016.
 */
public class SensorsListener extends BroadcastReceiver {



    LinkedHashMap<String, Object> sensorDataInstance = new LinkedHashMap<>(); //zapewnia kolejność "wsadzania" elementów

    Profiles profiles;


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
    double battery_level = 0;
    double battery_scale = 100;

    Cursor gpsCursor;
    double double_latitude = 0.0; // wartość w stopniach
    double double_longitude = 0.0;

    Cursor wifiCursor;
    String ssid;

    double timestamp = 0;
    double cursor_timestamp = 0; // wspólny dla wszystkich kursorów

    String profile = "blank"; // todo: dodać automatyczne labelowanie

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
            cursor_timestamp = appCursor.getDouble(appCursor.getColumnIndex("timestamp"));

            if (cursor_timestamp > timestamp) {
                timestamp = cursor_timestamp;
            }

            Log.d("sensors", "Applications: " + package_name + " :  " + application_name + " ( " + cursor_timestamp + " ) ");
        }

    }

    public void getBatteryData() {
        if (batteryCursor != null && batteryCursor.moveToFirst()) {
            battery_level = batteryCursor.getDouble(batteryCursor.getColumnIndex("battery_level")); // // TODO: 04.02.2016  nie wiadomo czy tak się to nazywa
            battery_scale = batteryCursor.getDouble(batteryCursor.getColumnIndex("battery_scale"));
            cursor_timestamp = batteryCursor.getDouble(batteryCursor.getColumnIndex("timestamp"));

            if (cursor_timestamp > timestamp) {
                timestamp = cursor_timestamp;
            }

            //Caused by: android.database.CursorIndexOutOfBoundsException: Index -1 requested, with a size of 1
            Log.d("sensors", "Battery: " + battery_level + " / " + battery_scale + " ( " + cursor_timestamp + " ) ");
        }

    }

    public void getGPSData() {
        if (gpsCursor != null && gpsCursor.moveToFirst()) {
            double_latitude = gpsCursor.getDouble(gpsCursor.getColumnIndex("double_latitude"));
            double_longitude = gpsCursor.getDouble(gpsCursor.getColumnIndex("double_longitude"));
            cursor_timestamp = gpsCursor.getDouble(gpsCursor.getColumnIndex("timestamp"));

            if (cursor_timestamp > timestamp) {
                timestamp = cursor_timestamp;
            }

            Log.d("sensors", "GPS: latitude: " + double_latitude + ", longitude:  " + double_longitude + " ( " + cursor_timestamp + " ) ");
        }

    }

    public void getWiFiData() {
        if (wifiCursor != null && wifiCursor.moveToFirst()) {
            ssid = wifiCursor.getString(wifiCursor.getColumnIndex("ssid"));
            cursor_timestamp = wifiCursor.getDouble(wifiCursor.getColumnIndex("timestamp"));

            if (cursor_timestamp > timestamp) {
                timestamp = cursor_timestamp;
            }

            Log.d("sensors", "WiFi: " + ssid + " ( " + cursor_timestamp + " ) ");
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

        Log.d("sensors", "Sensors Listener onReceive, intent: " + intent);


        startCursors(c);

        timestamp = 0;

        getAppData();
        getBatteryData(); // todo: bateria generuje za dużo eventów
        getGPSData();
        getWiFiData();

        //very pessimistic version of Broadcast with no DB data...
        if (timestamp == 0) {
            Log.d("sensors","timestamp was 0! is everything correct in codes? timestamp:"+timestamp + " | cursor_ts:"+cursor_timestamp);
            timestamp = (double)System.currentTimeMillis();
        }

        saveDataToHashMap();
        Instances instances = new DataToInstances(sensorDataInstance).sensorDataToInstance();

        SaveInstanceToFile saveInstanceToFile = new SaveInstanceToFile(c);
        try {
            saveInstanceToFile.writeInstancesData(instances); // todo: uprościć
        } catch (IOException e) {
            e.printStackTrace();
        }

        stopCursors();
    }

    private void saveDataToHashMap() {

        //todo: zabezpieczyć się przed pustymi danymi

        sensorDataInstance.put("application_name", application_name);
        sensorDataInstance.put("package_name", package_name);
        sensorDataInstance.put("battery_level", battery_level);
        sensorDataInstance.put("battery_scale", battery_scale); //todo: usunąć
        sensorDataInstance.put("double_latitude", double_latitude);
        sensorDataInstance.put("double_longitude", double_longitude);
        sensorDataInstance.put("ssid", ssid);
        sensorDataInstance.put("timestamp", new DataUtil().timeStampToDate(timestamp));
        sensorDataInstance.put("profile", chooseLabel(timestamp));
    }





    public String chooseLabel(double timestamp)
    {
        Date date = new DataUtil().timeStampToDate(timestamp);
        //Calendar c = Calendar.getInstance();
        //c.setTime(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // the format of your date
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);
        int hour = date.getHours();//c.get(Calendar.HOUR_OF_DAY);

        LinkedHashMap<Profiles.Day, String> profilesMap = new Profiles().getProfilesMap();
        if (hour >= 6 && hour < 8)
        {
            return profilesMap.get(Profiles.Day.MORNING);
        }
        else if(hour >= 8 && hour <= 16) // at work
        {
            return profilesMap.get(Profiles.Day.WORK);
        }
        else if(hour > 16 &&  hour <= 22)
        {
            return profilesMap.get(Profiles.Day.AFTERWORK);
        }
        else
        {
            return profilesMap.get(Profiles.Day.NIGHT);
        }


    }
}



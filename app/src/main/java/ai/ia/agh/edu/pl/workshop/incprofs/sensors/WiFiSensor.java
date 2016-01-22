package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

/**
 * Created by Rael on 21.01.2016.
 */
public class WiFiSensor {
    /*
        //Activate WIFI
		Log.d("sensors", "Activate WIFI");
		Aware.setSetting(this, Aware_Preferences.STATUS_WIFI, true);
		//Set GPS freq
		//Log.d("sensors", "Set WIFI sampling frequency");
		//Aware.setSetting(this, Aware_Preferences.FREQUENCY_WIFI, 60);
		//Apply settings
		Log.d("sensors", "Apply WIFI settings");
		Aware.startSensor(this, Aware_Preferences.STATUS_WIFI);
*/
// Cursor WIFI = getContentResolver().query(WiFi_Provider.WiFi_Sensor.CONTENT_URI, null, null, null, "_id ASC");


        /*
        i=0;
        while (WIFI.moveToNext()) {
            i++;
            Log.d("sensors", "iterator: " + i);

            String tmp;
            StringBuilder debug = new StringBuilder();


            tmp = WIFI.getString(WIFI.getColumnIndex("_id"));
            debug.append(tmp);
            debug.append(" ");

            tmp = WIFI.getString(WIFI.getColumnIndex("timestamp"));
            debug.append(tmp);
            debug.append(" ");

            tmp = WIFI.getString(WIFI.getColumnIndex("device_id"));
            debug.append(tmp);
            debug.append(" ");

            tmp = WIFI.getString(WIFI.getColumnIndex("mac_address"));
            debug.append(tmp);
            debug.append(" ");

            tmp = WIFI.getString(WIFI.getColumnIndex("bssid"));
            debug.append(tmp);
            debug.append(" ");

            tmp = WIFI.getString(WIFI.getColumnIndex("ssid"));
            debug.append(tmp);
            debug.append(" ");


            Log.d("sensors", "txt: " + debug.toString());


            Date date = new Date();
            date.setTime((long) WIFI.getLong(WIFI.getColumnIndex("timestamp")) );
            Log.d("sensors", "timestamp: " + date.toString());
        }
        */

}

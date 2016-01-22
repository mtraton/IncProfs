package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

/**
 * Created by Rael on 21.01.2016.
 */
public class GPSSensor {

    //
        /*
        //Activate GPS
        Log.d("sensors", "Activate GPS");
        Aware.setSetting(this, Aware_Preferences.STATUS_LOCATION_GPS, true);st
        //Set GPS freq
        Log.d("sensors", "Set GPS sampling frequency");
        Aware.setSetting(this, Aware_Preferences.FREQUENCY_LOCATION_GPS, 60);

        //Aware.setSetting(this, Aware_Preferences.MIN_LOCATION_GPS_ACCURACY, 5);

        //Aware.setSetting(this, Aware_Preferences.LOCATION_EXPIRATION_TIME, 10);

        //Apply settings
        Log.d("sensors", "Apply GPS settings");
        Aware.startSensor(this, Aware_Preferences.STATUS_LOCATION_GPS);
        */

/*
        //Activate GPS2
		Log.d("sensors", "Activate GPSNet");
		Aware.setSetting(this, Aware_Preferences.STATUS_LOCATION_NETWORK, true);
		//Set GPS freq
		Log.d("sensors", "Set GPSNet sampling frequency");
		Aware.setSetting(this, Aware_Preferences.FREQUENCY_LOCATION_NETWORK, 10);
		//Apply settings
		Log.d("sensors", "Apply GPSNet settings");
		Aware.startSensor(this, Aware_Preferences.STATUS_LOCATION_NETWORK);
*/

  /*  this.gpso = new GPSObserver(new Handler(), getApplicationContext());
    getContentResolver().registerContentObserver(Locations_Provider.Locations_Data.CONTENT_URI, true, this.gpso);
    */

      /*
      onDestroy
      (getContentResolver().unregisterContentObserver(this.so);
        getContentResolver().unregisterContentObserver(this.gpso);
        */

    // Cursor GPS = getContentResolver().query(Locations_Provider.Locations_Data.CONTENT_URI, null, null, null, "_id ASC");

    /*
        i=0;
		while (GPS.moveToNext()) {
			i++;
			Log.d("sensors", "iterator: " + i);

			String tmp;
			StringBuilder debug = new StringBuilder();


			tmp = GPS.getString(GPS.getColumnIndex("_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("timestamp"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("device_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("double_latitude"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("double_longitude"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("double_bearing"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("double_speed"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("double_altitude"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("provider"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("accuracy"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("label"));
			debug.append(tmp);
			debug.append(" ");


			Log.d("sensors", "txt: " + debug.toString());

		}
*/


}

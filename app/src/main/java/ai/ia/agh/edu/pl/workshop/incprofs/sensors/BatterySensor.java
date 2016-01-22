package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.providers.Battery_Provider;

/**
 * Created by Rael on 21.01.2016.
 */
public class BatterySensor extends ContextWrapper {
    //// TODO: 21.01.2016  sprawdzić, czy ten sposób pobierania kontekstu jest poprawny


    // Pola


    protected Context applicationContext;

    private String sensorName = Aware_Preferences.STATUS_BATTERY;
    private Cursor sensorData;
    private SensorQuery sensorQuery;
    private Uri sensorURI = Battery_Provider.Battery_Data.CONTENT_URI;
    private String orderBy = "_id ASC";
    // Metody

    public BatterySensor(Context base) {
        //Context base super(base);
        // // TODO: 22.01.2016 co zrobić z tym kontekstem?
        //this.applicationContext = applicationContext.getApplicationContext();
        super(base);
        Log.d("sensors", "Activate Battery");
        Aware.setSetting(this, sensorName, true);


        //Apply settings
        Log.d("sensors", "Apply Battery settings");
        Aware.startSensor(this, sensorName);

    }

    public Cursor getDataCursor() {
        //// TODO: 21.01.2016  sprawdzić czy działa
//        Cursor  data = getContentResolver().query(Accelerometer_Data.CONTENT_URI, tableColumns, whereCondition, whereArguments, orderBy);

        sensorQuery = new SensorQuery(sensorURI, null, null, null, orderBy); //todo: to powinien być argument tej funkcji!
        sensorData = getContentResolver().query(sensorQuery.getSensorURI(), sensorQuery.getTableColumns(), sensorQuery.getWhereCondition(), sensorQuery.getWhereArguments(), sensorQuery.getOrderBy());
        return sensorData;
    }


    public void closeDataCursor() {
        sensorData.close();
    }

    public void close() {
        Log.d("sensors", "DE-Activate Battery ");
        Aware.setSetting(this, sensorName, false);
        //Apply settings
        Log.d("sensors", "Apply Battery settings");
        Aware.stopSensor(this, sensorName);
    }
}


//todo:
            /*
            1) pobrać longa
            Date date = new Date();
             date.setTime((long) WIFI.getLong(WIFI.getColumnIndex("timestamp")) );
             Log.d("sensors", "timestamp: " + date.toString());

             */
            /*
            long startDate = battery_dis.getLong(battery_dis.getColumnIndex("timestamp"));
			tmp = battery_dis.getString(battery_dis.getColumnIndex("timestamp"));
			debug.append(tmp);
			debug.append(" ");

			tmp = battery_dis.getString(battery_dis.getColumnIndex("device_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = battery_dis.getString(battery_dis.getColumnIndex("battery_start"));
			debug.append(tmp);
			debug.append(" ");

			tmp = battery_dis.getString(battery_dis.getColumnIndex("battery_end"));
			debug.append(tmp);
			debug.append(" ");

			tmp = battery_dis.getString(battery_dis.getColumnIndex("double_end_timestamp"));
			debug.append(tmp);
			debug.append(" ");

			Log.d("sensors", "txt: " + debug.toString());

			long test = battery_dis.getLong(battery_dis.getColumnIndex("double_end_timestamp")) - battery_dis.getLong(battery_dis.getColumnIndex("timestamp"));

			Log.d("sensors", "timestamp diff: " + test);

			//battery_dis.getLong();
		}
        */

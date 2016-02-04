package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

import com.aware.providers.Locations_Provider;

/**
 * Created by Gecko_V on 2016-01-15.
 */
public class GPSObserver extends ContentObserver {

    private Context context;

    public GPSObserver(Handler handler, Context context) {
        super(handler);

        this.context = context;

        Log.d("AWARE", "GPSObserver constructed!");
    }


    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        // Get the latest recorded value
        Cursor gps = context.getContentResolver().query(Locations_Provider.Locations_Data.CONTENT_URI, null, null, null,
                Locations_Provider.Locations_Data.TIMESTAMP + " DESC LIMIT 1");
        if (gps != null && gps.moveToFirst()) {
            // Here we read the value

            Log.d("AWARE", "CHANGES IN GPS DB: (latitude)" + gps.getString(gps.getColumnIndex("double_latitude")) + " longtitude: " + gps.getString(gps.getColumnIndex("double_longitude")));
        }

        assert gps != null;
        gps.close();
    }


}



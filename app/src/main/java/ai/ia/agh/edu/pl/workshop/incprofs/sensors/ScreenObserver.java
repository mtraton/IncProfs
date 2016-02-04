package ai.ia.agh.edu.pl.workshop.incprofs.sensors;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

import com.aware.providers.Screen_Provider;

/**
 * Created by Gecko_V on 2016-01-15.
 */
public class ScreenObserver extends ContentObserver {

    private Context context;

    public ScreenObserver(Handler handler, Context context) {
        super(handler);

        this.context = context;
    }


    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        // Get the latest recorded value
        Cursor screen = context.getContentResolver().query(Screen_Provider.Screen_Data.CONTENT_URI, null, null, null,
                Screen_Provider.Screen_Data.TIMESTAMP + " DESC LIMIT 1");
        if (screen != null && screen.moveToFirst()) {
            // Here we read the value
            int screen_status = screen.getInt(screen.getColumnIndex(Screen_Provider.Screen_Data.SCREEN_STATUS));
            Log.d("AWARE", "CHANGES IN SCREEN DB: " + screen_status);
        }


        assert screen != null;
        screen.close();
    }


}

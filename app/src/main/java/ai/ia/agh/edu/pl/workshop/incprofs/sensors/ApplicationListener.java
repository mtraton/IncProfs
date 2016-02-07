package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.aware.providers.Applications_Provider;

/**
 * Cogito ergo sum
 * Created by Rael on 07.02.2016.
 */
public class ApplicationListener extends BroadcastReceiver {


    // pomysł - wszystko w jednym receiverze

    public void onReceive(Context c, Intent intent) {
        // Be sure to keep the work short inside onReceive(). Broadcasts need to return under 15 seconds, otherwise Android will interrupt it with ANR (Android Not Responding) messages.
        String appName;
        String packageName;
        long timestamp;

        Cursor app = c.getContentResolver().query(Applications_Provider.Applications_Foreground.CONTENT_URI, null, null, null,
                Applications_Provider.Applications_Foreground.TIMESTAMP + " DESC LIMIT 1");
        if (app != null && app.moveToFirst()) {
            appName = app.getString(app.getColumnIndex("application_name"));
            packageName = app.getString(app.getColumnIndex("package_name"));
            timestamp = app.getLong(app.getColumnIndex("timestamp"));
            Log.d("sensors", "CHANGES IN APP DB: (appname)" + appName + ", package : " + packageName + ", time: " + timestamp);
        }

        assert app != null;
        app.close();
        // extras jest nullem
        //double latitude =  intent.getDoubleExtra("double_latitude", 180.0);; // // TODO: 04.02.2016  nie wiadomo czy tak się to nazywa
    }
}

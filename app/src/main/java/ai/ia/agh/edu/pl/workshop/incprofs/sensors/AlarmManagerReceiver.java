package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Cogito ergo sum
 * Created by Rael on 08.02.2016.
 */
public class AlarmManagerReceiver extends BroadcastReceiver {

    public void onReceive(Context c, Intent intent) {


        Log.d("EveryXTime+Receiver", "onReceive, countdown complete: 15 min passed!");

        //todo: ucz się z pliku
        //Toast.makeText(c, "onReceive, countdown complete: 15 min passed!", Toast.LENGTH_LONG).show();
    }
}


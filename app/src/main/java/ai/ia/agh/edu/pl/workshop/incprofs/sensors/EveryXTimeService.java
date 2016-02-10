package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

/**
 * Cogito ergo sum
 * Created by Rael on 08.02.2016.
 */


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Gecko_V on 2016-02-07.
 */
public class EveryXTimeService extends Service {

    private static final String AMT_PING = "ai.ia.agh.edu.pl.workshop.incprofs.AlarmManagerPING";
    private PendingIntent AMT_PendingIntent;
    private Intent AMT_Intent = new Intent(AMT_PING);
    private BroadcastReceiver AMT_BroadcastReceiver = new AlarmManagerReceiver();


    @Override
    public void onCreate() {
        Log.d("EveryXTime", "onCreate()");
        super.onCreate();


        Log.d("AWARE", "Alarm Manager + Test Receiver");

        AMT_PendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                0,
                AMT_Intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        ((AlarmManager) getSystemService(Context.ALARM_SERVICE)).setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                1000 * 60 * 2,
                AMT_PendingIntent); //todo: możliwe źródło błędów

        /*
        // Each and every 15 minutes will trigger onReceive of your BroadcastReceiver
        ((AlarmManager) getSystemService(Context.ALARM_SERVICE)).setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                AlarmManager.,      // 15min
                //60*1000,      // 1min
                AMT_PendingIntent);
        */

        registerReceiver(AMT_BroadcastReceiver, new IntentFilter(AMT_PING));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("EveryXTime", "onStartCommand() | start id:" + startId + ": " + intent);
        return START_STICKY;
        //return START_NOT_STICKY;  // service killed with process
    }

    @Override
    public void onDestroy() {
        Log.d("EveryXTime", "onDestroy()");
        super.onDestroy();

        if (AMT_BroadcastReceiver != null) {
            unregisterReceiver(AMT_BroadcastReceiver);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("EveryXTime", "onBind()");
        return null;
    }

}

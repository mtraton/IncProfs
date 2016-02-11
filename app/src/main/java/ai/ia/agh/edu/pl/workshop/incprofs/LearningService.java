package ai.ia.agh.edu.pl.workshop.incprofs;


import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.aware.Applications;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.Battery;
import com.aware.Locations;
import com.aware.WiFi;

import ai.ia.agh.edu.pl.workshop.incprofs.sensors.EveryXTimeService;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.SensorsListener;

/**
 * Created by Gecko_V on 2016-02-10.
 */
public class LearningService extends Service {

    private Intent everyXTimeService;


    @Override
    public void onCreate() {
        Log.d("LearningService", "onCreate()");
        super.onCreate();

        startSensors();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("LearningService", "onStartCommand() | start id:" + startId + ": " + intent);
        return START_STICKY;
        //return START_NOT_STICKY;  // service killed with process
    }

    @Override
    public void onDestroy() {
        Log.d("LearningService", "onDestroy()");
        super.onDestroy();

        stopSensors();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("LearningService", "onBind()");
        return null;
    }







// Listeners
    private static SensorsListener sensorsListener;


// Sensors
    public void startAware() {

        Log.d("sensors", "Initialise AWARE");
        Intent aware = new Intent(this, Aware.class);

        Log.d("sensors", "startService(aware)");
        startService(aware);
    }

    public void startBatterySensor() {

        Log.d("sensors", "Activate Battery");
        Aware.setSetting(this, Aware_Preferences.STATUS_BATTERY, true);

        Log.d("sensors", "Apply Battery settings");
        Aware.startSensor(this, Aware_Preferences.STATUS_BATTERY);
    }

    public void startGPSSensor() {

        Log.d("AWARE", "Activate GPS");
        Aware.setSetting(this, Aware_Preferences.STATUS_LOCATION_GPS, true);

        Log.d("AWARE", "Set GPS sampling frequency");
        Aware.setSetting(this, Aware_Preferences.FREQUENCY_LOCATION_GPS, 60);

        Log.d("AWARE", "Apply GPS settings");
        Aware.startSensor(this, Aware_Preferences.STATUS_LOCATION_GPS);
    }

    public void startWifiSensor() {

        Log.d("AWARE", "Activate WiFi");
        Aware.setSetting(this, Aware_Preferences.STATUS_WIFI, true);

        Log.d("AWARE", "Set WiFi sampling frequency");
        Aware.setSetting(this, Aware_Preferences.FREQUENCY_WIFI, 300);
        //Android can take up to 60 seconds to resolve all the found Wi-Fi device’s names. There is no way around this. The default and recommended scanning interval is 60 seconds or higher.

        Log.d("AWARE", "Apply WiFi settings");
        Aware.startSensor(this, Aware_Preferences.STATUS_WIFI);
    }

    public void startApplicationSensor() {
        //Activate applications
        Log.d("AWARE", "Activate Apps");
        Aware.setSetting(this, Aware_Preferences.STATUS_APPLICATIONS, true);
        //Apply settings
        Log.d("AWARE", "Apply Apps settings");
        Aware.startSensor(this, Aware_Preferences.STATUS_APPLICATIONS);
    }


    public void startSensorsListener() {

        sensorsListener = new SensorsListener();
        IntentFilter sensorsFilter = new IntentFilter();
        sensorsFilter.addAction(Applications.ACTION_AWARE_APPLICATIONS_FOREGROUND);
        sensorsFilter.addAction(Battery.ACTION_AWARE_BATTERY_CHANGED);
        sensorsFilter.addAction(Locations.ACTION_AWARE_LOCATIONS);
        //sensorsFilter.addAction(WiFi.ACTION_AWARE_WIFI_NEW_DEVICE);//todo: jaki broadcast jest najodpowiedniejszy?
        sensorsFilter.addAction(WiFi.ACTION_AWARE_WIFI_SCAN_STARTED);

        registerReceiver(sensorsListener, sensorsFilter);
    }


    /*
    Start ALL required sensors
     */
    public void startSensors() {

        startAware();

        // LOKALIZACJA UŻYTKOWNIKA
        startGPSSensor();
        // startGPSListener();


        // AKTYWNOŚĆ UŻYTKOWNIKA

        // APLIKACJE
        startApplicationSensor();
        // startApplicationListener();

        // PORA DNIA

        // SIEĆ WIFI DO KTÓREJ JEST PODŁĄCZONY
        startWifiSensor();

        // ZUZYCIE BATERII
        startBatterySensor();
        //startBatteryListener();

        // Broadcast Receiver
        startSensorsListener();

        // Service running every X minutes
        everyXTimeService = new Intent(this, EveryXTimeService.class);
        startService(everyXTimeService);
    }


    /*
    Stop ALL required sensors + listener
    useful in onDestroy!
     */
    public void stopSensors() {

        try {
            if (sensorsListener != null) {
                unregisterReceiver(sensorsListener);
            }
        } catch (IllegalArgumentException e) {
            sensorsListener = null;
        }

        if(everyXTimeService!=null) {
            stopService(everyXTimeService);
        }

        Aware.stopSensor(this, Aware_Preferences.STATUS_LOCATION_GPS);
        Aware.stopSensor(this, Aware_Preferences.STATUS_BATTERY);
        Aware.stopSensor(this, Aware_Preferences.STATUS_APPLICATIONS);
        //02-11 10:17:54.832 25253-25253/ai.ia.agh.edu.pl.workshop.incprofs E/AWARE::Applications: Tried to unregister Applications receiver not registered.
        Aware.stopSensor(this, Aware_Preferences.STATUS_WIFI);
    }

}

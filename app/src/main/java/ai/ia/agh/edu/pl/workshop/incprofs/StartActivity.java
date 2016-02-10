package ai.ia.agh.edu.pl.workshop.incprofs;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.aware.Applications;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.Battery;
import com.aware.Locations;
import com.aware.WiFi;

import java.io.File;
import java.io.IOException;

import ai.ia.agh.edu.pl.workshop.incprofs.sensors.AlarmManagerReceiver;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.EveryXTimeService;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.SensorsListener;

public class StartActivity extends Activity {

    public static final String NAME = "name";
    public static final String SURNAME = "surname";

    // Listeners
    //private static BatteryListener batteryListener;
    //private static GPSListener gpsListener;
    //private static ApplicationListener appListener;
    private static SensorsListener sensorsListener;
    private static AlarmManagerReceiver learningListener;

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


    public void stopSensors() {
        if (sensorsListener != null) {
            unregisterReceiver(sensorsListener);
        }

        Aware.stopSensor(this, Aware_Preferences.STATUS_LOCATION_GPS);
        Aware.stopSensor(this, Aware_Preferences.STATUS_BATTERY);
        Aware.stopSensor(this, Aware_Preferences.STATUS_APPLICATIONS);
        Aware.stopSensor(this, Aware_Preferences.STATUS_WIFI);
    }

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
        startService(new Intent(this, EveryXTimeService.class));

        //startLearningListener();
    }

    /*
    public void startLearningListener()
    {
        learningListener = new AlarmManagerReceiver();
        IntentFilter learningFilter = new IntentFilter();
        learningFilter.addAction(EveryXTimeService.);


        registerReceiver(learningListener, learningFilter);
    }
    public void stopLearningListener()
    {
        if (learningListener != null) {
            unregisterReceiver(learningListener);
        }
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("START", "On create activity!");

        // Android activity settings
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //wyczyść plik uczący
        String fileName = "learningData.arff";
        File file = new File(getFilesDir(), fileName);
        boolean deleted = file.delete();
        Log.d("IO", fileName + " deleted? " + deleted);

        // TODO: 22.01.2016 czy sensory nie powinny działać w servisach, niezależnie od życia naszej aktywności?
        //todo: może powinien pobierać nowe dane na podstawie time stampów do uczenia?

        startSensors();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        stopSensors();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    /**
     * Called by onClick event of go_button
     *  
     */
    public void startQuiz(View view) throws IOException {

        /*
        // todo: zabezpieczyć przed uruchomieniem na pustej bazie?
        //todo: wykrywaj, że baza danych z sensorów jest pusta lub nie ma nowych informacji do uczenia
        Log.d("sensors", "BUTTON PRESSED!!!!");

        Cursor batteryCursor = batterySensor.getDataCursor();
        BatteryData batteryData = new BatteryData(batteryCursor);

        BatteryLearningData batteryLearningData = new BatteryLearningData(batteryData);
        Instances instances = batteryLearningData.getInstances(); //// TODO: 22.01.2016 możliwe źródło błędu - skąd ma wiedzieć, że
        instances.setClassIndex(0);
        ArffCreator arffCreator = new ArffCreator(instances);
        arffCreator.writeInstancesData();
        String arffFilePath = arffCreator.getArffFilePath();

        Learner learner = new Learner(arffFilePath);
        Classifier classifier = learner.learnOnStream();
        */
    }


}

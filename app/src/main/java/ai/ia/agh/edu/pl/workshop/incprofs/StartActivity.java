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

import java.io.IOException;

import ai.ia.agh.edu.pl.workshop.incprofs.sensors.ApplicationListener;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.BatteryListener;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.BatterySensor;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.GPSListener;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.GPSObserver;

public class StartActivity extends Activity {

    public static final String NAME = "name";
    public static final String SURNAME = "surname";

    // Listeners

    private static BatteryListener batteryListener;
    private static GPSListener gpsListener;
    private static ApplicationListener appListener;


    // Sensors

    BatterySensor batterySensor;
    GPSObserver gpsObserver;


    public void startAware() {

        Log.d("sensors", "Initialise AWARE");
        Intent aware = new Intent(this, Aware.class);

        Log.d("sensors", "startService(aware)");
        startService(aware);
    }


    public void startGPSSensor() {

        Log.d("AWARE", "Activate GPS");
        Aware.setSetting(this, Aware_Preferences.STATUS_LOCATION_GPS, true);

        Log.d("AWARE", "Set GPS sampling frequency");
        Aware.setSetting(this, Aware_Preferences.FREQUENCY_LOCATION_GPS, 60);

        Log.d("AWARE", "Apply GPS settings");
        Aware.startSensor(this, Aware_Preferences.STATUS_LOCATION_GPS);

    }

    public void startBatterySensor() {

        Log.d("sensors", "Activate Battery");
        Aware.setSetting(this, Aware_Preferences.STATUS_BATTERY, true);

        Log.d("sensors", "Apply Battery settings");
        Aware.startSensor(this, Aware_Preferences.STATUS_BATTERY);
    }

    public void startGPSListener() {

        gpsListener = new GPSListener();
        IntentFilter gpsFilter = new IntentFilter();
        gpsFilter.addAction(Locations.ACTION_AWARE_LOCATIONS); // // TODO: 05.02.2016 możliwe źródło błędu
        registerReceiver(gpsListener, gpsFilter);
    }

    public void startApplicationListener() {

        appListener = new ApplicationListener();
        IntentFilter appFilter = new IntentFilter();
        appFilter.addAction(Applications.ACTION_AWARE_APPLICATIONS_FOREGROUND); // // TODO: 05.02.2016 możliwe źródło błędu
        registerReceiver(appListener, appFilter);
    }

    public void stopGPS() {
        unregisterReceiver(batteryListener);
        Aware.stopSensor(this, Aware_Preferences.STATUS_BATTERY);
    }

    public void stopBattery() {
        unregisterReceiver(gpsListener);
        Aware.stopSensor(this, Aware_Preferences.STATUS_LOCATION_GPS);
    }

    public void stopApplication() {
        unregisterReceiver(appListener);
        Aware.stopSensor(this, Aware_Preferences.STATUS_APPLICATIONS);
    }

    public void startBatteryListener() {

        batteryListener = new BatteryListener();
        IntentFilter batteryFilter = new IntentFilter();
        batteryFilter.addAction(Battery.ACTION_AWARE_BATTERY_CHANGED);
        registerReceiver(batteryListener, batteryFilter);
    }

    public void startApplicationSensor() {
        //Activate applications
        Log.d("AWARE", "Activate Apps");
        Aware.setSetting(this, Aware_Preferences.STATUS_APPLICATIONS, true);
        //Apply settings
        Log.d("AWARE", "Apply Apps settings");
        Aware.startSensor(this, Aware_Preferences.STATUS_APPLICATIONS);
    }

    public void startSensors() {

        startAware();
        // LOKALIZACJA UŻYTKOWNIKA
        startGPSSensor();
        startGPSListener();


        // AKTYWNOŚĆ UŻYTKOWNIKA

        // APLIKACJE
        startApplicationSensor();
        startApplicationListener();
        // PORA DNIA
        // SIEĆ WIFI DO KTÓREJ JEST PODŁĄCZONY
        // ZUZYCIE BATERII
        startBatterySensor();
        startBatteryListener();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Log.d("START", "On create activity!");

        // Android activity settings
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        // // TODO: 22.01.2016 czy sensory nie powinny działać w servisach, niezależnie od życia naszej aktywności?

        //batterySensor = new BatterySensor(this); // todo: jako zmienna dla całej klasy

//        try {
//            Thread.sleep(1500);
//        }
//        catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
//        startSensors();
//
//
//
//        int i = 0;
//        int sampleLimit = 500;// todo: system nie mógł otworzyć pliku z bazą danych po 992 próbce
//        while (i < sampleLimit) {
//            batterySensor.getDataCursor().moveToNext();
//            i++;
//            Log.d("sensors", "" + i);
//        }
//        Log.d("sensors", "data aquistion ended?");

        //todo: może powinien pobierać nowe dane na podstawie time stampów do uczenia?


        startSensors();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopGPS();
        stopBattery();
        stopApplication();
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

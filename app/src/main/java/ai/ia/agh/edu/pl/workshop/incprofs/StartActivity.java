package ai.ia.agh.edu.pl.workshop.incprofs;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.Locations;

import java.io.IOException;

import ai.ia.agh.edu.pl.workshop.incprofs.learning.ArffCreator;
import ai.ia.agh.edu.pl.workshop.incprofs.learning.BatteryLearningData;
import ai.ia.agh.edu.pl.workshop.incprofs.learning.Learner;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.BatteryData;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.BatteryListener;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.BatterySensor;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.GPSListener;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.GPSObserver;
import moa.classifiers.Classifier;
import weka.core.Instances;

public class StartActivity extends Activity {

    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    private static BatteryListener batteryListener;
    private static GPSListener gpsListener;
    // sensors
    BatterySensor batterySensor;
    GPSObserver gpsObserver;
    private String sensorName = Aware_Preferences.STATUS_LOCATION_GPS;
    //todo: wykrywaj, że baza danych z sensorów jest pusta lub nie ma nowych informacji do uczenia

    public void startSensors() {
        // SENSORY JAKO OBSERVERY?

        // LOKALIZACJA UŻYTKOWNIKA
        //Activate GPS
        Log.d("AWARE", "Activate GPS");
        Aware.setSetting(this, Aware_Preferences.STATUS_LOCATION_GPS, true);
        //Set GPS freq
        Log.d("AWARE", "Set GPS sampling frequency");
        Aware.setSetting(this, Aware_Preferences.FREQUENCY_LOCATION_GPS, 60);

        //Aware.setSetting(this, Aware_Preferences.MIN_LOCATION_GPS_ACCURACY, 5);

        //Aware.setSetting(this, Aware_Preferences.LOCATION_EXPIRATION_TIME, 10);

        //Apply settings
        Log.d("AWARE", "Apply GPS settings");
        Aware.startSensor(this, Aware_Preferences.STATUS_LOCATION_GPS);


/*
        //Activate GPS2
		Log.d("AWARE", "Activate GPSNet");
		Aware.setSetting(this, Aware_Preferences.STATUS_LOCATION_NETWORK, true);
		//Set GPS freq
		Log.d("AWARE", "Set GPSNet sampling frequency");
		Aware.setSetting(this, Aware_Preferences.FREQUENCY_LOCATION_NETWORK, 10);
		//Apply settings
		Log.d("AWARE", "Apply GPSNet settings");
		Aware.startSensor(this, Aware_Preferences.STATUS_LOCATION_NETWORK);
*/


        // AKTYWNOŚĆ UŻYTKOWNIKA
        // APLIKACJE
        // PORA DNIA
        // SIEĆ WIFI DO KTÓREJ JEST PODŁĄCZONY
        // ZUZYCIE BATERII
        Log.d("sensors", "Activate Battery");
        Aware.setSetting(this, Aware_Preferences.STATUS_BATTERY, true);


        //Apply settings
        Log.d("sensors", "Apply Battery settings");
        Aware.startSensor(this, Aware_Preferences.STATUS_BATTERY);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // wystartuj wszystkie potrzebne sensory

        super.onCreate(savedInstanceState);
        Log.d("START", "On create activity!");
        /*Setting the layout*/
        setContentView(R.layout.activity_start);

        initialiseAndStartAware();
        startSensors();


        gpsListener = new GPSListener();


        IntentFilter gpsFilter = new IntentFilter();
        gpsFilter.addAction(Locations.ACTION_AWARE_LOCATIONS);
        registerReceiver(gpsListener, gpsFilter);
        registerReceiver(gpsListener, gpsFilter);

//        batteryListener = new BatteryListener();
//        IntentFilter batteryFilter = new IntentFilter();
//        batteryFilter.addAction(Battery.ACTION_AWARE_BATTERY_CHANGED);
//        registerReceiver(batteryListener, batteryFilter);




        // // TODO: 22.01.2016 czy sensory nie powinny działać w servisach, niezależnie od życia naszej aktywności?

//        batterySensor = new BatterySensor(this); // todo: jako zmienna dla całej klasy
//
//        try {
//            Thread.sleep(1500);
//        }
//        catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
//        startSensors();


        /*
        int i = 0;
        int sampleLimit = 500;// todo: system nie mógł otworzyć pliku z bazą danych po 992 próbce
        while (i < sampleLimit) {
            batterySensor.getDataCursor().moveToNext();
            i++;
            Log.d("sensors", "" + i);
        }
        Log.d("sensors", "data aquistion ended?");

        //todo: może powinien pobierać nowe dane na podstawie time stampów do uczenia?
        */


















    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryListener);
        unregisterReceiver(gpsListener);
        Aware.stopSensor(this, Aware_Preferences.STATUS_BATTERY);
        Aware.stopSensor(this, Aware_Preferences.STATUS_LOCATION_GPS);

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

        // todo: zabezpieczyć przed uruchomieniem na pustej bazie?
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

    }

    public void initialiseAndStartAware() {
        //Initialise AWARE
        Log.d("sensors", "Initialise AWARE");
        Intent aware = new Intent(this, Aware.class);

        Log.d("sensors", "startService(aware)");
        startService(aware);
    }

}

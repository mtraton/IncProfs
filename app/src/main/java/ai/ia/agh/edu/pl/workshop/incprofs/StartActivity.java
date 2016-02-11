package ai.ia.agh.edu.pl.workshop.incprofs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;

import ai.ia.agh.edu.pl.workshop.incprofs.learning.Learner;

public class StartActivity extends Activity {

    private ToggleButton toggle_learning;
    private static final String toggleButtonLearningState = "toggleButton_Learning_State";

    private Intent learningService;

    /*
    // Listeners
    //private static BatteryListener batteryListener;
    //private static GPSListener gpsListener;
    //private static ApplicationListener appListener;
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


    public void stopSensors() {

        try {
            if (sensorsListener != null) {
                unregisterReceiver(sensorsListener);
            }
        } catch (IllegalArgumentException e) {
            sensorsListener = null;
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
    }
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("StartActivity", "On create()");

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

        //startSensors();

        toggle_learning = (ToggleButton) findViewById(R.id.toggleButton_learning);
        toggle_learning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Log.d("StartActivity", "toggle On");
                    //startSensors();
                    learningService = new Intent(getApplicationContext(), LearningService.class);
                    startService(learningService);
                } else {
                    // The toggle is disabled
                    Log.d("StartActivity", "toggle Off");
                    //stopSensors();
                    if(learningService!=null) {
                        stopService(learningService);
                    }
                }
            }
        });
        LoadPreferences();
    }

    @Override
    public void onDestroy() {
        Log.d("StartActivity", "onDestroy()");

        super.onDestroy();
        //stopSensors();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }


/*
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("StartActivity", "onSaveInstanceState()");
        savedInstanceState.putBoolean(toggleButtonLearningState, toggle_learning.isChecked());

        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("StartActivity", "onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);

        toggle_learning.setChecked(savedInstanceState.getBoolean(toggleButtonLearningState, false));
    }
*/


    private void SavePreferences() {
        Log.d("StartActivity", "SavePreferences()");
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(toggleButtonLearningState, toggle_learning.isChecked());
        editor.commit();
    }
    private void LoadPreferences(){
        Log.d("StartActivity", "LoadPreferences()");
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        Boolean state = sharedPreferences.getBoolean(toggleButtonLearningState, false);
        toggle_learning.setChecked(state);
    }
    @Override
    public void onBackPressed() {
        SavePreferences();
        super.onBackPressed();
    }


    /*
    @+id/button_testing
     */
    public void startTesting(View view) {
        Log.d("StartActivity", "button testing pressed: startTesting()");
        // Test zapisanego podczas uczenia klasyfikatora

        // zakładamy że plik testowy nazywa się testData.arff i znajduje się w katalogu głównym karty SD
        // z niewiadomych przyczyn nie udaje się odczytać pliku wrzuconego do folderu files aplikacji
        String testDataFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getResources().getString(R.string.test_file_name);
        String outputClassifierFileName = getResources().getString (R.string.classifier_file_name);
        String outputClassifierFilePath = getFilesDir() + File.separator + outputClassifierFileName; // todo: możliwe źródło błędów

        File testDataFile = new File(testDataFilePath);
        File classifierFile = new File(outputClassifierFilePath);
        if(testDataFile.exists() && classifierFile.exists())
        {
            Learner testLearner = new Learner(testDataFilePath);
            double accuracy = testLearner.testClassifierFromFile(outputClassifierFilePath);

            //todo: wrzucić w osobną funkcję
            Context context = getApplicationContext();
            CharSequence text = "Dokładność klasyfikatora : " + accuracy + "%.";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else
        {
            Context context = getApplicationContext();
            CharSequence text = "Nie można testować\n" + "Brak pliku z klasyfikatorem lub danymi testowymi";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }



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

package ai.ia.agh.edu.pl.workshop.incprofs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.aware.Aware;

import java.io.IOException;

import ai.ia.agh.edu.pl.workshop.incprofs.learning.ArffCreator;
import ai.ia.agh.edu.pl.workshop.incprofs.learning.BatteryLearningData;
import ai.ia.agh.edu.pl.workshop.incprofs.learning.Learner;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.BatteryData;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.BatterySensor;
import moa.classifiers.Classifier;
import weka.core.Instances;

public class StartActivity extends Activity {

    public static final String NAME = "name";
    public static final String SURNAME = "surname";

    // sensors
    BatterySensor batterySensor;

    //todo: wykrywaj, że baza danych z sensorów jest pusta lub nie ma nowych informacji do uczenia

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("START", "On create activity!");
        /*Setting the layout*/
        setContentView(R.layout.activity_start);
        initialiseAndStartAware();

        // // TODO: 22.01.2016 czy sensory nie powinny działać w servisach, niezależnie od życia naszej aktywności?

        batterySensor = new BatterySensor(this); // todo: jako zmienna dla całej klasy

        int i = 0;
        int sampleLimit = 500;// todo: system nie mógł otworzyć pliku z bazą danych po 992 próbce
        while (i < sampleLimit) {
            batterySensor.getDataCursor().moveToNext();
            i++;
            Log.d("sensors", "" + i);
        }
        Log.d("sensors", "data aquistion ended?");

        //todo: może powinien pobierać nowe dane na podstawie time stampów do uczenia?



















    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        batterySensor.close();

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

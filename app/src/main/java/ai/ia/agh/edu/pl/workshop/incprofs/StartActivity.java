package ai.ia.agh.edu.pl.workshop.incprofs;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;

import ai.ia.agh.edu.pl.workshop.incprofs.learning.Learner;
import ai.ia.agh.edu.pl.workshop.incprofs.sensors.Utils;
import moa.classifiers.trees.HoeffdingTree;
import moa.core.Measurement;

public class StartActivity extends Activity {

    private ToggleButton toggle_learning;
    private TextView textView_output;

    private static final String toggleButtonLearningState = "toggleButton_Learning_State";
    private Intent learningService;

    public static final String GUIUpdateIntentLabel = "GUIUpdateIntentLabel";
    public static final String GUIUpdateKey = "GUIUpdateKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("StartActivity", "On create()");
        // Android activity settings
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //wyczyść plik uczący aby zapobiec błędom przy wczytywaniu danych
        cleanLearningDataFile();

        registerReceiver(uiUpdated, new IntentFilter(GUIUpdateIntentLabel));


        textView_output = (TextView) findViewById(R.id.textView_output);
        textView_output.setMovementMethod(new ScrollingMovementMethod());

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
                    if (learningService != null) {
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
        unregisterReceiver(uiUpdated);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

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

    // zapisz string do TextView
    public void printOutput(String out) {
        Log.d("StartActivity", "printOutput(" + out + ")");

        if(textView_output != null) {

            if(out==null || out.equals("")) {
                textView_output.setText(R.string.textView_output_no);
            } else {
                textView_output.setText(out);
            }
        }
    }

    /*
    @+id/button_testing
     */
    public void startTesting(View view) {
        Log.d("StartActivity", "button testing pressed: startTesting()");
        // Test zapisanego podczas uczenia klasyfikatora

        // zakładamy że plik testowy nazywa się testData.arff (nazwa w Resources.strings) i znajduje się w katalogu głównym karty SD
        // z niewiadomych przyczyn nie udaje się odczytać pliku wrzuconego do folderu files aplikacji

        String testDataFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getResources().getString(R.string.test_file_name);
        String outputClassifierFileName = getResources().getString (R.string.classifier_file_name);
        String outputClassifierFilePath = getFilesDir() + File.separator + outputClassifierFileName; //

        File testDataFile = new File(testDataFilePath);
        File classifierFile = new File(outputClassifierFilePath);
        if(testDataFile.exists() && classifierFile.exists())
        {
            Learner testLearner = new Learner(testDataFilePath);
            HoeffdingTree tree  = (HoeffdingTree)Learner.getLearner();


            String text = "";
            for(Measurement measurement: tree.getModelMeasurements())
            {
                text = text + measurement.getName() + " = " + measurement.getValue() + "\n";
            }

            StringBuilder out = new StringBuilder();
            tree.getModelDescription(out, 1);
            text = text + out.toString() + "\n";
            double accuracy = testLearner.testClassifierFromFile(outputClassifierFilePath);
            text =  text + "Dokładność klasyfikatora : " + accuracy + "%.\n";
            Utils.showToast(text, getApplicationContext());

            printOutput(text);

        }
        else
        {
            String text = "Nie można testować\n" + "Brak pliku z klasyfikatorem lub danymi testowymi";
            Utils.showToast(text, getApplicationContext());
            printOutput("");// wyczyść poprzednią wartość
            printOutput(text);
        }



    }

    private void cleanLearningDataFile()
    {
        String fileName = getResources().getString(R.string.arff_file_name);
        File file = new File(getFilesDir(), fileName);
        boolean deleted = file.delete();
        Log.d("IO", fileName + " deleted? " + deleted);

    }



    private BroadcastReceiver uiUpdated = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //TODO Update text here!
            //TextView.setText(intent.getExtras().getString("<KEY>"))
            printOutput("Przykładowy text:\n"+intent.getExtras().getString(GUIUpdateKey));
        }
    };
}

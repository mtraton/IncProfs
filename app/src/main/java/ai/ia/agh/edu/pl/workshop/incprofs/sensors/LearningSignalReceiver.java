package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;

import ai.ia.agh.edu.pl.workshop.incprofs.R;
import ai.ia.agh.edu.pl.workshop.incprofs.learning.Learner;

/**
 * Cogito ergo sum
 * Created by Rael on 08.02.2016.
 */
public class LearningSignalReceiver extends BroadcastReceiver {

    public void onReceive(Context c, Intent intent) {

        Log.d("EveryXTime+Receiver", "onReceive, countdown complete: X min passed!");

        //1. Stwórz ścieżkę do pliku z danymi uczącymi
        String inputDataFileName = c.getResources().getString(R.string.arff_file_name);
        String inputDataFilePath = c.getFilesDir().toString() + "/" + inputDataFileName;
        File inputDataFile = new File(inputDataFilePath);

        //1.a Sprawdź czy plik istnieje

        if (inputDataFile.exists()) {

            Utils utils = new Utils();
            String fileContent = utils.readFile(inputDataFileName, c);
            // 2. Ucz się z pliku
            long startTime = System.currentTimeMillis();

            Learner learner = new Learner(inputDataFilePath);
            learner.learnOnStream();

            double duration = (System.currentTimeMillis() - startTime) / 1000.0;
            Utils.showToast("Czas trwania nauki:" + duration + "sekund", c);

            //3. Zapisz klasyfikator do pliku
            String outputClassifierFileName = c.getResources().getString(R.string.classifier_file_name);
            String outputClassifierFilePath = c.getFilesDir() + "/" + outputClassifierFileName;
            learner.saveClassfierToFile(outputClassifierFilePath);

            //4. Usuń stary plik z danymi uczącymi
            boolean deleted = inputDataFile.delete();

        } else {
            Log.d("Learning/IO", "No file to learn from!");
        }


    }
}




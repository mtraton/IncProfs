package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ai.ia.agh.edu.pl.workshop.incprofs.R;
import ai.ia.agh.edu.pl.workshop.incprofs.learning.Learner;

/**
 * Cogito ergo sum
 * Created by Rael on 08.02.2016.
 */
public class AlarmManagerReceiver extends BroadcastReceiver {



    public void onReceive(Context c, Intent intent) {

        Log.d("EveryXTime+Receiver", "onReceive, countdown complete: X min passed!");

        //1. Stwórz ścieżkę do pliku
        String inputDataFileName =  c.getResources().getString (R.string.arff_file_name);
        String inputDataFilePath = c.getFilesDir().toString() + "/" + inputDataFileName;

        //1.a czy plik istnieje?

        File inputDataFile = new File(inputDataFilePath);
        //todo: kod opiera się na założeniu, że
        if(inputDataFile.exists()) {
            String fileContent = readFile(inputDataFileName, c);
            
            // 2. Naucz się
            long startTime = System.currentTimeMillis();

           Learner learner = new Learner(inputDataFilePath);
           learner.learnOnStream();

           double duration = (System.currentTimeMillis() - startTime)/1000.0;
            Utils.showToast("Czas trwania nauki:" + duration + "sekund", c);
            //4. Zapisz klasyfikator do pliku
            String outputClassifierFileName = c.getResources().getString (R.string.classifier_file_name);
            String outputClassifierFilePath = c.getFilesDir() + "/" + outputClassifierFileName;
            //File outputClassifierFile = new File(outputClassifierFilePath);
            learner.saveClassfierToFile(outputClassifierFilePath);
            String savingTest = readFile(outputClassifierFileName,c);
            Log.d("IO", "classifier saved");

            //3. Usuń plik
            //File file = new File(c.getFilesDir(), arffFileName);
            boolean deleted = inputDataFile.delete();
            Log.d("IO", inputDataFileName + " deleted? " + deleted);

        }
        else
        {
            Log.d("Learning/IO", "No file to learn from!");
        }

      
       
    }

    private String readFile(String fileName, Context c)   {

        //File file = new File(pathname);
        //String p = c.getFilesDir().getAbsolutePath();
        File f = new File(c.getFilesDir(),fileName);
        StringBuilder fileContents = new StringBuilder((int)f.length());
        Scanner scanner = null;
        try {
            scanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String lineSeparator = System.getProperty("line.separator");

        try {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }
}


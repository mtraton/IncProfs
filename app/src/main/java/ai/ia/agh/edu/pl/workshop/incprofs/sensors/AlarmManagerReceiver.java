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

        //todo: ucz się z pliku
        //1. Stwórz ścieżkę do pliku
        String arffFileName =  c.getResources().getString (R.string.arff_file_name);

        String filepath = c.getFilesDir().toString() + "/" + arffFileName;

        //1.a czy plik istnieje?

        File file = new File(filepath);
        //todo: kod opiera się na założeniu, że
        if(file.exists()) {
            String fileContent = readFile(arffFileName, c);
            // 2. Naucz się
            Learner learner = new Learner(filepath);
           learner.learnOnStream();

            //3. Usuń plik
            //File file = new File(c.getFilesDir(), arffFileName);
            boolean deleted = file.delete();
            Log.d("IO", arffFileName + " deleted? " + deleted);
            //Toast.makeText(c, "onReceive, countdown complete: 15 min passed!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Log.d("Learning/IO", "No file to learn from!");
        }

        //todo: zwróć klasyfikator
        // np. zapisz do pliku
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


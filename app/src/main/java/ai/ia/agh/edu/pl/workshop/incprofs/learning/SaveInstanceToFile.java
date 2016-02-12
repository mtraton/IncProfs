package ai.ia.agh.edu.pl.workshop.incprofs.learning;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import ai.ia.agh.edu.pl.workshop.incprofs.R;
import weka.core.Instances;

/**
 * Cogito ergo sum
 * Created by Rael on 08.02.2016.
 * Dopisz do pliku z instancjami kolejną instancję
 */
public class SaveInstanceToFile {

    String fileName;
    Context c;


    public static Instances getInstanceFromFile(String pFileName) {
        Instances data = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pFileName));
            try {
                data = new Instances(reader);
            } catch (IOException e) {
                return null;
            }
            reader.close();
            // setting class attribute
            data.setClassIndex(data.numAttributes() - 1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data;

    }


    public SaveInstanceToFile(Context c) {
        this.c = c;
        fileName = c.getResources().getString(R.string.arff_file_name);
    }


    // write instance to file
    public void writeInstancesData(Instances instances) throws IOException {
        if (instances != null) {

            // 1. Wczytaj poprzednie instancje
            String dataFilePath = c.getFilesDir().toString() + "/" + fileName;//
            File file = new File(dataFilePath);
            Instances prevInstances = null;
            if (file.exists()) {
                prevInstances = getInstanceFromFile(dataFilePath);
            }

            if (prevInstances == null) {
                //1.a nie ma poprzednich instancji, musimy stworzyć plik po raz pierwszy
                prevInstances = instances;
            } else {
                //2.  Dodaj nową instację
                prevInstances.add(instances.get(0));
            }

            // 3. Zapisz instancje do pliku
            FileOutputStream outputStream;

            try {
                outputStream = c.openFileOutput(fileName, Context.MODE_PRIVATE); //File creation mode: the default mode, where the created file can only be accessed by the calling application (or all applications sharing the same user ID).
                outputStream.write(prevInstances.toString().getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d("IO", "error - trying to write null instance");
            return;
        }

    }

}

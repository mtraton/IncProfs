package ai.ia.agh.edu.pl.workshop.incprofs.learning;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import ai.ia.agh.edu.pl.workshop.incprofs.R;
import weka.core.Instances;

/**
 * Cogito ergo sum
 * Created by Rael on 08.02.2016.
 * Dopisz do pliku z instancjami kolejną instancję
 */
public class SaveInstanceToFile {


        String fileName;
       // String arfffExtension = "";
        File instanceFile;
        private static String instanceFilePath; //todo: to musi być widoczne spoza klasy

        Context c;

        Instances dataSet;

        // constructor



    public static Instances getInstanceFromFile(String pFileName)
    {
        Instances data = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pFileName));
            try {
                data = new Instances(reader);
            }
            catch(IOException e)
            {

                return null;
            }
            reader.close();
            // setting class attribute
            data.setClassIndex(data.numAttributes() - 1); // todo: możliwe źródło błędu (poprawiona LinkedHashMap, do testu
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data;

    }

    public static String getInstanceFilePath() {
        return instanceFilePath;
    }

    public SaveInstanceToFile(Context c)
    {
        this.c = c;
        fileName = c.getResources().getString(R.string.arff_file_name);
    }




        // write instance to file
        public void writeInstancesData(Instances instances) throws IOException {
        //todo appending


            if(instances != null) {

                // 1. Wczytaj poprzednie instancje
                String dataFilePath = c.getFilesDir().toString() + "/" + fileName;//
                File file = new File(dataFilePath);
                Instances prevInstances = null;
                if(file.exists())
                {
                    prevInstances  = getInstanceFromFile(dataFilePath);
                }

                if(prevInstances == null)
                {
                  //1.a nie ma poprzednich instancji, musimy stworzyć plik po raz pierwszy
                    prevInstances = instances;
                }
                else
                {
                    //2.  Dodaj nową instację

                    prevInstances.add(instances.get(0));
                }
                // 3. Zapisz instancje do pliku


                FileOutputStream outputStream;

                try {
                    outputStream = c.openFileOutput(fileName, Context.MODE_PRIVATE); //File creation mode: the default mode, where the created file can only be accessed by the calling application (or all applications sharing the same user ID).

                    outputStream.write(prevInstances.toString().getBytes()); // todo: możliwe źródło błędu

                    //outputStream.write("\n".getBytes());
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String s = readFile(fileName);
                Log.d("IO", "file content:" + s);

            }
            else
            {
                Log.d("IO", "error - trying to write null instance");
                return;
            }

        }

    private String readFile(String fileName)   {

        //File file = new File(pathname);
        //String p = c.getFilesDir().getAbsolutePath();
        File f = new File(c.getFilesDir(),fileName);
        StringBuilder fileContents = new StringBuilder((int)f.length());
        Scanner scanner = null;
        try {
            scanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;



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

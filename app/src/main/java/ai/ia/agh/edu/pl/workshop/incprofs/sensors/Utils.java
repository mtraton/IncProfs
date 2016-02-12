package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * Created by Rael on 22.01.2016.
 * Różne przydatne funkcje
 */
public class Utils {

    public Date timeStampToDate(double timeStamp)
    {
        long t = (long) timeStamp;
        return new Date(t);
    }

    public static void showToast(String text, Context c)
    {
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(c, text, duration);
        toast.show();
    }
    public String readFile(String fileName, Context c)   {
        File f = new File(c.getFilesDir(),fileName);
        StringBuilder fileContents = new StringBuilder((int)f.length());
        Scanner scanner;
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




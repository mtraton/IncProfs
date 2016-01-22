package ai.ia.agh.edu.pl.workshop.incprofs.learning;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instances;

/**
 * Created by Rael on 21.01.2016.
 * Klasa tworząca z instancji plik potrzebny do wygenerowania strumienia uczącego
 */
public class ArffCreator {


    String arffFileName = "arff"; //// TODO: 21.01.2016 ta nazwa powinna być parametryzowana nazwą typu danych
    String arfffExtension = ".arff";
    File arffFile;
    String arffFilePath;

    Instances dataSet;

    public ArffCreator(Instances trainingData) {
        this.arffFile = createTmpArffFile(arffFileName);
        this.dataSet = trainingData;// todo: ujednolicić te nazwy z instaces


    }

    File createTmpArffFile(String fileName) {

        try {
            arffFile = File.createTempFile(fileName, arfffExtension); // błąd, zwraca nulla
            arffFilePath = arffFile.getAbsolutePath(); // todo: possible error
        } catch (Exception e) {
            e.printStackTrace();
            //todo: nie udało się utworzyć pliku - obsłużyć
        }
        arffFile.deleteOnExit();
        return arffFile;
    }

    public void writeInstancesData() {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(arffFilePath));
            writer.write(dataSet.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getArffFilePath() {
        return arffFilePath;
    }

}

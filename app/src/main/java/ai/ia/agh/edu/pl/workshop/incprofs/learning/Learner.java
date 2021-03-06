package ai.ia.agh.edu.pl.workshop.incprofs.learning;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingAdaptiveTree;
import moa.classifiers.trees.HoeffdingTree;
import moa.streams.ArffFileStream;
import weka.core.Instance;

import static moa.core.SerializeUtils.readFromFile;
import static moa.core.SerializeUtils.writeToFile;

/**
 * Created by Rael on 21.01.2016.
 * Klasa której zadaniem jest:
 * tworzenie strumienia z pliku
 * pomocnicze funkcje niezbędne do uczenia
 * przeprowadzanie uczenia
 * zwrot modelu/klasyfikatora
 */
public class Learner {

    private static HoeffdingTree learner;
    ArffFileStream stream;
    String streamFilePath;

    public Learner(String streamFilePath) {
        prepareStream(streamFilePath);
        prepareLearner();
    }

    public static Classifier getLearner() {
        return learner;
    }

    public void prepareStream(String streamFilePath) throws NullPointerException {
        this.streamFilePath = streamFilePath;

        /*
        ArffFileStream	(	String 	arffFileName, int 	classIndex
		"Class index of data. 0 for none or -1 for last attribute in file.",
         */

        stream = new ArffFileStream(streamFilePath, -1);
        stream.prepareForUse();
    }

    public void prepareLearner() {
        if(learner == null)
        {
            learner = new HoeffdingAdaptiveTree();
            learner.setModelContext(stream.getHeader());
            learner.prepareForUse();
        }
        else
        {
            Log.d("IO", "Classificator exists");
        }


    }

    public Classifier learnOnStream() {

        while (stream.hasMoreInstances()) {

            Instance trainInst = stream.nextInstance();
            learner.trainOnInstanceImpl(trainInst); // Trains this classifier incrementally using the given instance.
        }

        Log.d("ML", "Learning done!");
        return learner;
    }

    public void saveClassfierToFile(String path)
    {
        File f = new File(path);
        try {
            writeToFile(f, learner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double testLearningAccuracy() {
        int numberSamplesCorrect = 0;
        int numberSamples = 0;

        while (stream.hasMoreInstances() )  {
            Instance trainInst = stream.nextInstance();

            if (learner.correctlyClassifies(trainInst)) {
                numberSamplesCorrect++;
            }

            numberSamples++;

        }
        double learningAccuracy = 100.0 * (double) numberSamplesCorrect / (double) numberSamples;
        Log.d("ML", "Accuracy calculated! " + learningAccuracy);

        return learningAccuracy;
    }

    public Classifier loadClassifierFromFile(String classifierFilePath)
    {
        File classifierFile = new File(classifierFilePath);
        if(classifierFile.exists())
        {
            Object o = null;
            try {
                o = readFromFile(classifierFile);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return (Classifier) o;
        }
        else
        {
            Log.d("IO", "Could not load classifier file");
            return null;
        }

    }

    public double testClassifierFromFile(String classifierFilePath)
    {
        //1. Wczytaj klasyfikator z pliku
        learner = (HoeffdingTree) loadClassifierFromFile(classifierFilePath);

        //2. Oblicz dokładność klasyfikacji
        return testLearningAccuracy();
    }


}

package ai.ia.agh.edu.pl.workshop.incprofs.learning;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingAdaptiveTree;
import moa.streams.ArffFileStream;
import weka.core.Instance;

import static moa.core.SerializeUtils.readFromFile;
import static moa.core.SerializeUtils.writeToFile;

/**
 * Created by Rael on 21.01.2016.
 * Klasa której zadaniem jest:
 * odbiór danych z sensorów
 * pomocnicze funkcje niezbędne do uczenia
 * przeprowadzanie uczenia
 * zwrot modelu/klasyfikatora
 */
public class Learner {

    private static HoeffdingAdaptiveTree learner;
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
        this.streamFilePath = streamFilePath; // // TODO: 22.01.2016 może tu dostać nulla, obsłużyć 
        stream = new ArffFileStream(streamFilePath, -1);
        stream.prepareForUse();


    }

    public void prepareLearner() {
        if(learner == null)
        {
            learner = new HoeffdingAdaptiveTree();
            learner.setModelContext(stream.getHeader()); // błąd - możliwa różnica w headerach ARFF?

            //todo class index?
            learner.prepareForUse();
        }

        //Method threw 'java.lang.NullPointerException' exception. Cannot evaluate moa.classifiers.trees.HoeffdingTree$LearningNodeNBAdaptive.toString()



    }

    public Classifier learnOnStream() {

        while (stream.hasMoreInstances()) {

            Instance trainInst = stream.nextInstance();
            Log.d("Learning", "class value = " + trainInst.classValue()); // zwraca 0 zamiast blank
            learner.trainOnInstanceImpl(trainInst); // Trains this classifier incrementally using the given instance.
            /*
            Method threw 'java.lang.NullPointerException' exception. Cannot evaluate moa.classifiers.trees.HoeffdingTree$LearningNodeNBAdaptive.toString()

             */

        }

        Log.d("Learning", "Learning done!");
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
        //long numInstances = stream.estimatedRemainingInstances(); //todo: możliwe źródło błędów

        while (stream.hasMoreInstances() )  {//&& (numberSamples < numInstances))
            Instance trainInst = stream.nextInstance();

            if (learner.correctlyClassifies(trainInst)) {
                numberSamplesCorrect++;
            }

            numberSamples++;
            //learner.trainOnInstance(trainInst);

        }
        double learningAccuracy = 100.0 * (double) numberSamplesCorrect / (double) numberSamples;
        Log.d("ML", "Accuracy calculated! " + learningAccuracy);

        return learningAccuracy;
    }

    public void testClassifierFromFile(String classifierFilePath)
    {
        //1. Wczytaj klasyfikator
        File classifierFile = new File(classifierFilePath);
        Object o = null;
        try {
            o = readFromFile(classifierFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HoeffdingAdaptiveTree testedClassifier = (HoeffdingAdaptiveTree) o;

        learner = testedClassifier;
        testLearningAccuracy();


    }


}

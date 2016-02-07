package ai.ia.agh.edu.pl.workshop.incprofs.learning;

import android.util.Log;

import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingTree;
import moa.streams.ArffFileStream;
import weka.core.Instance;

/**
 * Created by Rael on 21.01.2016.
 * Klasa której zadaniem jest:
 * odbiór danych z sensorów
 * pomocnicze funkcje niezbędne do uczenia
 * przeprowadzanie uczenia
 * zwrot modelu/klasyfikatora
 */
public class Learner {

    Classifier learner;
    ArffFileStream stream;
    String streamFilePath;

    public Learner(String streamFilePath) {
        prepareStream(streamFilePath);
        prepareLearner();

    }

    public void prepareStream(String streamFilePath) throws NullPointerException {
        this.streamFilePath = streamFilePath; // // TODO: 22.01.2016 może tu dostać nulla, obsłużyć 
        stream = new ArffFileStream(streamFilePath, -1);
        stream.prepareForUse();
    }

    public void prepareLearner() {
        learner = new HoeffdingTree();
        learner.setModelContext(stream.getHeader());
        learner.prepareForUse();
    }

    public Classifier learnOnStream() {

        while (stream.hasMoreInstances()) {//// TODO: 22.01.2016 usunąć

            Instance trainInst = stream.nextInstance();
            Log.d("moa", "class value = " + trainInst.classValue());
            learner.trainOnInstance(trainInst);
            /*
            Method threw 'java.lang.NullPointerException' exception. Cannot evaluate moa.classifiers.trees.HoeffdingTree$LearningNodeNBAdaptive.toString()

             */

        }

        Log.d("ML", "Learning done!");
        return learner;
    }

    public double testLearningAccuracy() {
        int numberSamplesCorrect = 0;
        int numberSamples = 0;
        long numInstances = stream.estimatedRemainingInstances(); //todo: możliwe źródło błędów

        while ((stream.hasMoreInstances()) && (numberSamples < numInstances)) {
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


}

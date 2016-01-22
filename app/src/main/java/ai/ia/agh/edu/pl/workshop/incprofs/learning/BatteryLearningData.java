package ai.ia.agh.edu.pl.workshop.incprofs.learning;


import java.util.Random;

import ai.ia.agh.edu.pl.workshop.incprofs.sensors.BatteryData;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by Rael on 21.01.2016.
 */
public class BatteryLearningData {

    // Pola


    int numOfAttributes = 4;

    // todo: możliwe źródło błędu


    //todo: wrzucić to do res/string?
    String batteryLevelLabel = "BatteryLevel";
    String batteryPercentageLabel = "BatteryPercentage";
    String timeStampLabel = "TimeStamp";
    String instancesLabel = "BatteryData";

    BatteryData batteryData;

    FastVector<Attribute> fvWekaAttributes;
    Instances instances;
    int numInstances = 5000;
    int initialCapacity = numInstances;

    //todo: ujednolicić sposób przekazywania danych i odpowiadających im indeksów w tabeli/labeli w danych uczących

    //todo: ujednolicić kolejność - np. najpierw czas
    String[] attributeLabels = {batteryLevelLabel, batteryPercentageLabel, timeStampLabel};

    public BatteryLearningData(BatteryData batteryData) {

        this.fvWekaAttributes = new FastVector<>(numOfAttributes);
        for (String attributeLabel : attributeLabels) {
            fvWekaAttributes.add(new Attribute(attributeLabel));
        }
        this.instances = new Instances(instancesLabel, fvWekaAttributes, initialCapacity);
        instances.setClassIndex(0); // todo: dane potrzebują dodatkowego pola z wynikiem uczenia/klasyfikacji -> tj. profilem
        this.batteryData = batteryData;
    }

    public Instance getNextInstance() {
        try {
            batteryData = batteryData.getNextBatteryDataInstance();
            if (batteryData != null) {
                Instance newInstance = new DenseInstance(numOfAttributes);

                //todo: tymczasowe dodawanie klas do danych
                Random r = new Random();
                int Low = 1;
                int High = 5;
                int randomClass = r.nextInt(High - Low) + Low;


                //todo: jakoś to skrócić
                newInstance.setValue(fvWekaAttributes.elementAt(0), batteryData.getBatteryLevelValue());
                newInstance.setValue(fvWekaAttributes.elementAt(1), batteryData.getBatteryPercentageValue());
                newInstance.setValue(fvWekaAttributes.elementAt(2), batteryData.getTimeStampValue());
                newInstance.setValue(fvWekaAttributes.elementAt(3), randomClass);
                return newInstance;
            } else {
                return null;
            }
        } catch (Exception e) {
            //// TODO: 22.01.2016 obsłużyć
            return null;
        }


    }

    public Instances getInstances() {

        Instance instance = getNextInstance(); //todo: wziąć pod uwagę ograniczenia pamięciowe

        while (instance != null) // && prev != instance
        {
            instances.add(instance);
            instance = getNextInstance();
        }
        return instances;
    }


}

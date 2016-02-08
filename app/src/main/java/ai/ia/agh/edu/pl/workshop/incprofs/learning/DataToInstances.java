package ai.ia.agh.edu.pl.workshop.incprofs.learning;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;

/**
 * Created by Rael on 04.02.2016.
 * Klasa ma pobierać dane z ostatnich 20 minut (co 20 minut?)
 */
public class DataToInstances {

    HashMap<String, Object> sensorData;
    FastVector<Attribute> fvWekaAttributes;
    Instances instances;
    List<String> attributeLabels = new LinkedList<>();

    /*
    void sensorDataToInstance(HashMap<String, Object> sensorData) {
        this.sensorData = sensorData;

        for (Map.Entry<String, Object> entry : sensorData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            attributeLabels.add(key);


        }

        this.fvWekaAttributes = new FastVector<>(numOfAttributes);
        for (String attributeLabel : attributeLabels) {
            fvWekaAttributes.add(new Attribute(attributeLabel));
        }
        this.instances = new Instances(instancesLabel, fvWekaAttributes, initialCapacity);
        instances.setClassIndex(0); // todo: dane potrzebują dodatkowego pola z wynikiem uczenia/klasyfikacji -> tj. profilem
        this.batteryData = batteryData;


    }
    */
}

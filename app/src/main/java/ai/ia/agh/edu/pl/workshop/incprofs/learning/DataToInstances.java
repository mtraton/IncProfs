package ai.ia.agh.edu.pl.workshop.incprofs.learning;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by Rael on 04.02.2016.
 * Klasa ma pobierać dane z ostatnich 20 minut (co 20 minut?)
 */
@SuppressWarnings("deprecation")
public class DataToInstances {

    private static String instancesLabel = "SensorData";
    private static int initialCapacity = 1; // todo:
    Instances newInstances;

    ArrayList<Attribute> attributes;

    int numOfAttributes;
    int currentAttributeNum;

    LinkedHashMap<String, Object> sensorData;

    Instance newInstance;

    public DataToInstances(LinkedHashMap<String, Object> sensorData)
    {
        currentAttributeNum = 0;
        this.sensorData = sensorData;

    }
    //todo: czy data nie lepiej repreztować jako date?
    // todo: czy nie prościej robić zapis do pliku ARFF i parsowanie
    // ewentualnie upewnić sę że atrrNumer nie jest przypadkowym hashem
    public void createWekaAttributes() {
         if (sensorData != null) {
            numOfAttributes = sensorData.entrySet().size();

             // atts.addElement(new Attribute("att5", dataRel, 0));
             //atts.addElement(new Attribute("att3", (FastVector) null));
             //attsRel.addElement(new Attribute("att5.1"));

            this.attributes = new ArrayList<>(numOfAttributes); // todo: możliwe źródło błędu
            int attrNumber = 0;
            for (Map.Entry<String, Object> entry : sensorData.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                //
                if (value != null) {
                    if (value instanceof String) {
                        attributes.add(new Attribute(key, (List<String>) null, attrNumber));//żeby stworzyć atrybut typu String trzeba się posłużyć taką konstrukcją
                    }
                    //musi i tak przekonwertować na double
                    else { //todo: możliwe źródło błędu
                        attributes.add(new Attribute(key, attrNumber));//
                    }

                }
                else // todo: obsługa gdy dane są puste
                {
                    attributes.add(new Attribute(""));
                    Log.d("IO", "null in sensor data at attribute creation");
                }
                attrNumber++;
            }
        }
    }





    public Instances sensorDataToInstance() {

        //if (sensorData != null) {

            createWekaAttributes();
            Instances data = new Instances("ProfileLearning", attributes, 0);
            double vals [] = new double[data.numAttributes()];
            //newInstance = new DenseInstance(numOfAttributes);

            currentAttributeNum = 0;
            for (Map.Entry<String, Object> entry : sensorData.entrySet()) {

                Object value = entry.getValue();


                //todo: czy nie wystarczy samo currentatt numbe?
                if (value != null) {
                    if (value instanceof String) {
                        String stringValue = (String) value;
                        //int attIndex = attributes.get(currentAttributeNum).index();
                        //newInstance.setValue(attributes.get(currentAttributeNum), stringValue);
                        // konwertuje tutaj przy ustawianiu string na double = 0
                        vals[currentAttributeNum] = data.attribute(currentAttributeNum).addStringValue(stringValue);
                    }
                    //musi i tak przekonwertować na double
                    else { //todo: możliwe źródło błędu

                        double doubleValue = (double) value; // WTF wcześniej działało
                        vals[currentAttributeNum] = doubleValue;

                        //int attIndex = attributes.get(currentAttributeNum).index();
                        //newInstance.setValue(attributes.get(currentAttributeNum), doubleValue);
                    }


                } else {
                    Log.d("IO", "Sensor data part is null"); // // TODO: 08.02.2016  co jeśli dana jest nullem
                }
                currentAttributeNum++;

            }
            data.add(new DenseInstance(1.0, vals)); // todo: możliwe źródło błędu




        //todo: stringi reprezentowane jako zera - nie jest dobrze
        return data;
    }

}

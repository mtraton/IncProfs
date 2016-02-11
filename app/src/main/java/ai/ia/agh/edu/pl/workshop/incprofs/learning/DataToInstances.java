package ai.ia.agh.edu.pl.workshop.incprofs.learning;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ai.ia.agh.edu.pl.workshop.incprofs.sensors.Profiles;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.Utils;

/**
 * Created by Rael on 04.02.2016.
 * LinkedHashMap (dane z sensorów) -> Instances
 */


public class DataToInstances {

    //  Instances
    Instances newInstances;
    private static String instancesLabel = "SensorData";
    private static int initialCapacity = 1;

    // Attributes
    ArrayList<Attribute> attributes;
    int numOfAttributes;
    int currentAttributeNum;

    Profiles profiles;
    ArrayList<String> profileNames;

    // Dane wejściowe
    LinkedHashMap<String, Object> sensorData;

    public DataToInstances(LinkedHashMap<String, Object> sensorData)
    {
        currentAttributeNum = 0;
        this.sensorData = sensorData;
        profiles = new Profiles();
        profileNames= new ArrayList<String>(profiles.getProfilesMap().values());

    }

    public void createWekaAttributes() {
         if (sensorData != null) {

            numOfAttributes = sensorData.entrySet().size();
            this.attributes = new ArrayList<>(numOfAttributes);
            int attrNumber = 0;

            for (Map.Entry<String, Object> entry : sensorData.entrySet()) {

                String key = entry.getKey();
                Object value = entry.getValue();

                if (value != null) {
                    if (value instanceof String) {
                        if(key.equals("profile"))
                        {

                            attributes.add(new Attribute(key, profileNames, attrNumber));
                            Log.d("IO", "nominal");
                        }
                        else{
                            //żeby stworzyć atrybut typu String trzeba się posłużyć taką konstrukcją
                            attributes.add(new Attribute(key, (List<String>) null, attrNumber));
                        }


                    }
                    else if (value instanceof Date)
                    {
                        attributes.add(new Attribute(key,"yyyy-MM-dd HH:mm:ss", attrNumber));//todo:możliwe źródło błędów
                    }
                   // jeśli dana nie jest żadnym z powyższych typów to musi być doublem -> atrybutem typu numeric
                    else {
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
            data.setClassIndex(numOfAttributes - 1);// TODO możliwe źródło błędów
        
            double vals [] = new double[data.numAttributes()];
            //newInstance = new DenseInstance(numOfAttributes);

            currentAttributeNum = 0;
            for (Map.Entry<String, Object> entry : sensorData.entrySet()) {

                Object value = entry.getValue();
                String key = entry.getKey();

               
                if (value != null) {
                    if (value instanceof String) {
                        if(key.equals("profile"))
                        {
                            // nominal attribute
                            vals[currentAttributeNum] = profileNames.indexOf(value);
                        }
                        else
                        {
                            String stringValue = (String) value;

                            //int attIndex = attributes.get(currentAttributeNum).index();
                            //newInstance.setValue(attributes.get(currentAttributeNum), stringValue);
                            // konwertuje tutaj przy ustawianiu string na double = 0

                            vals[currentAttributeNum] = data.attribute(currentAttributeNum).addStringValue(stringValue);

                        }

                    }
                    else if (value instanceof Date)
                    {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // the format of your date
                        //sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
                        String formattedDate = sdf.format(date);


                        try {
                            vals[currentAttributeNum] = data.attribute(currentAttributeNum).parseDate( formattedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    //musi i tak przekonwertować na double
                    else {

                        double doubleValue = (double) value;
                        vals[currentAttributeNum] = doubleValue;

                        //int attIndex = attributes.get(currentAttributeNum).index();
                        //newInstance.setValue(attributes.get(currentAttributeNum), doubleValue);
                    }



                } else {
                    Log.d("IO", "Sensor data part is null"); // // TODO: 08.02.2016  co jeśli dana jest nullem
                    vals[currentAttributeNum] = Utils.missingValue();
                }
                currentAttributeNum++;

            }
            data.add(new DenseInstance(1.0, vals)); // todo: możliwe źródło błędu




        //todo: stringi reprezentowane jako zera - nie jest dobrze
        return data;
    }

}

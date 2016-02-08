package ai.ia.agh.edu.pl.workshop.incprofs.learning;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;

/**
 * Cogito ergo sum
 * Created by Rael on 04.02.2016.
 */

// Klasa przechowująca jedną próbkę wszystkich danych z sensorów do nauki
@SuppressWarnings("deprecation")
public class LearningData {


    //todo: czy nie byłoby wygodniej to reprezentowac jako hashmape


    String batteryLevelLabel = "BatteryLevel";
    String batteryPercentageLabel = "BatteryPercentage";
    String timeStampLabel = "TimeStamp";
    String instancesLabel = "SensorsData";

    //todo: applications
    FastVector<Attribute> fvWekaAttributes;
    Instances instances;
    int numInstances = 5000;
    int initialCapacity = numInstances;
    //todo: ujednolicić kolejność - np. najpierw czas
    String[] attributeLabels = {batteryLevelLabel, batteryPercentageLabel, timeStampLabel};
    // GPS data
    //  Data precision - You should not use float for lat/lon if you need precision down to sub-feet.
    private float longitude; // długość
    private float latitude; // szerokość
    private String activityName;
    private int activityType;
    private float timestamp; //todo: czy nie będzie wygodniej korzystać z daty? na potrzeby uczenia nie, na potrzeby wyświetlania tak
    private String networkSSID; // todo: 1) SSID nie jest chyba unikalny 2) jest tekstowy 3)
    private int batteryLevel;

    public LearningData(float longitude, float latitude, String activityName, int activityType, float timestamp, String networkSSID, int batteryLevel) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.activityName = activityName;
        this.activityType = activityType;
        this.timestamp = timestamp;
        this.networkSSID = networkSSID;
        this.batteryLevel = batteryLevel;
    }

    /*
    public LearningData(BatteryData batteryData) {

        this.fvWekaAttributes = new FastVector<>(numOfAttributes);
        for (String attributeLabel : attributeLabels) {
            fvWekaAttributes.add(new Attribute(attributeLabel));
        }
        this.instances = new Instances(instancesLabel, fvWekaAttributes, initialCapacity);
        instances.setClassIndex(0); // todo: dane potrzebują dodatkowego pola z wynikiem uczenia/klasyfikacji -> tj. profilem
        this.batteryData = batteryData;
    }
    */
    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    //todo: jak zrobić inicjalizację poprzednimi wartościami?
    // 1) przechowywać to wszystko w tablicy/liście, i przy dodawaniu nowego elementu skopiować stary i uzupełnić
    // 2) przekazywać stary i odpalać ustawienei nowej wartości

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public float getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(float timestamp) {
        this.timestamp = timestamp;
    }

    public String getNetworkSSID() {
        return networkSSID;
    }

    public void setNetworkSSID(String networkSSID) {
        this.networkSSID = networkSSID;
    }

    //todo: ujednolicić sposób przekazywania danych i odpowiadających im indeksów w tabeli/labeli w danych uczących

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
    /*
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
    */

}



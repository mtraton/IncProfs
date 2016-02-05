package ai.ia.agh.edu.pl.workshop.incprofs.learning;

/**
 * Created by Rael on 04.02.2016.
 */

// Klasa przechowująca jedną próbkę wszystkich danych z sensorów do nauki
public class LearningData {


    //todo: czy nie byłoby wygodniej to reprezentowac jako hashmape


    // GPS data
    //  Data precision - You should not use float for lat/lon if you need precision down to sub-feet.
    private float longitude; // długość
    private float latitude; // szerokość

    private String activityName;
    private int activityType;

    //todo: applications

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

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
    //todo: jak zrobić inicjalizację poprzednimi wartościami?
    // 1) przechowywać to wszystko w tablicy/liście, i przy dodawaniu nowego elementu skopiować stary i uzupełnić
    // 2) przekazywać stary i odpalać ustawienei nowej wartości

}

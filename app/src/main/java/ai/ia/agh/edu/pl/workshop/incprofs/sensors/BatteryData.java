package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import android.database.Cursor;

/**
 * Created by Rael on 21.01.2016.
 */
public class BatteryData {

    // todo: poddać dane wstępnej obróbce

    int batteryLevelValue;
    float batteryPercentageValue;
    int batteryScale;
    float timeStampValue;

    String timeStampColumnIndex = "timestamp";
    String batteryLevelColumnIndex = "battery_level";
    String batteryScaleColumnIndex = "battery_scale";
    private Cursor batterySensorData;

    public BatteryData(Cursor sensorData) {
        this.batterySensorData = sensorData;
    }

    public BatteryData(int batteryLevelValue, float batteryPercentageValue, int batteryScale, float timeStampValue, Cursor sensorData) {
        this.batteryLevelValue = batteryLevelValue;
        this.batteryPercentageValue = batteryPercentageValue;
        this.batteryScale = batteryScale;
        this.timeStampValue = timeStampValue;
        this.batterySensorData = sensorData;
    }

    public int getBatteryLevelValue() {
        return batteryLevelValue;
    }

    public void setBatteryLevelValue(int batteryLevelValue) {
        this.batteryLevelValue = batteryLevelValue;
    }

    public float getBatteryPercentageValue() {
        return batteryPercentageValue;
    }

    public void setBatteryPercentageValue(float batteryPercentageValue) {
        this.batteryPercentageValue = batteryPercentageValue;
    }

    public int getBatteryScale() {
        return batteryScale;
    }

    public void setBatteryScale(int batteryScale) {
        this.batteryScale = batteryScale;
    }

    public float getTimeStampValue() {
        return timeStampValue;
    }

    public void setTimeStampValue(float timeStampValue) {
        this.timeStampValue = timeStampValue;
    }

    public void gatherData() {

    }

    public BatteryData getNextBatteryDataInstance() throws Exception {
        //// TODO: 21.01.2016 zastananowić się czy ta klasa nie powinna tylko zawierać danych

        if (batterySensorData.moveToNext()) {
            batteryLevelValue = batterySensorData.getInt(batterySensorData.getColumnIndex(batteryLevelColumnIndex));
            batteryScale = batterySensorData.getInt(batterySensorData.getColumnIndex(batteryLevelColumnIndex));// todo: to nie jest 100% baterii!
            batteryPercentageValue = (float) (batteryLevelValue / batteryScale) * 100;

            timeStampValue = batterySensorData.getFloat(batterySensorData.getColumnIndex(timeStampColumnIndex));


            return new BatteryData(batteryLevelValue, batteryPercentageValue, batteryScale, timeStampValue, batterySensorData);
        } else {
            //todo: czy to jest poprawne?

            throw new Exception("cannot get any more data");
        }


    }

}

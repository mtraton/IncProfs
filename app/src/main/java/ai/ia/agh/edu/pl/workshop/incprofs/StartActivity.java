package ai.ia.agh.edu.pl.workshop.incprofs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.providers.Accelerometer_Provider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingTree;
import moa.streams.ArffFileStream;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class StartActivity extends Activity {

    public static final String NAME = "name";
    public static final String SURNAME = "surname";


    //todo: niech wykrywa dziad przed uczneiem, że nie ma się z czego uczyć!!!
    /*
    private ScreenObserver so;
    private GPSObserver gpso;
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		/*Setting the layout*/
        setContentView(R.layout.activity_start);

        Log.d("START", "On create activity!");




        //todo: public void startupAccelerometer(); itd. z inyymi sensorami
        //Activate Accelerometer




        /*
        //Activate Battery
        Log.d("sensors", "Activate Battery");
        Aware.setSetting(this, Aware_Preferences.STATUS_BATTERY, true);
        //Apply settings
        Log.d("sensors", "Apply Battery settings");
        Aware.startSensor(this, Aware_Preferences.STATUS_BATTERY);
        */
        // todo: poddać dane wstępnej obróbce
        // np. dane z GPS - lokacje za
        // aplikacje z których korzysta
        // lokalizacja
        // aktywność użytkownika
        // pora dnia - timestamp -> uważać na strefę czasową!
        // wifi - ssid? - silnie skorelowane z lokalizacją - zastanowić się czy jest sens
        // bateria
        // todo uzupełnić stronę wiki, problemy, założenia,
        //
        /*
        //Activate GPS
        Log.d("sensors", "Activate GPS");
        Aware.setSetting(this, Aware_Preferences.STATUS_LOCATION_GPS, true);st
        //Set GPS freq
        Log.d("sensors", "Set GPS sampling frequency");
        Aware.setSetting(this, Aware_Preferences.FREQUENCY_LOCATION_GPS, 60);

        //Aware.setSetting(this, Aware_Preferences.MIN_LOCATION_GPS_ACCURACY, 5);

        //Aware.setSetting(this, Aware_Preferences.LOCATION_EXPIRATION_TIME, 10);

        //Apply settings
        Log.d("sensors", "Apply GPS settings");
        Aware.startSensor(this, Aware_Preferences.STATUS_LOCATION_GPS);
        */

/*
		//Activate GPS2
		Log.d("sensors", "Activate GPSNet");
		Aware.setSetting(this, Aware_Preferences.STATUS_LOCATION_NETWORK, true);
		//Set GPS freq
		Log.d("sensors", "Set GPSNet sampling frequency");
		Aware.setSetting(this, Aware_Preferences.FREQUENCY_LOCATION_NETWORK, 10);
		//Apply settings
		Log.d("sensors", "Apply GPSNet settings");
		Aware.startSensor(this, Aware_Preferences.STATUS_LOCATION_NETWORK);
*/

/*
		//Activate WIFI
		Log.d("sensors", "Activate WIFI");
		Aware.setSetting(this, Aware_Preferences.STATUS_WIFI, true);
		//Set GPS freq
		//Log.d("sensors", "Set WIFI sampling frequency");
		//Aware.setSetting(this, Aware_Preferences.FREQUENCY_WIFI, 60);
		//Apply settings
		Log.d("sensors", "Apply WIFI settings");
		Aware.startSensor(this, Aware_Preferences.STATUS_WIFI);
*/






        /*
        //Activate screen
        Log.d("sensors", "Activate Screen");
        Aware.setSetting(this, Aware_Preferences.STATUS_SCREEN, true);
        //Apply settings
        Log.d("sensors", "Apply Screen settings");
        Aware.startSensor(this, Aware_Preferences.STATUS_SCREEN);

        //Activate applications
        Log.d("sensors", "Activate Apps");
        Aware.setSetting(this, Aware_Preferences.STATUS_APPLICATIONS, true);
        //Apply settings
        Log.d("sensors", "Apply Apps settings");
        Aware.startSensor(this, Aware_Preferences.STATUS_APPLICATIONS);
        */

        /* todo:
        Wrzucić każdy sensor do osobnej klasy i dopisać jakiś intefejs?


        */

//OBSERVER!
        /*
        this.so = new ScreenObserver(new Handler(), getApplicationContext());
        getContentResolver().registerContentObserver(Screen_Provider.Screen_Data.CONTENT_URI, true, this.so);


        this.gpso = new GPSObserver(new Handler(), getApplicationContext());
        getContentResolver().registerContentObserver(Locations_Provider.Locations_Data.CONTENT_URI, true, this.gpso);
        */
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        /*(getContentResolver().unregisterContentObserver(this.so);
        getContentResolver().unregisterContentObserver(this.gpso);
        */
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    /**
     * Called by onClick event of go_button
     * @param view
     */
    public void startQuiz(View view) throws IOException {


        Log.d("sensors", "BUTTON PRESSED!!!!");


        Cursor accelerometer_data = getContentResolver().query(Accelerometer_Provider.Accelerometer_Data.CONTENT_URI, null, null, null, "accuracy ASC");
        /*
        Cursor battery_charges = getContentResolver().query(Battery_Provider.Battery_Charges.CONTENT_URI, null, null, null, "_id ASC");

        Cursor battery_dis = getContentResolver().query(Battery_Provider.Battery_Discharges.CONTENT_URI, null, null, null, "_id ASC");

        Cursor GPS = getContentResolver().query(Locations_Provider.Locations_Data.CONTENT_URI, null, null, null, "_id ASC");

        Cursor WIFI = getContentResolver().query(WiFi_Provider.WiFi_Sensor.CONTENT_URI, null, null, null, "_id ASC");

        Cursor Screen = getContentResolver().query(Screen_Provider.Screen_Data.CONTENT_URI, null, null, null, "_id ASC");

        Cursor Apps = getContentResolver().query(Applications_Provider.Applications_Foreground.CONTENT_URI, null, null, null, "_id ASC");
        Cursor Apps2 = getContentResolver().query(Applications_Provider.Applications_History.CONTENT_URI, null, null, null, "_id ASC");
        */

    /*
        int i = 0;
        Log.d("sensors", "iterator: " + i);




       */


        String accelerometerData;
        StringBuilder dataBuilder = new StringBuilder();





        String csvSeparator = ",";

		//
        /*
        //nagłówek pliku csv
        dataBuilder.append(AxisXLabel);
        dataBuilder.append(csvSeparator);
        dataBuilder.append(AxisYLabel);
        dataBuilder.append(csvSeparator);
        dataBuilder.append(AxisZLabel);
        dataBuilder.append("\n");
        */


        String AxisXLabel = "AxisX";
        String AxisYLabel = "AxisY";
        String AxisZLabel = "AxisZ";

        Attribute Attribute1 = new Attribute(AxisXLabel);
        Attribute Attribute2 = new Attribute(AxisYLabel);
        Attribute Attribute3 = new Attribute(AxisZLabel);

        FastVector fvWekaAttributes = new FastVector(3);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(Attribute2);
        fvWekaAttributes.addElement(Attribute3);


        int numInstances = 5000;
        int initialCapacity = numInstances;
        Instances isTrainingSet = new Instances("AccelerometerData", fvWekaAttributes, initialCapacity);
        isTrainingSet.setClassIndex(0);


        String AxisXName = "double_values_0";
        String AxisYName = "double_values_1";
        String AxisZName = "double_values_2";


        float axisX;
        float axisY;
        float axisZ;

        int i = 0;


        // Zbierz informacje z sensorów do tablicy/listy/stringu
        while (accelerometer_data.moveToNext() && (i < numInstances) ) {
            i++;
            Log.d("sensors", "iterator: " + i);





            axisX = accelerometer_data.getFloat(accelerometer_data.getColumnIndex(AxisXName));
            axisY = accelerometer_data.getFloat(accelerometer_data.getColumnIndex(AxisYName));
            axisZ = accelerometer_data.getFloat(accelerometer_data.getColumnIndex(AxisZName));

            // todo: możliwe źródło błędu
            Instance iExample = new DenseInstance(3);
            iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), axisX);
            iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), axisY);
            iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), axisZ);

            // add the instance
            isTrainingSet.add(iExample);

            /*
            dataBuilder.append(axisX);
            dataBuilder.append(",");
            dataBuilder.append(axisY);
            dataBuilder.append(",");
            dataBuilder.append(axisZ);
            dataBuilder.append("\n");
            */

            /*
            tmp = accelerometer_data.getString(accelerometer_data.getColumnIndex(firstAxis));
			debug.append(tmp);
			debug.append(" ");

			tmp = accelerometer_data.getString(accelerometer_data.getColumnIndex("double_values_1"));
			debug.append(tmp);
			debug.append(" ");

			tmp = accelerometer_data.getString(accelerometer_data.getColumnIndex("double_values_2"));
			debug.append(tmp);
			debug.append(" ");

			Log.d("sensors", "txt: " + debug.toString());
			*/
        }

        //public void createARFFFile() {
        // Declare two numeric attributes


        /*
        // Declare a nominal attribute along with its values
        FastVector fvNominalVal = new FastVector(3);
        fvNominalVal.addElement(“blue”);
        fvNominalVal.addElement(“gray”);
        fvNominalVal.addElement(“black”);
        Attribute Attribute3 = new Attribute(“aNominal”, fvNominalVal);
        */

        // Declare the class attribute along with its values
        /*
        FastVector fvClassVal = new FastVector(2);
        fvClassVal.addElement(“positive”);
        fvClassVal.addElement(“negative”);
        Attribute ClassAttribute = new Attribute(“theClass”, fvClassVal);
        */

        // Declare the feature vector

        // Set class index


        /*
            accelerometerData = dataBuilder.toString();
            StringReader stringReader = new StringReader(accelerometerData);

            Log.d("moa", accelerometerData);
            // tymczasowy plik

            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            writer.write(accelerometerData);
            writer.close();

            CSVLoader loader = new CSVLoader();
            loader.setSource(tempFile);
            Instances data = loader.getDataSet();// błąd?
            FastVector atts = new FastVector();

            ;
            ArffSaver  saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(tempFile);
           // saver.setDestination(tempFile);
            saver.writeBatch();

        */
        File tempFile = File.createTempFile("arffData", ".arff");
        String tempFilePath = tempFile.getAbsolutePath(); // todo: possible error
        tempFile.deleteOnExit();
        /*
        Instances dataSet = isTrainingSet;
        ArffSaver saver = new ArffSaver();
        saver.setInstances(dataSet);
        saver.setFile(tempFile);
        //saver.setDestination(new File("./data/test.arff"));   // **not** necessary in 3.5.4 and later
        saver.writeBatch();
        */
        // todo: zamienić
        Instances dataSet = isTrainingSet;
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFilePath));
        writer.write(dataSet.toString());
        writer.flush();
        writer.close();

        Classifier learner = new HoeffdingTree();

        ArffFileStream stream = new ArffFileStream(tempFilePath, -1); // todo: zmienić -1 na stałą nazwaną
        stream.prepareForUse();

        learner.setModelContext(stream.getHeader());
        learner.prepareForUse();

        int numberSamplesCorrect = 0;
        int numberSamples = 0;
        boolean isTesting = true;
        while (stream.hasMoreInstances() && numberSamples < numInstances) {
            Instance trainInst = stream.nextInstance();
            if (isTesting) {
                if (learner.correctlyClassifies(trainInst)) {
                    numberSamplesCorrect++;
                }
            }
            numberSamples++;
            learner.trainOnInstance(trainInst);
            if (isTesting) {
                ;
            }
        }
        double accuracy = 100.0 * (double) numberSamplesCorrect / (double) numberSamples;
        if (true) {
            Log.d("TAG", "Test data calculated! " + accuracy);
        }









		/*
		i=0;
		while (battery_charges.moveToNext()) {
			i++;
			Log.d("sensors", "iterator: " + i);

			String tmp;
			StringBuilder debug = new StringBuilder();


			tmp = battery_charges.getString(battery_charges.getColumnIndex("_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = battery_charges.getString(battery_charges.getColumnIndex("timestamp"));
			debug.append(tmp);
			debug.append(" ");

			tmp = battery_charges.getString(battery_charges.getColumnIndex("device_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = battery_charges.getString(battery_charges.getColumnIndex("battery_start"));
			debug.append(tmp);
			debug.append(" ");

			tmp = battery_charges.getString(battery_charges.getColumnIndex("battery_end"));
			debug.append(tmp);
			debug.append(" ");

			tmp = battery_charges.getString(battery_charges.getColumnIndex("double_end_timestamp"));
			debug.append(tmp);
			debug.append(" ");

			Log.d("sensors", "txt: " + debug.toString());

long test = battery_charges.getLong(battery_charges.getColumnIndex("double_end_timestamp")) - battery_charges.getLong(battery_charges.getColumnIndex("timestamp"));

			Log.d("sensors", "timestamp diff: " + test);

			//battery_charges.getLong();
		}
		*/

        /*
		int i=0; // z dupy
		while (battery_dis.moveToNext()) { // przejdz do nastepnej probki
			i++;
			Log.d("sensors", "iterator: " + i);

			String tmp;
			StringBuilder debug = new StringBuilder();


			tmp = battery_dis.getString(battery_dis.getColumnIndex("_id"));
			debug.append(tmp);
			debug.append("");

            //todo:
            /*
            1) pobrać longa
            Date date = new Date();
             date.setTime((long) WIFI.getLong(WIFI.getColumnIndex("timestamp")) );
             Log.d("sensors", "timestamp: " + date.toString());

             */
            /*
            long startDate = battery_dis.getLong(battery_dis.getColumnIndex("timestamp"));
			tmp = battery_dis.getString(battery_dis.getColumnIndex("timestamp"));
			debug.append(tmp);
			debug.append(" ");

			tmp = battery_dis.getString(battery_dis.getColumnIndex("device_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = battery_dis.getString(battery_dis.getColumnIndex("battery_start"));
			debug.append(tmp);
			debug.append(" ");

			tmp = battery_dis.getString(battery_dis.getColumnIndex("battery_end"));
			debug.append(tmp);
			debug.append(" ");

			tmp = battery_dis.getString(battery_dis.getColumnIndex("double_end_timestamp"));
			debug.append(tmp);
			debug.append(" ");

			Log.d("sensors", "txt: " + debug.toString());

			long test = battery_dis.getLong(battery_dis.getColumnIndex("double_end_timestamp")) - battery_dis.getLong(battery_dis.getColumnIndex("timestamp"));

			Log.d("sensors", "timestamp diff: " + test);

			//battery_dis.getLong();
		}
        */

/*
		i=0;
		while (GPS.moveToNext()) {
			i++;
			Log.d("sensors", "iterator: " + i);

			String tmp;
			StringBuilder debug = new StringBuilder();


			tmp = GPS.getString(GPS.getColumnIndex("_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("timestamp"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("device_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("double_latitude"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("double_longitude"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("double_bearing"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("double_speed"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("double_altitude"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("provider"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("accuracy"));
			debug.append(tmp);
			debug.append(" ");

			tmp = GPS.getString(GPS.getColumnIndex("label"));
			debug.append(tmp);
			debug.append(" ");


			Log.d("sensors", "txt: " + debug.toString());

		}
*/

        /*
        i=0;
        while (WIFI.moveToNext()) {
            i++;
            Log.d("sensors", "iterator: " + i);

            String tmp;
            StringBuilder debug = new StringBuilder();


            tmp = WIFI.getString(WIFI.getColumnIndex("_id"));
            debug.append(tmp);
            debug.append(" ");

            tmp = WIFI.getString(WIFI.getColumnIndex("timestamp"));
            debug.append(tmp);
            debug.append(" ");

            tmp = WIFI.getString(WIFI.getColumnIndex("device_id"));
            debug.append(tmp);
            debug.append(" ");

            tmp = WIFI.getString(WIFI.getColumnIndex("mac_address"));
            debug.append(tmp);
            debug.append(" ");

            tmp = WIFI.getString(WIFI.getColumnIndex("bssid"));
            debug.append(tmp);
            debug.append(" ");

            tmp = WIFI.getString(WIFI.getColumnIndex("ssid"));
            debug.append(tmp);
            debug.append(" ");


            Log.d("sensors", "txt: " + debug.toString());


            Date date = new Date();
            date.setTime((long) WIFI.getLong(WIFI.getColumnIndex("timestamp")) );
            Log.d("sensors", "timestamp: " + date.toString());
        }
        */


/*
		i=0;
		while (Screen.moveToNext()) {
			i++;
			Log.d("sensors", "iterator: " + i);

			String tmp;
			StringBuilder debug = new StringBuilder();


			tmp = Screen.getString(Screen.getColumnIndex("_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Screen.getString(Screen.getColumnIndex("timestamp"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Screen.getString(Screen.getColumnIndex("device_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Screen.getString(Screen.getColumnIndex("screen_status"));
			debug.append(tmp);
			debug.append(" ");

			Log.d("sensors", "txt: " + debug.toString());
		}
*/

/*
		i=0;
		while (Apps.moveToNext()) {
			i++;
			Log.d("sensors", "iterator: " + i);

			String tmp;
			StringBuilder debug = new StringBuilder();


			tmp = Apps.getString(Apps.getColumnIndex("_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Apps.getString(Apps.getColumnIndex("timestamp"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Apps.getString(Apps.getColumnIndex("device_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Apps.getString(Apps.getColumnIndex("package_name"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Apps.getString(Apps.getColumnIndex("application_name"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Apps.getString(Apps.getColumnIndex("is_system_app"));
			debug.append(tmp);
			debug.append(" ");


			Log.d("sensors", "txt: " + debug.toString());
		}

		*/


		/*
		i=0;
		while (Apps2.moveToNext()) {
			i++;
			Log.d("sensors", "iterator: " + i);

			String tmp;
			StringBuilder debug = new StringBuilder();


			tmp = Apps2.getString(Apps2.getColumnIndex("_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Apps2.getString(Apps2.getColumnIndex("timestamp"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Apps2.getString(Apps2.getColumnIndex("device_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Apps2.getString(Apps2.getColumnIndex("package_name"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Apps2.getString(Apps2.getColumnIndex("application_name"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Apps2.getString(Apps2.getColumnIndex("process_importance"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Apps2.getString(Apps2.getColumnIndex("process_id"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Apps2.getString(Apps2.getColumnIndex("end_timestamp"));
			debug.append(tmp);
			debug.append(" ");

			tmp = Apps2.getString(Apps2.getColumnIndex("is_system_app"));
			debug.append(tmp);
			debug.append(" ");


			Log.d("sensors", "txt: " + debug.toString());
		}
*/



        Log.d("sensors", "!passed");

        accelerometer_data.close();
        /*
        battery_charges.close();
        battery_dis.close();
        GPS.close();
        WIFI.close();
        Screen.close();
        Apps.close();
        Apps2.close();
        */


    }

    public void initialiseAndStartAware() {
        //Initialise AWARE
        Log.d("sensors", "Initialise AWARE");
        Intent aware = new Intent(this, Aware.class);

        Log.d("sensors", "startService(aware)");
        startService(aware);
    }

    public void setupAndStartAccelerometer(){
        Log.d("sensors", "Activate Accelerometer");
        Aware.setSetting(this, Aware_Preferences.STATUS_ACCELEROMETER, false);
        //Set sampling frequency
        Log.d("sensors", "Set Acc sampling frequency");
        Aware.setSetting(this, Aware_Preferences.FREQUENCY_ACCELEROMETER, 200000);
        //Apply settings
        Log.d("sensors", "Apply Acc settings");
    }

    public void closeAccelerometer() {

    }

    public long getDataInstance() {

        return 0;
    }




    public void testMOA() {
        /*
        int numInstances = 10000;
        Classifier learner = new HoeffdingTree();
        ArffFileStream stream  = new ArffFileStream();

        instances.iterator();

        stream.prepareForUse();

        learner.setModelContext(stream.getHeader());
        learner.prepareForUse();

        int numberSamplesCorrect = 0;
        int numberSamples = 0;
        boolean isTesting = true;

        while (stream.hasMoreInstances() && numberSamples < numInstances) {
            Instance trainInst = stream.nextInstance();
            if (isTesting) {
                if (learner.correctlyClassifies(trainInst)) {
                    numberSamplesCorrect++;
                }
            }
            numberSamples++;
            learner.trainOnInstance(trainInst);
            if (isTesting) {
                ;
            }
        }

        double accuracy = 100.0 * (double) numberSamplesCorrect / (double) numberSamples;
        if (true) {
            Log.d("TAG", "Test data calculated!");
        }
    */
    }




}

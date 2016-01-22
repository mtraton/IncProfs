package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

/**
 * Created by Rael on 21.01.2016.
 */
public class AppSensor {
    //Activate applications
   /* Log.d("sensors", "Activate Apps");
    Aware.setSetting(this, Aware_Preferences.STATUS_APPLICATIONS, true);
    //Apply settings
    Log.d("sensors", "Apply Apps settings");
    Aware.startSensor(this, Aware_Preferences.STATUS_APPLICATIONS);
*/
/*

    Cursor Apps = getContentResolver().query(Applications_Provider.Applications_Foreground.CONTENT_URI, null, null, null, "_id ASC");
    Cursor Apps2 = getContentResolver().query(Applications_Provider.Applications_History.CONTENT_URI, null, null, null, "_id ASC");
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


}

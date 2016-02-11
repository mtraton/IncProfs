package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import android.content.Context;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Rael on 22.01.2016.
 */
public class Utils {

    public Date timeStampToDate(double timeStamp)
    {
        long t = (long) timeStamp;
        Date time = new java.util.Date(t);
        return time;
    }

    public static void showToast(String text, Context c)
    {
        Context context = c;
        CharSequence charSequence = text;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


}

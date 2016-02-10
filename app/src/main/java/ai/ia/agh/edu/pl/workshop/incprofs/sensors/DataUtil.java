package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import java.util.Date;

/**
 * Created by Rael on 22.01.2016.
 */
public class DataUtil {

    public Date timeStampToDate(double timeStamp)
    {
        long t = (long) timeStamp;
        Date time = new java.util.Date(t);
        return time;
    }

}

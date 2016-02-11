package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import java.util.LinkedHashMap;

/**
 *
 * Created by Rael on 10.02.2016.
 * Pomocnicza klasa przechowująca nazwy profili
 */
public class Profiles {
    String profileNames [] = {"morning", "work", "afterwork", "night"};

    public LinkedHashMap<Day, String> getProfilesMap() {
        return profilesMap;
    }

    public enum Day {
        MORNING, WORK, AFTERWORK, NIGHT
    }

    private LinkedHashMap<Day,String> profilesMap = new LinkedHashMap<>();

    public void createProfileMap()
    {
        int stringIndex = 0;
        for(Day d : Day.values())
        {
            profilesMap.put(d, profileNames[stringIndex]);
            stringIndex++;
        }
    }

    public Profiles()
    {
        createProfileMap();
    }

}

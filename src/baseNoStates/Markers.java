package baseNoStates;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/*
* These Markers' purpose is to let logback.xml filter the logs based
* on wich milstone the class belongs to. A marker is used as a parameter
* in each call to a logger object to make a log.
*/

public class Markers {
    public static final Marker MILESTONE_1 = MarkerFactory.getMarker("MILESTONE_1");
    public static final Marker MILESTONE_2 = MarkerFactory.getMarker("MILESTONE_2");
}

package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Concrete state of the State design pattern representing a propped door.
 * A door enters this alarm state when it was unlocked shortly, opened, and left open
 * for more than 10 seconds. Because the door is physically open, it cannot be locked
 * directly (locking an open door would be inconsistent). Once the door is physically
 * closed (close action), it automatically returns to the Locked state.
 */
public class Propped extends DoorState {
    private static final Logger logger = LoggerFactory.getLogger("baseNoStates.Propped");

    public Propped(Door door) {
        super(door);
        this.name = States.PROPPED;
    }

    @Override
    public void open(){
        // Ut will always be open since the state Propped is only attainable from having an UnlockedShortly door be open.
        String msg = "Can't open door " + door.getId() + " because it's already open";
        logger.info(Markers.MILESTONE_1, msg);

    }

    @Override
    public void close(){
        door.setClosed(true);
        door.setState(new Locked(door));
    }

    @Override
    public void lock(){
        door.setState(new Locked(door));
    }

    @Override
    public void unlock(){
        String msg = "Can't unlock door " + door.getId() + "because it's propped";
        logger.info(Markers.MILESTONE_1, msg);

    }
    @Override
    public void unlockShortly(){
        String msg = "Can't unlock shortly door " + door.getId() + "because it's propped";
        logger.info(Markers.MILESTONE_1, msg);
    }
}

package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Concrete State in the State design pattern representing a propped door alarm.
 * A door enters Propped automatically when it stays physically open past the 10-second
 * UnlockedShortly window. Because the door is physically open, lock, unlock, open, and
 * unlockShortly requests are ignored; only physically closing the door clears the alarm
 * and returns it to Locked.
 */
public class Propped extends DoorState {
  private static final Logger logger = LoggerFactory.getLogger(Propped.class);

  public Propped(Door door) {
    super(door);
    this.name = States.PROPPED;
  }

  @Override
  public void open() {
    logger.warn(Markers.MILESTONE_1, "Can't open door '{}' because it's propped and already open",
        door.getId());
  }

  @Override
  public void close() {
    door.setClosed(true);
    door.setState(new Locked(door));
    logger.info(Markers.MILESTONE_1, "Propped door '{}' closed and automatically locked",
        door.getId());
  }

  @Override
  public void lock() {
    // Cannot lock a propped door because it is physically open
    logger.warn(Markers.MILESTONE_1, "Can't lock door '{}' because it's propped (open)",
        door.getId());
  }

  @Override
  public void unlock() {
    logger.warn(Markers.MILESTONE_1, "Can't unlock door '{}' because it's propped",
        door.getId());
  }

  @Override
  public void unlockShortly() {
    logger.warn(Markers.MILESTONE_1, "Can't unlock shortly door '{}' because it's propped",
        door.getId());
  }
}
package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Concrete State in the State design pattern representing an unlocked door.
 * While unlocked, authorized users may freely open and close the door.
 * It can only transition to Locked when physically closed; attempting to lock an open
 * door or unlock an already unlocked door is rejected with a warning.
 */
public class Unlocked extends DoorState {
  private static final Logger logger = LoggerFactory.getLogger(Unlocked.class);

  public Unlocked(Door door) {
    super(door);
    this.name = States.UNLOCKED;
  }

  @Override
  public void open() {
    if (door.isClosed()) {
      door.setClosed(false);
      logger.info(Markers.MILESTONE_1, "Door '{}' opened", door.getId());
    } else {
      logger.warn(Markers.MILESTONE_1, "Can't open door '{}' because it's already open",
          door.getId());
    }
  }

  @Override
  public void close() {
    if (door.isClosed()) {
      logger.warn(Markers.MILESTONE_1, "Can't close door '{}' because it's already closed",
          door.getId());
    } else {
      door.setClosed(true);
      logger.info(Markers.MILESTONE_1, "Door '{}' closed", door.getId());
    }
  }

  @Override
  public void lock() {
    // Locking an open door would leave the system in an inconsistent physical state
    if (door.isClosed()) {
      door.setState(new Locked(door));
      logger.info(Markers.MILESTONE_1, "Door '{}' locked", door.getId());
    } else {
      logger.warn(Markers.MILESTONE_1, "Can't lock door '{}' because it's open", door.getId());
    }
  }

  @Override
  public void unlock() {
    logger.warn(Markers.MILESTONE_1, "Can't unlock door '{}' because it's already unlocked",
        door.getId());
  }

  @Override
  public void unlockShortly() {
    logger.warn(Markers.MILESTONE_1, "Can't unlock shortly door '{}' because it's already unlocked",
        door.getId());
  }
}
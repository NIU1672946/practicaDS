package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Concrete State in the State design pattern representing a locked door.
 * A locked door is guaranteed to be physically closed and blocks open requests.
 * From Locked, a door can only transition to Unlocked (via unlock) or to
 * UnlockedShortly (via unlockShortly).
 */
public class Locked extends DoorState {
  private static final Logger logger = LoggerFactory.getLogger(Locked.class);

  public Locked(Door door) {
    super(door);
    this.name = States.LOCKED;
  }

  @Override
  public void open() {
    logger.warn(Markers.MILESTONE_1, "Can't open door '{}' because it's locked", door.getId());
  }

  @Override
  public void close() {
    logger.info(Markers.MILESTONE_1, "Door '{}' is already closed and locked", door.getId());
  }

  @Override
  public void lock() {
    logger.warn(Markers.MILESTONE_1, "Can't lock door '{}' because it's already locked",
        door.getId());
  }

  @Override
  public void unlock() {
    door.setState(new Unlocked(door));
    logger.info(Markers.MILESTONE_1, "Door '{}' unlocked", door.getId());
  }

  @Override
  public void unlockShortly() {
    door.setState(new UnlockedShortly(door));
    logger.info(Markers.MILESTONE_1, "Door '{}' unlocked shortly", door.getId());
  }
}
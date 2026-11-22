package baseNoStates;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Concrete state of the State design pattern representing a locked door.
 * A locked door is always physically closed and cannot be opened directly.
 * It can only transition to Unlocked (via unlock) or to UnlockedShortly (via unlockShortly).
 */
@SuppressWarnings({"checkstyle:MissingJavadocType", "checkstyle:EmptyLineSeparator"})
public class Locked extends DoorState {
  private static final Logger logger = LoggerFactory.getLogger("baseNoStates.Locked");


  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public Locked(Door door) {
    super(door);
    this.name = States.LOCKED;
  }

  @Override
  public void open() {
    String msg="Can't open door " + door.getId() + " because it's locked";
    logger.info(Markers.MILESTONE_1,msg);
  }

  @Override
  public void close() {
    String msg = "Door " + door.getId() + " is already closed and locked";
    logger.info(Markers.MILESTONE_1,msg);
  }

  @Override
  public void lock() {
    String msg = "Can't lock door " + door.getId() + " because it's already locked";
    logger.info(Markers.MILESTONE_1,msg);
  }

  @Override
  public void unlock() {
    door.setState(new Unlocked(door));
  }

  @SuppressWarnings({"checkstyle:WhitespaceAround", "checkstyle:GoogleRightCurly", "checkstyle:WhitespaceAfter", "checkstyle:LeftCurly"})
  @Override
  public void unlockShortly(){door.setState(new UnlockedShortly(door));}

}


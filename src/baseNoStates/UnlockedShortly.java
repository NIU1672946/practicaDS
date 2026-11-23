package baseNoStates;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Concrete State (State pattern) and Concrete Observer (Observer pattern) representing
 * a door temporarily unlocked for 10 seconds so an authorized user can pass through
 * without manually locking it afterwards.
 *
 * Upon instantiation, it subscribes to the global Clock Singleton and records its
 * creation timestamp. On each tick, once 10 seconds have elapsed, it unsubscribes from
 * Clock (preventing memory leaks) and transitions automatically to Locked if closed,
 * or to Propped if the door was left open.
 */
// java.util.Observer and Observable are deprecated since Java 9, but required by the spec.
@SuppressWarnings("deprecation")
public class UnlockedShortly extends DoorState implements Observer {
  private static final Logger logger = LoggerFactory.getLogger(UnlockedShortly.class);
  private static final int TIME_TO_TRANSITION_SECONDS = 10;

  private final LocalDateTime unlockTime;

  public UnlockedShortly(Door door) {
    super(door);
    this.name = States.UNLOCKED_SHORTLY;
    this.unlockTime = LocalDateTime.now();
    Clock clock = Clock.getInstance();
    clock.start();
    clock.addObserver(this);
  }

  @Override
  public void open() {
    if (door.isClosed()) {
      door.setClosed(false);
      logger.info(Markers.MILESTONE_1, "Door '{}' opened while unlocked shortly", door.getId());
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
      logger.info(Markers.MILESTONE_1, "Door '{}' closed while unlocked shortly", door.getId());
    }
  }

  @Override
  public void lock() {
    logger.warn(Markers.MILESTONE_1, "Can't lock door '{}' because it's unlocked shortly",
        door.getId());
  }

  @Override
  public void unlock() {
    logger.warn(Markers.MILESTONE_1, "Can't unlock door '{}' because it's unlocked shortly",
        door.getId());
  }

  @Override
  public void unlockShortly() {
    logger.warn(Markers.MILESTONE_1, "Can't unlock shortly door '{}' because it already is",
        door.getId());
  }

  @Override
  public void update(Observable observable, Object arg) {
    LocalDateTime now = (LocalDateTime) arg;
    long elapsed = Duration.between(unlockTime, now).getSeconds();
    logger.debug(Markers.MILESTONE_1, "Door '{}' unlocked shortly for {}s", door.getId(), elapsed);
    if (elapsed >= TIME_TO_TRANSITION_SECONDS) {
      // Unsubscribe immediately so obsolete state objects can be garbage collected
      Clock.getInstance().deleteObserver(this);
      transition();
    }
  }

  private void transition() {
    if (door.isClosed()) {
      door.setState(new Locked(door));
      logger.info(Markers.MILESTONE_1, "Door '{}' automatically returned to locked", door.getId());
    } else {
      door.setState(new Propped(door));
      logger.warn(Markers.MILESTONE_1, "ALARM: Door '{}' entered propped state (left open)",
          door.getId());
    }
  }
}
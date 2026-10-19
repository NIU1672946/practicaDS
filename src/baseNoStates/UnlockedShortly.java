package baseNoStates;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

/*
 * Concrete state of the State design pattern and Observer in the Observer design pattern.
 * Represents a door that has been temporarily unlocked for 10 seconds so a user can cross it.
 *
 * When instantiated, it registers itself as an observer of the shared Clock instance.
 * Every second, the Clock notifies this state of the current time via update().
 * After 10 seconds have elapsed since creation (t0), this state unsubscribes from the Clock
 * to prevent memory leaks and checks the physical status of the door:
 * - If the door is closed, it automatically returns to the Locked state.
 * - If the door was left open, it transitions to the Propped state.
 */
@SuppressWarnings("deprecation")
public class UnlockedShortly extends DoorState implements Observer {
  private static final int TIME_TO_TRANSITION = 10; // maximum duration in seconds
  // A unique clock is shared by all UnlockedShortly instances because it is static
  private static final Clock clock = new Clock(1);
  private static boolean clockStarted = false;

  private final LocalDateTime t0;

  public UnlockedShortly(Door door) {
    super(door);
    this.name = States.UNLOCKED_SHORTLY;
    this.t0 = LocalDateTime.now();
    if (!clockStarted) {
      clock.start();
      clockStarted = true;
    }
    clock.addObserver(this);
  }

  @Override
  public void open() {
    if (door.isClosed()) {
      door.setClosed(false);
    } else {
      System.out.println("Can't open door " + door.getId() + " because it's already open");
    }
  }

  @Override
  public void close() {
    if (door.isClosed()) {
      System.out.println("Can't close door " + door.getId() + " because it's already closed");
    } else {
      door.setClosed(true);
    }
  }

  @Override
  public void lock() {
    System.out.println("Can't lock door " + door.getId() + " because it's unlocked shortly");
  }

  @Override
  public void unlock() {
    System.out.println("Can't unlock door " + door.getId() + " because it's unlocked shortly");
  }

  @Override
  public void unlockShortly() {
    // A door cannot be repeatedly unlocked shortly while it is already in this temporary state
    System.out.println("Can't unlock shortly door " + door.getId() + " because it already is");
  }

  @Override
  public void update(Observable o, Object arg) {
    LocalDateTime now = (LocalDateTime) arg;
    // Check if 10 seconds have passed since the door entered UnlockedShortly
    if (Duration.between(t0, now).getSeconds() >= TIME_TO_TRANSITION) {
      // Unsubscribe from the observable clock so unused state objects can be garbage collected
      clock.deleteObserver(this);
      transition();
    }
  }

  private void transition() {
    // After 10 seconds from creation, return to Locked if closed, or become Propped if still open
    if (door.isClosed()) {
      door.setState(new Locked(door));
    } else {
      door.setState(new Propped(door));
    }
  }
}
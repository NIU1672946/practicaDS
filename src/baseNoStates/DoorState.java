package baseNoStates;

/*
 * Abstract State in the State design pattern modeling the behavior of a Door.
 * Rather than filling Door with long conditional blocks checking the current state
 * before every action, Door delegates operations (open, close, lock, unlock,
 * unlockShortly) to its active DoorState object. Adding future states (e.g.,
 * DoNotDisturb or Idle) only requires creating a new subclass without modifying Door.
 *
 * Each state keeps a reference to its owning Door so concrete states can check or update
 * the door's physical closed status and trigger transitions via door.setState(...).
 */
public abstract class DoorState {
  protected final Door door;
  protected String name;

  public DoorState(Door door) {
    this.door = door;
  }

  public String getName() {
    return name;
  }

  public abstract void open();

  public abstract void close();

  public abstract void lock();

  public abstract void unlock();

  public abstract void unlockShortly();
}
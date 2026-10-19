package baseNoStates;

/*
 * Abstract class representing the state of a door, applying the State design pattern.
 * Instead of having large conditional (if-else or switch) statements scattered across
 * the Door class to check its state before every action, Door delegates the execution
 * of actions (open, close, lock, unlock, unlockShortly) to its current DoorState object.
 * This makes the design cohesive and easy to extend if new states are added in the future.
 *
 * A DoorState needs a reference to its associated Door (#door) so that concrete states
 * can query or change the physical closed status of the door and trigger state transitions
 * by invoking door.setState(...).
 */
public abstract class DoorState {

  protected Door door;

  protected String name;

  public DoorState(Door door){
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

package baseNoStates;
/*
 * Concrete state of the State design pattern representing a locked door.
 * A locked door is always physically closed and cannot be opened directly.
 * It can only transition to Unlocked (via unlock) or to UnlockedShortly (via unlockShortly).
 */
public class Locked extends DoorState {

  public Locked(Door door) {
    super(door);
    this.name = States.LOCKED;
  }

  @Override
  public void open() {
    System.out.println("Can't open door " + door.getId() + " because it's locked");
  }

  @Override
  public void close() {
    System.out.println("Door " + door.getId() + " is already closed and locked");
  }

  @Override
  public void lock() {
    System.out.println("Can't lock door " + door.getId() + " because it's already locked");
  }

  @Override
  public void unlock() {
    door.setState(new Unlocked(door));
  }

  @Override
  public void unlockShortly(){door.setState(new UnlockedShortly(door));}

}


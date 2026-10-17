package baseNoStates;

public class Unlocked extends DoorState{

  public Unlocked(Door door){
    super(door);
    this.name = States.UNLOCKED;
  }
  @Override
  public void open(){
    if (door.isClosed()){
      door.setClosed(false);
    } else {
      System.out.println("Can't open door " + door.getId() + " because it's already open");
    }
  }

  @Override
  public void close(){
    if (door.isClosed()) {
      System.out.println("Can't close door " + door.getId() + " because it's already closed");
    } else {
      door.setClosed(true);
    }
  }

  @Override
  public void lock() {
    if(door.isClosed()){
      door.setState(new Locked(door));
    }
    else {
      System.out.println("Can't lock door " + door.getId() + " because it's open");
    }
  }

  @Override
  public void unlock() {
    System.out.println("Can't unlock door " + door.getId() + " because it's already unlocked");
  }

  @Override
  public void unlockShortly(){
    System.out.println("Can't unlock shortly door " + door.getId() + "because it's unlocked");
  }
}


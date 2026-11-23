package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Concrete state of the State design pattern representing an unlocked door.
 * When a door is in the Unlocked state, its bolt is retracted so anyone authorized
 * can physically open and close it. If the door is physically closed, it can transition
 * to the Locked state upon receiving a lock action.
 */
public class Unlocked extends DoorState{
  private static final Logger logger = LoggerFactory.getLogger("baseNoStates.Unlocked");
  public Unlocked(Door door){
    super(door);
    this.name = States.UNLOCKED;
  }
  @Override
  public void open(){
    if (door.isClosed()){
      door.setClosed(false);
    } else {
      String msg = "Can't open door " + door.getId() + " because it's already open";
      logger.info(Markers.MILESTONE_1,msg);
    }
  }

  @Override
  public void close(){
    if (door.isClosed()) {
      String msg = "Can't close door " + door.getId() + " because it's already closed";
      logger.info(Markers.MILESTONE_1,msg);

    } else {
      door.setClosed(true);
    }
  }

  @Override
  public void lock() {
    // A door can only be locked if it is physically closed first
    if(door.isClosed()){
      door.setState(new Locked(door));
    }
    else {
      String msg = "Can't lock door " + door.getId() + " because it's open";
      logger.info(Markers.MILESTONE_1,msg);
    }
  }

  @Override
  public void unlock() {
    String msg = "Can't unlock door " + door.getId() + " because it's already unlocked";
    logger.info(Markers.MILESTONE_1,msg);

  }

  @Override
  public void unlockShortly(){
    String msg = "Can't unlock shortly door " + door.getId() + "because it's unlocked";
    logger.info(Markers.MILESTONE_1,msg);

  }
}


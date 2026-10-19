package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Represents a specific physical room or space in the building.
 * Acts as the Leaf class in the Composite design pattern because it cannot contain
 * sub-areas.
 *
 * A Space needs to know the doors that give access to it (those having this space as 'toSpace')
 * so that when an area request (lock/unlock) is sent to this space or an ancestor partition,
 * all descendant access doors can be retrieved and processed.
 */
public class Space extends Area{

  private final ArrayList<Door> doors = new ArrayList<>();

  public Space(String id, String description, Partition parent){
    super(id, description, parent);
  }

  public void addDoor(Door door){
    doors.add(door);
  }

  @Override
  public ArrayList<Door> getDoorsGivingAccess(){
    return doors;
  }

  @Override
  public Area findAreaById(String id){
    if (this.id.equals(id)) {
      return this;
    }
    return null;
  }

  @Override
  public  ArrayList<Space> getSpaces(){
    return new ArrayList<>(Arrays.asList(this));
  }
}

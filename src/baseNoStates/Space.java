package baseNoStates;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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

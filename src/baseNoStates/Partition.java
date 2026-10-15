package baseNoStates;

import java.util.ArrayList;

public class Partition extends Area{

  private final ArrayList<Area> areas = new ArrayList<>();

  public Partition(String id, String description, Partition parent ){
    super(id, description, parent);
  }

  public void addArea(Area area){
    areas.add(area);
  }

  @Override
  public  ArrayList<Door> getDoorsGivingAccess(){
    ArrayList<Door> doorsGivingAccess = new ArrayList<>();
    for (Area area : areas){
      doorsGivingAccess.addAll(area.getDoorsGivingAccess());
    }
    return doorsGivingAccess;
  }

  @Override
  public  Area findAreaById(String id){
    if (this.id.equals(id)) {
      return this;
    }
    for (Area area : areas){
      Area found = area.findAreaById(id);
      if (found != null)
        return found;
    }
    return null;
  }

  @Override
  public  ArrayList<Space> getSpaces(){
    ArrayList<Space> spaces = new ArrayList<>();
    for (Area area : areas){
      spaces.addAll(area.getSpaces());
    }
    return spaces;
  }

}

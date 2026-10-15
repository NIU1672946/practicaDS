package baseNoStates;

import java.util.ArrayList;

public abstract class Area {

  protected String id;

  protected String description;


  public Area(String id, String description, Partition parent){
    this.id = id;
    this.description = description;
    if(parent != null){
      parent.addArea(this);
    }
  }
  public String getId() {
    return id;
  }
  public abstract ArrayList<Door> getDoorsGivingAccess();

  public abstract Area findAreaById(String id);

  public abstract ArrayList<Space> getSpaces();

}



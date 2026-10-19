package baseNoStates;

import java.util.ArrayList;

/*
 * Abstract class acting as the Component in the Composite design pattern.
 * It provides a common interface for both individual rooms (Space) and hierarchical
 * groups of spaces/partitions (Partition). This allows the system to grant permissions
 * and process area requests uniformly over a single space, a floor, or the whole building.
 */

public abstract class Area {

  protected String id;
  protected String description;

  public Area(String id, String description, Partition parent){
    this.id = id;
    this.description = description;
    // If this area belongs to a parent partition, automatically add it to the parent's children
    if(parent != null){
      parent.addArea(this);
    }
  }
  public String getId() {
    return id;
  }
  // Returns the list of doors that give access to some space within this area
  public abstract ArrayList<Door> getDoorsGivingAccess();

  // Recursively searches the area tree for a node with the given id
  public abstract Area findAreaById(String id);

  // Returns all individual spaces contained within this area to check user permissions
  public abstract ArrayList<Space> getSpaces();
}



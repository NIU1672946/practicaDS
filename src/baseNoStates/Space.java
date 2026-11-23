package baseNoStates;

import java.util.ArrayList;

/*
 * Leaf node in the Composite design pattern representing an individual physical room
 * or zone (e.g., parking, hall, IT) that cannot contain child areas.
 *
 * Each Space keeps references to the doors giving access into it (doors with this space
 * as 'toSpace') so area requests can locate every door leading into a room or partition.
 * In the Visitor pattern, Space is a ConcreteElement dispatching to visitSpace(this).
 */
public class Space extends Area {
  private final ArrayList<Door> doors = new ArrayList<>();

  public Space(String id, String description, Partition parent) {
    super(id, description, parent);
  }

  public void addDoor(Door door) {
    doors.add(door);
  }

  public ArrayList<Door> getDoors() {
    return doors;
  }

  @Override
  public void accept(AreaVisitor visitor) {
    visitor.visitSpace(this);
  }
}
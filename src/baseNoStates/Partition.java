package baseNoStates;

import java.util.ArrayList;

/*
 * Composite node in the Composite design pattern representing a logical grouping of
 * spaces and/or nested partitions (such as a floor or the entire building).
 *
 * Grouping spaces into partitions allows administrators to grant permissions or
 * lock/unlock whole floors at once. In the Visitor pattern, Partition acts as a
 * ConcreteElement whose accept() dispatches back to visitor.visitPartition(this).
 */
public class Partition extends Area {
  private final ArrayList<Area> areas = new ArrayList<>();

  public Partition(String id, String description, Partition parent) {
    super(id, description, parent);
  }

  public void addArea(Area area) {
    areas.add(area);
  }

  public ArrayList<Area> getAreas() {
    return areas;
  }

  @Override
  public void accept(AreaVisitor visitor) {
    visitor.visitPartition(this);
  }
}
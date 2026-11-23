package baseNoStates;

import java.util.ArrayList;

/*
 * Represents a logical grouping of one or more spaces and/or other partitions (e.g., a floor
 * or the entire building). Acts as the Composite class in the Composite design pattern.
 * It implements the Area operations by recursively traversing its child areas.
 */

public class Partition extends Area {

  private final ArrayList<Area> areas = new ArrayList<>();

  public Partition(String id, String description, Partition parent) {
    super(id, description, parent);
  }

  public void addArea(Area area) {
    areas.add(area);
  }

  @Override
  public ArrayList<Space> getSpaces() {
    ArrayList<Space> spaces = new ArrayList<>();
    for (Area area : areas) {
      spaces.addAll(area.getSpaces());
    }
    return spaces;
  }
  
  public ArrayList<Area> getAreas() {
    return areas;
  }

  @Override
  public void accept(AreaVisitor visitor) {
    visitor.visitPartition(this);
  }
}

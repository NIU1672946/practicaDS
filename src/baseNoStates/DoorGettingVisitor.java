package baseNoStates;

import java.util.ArrayList;

public class DoorGettingVisitor extends AreaVisitor {

  @Override
  public ArrayList<Door> visitPartition(Partition partition) {
    ArrayList<Door> doorsGivingAccess = new ArrayList<>();
    ArrayList<Area> areas = partition.getAreas();
    for (Area area : areas) {
      doorsGivingAccess.addAll(area.accept(this));
    }
    return doorsGivingAccess;
  }

  @Override
  public ArrayList<Door> visitSpace(Space space) {
    return space.getDoors();
  }
}

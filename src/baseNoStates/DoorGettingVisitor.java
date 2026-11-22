package baseNoStates;

import java.util.ArrayList;

public class DoorGettingVisitor extends AreaVisitor {
  private ArrayList<Door> result;

  public ArrayList<Door> getResult() {
    return result;
  }

  @Override
  public void visitPartition(Partition partition) {
    ArrayList<Door> doorsGivingAccess = new ArrayList<>();
    ArrayList<Area> areas = partition.getAreas();
    for (Area area : areas) {
      area.accept(this);
      doorsGivingAccess.addAll(result);
    }
    result = doorsGivingAccess;
  }

  @Override
  public void visitSpace(Space space) {
    result = space.getDoors();
  }
}

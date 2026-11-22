package baseNoStates;

import java.util.ArrayList;

public class IdFindingVisitor extends AreaVisitor {
  private Area result;
  private final String id;

  public Area getResult() {
    return result;
  }

  public IdFindingVisitor(String targetId) {
    this.id = targetId;
  }

  @Override
  public void visitPartition(Partition partition) {
    if (partition.getId().equals(id)) {
      result = partition;
    }
    for (Area area : partition.getAreas()) {
      area.accept(this);
      if (result != null) {
        break;
      }
    }
  }

  @Override
  public void visitSpace(Space space) {
    if (result == null && space.getId().equals(id)) {
      result = space;
    }
  }
}

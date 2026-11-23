package baseNoStates;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Concrete Visitor that traverses an Area subtree and collects all doors currently
 * in the Propped alarm state. Demonstrates how new tree-based computations can be
 * added cleanly without modifying Area, Partition, or Space.
 */
public class ProppedDoorsVisitor implements AreaVisitor {
  private static final Logger logger = LoggerFactory.getLogger(ProppedDoorsVisitor.class);
  private final ArrayList<Door> proppedDoors = new ArrayList<>();

  public ArrayList<Door> getProppedDoors() {
    return proppedDoors;
  }

  @Override
  public void visitPartition(Partition partition) {
    logger.debug(Markers.MILESTONE_2, "Visiting partition '{}' to find propped doors",
        partition.getId());
    for (Area child : partition.getAreas()) {
      child.accept(this);
    }
  }

  @Override
  public void visitSpace(Space space) {
    logger.debug(Markers.MILESTONE_2, "Checking doors of space '{}' for propped state",
        space.getId());
    for (Door door : space.getDoors()) {
      if (States.PROPPED.equals(door.getStateName())) {
        proppedDoors.add(door);
      }
    }
  }
}
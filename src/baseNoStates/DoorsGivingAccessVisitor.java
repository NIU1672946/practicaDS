package baseNoStates;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Concrete Visitor that traverses an Area subtree in pre-order and collects every Door
 * giving access to the spaces under it. Used by RequestArea to decompose a lock/unlock
 * area request into individual RequestReader objects.
 *
 * Accumulating doors into an internal list avoids exposing the mutable list held inside
 * each Space object.
 */
public class DoorsGivingAccessVisitor implements AreaVisitor {
  private static final Logger logger = LoggerFactory.getLogger(DoorsGivingAccessVisitor.class);
  private final ArrayList<Door> doors = new ArrayList<>();

  public ArrayList<Door> getDoors() {
    return doors;
  }

  @Override
  public void visitPartition(Partition partition) {
    logger.debug(Markers.MILESTONE_2, "Visiting partition '{}' to collect access doors",
        partition.getId());
    for (Area child : partition.getAreas()) {
      child.accept(this);
    }
  }

  @Override
  public void visitSpace(Space space) {
    logger.debug(Markers.MILESTONE_2, "Visiting space '{}', found {} access door(s)",
        space.getId(), space.getDoors().size());
    doors.addAll(space.getDoors());
  }
}
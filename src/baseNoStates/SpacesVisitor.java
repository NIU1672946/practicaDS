package baseNoStates;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Concrete Visitor that collects all leaf Space instances contained inside an Area.
 * Used during request authorization (User.canBeInSpace) to expand the partitions and
 * spaces granted to a UserGroup into individual spaces comparable with a door's
 * origin and destination spaces.
 */
public class SpacesVisitor implements AreaVisitor {
  private static final Logger logger = LoggerFactory.getLogger(SpacesVisitor.class);
  private final ArrayList<Space> spaces = new ArrayList<>();

  public ArrayList<Space> getSpaces() {
    return spaces;
  }

  @Override
  public void visitPartition(Partition partition) {
    logger.debug(Markers.MILESTONE_2, "Visiting partition '{}' to collect spaces",
        partition.getId());
    for (Area child : partition.getAreas()) {
      child.accept(this);
    }
  }

  @Override
  public void visitSpace(Space space) {
    logger.debug(Markers.MILESTONE_2, "Collected space '{}'", space.getId());
    spaces.add(space);
  }
}
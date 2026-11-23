package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Concrete Visitor that searches the Area hierarchy for a Partition or Space matching
 * a target identifier. Used by DirectoryAreas when setting up user group permissions
 * and when resolving incoming RequestArea requests.
 *
 * Because the visitor controls traversal order, it stops immediately as soon as a
 * match is found instead of traversing the rest of the building tree.
 */
public class FindAreaByIdVisitor implements AreaVisitor {
  private static final Logger logger = LoggerFactory.getLogger(FindAreaByIdVisitor.class);
  private final String targetId;
  private Area result;

  public FindAreaByIdVisitor(String targetId) {
    this.targetId = targetId;
  }

  public Area getResult() {
    return result;
  }

  @Override
  public void visitPartition(Partition partition) {
    logger.debug(Markers.MILESTONE_2, "Checking partition '{}' against target '{}'",
        partition.getId(), targetId);
    if (partition.getId().equals(targetId)) {
      result = partition;
      logger.debug(Markers.MILESTONE_2, "Found target partition '{}'", targetId);
    } else {
      for (Area child : partition.getAreas()) {
        child.accept(this);
        if (result != null) {
          break;
        }
      }
    }
  }

  @Override
  public void visitSpace(Space space) {
    logger.debug(Markers.MILESTONE_2, "Checking space '{}' against target '{}'",
        space.getId(), targetId);
    if (result == null && space.getId().equals(targetId)) {
      result = space;
      logger.debug(Markers.MILESTONE_2, "Found target space '{}'", targetId);
    }
  }
}
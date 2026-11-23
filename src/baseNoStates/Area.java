package baseNoStates;

/*
 * Abstract Component in the Composite design pattern and Element in the Visitor pattern.
 * Provides a uniform abstraction for both individual rooms (Space) and hierarchical
 * groupings (Partition), letting permissions and area requests treat a room, a floor,
 * or the entire building identically.
 *
 * All tree traversal operations (finding an area by ID, collecting access doors,
 * collecting spaces, or locating propped doors) are delegated to AreaVisitor subclasses
 * via accept(), keeping the Area hierarchy cohesive and closed to modification.
 */
public abstract class Area {
  protected final String id;
  protected final String description;

  public Area(String id, String description, Partition parent) {
    this.id = id;
    this.description = description;
    // Automatically attach this node to its parent partition in the building tree
    if (parent != null) {
      parent.addArea(this);
    }
  }

  public String getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public abstract void accept(AreaVisitor visitor);
}
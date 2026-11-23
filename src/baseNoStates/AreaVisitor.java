package baseNoStates;

/*
 * Visitor interface in the Visitor design pattern applied over the Area Composite tree.
 * Moving traversal algorithms out of Partition and Space prevents structural classes
 * from becoming bloated and non-cohesive, and allows adding new tree operations
 * (such as listing propped doors) simply by adding a new visitor implementation.
 */
public interface AreaVisitor {
  void visitPartition(Partition partition);

  void visitSpace(Space space);
}
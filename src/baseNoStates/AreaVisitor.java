package baseNoStates;
import java.util.ArrayList;

public abstract class AreaVisitor {
  public abstract void visitPartition(Partition partition);
  public abstract void visitSpace(Space space);
}

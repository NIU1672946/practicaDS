package baseNoStates;
import java.util.ArrayList;

public abstract class AreaVisitor {
  public abstract ArrayList<Door> visitPartition(Partition partition);
  public abstract ArrayList<Door> visitSpace(Space space);
}

package baseNoStates;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/*
 * Defines SLF4J markers used to tag log messages by project milestone.
 * Combined with Logback's MarkerFilter in logback.xml, these markers let developers
 * toggle logging between Milestone 1 features, Milestone 2 refactoring (Singletons and
 * Visitors), or the whole application without changing Java code.
 */
public final class Markers {
  public static final Marker MILESTONE_1 = MarkerFactory.getMarker("MILESTONE_1");
  public static final Marker MILESTONE_2 = MarkerFactory.getMarker("MILESTONE_2");

  private Markers() {
  }
}
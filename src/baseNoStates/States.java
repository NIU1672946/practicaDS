package baseNoStates;

/*
 * Defines string constants for the possible state names of a door.
 * The simulator expects "unlocked_shortly" (with an underscore) in the JSON response
 * to properly paint the reader state on the building blueprint.
 */
public final class States {
  public static final String LOCKED = "locked";
  public static final String UNLOCKED = "unlocked";
  public static final String UNLOCKED_SHORTLY = "unlocked_shortly";
  public static final String PROPPED = "propped";
}

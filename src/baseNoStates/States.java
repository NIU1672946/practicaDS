package baseNoStates;

/*
 * Defines string constants for the possible states of a Door.
 * These exact lowercase values (including the underscore in "unlocked_shortly") are
 * expected by the web simulator and mobile app to parse JSON answers and paint readers.
 */
public final class States {
  public static final String LOCKED = "locked";
  public static final String UNLOCKED = "unlocked";
  public static final String UNLOCKED_SHORTLY = "unlocked_shortly";
  public static final String PROPPED = "propped";

  private States() {
  }
}
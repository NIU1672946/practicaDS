package baseNoStates;

/*
 * Defines string constants for all action names accepted in reader and area requests.
 * Centralizing these strings avoids silent bugs from typos when matching actions across
 * request parsers, user group permissions, and door states.
 */
public final class Actions {
  // Actions valid for both reader and area requests
  public static final String LOCK = "lock";
  public static final String UNLOCK = "unlock";
  // Actions valid only for individual reader requests
  public static final String UNLOCK_SHORTLY = "unlock_shortly";
  public static final String OPEN = "open";
  public static final String CLOSE = "close";

  private Actions() {
  }
}
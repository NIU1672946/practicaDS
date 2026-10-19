package baseNoStates;
/*
 * Defines constants for all possible action names that can be sent in requests.
 * Using constants avoids typos when comparing action strings across the system.
 */
public final class Actions {
  // possible actions in reader and area requests
  public static final String LOCK = "lock";
  public static final String UNLOCK = "unlock";
  public static final String UNLOCK_SHORTLY = "unlock_shortly";
  // possible actions in door requests
  public static final String OPEN = "open";
  public static final String CLOSE = "close";
}

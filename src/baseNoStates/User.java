package baseNoStates;

import java.time.LocalDateTime;
import java.util.ArrayList;

/*
 * Represents an individual user of the ACS identified by a name and a unique credential.
 * Following Role-Based Access Control (RBAC), permissions are not assigned directly to
 * each user; instead, a User belongs to a UserGroup and acquires its access rights
 * (allowed areas, actions, and schedule) by delegating permission checks to its group.
 */
public class User {
  private final String name;
  private final UserGroup userGroup;
  private final String credential;

  public User(String name, UserGroup userGroup, String credential) {
    this.name = name;
    this.userGroup = userGroup;
    this.credential = credential;
  }

  public String getCredential() {
    return credential;
  }

  public String getName() {
    return name;
  }

  // Checks if the user is allowed to be in a given physical space
  public boolean canBeInSpace(Space sp) {
    return getSpaces().contains(sp);
  }

  // Checks if the user is allowed to send requests at the given date and time
  public boolean canSendRequests(LocalDateTime now) {
    return userGroup.canSendRequests(now);
  }

  // Checks if the user's role authorizes performing the requested action
  public boolean canDoAction(String action) {
    return userGroup.canDoAction(action);
  }

  // Retrieves all individual spaces contained within the areas granted to the user's group
  private ArrayList<Space> getSpaces() {
    ArrayList<Space> spaces = new ArrayList<>();
    for (Area area : userGroup.getAreas()) {
      spaces.addAll(area.getSpaces());
    }
    return spaces;
  }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }
}
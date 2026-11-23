package baseNoStates;

import java.time.LocalDateTime;
import java.util.ArrayList;

/*
 * Represents an individual person registered in the ACS with a name and unique mobile
 * credential. Following Role-Based Access Control (RBAC), a User holds no permissions
 * directly; all authorization checks (spaces, schedule, and actions) are delegated to
 * the UserGroup the user belongs to.
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

  public String getGroupName() {
    return userGroup.getName();
  }

  public boolean canBeInSpace(Space space) {
    return getSpaces().contains(space);
  }

  public boolean canSendRequests(LocalDateTime now) {
    return userGroup.canSendRequests(now);
  }

  public boolean canDoAction(String action) {
    return userGroup.canDoAction(action);
  }

  private ArrayList<Space> getSpaces() {
    SpacesVisitor visitor = new SpacesVisitor();
    for (Area area : userGroup.getAreas()) {
      area.accept(visitor);
    }
    return visitor.getSpaces();
  }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }
}
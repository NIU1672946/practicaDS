package baseNoStates;

import java.time.LocalDateTime;
import java.util.ArrayList;

/*
 * Represents a role in Role-Based Access Control (RBAC).
 * Instead of assigning permissions to individual users, access rights are granted to
 * a UserGroup and acquired by all its members. A UserGroup binds the four dimensions
 * of access control: who (users), where (areas), what (actions), and when (schedule).
 */
public class UserGroup {
  private final String name;
  private final ArrayList<User> users = new ArrayList<>();
  private final ArrayList<Area> areas;
  private final ArrayList<String> actions;
  private Schedule schedule;

  public UserGroup(String name, ArrayList<Area> areas, Schedule schedule,
                   ArrayList<String> actions) {
    this.name = name;
    this.areas = areas;
    this.schedule = schedule;
    this.actions = actions;
  }

  public boolean canSendRequests(LocalDateTime now) {
    return schedule.includes(now);
  }

  public boolean canDoAction(String action) {
    return actions.contains(action);
  }

  public ArrayList<Area> getAreas() {
    return areas;
  }

  public String getName() {
    return name;
  }

  public void setSchedule(Schedule schedule) {
    this.schedule = schedule;
  }

  public void addUser(User user) {
    users.add(user);
  }

  public User findUserByCredential(String credential) {
    for (User user : users) {
      if (user.getCredential().equals(credential)) {
        return user;
      }
    }
    return null;
  }
}
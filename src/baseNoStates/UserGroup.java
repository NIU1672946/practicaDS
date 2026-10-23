package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/*
 * Represents a role or group of users in Role-Based Access Control (RBAC).
 * Grouping users by role simplifies administration: permissions are granted to a UserGroup
 * rather than to individual users. A UserGroup encapsulates the four dimensions of access:
 * - Who: the list of users belonging to this group.
 * - Where: the list of areas (partitions and/or spaces) they can access.
 * - What: the list of actions they are allowed to perform on doors.
 * - When: the schedule (date interval, days of the week, and daily time interval).
 */
public class UserGroup {
  private final String name;
  private final ArrayList<User> users = new ArrayList<>();
  private final ArrayList<Area> areas;
  private final ArrayList<String> actions;
  private Schedule schedule; //the schedule must be modifiable

  public UserGroup(String name, ArrayList<Area> areas, Schedule schedule,ArrayList<String> actions) {
    this.name = name;
    this.areas = areas;
    this.schedule = schedule;
    this.actions = actions;
  }

  // Validates whether a given timestamp falls within the group's schedule (dates, weekday, hours)
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
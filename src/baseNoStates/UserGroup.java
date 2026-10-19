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
  private final LocalDate fromDate;
  private final LocalDate toDate;
  private final ArrayList<DayOfWeek> daysOfWeek;
  private final LocalTime fromTime;
  private final LocalTime toTime;
  private final ArrayList<String> actions;

  public UserGroup(String name, ArrayList<Area> areas,
                   LocalDate fromDate, LocalDate toDate,
                   ArrayList<DayOfWeek> daysOfWeek,
                   LocalTime fromTime, LocalTime toTime,
                   ArrayList<String> actions) {
    this.name = name;
    this.areas = areas;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.daysOfWeek = daysOfWeek;
    this.fromTime = fromTime;
    this.toTime = toTime;
    this.actions = actions;
  }

  // Validates whether a given timestamp falls within the group's schedule (dates, weekday, hours)
  public boolean canSendRequests(LocalDateTime now) {
    LocalDate currentDate = now.toLocalDate();
    LocalTime currentTime = now.toLocalTime();
    DayOfWeek currentDay = now.getDayOfWeek();

    boolean validDate = !currentDate.isBefore(fromDate) && !currentDate.isAfter(toDate);
    boolean validDay = daysOfWeek.contains(currentDay);
    boolean validTime = !currentTime.isBefore(fromTime) && !currentTime.isAfter(toTime);

    return validDate && validDay && validTime;
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
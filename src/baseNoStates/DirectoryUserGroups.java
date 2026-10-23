package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Directory class responsible for creating and storing all UserGroup instances
 * (blank, employees, managers, admin) along with their assigned users, areas,
 * allowed actions, and schedules. Also provides user lookup by credential.
 */
public final class DirectoryUserGroups {
  private static final ArrayList<UserGroup> userGroups = new ArrayList<>();

  public static void makeUserGroups() {
    userGroups.clear();

    ArrayList<DayOfWeek> monToFri = new ArrayList<>(Arrays.asList(
        DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
    ));
    ArrayList<DayOfWeek> monToSat = new ArrayList<>(monToFri);
    monToSat.add(DayOfWeek.SATURDAY);

    ArrayList<DayOfWeek> allDays = new ArrayList<>(monToSat);
    allDays.add(DayOfWeek.SUNDAY);

    // users without any privilege, just to keep temporally users instead of deleting them,
    // this is to withdraw all permissions but still to keep user data to give back
    // permissions later
    ArrayList<Area> noAreas = new ArrayList<>();
    ArrayList<String> emptyActions = new ArrayList<>();

    // can access nowhere, do nothing, never

    Schedule blankGroupSchedule = new Schedule(
            LocalDate.of(2026, Month.JANUARY, 1),
            LocalDate.of(2026, Month.JANUARY, 1),
            new ArrayList<>(),
            LocalTime.of(0, 0),
            LocalTime.of(0, 0)
    );

    UserGroup blankGroup = new UserGroup(
        "blank",
        noAreas,
        blankGroupSchedule,
        emptyActions
    );
    blankGroup.addUser(new User("Bernat", blankGroup, "12345"));
    blankGroup.addUser(new User("Blai", blankGroup, "77532"));
    userGroups.add(blankGroup);

    // employees :
    // Sep. 1 this year to Mar. 1 next year
    // week days 9-17h
    // open, close, and shortly unlock
    // ground floor, floor1, exterior, stairs (this, for all), that is, everywhere but the parking
    ArrayList<Area> employeeAreas = new ArrayList<>(Arrays.asList(
        DirectoryAreas.findAreaById("ground_floor"),
        DirectoryAreas.findAreaById("floor1"),
        DirectoryAreas.findAreaById("exterior"),
        DirectoryAreas.findAreaById("stairs")
    ));
    ArrayList<String> employeeActions = new ArrayList<>(Arrays.asList(
        Actions.OPEN, Actions.CLOSE, Actions.UNLOCK_SHORTLY
    ));

    Schedule employeeSchedule = new Schedule(
            LocalDate.of(2026, Month.SEPTEMBER, 1),
            LocalDate.of(2027, Month.MARCH, 1),
            monToFri,
            LocalTime.of(9, 0),
            LocalTime.of(17, 0)
    );

    UserGroup employeeGroup = new UserGroup(
        "employees",
        employeeAreas,
        employeeSchedule,
        employeeActions
    );
    employeeGroup.addUser(new User("Ernest", employeeGroup, "74984"));
    employeeGroup.addUser(new User("Eulalia", employeeGroup, "43295"));
    userGroups.add(employeeGroup);

    // managers :
    // Sep. 1 this year to Mar. 1 next year
    // week days + saturday, 8-20h
    // all actions
    // all spaces
    ArrayList<Area> managerAreas = new ArrayList<>(Arrays.asList(
        DirectoryAreas.findAreaById("building")
    ));
    ArrayList<String> managerActions = new ArrayList<>(Arrays.asList(
        Actions.UNLOCK_SHORTLY, Actions.UNLOCK, Actions.LOCK, Actions.OPEN, Actions.CLOSE
    ));

    Schedule managerSchedule = new Schedule(
            LocalDate.of(2026, Month.SEPTEMBER, 1),
            LocalDate.of(2027, Month.MARCH, 1),
            monToSat,
            LocalTime.of(8, 0),
            LocalTime.of(20, 0)
    );

    UserGroup managerGroup = new UserGroup(
        "managers",
        managerAreas,
        managerSchedule,
        managerActions
    );
    managerGroup.addUser(new User("Manel", managerGroup, "95783"));
    managerGroup.addUser(new User("Marta", managerGroup, "05827"));
    userGroups.add(managerGroup);

    // admin :
    // always=Jan. 1 this year to 2100
    // all days of the week
    // all actions
    // all spaces
    ArrayList<Area> adminAreas = new ArrayList<>(Arrays.asList(
        DirectoryAreas.findAreaById("building")
    ));
    ArrayList<String> adminActions = new ArrayList<>(Arrays.asList(
        Actions.UNLOCK_SHORTLY, Actions.UNLOCK, Actions.LOCK, Actions.OPEN, Actions.CLOSE
    ));
    Schedule adminSchedule = new Schedule(
            LocalDate.of(2026, Month.JANUARY, 1),
            LocalDate.of(2100, Month.JANUARY, 1),
            allDays,
            LocalTime.of(0, 0),
            LocalTime.of(23, 59)
    );

    UserGroup adminGroup = new UserGroup(
        "admin",
        adminAreas,
        adminSchedule,
        adminActions
    );
    adminGroup.addUser(new User("Ana", adminGroup, "11343"));
    userGroups.add(adminGroup);
  }

  public static User findUserByCredential(String credential) {
    for (UserGroup userGroup : userGroups) {
      User user = userGroup.findUserByCredential(credential);
      if (user != null) {
        return user;
      }
    }
    System.out.println("user with credential " + credential + " not found");
    return null; // otherwise we get a Java error
  }
}
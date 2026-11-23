package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Central in-memory repository that creates and stores all RBAC UserGroups (blank,
 * employees, managers, admin) together with their users, allowed areas, actions, and
 * schedules. Provides credential-based lookup when authorizing incoming requests.
 *
 * Design Pattern: Singleton.
 * Ensures only one authoritative directory of credentials and permissions exists at
 * runtime, accessible globally by request-processing threads via getInstance().
 */
public final class DirectoryUserGroups {
  private static final Logger logger = LoggerFactory.getLogger(DirectoryUserGroups.class);
  private static DirectoryUserGroups uniqueInstance = null;

  private final ArrayList<UserGroup> userGroups;

  private DirectoryUserGroups() {
    userGroups = new ArrayList<>();
  }

  public static synchronized DirectoryUserGroups getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new DirectoryUserGroups();
      logger.debug(Markers.MILESTONE_2, "Created unique DirectoryUserGroups instance");
    }
    return uniqueInstance;
  }

  public void makeUserGroups() {
    userGroups.clear();

    ArrayList<DayOfWeek> monToFri = new ArrayList<>(Arrays.asList(
        DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
    ));
    ArrayList<DayOfWeek> monToSat = new ArrayList<>(monToFri);
    monToSat.add(DayOfWeek.SATURDAY);

    ArrayList<DayOfWeek> allDays = new ArrayList<>(monToSat);
    allDays.add(DayOfWeek.SUNDAY);

    // Blank group: temporarily holds users with all permissions revoked without deleting them
    Schedule blankGroupSchedule = new Schedule(
        LocalDate.of(2026, Month.JANUARY, 1),
        LocalDate.of(2026, Month.JANUARY, 1),
        new ArrayList<>(),
        LocalTime.of(0, 0),
        LocalTime.of(0, 0)
    );
    UserGroup blankGroup = new UserGroup(
        "blank", new ArrayList<>(), blankGroupSchedule, new ArrayList<>()
    );
    blankGroup.addUser(new User("Bernat", blankGroup, "12345"));
    blankGroup.addUser(new User("Blai", blankGroup, "77532"));
    userGroups.add(blankGroup);

    DirectoryAreas directoryAreas = DirectoryAreas.getInstance();

    // Employees: Sept 1 2026 - Mar 1 2027, Mon-Fri 9:00-17:00, everywhere except parking
    ArrayList<Area> employeeAreas = new ArrayList<>(Arrays.asList(
        directoryAreas.findAreaById("ground_floor"),
        directoryAreas.findAreaById("floor1"),
        directoryAreas.findAreaById("exterior"),
        directoryAreas.findAreaById("stairs")
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
        "employees", employeeAreas, employeeSchedule, employeeActions
    );
    employeeGroup.addUser(new User("Ernest", employeeGroup, "74984"));
    employeeGroup.addUser(new User("Eulalia", employeeGroup, "43295"));
    userGroups.add(employeeGroup);

    // Managers: Sept 1 2026 - Mar 1 2027, Mon-Sat 8:00-20:00, all actions, all spaces
    ArrayList<Area> allAreas = new ArrayList<>(Arrays.asList(
        directoryAreas.findAreaById("building")
    ));
    ArrayList<String> allActions = new ArrayList<>(Arrays.asList(
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
        "managers", allAreas, managerSchedule, allActions
    );
    managerGroup.addUser(new User("Manel", managerGroup, "95783"));
    managerGroup.addUser(new User("Marta", managerGroup, "05827"));
    userGroups.add(managerGroup);

    // Admin: unrestricted access across all dates, times, actions, and spaces
    Schedule adminSchedule = new Schedule(
        LocalDate.of(2026, Month.JANUARY, 1),
        LocalDate.of(2100, Month.JANUARY, 1),
        allDays,
        LocalTime.of(0, 0),
        LocalTime.of(23, 59)
    );
    UserGroup adminGroup = new UserGroup(
        "admin", new ArrayList<>(allAreas), adminSchedule, new ArrayList<>(allActions)
    );
    adminGroup.addUser(new User("Ana", adminGroup, "11343"));
    userGroups.add(adminGroup);

    logger.info(Markers.MILESTONE_1, "Initialized {} user groups", userGroups.size());
  }

  public User findUserByCredential(String credential) {
    for (UserGroup userGroup : userGroups) {
      User user = userGroup.findUserByCredential(credential);
      if (user != null) {
        return user;
      }
    }
    logger.warn(Markers.MILESTONE_1, "User with credential '{}' not found", credential);
    return null;
  }
}
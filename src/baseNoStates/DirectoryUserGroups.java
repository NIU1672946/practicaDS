package baseNoStates;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import java.time.LocalDate;
import java.time.Month;


public final class DirectoryUserGroups {
  private static final ArrayList<UserGroup> userGroups = new ArrayList<>();

  public static void makeUserGroups() {
    // users without any privilege, just to keep temporally users instead of deleting them,
    // this is to withdraw all permissions but still to keep user data to give back
    // permissions later
    ArrayList<Area> noAreas = new ArrayList<>();

    ArrayList<String> emptyActions = new ArrayList<String>();

    //can acces nowhere, do nothing, never.
    UserGroup unknown =new UserGroup(noAreas,
            new LocalDateTime[]{LocalDate.of(0000, Month.JANUARY, 1).atStartOfDay(),
                    LocalDate.of(0000, Month.JANUARY, 1).atStartOfDay()},
            new int[]{0,0},
            new int[]{0,0},
            emptyActions
            );

    unknown.addUser(new User("Bernat",unknown, "12345"));
    unknown.addUser(new User("Blai", unknown,"77532"));

    userGroups.add(
            unknown
    );



    // employees :
    // Sep. 1 this year to Mar. 1 next year
    // week days 9-17h
    // just shortly unlock
    // ground floor, floor1, exterior, stairs (this, for all), that is, everywhere but the parking
    ArrayList<Area> employeeAreas = new ArrayList<>(Arrays.asList(
            DirectoryAreas.findAreaById("ground_floor"),
            DirectoryAreas.findAreaById("floor1"),
            DirectoryAreas.findAreaById("exterior"),
            DirectoryAreas.findAreaById("stairs")
    ));

    ArrayList<String> employeeActions = new ArrayList<String>();
    employeeActions.add(Actions.UNLOCK_SHORTLY);

    UserGroup employeeGroup = new UserGroup(employeeAreas,
            new LocalDateTime[]{LocalDate.of(2026, Month.SEPTEMBER, 1).atStartOfDay(),
                    LocalDate.of(2027, Month.MARCH, 1).atStartOfDay()},
            new int[]{0,4},
            new int[]{9,17},
            employeeActions
    );

    unknown.addUser(new User("Ernest", employeeGroup,"74984"));
    unknown.addUser(new User("Eulalia", employeeGroup,"43295"));
    userGroups.add(
            employeeGroup
    );

    // managers :
    // Sep. 1 this year to Mar. 1 next year
    // week days + saturday, 8-20h
    // all actions
    // all spaces
    ArrayList<Area> managerAreas = new ArrayList<>(Arrays.asList(
            DirectoryAreas.findAreaById("building")
    ));

    ArrayList<String> managerActions = new ArrayList<String>();
    managerActions.add(Actions.UNLOCK_SHORTLY);
    managerActions.add(Actions.UNLOCK);
    managerActions.add(Actions.LOCK);
    managerActions.add(Actions.OPEN);
    managerActions.add(Actions.CLOSE);

    UserGroup managerGroup = new UserGroup(managerAreas,
            new LocalDateTime[]{LocalDate.of(2026, Month.SEPTEMBER, 1).atStartOfDay(),
                    LocalDate.of(2027, Month.MARCH, 1).atStartOfDay()},
            new int[]{0,5},
            new int[]{8,20},
            managerActions
    );
    managerGroup.addUser(new User("Manel", managerGroup,"95783" ));
    managerGroup.addUser(new User("Marta", managerGroup,"05827"));

    userGroups.add(
            managerGroup
    );

    // admin :
    // always=Jan. 1 this year to 2100
    // all days of the week
    // all actions
    // all spaces
    ArrayList<Area> adminAreas = new ArrayList<>(Arrays.asList(
            DirectoryAreas.findAreaById("building")
    ));

    ArrayList<String> adminActions = new ArrayList<String>();
    adminActions.add(Actions.UNLOCK_SHORTLY);
    adminActions.add(Actions.UNLOCK);
    adminActions.add(Actions.LOCK);
    adminActions.add(Actions.OPEN);
    adminActions.add(Actions.CLOSE);

    UserGroup adminGroup = new UserGroup(adminAreas,
            new LocalDateTime[]{LocalDate.of(2026, Month.JANUARY, 1).atStartOfDay(),
                    LocalDate.of(2100, Month.JANUARY, 1).atStartOfDay()},
            new int[]{0,7},
            new int[]{0,24},
            adminActions
            );
    adminGroup.addUser(new User("Ana", adminGroup,"11343"));
    userGroups.add(
            adminGroup
    );
  }

  public static User findUserByCredential(String credential) {
    for (UserGroup userGroup : userGroups ){
      User user = userGroup.findUserByCredential(credential);
      if (user != null){
        return user;
      }
    }
    return null;
  }
}
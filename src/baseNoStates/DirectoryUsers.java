package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

public final class DirectoryUsers {
  private static final ArrayList<User> users = new ArrayList<>();

  public static void makeUsers() {

    // users without any privilege, just to keep temporally users instead of deleting them,
    // this is to withdraw all permissions but still to keep user data to give back
    // permissions later
    ArrayList<Area> noAreas = new ArrayList<>();
    users.add(new User("Bernat", "12345", noAreas));
    users.add(new User("Blai", "77532", noAreas));

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
    users.add(new User("Ernest", "74984", employeeAreas));
    users.add(new User("Eulalia", "43295", employeeAreas));

    // managers :
    // Sep. 1 this year to Mar. 1 next year
    // week days + saturday, 8-20h
    // all actions
    // all spaces
    ArrayList<Area> managerAreas = new ArrayList<>(Arrays.asList(
        DirectoryAreas.findAreaById("building")
    ));
    users.add(new User("Manel", "95783", managerAreas));
    users.add(new User("Marta", "05827", managerAreas));

    // admin :
    // always=Jan. 1 this year to 2100
    // all days of the week
    // all actions
    // all spaces
    ArrayList<Area> adminAreas = new ArrayList<>(Arrays.asList(
        DirectoryAreas.findAreaById("building")
    ));
    users.add(new User("Ana", "11343", adminAreas));
  }

  public static User findUserByCredential(String credential) {
    for (User user : users) {
      if (user.getCredential().equals(credential)) {
        return user;
      }
    }
    System.out.println("user with credential " + credential + " not found");
    return null; // otherwise we get a Java error
  }

}
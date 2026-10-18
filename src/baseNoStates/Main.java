package baseNoStates;

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

import java.time.LocalDateTime;

public class Main {
  public static void main(String[] args) {
    DirectoryAreas.makeAreas();
    //DirectoryUsers.makeUsers();
    DirectoryUserGroups.makeUserGroups();


    Clock c = new Clock(1);

    c.start();
    LocalDateTime a = c.getDate();
    LocalDateTime b = c.getDate();

    System.out.println("TEMPS:");
    System.out.println(a);



    new WebServer();



  }
}

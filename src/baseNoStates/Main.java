// Package name kept from the starter code (breaks Checkstyle lowercase PackageName rule).
package baseNoStates;

/*
 * Entry point of the Access Control Unit (ACU) server.
 * Initialization order is critical: areas and doors must be built before user groups
 * (which look up areas by ID), after which the Clock and WebServer Singletons are started.
 */
public class Main {
  public static void main(String[] args) {
    DirectoryAreas.getInstance().makeAreas();
    DirectoryUserGroups.getInstance().makeUserGroups();
    Clock.getInstance().start();
    WebServer.getInstance().start();
  }
}
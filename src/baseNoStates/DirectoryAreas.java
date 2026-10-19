package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Directory class that instantiates and holds the hierarchical tree of areas
 * (partitions and spaces, via rootArea) as well as the list of all doors in the building.
 * Provides static lookup methods to find any area or door by its unique identifier.
 */
public final class DirectoryAreas {
  private static Area rootArea;
  private static ArrayList<Door> allDoors;

  public static void makeAreas() {
    // Creation of partitions (composite nodes of the hierarchy tree)
    Partition building = new Partition("building", "whole building", null);
    Partition basement = new Partition("basement", "basement floor", building);
    Partition groundFloor = new Partition("ground_floor", "ground floor", building);
    Partition floor1 = new Partition("floor1", "first floor", building);

    // Spaces directly under building
    Space exterior = new Space("exterior", "exterior", building);
    Space stairs = new Space("stairs", "stairs", building);

    // Spaces in basement
    Space parking = new Space("parking", "parking", basement);

    // Spaces in ground_floor
    Space hall = new Space("hall", "hall", groundFloor);
    Space room1 = new Space("room1", "room 1", groundFloor);
    Space room2 = new Space("room2", "room 2", groundFloor);

    // Spaces in floor1
    Space room3 = new Space("room3", "room 3", floor1);
    Space corridor = new Space("corridor", "corridor", floor1);
    Space it = new Space("IT", "IT room", floor1);

    rootArea = building;

    // Creation of doors specifying (id, fromSpace, toSpace)
    // basement
    Door d1 = new Door("D1", exterior, parking); // exterior, parking
    Door d2 = new Door("D2", stairs, parking);   // stairs, parking

    // ground floor
    Door d3 = new Door("D3", exterior, hall);    // exterior, hall
    Door d4 = new Door("D4", stairs, hall);      // stairs, hall
    Door d5 = new Door("D5", hall, room1);       // hall, room1
    Door d6 = new Door("D6", hall, room2);       // hall, room2

    // first floor
    Door d7 = new Door("D7", stairs, corridor);  // stairs, corridor
    Door d8 = new Door("D8", corridor, room3);   // corridor, room3
    Door d9 = new Door("D9", corridor, it);      // corridor, IT

    allDoors = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9));
  }

  public static Area findAreaById(String id) {
    return rootArea.findAreaById(id); // an Area or null if not found
  }

  public static Door findDoorById(String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    System.out.println("door with id " + id + " not found");
    return null; // otherwise we get a Java error
  }

  // this is needed by RequestRefresh
  public static ArrayList<Door> getAllDoors() {
    System.out.println(allDoors);
    return allDoors;
  }
}
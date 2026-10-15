package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

public class DirectoryAreas {

  private static Area rootArea;
  private static ArrayList<Door> allDoors;

  public static void makeAreas() {
    // Creación de las particiones (nodos compuestos del árbol)
    Partition building = new Partition("building", "whole building", null);
    Partition basement = new Partition("basement", "basement floor", building);
    Partition groundFloor = new Partition("ground_floor", "ground floor", building);
    Partition floor1 = new Partition("floor1", "first floor", building);

    // Espacios directamente bajo building
    Space exterior = new Space("exterior", "exterior", building);
    Space stairs = new Space("stairs", "stairs", building);

    // Espacios del basement
    Space parking = new Space("parking", "parking", basement);

    // Espacios de ground_floor
    Space hall = new Space("hall", "hall", groundFloor);
    Space room1 = new Space("room1", "room 1", groundFloor);
    Space room2 = new Space("room2", "room 2", groundFloor);

    // Espacios de floor1
    Space room3 = new Space("room3", "room 3", floor1);
    Space corridor = new Space("corridor", "corridor", floor1);
    Space it = new Space("IT", "IT room", floor1);

    rootArea = building;

    // Creación de las puertas indicando (id, fromSpace, toSpace)
    // basement
    Door d1 = new Door("D1", exterior, parking);
    Door d2 = new Door("D2", stairs, parking);

    // ground floor
    Door d3 = new Door("D3", exterior, hall);
    Door d4 = new Door("D4", stairs, hall);
    Door d5 = new Door("D5", hall, room1);
    Door d6 = new Door("D6", hall, room2);

    // first floor
    Door d7 = new Door("D7", stairs, corridor);
    Door d8 = new Door("D8", corridor, room3);
    Door d9 = new Door("D9", corridor, it);

    allDoors = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9));
  }

  public static Area findAreaById(String id) {
    return rootArea.findAreaById(id); // an Area or null if not found
  }

  public static Door findDoorById(String id){
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    System.out.println("door with id " + id + " not found");
    return null;
  }

  public static ArrayList<Door> getAllDoors(){
    System.out.println(allDoors);
    return allDoors;
  }



}

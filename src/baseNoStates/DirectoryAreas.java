package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Central in-memory repository that builds and holds the hierarchical Area tree
 * (rooted at 'rootArea') and the complete list of building doors.
 *
 * Design Pattern: Singleton.
 * Guarantees a single consistent representation of building areas and door states
 * across all request-handling threads, providing global access via getInstance().
 */
public final class DirectoryAreas {
  private static final Logger logger = LoggerFactory.getLogger(DirectoryAreas.class);
  private static DirectoryAreas uniqueInstance = null;

  private Area rootArea;
  private final ArrayList<Door> allDoors;

  private DirectoryAreas() {
    allDoors = new ArrayList<>();
  }

  // Synchronized because WebServer handles concurrent client requests in separate threads
  public static synchronized DirectoryAreas getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new DirectoryAreas();
      logger.debug(Markers.MILESTONE_2, "Created unique DirectoryAreas instance");
    }
    return uniqueInstance;
  }

  public void makeAreas() {
    Partition building = new Partition("building", "whole building", null);
    Partition basement = new Partition("basement", "basement floor", building);
    Partition groundFloor = new Partition("ground_floor", "ground floor", building);
    Partition floor1 = new Partition("floor1", "first floor", building);

    Space exterior = new Space("exterior", "exterior", building);
    Space stairs = new Space("stairs", "stairs", building);

    Space parking = new Space("parking", "parking", basement);

    Space hall = new Space("hall", "hall", groundFloor);
    Space room1 = new Space("room1", "room 1", groundFloor);
    Space room2 = new Space("room2", "room 2", groundFloor);

    Space room3 = new Space("room3", "room 3", floor1);
    Space corridor = new Space("corridor", "corridor", floor1);
    Space it = new Space("IT", "IT room", floor1);

    rootArea = building;

    // Doors connect (id, fromSpace where the reader sits, toSpace entered upon opening)
    Door d1 = new Door("D1", exterior, parking);
    Door d2 = new Door("D2", stairs, parking);

    Door d3 = new Door("D3", exterior, hall);
    Door d4 = new Door("D4", stairs, hall);
    Door d5 = new Door("D5", hall, room1);
    Door d6 = new Door("D6", hall, room2);

    Door d7 = new Door("D7", stairs, corridor);
    Door d8 = new Door("D8", corridor, room3);
    Door d9 = new Door("D9", corridor, it);

    allDoors.clear();
    allDoors.addAll(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9));
    logger.info(Markers.MILESTONE_1, "Initialized building areas and {} doors", allDoors.size());
  }

  public Area findAreaById(String id) {
    FindAreaByIdVisitor visitor = new FindAreaByIdVisitor(id);
    rootArea.accept(visitor);
    Area found = visitor.getResult();
    if (found == null) {
      logger.warn(Markers.MILESTONE_2, "Area with id '{}' not found", id);
    }
    return found;
  }

  public ArrayList<Door> getProppedDoors() {
    ProppedDoorsVisitor visitor = new ProppedDoorsVisitor();
    rootArea.accept(visitor);
    return visitor.getProppedDoors();
  }

  public Door findDoorById(String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    logger.warn(Markers.MILESTONE_1, "Door with id '{}' not found", id);
    return null;
  }

  public ArrayList<Door> getAllDoors() {
    StringBuilder summary = new StringBuilder();
    for (Door door : allDoors) {
      summary.append(door.toString()).append('\n');
    }
    logger.debug(Markers.MILESTONE_1, "All doors:\n{}", summary);
    return allDoors;
  }
}
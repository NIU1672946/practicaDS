package baseNoStates.requests;

import baseNoStates.DirectoryAreas;
import baseNoStates.Door;
import baseNoStates.Markers;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Represents a refresh request sent by the simulator to retrieve the current state
 * and physical closed status of every door in the building so the blueprint can be
 * repainted (for instance, after an automatic transition to Locked or Propped).
 */
public class RequestRefresh implements Request {
  private static final Logger logger = LoggerFactory.getLogger(RequestRefresh.class);
  private final ArrayList<JSONObject> jsonsDoors = new ArrayList<>();

  @Override
  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("doors", new JSONArray(jsonsDoors));
    return json;
  }

  @Override
  public String toString() {
    return "refresh";
  }

  @Override
  public void process() {
    logger.debug(Markers.MILESTONE_1, "Processing refresh request");
    for (Door door : DirectoryAreas.getInstance().getAllDoors()) {
      jsonsDoors.add(door.toJson());
    }
  }
}
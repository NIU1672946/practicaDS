package baseNoStates.requests;

import baseNoStates.Actions;
import baseNoStates.Area;
import baseNoStates.DirectoryAreas;
import baseNoStates.DirectoryUserGroups;
import baseNoStates.Door;
import baseNoStates.DoorsGivingAccessVisitor;
import baseNoStates.Markers;
import baseNoStates.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Represents a request to lock or unlock all doors giving access to a given Area
 * (either a single Space or a Partition). Rather than duplicating authorization logic,
 * it collects all access doors via DoorsGivingAccessVisitor and delegates each door
 * to an individual RequestReader.
 */
public class RequestArea implements Request {
  private static final Logger logger = LoggerFactory.getLogger(RequestArea.class);

  private final String credential;
  private final String action;
  private final String areaId;
  private final LocalDateTime now;
  private final ArrayList<RequestReader> requests = new ArrayList<>();
  private String userName;

  public RequestArea(String credential, String action, LocalDateTime now, String areaId) {
    this.credential = credential;
    this.areaId = areaId;
    assert action.equals(Actions.LOCK) || action.equals(Actions.UNLOCK)
        : "invalid action " + action + " for an area request";
    this.action = action;
    this.now = now;
  }

  public String getAction() {
    return action;
  }

  @Override
  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("action", action);
    json.put("areaId", areaId);
    JSONArray jsonRequests = new JSONArray();
    for (RequestReader rd : requests) {
      jsonRequests.put(rd.answerToJson());
    }
    json.put("requestsDoors", jsonRequests);
    return json;
  }

  @Override
  public String toString() {
    String displayName = (userName == null) ? "unknown" : userName;
    boolean allAuthorized = !requests.isEmpty();
    for (RequestReader rd : requests) {
      if (!rd.isAuthorized()) {
        allAuthorized = false;
        break;
      }
    }
    return "Request area userName " + displayName
        + " action " + action
        + " datetime " + now
        + " areaId " + areaId
        + " authorized " + allAuthorized;
  }

  @Override
  public void process() {
    logger.debug(Markers.MILESTONE_1, "Processing area request for area '{}' with action '{}'",
        areaId, action);
    User user = DirectoryUserGroups.getInstance().findUserByCredential(credential);
    userName = (user == null) ? "unknown" : user.getName();

    Area area = DirectoryAreas.getInstance().findAreaById(areaId);
    if (area != null) {
      DoorsGivingAccessVisitor visitor = new DoorsGivingAccessVisitor();
      area.accept(visitor);
      for (Door door : visitor.getDoors()) {
        RequestReader requestReader = new RequestReader(credential, action, now, door.getId());
        requestReader.process();
        requests.add(requestReader);
      }
    } else {
      logger.warn(Markers.MILESTONE_1, "Area '{}' not found for area request", areaId);
    }
  }
}
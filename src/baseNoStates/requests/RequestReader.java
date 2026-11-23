package baseNoStates.requests;

import baseNoStates.DirectoryAreas;
import baseNoStates.DirectoryUserGroups;
import baseNoStates.Door;
import baseNoStates.Markers;
import baseNoStates.Space;
import baseNoStates.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Represents a request targeting a single Door (originated at a physical reader or
 * remotely). Validates the four Ws of access control (Who: credential, Where: door's
 * fromSpace and toSpace, When: schedule at timestamp 'now', What: action) and delegates
 * execution to the target Door.
 */
public class RequestReader implements Request {
  private static final Logger logger = LoggerFactory.getLogger(RequestReader.class);

  private final String credential;
  private final String action;
  private final LocalDateTime now;
  private final String doorId;
  private String userName;
  private boolean authorized;
  private final ArrayList<String> reasons;
  private String doorStateName;
  private boolean doorClosed;

  public RequestReader(String credential, String action, LocalDateTime now, String doorId) {
    this.credential = credential;
    this.action = action;
    this.doorId = doorId;
    this.reasons = new ArrayList<>();
    this.now = now;
  }

  public void setDoorStateName(String name) {
    doorStateName = name;
  }

  public String getAction() {
    return action;
  }

  public boolean isAuthorized() {
    return authorized;
  }

  public void addReason(String reason) {
    reasons.add(reason);
  }

  @Override
  public String toString() {
    String displayName = (userName == null) ? "unknown" : userName;
    return "Request reader userName " + displayName
        + " action " + action
        + " datetime " + now
        + " doorId " + doorId
        + " authorized " + authorized
        + (authorized ? "" : " reasons " + reasons);
  }

  @Override
  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("authorized", authorized);
    json.put("action", action);
    json.put("doorId", doorId);
    json.put("closed", doorClosed);
    json.put("state", doorStateName);
    json.put("reasons", new JSONArray(reasons));
    return json;
  }

  @Override
  public void process() {
    logger.debug(Markers.MILESTONE_1, "Processing reader request for door '{}' with action '{}'",
        doorId, action);
    User user = DirectoryUserGroups.getInstance().findUserByCredential(credential);
    Door door = DirectoryAreas.getInstance().findDoorById(doorId);
    assert door != null : "door " + doorId + " not found";
    authorize(user, door);
    // Process even if unauthorized so the door reports its current state and logs the attempt
    door.processRequest(this);
    doorClosed = door.isClosed();
  }

  private void authorize(User user, Door door) {
    if (user == null) {
      userName = "unknown";
      authorized = false;
      addReason("User doesn't exist");
    } else {
      userName = user.getName();
      Space fromSpace = door.getFromSpace();
      Space toSpace = door.getToSpace();

      // Evaluate each condition once to avoid redundant Visitor traversals over areas
      boolean inFromSpace = user.canBeInSpace(fromSpace);
      boolean inToSpace = user.canBeInSpace(toSpace);
      boolean inSchedule = user.canSendRequests(now);
      boolean actionAllowed = user.canDoAction(action);

      authorized = inFromSpace && inToSpace && inSchedule && actionAllowed;

      if (!inFromSpace) {
        addReason("Not allowed origin " + fromSpace.getId()
            + " for group " + user.getGroupName());
      }
      if (!inToSpace) {
        addReason("Not allowed destination " + toSpace.getId()
            + " for group " + user.getGroupName());
      }
      if (!inSchedule) {
        addReason("Present date and/or time not in schedule");
      }
      if (!actionAllowed) {
        addReason("Not allowed action " + action + " for group " + user.getGroupName());
      }
    }
  }
}
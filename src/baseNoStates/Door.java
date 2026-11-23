package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Represents a physical electrified door controlled by the ACS.
 * Acts as the Context in the State design pattern: it holds its current DoorState
 * (initially Unlocked and closed) and delegates every state-dependent action to it.
 *
 * Each Door tracks both 'fromSpace' (where the credential reader is mounted) and
 * 'toSpace' (the destination room entered through the door). Both spaces are verified
 * during authorization, and the door registers itself in 'toSpace' so area requests
 * can locate all access doors leading into a room or partition.
 */
public class Door {
  private static final Logger logger = LoggerFactory.getLogger(Door.class);

  private final String id;
  private boolean closed;
  private DoorState state;
  private final Space fromSpace;
  private final Space toSpace;

  public Door(String id, Space fromSpace, Space toSpace) {
    this.id = id;
    this.closed = true;
    this.state = new Unlocked(this);
    this.fromSpace = fromSpace;
    this.toSpace = toSpace;
    this.toSpace.addDoor(this);
  }

  public void processRequest(RequestReader request) {
    // Door executes the action because only it owns its physical closed status and DoorState
    if (request.isAuthorized()) {
      String action = request.getAction();
      logger.debug(Markers.MILESTONE_1, "Door '{}' executing authorized action '{}'", id, action);
      doAction(action);
    } else {
      logger.warn(Markers.MILESTONE_1, "Request for door '{}' with action '{}' not authorized",
          id, request.getAction());
    }
    request.setDoorStateName(getStateName());
  }

  private void doAction(String action) {
    switch (action) {
      case Actions.OPEN:
        state.open();
        break;
      case Actions.CLOSE:
        state.close();
        break;
      case Actions.LOCK:
        state.lock();
        break;
      case Actions.UNLOCK:
        state.unlock();
        break;
      case Actions.UNLOCK_SHORTLY:
        state.unlockShortly();
        break;
      default:
        assert false : "Unknown action " + action;
        System.exit(-1);
    }
  }

  public boolean isClosed() {
    return closed;
  }

  public String getId() {
    return id;
  }

  public String getStateName() {
    return state.getName();
  }

  public Space getFromSpace() {
    return fromSpace;
  }

  public Space getToSpace() {
    return toSpace;
  }

  public void setClosed(boolean closed) {
    this.closed = closed;
  }

  public void setState(DoorState state) {
    logger.debug(Markers.MILESTONE_1, "Door '{}' transitioning from '{}' to '{}'",
        id, this.state != null ? this.state.getName() : "none", state.getName());
    this.state = state;
  }

  @Override
  public String toString() {
    return "Door{id='" + id + "', closed=" + closed + ", state=" + getStateName() + "}";
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("state", getStateName());
    json.put("closed", closed);
    return json;
  }
}
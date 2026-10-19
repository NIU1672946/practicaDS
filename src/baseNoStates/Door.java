package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;

/*
 * Represents a physical door controlled by the ACS.
 * Acts as the Context class in the State design pattern: it holds a reference to its
 * current DoorState (initially Unlocked) and delegates state-dependent actions to it.
 *
 * Each door also knows the two spaces it connects: 'fromSpace' (where the credential
 * reader is located) and 'toSpace' (the destination space entered when crossing the door).
 * Knowing both spaces is required to authorize reader requests based on user permissions.
 */
public class Door {
  private final String id;
  private boolean closed; // physically closed or open
  private DoorState state;
  private final Space fromSpace;
  private final Space toSpace;

  public Door(String id, Space fromSpace, Space toSpace) {
    this.id = id;
    this.closed = true;
    this.state = new Unlocked(this); // initial state according to the state diagram
    this.fromSpace = fromSpace;
    this.toSpace = toSpace;
    // Register this door in the destination space so area requests can find all access doors
    this.toSpace.addDoor(this);
  }

  public void processRequest(RequestReader request) {
    // it is the Door that process the request because the door has and knows
    // its state, and if closed or open
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
    } else {
      System.out.println("not authorized");
    }
    request.setDoorStateName(getStateName());
  }

  private void doAction(String action) {
    // Delegate the action to the current DoorState object (State pattern)
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

  public String getStateName() {return state.getName();}

  public Space getFromSpace() {return fromSpace;}

  public Space getToSpace() {return toSpace;}

  public void setClosed(boolean closed) {this.closed = closed;}

  public void setState(DoorState state){this.state = state;}

  @Override
  public String toString() {
    return "Door{"
        + ", id='" + id + '\''
        + ", closed=" + closed
        + ", state=" + getStateName()
        + "}";
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("state", getStateName());
    json.put("closed", closed);
    return json;
  }
}

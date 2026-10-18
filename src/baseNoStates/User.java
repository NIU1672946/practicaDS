package baseNoStates;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class User {
  private final String name;
  private UserGroup userGroup;
  private final String credential;




  public User(String name, UserGroup userGroup,String credential) {
    this.name = name;
    this.userGroup = userGroup;
    this.credential = credential;
  }

  public String getCredential() {
    return credential;
  }

  public String getName(){ return name;}

  /**
   * Comprueba si el usuario tiene permiso para estar en un espacio determinado.
   */
  public boolean canBeInSpace(Space sp) {
    return getSpaces().contains(sp);
  }

  /**
   * Obtiene todos los espacios individuales contenidos dentro de las áreas
   * asignadas al usuario.
   */

  public boolean canSendRequests(LocalDateTime now){
    return userGroup.canSendRequests(now);
  }

  public boolean canDoAction(String action){
    return userGroup.canDoAction(action);
  }

  private ArrayList<Space> getSpaces() {
    ArrayList<Space> spaces = new ArrayList<>();
    for (Area area : userGroup.getAreas()) {
      spaces.addAll(area.getSpaces());
    }
    return spaces;
  }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }
}

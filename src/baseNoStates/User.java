package baseNoStates;

import java.util.ArrayList;

public class User {
  private final String name;
  private final String credential;
  private final ArrayList<Area> areas;

  public User(String name, String credential, ArrayList<Area> areas) {
    this.name = name;
    this.credential = credential;
    this.areas = areas;
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
  private ArrayList<Space> getSpaces() {
    ArrayList<Space> spaces = new ArrayList<>();
    for (Area area : areas) {
      spaces.addAll(area.getSpaces());
    }
    return spaces;
  }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }
}

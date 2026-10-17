package baseNoStates;

public class Propped extends DoorState {
    public Propped(Door door) {
        super(door);
        this.name = States.PROPPED;
    }
    @Override
    public void open(){
        //it will always be open since the state Propped is only attainable from having an UnlockedShortly door be open.
        System.out.println("Can't open door " + door.getId() + " because it's already open");
    }

    @Override
    public void close(){
        door.setClosed(true);
        door.setState(new Locked(door));
    }

    @Override
    public void lock(){
        door.setState(new Locked(door));
    }

    @Override
    public void unlock(){
        System.out.println("Can't unlock door " + door.getId() + "because it's propped");

    }
    @Override
    public void unlockShortly(){
        System.out.println("Can't unlock shortly door " + door.getId() + "because it's propped");
    }
}

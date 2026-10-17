package baseNoStates;

import java.time.LocalDateTime;
import java.time.Duration;


public class UnlockedShortly extends DoorState {
    LocalDateTime t0 = null;
    final int time_to_transition = 10;
    private static final Clock clock = new Clock();

    private boolean readyToDelete = false;

    public UnlockedShortly(Door door) {
        super(door);
        this.name = States.UNLOCKED_SHORTLY;
        clock.addObserver(this);

    }
    public boolean getReadyToDelete(){
        return readyToDelete;
    }

    @Override
    public void open(){
        if (door.isClosed()){
            door.setClosed(false);
        } else {
            System.out.println("Can't open door " + door.getId() + " because it's already open");
        }
    }

    @Override
    public void close(){
        if (door.isClosed()) {
            System.out.println("Can't close door " + door.getId() + " because it's already closed");
        } else {
            door.setClosed(true);
        }
    }

    @Override
    public void lock(){
        System.out.println("Can't lock door " + door.getId() + " because it's unlocked shortly");
    }

    @Override
    public void unlock(){
        System.out.println("Can't unlock door " + door.getId() + " because it's unlocked shortly");
    }

    @Override
    public void unlockShortly(){
        System.out.println("Can't unlock shortly door " + door.getId() + "because it already is");//is this how it's inteded to work?
        //like you can't perpetually keep on unlocking shortly a door while it already is, right?
    }
/*
    public void deleteSelfFromObservers(){//is this overkill? I think it's good that the code deletes observers from the observable list so
        //u don't end up with a memory leak. But I don't know if this is spected from me.
        //I figure that since it's a good practice I'll keep it.

        clock.deleteObserver(this);
    }
*/

    public void updateTime(LocalDateTime time){
        if (t0 == null){
            t0 = time;
        }else{
            //if ((time.getSecond() - t0.getSecond()) >= time_to_transition){
             //   deleteSelfFromObservers();
             //   transition();
            //}
            if (Duration.between(t0, time).getSeconds() >= time_to_transition){
                //deleteSelfFromObservers();
                readyToDelete = true;
                transition();
            }
        }
    }

    public void transition(){//after 10 seconds from the creation
        if (door.isClosed()){
            door.setState(new Locked(door));
        }else{
            door.setState(new Propped(door));
        }
    }
}

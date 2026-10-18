package baseNoStates;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

public class Clock {
    private LocalDateTime date;
    private Timer timer;
    private int period; // seconds

    private ArrayList<UnlockedShortly> observers = new ArrayList<UnlockedShortly>();



    public Clock(int period) {
        this.period = period;
        timer = new Timer();
        this.start();

    }

    public Clock() {
        this.period = 1;
        timer = new Timer();
        this.start();

    }

    public void updateObservers(LocalDateTime time){//iterates through the observers array and updates each one of them.
        for (UnlockedShortly currentObserver : observers){
            currentObserver.updateTime(time);
        }
    }

    public void addObserver(UnlockedShortly observer){
        observers.add(observer);
    }

/*
    public void deleteObserver(UnlockedShortly observer){
        int i;

        for (i = 0; i<observers.size(); i++){
            if (observers.get(i) == observer){
                break;
            }
        }

        observers.remove(i);

        //yes I do know that it would be way more efficient to just return the index in wich the observer is added in addObserver,
        //make UnlockedShortly store it. This way deleteObserver() could just have one int parameter named index or whatever
        //and the funciton would be as simple and efficient as observers.remove(index).

        //but I'm worreid that could cause problem on the long run. What if an element previous to the one you are deleting gets deleted? you wouldn't be deleting
        //the right one but the next one. what if there's no next one?

        //for me this is safer althought less efficient.

        //espero que valoris que faci aquestes parrafades en angles enlloc de demanar-li a la IA que me les faci . . .

    }

 */
    public void deleteReadyStates(){
        for (int i = 0; i< observers.size(); i++){
            if (observers.get(i).getReadyToDelete()){
                observers.remove(i);
            }
        }
    }

    public void start() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                // Instance of an anonymous class
                deleteReadyStates();

                date = LocalDateTime.now();
                updateObservers(date); // update all observers

                //System.out.println("run() executed at " + date);
            }
        };

        timer.scheduleAtFixedRate(
                repeatedTask,
                0,
                1000 * period
        );
    }

    public void stop() {
        timer.cancel();
    }

    public int getPeriod() {
        return period;
    }

    public LocalDateTime getDate() {
        return date;
    }
}

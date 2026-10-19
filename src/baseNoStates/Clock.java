package baseNoStates;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/*
 * System clock running in a separate background thread using java.util.Timer and TimerTask.
 * Acts as the Observable subject in the Observer design pattern.
 *
 * Instead of pausing the main execution thread with Thread.sleep(10000) (which would freeze
 * the ACU server and prevent it from processing other requests), this clock ticks periodically
 * every 'period' seconds and notifies all subscribed UnlockedShortly observers of the current time.
 */
@SuppressWarnings("deprecation")
public class Clock extends Observable {
  private LocalDateTime date;
  private final Timer timer;
  private final int period; // seconds

  public Clock(int period) {
    this.period = period;
    this.timer = new Timer(true);
  }

  public Clock() {
    this(1);
  }

  public void start() {
    TimerTask repeatedTask = new TimerTask() {
      @Override
      public void run() {
        // Instance of an anonymous class executed periodically by the timer thread
        date = LocalDateTime.now();
        setChanged();
        notifyObservers(date); // update all subscribed UnlockedShortly observers
        // System.out.println("run() executed at " + date);
      }
    };

    timer.scheduleAtFixedRate(
        repeatedTask,
        0,
        1000L * period
    );
  }

  public void stop() {timer.cancel();}

  public int getPeriod() {return period;}

  public LocalDateTime getDate() {return date;}
}
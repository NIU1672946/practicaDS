package baseNoStates;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Global system clock running on a background daemon thread via java.util.Timer.
 * Plays two design-pattern roles:
 * 1) Observable (Observer pattern): ticks periodically every 'period' seconds and
 *    notifies subscribed UnlockedShortly states of the current time, avoiding
 *    Thread.sleep() which would block the entire ACU server.
 * 2) Singleton: guarantees at most one clock thread exists across the ACU, preventing
 *    wasted threads and desynchronized timers.
 */
// java.util.Observable is deprecated since Java 9, but required by the project spec.
@SuppressWarnings("deprecation")
public class Clock extends Observable {
  private static final Logger logger = LoggerFactory.getLogger(Clock.class);
  private static final int DEFAULT_PERIOD_SECONDS = 1;
  private static Clock uniqueInstance = null;

  private LocalDateTime date;
  private final Timer timer;
  private final int period;
  private boolean running;

  private Clock(int period) {
    this.period = period;
    // Daemon thread so the JVM exits cleanly when the main server terminates
    this.timer = new Timer(true);
    this.running = false;
  }

  // Synchronized for thread-safety because multiple SocketThreads may access the clock
  public static synchronized Clock getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new Clock(DEFAULT_PERIOD_SECONDS);
      logger.debug(Markers.MILESTONE_2, "Created unique Clock instance (period={}s)",
          DEFAULT_PERIOD_SECONDS);
    }
    return uniqueInstance;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public synchronized void start() {
    if (running) {
      return;
    }
    running = true;
    TimerTask repeatedTask = new TimerTask() {
      @Override
      public void run() {
        date = LocalDateTime.now();
        setChanged();
        notifyObservers(date);
      }
    };
    timer.scheduleAtFixedRate(repeatedTask, 0, 1000L * period);
    logger.info(Markers.MILESTONE_1, "Clock started with period {} second(s)", period);
  }
}
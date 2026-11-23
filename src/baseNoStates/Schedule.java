package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/*
 * Encapsulates the temporal dimension ("when") of a UserGroup's access rights:
 * a valid date interval, allowed days of the week, and a daily time range.
 * Extracting schedule logic into its own class keeps UserGroup cohesive and allows
 * replacing a group's schedule independently of its users or areas.
 */
public class Schedule {
  private final LocalDate fromDate;
  private final LocalDate toDate;
  private final ArrayList<DayOfWeek> daysOfWeek;
  private final LocalTime fromTime;
  private final LocalTime toTime;

  public Schedule(LocalDate fromDate, LocalDate toDate,
                  ArrayList<DayOfWeek> daysOfWeek,
                  LocalTime fromTime, LocalTime toTime) {
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.daysOfWeek = daysOfWeek;
    this.fromTime = fromTime;
    this.toTime = toTime;
  }

  public boolean includes(LocalDateTime dateTime) {
    LocalDate currentDate = dateTime.toLocalDate();
    LocalTime currentTime = dateTime.toLocalTime();
    DayOfWeek currentDay = dateTime.getDayOfWeek();

    boolean validDate = !currentDate.isBefore(fromDate) && !currentDate.isAfter(toDate);
    boolean validDay = daysOfWeek.contains(currentDay);
    boolean validTime = !currentTime.isBefore(fromTime) && !currentTime.isAfter(toTime);

    return validDate && validDay && validTime;
  }
}
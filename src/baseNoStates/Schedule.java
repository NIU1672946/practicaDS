package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;


//new class to handle schedules comparision and not overload user groups with too much attributes referencing time of day and weekdays.

public class Schedule {
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final ArrayList<DayOfWeek> daysOfWeek;
    private final LocalTime fromTime;
    private final LocalTime toTime;

    public Schedule(LocalDate fromDate, LocalDate toDate,
                    ArrayList<DayOfWeek> daysOfWeek,
                    LocalTime fromTime, LocalTime toTime){

        this.fromDate = fromDate;
        this.toDate = toDate;
        this.daysOfWeek = daysOfWeek;
        this.fromTime = fromTime;
        this.toTime = toTime;

    }

    public boolean includes(LocalDateTime now ){//verifies if a instant is included inside the schedule
        LocalDate currentDate = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();
        DayOfWeek currentDay = now.getDayOfWeek();

        boolean validDate = !currentDate.isBefore(fromDate) && !currentDate.isAfter(toDate);
        boolean validDay = daysOfWeek.contains(currentDay);
        boolean validTime = !currentTime.isBefore(fromTime) && !currentTime.isAfter(toTime);

        return validDate && validDay && validTime;
    }
}

package app.DTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Timekeeping implements Serializable{

    private LocalDate days;
    private LocalTime timeStart;
    private LocalTime timeEnd;

    public Timekeeping() {
        this.days = LocalDate.now();
        this.timeEnd = null;
    }

    public Timekeeping(LocalDate days, LocalTime timeStart, LocalTime timeEnd) {
        this.days = days;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public LocalDate getDays() {
        return days;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setDays(LocalDate days) {
        this.days = days;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public String toString() {
        return "Timekeeping{" + ", days=" + days + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + '}';
    }

}

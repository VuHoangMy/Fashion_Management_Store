package app.DTO;

import java.io.Serializable;
import java.time.LocalDate;

public class Payroll implements Serializable{

    private int numberOfGoesLate;
    private int numberOfLeaveEarly;
    private int numberOfDaysOff;
    private LocalDate days;
    private Fines fines;
    private double totalSalary;

    public Payroll() {
    }

    public Payroll(int numberOfGoesLate, int numberOfLeaveEarly, int numberOfDaysOff, LocalDate days, Fines fines, double totalSalary) {
        this.numberOfGoesLate = numberOfGoesLate;
        this.numberOfLeaveEarly = numberOfLeaveEarly;
        this.numberOfDaysOff = numberOfDaysOff;
        this.days = days;
        this.fines = fines;
        this.totalSalary = totalSalary;
    }

    public int getNumberOfGoesLate() {
        return numberOfGoesLate;
    }

    public void setNumberOfGoesLate(int numberOfGoesLate) {
        this.numberOfGoesLate = numberOfGoesLate;
    }

    public int getNumberOfLeaveEarly() {
        return numberOfLeaveEarly;
    }

    public void setNumberOfLeaveEarly(int numberOfLeaveEarly) {
        this.numberOfLeaveEarly = numberOfLeaveEarly;
    }

    public int getNumberOfDaysOff() {
        return numberOfDaysOff;
    }

    public void setNumberOfDaysOff(int numberOfDaysOff) {
        this.numberOfDaysOff = numberOfDaysOff;
    }

    public LocalDate getDays() {
        return days;
    }

    public void setDays(LocalDate days) {
        this.days = days;
    }

    public Fines getFines() {
        return fines;
    }

    public void setFines(Fines fines) {
        this.fines = fines;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }

    @Override
    public String toString() {
        return "Payroll{" + "numberOfGoesLate=" + numberOfGoesLate + ", numberOfLeaveEarly=" + numberOfLeaveEarly + ", numberOfDaysOff=" + numberOfDaysOff + ", days=" + days + ", fines=" + fines + ", totalSalary=" + totalSalary + '}';
    }

}

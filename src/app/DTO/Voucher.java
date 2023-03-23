package app.DTO;

import java.io.Serializable;
import java.time.LocalDate;

public class Voucher implements Serializable{

    private String ID;
    private String name;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String note;
    private double discountPercent;
    private double totalMoneyCondition;

    public Voucher() {
    }

    public Voucher(String ID, String name, LocalDate fromDate, LocalDate toDate, String note, double totalMoneyCondition, double discountPercent) {
        this.ID = ID;
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.note = note;
        this.totalMoneyCondition = totalMoneyCondition;
        this.discountPercent = discountPercent;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getTotalMoneyCondition() {
        return totalMoneyCondition;
    }

    public void setTotalMoneyCondition(double totalMoneyCondition) {
        this.totalMoneyCondition = totalMoneyCondition;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }
    
    

    @Override
    public String toString() {
        return "Voucher{" + "ID=" + ID + ", name=" + name + ", fromDate=" + fromDate + ", toDate=" + toDate + ", note=" + note + ", totalMoneyCondition=" + totalMoneyCondition + '}';
    }

}

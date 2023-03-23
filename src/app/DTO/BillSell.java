package app.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public class BillSell implements Serializable {

    private String ID;
    private LocalDateTime dateSell;
//    private ItemsList itemsList;
    private ArrayList<Items> itemsList;
    
    private String note;
    private Staff staff;
    private Customer customer;
    private String paymentsBy;

    private double moneyCustomerGive;
    private double totalInvoice;
    private Voucher voucher; 

    public BillSell() {

    }

    public BillSell(String ID, String note, String paymentsBy, double moneyCustomerGive, double totalInvoice, 
            ArrayList<Items> itemsList, Staff staff, Customer customer, Voucher voucher) {
        this.ID = ID;
        this.dateSell = LocalDateTime.now();
        this.itemsList = itemsList;
        this.note = note;
        this.staff = staff;
        this.paymentsBy = paymentsBy;
        this.moneyCustomerGive = moneyCustomerGive;
        this.totalInvoice = totalInvoice;
        this.customer = customer;
        this.voucher = voucher;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public LocalDateTime getDateSell() {
        return dateSell;
    }

    public void setDateSell(LocalDateTime dateSell) {
        this.dateSell = dateSell;
    }

    public ArrayList<Items> getItemsList() {
        return itemsList;
    }

    public void setItemsList(ArrayList<Items> itemsList) {
        this.itemsList = itemsList;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getPaymentsBy() {
        return paymentsBy;
    }

    public void setPaymentsBy(String paymentsBy) {
        this.paymentsBy = paymentsBy;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getMoneyCustomerGive() {
        return moneyCustomerGive;
    }

    public void setMoneyCustomerGive(double moneyCustomerGive) {
        this.moneyCustomerGive = moneyCustomerGive;
    }

    public double getTotalInvoice() {
        return totalInvoice;
    }

    public void setTotalInvoice(double totalInvoice) {
        this.totalInvoice = totalInvoice;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }
    
    public static Comparator<BillSell> billSellDateCompareASC = new Comparator<BillSell>() {
        @Override
        public int compare(BillSell b1, BillSell b2) {
            LocalDateTime d1 = b1.getDateSell();
            LocalDateTime d2 = b2.getDateSell();

            /*For ascending order*/
            return d1.compareTo(d2);
        }
    };
    
    public static Comparator<BillSell> billSellDateCompareDES = new Comparator<BillSell>() {
        @Override
        public int compare(BillSell b1, BillSell b2) {
            LocalDateTime d1 = b1.getDateSell();
            LocalDateTime d2 = b2.getDateSell();

            /*For ascending order*/
            return d2.compareTo(d1);
        }
    };
    
}

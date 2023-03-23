package app.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BillImportGoods implements Serializable {

    private String ID;
    private LocalDateTime dateImportGoods;
    private String note;
    private double priceImport;
    private int amountImport;
    private Supplier supplier;
    private Items items;

    public BillImportGoods(String ID, Items items, Supplier supplier, String note, int amountImport, double priceImport) {
        this.ID = ID;
        this.items = items;
        this.supplier = supplier;
        this.dateImportGoods = LocalDateTime.now();
        this.note = note;
        this.amountImport = amountImport;
        this.priceImport = priceImport;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getPriceImport() {
        return priceImport;
    }

    public void setPriceImport(double priceImport) {
        this.priceImport = priceImport;
    }

    public int getAmountImport() {
        return amountImport;
    }

    public void setAmountImport(int amountImport) {
        this.amountImport = amountImport;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public LocalDateTime getDateImportGoods() {
        return dateImportGoods;
    }

    public void setDateImportGoods(LocalDateTime dateImportGoods) {
        this.dateImportGoods = dateImportGoods;
    }

    @Override
    public String toString() {
        return "BillImportGoods{" + "ID=" + ID + ", dateImportGoods=" + dateImportGoods + ", note=" + note + ", priceImport=" + priceImport + ", amountImport=" + amountImport + ", supplier=" + supplier + ", items=" + items + '}';
    }

}

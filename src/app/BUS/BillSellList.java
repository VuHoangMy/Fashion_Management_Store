package app.BUS;

import app.DTO.BillSell;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class BillSellList implements Serializable{

    private ArrayList<BillSell> billSellList;
    private File file;

    public BillSellList() {
        this.billSellList = new ArrayList<>();
    }

    public BillSellList(ArrayList<BillSell> billSellList, File file) {
        this.billSellList = billSellList;
        this.file = file;
    }

    public ArrayList<BillSell> getBillSellList() {
        return billSellList;
    }

    public void initFile() {
        this.file = new File("src/app/Data/billSellList.txt");
    }

    public String createNewID() {
        int number = this.billSellList.size() + 1000;
        return String.valueOf(number);
    }

    public void addNewBillSell(BillSell bill) {
        this.billSellList.add(bill);
    }
    
    public BillSell findBillByID(String ID){
        for(BillSell bil : this.billSellList){
            if(bil.getID().equals(bil)){
                return bil;
            }
        }
        return null;
    }

    public void showAllBill() {
        for (BillSell bs : this.billSellList) {
            System.out.println(bs.getCustomer());
            System.out.println("Total Invoice " + bs.getTotalInvoice());
            System.out.println("Total MoneyCustomerGive " + bs.getMoneyCustomerGive());
        }
    }

    public void writeToFile() {
        try {
            FileOutputStream fout = new FileOutputStream(this.file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            for (BillSell bill : this.billSellList) {
                oos.writeObject(bill);
            }
            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Save BillSell Success");
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("ERROR: Save BillSell ");
        }
    }

    public void readFromFile() {
        try {
            FileInputStream fis = new FileInputStream(this.file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            BillSell newBill = null;
            while (true) {
                Object obj = ois.readObject();
                if (obj == null) {
                    break;
                } else {
                    newBill = (BillSell) obj;
                    this.billSellList.add(newBill);
                }
            }
            fis.close();
            ois.close();
        } catch (Exception e) {
        }
    }

    public void appendACustomerIntoFile(BillSell billSell) {
        try {
            FileOutputStream fout = new FileOutputStream(this.file, true);
            ObjectOutputStream oos = new ObjectOutputStream(fout) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };

            oos.writeObject(billSell);

            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Append Customer Success");
        } catch (Exception e) {
            System.out.println("Append file" + e);
        }
    }

}

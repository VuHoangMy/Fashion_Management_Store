
package app.BUS;

import app.DTO.Supplier;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SupplierList {
    private Supplier supplier;
    private ArrayList<Supplier> suppliersList;
    private File file;

    public SupplierList() {
        this.suppliersList = new ArrayList<>();
    }
    
    // Func
    public void initFile(){
        this.file = new File("src/app/Data/supplierList.txt");
    }
    
    public void addNewSupplier(Supplier s){
        this.suppliersList.add(s);
    }
    
    public String createItemsID(){
        int number = (this.suppliersList.size() + 100);
        return String.valueOf(number);
    }
    
     public void showAllItems(){
        for(Supplier s : this.suppliersList){
            System.out.println(s);
        }
    }
     
     public ArrayList<Supplier> getSupplierList(){
         return this.suppliersList;
     }
    
   //Find
     
     public Supplier findSupplierByID(String ID){
         for(Supplier supplier : this.suppliersList){
             if(supplier.getID().equals(ID)){
                 return supplier;
             }
         }
         return null;
     }
     
     public Supplier findSupplierByName(String name){
         for(Supplier supplier : this.suppliersList){
             if(supplier.getName().equals(name)){
                 return supplier;
             }
         }
         return null;
     }
    
    //Work with file
    
     public void writeToFile() {
        try {
            FileOutputStream fout = new FileOutputStream(this.file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            for (Supplier p: this.suppliersList) {
                oos.writeObject(p);
            }
            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Save Supplier Success");
        } catch (Exception e) {
            System.out.println("ERROR: Save Supplier " + e);
        }
    }
    
    public void readFromFile() {
        try {
            FileInputStream fis = new FileInputStream(this.file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Supplier newSupplier = null;
            while (true) {
                Object obj = ois.readObject();
                if (obj == null) {
                    break;
                } else {
                    newSupplier = (Supplier) obj;
                    this.suppliersList.add(newSupplier);
                }
            }
            fis.close();
            ois.close();
        } catch (Exception e) {
        }
    }
    
    public void appendASupplierIntoFile(Supplier supplier) {
        try {
            FileOutputStream fout = new FileOutputStream(this.file, true);
            ObjectOutputStream oos = new ObjectOutputStream(fout) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
            oos.writeObject(supplier);
            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Append Supplier  Success");
        } catch (Exception e) {
            System.out.println("Append file" + e);
        }
    }
    
}

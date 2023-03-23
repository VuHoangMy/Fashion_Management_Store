
package app.BUS;

import app.DTO.Products;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ProductsList {
    
    private File file;
    private ArrayList<Products> productsList;

    public ProductsList() {
        this.productsList = new ArrayList<>();
    }

    public ProductsList(ArrayList<Products> productsList) {
        this.productsList = productsList;
    }

    public ArrayList<Products> getProductsList() {
        return productsList;
    }
    
    public void addNewItem(Products i){
        this.productsList.add(i);
    }
    
    //Func
    public void initFile(){
        this.file = new File("src/app/Data/productsList.txt");
    }
    
    public String createItemsID(){
        int number = this.productsList.size() + 10;
        return String.valueOf(number);
    }
    
    public void showAllItems(){
        for(Products i : this.productsList){
            System.out.println(i);
        }
    }
    
    //Find
    public Products findItemsByID(String ID){
        for(Products i : this.productsList){
            if(i.getID().equals(ID)){
                return i;
            }
        }
        return null;
    }
    
    public ProductsList FillterProductByStutus(boolean status){
        ProductsList tmp = new ProductsList();
       for(Products products : this.productsList){
           if(products.getStatus() == status){
               tmp.addNewItem(products);
           }
       }
        return tmp;
    }
    
    public ProductsList FillterProductByType(String type){
        ProductsList tmp = new ProductsList();
       for(Products products : this.productsList){
           if(products.getType().equals(type)){
               tmp.addNewItem(products);
           }
       }
        return tmp;
    }
    
    // Work with file
    public void writeToFile() {
        try {
            FileOutputStream fout = new FileOutputStream(this.file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            for (Products p: this.productsList) {
                oos.writeObject(p);
            }
            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Save Product Success");
        } catch (Exception e) {
            System.out.println("ERROR: Save Products ");
        }
    }
    
    public void readFromFile() {
        try {
            FileInputStream fis = new FileInputStream(this.file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Products newProducts = null;
            while (true) {
                Object obj = ois.readObject();
                if (obj == null) {
                    break;
                } else {
                    newProducts = (Products) obj;
                    this.productsList.add(newProducts);
                }
            }
            fis.close();
            ois.close();
        } catch (Exception e) {
        }
    }
    
    public void appendAProductsIntoFile(Products products) {
        try {
            FileOutputStream fout = new FileOutputStream(this.file, true);
            ObjectOutputStream oos = new ObjectOutputStream(fout) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
            oos.writeObject(products);
            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Append Items Success");
        } catch (Exception e) {
            System.out.println("Append file" + e);
        }
    }
    
    //End
}

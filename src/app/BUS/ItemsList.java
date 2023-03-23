package app.BUS;

import app.CommonFuncion.CommonFunction;
import app.DTO.Items;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ItemsList {

    private ArrayList<Items> itemsList;
    private File file;

    public ItemsList() {
        this.itemsList = new ArrayList<>();
    }

    public ItemsList(ArrayList<Items> itemsList) {
        this.itemsList = itemsList;
    }

    public ArrayList<Items> getItemslist() {
        return this.itemsList;
    }

    public void initFile() {
        this.file = new File("src/app/Data/itemsList.txt");
    }

    public void showAllItems() {
        for (Items i : this.itemsList) {
            System.out.println(i);
        }
    }

    public String createItemID() {
        int number = this.itemsList.size() + 500;
        return String.valueOf(number);
    }

    public void addNewItem(Items i) {
        this.itemsList.add(i);
    }

    //
    public void removeItemByID(String ID) {
        try {
            for (Items i : this.itemsList) {
                if (i.getID().equals(ID)) {
                    this.itemsList.remove(i);
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Remove Error");
        }
    }

    //Find
    public Items findItemsByID(String ID) {
        for (Items i : this.itemsList) {
            if (i.getID().equals(ID)) {
                return i;
            }
        }
        return null;
    }

    public ItemsList findListItemByIDOrName(String s) {
        ItemsList TMP = new ItemsList();
        if (CommonFunction.isNumeric(s)) {
            for (Items i : this.itemsList) {
                if (i.getID().contains(s)) {
                    TMP.addNewItem(i);
                }
            }
        } else {
            for (Items i : this.itemsList) {
                if (i.getName().toLowerCase().contains(s.toLowerCase())) {
                    TMP.addNewItem(i);
                }
            }
        }
        return TMP;
    }

    /*
    *Change inventory number when import new goods or sell goods
     */
    public void updateItemInventory(String ID, int amount) {
        if (findItemsByID(ID) != null) {
            findItemsByID(ID).setInventoryNumber(amount);
        }
    }

    // Work with file
    public void writeToFile() {
        try {
            FileOutputStream fout = new FileOutputStream(this.file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            for (Items i : this.itemsList) {
                oos.writeObject(i);
            }
            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Save Items Success");
        } catch (Exception e) {
            System.out.println("ERROR: Save Items ");
        }
    }

    public void readFromFile() {
        try {
            FileInputStream fis = new FileInputStream(this.file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Items newItems = null;
            while (true) {
                Object obj = ois.readObject();
                if (obj == null) {
                    break;
                } else {
                    newItems = (Items) obj;
                    this.itemsList.add(newItems);
                }
            }
            fis.close();
            ois.close();
        } catch (Exception e) {
        }
    }

    public void appendAItemsIntoFile(Items item) {
        try {
            FileOutputStream fout = new FileOutputStream(this.file, true);
            ObjectOutputStream oos = new ObjectOutputStream(fout) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
            oos.writeObject(item);
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

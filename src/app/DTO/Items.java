package app.DTO;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Items implements Serializable {

    private String ID;
    private String name;
    private double price;
    private String size;
    private Color color;
    private String material;
    private int inventoryNumber;
    private ArrayList<File> imageList;

    public Items() {
    }

    public Items(Items items) {
        this(items.getID(), items.getName(), items.getPrice(), items.getSize(), items.getColor(), items.getMaterial(), items.getImageList());
    }

    public Items(String ID, String name, double price, String size, Color color, String material, ArrayList<File> imageList) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.size = size;
        this.color = color;
        this.material = material;
        this.inventoryNumber = 0;
        this.imageList = imageList;
    }

    public void initImageList() {
        this.imageList = new ArrayList<>();
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(int inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public ArrayList<File> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<File> imageList) {
        this.imageList = imageList;
    }

    public void addNewImage(File image) {
        this.imageList.add(image);
    }

    @Override
    public String toString() {
        return "Items{" + "ID=" + ID + ", name=" + name + ", price=" + price + ", size=" + size + ", color=" + color + ", material=" + material + ", inventoryNumber=" + inventoryNumber + ", imageList=" + imageList + '}';
    }

    // Sort
    public static Comparator<Items> ItemPriceCompareASC = new Comparator<Items>() {
        @Override
        public int compare(Items o1, Items o2) {
            /*For ascending order*/
            return (int) (o1.getPrice() - o2.getPrice());
        }
    };

    public static Comparator<Items> ItemPriceCompareDES = new Comparator<Items>() {
        @Override
        public int compare(Items o1, Items o2) {
            /*For descending  order*/
            return (int) (o2.getPrice() - o1.getPrice());
        }
    };

    public static Comparator<Items> ItemInventoryCompareASC = new Comparator<Items>() {
        @Override
        public int compare(Items o1, Items o2) {
            /*For ascending  order*/
            return (int) (o1.getInventoryNumber() - o2.getInventoryNumber());
        }
    };

    public static Comparator<Items> ItemInventoryCompareDES = new Comparator<Items>() {
        @Override
        public int compare(Items o1, Items o2) {
            /*For descending  order*/
            return (int) (o2.getInventoryNumber() - o1.getInventoryNumber());
        }
    };

    public static Comparator<Items> ItemNameCompareASC = new Comparator<Items>() {
        @Override
        public int compare(Items o1, Items o2) {
            String itemName1 = o1.getName().toLowerCase();
            String itemName2 = o2.getName().toLowerCase();

            /*For ascending order*/
            return itemName1.compareTo(itemName2);
        }
    };

    public static Comparator<Items> ItemNameCompareDES = new Comparator<Items>() {
        @Override
        public int compare(Items o1, Items o2) {
            String itemName1 = o1.getName().toLowerCase();
            String itemName2 = o2.getName().toLowerCase();
            /*For descending  order*/
            return itemName2.compareTo(itemName1);

        }
    };

}


package app.DTO;

import java.io.Serializable;

public class Customer implements Serializable{
    private String ID;
    private String name;
    private String phonenumber;
    private String address;
    private boolean isMale;

    public Customer() {
    }
    
    public Customer(String name, String phonenumber, String address) {
        this.ID = "";
        this.name = name;
        this.phonenumber = phonenumber;
        this.address = address;
        this.isMale = true;
    }

    public Customer(String ID, String name, String phonenumber, String address, boolean isMale) {
        this.ID = ID;
        this.name = name;
        this.phonenumber = phonenumber;
        this.address = address;
        this.isMale = isMale;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(boolean isMale) {
        this.isMale = isMale;
    }

    @Override
    public String toString() {
        return "Customer{" + "ID=" + ID + ", name=" + name + ", phonenumber=" + phonenumber + ", address=" + address + ", isMale=" + isMale + '}';
    }
    
    

}

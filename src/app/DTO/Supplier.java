
package app.DTO;

import java.io.Serializable;

public class Supplier implements Serializable{
    private String ID;
    private String name;
    private String phonenumber;
    private String address;

    public Supplier() {
    }

    public Supplier(String ID, String name, String phonenumber, String address) {
        this.ID = ID;
        this.name = name;
        this.phonenumber = phonenumber;
        this.address = address;
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

    @Override
    public String toString() {
        return "Supplier{" + "ID=" + ID + ", name=" + name + ", phonenumber=" + phonenumber + ", address=" + address + '}';
    }
    
    
    
}

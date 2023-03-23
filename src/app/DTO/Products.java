
package app.DTO;

import java.io.Serializable;

public class Products implements Serializable{
    private String ID;
    private String name;
    private String type;
    private String describe;
    private boolean status; //True "Countinue" | False "Stop"

    public Products() {
    }

    public Products(String ID, String name, String type, String describe, boolean status) {
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.describe = describe;
        this.status = status;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Items{" + "ID=" + ID + ", name=" + name + ", type=" + type + ", describe=" + describe + ", status=" + status + '}';
    }
    
}


package app.BUS;

import app.DTO.Staff;
import app.DTO.Timekeeping;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

public class TimekeepingList {
    private ArrayList<Timekeeping> timekeepingLists;
    
    public TimekeepingList(){
        this.timekeepingLists = new ArrayList();
    }

    public TimekeepingList(ArrayList<Timekeeping> timekeepingLists) {
        this.timekeepingLists = timekeepingLists;
    }

    public ArrayList<Timekeeping> getTimekeepingLists() {
        return timekeepingLists;
    }

    public void setTimekeepingLists(ArrayList<Timekeeping> timekeepingLists) {
        this.timekeepingLists = timekeepingLists;
    }  
    
    //Func
    
    public void addNewTimekeeping(Timekeeping t){
        this.timekeepingLists.add(t);
    }
    
    public Timekeeping findTimekeepingByDay(LocalDate date){
        for(Timekeeping t : this.timekeepingLists){
            if(t.getDays().isEqual(date)){
                return t;
            }
        }
        return null;
    }
    
    public void showAllTinekeeping(){
        for(Timekeeping t: this.timekeepingLists){
            System.out.println(t);
        }
    }
    
    //Write and Read File
    public void writeTimekeepingIntoFile(File file) {
        try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            for (Timekeeping t : this.timekeepingLists) {
                oos.writeObject(t);
            }
            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Save Timekeeping Success");
        } catch (Exception e) {
            System.out.println("WriteFile" + e);
        }
    }
    
    public void readTimekeepingFromFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Timekeeping newTimekeeping = null;
            while (true) {
                Object obj = ois.readObject();
                if (obj == null) {
                    break;
                } else {
                    newTimekeeping = (Timekeeping) obj;
                    this.timekeepingLists.add(newTimekeeping);
                }
            }

            fis.close();
            ois.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}

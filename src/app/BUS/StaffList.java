package app.BUS;

import app.DTO.Account;
import app.DTO.Staff;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StaffList {

    private ArrayList<Staff> staffList;
    private File file;
    public StaffList() {
        this.staffList = new ArrayList();
    }

    public StaffList(ArrayList<Staff> staffList) {
        this.staffList = staffList;
    }

    public ArrayList<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(ArrayList<Staff> staffList) {
        this.staffList = staffList;
    }
    
    public void initFile(){
        this.file = new File("src/app/Data/staffList.txt");
    }

    public void writeStaffIntoFile() {
        try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            for (Staff staff : staffList) {
                oos.writeObject(staff);
            }
            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Save Staff Success");
        } catch (Exception e) {
            System.out.println("ERROR Staff WriteFile" + e);
        }
    }

    public void readStaffFromFile() {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Staff newStaff = null;
            while (true) {
                Object obj = ois.readObject();
                if (obj == null) {
                    break;
                } else {
                    newStaff = (Staff) obj;
                    this.staffList.add(newStaff);
                }
            }

            fis.close();
            ois.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void appendStaffIntoFile(Staff newStaff) {
        try {
            FileOutputStream fout = new FileOutputStream(this.file, true);
            ObjectOutputStream oos = new ObjectOutputStream(fout) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
            oos.writeObject(newStaff);
            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Append new Staff Success!");
        } catch (Exception e) {
            System.out.println("Append file" + e);
        }
    }

    public void showAllSatff() {
        for (Staff staff : this.staffList) {
            System.out.println(staff.toString());
        }
    }

    public Staff findStaff(String name) {
        for (Staff staff : staffList) {
            if (staff.getName().equals(name)) {
                return staff;
            }
        }
        return null;
    }

    public void addNewStaff(Staff newStaff) {
        this.staffList.add(newStaff);
    }

    public String createID() {
        int number = this.staffList.size() + 100;
        return "ST " + String.valueOf(number);
    }

    //Find
    public Staff findStaffByAccount(Account acc) {
        for (Staff staff : staffList) {
            String username = staff.getAccount().getUsername();
            String pwd = staff.getAccount().getPassword();
            if (acc.getUsername().equals(username) && acc.getPassword().equals(pwd)) {
                return staff;
            }
        }
        return null;
    }
    
    public Staff findStaffByID(String ID){
        for (Staff staff : staffList) {
            if (staff.getID().equals(ID)) {
                return staff;
            }
        }
        return null;
    }
    
    public StaffList fillterStaffListByName(String name){
        StaffList TMPlist = new StaffList();
        for (Staff staff : this.staffList) {
            if (staff.getName().toLowerCase().contains(name.toLowerCase())) {
                TMPlist.addNewStaff(staff);
            }
        }
        return TMPlist;
    }

}

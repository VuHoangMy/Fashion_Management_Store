package app.DTO;

import app.BUS.StaffList;
import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

public class Staff implements Serializable {

    private String ID;
    private String role;
    private String name;
    private String gender;
    private LocalDate birthday;
    private String address;
    private String phonenumber;
    private String email;
    private double salary;
    private boolean status;
    private Account account;
    private File imageProfile;

    private LocalTime workFrom;
    private LocalTime workTo;

    //ArrayList
    private ArrayList<Timekeeping> timekeepingLists;
    private ArrayList<Payroll> payrollLists;
    private ArrayList<BillImportGoods> billImportGoodses;

    public Staff() {
        this.billImportGoodses = new ArrayList<>();
        this.timekeepingLists = new ArrayList<>();
        this.payrollLists = new ArrayList<>();

        initInstances();

    }

    public Staff(String ID, String role, String name, String gender, LocalDate birthday, String address, String phonenumber, String email, double salary, boolean status, Account account) {
        this.ID = ID;
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.phonenumber = phonenumber;
        this.email = email;
        this.salary = salary;
        this.status = status;
        this.account = account;

        initInstances();
    }

    private void initInstances() {
        this.imageProfile = new File("src/app/Images/Account IMG.png");
        this.timekeepingLists = new ArrayList<>();
        this.billImportGoodses = new ArrayList<>();
        this.payrollLists = new ArrayList<>();

        // set default time work
        this.workFrom = LocalTime.parse("09:00:00");
        this.workTo = LocalTime.parse("21:00:00");
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public File getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(File imageProfile) {
        this.imageProfile = imageProfile;
    }

    public LocalTime getInWorkAt() {
        return workFrom;
    }

    public void setInWorkAt(LocalTime inWorkAt) {
        this.workFrom = inWorkAt;
    }

    public LocalTime getGetsOffAt() {
        return workTo;
    }

    public void setGetsOffAt(LocalTime getsOffAt) {
        this.workTo = getsOffAt;
    }

    @Override
    public String toString() {
        return "Staff{" + "role=" + role + ", name=" + name + ", gender=" + gender + ", birthday=" + birthday + ", address=" + address + ", phonenumber=" + phonenumber + ", email=" + email + ", salary=" + salary + ", account=" + account + '}';
    }

    //Func
    //Bil Import
    public ArrayList<BillImportGoods> getBillImportGoodses() {
        return billImportGoodses;
    }

    public void setBillImportGoodses(ArrayList<BillImportGoods> billImportGoodses) {
        this.billImportGoodses = billImportGoodses;
    }

    public void addNewBillImportItems(BillImportGoods bill) {
        this.billImportGoodses.add(bill);
    }

    public String createIDBillImportGoods() {
        StaffList staffList = new StaffList();
        staffList.initFile();
        staffList.readStaffFromFile();

        int sizeBillList = 0;
        for (Staff staff : staffList.getStaffList()) {
            if (staff.getBillImportGoodses() == null) {
                continue;
            }
            sizeBillList += staff.getBillImportGoodses().size();
        }
        int num = sizeBillList + 100;
        return String.valueOf(num);
    }

    //Time keeping
    public ArrayList<Timekeeping> getTimekeepingLists() {
        return timekeepingLists;
    }

    public void setTimekeepingLists(ArrayList<Timekeeping> timekeepingLists) {
        this.timekeepingLists = timekeepingLists;
    }

    public void addNewTimekeeping(Timekeeping timekeeping) {
        this.timekeepingLists.add(timekeeping);
    }

    //payroll
    public ArrayList<Payroll> getPayrollLists() {
        return payrollLists;
    }

    public void setPayrollLists(ArrayList<Payroll> payrollLists) {
        this.payrollLists = payrollLists;
    }

    public void addNewPayroll(Payroll payroll) {
        this.payrollLists.add(payroll);
    }

    // sort
    public static Comparator<Staff> staffIDCompareASC = new Comparator<Staff>() {
        @Override
        public int compare(Staff s1, Staff s2) {
            String name1 = s1.getID();
            String name2 = s2.getID();

//            int id1 = Integer.parseInt(s1.getName().replace("ST ", ""));
//            int id2 = Integer.parseInt(s2.getName().replace("ST ", ""));

            /*For ascending  order*/
            return name1.compareTo(name2);
//            return id1 - id2;
        }
    };

    public static Comparator<Staff> staffIDCompareDES = new Comparator<Staff>() {
        @Override
        public int compare(Staff s1, Staff s2) {
            String name1 = s1.getName();
            String name2 = s2.getName();
            /*For descending  order*/
            return name2.compareTo(name1);
        }
    };

}

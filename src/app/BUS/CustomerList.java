package app.BUS;

import app.CommonFuncion.CommonFunction;
import app.DTO.Customer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CustomerList {

    private File file;
    private ArrayList<Customer> customerList;

    public CustomerList() {
        this.customerList = new ArrayList<>();
    }

    public CustomerList(ArrayList<Customer> customerList) {
        this.customerList = customerList;
    }

    public void initFile() {
        this.file = new File("src/app/Data/customerList.txt");
    }

    //Func
    public void addNewCustomer(Customer customer) {
        this.customerList.add(customer);
    }

    public String createCustomerID() {
        int number = this.customerList.size() + 1;
        return String.format("CU " + number);
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public void showAllCustomer() {
        for (Customer customer : this.customerList) {
            System.out.println(customer);
        }
    }

    // Work with file
    public void appendACustomerIntoFile(Customer customer) {
        try {
            FileOutputStream fout = new FileOutputStream(this.file, true);
            ObjectOutputStream oos = new ObjectOutputStream(fout) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };

            oos.writeObject(customer);

            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Append Customer Success");
        } catch (Exception e) {
            System.out.println("Append file" + e);
        }
    }

    public void writeToFile() {
        try {
            FileOutputStream fout = new FileOutputStream(this.file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            for (Customer customer : this.customerList) {
                oos.writeObject(customer);
            }
            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Save Customer Success");
        } catch (Exception e) {
            System.out.println("WriteFile" + e);
        }
    }

    public void readFromFile() {
        try {
            FileInputStream fis = new FileInputStream(this.file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Customer newCustomer = null;
            while (true) {
                Object obj = ois.readObject();
                if (obj == null) {
                    break;
                } else {
                    newCustomer = (Customer) obj;
                    this.customerList.add(newCustomer);
                }
            }
            fis.close();
            ois.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    //Find
    public Customer findCustomerByID(String ID) {
        for (Customer c : this.customerList) {
            if (c.getID().equals(ID)) {
                return c;
            }
        }
        return null;
    }

    public Customer findCustomerByPhonenumber(String phonenumber) {
        for (Customer c : this.customerList) {
            if (c.getPhonenumber().equals(phonenumber)) {
                return c;
            }
        }
        return null;
    }

    public CustomerList findListCustomerByNameOrID(String txtSearch) {
        CustomerList c = new CustomerList();
        for(Customer customer : this.customerList){
            if(customer.getID().toLowerCase().contains(txtSearch.toLowerCase()) || customer.getName().toLowerCase().contains(txtSearch.toLowerCase())){
                c.addNewCustomer(customer);
            }
        }
        return c;
    }

}

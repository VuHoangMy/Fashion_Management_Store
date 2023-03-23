package app.BUS;

import app.DTO.Account;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AccountList {

    private ArrayList<Account> accountList;
    private File file;

    public AccountList() {
        this.accountList = new ArrayList<Account>();
    }

    public AccountList(ArrayList<Account> accountList) {
        this.accountList = accountList;
    }

    public void initFile() {
        this.file = new File("src/app/Data/accountList.txt");
    }

    public void addNewAccount(Account newAcc) {
        this.accountList.add(newAcc);
    }

    public ArrayList<Account> getAccountList() {
        return accountList;
    }

//    Work with FIle
    public void writeAcountIntoFile() {
        try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            for (Account acc : accountList) {
                oos.writeObject(acc);
            }
            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Save Account Success");
        } catch (Exception e) {
            System.out.println("WriteFile" + e);
        }
    }

    public void appendAccountIntoFile(Account acc) {
        try {
            FileOutputStream fout = new FileOutputStream(file, true);
            ObjectOutputStream oos = new ObjectOutputStream(fout) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
            oos.writeObject(acc);
            oos.flush();
            fout.close();
            oos.close();
        } catch (Exception e) {
            System.out.println("Append file" + e);
        }
    }

    public void readAccountFromFile() {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Account newAccount = null;
            while (true) {
                Object obj = ois.readObject();
                if (obj == null) {
                    break;
                }
                newAccount = (Account) obj;
                this.accountList.add(newAccount);
            }
            fis.close();
            ois.close();
        } catch (Exception e) {
            
        }
    }

//    Func
    public Account findAccount(String username, String pwd) {
        for (Account account : accountList) {
            if (account.getUsername().equals(username) && account.getPassword().equals(pwd)) {

                return account;
            }
        }
        return null;
    }

    public void showAllAccount() {
        for (Account account : accountList) {
            System.out.println(account.toString());
        }
    }

}

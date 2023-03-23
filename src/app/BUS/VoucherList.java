package app.BUS;

import app.CommonFuncion.CommonFunction;
import app.DTO.Voucher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class VoucherList {

    private File file;
    private ArrayList<Voucher> voucherList;

    public VoucherList() {
        this.voucherList = new ArrayList<>();
    }

    public VoucherList(ArrayList<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    public void initFile() {
        this.file = new File("src/app/Data/voucherList.txt");
    }

    //Func
    public void addNewVoucher(Voucher newVoucher) {
        this.voucherList.add(newVoucher);
    }

    public String createVoucherID() {
        int number = this.voucherList.size() + 100;
        return String.valueOf(number);
    }

    public ArrayList<Voucher> getVoucherList() {
        return this.voucherList;
    }

    public void showAllVoucher() {
        for (Voucher v : this.voucherList) {
            System.out.println(v);
        }
    }

    // Work with file
    public void appendAVoucherIntoFile(Voucher voucher) {
        try {
            FileOutputStream fout = new FileOutputStream(file, true);
            ObjectOutputStream oos = new ObjectOutputStream(fout) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };

            oos.writeObject(voucher);

            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Append Voucher Success");
        } catch (Exception e) {
            System.out.println("Error Append list voucher file" + e);
        }
    }

    public void writeToFile() {
        try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            for (Voucher voucher : this.voucherList) {
                oos.writeObject(voucher);
            }
            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Save Voucher Success");
        } catch (Exception e) {
            System.out.println("Error Save Voucher " + e);
//            e.printStackTrace();
        }
    }

    public void readFromFile() {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Voucher newVoucher = null;
            while (true) {
                Object obj = ois.readObject();
                if (obj == null) {
                    break;
                } else {
                    newVoucher = (Voucher) obj;
                    this.voucherList.add(newVoucher);
                }
            }
            fis.close();
            ois.close();
        } catch (Exception e) {
//            e.printStackTrace();
//System.out.println("Error Read File Voucher: " + e);
        }
    }

    //Find
    public Voucher findVoucherByID(String ID) {
        for (Voucher v : this.voucherList) {
            if (v.getID().equals(ID)) {
                return v;
            }
        }
        return null;
    }

    public VoucherList findListVoucherByIDOrName(String txtSearch) {
        VoucherList v = new VoucherList();

        for (Voucher voucher : this.voucherList) {
                if (voucher.getID().toLowerCase().contains(txtSearch.toLowerCase()) || voucher.getName().toLowerCase().contains(txtSearch.toLowerCase())) {
                    v.addNewVoucher(voucher);
                }
            }

        return v;
    }

}

package app.test;

import app.BUS.AccountList;
import app.BUS.BillSellList;
import app.BUS.CustomerList;
import app.BUS.ItemsList;
import app.BUS.ProductsList;
import app.BUS.StaffList;
import app.BUS.SupplierList;
import app.BUS.VoucherList;
import app.CommonFuncion.QRCodeGenerator;
import app.DTO.Account;
import app.DTO.BillImportGoods;
import app.DTO.BillSell;
import app.DTO.Customer;
import app.DTO.Items;
import app.DTO.Products;
import app.DTO.Staff;
import app.DTO.Supplier;
import app.DTO.Timekeeping;
import app.DTO.Voucher;
import java.awt.Color;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Random;

public class Test {

    public static void addToAccList(AccountList lists) {

        Account acc1 = new Account("Admin", "123");
        Account acc2 = new Account("Han", "123");
        Account acc3 = new Account("account", "123");

        lists.addNewAccount(acc1);
        lists.addNewAccount(acc2);
        lists.addNewAccount(acc3);
    }

    public static Staff createNewStaff() {
        LocalDate date1 = LocalDate.of(1999, Month.JANUARY, 15);
        Account acc1 = new Account("Admin", "123");
        return new Staff("", "Owner", "New Staff", "Male", date1, "Dalat", "0954667321", "Abc@gmail.com", 5900000, true, acc1);

    }

    public static void addToStaffList(AccountList accLists, StaffList stafflists) throws Exception {
        LocalDate date1 = LocalDate.of(1999, Month.JANUARY, 15);
        LocalDate date2 = LocalDate.of(1997, Month.JULY, 2);
        LocalDate date3 = LocalDate.of(2001, Month.OCTOBER, 9);

        Staff st1 = new Staff(stafflists.createID(), "Owner", "Phong", "Male", date1, "Dalat", "0954667321", "Abc@gmail.com", 5900000, true, accLists.getAccountList().get(0));
        Items items = new Items("100", "Ao j3", 2000000, "M", Color.GREEN, "Cotton", null);

        SupplierList supplierList = new SupplierList();
        supplierList.initFile();
        supplierList.readFromFile();
        Supplier s = new Supplier(supplierList.createItemsID(), "LV", "096734622", "Dong da");
        BillImportGoods bill = new BillImportGoods(st1.createIDBillImportGoods(), items, s, "Bill dau tien", 10, 1200000);
        st1.addNewBillImportItems(bill);
        stafflists.addNewStaff(st1);

        Staff st2 = new Staff(stafflists.createID(), "Manager", "Han", "Male", date2, "Dalat", "0952234564", "Abc@gmail.com", 5900000, false, accLists.getAccountList().get(1));
        st2.setBillImportGoodses(null);

        Timekeeping time1 = new Timekeeping(LocalDate.now(), LocalTime.now(), null);

        st2.addNewTimekeeping(time1);
        stafflists.addNewStaff(st2);

        Staff st3 = new Staff(stafflists.createID(), "Cashier", "Jin", "Male", date3, "Dalat", "0978327321", "Abc@gmail.com", 5900000, false, accLists.getAccountList().get(2));
        st3.setBillImportGoodses(null);
        stafflists.addNewStaff(st3);

        addNewQRCodeForStaff(stafflists);

    }

    public static void addNewQRCodeForStaff(StaffList staffList) throws Exception {
        for (Staff staff : staffList.getStaffList()) {
            String ID = staff.getID();
            String path = "D:\\Code\\Java\\" + ID + ".jpg";
            QRCodeGenerator.generatorQRCode(ID, path);
        }
    }

    public static void addNewCustomerToList(CustomerList cl) {

        Customer c1 = new Customer(cl.createCustomerID(), "Nguyen van A", "0912376322", "Dalat", true);
        cl.addNewCustomer(c1);
        Customer c2 = new Customer(cl.createCustomerID(), "Nguyen van b", "0934082213", "DaNang", false);
        cl.addNewCustomer(c2);
        Customer c3 = new Customer(cl.createCustomerID(), "Nguyen van c", "0944522245", "NhaTrang", true);
        cl.addNewCustomer(c3);

    }

    public static void addNewVoucherToList(VoucherList voucherList) {
        Voucher v1 = new Voucher(voucherList.createVoucherID(), "Summer happy", LocalDate.of(2022, Month.JUNE, 15), LocalDate.of(2022, Month.JULY, 5), "", 1200000, 12);
        voucherList.addNewVoucher(v1);
        Voucher v2 = new Voucher(voucherList.createVoucherID(), "Winter not cold", LocalDate.of(2022, Month.OCTOBER, 20), LocalDate.of(2022, Month.DECEMBER, 5), "", 2200000,30);
        voucherList.addNewVoucher(v2);
        Voucher v3 = new Voucher(voucherList.createVoucherID(), "New term", LocalDate.of(2022, Month.NOVEMBER, 20), LocalDate.of(2022, Month.DECEMBER, 10), "Voucher i voucher", 3200000,40);
        voucherList.addNewVoucher(v3);
    }

    public static void addToProductsList(ProductsList itemsList) {
        Products i1 = new Products(itemsList.createItemsID(), "Giay Convert", "Giay", "Giay la giay aasd", true);
        itemsList.addNewItem(i1);
        Products i2 = new Products(itemsList.createItemsID(), "Ao Hoddie", "Giay", "Some thing here", true);
        itemsList.addNewItem(i2);
        Products i3 = new Products(itemsList.createItemsID(), "Ao thun", "Ao", "Ao asdasdas", true);
        itemsList.addNewItem(i3);
    }

    public static ArrayList<File> getArrImageShose(int index) {
        ArrayList<File> arrImageShose = new ArrayList<>();
        switch (index) {
            case 1:
                arrImageShose.add(new File("src/app/ImageItems/Shose1.jpg"));
                arrImageShose.add(new File("src/app/ImageItems/Shose2.jpg"));
                break;
            case 2:
                arrImageShose.add(new File("src/app/ImageItems/Shose4.jpg"));
                arrImageShose.add(new File("src/app/ImageItems/Shose8.jpg"));
                break;
            case 3:
                arrImageShose.add(new File("src/app/ImageItems/Shose2.jpg"));
                arrImageShose.add(new File("src/app/ImageItems/Shose7.jpg"));
                break;
            case 4:
                arrImageShose.add(new File("src/app/ImageItems/Shose1.jpg"));
                arrImageShose.add(new File("src/app/ImageItems/Shose6.jpg"));
                break;
            case 5:
                arrImageShose.add(new File("src/app/ImageItems/Shose2.jpg"));
                arrImageShose.add(new File("src/app/ImageItems/Shose9.jpg"));
                break;
            case 6:
                arrImageShose.add(new File("src/app/ImageItems/Shose10.jpg"));
                arrImageShose.add(new File("src/app/ImageItems/Shose3.jpg"));
                break;
        }
        arrImageShose.add(new File("src/app/ImageItems/Shose5.jpg"));
        return arrImageShose;
    }

    public static ArrayList<File> getArrImageTShirt(int index) {
        ArrayList<File> arrImageTShirt = new ArrayList<>();
        switch (index) {
            case 1:
                arrImageTShirt.add(new File("src/app/ImageItems/TShirt1.jpg"));
                arrImageTShirt.add(new File("src/app/ImageItems/TShirt2.jpg"));
                break;
            case 2:
                arrImageTShirt.add(new File("src/app/ImageItems/TShirt10.jpg"));
                arrImageTShirt.add(new File("src/app/ImageItems/TShirt7.jpg"));
                break;
            case 3:
                arrImageTShirt.add(new File("src/app/ImageItems/TShirt5.jpg"));
                arrImageTShirt.add(new File("src/app/ImageItems/TShirt9.jpg"));
                break;
            case 4:
                arrImageTShirt.add(new File("src/app/ImageItems/TShirt7.jpg"));
                arrImageTShirt.add(new File("src/app/ImageItems/TShirt8.jpg"));
                break;
            case 5:
                arrImageTShirt.add(new File("src/app/ImageItems/TShirt3.jpg"));
                arrImageTShirt.add(new File("src/app/ImageItems/TShirt2.jpg"));
                break;
            case 6:
                arrImageTShirt.add(new File("src/app/ImageItems/TShirt4.jpg"));
                arrImageTShirt.add(new File("src/app/ImageItems/TShirt5.jpg"));
                break;
        }
        arrImageTShirt.add(new File("src/app/ImageItems/TShirt6.jpg"));
        return arrImageTShirt;
    }

    public static void addToItemsLists() {
        ItemsList itemsList = new ItemsList();

        Items i1 = new Items(itemsList.createItemID(), "Giay Convert", 200000, "M", Color.RED, "Cotton", getArrImageShose(6));
        i1.setInventoryNumber(120);
        itemsList.addNewItem(i1);

        Items i2 = new Items(itemsList.createItemID(), "Giay Van", 600000, "L", Color.YELLOW, "Cotton", getArrImageShose(1));
        itemsList.addNewItem(i2);

        Items i3 = new Items(itemsList.createItemID(), "Giay C", 500000, "S", Color.GREEN, "Cotton", getArrImageShose(2));
        i1.setInventoryNumber(4);
        itemsList.addNewItem(i3);

        Items i4 = new Items(itemsList.createItemID(), "Giay D", 700000, "XL", Color.BLUE, "Cotton", getArrImageShose(3));
        itemsList.addNewItem(i4);

        Items i5 = new Items(itemsList.createItemID(), "Giay E", 700000, "XL", new Color(153 - 0 - 0), "Cotton", getArrImageShose(4));
        itemsList.addNewItem(i5);

        Items i6 = new Items(itemsList.createItemID(), "Giay F", 700000, "XL", new Color(51 - 204 - 255), "Cotton", getArrImageShose(5));
        itemsList.addNewItem(i6);

        // T Shirt
        Items ts1 = new Items(itemsList.createItemID(), "Ao Thun A", 200000, "M", new Color(102 - 51 - 0), "Cotton", getArrImageTShirt(6));
        itemsList.addNewItem(ts1);

        Items ts2 = new Items(itemsList.createItemID(), "Ao Thun A", 600000, "L", Color.YELLOW, "Cotton", getArrImageTShirt(1));
        ts2.setInventoryNumber(32);
        itemsList.addNewItem(ts2);

        Items ts3 = new Items(itemsList.createItemID(), "Ao Thun A", 500000, "S", new Color(102 - 0 - 153), "Cotton", getArrImageTShirt(2));
        itemsList.addNewItem(ts3);

        Items ts4 = new Items(itemsList.createItemID(), "Ao Thun  D", 600000, "XL", new Color(255, 102, 102), "Cotton", getArrImageTShirt(3));
        ts4.setInventoryNumber(25);
        itemsList.addNewItem(ts4);

        Items ts5 = new Items(itemsList.createItemID(), "Ao Thun E", 900000, "XXL", new Color(255 - 102 - 0), "Cotton", getArrImageTShirt(4));
        itemsList.addNewItem(ts5);

        Items ts6 = new Items(itemsList.createItemID(), "Ao Thun F", 750000, "XL", new Color(153 - 153 - 153), "Cotton", getArrImageTShirt(5));
        itemsList.addNewItem(ts6);

        // Create QRCode For Items
        try {
            addNewQRCodeForItems(itemsList);
        } catch (Exception e) {
        }

        itemsList.initFile();
        itemsList.writeToFile();
    }

    public static void addNewQRCodeForItems(ItemsList itemsList) throws Exception {
        for (Items items : itemsList.getItemslist()) {
            String ID = items.getID();
            String path = "D:\\Code\\Java\\" + ID + ".jpg";
            QRCodeGenerator.generatorQRCode(ID, path);
        }
    }

    public static void addTSupplierList() {
        SupplierList supplierList = new SupplierList();
        supplierList.initFile();

        Supplier s = new Supplier(supplierList.createItemsID(), "LV", "096734622", "Dong da");
        supplierList.addNewSupplier(s);
        Supplier s1 = new Supplier(supplierList.createItemsID(), "Gucci", "096734622", "Dong da3");
        supplierList.addNewSupplier(s1);
        Supplier s2 = new Supplier(supplierList.createItemsID(), "Tinh than", "09234324", "Jin hho");
        supplierList.addNewSupplier(s2);
        Supplier s3 = new Supplier(supplierList.createItemsID(), "Ricu", "076327373", "Dong da2");
        supplierList.addNewSupplier(s3);
        Supplier s4 = new Supplier(supplierList.createItemsID(), "Ply cu", "037423432", "Dong da4");
        supplierList.addNewSupplier(s4);

        supplierList.writeToFile();
    }

    public static void clearFile() {
        ProductsList producList = new ProductsList();
        AccountList accountList = new AccountList();
        StaffList staffList = new StaffList();
        VoucherList voucherList = new VoucherList();
        CustomerList cl = new CustomerList();
        ItemsList itemsList = new ItemsList();

        accountList.initFile();
        staffList.initFile();
        producList.initFile();
        voucherList.initFile();
        cl.initFile();
        itemsList.initFile();

        producList.writeToFile();
        accountList.writeAcountIntoFile();
        staffList.writeStaffIntoFile();
        voucherList.writeToFile();
        cl.writeToFile();
        itemsList.writeToFile();
    }

    public static void addToBillSellList() {
        ItemsList itemsList = new ItemsList();
        BillSellList billSellList = new BillSellList();
        billSellList.initFile();

        Items i1 = new Items(itemsList.createItemID(), "Giay Convert", 200000, "M", Color.RED, "Cotton", getArrImageShose(6));
        itemsList.addNewItem(i1);
        Customer c1 = new Customer("101", "Nguyen van A", "0912376322", "Dalat", true);
        Staff st1 = new Staff("123", "Manager", "Han", "Male", LocalDate.now(), "Dalat", "0952234564", "Abc@gmail.com", 5900000, false, null);

        BillSell billSell1 = new BillSell("101", "Bill ban dau tien", "Chuyen khoan", 12000, 12000, itemsList.getItemslist(), st1, c1,null);
        billSellList.addNewBillSell(billSell1);

        Items i2 = new Items(itemsList.createItemID(), "Giay X", 200000, "S", Color.gray, "Cotton 778", getArrImageShose(2));
        itemsList.addNewItem(i2);
        Customer c2 = new Customer("102", "Nguyen van B", "0914546322", "Fu quoc", true);
        Staff st2 = new Staff("123", "Cashier", "Jin", "Male", LocalDate.now(), "Dalat", "0952234564", "Abc@gmail.com", 5900000, false, null);

        BillSell billSell2 = new BillSell("102", "Bill thu 2", "Tien Mat", 12000, 12000, itemsList.getItemslist(), st2, c2, null);
        billSellList.addNewBillSell(billSell2);

        itemsList.getItemslist().removeAll(itemsList.getItemslist());

        Items i3 = new Items(itemsList.createItemID(), "Giay Y", 200000, "XX", Color.BLUE, "Cotton 223", getArrImageShose(0));
        itemsList.addNewItem(i3);
        Items i4 = new Items(itemsList.createItemID(), "Giay Z", 200000, "XXL", Color.yellow, "Cotton", getArrImageShose(1));
        itemsList.addNewItem(i4);
        Customer c3 = new Customer("103", "Nguyen van c", "0914546322", "kang tho", true);
        Staff st3 = new Staff("155", "Owner", "Phong", "Male", LocalDate.now(), "Dalat", "0958897564", "Abc@gmail.com", 5900000, false, null);

        BillSell billSell3 = new BillSell("103", "Bill thu 3", "Tien Mat", 12000, 12000, itemsList.getItemslist(), st3, c3, null);
        billSellList.addNewBillSell(billSell3);

        billSellList.writeToFile();
    }

    public static void writeToFile() {
        ProductsList producList = new ProductsList();
        AccountList accountList = new AccountList();
        StaffList staffList = new StaffList();
        VoucherList voucherList = new VoucherList();
        CustomerList cl = new CustomerList();

        accountList.initFile();
        staffList.initFile();
        producList.initFile();
        voucherList.initFile();
        cl.initFile();

        try {
            addToAccList(accountList);
            addToStaffList(accountList, staffList);
            addToProductsList(producList);
            addNewVoucherToList(voucherList);
            addNewCustomerToList(cl);
        } catch (Exception e) {
        }

        //Write tofile
        addToBillSellList();
        addTSupplierList();
        addToItemsLists();
        accountList.writeAcountIntoFile();
        staffList.writeStaffIntoFile();
        producList.writeToFile();
        voucherList.writeToFile();
        cl.writeToFile();
    }

    public static void run() throws Exception {
        writeToFile();
//clearFile();
    }

    public static void testTimekeeping() {
        StaffList staffLists = new StaffList();
        staffLists.initFile();
        staffLists.readStaffFromFile();
        Staff staff = staffLists.findStaffByID("ST 101");

        LocalDate today = LocalDate.now();
        ArrayList<Timekeeping> timekeepingLists = staff.getTimekeepingLists();
        int indexLastTimekeeping = timekeepingLists.size() - 1;

        boolean check = timekeepingLists.get(indexLastTimekeeping).getDays().isEqual(today)
                && timekeepingLists.get(indexLastTimekeeping).getTimeEnd() == null;

        if (check) {
            // if had get in
            System.out.println("Had get in");
            timekeepingLists.get(indexLastTimekeeping).setTimeEnd(LocalTime.now());
        } else {
            System.out.println("Still not get in");

            Timekeeping newTimekeeping = new Timekeeping(today, LocalTime.now(), null);
            staff.addNewTimekeeping(newTimekeeping);
        }

        for (Staff s : staffLists.getStaffList()) {
            for (Timekeeping t : s.getTimekeepingLists()) {
                System.out.println(t);
            }
        }
    }

    public static int randomInteger(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }

    public static void main(String[] args) throws Exception {
//        run();
String txt = "CU 1";
txt = txt.replaceAll("CU ", "");
        System.out.println(txt);

    }
}

//File directory = new File("./");
//   System.out.println(directory.getAbsolutePath());

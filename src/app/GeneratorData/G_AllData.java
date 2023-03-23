package app.GeneratorData;

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
import app.DTO.Fines;
import app.DTO.Items;
import app.DTO.Payroll;
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
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Random;

public class G_AllData {

    public static int randomInteger(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }

    public static String randomPhonenumber() {
        String phoneNums[] = new String[]{"0954667321", "0954667321", "0954667321",
            "0954667321", "0954667321", "0954667321",
            "09 37 292 663 ", "(08)38222228",
            "(84-33) 386 2259 ", "(84-230) 384 5251 ", "(84-4) 2662168/2662589 ", "0903872637 ",
            "(08) 35111241 ", "(84-8) 8963139"};

        return phoneNums[randomInteger(0, phoneNums.length)];
    }

    public static String randomGmail() {
        String gmails[] = new String[]{"eurohack@att.net", "dunstan@comcast.net", "thurston@gmail.com", "arnold@optonline.net",
            "rtanter@outlook.com", "guialbu@icloud.com"};
        return gmails[randomInteger(0, gmails.length)];
    }

    public static ArrayList<File> getListIMGNotDuplicate(ArrayList<File> ImgTshirt, int n) {
        ArrayList<File> files = new ArrayList<>();
        ArrayList<File> Imgs = ImgTshirt;
        ArrayList<Integer> indexs = new ArrayList<>();
        int count = 0;
        while (true) {
            if (count == 5) {
                break;
            }
            int random = randomInteger(0, n);

            if (indexs.indexOf(random) != -1) {
                continue;
            }
            indexs.add(random);
            files.add(Imgs.get(random));
            count++;
        }
        return files;
    }

    public static ArrayList<File> getArrImageShose() {
        ArrayList<File> arrImageShose = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            arrImageShose.add(new File("src/app/ImageItems/Shose" + i + ".jpg"));
        }
        return arrImageShose;
    }

    public static ArrayList<File> getArrImageTShirt() {
        ArrayList<File> arrImageTShirt = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            arrImageTShirt.add(new File("src/app/ImageItems/TShirt" + i + ".jpg"));
        }
        arrImageTShirt.add(new File("src/app/ImageItems/TShirt10.webp"));
        return arrImageTShirt;
    }

    public static void G_Account() {
        Account acc1 = new Account("admin", "123");
        Account acc2 = new Account("Han", "123");
        Account acc3 = new Account("account 1", "123");
        Account acc4 = new Account("account 2", "123");
        Account acc5 = new Account("account 3", "123");
        Account acc6 = new Account("account 4", "123");

        Account acc7 = new Account("account 5", "123");

        AccountList accountList = new AccountList();
        accountList.initFile();

        accountList.addNewAccount(acc1);
        accountList.addNewAccount(acc2);
        accountList.addNewAccount(acc3);
        accountList.addNewAccount(acc4);

        accountList.addNewAccount(acc5);
        accountList.addNewAccount(acc6);
        accountList.addNewAccount(acc7);

        accountList.writeAcountIntoFile();
    }

    public static void G_Product() {
        ProductsList productsList = new ProductsList();
        productsList.initFile();
//        Jacket
//Jeans
//T-Shirt
//Trousers
//Dress
//Shorts
//Jumper
//Shoes
//        ArrayList<String> nameProduct = new ArrayList<>();

        Random generator = new Random();
        String nameNikeProduct[] = new String[]{"Nike \"Killshot 2\" sneakers",
            "Nike \"Blazer\" mid '77 vintage sneakers", "Nike Air Force 1 '07 sneakers",
            "Nike \"Challenger OG\" sneakers", "Nike \"Air Tuned\" max sneakers",
            "Nike \"Cortez\" sneakers"
        };

        String nameAddidasProduct[] = new String[]{"Adidas 'Rod Laver' sneakers", "Adidas Forum 84 low sneakers",
            "Adidas 'Samba' classic sneakers", "Adidas Originals 'Stan Smith' sneakers",
            "Adidas Originals 'Superstar' sneakers", "Adidas Originals 'Gazelle' sneakers"};

        String nameConvertProduct[] = new String[]{"Converse 'Chuck 70' hi sneakers", "Converse one star sneakers",
            "Converse Pro leather parquet court sneakers", "Converse 'Jack Purcell' sneakers",
            "Converse sherpa 'Chuck 70 hi' sneakers", "Converse CONS 'Louie Lopez' pro sneakers"};

        String nameReebokProduct[] = new String[]{"Reebok Club C sneakers", "Reebok 'Beatnik' shoes",
            "Reebok classic leather sneaker (was $75, now 26% off)", "Reebok 'Zig Kinetica II' shoes",
            "Reebok Classic leather legacy shoes", "Reebok x Beams Club C laceless mules"};

        String nameNewBalanceProduct[] = new String[]{"New Balance 990v5 sneakers", "New Balance 993 sneakers",
            "New Balance 574 V2 sneakers", "New Balance 327 sneakers", "New Balance 2002R sneakers", "New Balance 992 sneakers"};

        String nameNewTshirtProduct[] = new String[]{"Gildan heavy cotton T-shirt",
            "Hanes short sleeve Beefy T ", "Velva Sheen pocket tee",
            "Uniqlo U T-shirt", "Carhartt K87 workwear pocket T-shirt",
            "Everlane organic cotton crew", "Russell Athletic cotton", "Russell short sleeve T-shirt",
            "Kirkland Signature", "Gap classic T-shirt",
            "Mott & Bow 'Carlton' ", "John Elliott Anti-Expo tee",
            "Handvaerk classic T-shirt", "Comfort Colors short",
            "J.Crew garment-dyed", "Todd Snyder x Champion basic jersey tee",
            "Jungmaven Jung pocket tee", "Uniqlo Dry crew neck",
            "Smartwool merino 250 baselayer", "Proof 72-hour merino tee",
            "Calvin Klein cotton crew neck classic",
            "Lululemon Vent Tech 2.0", "Nike Dri-FIT Legend",
            "Rhone Reign short", "Ten Thousand Versatile shirt",
            "Lady White Co. lite jersey T-shirt", "Everybody. World natural dye recycled",
            "Warehouse slub cotton T-shirt", "Buck Mason Field-Spec"};

        for (int i = 0; i < nameConvertProduct.length; i++) {
            Products i1 = new Products(productsList.createItemsID(), nameConvertProduct[i], "Shoes", "Giay la giay", true);
            productsList.addNewItem(i1);
        }
        for (int i = 0; i < nameNikeProduct.length; i++) {
            Products i1 = new Products(productsList.createItemsID(), nameNikeProduct[i], "Shoes", "Giay la giay", true);
            productsList.addNewItem(i1);
        }

        for (int i = 0; i < 10; i++) {
            Products i1 = new Products(productsList.createItemsID(), nameNewTshirtProduct[i], "T-Shirt", "Giay la T-Shirt", true);
            productsList.addNewItem(i1);
        }

        productsList.writeToFile();
    }

    public static void G_Voucher() {
        VoucherList voucherList = new VoucherList();
        voucherList.initFile();

        Voucher v1 = new Voucher(voucherList.createVoucherID(), "Summer happy", LocalDate.of(2022, Month.JUNE, 15),
                LocalDate.of(2022, Month.JULY, 5), "", 1200000, randomInteger(1, 6) * 10);
        voucherList.addNewVoucher(v1);
        Voucher v2 = new Voucher(voucherList.createVoucherID(), "Winter not cold", LocalDate.of(2022, Month.OCTOBER, 20),
                LocalDate.of(2022, Month.DECEMBER, 5), "", 2200000, randomInteger(1, 6) * 10);
        voucherList.addNewVoucher(v2);
        Voucher v3 = new Voucher(voucherList.createVoucherID(), "New term", LocalDate.of(2022, Month.NOVEMBER, 20),
                LocalDate.of(2022, Month.DECEMBER, 10), "Voucher i voucher", 3200000, randomInteger(1, 6) * 10);
        voucherList.addNewVoucher(v3);

        voucherList.writeToFile();
    }

    public static void G_Supplier() {
        SupplierList supplierList = new SupplierList();
        supplierList.initFile();

        Supplier s = new Supplier(supplierList.createItemsID(), "LV", "096734622", "P. Ben Nghé, Q.1, TP. Ho Chí Minh");
        supplierList.addNewSupplier(s);
        Supplier s1 = new Supplier(supplierList.createItemsID(), "Gucci", "096734622", "Dong da3");
        supplierList.addNewSupplier(s1);
        Supplier s2 = new Supplier(supplierList.createItemsID(), "Addidas", "09234324", "Số 03 đường Nguyễn Trãi, Phường Bến Nghé, Q. 1, TP HCM");
        supplierList.addNewSupplier(s2);
        Supplier s3 = new Supplier(supplierList.createItemsID(), "Ricu", "076327373", "Dong da2");
        supplierList.addNewSupplier(s3);
        Supplier s4 = new Supplier(supplierList.createItemsID(), "Ply cu", "037423432", "Cao Thắng, Phường 2, Q. 3, TP HCM");
        supplierList.addNewSupplier(s4);

        supplierList.writeToFile();

    }

    public static void G_Customer() {
        CustomerList cl = new CustomerList();
        cl.initFile();
        String hTown[] = new String[]{"14 Đống Đa, Phường 3, Thành phố Đà Lạt.", "02 Đống Đa – Phường 3 – Đà Lạt",
            "NhaTrang", "Trần Quốc Toản, Phường 1, Thành phố Đà Lạt.", "Đ. Trần Quốc Toản, Phường 8, Thành phố Đà Lạt", "Quang Ngai", "Quang Name"};
        String phoneNum = randomPhonenumber();
        boolean isMale = true;
        for (int i = 0; i < hTown.length; i++) {
            if (randomInteger(0, 20) % 2 == 0) {
                isMale = false;
            }
            Customer c1 = new Customer(cl.createCustomerID(), "Nguyen van A" + i, phoneNum, hTown[i], isMale);
            cl.addNewCustomer(c1);
        }

        cl.writeToFile();
    }

    public static void G_Item() {
        Color colors[] = new Color[]{Color.RED, Color.BLUE, Color.CYAN, Color.GREEN,
            Color.ORANGE, Color.darkGray, Color.YELLOW, Color.PINK, Color.GRAY, Color.MAGENTA};
        String materials[] = new String[]{"Cotton", "Denim", "Canvas", "Linen", "Silk", "Leather", "Suede", "Velvet", "Wool", "Fur", "Polyester"};
        String sizes[] = new String[]{"S", "M", "L", "X", "XX", "XXL"};

        ItemsList itemsList = new ItemsList();
        itemsList.initFile();

        ProductsList productsList = new ProductsList();
        productsList.initFile();
        productsList.readFromFile();

        for (int i = 0; i < 15; i++) { // create item for shoe
            ArrayList<File> arrIMG = getListIMGNotDuplicate(getArrImageShose(), 5);
            String itemName = productsList.getProductsList().get(randomInteger(0, 12)).getName();
            Items i1 = new Items(itemsList.createItemID(), itemName, 100000 * randomInteger(1, 11), sizes[randomInteger(0, 6)],
                    colors[randomInteger(0, 10)], materials[randomInteger(0, 11)], arrIMG);
            i1.setInventoryNumber(randomInteger(0, 201));
            itemsList.addNewItem(i1);
        }

        for (int i = 0; i < 20; i++) { // create TShirt
            ArrayList<File> arrIMG = getListIMGNotDuplicate(getArrImageTShirt(), 5);
            String itemName = productsList.getProductsList().get(randomInteger(12, productsList.getProductsList().size())).getName();
            Items i1 = new Items(itemsList.createItemID(), itemName, 100000 * randomInteger(1, 16), sizes[randomInteger(0, 6)],
                    colors[randomInteger(0, 10)], materials[randomInteger(0, 11)], arrIMG);
            i1.setInventoryNumber(randomInteger(0, 201));
            itemsList.addNewItem(i1);
        }
        itemsList.writeToFile();
    }

    public static StaffList G_Staff() {

        LocalDate date1 = LocalDate.of(1999, Month.JANUARY, 15);
        LocalDate date2 = LocalDate.of(1997, Month.JULY, 2);
        LocalDate date3 = LocalDate.of(2001, Month.OCTOBER, 9);

        StaffList staffList = new StaffList();
        staffList.initFile();

        AccountList accountList = new AccountList();
        accountList.initFile();
        accountList.readAccountFromFile();
        ArrayList<Account> arrAcc = accountList.getAccountList();

//        Owner
//Seller
//Cashier
        Staff st1 = new Staff(staffList.createID(), "Owner", "Phong", "Male", date1, "Dalat", randomPhonenumber(),
                randomGmail(), 5900000, true, arrAcc.get(0));
        st1.setImageProfile(new File("src/app/Images/profile IMG 4.jpg"));
        staffList.addNewStaff(st1);

        Staff st2 = new Staff(staffList.createID(), "Cashier", "Han", "Male", date2, "Dalat", randomPhonenumber(),
                randomGmail(), 6000000, false, arrAcc.get(1));
        st2.setImageProfile(new File("src/app/Images/profile IMG 2.jpg"));
        staffList.addNewStaff(st2);

        Staff st3 = new Staff(staffList.createID(), "Cashier", "Ngan", "Female", date3, "Dalat", randomPhonenumber(),
                randomGmail(), 600000, true, arrAcc.get(2));
        st3.setImageProfile(new File("src/app/Images/profile IMG 3.jpg"));
        staffList.addNewStaff(st3);

        Staff st4 = new Staff(staffList.createID(), "Cashier", "Linh", "Female", date3, "Dalat", randomPhonenumber(),
                randomGmail(), 5000000, false, arrAcc.get(3));
        st4.setImageProfile(new File("src/app/Images/profile IMG.jpg"));
        staffList.addNewStaff(st4);

        Staff st5 = new Staff(staffList.createID(), "Seller", "Phu", "Male", date3, "Dalat", randomPhonenumber(),
                randomGmail(), 5000000, true, arrAcc.get(4));
        staffList.addNewStaff(st5);

        Staff st6 = new Staff(staffList.createID(), "Seller", "Quan", "Male", date3, "Dalat", randomPhonenumber(),
                randomGmail(), 5100000, true, arrAcc.get(5));
        staffList.addNewStaff(st6);

        Staff st7 = new Staff(staffList.createID(), "Seller", "Dung", "Male", date3, "Dalat", randomPhonenumber(),
                randomGmail(), 5200000, false, arrAcc.get(6));
        staffList.addNewStaff(st7);

        return staffList;
    }

    public static void G_Bill() {

        StaffList staffList = G_Staff();

        SupplierList supplierList = new SupplierList();
        supplierList.initFile();
        supplierList.readFromFile();

        CustomerList customerList = new CustomerList();
        customerList.initFile();
        customerList.readFromFile();

        ItemsList itemsList = new ItemsList();
        itemsList.initFile();
        itemsList.readFromFile();

        VoucherList voucherList = new VoucherList();
        voucherList.initFile();
        voucherList.readFromFile();

        String description[] = new String[]{"Do chính sách của Shopee không được kiểm hàng", "phi bóng giá thành rẻ hơn",
            "giới thiệu đến quý khách hàng bộ đồ lam đi chùa", ""};

        //init timekeeping
        G_Timekeeping(staffList);
        //init payroll
        G_Payroll(staffList);
        int idBill = 100;
        // init bill import
        for (Staff staff : staffList.getStaffList()) {
            if (staff.getRole().equals("Employee")) {
                continue;
            }
            for (int i = 0; i < 10; i++) {
                int randomAmountImport = randomInteger(5, 120);
                LocalDateTime dateImport = LocalDateTime.now().minusWeeks(randomInteger(1, 6));
                if (i % 2 == 0) {
                    dateImport = LocalDateTime.now().minusMonths(randomInteger(1, 6));
                }

                Supplier supplier = supplierList.getSupplierList().get(randomInteger(0, supplierList.getSupplierList().size()));
                Items item = new Items(itemsList.getItemslist().get(randomInteger(0, itemsList.getItemslist().size())));
                String note = description[randomInteger(0, description.length)];
                BillImportGoods bill = new BillImportGoods(String.valueOf(idBill), item, supplier, note, randomAmountImport, 100000 * (i + 1) * 4);
                bill.setDateImportGoods(dateImport);

                staff.addNewBillImportItems(bill);
                idBill++;
            }

        }

        // init billsell
        BillSellList billSellList = new BillSellList();
        billSellList.initFile();
        for (Staff staff : staffList.getStaffList()) {
            if (staff.getRole().equals("Employee")) {
                continue;
            }
            int amountBill = randomInteger(4, 8);
            for (int j = 0; j < amountBill; j++) {
                int amount = randomInteger(5, 11);
                ArrayList<Items> tmp = new ArrayList<>();
                for (int i = 0; i < amount; i++) {
                    Items item = itemsList.getItemslist().get(randomInteger(0, itemsList.getItemslist().size()));
                    item.setInventoryNumber(randomInteger(1, 10));
                    tmp.add(item);
                }

                Customer customer = customerList.getCustomerList().get(randomInteger(0, customerList.getCustomerList().size()));

                LocalDateTime dateSell = LocalDateTime.now().minusWeeks(randomInteger(1, 8));
                if (amount % 2 == 0) {
                    dateSell = LocalDateTime.now().minusMonths(randomInteger(1, 10));
                } else if (amount > 4) {
                    customer = null;
                }

                Voucher voucher = voucherList.getVoucherList().get(randomInteger(0, voucherList.getVoucherList().size()));
                double money = randomInteger(1, 9) * 1000000;
                BillSell billSell = new BillSell(billSellList.createNewID(), "Note " + amount, "Chuyen khoan", money, money, tmp, staff, customer, voucher);
                billSell.setDateSell(dateSell);

                billSellList.addNewBillSell(billSell);
            }

        }
        billSellList.writeToFile();
        staffList.writeStaffIntoFile();
    }

    public static void G_Timekeeping(StaffList staffList) {
        LocalDate date = LocalDate.of(2022, Month.APRIL, 1);
        ArrayList<LocalDate> arrDate = new ArrayList<>();
        arrDate.add(date);
        arrDate.add(LocalDate.of(2022, Month.MARCH, 1));

        for (int month = 0; month < arrDate.size(); month++) {
            date = arrDate.get(month);
            for (int i = 0; i < date.lengthOfMonth(); i++) {
                for (Staff staff : staffList.getStaffList()) {
                    Timekeeping time1 = new Timekeeping(date, LocalTime.parse("08:45:00"), LocalTime.parse("22:01:00")); //good
                    int random = randomInteger(1, 100);
                    if (random == 8) {// go home soon
                        time1 = new Timekeeping(date, LocalTime.parse("09:00:00").minusMinutes(i * 3), LocalTime.parse("17:00:00").minusMinutes(i * 4));
                    }
                    if (random == 2) { //go late
                        time1 = new Timekeeping(date, LocalTime.parse("09:00:00").plusMinutes(i * 3), LocalTime.parse("17:00:00").plusMinutes(i * 5));
                    }
                    if (random == 11) {
                        time1 = new Timekeeping(date, LocalTime.parse("09:00:00").plusMinutes(i * 2), LocalTime.parse("17:00:00").minusMinutes(i * 5));
                    }
                    staff.addNewTimekeeping(time1);
                }
                date = date.plusDays(1);
            }
        }

    }

    public static void G_Payroll(StaffList staffList) {
        double feeQuitDay = 150000;
        double feeGoInLate = 100000;
        double feeGetOutSoon = 100000;

        int lastMonth = LocalDate.now().minusMonths(1).getMonthValue();
        int currentYear = LocalDate.now().getYear();

        for (Staff staff : staffList.getStaffList()) {
            int amountWorkDay = 0;
            int amountQuitDay = 0;
            int amountGetInLate = 0;
            int amountGetOutSoon = 0;

            LocalTime timeStartPrescribed = staff.getInWorkAt();
            LocalTime timeEndPrescribed = staff.getGetsOffAt();

//            System.out.println("staff.getTimekeepingLists().isEmpty(): " + staff.getTimekeepingLists().isEmpty());
            if (staff.getTimekeepingLists().isEmpty()) {
                continue;
            }

            for (int month = 3; month <= 4; month++) {
                amountWorkDay = 0;
                amountQuitDay = 0;
                amountGetInLate = 0;
                amountGetOutSoon = 0;
                for (Timekeeping timekeeping : staff.getTimekeepingLists()) {

                    if (timekeeping.getDays().getMonthValue() == month && timekeeping.getDays().getYear() == currentYear) {

                        if (timekeeping.getTimeStart().isAfter(timeStartPrescribed)) {
                            amountGetInLate += 1;
                        }
                        if (timekeeping.getTimeEnd().isBefore(timeEndPrescribed)) {
                            amountGetOutSoon += 1;
                        }
                        amountWorkDay += 1;
                    }
                }
                int amountDayOfLastMonth = LocalDate.of(2022, Month.of(month), 1).lengthOfMonth();
                amountQuitDay = amountDayOfLastMonth - amountWorkDay;
                double totalFine = (feeGetOutSoon * amountGetOutSoon) + (feeGoInLate * amountGetInLate) + (feeQuitDay * amountQuitDay);
                double totalSalay = staff.getSalary() - totalFine;
                Fines fines = new Fines(feeGoInLate, feeGetOutSoon, feeQuitDay);
                Payroll payroll = new Payroll(amountGetInLate, amountGetOutSoon, amountQuitDay, LocalDate.of(2022, Month.of(month), 1), fines, totalSalay);

                staffList.findStaffByID(staff.getID()).addNewPayroll(payroll);

            }

        }

        staffList.writeStaffIntoFile();
    }

    public static void G_Fines() {
        Fines fines = new Fines(100000, 100000, 150000);
        fines.initFile();
        fines.writeAcountIntoFile();
    }

    public static void G_QRCoded_ItemID() {
        ItemsList itemsList = new ItemsList();
        itemsList.initFile();
        itemsList.readFromFile();
        try {
            for (Items items : itemsList.getItemslist()) {
                String ID = items.getID();
                String path = "src/app/QRCodeIMG/" + ID + ".jpg";
                QRCodeGenerator.generatorQRCode(ID, path);
            }
            System.out.println("Generator QR code for Item success");
        } catch (Exception e) {
        }

    }

    public static void G_EmployeeID_QrCode() {
        try {
            StaffList list = new StaffList();
            list.initFile();
            list.readStaffFromFile();

            for (Staff s : list.getStaffList()) {
                QRCodeGenerator.generatorQRCode(s.getID(), "src/app/QRCodeIMG/" + s.getID() + ".jpg");
            }
            System.out.println("Genara QR code Employee ID Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadData() {
        G_Account(); //1
        G_Customer(); //2
        G_Product(); //3
        G_Voucher(); //4
        G_Supplier(); // 5
        G_Item(); //6
        G_Staff(); // 7
        G_Bill(); // 8 9 10 11 (Payrol, time keeping, bill import, bill sell)
        G_Fines();
        G_EmployeeID_QrCode();
        G_QRCoded_ItemID();
    }

    public static void testData() {
        BillSellList billSellList = new BillSellList();
        billSellList.initFile();
        billSellList.readFromFile();

        StaffList staffList = new StaffList();
        staffList.initFile();
        staffList.readStaffFromFile();

        System.out.println("Size list bill sell: " + billSellList.getBillSellList().size());
        staffList.getStaffList().forEach((staff) -> {
            System.out.println("Size list Timekeepingl: " + staff.getTimekeepingLists().size());
        });
    }

    public static void main(String[] args) {
        loadData();

//        testData();
    }
}

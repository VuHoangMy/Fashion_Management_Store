package app.component;

import app.BUS.BillSellList;
import app.BUS.CustomerList;
import app.BUS.ItemsList;
import app.BUS.VoucherList;
import app.CommonFuncion.ChangeColorCellTable;
import app.CommonFuncion.CommonFunction;
import app.CommonFuncion.CurrentUserLogin;
import app.CommonFuncion.PrintBill;
import app.DTO.BillSell;
import app.DTO.Customer;
import app.DTO.Items;
import app.DTO.Staff;
import app.DTO.Voucher;
import app.GUI.ItemsDetail;
import app.GUI.ScanQRCodeForm;
import app.event.EventMenu;
import app.swing.Button;
import com.beust.jcommander.converters.CommaParameterSplitter;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class SellForm extends javax.swing.JPanel {

    private EventMenu event;
    private ItemsList itemsList;
    private ItemsList TMPItemList;
    private BillSellList buyItemList;
    private CustomerList customerList;

    private VoucherList voucherList;

    private Customer customer = null;
    private double total = 0;
    private double shipFee = 0;
    private double discount = 0;
    private boolean isHaveVoucher = false;
    private boolean isPayment = false;

    private String header[] = {"ID", "Name", "Material", "Size", "Color", "Price", "Amount"};
    private DefaultTableModel defaultTableModel = new DefaultTableModel(header, 0);

    public SellForm() {
        initComponents();
        initMenu();
        setStyleComponent();
    }

    public int getAmountItemsInCart() {
        return this.TMPItemList.getItemslist().size();
    }

    private Vector addToVector(Items items) {
        Vector<Object> v = new Vector<>();
        v.add(items.getID());
        v.add(items.getName());
        v.add(items.getMaterial());
        v.add(items.getSize());
        v.add("");
        v.add(CommonFunction.formatMoney(items.getPrice()));
        if (items.getInventoryNumber() == 0) {
            v.add(1);
        } else {
            v.add(items.getInventoryNumber());
        }

        return v;
    }

    private void setEnableItemButton(boolean isEnable, String ID) {
        int index = this.itemsList.getItemslist().indexOf(this.itemsList.findItemsByID(ID));
        panel.getComponent(index).setEnabled(isEnable);
    }

    public boolean isOutOfStock(String ID) {
        if (this.TMPItemList.findItemsByID(ID) == null) {
            return false;
        }

        return this.TMPItemList.findItemsByID(ID).getInventoryNumber() >= this.itemsList.findItemsByID(ID).getInventoryNumber();
    }

    public boolean isItemHaveInCart(String ID) {
        Items item = this.TMPItemList.findItemsByID(ID);
        return item == null ? false : true;
    }

    public void addToTMPList(String ID) {

        if (isItemHaveInCart(ID)) {
            int amount = Integer.parseInt(table.getValueAt(findRow(ID), 6).toString()) + 1;
            this.TMPItemList.findItemsByID(ID).setInventoryNumber(amount);
        } else {
            Items i1 = new Items(this.itemsList.findItemsByID(ID)); // Warning
            i1.setInventoryNumber(1);
            this.TMPItemList.addNewItem(i1);
        }

        if (isOutOfStock(ID)) {
            setEnableItemButton(false, ID);
        }

        loadDataTable(this.TMPItemList);
        caculateTotalInvoice();

        setEnableCheckBoxVoucher();

    }

    private boolean isCanUseVoucher() {
        for (Voucher voucher : this.voucherList.getVoucherList()) {
            if (this.total >= voucher.getTotalMoneyCondition()) {
                return true;
            }
        }
        return false;
    }

    private void updateItemsInventoryNumber(ArrayList<Items> iLists) {

        for (Items i : iLists) {
            int oldInventoryNum = this.itemsList.findItemsByID(i.getID()).getInventoryNumber();
            int newInventoryNum = oldInventoryNum - i.getInventoryNumber();
            this.itemsList.updateItemInventory(i.getID(), newInventoryNum);
        }
        this.itemsList.writeToFile();
    }

    private void createNewBill(String ID, String note, String paymentsBy, double moneyCustomerGive,
            double totalInvoice, Staff staff, ArrayList<Items> iLists, Customer customer, Voucher voucher) {
        boolean respone = CommonFunction.isConfirmDialog("Confirm", "Xac nhan thanh toan.");
        if (!respone) {
            return;
        }

        BillSell billSell = new BillSell(ID, note, paymentsBy, moneyCustomerGive, totalInvoice, iLists, staff, customer, voucher);

        this.buyItemList.addNewBillSell(billSell);
        this.buyItemList.writeToFile();

        updateItemsInventoryNumber(iLists);
        if (this.tabbedPane.getSelectedIndex() == 0) {
            refreshTab1();
        } else {
            refreshTab2();
        }

        this.isPayment = true;

//            create bill
        eventPrintBill(billSell);
        eventRemoveAllDataTable();
    }

    private void eventPrintBill(BillSell billSell) {
        boolean respone = CommonFunction.isConfirmDialog("Confirm", "Ban Co muon in hoa don khong?.");
        if (!respone) {
            return;
        }
        new PrintBill(billSell);
    }

    private int findRow(String ID) {
        int amountRow = table.getRowCount();
        for (int i = 0; i < amountRow; i++) {
            if (table.getValueAt(i, 0).toString().equals(ID)) {
                return i;
            }
        }
        return -100;
    }

    private void loadDataTable(ItemsList itemsList) {
        defaultTableModel.setRowCount(0);
        for (Items i : itemsList.getItemslist()) {
            Vector<Object> v = addToVector(i);
            defaultTableModel.addRow(v);
        }
        table.setModel(defaultTableModel);
        new ChangeColorCellTable().changeTable(table, 4, 1, itemsList);
        table.getColumnModel().getColumn(4).setMaxWidth(50);
    }

    private void initMenu() {
        event = new EventMenu() {
            @Override
            public void selectMenu(int index) {
                String ID = String.valueOf(index);
                addToTMPList(ID);
            }
        };
    }

    private void showItems(Icon icon, String name, int index, Color itemColor, boolean isEnable) {
        Button menu = new Button();
        menu.setHorizontalTextPosition(SwingConstants.CENTER);
        menu.setVerticalTextPosition(SwingConstants.BOTTOM);
        menu.setBounds(index, index, WIDTH, HEIGHT);
        menu.setText(name);
        if (name.length() > 10) {
            menu.setText(name.substring(0, 10));
        }

        menu.setEnabled(isEnable);
//        menu.setForeground(new Color(106, 11, 208, 100));
        menu.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        menu.setIcon(icon);
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event.selectMenu(index);
            }
        });

        menu.setBackgroundColorTop(itemColor);

        panel.add(menu);
        revalidate();
        repaint();
    }

    private void initItems(ItemsList itemsList) {
        panel.removeAll();
        for (Items item : itemsList.getItemslist()) {
            int index = Integer.parseInt(item.getID());
            ImageIcon oldIcon = null;
            if (item.getImageList() != null) {
                oldIcon = new ImageIcon(item.getImageList().get(0).getAbsolutePath());
            } else {
                oldIcon = new ImageIcon("src/app/Images/No IMG.png");
            }
            ImageIcon icon = CommonFunction.resizeImage(oldIcon, 100, 100);
            boolean isEnable = false;
            if (item.getInventoryNumber() > 0) {
                isEnable = true;
            }
            showItems(icon, item.getName(), index, item.getColor(), isEnable);
        }
    }

    private void setStyleComponent() {
        table.setModel(defaultTableModel);
        panel.setLayout(new MigLayout("wrap 5, inset 30", "[fill, 100]80[fill, 100]", "[]40[]"));
        scrollPane2.setOpaque(false);
        scrollPane2.getViewport().setOpaque(false);
        CommonFunction.initScrollbar(scrollPane); // Custome Scrollbar
        CommonFunction.initScrollbar(scrollPane2); // Custome Scrollbar
        setStyleButton();
    }

    public void loadDataFromFile() {
        this.itemsList = new ItemsList();
        this.itemsList.initFile();
        this.itemsList.readFromFile();

        this.buyItemList = new BillSellList();
        this.buyItemList.initFile();
        this.buyItemList.readFromFile();

        this.TMPItemList = new ItemsList();

        this.customerList = new CustomerList();
        this.customerList.initFile();
        this.customerList.readFromFile();

        VoucherList tmp = new VoucherList();
        tmp.initFile();
        tmp.readFromFile();
        this.voucherList = getVoucherAvailable(tmp);

        initItems(this.itemsList);

        loadDataToVoucherCombobox(this.voucherList);
    }

    private VoucherList getVoucherAvailable(VoucherList voucherList) {
        LocalDate today = LocalDate.now();
        VoucherList result = new VoucherList();

        for (Voucher voucher : voucherList.getVoucherList()) {
            if (today.isBefore(voucher.getFromDate()) || today.isAfter(voucher.getToDate())) {
                continue;
            }
            result.addNewVoucher(voucher);
            this.isHaveVoucher = true;
        }
        return result;
    }

    private void eventUpdateInventoryNumberInTable(String ID, int row) {
        /*
        * update collumn amount in table when change value of it       
         */
        int newInventory = 0;
        int colAmount = 6;
        newInventory = Integer.parseInt(table.getValueAt(row, colAmount).toString());

        if (newInventory > this.itemsList.findItemsByID(ID).getInventoryNumber()) {
            table.setValueAt(this.itemsList.findItemsByID(ID).getInventoryNumber(), row, colAmount);
            newInventory = this.itemsList.findItemsByID(ID).getInventoryNumber();
        }

        this.TMPItemList.findItemsByID(ID).setInventoryNumber(newInventory);
        setEnableItemButton(true, ID);

        if (isOutOfStock(ID)) {
            setEnableItemButton(false, ID);
        }
    }

    private void caculateTotalInvoice() {
        int amountRow = table.getRowCount();
        int colAmount = 6;
        int colPrice = 5;
        this.total = 0;
        String ID = "";
        for (int i = 0; i < amountRow; i++) {
            if (i == table.getSelectedRow()) {
                ID = table.getValueAt(table.getSelectedRow(), 0).toString();
                eventUpdateInventoryNumberInTable(ID, i);
            }
            int amount = Integer.parseInt(table.getValueAt(i, colAmount).toString());
            double price = CommonFunction.formatStringToMoney(table.getValueAt(i, colPrice).toString());
            double totalInvoice = amount * price;
            this.total += totalInvoice;
        }

        this.total += this.shipFee;
        this.total -= this.discount;
        showTotalBill();
    }

    private void showTotalBill() {
        this.lbl_totalInvoice.setText(CommonFunction.formatMoney(total));
        this.lbl_totalInvoice1.setText(CommonFunction.formatMoney(total));
    }

    private boolean isValidateDataInput() {

        if (table.getRowCount() < 1) {
            JOptionPane.showMessageDialog(this, "Please Select Items", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (this.tabbedPane.getSelectedIndex() == 0) {
            if (this.txt_CGive.getText().isEmpty() || ((this.txt_CustomerName.getText().isEmpty() || this.txt_Phonenumber.getText().isEmpty()) && this.cbox_TypeCustomer.getSelectedIndex() == 1)) {
                JOptionPane.showMessageDialog(this, "Please Input Infomation", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (CommonFunction.formatStringToMoney(this.txt_CGive.getText()) == -1000) {
                JOptionPane.showMessageDialog(this, "Tien Khach dua phai la so!", "ERROR", JOptionPane.ERROR_MESSAGE);
                this.txt_CGive.setText("");
                return false;
            }

        } else {
            if (this.txt_CGive1.getText().isEmpty() || this.txt_CustomerName1.getText().isEmpty() || this.txt_Phonenumber1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Input Infomation", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (CommonFunction.formatStringToMoney(this.txt_CGive1.getText()) == -1000) {
                JOptionPane.showMessageDialog(this, "Tien Khach dua phai la so!", "ERROR", JOptionPane.ERROR_MESSAGE);
                this.txt_CGive1.setText("");
                return false;
            }

            if (!CommonFunction.isNumeric(this.txt_ShipPay.getText())) {
                JOptionPane.showMessageDialog(this, "Phi Ship phai la so!", "ERROR", JOptionPane.ERROR_MESSAGE);
                this.txt_ShipPay.setText("");
                return false;
            }

            boolean resultPhoneNum = CommonFunction.validatePhoneNumber(this.txt_Phonenumber.getText());
            boolean resultPhoneNum1 = CommonFunction.validatePhoneNumber(this.txt_Phonenumber1.getText());

            if (!resultPhoneNum && !resultPhoneNum1) {
                JOptionPane.showMessageDialog(this, "Nhap lai SDT", "ERROR", JOptionPane.ERROR_MESSAGE);

                return false;

            }

        }
        return true;
    }

    private void eventCustomerInputMoney() {
        if (!this.txt_CGive.getText().isEmpty() || !this.txt_CGive1.getText().isEmpty()) {
            double moneyCustomerGive = 0;

            try {
                if (this.tabbedPane.getSelectedIndex() == 0) {
                    moneyCustomerGive = CommonFunction.formatStringToMoney(this.txt_CGive.getText());
                } else {
                    moneyCustomerGive = CommonFunction.formatStringToMoney(this.txt_CGive1.getText());
                }
            } catch (Exception e) {
            }

            this.lbl_excessCash.setText(CommonFunction.formatMoney(moneyCustomerGive - total));
            this.lbl_excessCash1.setText(CommonFunction.formatMoney(moneyCustomerGive - total));

        } else {
            this.lbl_excessCash.setText("0 VND.");
            this.lbl_excessCash1.setText("0 VND.");
        }

    }

    private void eventShipPyamentInput(String txtMoney) {
        try {
            double money = Double.parseDouble(txtMoney);
            this.shipFee = money;
            caculateTotalInvoice();
        } catch (Exception e) {
            this.shipFee = 0;
        }
    }

    private void eventApplyDiscount() {
        int indexSelected = 0;
        this.discount = 0;
        if (this.tabbedPane.getSelectedIndex() == 0) {
            indexSelected = this.cbox_Voucher.getSelectedIndex();
        } else {
            indexSelected = this.cbox_Voucher1.getSelectedIndex();
        }

        if (indexSelected < 0) {
            return;
        }

        System.out.println("Voucher name: " + this.voucherList.getVoucherList().get(indexSelected).getName());
        double discountPercent = this.voucherList.getVoucherList().get(indexSelected).getDiscountPercent();

        this.discount = (discountPercent / 100) * this.total;
        caculateTotalInvoice();
    }

    private void eventRemoveAllDataTable() {
        if (!this.TMPItemList.getItemslist().isEmpty() && !isPayment) {
            boolean result = CommonFunction.isConfirmDialog("Clear", "Confirm. Delete all products in the cart!");
            if (!result) {
                return;
            }
        }

        this.isPayment = false;

        this.TMPItemList.getItemslist().removeAll(this.TMPItemList.getItemslist());
        loadDataTable(TMPItemList);
        total = 0;
        this.discount = 0;
        this.lbl_totalInvoice.setText("0 VND.");
        this.lbl_excessCash.setText("0 VND.");
        this.lbl_totalInvoice1.setText("0 VND.");
        this.lbl_excessCash1.setText("0 VND.");
        initItems(this.itemsList);
        setEnableCheckBoxVoucher();
    }

    private void eventRemoveItem() {
        try {
            int row = table.getSelectedRow();
            this.TMPItemList.getItemslist().remove(row);
            loadDataTable(TMPItemList);

            caculateTotalInvoice();
            setEnableCheckBoxVoucher();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please Select row!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchItems(String txtSearch) {
        ItemsList TMP = this.itemsList.findListItemByIDOrName(txtSearch);
        initItems(TMP);
    }

    private void showDetaiItem() {
        try {
            String ID = table.getValueAt(table.getSelectedRow(), 0).toString();
            Items i = this.itemsList.findItemsByID(ID);
            ItemsDetail form = new ItemsDetail(i);
            form.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please Select row!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getDataTab1() {

        //customer
        String nameC = this.txt_CustomerName.getText();
        String phonenum = this.txt_Phonenumber.getText();

        String ID = this.buyItemList.createNewID();
        String note = this.txt_Note.getText();
        String paymentsBy = this.cbox_PaymentBy.getSelectedItem().toString();
        double moneyCustomerGive = CommonFunction.formatStringToMoney(this.txt_CGive.getText());
        double totalInvoice = total;

        Staff staff = CurrentUserLogin.getCurrentStaff().getCurrentuserLogin();
        ArrayList<Items> iLists = this.TMPItemList.getItemslist();
        if (customer == null) {
            this.customer = new Customer(nameC, phonenum, "");
        }

        Voucher voucher = null;
        if (isCanUseVoucher()) {
            voucher = this.voucherList.getVoucherList().get(this.cbox_Voucher.getSelectedIndex());
        }

        createNewBill(ID, note, paymentsBy, moneyCustomerGive, totalInvoice, staff, iLists, customer, voucher);
    }

    private void getDataTab2() {
        //Customer
        String nameC = this.txt_CustomerName1.getText();
        String phonenum = this.txt_Phonenumber1.getText();
        String address = this.txt_Address.getText();

        String ID = this.buyItemList.createNewID();
        String note = this.txt_Note1.getText();
        String paymentsBy = this.cbox_PaymentBy1.getSelectedItem().toString();
        double moneyCustomerGive = CommonFunction.formatStringToMoney(this.txt_CGive1.getText());
        double totalInvoice = total;

        Staff staff = CurrentUserLogin.getCurrentStaff().getCurrentuserLogin();
        ArrayList<Items> iLists = this.TMPItemList.getItemslist();
        if (customer == null) {
            this.customer = new Customer(nameC, phonenum, address);
        }

        Voucher voucher = null;
        if (isCanUseVoucher()) {
            voucher = this.voucherList.getVoucherList().get(this.cbox_Voucher1.getSelectedIndex());
        }

        createNewBill(ID, note, paymentsBy, moneyCustomerGive, totalInvoice, staff, iLists, customer, voucher);
    }

    private void refreshTab1() {
        this.lbl_totalInvoice.setText("0 VND.");
        this.lbl_excessCash.setText("0 VND.");
        this.txt_CGive.setText("");
        this.txt_Note.setText("");
        this.txt_CGive.setText("");
        this.txt_CustomerName.setText("");
        this.txt_Phonenumber.setText("");
        this.cbox_PaymentBy.setSelectedIndex(0);
    }

    private void refreshTab2() {
        this.lbl_totalInvoice1.setText("0 VND.");
        this.lbl_excessCash1.setText("0 VND.");
        this.txt_CGive1.setText("");
        this.txt_Note1.setText("");
        this.txt_CGive1.setText("");
        this.txt_CustomerName1.setText("");
        this.txt_Phonenumber1.setText("");
        this.cbox_PaymentBy1.setSelectedIndex(0);
        this.txt_Address.setText("");;
        this.txt_ShipPay.setText("");
    }

    private void setEnableCheckBoxVoucher() {
        this.chBox_Voucher.setEnabled(isCanUseVoucher());
        this.chBox_Voucher1.setEnabled(isCanUseVoucher());
        if (!isCanUseVoucher()) {
            this.chBox_Voucher.setSelected(false);
            this.chBox_Voucher1.setSelected(false);
            setEnableCombobox(true);
            setEnableCombobox(false);
            return;
        }
        VoucherList tmp = new VoucherList();
        for (Voucher voucher : this.voucherList.getVoucherList()) {
            if (this.total >= voucher.getTotalMoneyCondition()) {
                tmp.addNewVoucher(voucher);
            }
        }
        loadDataToVoucherCombobox(tmp);
    }

    private void setEnableCombobox(boolean isChBoxVoucher) {
        if (isChBoxVoucher) {
            this.cbox_Voucher.setEnabled(this.chBox_Voucher.isSelected());
        } else {
            this.cbox_Voucher1.setEnabled(this.chBox_Voucher1.isSelected());
        }
    }

    private void loadDataToVoucherCombobox(VoucherList voucherList) {
        this.cbox_Voucher.removeAllItems();
        this.cbox_Voucher1.removeAllItems();

        for (Voucher voucher : voucherList.getVoucherList()) {
            String pecent = " (" + voucher.getDiscountPercent() + " %)";
            this.cbox_Voucher.addItem(voucher.getName() + pecent);
            this.cbox_Voucher1.addItem(voucher.getName() + pecent);
        }
        if (!isHaveVoucher) {
            this.cbox_Voucher.addItem("Voucher");
            this.cbox_Voucher1.addItem("Voucher");
        }

        this.cbox_Voucher.setSelectedIndex(0);
        this.cbox_Voucher1.setSelectedIndex(0);
    }

    private void eventBackToPreviousMenu() {
        System.out.println("Size TMP: " + !this.TMPItemList.getItemslist().isEmpty());
        if (!this.TMPItemList.getItemslist().isEmpty()) {
            boolean result = CommonFunction.isConfirmDialog("Confirm", "Confirm. Then the data will be lost!");
            if (!result) {
                return;
            }
        }
        refreshTab1();
        refreshTab2();
        eventRemoveAllDataTable();
        if (CurrentUserLogin.getCurrentStaff().getCurrentuserLogin().getRole().equals("Cashier")) {
            CommonFunction.backToMainMenu();
            return;
        }
        CommonFunction.backToTransactionMenu();
    }

    private void eventBackToHomeMenu() {
        eventBackToPreviousMenu();
        if (CurrentUserLogin.getCurrentStaff().getCurrentuserLogin().getRole().equals("Cashier")) {
            return;
        }
        CommonFunction.backToMainMenu();
    }

    private void setStyleButton() {
        this.btn_Remove.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_RemoveAll.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_ShowDetail.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Payment.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Payment1.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Redresh1.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Refresh.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_Back = new app.swing.Button();
        formName = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        scrollPane2 = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        BillPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txt_CustomerName = new javax.swing.JTextField();
        txt_Phonenumber = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        cbox_PaymentBy = new javax.swing.JComboBox<>();
        txt_CGive = new javax.swing.JTextField();
        lbl_totalInvoice = new javax.swing.JLabel();
        lbl_excessCash = new javax.swing.JLabel();
        btn_Payment = new app.swing.Button();
        jSeparator2 = new javax.swing.JSeparator();
        txt_Note = new javax.swing.JTextArea();
        btn_Refresh = new app.swing.Button();
        btn_PrintBill = new app.swing.Button();
        cbox_TypeCustomer = new javax.swing.JComboBox<>();
        chBox_Voucher = new javax.swing.JCheckBox();
        cbox_Voucher = new javax.swing.JComboBox<>();
        paymentsPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_CustomerName1 = new javax.swing.JTextField();
        txt_Phonenumber1 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        txt_Address = new javax.swing.JTextField();
        cbox_PaymentBy1 = new javax.swing.JComboBox<>();
        txt_Note1 = new javax.swing.JTextArea();
        lbl_totalInvoice1 = new javax.swing.JLabel();
        txt_CGive1 = new javax.swing.JTextField();
        lbl_excessCash1 = new javax.swing.JLabel();
        txt_ShipPay = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        btn_Payment1 = new app.swing.Button();
        btn_Bill1 = new app.swing.Button();
        btn_Redresh1 = new app.swing.Button();
        chBox_Voucher1 = new javax.swing.JCheckBox();
        cbox_Voucher1 = new javax.swing.JComboBox<>();
        btn_Remove = new app.swing.Button();
        btn_RemoveAll = new app.swing.Button();
        btn_ShowDetail = new app.swing.Button();
        txt_Search = new javax.swing.JTextField();
        btn_Search = new app.swing.Button();
        btn_RefreshSearch = new app.swing.Button();
        btn_Home = new app.swing.Button();
        btn_Scan = new app.swing.Button();
        Background = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1800, 900));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Back.setForeground(new java.awt.Color(51, 51, 51));
        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 60, 60));

        formName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        formName.setForeground(new java.awt.Color(255, 255, 255));
        formName.setText("Sales");
        add(formName, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.setOpaque(false);
        table.setRowHeight(25);
        table.setRowMargin(5);
        table.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablePropertyChange(evt);
            }
        });
        scrollPane.setViewportView(table);

        add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 640, 1060, 230));

        panel.setBackground(new java.awt.Color(204, 204, 204,1));
        panel.setOpaque(false);
        scrollPane2.setViewportView(panel);

        add(scrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 1060, 380));

        tabbedPane.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));

        BillPanel.setBackground(new java.awt.Color(255, 255, 255));
        BillPanel.setOpaque(false);
        BillPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Phonenumber");
        BillPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Customer Name");
        BillPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 86, -1, 20));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Payment By");
        BillPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 155, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Total Money");
        BillPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Customer Give");
        BillPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Change");
        BillPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Note");
        BillPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Khach");
        BillPanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, -1, 30));

        txt_CustomerName.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_CustomerName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        txt_CustomerName.setForeground(new java.awt.Color(0, 0, 102));
        txt_CustomerName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_CustomerName.setOpaque(false);
        BillPanel.add(txt_CustomerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 360, 30));

        txt_Phonenumber.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Phonenumber.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        txt_Phonenumber.setForeground(new java.awt.Color(0, 0, 102));
        txt_Phonenumber.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_Phonenumber.setOpaque(false);
        txt_Phonenumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_PhonenumberKeyReleased(evt);
            }
        });
        BillPanel.add(txt_Phonenumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 360, 30));

        jSeparator1.setForeground(new java.awt.Color(255, 51, 51));
        BillPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 550, 10));

        cbox_PaymentBy.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        cbox_PaymentBy.setForeground(new java.awt.Color(0, 153, 153));
        cbox_PaymentBy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Transfer" }));
        cbox_PaymentBy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbox_PItemStateChanged(evt);
            }
        });
        BillPanel.add(cbox_PaymentBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 150, 30));

        txt_CGive.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_CGive.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txt_CGive.setForeground(new java.awt.Color(255, 255, 0));
        txt_CGive.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_CGive.setOpaque(false);
        txt_CGive.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_CGiveKeyReleased(evt);
            }
        });
        BillPanel.add(txt_CGive, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, 360, 40));

        lbl_totalInvoice.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        lbl_totalInvoice.setForeground(new java.awt.Color(255, 102, 102));
        lbl_totalInvoice.setText("0 VND.");
        BillPanel.add(lbl_totalInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 320, 220, -1));

        lbl_excessCash.setFont(new java.awt.Font("Yu Gothic Medium", 0, 18)); // NOI18N
        lbl_excessCash.setForeground(new java.awt.Color(102, 255, 0));
        lbl_excessCash.setText("0 VND.");
        BillPanel.add(lbl_excessCash, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 430, 240, -1));

        btn_Payment.setForeground(new java.awt.Color(0, 255, 0));
        btn_Payment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/money.png"))); // NOI18N
        btn_Payment.setText("Pay");
        btn_Payment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PaymentActionPerformed(evt);
            }
        });
        BillPanel.add(btn_Payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 570, 220, 50));

        jSeparator2.setForeground(new java.awt.Color(255, 51, 51));
        BillPanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 122, 550, 10));

        txt_Note.setBackground(new java.awt.Color(204, 204, 204,1));
        txt_Note.setColumns(20);
        txt_Note.setFont(new java.awt.Font("Monospaced", 0, 17)); // NOI18N
        txt_Note.setForeground(new java.awt.Color(0, 153, 153));
        txt_Note.setRows(5);
        txt_Note.setAutoscrolls(false);
        txt_Note.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));
        txt_Note.setOpaque(false);
        BillPanel.add(txt_Note, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 360, 80));

        btn_Refresh.setForeground(new java.awt.Color(0, 0, 255));
        btn_Refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Refresh.png"))); // NOI18N
        btn_Refresh.setText("Refresh");
        btn_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefreshActionPerformed(evt);
            }
        });
        BillPanel.add(btn_Refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 480, 120, 50));

        btn_PrintBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/BillIcon.png"))); // NOI18N
        btn_PrintBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PrintBillActionPerformed(evt);
            }
        });
        BillPanel.add(btn_PrintBill, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 570, 80, -1));

        cbox_TypeCustomer.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        cbox_TypeCustomer.setForeground(new java.awt.Color(0, 153, 153));
        cbox_TypeCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Member" }));
        BillPanel.add(cbox_TypeCustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 150, 170, 30));

        chBox_Voucher.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        chBox_Voucher.setEnabled(false);
        chBox_Voucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chBox_VoucherActionPerformed(evt);
            }
        });
        BillPanel.add(chBox_Voucher, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 495, -1, 30));

        cbox_Voucher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Voucher" }));
        cbox_Voucher.setEnabled(false);
        cbox_Voucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbox_VoucherActionPerformed(evt);
            }
        });
        BillPanel.add(cbox_Voucher, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 490, 170, 30));

        tabbedPane.addTab("Bill", BillPanel);

        paymentsPanel.setOpaque(false);
        paymentsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Ten Khach Hang");
        paymentsPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("SDT");
        paymentsPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Dia Chi");
        paymentsPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Thanh Toan Bang");
        paymentsPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Note");
        paymentsPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Tong tien hang");
        paymentsPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, -1, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Tong Khach dua");
        paymentsPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, -1, 30));

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Tien Thua");
        paymentsPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 520, -1, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Phi Giao Hang");
        paymentsPanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, -1, 30));

        txt_CustomerName1.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_CustomerName1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        txt_CustomerName1.setForeground(new java.awt.Color(0, 0, 102));
        txt_CustomerName1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        paymentsPanel.add(txt_CustomerName1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 360, 30));

        txt_Phonenumber1.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Phonenumber1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        txt_Phonenumber1.setForeground(new java.awt.Color(0, 0, 102));
        txt_Phonenumber1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_Phonenumber1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_Phonenumber1KeyReleased(evt);
            }
        });
        paymentsPanel.add(txt_Phonenumber1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 360, 30));
        paymentsPanel.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 560, 550, 20));

        txt_Address.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Address.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        txt_Address.setForeground(new java.awt.Color(0, 0, 102));
        txt_Address.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        paymentsPanel.add(txt_Address, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 360, 30));

        cbox_PaymentBy1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tien Mat", "Chuyen Khoan", "Quet The" }));
        cbox_PaymentBy1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbox_PaymentBy1cbox_PItemStateChanged(evt);
            }
        });
        paymentsPanel.add(cbox_PaymentBy1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 180, 30));

        txt_Note1.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Note1.setColumns(20);
        txt_Note1.setFont(new java.awt.Font("Monospaced", 0, 17)); // NOI18N
        txt_Note1.setRows(5);
        txt_Note1.setAutoscrolls(false);
        txt_Note1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));
        txt_Note1.setOpaque(false);
        paymentsPanel.add(txt_Note1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 360, 80));

        lbl_totalInvoice1.setFont(new java.awt.Font("Yu Gothic", 0, 18)); // NOI18N
        lbl_totalInvoice1.setForeground(new java.awt.Color(255, 102, 102));
        lbl_totalInvoice1.setText("0 VND.");
        paymentsPanel.add(lbl_totalInvoice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, 240, 20));

        txt_CGive1.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_CGive1.setFont(new java.awt.Font("Yu Gothic Medium", 0, 18)); // NOI18N
        txt_CGive1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_CGive1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_CGive1KeyReleased(evt);
            }
        });
        paymentsPanel.add(txt_CGive1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 440, 360, 40));

        lbl_excessCash1.setFont(new java.awt.Font("Yu Gothic Medium", 0, 18)); // NOI18N
        lbl_excessCash1.setForeground(new java.awt.Color(102, 255, 0));
        lbl_excessCash1.setText("0 VND.");
        paymentsPanel.add(lbl_excessCash1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 510, 240, -1));

        txt_ShipPay.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_ShipPay.setFont(new java.awt.Font("Yu Gothic Medium", 0, 18)); // NOI18N
        txt_ShipPay.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_ShipPay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_ShipPayKeyReleased(evt);
            }
        });
        paymentsPanel.add(txt_ShipPay, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, 360, 40));
        paymentsPanel.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 550, 20));

        btn_Payment1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/money.png"))); // NOI18N
        btn_Payment1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Payment1ActionPerformed(evt);
            }
        });
        paymentsPanel.add(btn_Payment1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 170, 50));

        btn_Bill1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/BillIcon.png"))); // NOI18N
        paymentsPanel.add(btn_Bill1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 570, 130, 60));

        btn_Redresh1.setForeground(new java.awt.Color(0, 0, 255));
        btn_Redresh1.setText("Refresh");
        btn_Redresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Redresh1ActionPerformed(evt);
            }
        });
        paymentsPanel.add(btn_Redresh1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 500, 140, -1));

        chBox_Voucher1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        chBox_Voucher1.setEnabled(false);
        chBox_Voucher1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chBox_Voucher1ActionPerformed(evt);
            }
        });
        paymentsPanel.add(chBox_Voucher1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 180, -1, 30));

        cbox_Voucher1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Voucher" }));
        cbox_Voucher1.setEnabled(false);
        cbox_Voucher1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbox_Voucher1ActionPerformed(evt);
            }
        });
        paymentsPanel.add(cbox_Voucher1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, 150, 30));

        tabbedPane.addTab("Order", paymentsPanel);

        add(tabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 190, 560, 680));

        btn_Remove.setForeground(new java.awt.Color(204, 0, 0));
        btn_Remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Delete.png"))); // NOI18N
        btn_Remove.setText("Remove");
        btn_Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RemoveActionPerformed(evt);
            }
        });
        add(btn_Remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 580, 120, 50));

        btn_RemoveAll.setForeground(new java.awt.Color(255, 255, 255));
        btn_RemoveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/DeleteAll.png"))); // NOI18N
        btn_RemoveAll.setText("Remove All");
        btn_RemoveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RemoveAllActionPerformed(evt);
            }
        });
        add(btn_RemoveAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 580, 140, 50));

        btn_ShowDetail.setForeground(new java.awt.Color(255, 255, 0));
        btn_ShowDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Detail.png"))); // NOI18N
        btn_ShowDetail.setText("Detail");
        btn_ShowDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ShowDetailActionPerformed(evt);
            }
        });
        add(btn_ShowDetail, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 580, 120, 50));

        txt_Search.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_Search.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        txt_Search.setForeground(new java.awt.Color(153, 255, 255));
        txt_Search.setText("Search");
        txt_Search.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_Search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_SearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_SearchFocusLost(evt);
            }
        });
        txt_Search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_SearchKeyReleased(evt);
            }
        });
        add(txt_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 122, 440, 40));

        btn_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Search.png"))); // NOI18N
        btn_Search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_SearchMouseClicked(evt);
            }
        });
        btn_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SearchActionPerformed(evt);
            }
        });
        add(btn_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 120, -1, 40));

        btn_RefreshSearch.setForeground(new java.awt.Color(204, 204, 255));
        btn_RefreshSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Refresh.png"))); // NOI18N
        btn_RefreshSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefreshSearchActionPerformed(evt);
            }
        });
        add(btn_RefreshSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, 80, 40));

        btn_Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/home.png"))); // NOI18N
        btn_Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HomeActionPerformed(evt);
            }
        });
        add(btn_Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 60, 60));

        btn_Scan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Scan.png"))); // NOI18N
        btn_Scan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ScanActionPerformed(evt);
            }
        });
        add(btn_Scan, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 580, 50, 50));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor5.jpg"))); // NOI18N
        add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        eventBackToPreviousMenu();
    }//GEN-LAST:event_btn_BackActionPerformed

    private void btn_RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RemoveActionPerformed
        eventRemoveItem();
    }//GEN-LAST:event_btn_RemoveActionPerformed

    private void btn_RemoveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RemoveAllActionPerformed
        eventRemoveAllDataTable();
    }//GEN-LAST:event_btn_RemoveAllActionPerformed

    private void btn_PaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PaymentActionPerformed
        if (isValidateDataInput()) {
            if (CommonFunction.formatStringToMoney(this.txt_CGive.getText()) 
                    < CommonFunction.formatStringToMoney(this.lbl_totalInvoice.getText())) {
                boolean result = CommonFunction.isConfirmDialog("Warning", "Customer give not enough! Do you wanna countinue?");
                if(!result){
                    return;
                }
            }
            getDataTab1();
        }
    }//GEN-LAST:event_btn_PaymentActionPerformed

    private void tablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablePropertyChange
        caculateTotalInvoice();
    }//GEN-LAST:event_tablePropertyChange

    private void cbox_PItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbox_PItemStateChanged

        if (this.cbox_PaymentBy.getSelectedIndex() == 1) {
            this.txt_CGive.setText(CommonFunction.formatMoney(this.total));
        }
    }//GEN-LAST:event_cbox_PItemStateChanged

    private void btn_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefreshActionPerformed
        refreshTab1();
    }//GEN-LAST:event_btn_RefreshActionPerformed

    private void btn_ShowDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ShowDetailActionPerformed
        showDetaiItem();
    }//GEN-LAST:event_btn_ShowDetailActionPerformed

    private void btn_RefreshSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefreshSearchActionPerformed
        initItems(this.itemsList);
        this.txt_Search.setText("Search");
    }//GEN-LAST:event_btn_RefreshSearchActionPerformed

    private void btn_SearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SearchMouseClicked
        searchItems(this.txt_Search.getText());
    }//GEN-LAST:event_btn_SearchMouseClicked

    private void txt_SearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusGained
        CommonFunction.setPlaceHolder(txt_Search, true, "Search");
    }//GEN-LAST:event_txt_SearchFocusGained

    private void txt_SearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusLost
        CommonFunction.setPlaceHolder(txt_Search, false, "Search");
    }//GEN-LAST:event_txt_SearchFocusLost

    private void cbox_PaymentBy1cbox_PItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbox_PaymentBy1cbox_PItemStateChanged
        if (this.cbox_PaymentBy1.getSelectedIndex() == 1) {
            this.txt_CGive1.setText(CommonFunction.formatMoney(this.total));
        }
    }//GEN-LAST:event_cbox_PaymentBy1cbox_PItemStateChanged

    private void btn_Payment1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Payment1ActionPerformed
        if (isValidateDataInput()) {
            getDataTab2();
        }
    }//GEN-LAST:event_btn_Payment1ActionPerformed

    private void btn_Redresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Redresh1ActionPerformed
        refreshTab2();
    }//GEN-LAST:event_btn_Redresh1ActionPerformed

    private void btn_HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HomeActionPerformed
        eventBackToHomeMenu();
    }//GEN-LAST:event_btn_HomeActionPerformed

    private void btn_ScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ScanActionPerformed
        ScanQRCodeForm form = new ScanQRCodeForm(1, this);
        form.setVisible(true);
    }//GEN-LAST:event_btn_ScanActionPerformed

    private void btn_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SearchActionPerformed
        searchItems(this.txt_Search.getText());
    }//GEN-LAST:event_btn_SearchActionPerformed

    private void btn_PrintBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PrintBillActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_PrintBillActionPerformed

    private void chBox_VoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chBox_VoucherActionPerformed
        setEnableCombobox(true);
        if (chBox_Voucher.isSelected()) {
            eventApplyDiscount();
        } else {
            this.total += this.discount;
            this.discount = 0;
            showTotalBill();
        }
    }//GEN-LAST:event_chBox_VoucherActionPerformed

    private void chBox_Voucher1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chBox_Voucher1ActionPerformed
        setEnableCombobox(false);
        if (chBox_Voucher1.isSelected()) {
            eventApplyDiscount();
        } else {
            this.total += this.discount;
            this.discount = 0;
            showTotalBill();
        }
    }//GEN-LAST:event_chBox_Voucher1ActionPerformed

    private void cbox_VoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbox_VoucherActionPerformed
        if (isCanUseVoucher()) {
            if (this.chBox_Voucher.isSelected()) {
                this.total += discount;
                eventApplyDiscount();
            }
        }
    }//GEN-LAST:event_cbox_VoucherActionPerformed

    private void txt_SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyReleased
        searchItems(this.txt_Search.getText());
    }//GEN-LAST:event_txt_SearchKeyReleased

    private void txt_PhonenumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PhonenumberKeyReleased
        String phoneNum = this.txt_Phonenumber.getText();
        Customer c = this.customerList.findCustomerByPhonenumber(phoneNum);
        if (c != null) {
            this.txt_CustomerName.setText(c.getName());
            this.cbox_TypeCustomer.setSelectedIndex(1);
        } else {
            this.txt_CustomerName.setText("");
        }
        this.customer = c;
    }//GEN-LAST:event_txt_PhonenumberKeyReleased

    private void txt_Phonenumber1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_Phonenumber1KeyReleased
        String phoneNum = this.txt_Phonenumber1.getText();
        Customer c = this.customerList.findCustomerByPhonenumber(phoneNum);
        if (c != null) {
            this.txt_CustomerName1.setText(c.getName());
            this.txt_Address.setText(c.getAddress());

        } else {
            this.txt_CustomerName1.setText("");
        }
        this.customer = c;
    }//GEN-LAST:event_txt_Phonenumber1KeyReleased

    private void cbox_Voucher1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbox_Voucher1ActionPerformed
        if (isCanUseVoucher()) {
            if (this.chBox_Voucher1.isSelected()) {
                this.total += discount;
                eventApplyDiscount();
            }
        }
    }//GEN-LAST:event_cbox_Voucher1ActionPerformed

    private void txt_CGiveKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_CGiveKeyReleased

        if (this.txt_CGive.getText().isEmpty() || CommonFunction.formatStringToMoney(this.txt_CGive.getText()) < 0) {
            this.txt_CGive.setText(CommonFunction.formatMoney(0));
        } else {
            this.txt_CGive.setText(CommonFunction.formatMoney(CommonFunction.formatStringToMoney(this.txt_CGive.getText())));
        }
        eventCustomerInputMoney();

    }//GEN-LAST:event_txt_CGiveKeyReleased

    private void txt_ShipPayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ShipPayKeyReleased
        eventShipPyamentInput(this.txt_ShipPay.getText());
    }//GEN-LAST:event_txt_ShipPayKeyReleased

    private void txt_CGive1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_CGive1KeyReleased
        eventCustomerInputMoney();
    }//GEN-LAST:event_txt_CGive1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JPanel BillPanel;
    private app.swing.Button btn_Back;
    private app.swing.Button btn_Bill1;
    private app.swing.Button btn_Home;
    private app.swing.Button btn_Payment;
    private app.swing.Button btn_Payment1;
    private app.swing.Button btn_PrintBill;
    private app.swing.Button btn_Redresh1;
    private app.swing.Button btn_Refresh;
    private app.swing.Button btn_RefreshSearch;
    private app.swing.Button btn_Remove;
    private app.swing.Button btn_RemoveAll;
    private app.swing.Button btn_Scan;
    private app.swing.Button btn_Search;
    private app.swing.Button btn_ShowDetail;
    private javax.swing.JComboBox<String> cbox_PaymentBy;
    private javax.swing.JComboBox<String> cbox_PaymentBy1;
    private javax.swing.JComboBox<String> cbox_TypeCustomer;
    private javax.swing.JComboBox<String> cbox_Voucher;
    private javax.swing.JComboBox<String> cbox_Voucher1;
    private javax.swing.JCheckBox chBox_Voucher;
    private javax.swing.JCheckBox chBox_Voucher1;
    private javax.swing.JLabel formName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl_excessCash;
    private javax.swing.JLabel lbl_excessCash1;
    private javax.swing.JLabel lbl_totalInvoice;
    private javax.swing.JLabel lbl_totalInvoice1;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel paymentsPanel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JScrollPane scrollPane2;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_Address;
    private javax.swing.JTextField txt_CGive;
    private javax.swing.JTextField txt_CGive1;
    private javax.swing.JTextField txt_CustomerName;
    private javax.swing.JTextField txt_CustomerName1;
    private javax.swing.JTextArea txt_Note;
    private javax.swing.JTextArea txt_Note1;
    private javax.swing.JTextField txt_Phonenumber;
    private javax.swing.JTextField txt_Phonenumber1;
    private javax.swing.JTextField txt_Search;
    private javax.swing.JTextField txt_ShipPay;
    // End of variables declaration//GEN-END:variables
}

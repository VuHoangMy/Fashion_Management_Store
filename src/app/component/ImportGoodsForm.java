package app.component;

import app.BUS.ItemsList;
import app.BUS.StaffList;
import app.BUS.SupplierList;
import app.CommonFuncion.ChangeColorCellTable;
import app.CommonFuncion.CommonFunction;
import app.CommonFuncion.CurrentUserLogin;
import app.DTO.BillImportGoods;
import app.DTO.Items;
import app.DTO.Staff;
import app.DTO.Supplier;
import app.main.MainProgram;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ImportGoodsForm extends javax.swing.JPanel {

    private SupplierList supplierList;
    private ItemsList itemsList;
    private ArrayList<BillImportGoods> billImportGoodsesList;
    private String headerT1[] = {"ID", "Name", "Color", "Material", "Size", "Price", "Inventory Number"};
    private DefaultTableModel defaultTableModelT1 = new DefaultTableModel(headerT1, 0);
    private String headerT2[] = {"ID", "Name", "Color", "Material", "Size", "Amount Import", "Price Import", "Supplier", "Total"};
    private DefaultTableModel defaultTableModelT2 = new DefaultTableModel(headerT2, 0);

    public ImportGoodsForm() {
        initComponents();
        initTable();
    }

    public void initTable() {
        CommonFunction.initScrollbar(ScrollPane1);
        CommonFunction.initScrollbar(ScrollPane2);
        CommonFunction.initScrollbar(ScrollPane3);
        

        // set col name and style of table 2
        table2.setModel(defaultTableModelT2);
        table2.getColumnModel().getColumn(2).setMaxWidth(50);

        this.btn_Add.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Remove.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_RemoveAll.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Save.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
    }

    private void loadDataOfSupplier() {
        this.supplierList = new SupplierList();
        this.supplierList.initFile();
        this.supplierList.readFromFile();
        this.cbox_Supplier.removeAllItems();
        for (Supplier supplier : this.supplierList.getSupplierList()) {
            this.cbox_Supplier.addItem(supplier.getName());
        }
        this.cbox_Supplier.setSelectedIndex(0);
    }

    public void loadDataFromFile() {
        this.billImportGoodsesList = new ArrayList<>();
        this.itemsList = new ItemsList();
        this.itemsList.initFile();
        this.itemsList.readFromFile();
        loadDataTable(table1, defaultTableModelT1, true, this.itemsList);
        loadDataOfSupplier();
    }

    private Vector addToVector(Items items) {
        Vector<Object> v = new Vector<>();
        v.add(items.getID());
        v.add(items.getName());
        v.add("");
        v.add(items.getMaterial());
        v.add(items.getSize());

        v.add(items.getPrice() + " VND");
        v.add(items.getInventoryNumber());

        return v;
    }

    private Vector addToVectorBillTable(BillImportGoods billImportGoods) {
        Vector<Object> v = new Vector<>();
        v.add(billImportGoods.getItems().getID());
        v.add(billImportGoods.getItems().getName());
        v.add("");
        v.add(billImportGoods.getItems().getMaterial());
        v.add(billImportGoods.getItems().getSize());

        v.add(billImportGoods.getAmountImport());
        v.add(CommonFunction.formatMoney(billImportGoods.getPriceImport()));
        v.add(billImportGoods.getSupplier().getName());
        
        double total = billImportGoods.getAmountImport() * billImportGoods.getPriceImport();
        v.add(CommonFunction.formatMoney(total));

        return v;
    }

    public void loadDataTable(JTable table, DefaultTableModel defaultTableModel, boolean isTable1, ItemsList colorList) {
        defaultTableModel.setRowCount(0);

        if (isTable1) {
            for (Items i : colorList.getItemslist()) {
                Vector<Object> v = addToVector(i);
                defaultTableModel.addRow(v);
            }
        } else {
            for (BillImportGoods bill : this.billImportGoodsesList) {
                Vector<Object> v = addToVectorBillTable(bill);
                defaultTableModel.addRow(v);
            }
        }
        table.setModel(defaultTableModel);
        new ChangeColorCellTable().changeTable(table, 2, 0, colorList);
        table.getColumnModel().getColumn(2).setMaxWidth(50);
    }

    private ItemsList getItemsListFromBill(ArrayList<BillImportGoods> billList) {
        ItemsList itemsList = new ItemsList();
        for (BillImportGoods bill : billList) {
            itemsList.addNewItem(bill.getItems());
        }
        return itemsList;
    }

    private boolean validateDataInput() {
        if (!CommonFunction.isNumeric(this.txt_Amount.getText()) || !CommonFunction.isNumeric(this.txt_Amount.getText())) {
            JOptionPane.showMessageDialog(this, "Please input Number in Price and Amount textbox!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void addToTMPTable() {

        if (validateDataInput()) {
            try {
                int row = table1.getSelectedRow();
                int col = 0;
                String ID = this.table1.getValueAt(row, col).toString();

                int amount = Integer.parseInt(this.txt_Amount.getText());
                double importPrice = CommonFunction.formatStringToMoney(this.txt_ImportPrice.getText());
                Supplier supplier = this.supplierList.findSupplierByName(this.cbox_Supplier.getSelectedItem().toString());
                String note = this.txt_Note.getText();
                Items items = this.itemsList.findItemsByID(ID);
                Staff s = CurrentUserLogin.getCurrentStaff().getCurrentuserLogin(); // Current user login

                BillImportGoods bill = new BillImportGoods("", items, supplier, note, amount, importPrice);

//                bill.setDateImportGoods(LocalDateTime.of(LocalDate.of(2017, Month.MAY, 15), LocalTime.now())); // Can Delete
                this.billImportGoodsesList.add(bill);

                loadDataTable(table2, defaultTableModelT2, false, getItemsListFromBill(this.billImportGoodsesList));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Please Select Items", "ERROR", JOptionPane.ERROR_MESSAGE);

            }
        }

    }

    private void removeFromTMPTable() {
        int row = table2.getSelectedRow();
        this.billImportGoodsesList.remove(row);
        loadDataTable(table2, defaultTableModelT2, false, getItemsListFromBill(this.billImportGoodsesList));
    }

    private void removeAllFromTMPTable() {
        defaultTableModelT2.setRowCount(0);
        this.billImportGoodsesList.removeAll(billImportGoodsesList);
        loadDataTable(table2, defaultTableModelT2, false, getItemsListFromBill(billImportGoodsesList));
    }

    private void saveBill() {
        try {
            StaffList list = new StaffList();
            list.initFile();
            list.readStaffFromFile();

            int index = list.getStaffList().indexOf(list.findStaffByID(CurrentUserLogin.getCurrentStaff().getCurrentuserLogin().getID()));
            for (BillImportGoods bill : this.billImportGoodsesList) {
                Items tmpItem = this.itemsList.findItemsByID(bill.getItems().getID());
                this.itemsList.updateItemInventory(tmpItem.getID(), bill.getAmountImport() + tmpItem.getInventoryNumber());// Update Item inventory number
                
                String ID = list.findStaffByID(CurrentUserLogin.getCurrentStaff().getCurrentuserLogin().getID()).createIDBillImportGoods();
                bill.setID(ID);
                list.getStaffList().get(index).addNewBillImportItems(bill);
            }

            this.itemsList.writeToFile();
            list.writeStaffIntoFile(); // save staff into file
            JOptionPane.showMessageDialog(this, "Save Success!", "Save New Bill", JOptionPane.INFORMATION_MESSAGE);
            loadDataTable(table1, defaultTableModelT1, true, this.itemsList);
            removeAllFromTMPTable();
            txt_Amount.setText("");;
            txt_ImportPrice.setText("");
            txt_Note.setText("");
            this.cbox_Supplier.setSelectedIndex(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void Search(String searchText) {
        ItemsList TMP = this.itemsList.findListItemByIDOrName(searchText);
        loadDataTable(table1, defaultTableModelT1, true, TMP);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_Back = new app.swing.Button();
        formName = new javax.swing.JLabel();
        txt_Search = new javax.swing.JTextField();
        ScrollPane2 = new javax.swing.JScrollPane();
        table2 = new javax.swing.JTable();
        txt_Amount = new javax.swing.JTextField();
        txt_ImportPrice = new javax.swing.JTextField();
        btn_Add = new app.swing.Button();
        btn_Remove = new app.swing.Button();
        cbox_Supplier = new javax.swing.JComboBox<>();
        ScrollPane3 = new javax.swing.JScrollPane();
        txt_Note = new javax.swing.JTextArea();
        btn_Save = new app.swing.Button();
        ScrollPane1 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        btn_Search = new app.swing.Button();
        btn_Home = new app.swing.Button();
        buttonClose1 = new app.swing.ButtonClose();
        btn_Refresh = new app.swing.Button();
        btn_RemoveAll = new app.swing.Button();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 32, 80, -1));

        formName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        formName.setForeground(new java.awt.Color(255, 255, 255));
        formName.setText("Import Goods");
        add(formName, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, -1, -1));

        txt_Search.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_Search.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_Search.setForeground(new java.awt.Color(102, 255, 255));
        txt_Search.setText("Search");
        txt_Search.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_Search.setOpaque(false);
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
        add(txt_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 466, 39));

        table2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Type", "Size", "Color", "Material", "The Number Of Import"
            }
        ));
        table2.setRowHeight(25);
        table2.setRowMargin(5);
        ScrollPane2.setViewportView(table2);

        add(ScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 680, 1068, 180));

        txt_Amount.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_Amount.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt_Amount.setForeground(new java.awt.Color(0, 255, 255));
        txt_Amount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_Amount.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_Amount.setOpaque(false);
        add(txt_Amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 570, 217, 35));

        txt_ImportPrice.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_ImportPrice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_ImportPrice.setForeground(new java.awt.Color(255, 255, 0));
        txt_ImportPrice.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_ImportPrice.setOpaque(false);
        txt_ImportPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_ImportPriceKeyReleased(evt);
            }
        });
        add(txt_ImportPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 570, 272, 35));

        btn_Add.setForeground(new java.awt.Color(51, 51, 255));
        btn_Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/AddToCart.png"))); // NOI18N
        btn_Add.setText("ADD Cart");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });
        add(btn_Add, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 560, 140, 50));

        btn_Remove.setForeground(new java.awt.Color(255, 0, 0));
        btn_Remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Delete.png"))); // NOI18N
        btn_Remove.setText("Remove");
        btn_Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RemoveActionPerformed(evt);
            }
        });
        add(btn_Remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 560, 120, 50));

        cbox_Supplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(cbox_Supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(1310, 680, 330, 30));

        ScrollPane3.setAutoscrolls(true);

        txt_Note.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_Note.setColumns(20);
        txt_Note.setRows(5);
        ScrollPane3.setViewportView(txt_Note);

        add(ScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1310, 750, 330, 110));

        btn_Save.setForeground(new java.awt.Color(0, 255, 0));
        btn_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Save.png"))); // NOI18N
        btn_Save.setText("  Save");
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });
        add(btn_Save, new org.netbeans.lib.awtextra.AbsoluteConstraints(1510, 560, 130, 50));

        table1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Type", "Material", "Size", "Color", "Price", "Inventory number"
            }
        ));
        table1.setOpaque(false);
        table1.setRowHeight(25);
        table1.setRowMargin(5);
        ScrollPane1.setViewportView(table1);

        add(ScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 1260, 280));

        btn_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Search.png"))); // NOI18N
        btn_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SearchActionPerformed(evt);
            }
        });
        add(btn_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 130, 70, 45));

        btn_Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/home.png"))); // NOI18N
        btn_Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HomeActionPerformed(evt);
            }
        });
        add(btn_Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 80, -1));
        add(buttonClose1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1760, 20, -1, -1));

        btn_Refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Refresh.png"))); // NOI18N
        btn_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefreshActionPerformed(evt);
            }
        });
        add(btn_Refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 130, 110, 45));

        btn_RemoveAll.setForeground(new java.awt.Color(204, 0, 153));
        btn_RemoveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/DeleteAll.png"))); // NOI18N
        btn_RemoveAll.setText("Remove All");
        btn_RemoveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RemoveAllActionPerformed(evt);
            }
        });
        add(btn_RemoveAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 560, 140, 50));

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 17)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Import Price");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 530, -1, -1));

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 17)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Amount");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 530, -1, -1));

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 17)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/AddToCart.png"))); // NOI18N
        jLabel1.setText("   Cart");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 630, 100, 50));

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 17)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Note");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1310, 720, 50, -1));

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 0, 17)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Supplier");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1310, 650, -1, -1));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor5.jpg"))); // NOI18N
        add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1800, 900));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        MainProgram.getInstanceTransactionForm().getSlideshow().slideTo(0);
    }//GEN-LAST:event_btn_BackActionPerformed

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        addToTMPTable();
        this.txt_Amount.setText("");
        this.txt_ImportPrice.setText("");
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RemoveActionPerformed
        removeFromTMPTable();
    }//GEN-LAST:event_btn_RemoveActionPerformed

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        if(this.billImportGoodsesList.isEmpty()){
            JOptionPane.showMessageDialog(null, "Chua co san pham duoc them vao!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        saveBill();
    }//GEN-LAST:event_btn_SaveActionPerformed

    private void btn_RemoveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RemoveAllActionPerformed
        // add Comfirm dialog
        removeAllFromTMPTable();
    }//GEN-LAST:event_btn_RemoveAllActionPerformed

    private void txt_SearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusGained
        CommonFunction.setPlaceHolder(txt_Search, true, "Search");
    }//GEN-LAST:event_txt_SearchFocusGained

    private void txt_SearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusLost
        CommonFunction.setPlaceHolder(txt_Search, false, "Search");
    }//GEN-LAST:event_txt_SearchFocusLost

    private void btn_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SearchActionPerformed
        String searchText = txt_Search.getText();
        Search(searchText);
    }//GEN-LAST:event_btn_SearchActionPerformed

    private void btn_HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HomeActionPerformed
        CommonFunction.backToTransactionMenu();
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_HomeActionPerformed

    private void txt_SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyReleased
       String searchText = txt_Search.getText();
        Search(searchText);
    }//GEN-LAST:event_txt_SearchKeyReleased

    private void txt_ImportPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ImportPriceKeyReleased
        if (this.txt_ImportPrice.getText().isEmpty() || CommonFunction.formatStringToMoney(this.txt_ImportPrice.getText()) < 0) {
            this.txt_ImportPrice.setText(CommonFunction.formatMoney(0));
            return;
        }
        this.txt_ImportPrice.setText(CommonFunction.formatMoney(CommonFunction.formatStringToMoney(this.txt_ImportPrice.getText())));
    }//GEN-LAST:event_txt_ImportPriceKeyReleased

    private void btn_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefreshActionPerformed
       this.table1.getSelectionModel().clearSelection();
       this.table2.getSelectionModel().clearSelection();
       this.txt_Amount.setText("");
       this.txt_ImportPrice.setText("");
    }//GEN-LAST:event_btn_RefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JScrollPane ScrollPane1;
    private javax.swing.JScrollPane ScrollPane2;
    private javax.swing.JScrollPane ScrollPane3;
    private app.swing.Button btn_Add;
    private app.swing.Button btn_Back;
    private app.swing.Button btn_Home;
    private app.swing.Button btn_Refresh;
    private app.swing.Button btn_Remove;
    private app.swing.Button btn_RemoveAll;
    private app.swing.Button btn_Save;
    private app.swing.Button btn_Search;
    private app.swing.ButtonClose buttonClose1;
    private javax.swing.JComboBox<String> cbox_Supplier;
    private javax.swing.JLabel formName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTable table1;
    private javax.swing.JTable table2;
    private javax.swing.JTextField txt_Amount;
    private javax.swing.JTextField txt_ImportPrice;
    private javax.swing.JTextArea txt_Note;
    private javax.swing.JTextField txt_Search;
    // End of variables declaration//GEN-END:variables
}

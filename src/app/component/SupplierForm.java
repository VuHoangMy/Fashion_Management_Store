package app.component;

import app.BUS.SupplierList;
import app.CommonFuncion.CommonFunction;
import app.DTO.Supplier;
import java.awt.Font;
import java.io.File;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SupplierForm extends javax.swing.JPanel {

    private File file;
    private SupplierList supplierList;
    private String header[] = {"ID", "Name", "Phonenumber", "Address"};
    private DefaultTableModel defaultTableModel = new DefaultTableModel(header, 0);

    public SupplierForm() {
        initComponents();
        setStyleButton();
    }

    public void initTable() {
        loadDataFromFile();
        CommonFunction.initScrollbar(scrollPane); // custome Scrollbar
    }

    private void setStyleButton() {
        this.btn_Add.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Edit.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Clear.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Remove.setFont(new Font("Yu Gothic", Font.PLAIN, 15));

    }

    public void loadDataFromFile() {
        this.supplierList = new SupplierList();
        supplierList.initFile();
        supplierList.readFromFile();
        loadDataTable(supplierList);
        setEnableButton(false);
    }

    private void setEnableButton(boolean b) {
        this.btn_Edit.setEnabled(b);
        this.btn_Remove.setEnabled(b);
        this.btn_Add.setEnabled(!b);
    }

    private Vector addToVector(Supplier vou) {
        Vector<Object> v = new Vector<>();
        v.add(vou.getID());
        v.add(vou.getName());
        v.add(vou.getPhonenumber());
        v.add(vou.getAddress());
        return v;
    }

    public void loadDataTable(SupplierList supplierList) {

        defaultTableModel.setRowCount(0);
        for (Supplier supplier : supplierList.getSupplierList()) {
            Vector<Object> v = addToVector(supplier);
            defaultTableModel.addRow(v);
        }
        table.setModel(defaultTableModel);

        setEnableButton(false);
    }

    private boolean isValidateDataInput() {
        if (this.txt_Name.getText().isEmpty() || this.txt_Phonenumber.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui Long nhap du Thong tin!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // CRUD
    private Supplier createNewSupplier() {
        return new Supplier(this.supplierList.createItemsID(), this.txt_Name.getText(), this.txt_Phonenumber.getText(), this.txt_address.getText());
    }

    //Search
    private void eventSearchSupplier(String searchText) {
        SupplierList TMP = new SupplierList();
        if (CommonFunction.isNumeric(searchText)) {
            for (Supplier supplier : this.supplierList.getSupplierList()) {
                if (supplier.getID().contains(searchText)) {
                    TMP.addNewSupplier(supplier);
                }
            }
        } else {
            for (Supplier supplier : this.supplierList.getSupplierList()) {
                if (supplier.getName().toLowerCase().contains(searchText.toLowerCase())) {
                    TMP.addNewSupplier(supplier);
                }
            }
        }
        loadDataTable(TMP);
    }

    private void loadDataToComponent(String name, String phonenumber, String address) {
        this.txt_Name.setText(name);;
        this.txt_Phonenumber.setText(phonenumber);
        this.txt_address.setText(address);
    }

    private void eventLoadDataToComponent() {
        int column = 0;
        int row = table.getSelectedRow();
        String ID = table.getModel().getValueAt(row, column).toString();
        Supplier result = this.supplierList.findSupplierByID(ID);
        loadDataToComponent(result.getName(), result.getPhonenumber(), result.getAddress());
    }

    private void eventSaveNewSupplier() {
        if (!isValidateDataInput()) {
            return;
        }
        Supplier s = createNewSupplier();

        // s = new Supplier(this.supplierList.createItemsID(), "LV", "090851616", "Dong da, haNoi");
        this.supplierList.addNewSupplier(s);
        this.supplierList.appendASupplierIntoFile(s);
        this.loadDataTable(supplierList);
        JOptionPane.showMessageDialog(this, "Them du lieu thanh cong", "Thong Bao", JOptionPane.INFORMATION_MESSAGE);

    }

    private void eventRemoveSupplier() {
        int column = 0;
        int row = table.getSelectedRow();
        String ID = table.getModel().getValueAt(row, column).toString();
        boolean respone = CommonFunction.isConfirmDialog("Delete Supplier", "Xac nhan XOA NCC ID: " + ID + "?.");

        if (!respone) {
            return;
        }
        this.supplierList.getSupplierList().remove(this.supplierList.findSupplierByID(ID));
        this.supplierList.writeToFile();

        loadDataTable(supplierList);
    }

    private void eventUpdateSupplier() {
        int row = this.table.getSelectedRow();
        String ID = this.table.getValueAt(row, 0).toString();

        boolean respone = CommonFunction.isConfirmDialog("Cap nhap", "Xac nhan thay doi du lieu.");
        if (!respone) {
            return;
        }

        if (!isValidateDataInput()) {
            return;
        }

        Supplier editSupplier = this.supplierList.findSupplierByID(ID);
        Supplier newSupplier = createNewSupplier();
        newSupplier.setID(ID);
        this.supplierList.getSupplierList().remove(editSupplier);
        this.supplierList.addNewSupplier(newSupplier);
        this.supplierList.writeToFile();
        loadDataTable(supplierList);
    }

    private void eventClearAll() {
        try {
            loadDataToComponent("", "", "");
            table.getSelectionModel().clearSelection();
            setEnableButton(false);
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        txt_Search = new javax.swing.JTextField();
        btn_Search = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_Name = new javax.swing.JTextField();
        btn_Remove = new app.swing.Button();
        btn_Clear = new app.swing.Button();
        btn_Edit = new app.swing.Button();
        btn_Refresh = new app.swing.Button();
        jLabel4 = new javax.swing.JLabel();
        txt_Phonenumber = new javax.swing.JTextField();
        btn_Add = new app.swing.Button();
        btn_Back = new app.swing.Button();
        jLabel6 = new javax.swing.JLabel();
        txt_address = new javax.swing.JTextField();
        Background_Edit = new javax.swing.JLabel();
        BackGround = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Supplier");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, -1));

        table.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Phonenumber", "Address"
            }
        ));
        table.setOpaque(false);
        table.setRowHeight(25);
        table.setRowMargin(3);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(table);

        add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 1090, 540));

        txt_Search.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Search.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txt_Search.setForeground(new java.awt.Color(204, 204, 204));
        txt_Search.setText("Search");
        txt_Search.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 153, 255)));
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_SearchKeyTyped(evt);
            }
        });
        add(txt_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 510, 40));

        btn_Search.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Search.png"))); // NOI18N
        btn_Search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_SearchMouseClicked(evt);
            }
        });
        add(btn_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, -1, 60));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Supplier name");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 310, 120, 30));

        txt_Name.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_Name.setFont(new java.awt.Font("Segoe UI Historic", 0, 17)); // NOI18N
        txt_Name.setForeground(new java.awt.Color(0, 255, 204));
        txt_Name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 204)));
        txt_Name.setOpaque(false);
        add(txt_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(1390, 310, 260, 30));

        btn_Remove.setForeground(new java.awt.Color(204, 0, 51));
        btn_Remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Delete.png"))); // NOI18N
        btn_Remove.setText("Remove");
        btn_Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RemoveActionPerformed(evt);
            }
        });
        add(btn_Remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(1470, 610, -1, -1));

        btn_Clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Clear.png"))); // NOI18N
        btn_Clear.setText("Clear All");
        btn_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ClearActionPerformed(evt);
            }
        });
        add(btn_Clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(1600, 610, 120, -1));

        btn_Edit.setForeground(new java.awt.Color(255, 255, 0));
        btn_Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Edit.png"))); // NOI18N
        btn_Edit.setText("Edit");
        btn_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditActionPerformed(evt);
            }
        });
        add(btn_Edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 610, 100, -1));

        btn_Refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Refresh.png"))); // NOI18N
        btn_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefreshActionPerformed(evt);
            }
        });
        add(btn_Refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 130, 110, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Phonenumber");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 420, 100, 30));

        txt_Phonenumber.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_Phonenumber.setFont(new java.awt.Font("Segoe UI Historic", 0, 17)); // NOI18N
        txt_Phonenumber.setForeground(new java.awt.Color(0, 255, 204));
        txt_Phonenumber.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 204)));
        txt_Phonenumber.setOpaque(false);
        add(txt_Phonenumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(1390, 420, 260, 30));

        btn_Add.setForeground(new java.awt.Color(0, 255, 0));
        btn_Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Add.png"))); // NOI18N
        btn_Add.setText("Add");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });
        add(btn_Add, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 610, 110, -1));

        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 50, 40));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Address");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 510, 100, 30));

        txt_address.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_address.setFont(new java.awt.Font("Segoe UI Historic", 0, 17)); // NOI18N
        txt_address.setForeground(new java.awt.Color(0, 255, 204));
        txt_address.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 204)));
        txt_address.setOpaque(false);
        add(txt_address, new org.netbeans.lib.awtextra.AbsoluteConstraints(1390, 510, 260, 30));

        Background_Edit.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "New Supplier", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI", 1, 15), new java.awt.Color(255, 255, 255))); // NOI18N
        add(Background_Edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 260, 550, 450));

        BackGround.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor5.jpg"))); // NOI18N
        add(BackGround, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        eventSaveNewSupplier();
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RemoveActionPerformed
        eventRemoveSupplier();
    }//GEN-LAST:event_btn_RemoveActionPerformed

    private void btn_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ClearActionPerformed
        eventClearAll();
    }//GEN-LAST:event_btn_ClearActionPerformed

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_BackActionPerformed

    private void txt_SearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusGained
        CommonFunction.setPlaceHolder(txt_Search, true, "Search");
    }//GEN-LAST:event_txt_SearchFocusGained

    private void txt_SearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusLost
        CommonFunction.setPlaceHolder(txt_Search, false, "Search");
    }//GEN-LAST:event_txt_SearchFocusLost

    private void txt_SearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyTyped
        String searchText = this.txt_Search.getText();
        eventSearchSupplier(searchText);
    }//GEN-LAST:event_txt_SearchKeyTyped

    private void btn_SearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SearchMouseClicked
        String searchText = this.txt_Search.getText();
        if (!searchText.equals("Search")) {
            eventSearchSupplier(searchText);
        }

    }//GEN-LAST:event_btn_SearchMouseClicked

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        eventLoadDataToComponent();
        setEnableButton(true);
    }//GEN-LAST:event_tableMouseClicked

    private void btn_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditActionPerformed
        eventUpdateSupplier();
    }//GEN-LAST:event_btn_EditActionPerformed

    private void btn_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefreshActionPerformed
        loadDataFromFile();
    }//GEN-LAST:event_btn_RefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackGround;
    private javax.swing.JLabel Background_Edit;
    private app.swing.Button btn_Add;
    private app.swing.Button btn_Back;
    private app.swing.Button btn_Clear;
    private app.swing.Button btn_Edit;
    private app.swing.Button btn_Refresh;
    private app.swing.Button btn_Remove;
    private javax.swing.JLabel btn_Search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_Name;
    private javax.swing.JTextField txt_Phonenumber;
    private javax.swing.JTextField txt_Search;
    private javax.swing.JTextField txt_address;
    // End of variables declaration//GEN-END:variables
}

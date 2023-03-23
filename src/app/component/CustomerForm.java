package app.component;

import app.BUS.CustomerList;
import app.CommonFuncion.CommonFunction;
import app.DTO.Customer;
import java.awt.Font;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CustomerForm extends javax.swing.JPanel {

    private CustomerList customerList;
    private String header[] = {"ID", "Name", "Phonenumber", "Gender", "Address"};
    private DefaultTableModel defaultTableModel = new DefaultTableModel(header, 0);

    public CustomerForm() {
        initComponents();
        setStyleButton();
    }

    public void initTable() {
        CommonFunction.initScrollbar(scrollPane);
        loadDataFromFile();
    }

    private void setStyleButton() {
        this.btn_Add.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Edit.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_ClearAll.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Remove.setFont(new Font("Yu Gothic", Font.PLAIN, 15));

    }

    private Vector addToVector(Customer customer) {
        Vector<Object> v = new Vector<>();
        v.add(customer.getID());
        v.add(customer.getName());
        v.add(customer.getPhonenumber());
        if (customer.getIsMale()) {
            v.add("Male");
        } else {
            v.add("Female");
        }
        v.add(customer.getAddress());
        return v;
    }

    public void loadDataFromFile() {
        this.customerList = new CustomerList();
        customerList.initFile();
        customerList.readFromFile();
        loadDataTable(customerList);
    }

    public void loadDataTable(CustomerList customerList) {
        defaultTableModel.setRowCount(0);
        for (Customer customer : customerList.getCustomerList()) {
            Vector<Object> v = addToVector(customer);
            defaultTableModel.addRow(v);
        }
        table.setModel(defaultTableModel);
        setEnableButton(false);
    }

    private Customer createNewCustomer() {
        String name = this.txt_Name.getText();
        String phonenum = this.txt_Phonenumber.getText();
        String address = this.txt_Address.getText().isEmpty() ? "" : this.txt_Address.getText();
        boolean gender = this.btnRadio_Male.isSelected() ? true : false;
        return new Customer(this.customerList.createCustomerID(), name, phonenum, address, gender);
    }

    private boolean validateDataInput() {
        String name = this.txt_Name.getText();
        String phonenum = this.txt_Phonenumber.getText();
        if (name.isEmpty() || phonenum.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Voi long nhap Ten va SDT.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void setEnableButton(boolean isEnable) {
        this.btn_Remove.setEnabled(isEnable);
        this.btn_Edit.setEnabled(isEnable);
        this.btn_Add.setEnabled(!isEnable);
    }

    private void setContainOfComponent(String name, String address, String phonenumber, boolean isMale) {
        this.txt_Address.setText(address);
        this.txt_Name.setText(name);
        this.txt_Phonenumber.setText(phonenumber);
        if (isMale) {
            this.btnRadio_Male.setSelected(isMale);
        } else {
            this.btnRadio_Female.setSelected(!isMale);
        }
    }

    private void eventSearchCustomer(String txtSearch) {
        CustomerList list = this.customerList.findListCustomerByNameOrID(txtSearch);
        loadDataTable(list);
    }

    private void eventFillDataToComponent() {
        int row = table.getSelectedRow();
        String ID = this.table.getValueAt(row, 0).toString();
        Customer c = this.customerList.findCustomerByID(ID);
        setContainOfComponent(c.getName(), c.getAddress(), c.getPhonenumber(), c.getIsMale());
    }

    private void eventAddNewCustomer() {
        if (validateDataInput()) {
            Customer c1 = createNewCustomer();
//            Customer c1 = new Customer(this.customerList.createCustomerID(), "Nguyen van A", "0912376322", "Dalat", true);
            this.customerList.addNewCustomer(c1);
            this.customerList.appendACustomerIntoFile(c1);
            loadDataTable(customerList);
            JOptionPane.showMessageDialog(null, "Them khach hang thanh cong!", "Thong Bao", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void eventClearAll() {
        setContainOfComponent("", "", "", true);
        setEnableButton(false);
        table.clearSelection();
    }

    private void eventRemoveCustomer() {
        int column = 0;
        int row = table.getSelectedRow();
        String ID = table.getModel().getValueAt(row, column).toString();
        int respone = JOptionPane.showConfirmDialog(this, "Xac nhan XOA khach hang ID: " + ID + " ?.", "Xoa Khach Hang",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respone == JOptionPane.YES_OPTION) {
            this.customerList.getCustomerList().remove(this.customerList.findCustomerByID(ID));
            this.customerList.writeToFile();
            this.loadDataTable(customerList);
        }
    }

    private void eventUpdateCustomer() {
        int row = this.table.getSelectedRow();
        String ID = this.table.getValueAt(row, 0).toString();
        int respone = JOptionPane.showConfirmDialog(this, "Xac nhan thay doi du lieu khach hang ID: " + ID + " ?.", "Cap nhap du lieu",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respone == JOptionPane.CLOSED_OPTION || respone == JOptionPane.NO_OPTION) {
            return;
        }
        Customer editC = this.customerList.findCustomerByID(ID);
        Customer newCustomer = createNewCustomer();
        newCustomer.setID(ID);
        this.customerList.getCustomerList().remove(editC);
        this.customerList.addNewCustomer(newCustomer);
        this.customerList.writeToFile();

        loadDataTable(customerList);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_GroupGender = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        txt_Search = new javax.swing.JTextField();
        btn_Search = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_Name = new javax.swing.JTextField();
        btnRadio_Male = new javax.swing.JRadioButton();
        btn_BackToMenu = new app.swing.Button();
        btnRadio_Female = new javax.swing.JRadioButton();
        btn_Remove = new app.swing.Button();
        jLabel7 = new javax.swing.JLabel();
        btn_ClearAll = new app.swing.Button();
        btn_Edit = new app.swing.Button();
        btn_Printer = new app.swing.Button();
        jLabel4 = new javax.swing.JLabel();
        txt_Phonenumber = new javax.swing.JTextField();
        txt_Address = new javax.swing.JTextField();
        btn_Add = new app.swing.Button();
        jLabel5 = new javax.swing.JLabel();
        BackgroundCreateNewProduct = new javax.swing.JLabel();
        BackGround = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Customer");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        table.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Phonenumber", "Gender", "Address"
            }
        ));
        table.setOpaque(false);
        table.setRowHeight(25);
        table.setRowMargin(5);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(table);

        add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 1090, 590));

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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_SearchKeyReleased(evt);
            }
        });
        add(txt_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 510, 40));

        btn_Search.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Search.png"))); // NOI18N
        add(btn_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, -1, 60));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Customer Name");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 350, 120, 30));

        txt_Name.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_Name.setFont(new java.awt.Font("Segoe UI Historic", 0, 17)); // NOI18N
        txt_Name.setForeground(new java.awt.Color(0, 255, 204));
        txt_Name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 204)));
        txt_Name.setOpaque(false);
        add(txt_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 350, 260, 30));

        btn_GroupGender.add(btnRadio_Male);
        btnRadio_Male.setFont(new java.awt.Font("Segoe UI Light", 1, 16)); // NOI18N
        btnRadio_Male.setForeground(new java.awt.Color(0, 255, 204));
        btnRadio_Male.setSelected(true);
        btnRadio_Male.setText("Male");
        add(btnRadio_Male, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 650, -1, -1));

        btn_BackToMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_BackToMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackToMenuActionPerformed(evt);
            }
        });
        add(btn_BackToMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 50, 50));

        btn_GroupGender.add(btnRadio_Female);
        btnRadio_Female.setFont(new java.awt.Font("Segoe UI Light", 1, 16)); // NOI18N
        btnRadio_Female.setForeground(new java.awt.Color(0, 255, 204));
        btnRadio_Female.setText("Female");
        add(btnRadio_Female, new org.netbeans.lib.awtextra.AbsoluteConstraints(1450, 650, -1, -1));

        btn_Remove.setForeground(new java.awt.Color(204, 0, 0));
        btn_Remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Delete.png"))); // NOI18N
        btn_Remove.setText("Remove");
        btn_Remove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_RemoveMouseClicked(evt);
            }
        });
        add(btn_Remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(1450, 730, 120, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Gender");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 650, -1, -1));

        btn_ClearAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Clear.png"))); // NOI18N
        btn_ClearAll.setText("Clear ALl");
        btn_ClearAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ClearAllMouseClicked(evt);
            }
        });
        add(btn_ClearAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(1590, 730, 140, -1));

        btn_Edit.setForeground(new java.awt.Color(255, 255, 0));
        btn_Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Edit.png"))); // NOI18N
        btn_Edit.setText("Edit");
        btn_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditActionPerformed(evt);
            }
        });
        add(btn_Edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1340, 730, 100, -1));

        btn_Printer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Printer.png"))); // NOI18N
        btn_Printer.setText("Printer");
        add(btn_Printer, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 140, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Phonenumber");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 550, 100, 30));

        txt_Phonenumber.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_Phonenumber.setFont(new java.awt.Font("Segoe UI Historic", 0, 17)); // NOI18N
        txt_Phonenumber.setForeground(new java.awt.Color(0, 255, 204));
        txt_Phonenumber.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 204)));
        txt_Phonenumber.setOpaque(false);
        add(txt_Phonenumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 550, 260, 30));

        txt_Address.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_Address.setFont(new java.awt.Font("Segoe UI Historic", 0, 17)); // NOI18N
        txt_Address.setForeground(new java.awt.Color(0, 255, 204));
        txt_Address.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 204)));
        txt_Address.setOpaque(false);
        add(txt_Address, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 450, 260, 30));

        btn_Add.setForeground(new java.awt.Color(51, 255, 0));
        btn_Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Add.png"))); // NOI18N
        btn_Add.setText("Add");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });
        add(btn_Add, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 730, 110, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Address");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 450, 60, 30));

        BackgroundCreateNewProduct.setToolTipText("");
        BackgroundCreateNewProduct.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "New Customer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 15), new java.awt.Color(255, 255, 255))); // NOI18N
        add(BackgroundCreateNewProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 250, 560, 600));

        BackGround.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor5.jpg"))); // NOI18N
        add(BackGround, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ClearAllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ClearAllMouseClicked
        eventClearAll();
    }//GEN-LAST:event_btn_ClearAllMouseClicked

    private void btn_BackToMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackToMenuActionPerformed
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_BackToMenuActionPerformed

    private void txt_SearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusGained
        CommonFunction.setPlaceHolder(this.txt_Search, true, "Search");
    }//GEN-LAST:event_txt_SearchFocusGained

    private void txt_SearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusLost
        CommonFunction.setPlaceHolder(this.txt_Search, false, "Search");
    }//GEN-LAST:event_txt_SearchFocusLost

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        eventFillDataToComponent();
        setEnableButton(true);
    }//GEN-LAST:event_tableMouseClicked

    private void btn_RemoveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_RemoveMouseClicked
        eventRemoveCustomer();
    }//GEN-LAST:event_btn_RemoveMouseClicked

    private void btn_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditActionPerformed
        eventUpdateCustomer();
    }//GEN-LAST:event_btn_EditActionPerformed

    private void txt_SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyReleased
        eventSearchCustomer(this.txt_Search.getText());
    }//GEN-LAST:event_txt_SearchKeyReleased

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        eventAddNewCustomer();
    }//GEN-LAST:event_btn_AddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackGround;
    private javax.swing.JLabel BackgroundCreateNewProduct;
    private javax.swing.JRadioButton btnRadio_Female;
    private javax.swing.JRadioButton btnRadio_Male;
    private app.swing.Button btn_Add;
    private app.swing.Button btn_BackToMenu;
    private app.swing.Button btn_ClearAll;
    private app.swing.Button btn_Edit;
    private javax.swing.ButtonGroup btn_GroupGender;
    private app.swing.Button btn_Printer;
    private app.swing.Button btn_Remove;
    private javax.swing.JLabel btn_Search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_Address;
    private javax.swing.JTextField txt_Name;
    private javax.swing.JTextField txt_Phonenumber;
    private javax.swing.JTextField txt_Search;
    // End of variables declaration//GEN-END:variables
}

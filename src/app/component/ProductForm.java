package app.component;

import app.BUS.ProductsList;
import app.CommonFuncion.CommonFunction;
import app.DTO.Products;
import java.awt.Font;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProductForm extends javax.swing.JPanel {

    private ProductsList productsList;
    private String header[] = {"ID", "Name", "Type", "Desciption", "Status"};
    private DefaultTableModel defaultTableModel = new DefaultTableModel(header, 0);

    public ProductForm() {
        initComponents();
        setStyleButton();
        CommonFunction.initScrollbar(scrollPane);
    }

    public void loadDataFromFile() {
        this.productsList = new ProductsList();
        this.productsList.initFile();
        this.productsList.readFromFile();
        loadDataTable(productsList);
    }

    public void loadDataTable(ProductsList productsList) {
        try {
            defaultTableModel.setRowCount(0);
            for (Products p : productsList.getProductsList()) {
                Vector<Object> v = addToVector(p);
                defaultTableModel.addRow(v);
            }
            table.setModel(defaultTableModel);
            setEnableButton(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Vector addToVector(Products products) {
        Vector<Object> v = new Vector<>();
        v.add(products.getID());
        v.add(products.getName());
        v.add(products.getType());
        v.add(products.getDescribe());
        if (products.getStatus()) {
            v.add("Countinue");
        } else {
            v.add("Stop");
        }
        return v;
    }

    private void eventFillData() {
        String ID = this.table.getValueAt(table.getSelectedRow(), 0).toString();
        Products p = this.productsList.findItemsByID(ID);
        loadDataToComponent(p.getDescribe(), p.getName(), p.getType(), p.getStatus());
    }

    private void eventRemoveProduct() {

        int column = 0;
        int row = table.getSelectedRow();
        String ID = table.getModel().getValueAt(row, column).toString();
        boolean respone = CommonFunction.isConfirmDialog("Xoa SP", "Xac nhan XOA san pham " + ID + "?.");

        if (!respone) {
            return;
        }

        this.productsList.getProductsList().remove(this.productsList.findItemsByID(ID));
        this.productsList.writeToFile();
        loadDataTable(productsList);

    }

    private void eventEditProduct() {
        boolean respone = CommonFunction.isConfirmDialog("Confirm", "Dong y thay doi su lieu?");

        if (!respone) {
            return;
        }

        String ID = this.table.getValueAt(table.getSelectedRow(), 0).toString();
        Products p = this.productsList.findItemsByID(ID);
        this.productsList.getProductsList().remove(p);
        Products pEdit = createNewProduct();
        pEdit.setID(ID);
        this.productsList.addNewItem(pEdit);
        this.productsList.writeToFile();

        loadDataTable(productsList);
    }
    
    private void setStyleButton(){
        this.btn_Remove.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Refesh.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Add.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Edit.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_ClearAll.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
    }

    private Products createNewProduct() {
        String des = this.txt_Desciption.getText();
        String name = this.txt_NameProduct.getText();
        String type = this.cbox_Type.getSelectedItem().toString();
        boolean status = this.rbtn_Countinue.isSelected() ? true : false;

        return new Products(this.productsList.createItemsID(), name, type, des, status);
    }

    private void loadDataToComponent(String description, String name, String type, boolean status) {
        this.txt_Desciption.setText(description);
        this.txt_NameProduct.setText(name);
        this.cbox_Type.setSelectedItem(type);
        this.rbtn_Countinue.setSelected(status);
        this.rbtn_Stop.setSelected(!status);
    }

    private void eventClearAll() {
        loadDataToComponent("", "", "", true);
        table.getSelectionModel().clearSelection();
        setEnableButton(false);
    }

    private boolean isValidateDataInput() {
        if (this.txt_NameProduct.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui long nhap ten san pham!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void eventSearchProduct() {
        String txtSearch = this.txt_Search.getText();
        ProductsList tmp = new ProductsList();
        if (CommonFunction.isNumeric(txtSearch)) {
            for (Products p : this.productsList.getProductsList()) {
                if (p.getID().contains(txtSearch)) {
                    tmp.addNewItem(p);
                }
            }
        } else {
            for (Products p : this.productsList.getProductsList()) {
                if (p.getName().toLowerCase().contains(txtSearch.toLowerCase())) {
                    tmp.addNewItem(p);
                }
            }
        }
        loadDataTable(tmp);
    }

    private void eventSaveNewProduct() {
        if (!isValidateDataInput()) {
            return;
        }
        boolean respone = CommonFunction.isConfirmDialog("INFO", "Xac nhan them san pham!");

        if (!respone) {
            return;
        }

        Products i1 = createNewProduct();
//        Products i1 = new Products(this.productsList.createItemsID(), "Giay Convert", "Giay", "Giay la giay aasd", true);
        this.productsList.addNewItem(i1);
        this.productsList.appendAProductsIntoFile(i1);
        loadDataTable(productsList);

    }

    private void setEnableButton(boolean isEnable) {
        this.btn_Edit.setEnabled(isEnable);
        this.btn_Remove.setEnabled(isEnable);
        this.btn_Add.setEnabled(!isEnable);
    }

    private void eventRefresh() {
        loadDataFromFile();
        loadDataToComponent("", "", "", true);
        this.Rbtn_All.setSelected(true);
        this.cbox_Type_Search.setSelectedIndex(0);
        this.cbox_Type.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroup_BusinessStatus = new javax.swing.ButtonGroup();
        btnGroup_FindProduct = new javax.swing.ButtonGroup();
        Title = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        txt_Search = new javax.swing.JTextField();
        btn_Search = new javax.swing.JLabel();
        txt_Desciption = new javax.swing.JTextArea();
        cbox_Type = new javax.swing.JComboBox<>();
        txt_NameProduct = new javax.swing.JTextField();
        rbtn_Countinue = new javax.swing.JRadioButton();
        btn_Back = new app.swing.Button();
        rbtn_Stop = new javax.swing.JRadioButton();
        Rbtn_All = new javax.swing.JRadioButton();
        btn_ClearAll = new app.swing.Button();
        btn_Remove = new app.swing.Button();
        Rbtn_Continue = new javax.swing.JRadioButton();
        Rbtn_Stop = new javax.swing.JRadioButton();
        btn_AddType = new app.swing.Button();
        btn_Add = new app.swing.Button();
        btn_Edit = new app.swing.Button();
        btn_Type_Search = new app.swing.Button();
        btn_Refesh = new app.swing.Button();
        cbox_Type_Search = new javax.swing.JComboBox<>();
        BacgroundFindProduct = new javax.swing.JLabel();
        BackgroundCreateNewProduct = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Title.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        Title.setForeground(new java.awt.Color(255, 255, 255));
        Title.setText("Products");
        add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Type");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1510, 550, 110, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Product's name");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 470, 110, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Decription");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 630, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Business Status");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 310, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Business Status");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 550, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Type");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1540, 300, 110, 30));

        scrollPane.setBorder(null);

        table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Name", "Type", "Description", "Status"
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

        add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 1180, 610));

        txt_Search.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Search.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txt_Search.setForeground(new java.awt.Color(153, 255, 255));
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_SearchKeyTyped(evt);
            }
        });
        add(txt_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 510, 40));

        btn_Search.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Search.png"))); // NOI18N
        btn_Search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_SearchMouseClicked(evt);
            }
        });
        add(btn_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, -1, 60));

        txt_Desciption.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Desciption.setColumns(20);
        txt_Desciption.setFont(new java.awt.Font("Monospaced", 0, 17)); // NOI18N
        txt_Desciption.setForeground(new java.awt.Color(0, 255, 204));
        txt_Desciption.setRows(5);
        txt_Desciption.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));
        txt_Desciption.setOpaque(false);
        add(txt_Desciption, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 670, 440, 90));

        cbox_Type.setEditable(true);
        cbox_Type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jacket", "Jeans", "T-Shirt", "Trousers", "Dress", "Shorts", "Jumper", "Shoes" }));
        cbox_Type.setOpaque(false);
        add(cbox_Type, new org.netbeans.lib.awtextra.AbsoluteConstraints(1510, 580, 170, 30));

        txt_NameProduct.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_NameProduct.setFont(new java.awt.Font("Yu Gothic", 0, 17)); // NOI18N
        txt_NameProduct.setForeground(new java.awt.Color(0, 255, 204));
        txt_NameProduct.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_NameProduct.setOpaque(false);
        add(txt_NameProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 500, 440, 30));

        btnGroup_BusinessStatus.add(rbtn_Countinue);
        rbtn_Countinue.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        rbtn_Countinue.setForeground(new java.awt.Color(0, 255, 204));
        rbtn_Countinue.setSelected(true);
        rbtn_Countinue.setText("Countinue");
        add(rbtn_Countinue, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 580, -1, -1));

        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        btnGroup_BusinessStatus.add(rbtn_Stop);
        rbtn_Stop.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        rbtn_Stop.setForeground(new java.awt.Color(0, 255, 204));
        rbtn_Stop.setText("Stop");
        add(rbtn_Stop, new org.netbeans.lib.awtextra.AbsoluteConstraints(1380, 580, -1, -1));

        btnGroup_FindProduct.add(Rbtn_All);
        Rbtn_All.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        Rbtn_All.setForeground(new java.awt.Color(0, 255, 204));
        Rbtn_All.setSelected(true);
        Rbtn_All.setText("All");
        Rbtn_All.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rbtn_AllActionPerformed(evt);
            }
        });
        add(Rbtn_All, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 340, -1, 30));

        btn_ClearAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Clear.png"))); // NOI18N
        btn_ClearAll.setText("Clear All");
        btn_ClearAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ClearAllMouseClicked(evt);
            }
        });
        add(btn_ClearAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(1600, 780, 130, -1));

        btn_Remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Delete.png"))); // NOI18N
        btn_Remove.setText("Remove");
        btn_Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RemoveActionPerformed(evt);
            }
        });
        add(btn_Remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 130, -1, -1));

        btnGroup_FindProduct.add(Rbtn_Continue);
        Rbtn_Continue.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        Rbtn_Continue.setForeground(new java.awt.Color(0, 255, 204));
        Rbtn_Continue.setText("Countinue");
        Rbtn_Continue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rbtn_ContinueActionPerformed(evt);
            }
        });
        add(Rbtn_Continue, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 340, -1, 30));

        btnGroup_FindProduct.add(Rbtn_Stop);
        Rbtn_Stop.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        Rbtn_Stop.setForeground(new java.awt.Color(0, 255, 204));
        Rbtn_Stop.setText("Stop");
        Rbtn_Stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rbtn_StopActionPerformed(evt);
            }
        });
        add(Rbtn_Stop, new org.netbeans.lib.awtextra.AbsoluteConstraints(1430, 340, -1, 30));

        btn_AddType.setForeground(new java.awt.Color(0, 255, 102));
        btn_AddType.setText("+");
        add(btn_AddType, new org.netbeans.lib.awtextra.AbsoluteConstraints(1690, 580, 40, 30));

        btn_Add.setForeground(new java.awt.Color(0, 255, 0));
        btn_Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Add.png"))); // NOI18N
        btn_Add.setText(" Add");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });
        add(btn_Add, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 780, 150, 50));

        btn_Edit.setForeground(new java.awt.Color(255, 255, 153));
        btn_Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Edit.png"))); // NOI18N
        btn_Edit.setText("Edit");
        btn_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditActionPerformed(evt);
            }
        });
        add(btn_Edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1460, 780, 110, 50));

        btn_Type_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/SearchHover.png"))); // NOI18N
        btn_Type_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Type_SearchActionPerformed(evt);
            }
        });
        add(btn_Type_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(1700, 330, 40, 40));

        btn_Refesh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Refresh.png"))); // NOI18N
        btn_Refesh.setText("Refresh");
        btn_Refesh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefeshActionPerformed(evt);
            }
        });
        add(btn_Refesh, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 130, -1, -1));

        cbox_Type_Search.setEditable(true);
        cbox_Type_Search.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jacket", "Jeans", "T-Shirt", "Trousers", "Dress", "Shorts", "Jumper", "Shoes" }));
        cbox_Type_Search.setOpaque(false);
        add(cbox_Type_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(1520, 340, 170, 30));

        BacgroundFindProduct.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Find Product", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        add(BacgroundFindProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 240, 520, 180));

        BackgroundCreateNewProduct.setToolTipText("");
        BackgroundCreateNewProduct.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "New Product", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        add(BackgroundCreateNewProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 430, 520, 430));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor5.jpg"))); // NOI18N
        add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1800, 900));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_BackActionPerformed

    private void btn_ClearAllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ClearAllMouseClicked
        eventClearAll();
    }//GEN-LAST:event_btn_ClearAllMouseClicked

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        eventSaveNewProduct();
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RemoveActionPerformed
        eventRemoveProduct();
    }//GEN-LAST:event_btn_RemoveActionPerformed

    private void txt_SearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusGained
        CommonFunction.setPlaceHolder(txt_Search, true, "Search");
    }//GEN-LAST:event_txt_SearchFocusGained

    private void txt_SearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusLost
        CommonFunction.setPlaceHolder(txt_Search, false, "Search");
    }//GEN-LAST:event_txt_SearchFocusLost

    private void btn_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditActionPerformed
        eventEditProduct();
        eventClearAll();
    }//GEN-LAST:event_btn_EditActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        setEnableButton(true);
        eventFillData();
    }//GEN-LAST:event_tableMouseClicked

    private void btn_SearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SearchMouseClicked
        eventSearchProduct();
    }//GEN-LAST:event_btn_SearchMouseClicked

    private void txt_SearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyTyped
        eventSearchProduct();
    }//GEN-LAST:event_txt_SearchKeyTyped

    private void Rbtn_ContinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rbtn_ContinueActionPerformed
        ProductsList result = this.productsList.FillterProductByStutus(true);
        loadDataTable(result);
    }//GEN-LAST:event_Rbtn_ContinueActionPerformed

    private void Rbtn_AllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rbtn_AllActionPerformed
        loadDataTable(this.productsList);
    }//GEN-LAST:event_Rbtn_AllActionPerformed

    private void Rbtn_StopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rbtn_StopActionPerformed
        ProductsList result = productsList.FillterProductByStutus(false);
        loadDataTable(result);
    }//GEN-LAST:event_Rbtn_StopActionPerformed

    private void btn_Type_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Type_SearchActionPerformed
        ProductsList result = this.productsList.FillterProductByType(this.cbox_Type_Search.getSelectedItem().toString());
        loadDataTable(result);
    }//GEN-LAST:event_btn_Type_SearchActionPerformed

    private void btn_RefeshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefeshActionPerformed
        eventRefresh();
    }//GEN-LAST:event_btn_RefeshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BacgroundFindProduct;
    private javax.swing.JLabel Background;
    private javax.swing.JLabel BackgroundCreateNewProduct;
    private javax.swing.JRadioButton Rbtn_All;
    private javax.swing.JRadioButton Rbtn_Continue;
    private javax.swing.JRadioButton Rbtn_Stop;
    private javax.swing.JLabel Title;
    private javax.swing.ButtonGroup btnGroup_BusinessStatus;
    private javax.swing.ButtonGroup btnGroup_FindProduct;
    private app.swing.Button btn_Add;
    private app.swing.Button btn_AddType;
    private app.swing.Button btn_Back;
    private app.swing.Button btn_ClearAll;
    private app.swing.Button btn_Edit;
    private app.swing.Button btn_Refesh;
    private app.swing.Button btn_Remove;
    private javax.swing.JLabel btn_Search;
    private app.swing.Button btn_Type_Search;
    private javax.swing.JComboBox<String> cbox_Type;
    private javax.swing.JComboBox<String> cbox_Type_Search;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JRadioButton rbtn_Countinue;
    private javax.swing.JRadioButton rbtn_Stop;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private javax.swing.JTextArea txt_Desciption;
    private javax.swing.JTextField txt_NameProduct;
    private javax.swing.JTextField txt_Search;
    // End of variables declaration//GEN-END:variables
}

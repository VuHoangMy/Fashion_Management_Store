package app.component;

import app.BUS.ItemsList;
import app.CommonFuncion.ChangeColorCellTable;
import app.CommonFuncion.CommonFunction;
import app.DTO.Items;
import app.GUI.AddNewItem;
import app.GUI.ItemsDetail;
import java.awt.Font;
import java.util.Collections;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ItemsForm extends javax.swing.JPanel {

    private ItemsList itemsList;
    private String header[] = {"ID", "Name", "Material", "Size", "Color", "Price", "Inventory Number"};
    private DefaultTableModel defaultTableModel = new DefaultTableModel(header, 0);

    public ItemsForm() {
        initComponents();
        setStyleButton();
        CommonFunction.initScrollbar(scrollPane);
//        table.setBackground(Color.blue);
//        JTableHeader tableHeader = table.getTableHeader();
//        tableHeader.setBackground(Color.black);
//        tableHeader.setForeground(Color.white);
    }

    public void loadDataFromFile() {
        this.itemsList = new ItemsList();
        this.itemsList.initFile();
        this.itemsList.readFromFile();
        loadDataTable(itemsList);
        setButtonStatus(false);
    }

    private void setButtonStatus(boolean isEnable) {
        this.btn_Edit.setEnabled(isEnable);
        this.btn_Detail.setEnabled(isEnable);
    }

    public void loadDataTable(ItemsList itemsList) {
        defaultTableModel.setRowCount(0);
        for (Items i : itemsList.getItemslist()) {
            Vector<Object> v = addToVector(i);
            defaultTableModel.addRow(v);
        }
        table.setModel(defaultTableModel);
        new ChangeColorCellTable().changeTable(table, 4, 1, itemsList);
        table.getColumnModel().getColumn(4).setMaxWidth(50);
    }
    
    private void setStyleButton(){
        this.btn_Add.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Detail.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Edit.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Refresh.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
    }

    private Vector addToVector(Items items) {
        Vector<Object> v = new Vector<>();
        v.add(items.getID());
        v.add(items.getName());
        v.add(items.getMaterial());
        v.add(items.getSize());
        v.add("");
        v.add(CommonFunction.formatMoney(items.getPrice()));
        v.add(items.getInventoryNumber());
        return v;
    }

    private void eventSearchItem() {
        String searchText = txt_Search.getText();
        ItemsList TMPItemsLists = this.itemsList.findListItemByIDOrName(searchText);
        loadDataTable(TMPItemsLists);
    }

    private void eventShowDetailItems() {
        try {
            String ID = table.getValueAt(table.getSelectedRow(), 0).toString();
            Items i = this.itemsList.findItemsByID(ID);
            ItemsDetail form = new ItemsDetail(i);
            form.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please Select row!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eventEditItem() {
        String ID = table.getValueAt(table.getSelectedRow(), 0).toString();
        Items i = this.itemsList.findItemsByID(ID);
        AddNewItem addNewItem = new AddNewItem(this, i);
        addNewItem.setVisible(true);
    }

    private void eventRefresh() {
        loadDataFromFile();
        this.Rbtn_All.setSelected(true);
        this.txt_AmountInventory_Search.setText("");
        setButtonStatus(false);
        this.txt_Search.setText("Search");
    }

    private boolean isValidateInput() {
        String txtSearch = this.txt_AmountInventory_Search.getText();
        if (txtSearch.isEmpty() || !CommonFunction.isNumeric(txtSearch)) {
            JOptionPane.showMessageDialog(this, "Vui long nhap so luong.", "ERROR", JOptionPane.ERROR_MESSAGE);
            this.Rbtn_All.setSelected(true);
            return false;
        }
        return true;
    }

    private void eventFillterItemByInventoryNumber1(boolean isOver) {
        if (!isValidateInput()) {
            return;
        }
        ItemsList result = new ItemsList();
        String txtSearch = this.txt_AmountInventory_Search.getText();
        int amount = Integer.parseInt(txtSearch);

        if (isOver) {
            for (Items items : this.itemsList.getItemslist()) {
                if (items.getInventoryNumber() > amount) {
                    result.addNewItem(items);
                }
            }        
        }else{
            for (Items items : this.itemsList.getItemslist()) {
                if (items.getInventoryNumber() < amount) {
                    result.addNewItem(items);
                }
            }
        }

        loadDataTable(result);
    }

    private void eventFillterItemByInventoryNumber2(boolean isInStock) {
        ItemsList result = new ItemsList();
        if (isInStock) {
            for (Items items : this.itemsList.getItemslist()) {
                if (items.getInventoryNumber() > 0) {
                    result.addNewItem(items);
                }
            }
        } else {
            for (Items items : this.itemsList.getItemslist()) {
                if (items.getInventoryNumber() <= 0) {
                    result.addNewItem(items);
                }
            }
        }
        loadDataTable(result);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroup_Sort = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        txt_Search = new javax.swing.JTextField();
        btn_Search = new javax.swing.JLabel();
        Rbtn_All = new javax.swing.JRadioButton();
        Rbtn_AZ_Sort = new javax.swing.JRadioButton();
        Rbtn_ZA_Sort = new javax.swing.JRadioButton();
        Rbtn_PriceHtoL = new javax.swing.JRadioButton();
        Rbtn_PriceLtoH = new javax.swing.JRadioButton();
        txt_AmountInventory_Search = new javax.swing.JTextField();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        Rbtn_Inv_OverAm = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        Rbtn_Inv_UnderAm = new javax.swing.JRadioButton();
        Rbtn_Inv_InStock = new javax.swing.JRadioButton();
        Rbtn_Inv_OutStock = new javax.swing.JRadioButton();
        BacgroundFindProduct = new javax.swing.JLabel();
        BackgroundFindByPrice = new javax.swing.JLabel();
        BackgroundInventoryNumber = new javax.swing.JLabel();
        btn_Detail = new app.swing.Button();
        btn_Back = new app.swing.Button();
        btn_Add = new app.swing.Button();
        btn_Edit = new app.swing.Button();
        btn_Printer = new app.swing.Button();
        btn_Refresh = new app.swing.Button();
        Background = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Items");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, -1, -1));

        txt_Search.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Search.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txt_Search.setForeground(new java.awt.Color(204, 204, 204));
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
        add(txt_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 150, 510, 40));

        btn_Search.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Search.png"))); // NOI18N
        btn_Search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_SearchMouseClicked(evt);
            }
        });
        add(btn_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 140, -1, 60));

        btnGroup_Sort.add(Rbtn_All);
        Rbtn_All.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        Rbtn_All.setForeground(new java.awt.Color(0, 255, 204));
        Rbtn_All.setSelected(true);
        Rbtn_All.setText("All");
        Rbtn_All.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rbtn_AllActionPerformed(evt);
            }
        });
        add(Rbtn_All, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, -1, -1));

        btnGroup_Sort.add(Rbtn_AZ_Sort);
        Rbtn_AZ_Sort.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        Rbtn_AZ_Sort.setForeground(new java.awt.Color(0, 255, 204));
        Rbtn_AZ_Sort.setText("A - Z");
        Rbtn_AZ_Sort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rbtn_AZ_SortActionPerformed(evt);
            }
        });
        add(Rbtn_AZ_Sort, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, -1, -1));

        btnGroup_Sort.add(Rbtn_ZA_Sort);
        Rbtn_ZA_Sort.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        Rbtn_ZA_Sort.setForeground(new java.awt.Color(0, 255, 204));
        Rbtn_ZA_Sort.setText("Z - A");
        Rbtn_ZA_Sort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rbtn_ZA_SortActionPerformed(evt);
            }
        });
        add(Rbtn_ZA_Sort, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, -1));

        btnGroup_Sort.add(Rbtn_PriceHtoL);
        Rbtn_PriceHtoL.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        Rbtn_PriceHtoL.setForeground(new java.awt.Color(0, 255, 204));
        Rbtn_PriceHtoL.setText("High to Low");
        Rbtn_PriceHtoL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rbtn_PriceHtoLActionPerformed(evt);
            }
        });
        add(Rbtn_PriceHtoL, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, -1, -1));

        btnGroup_Sort.add(Rbtn_PriceLtoH);
        Rbtn_PriceLtoH.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        Rbtn_PriceLtoH.setForeground(new java.awt.Color(0, 255, 204));
        Rbtn_PriceLtoH.setText("Low to High");
        Rbtn_PriceLtoH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rbtn_PriceLtoHActionPerformed(evt);
            }
        });
        add(Rbtn_PriceLtoH, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 330, -1, -1));
        add(txt_AmountInventory_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 530, 195, 30));

        table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Material", "Size", "Color", "Price", "Inventory number"
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

        add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 220, 1260, 430));

        btnGroup_Sort.add(Rbtn_Inv_OverAm);
        Rbtn_Inv_OverAm.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        Rbtn_Inv_OverAm.setForeground(new java.awt.Color(0, 255, 204));
        Rbtn_Inv_OverAm.setText("Over the norms");
        Rbtn_Inv_OverAm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rbtn_Inv_OverAmActionPerformed(evt);
            }
        });
        add(Rbtn_Inv_OverAm, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 580, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Amount");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 500, -1, -1));

        btnGroup_Sort.add(Rbtn_Inv_UnderAm);
        Rbtn_Inv_UnderAm.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        Rbtn_Inv_UnderAm.setForeground(new java.awt.Color(0, 255, 204));
        Rbtn_Inv_UnderAm.setText("Under the norms");
        Rbtn_Inv_UnderAm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rbtn_Inv_UnderAmActionPerformed(evt);
            }
        });
        add(Rbtn_Inv_UnderAm, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 610, -1, -1));

        btnGroup_Sort.add(Rbtn_Inv_InStock);
        Rbtn_Inv_InStock.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        Rbtn_Inv_InStock.setForeground(new java.awt.Color(0, 255, 204));
        Rbtn_Inv_InStock.setText("In Stock");
        Rbtn_Inv_InStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rbtn_Inv_InStockActionPerformed(evt);
            }
        });
        add(Rbtn_Inv_InStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 640, -1, -1));

        btnGroup_Sort.add(Rbtn_Inv_OutStock);
        Rbtn_Inv_OutStock.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        Rbtn_Inv_OutStock.setForeground(new java.awt.Color(0, 255, 204));
        Rbtn_Inv_OutStock.setText("Out of Stock");
        Rbtn_Inv_OutStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rbtn_Inv_OutStockActionPerformed(evt);
            }
        });
        add(Rbtn_Inv_OutStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 670, -1, -1));

        BacgroundFindProduct.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sort By Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 15), new java.awt.Color(255, 255, 255))); // NOI18N
        add(BacgroundFindProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 240, 150));

        BackgroundFindByPrice.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Price", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 15), new java.awt.Color(255, 255, 255))); // NOI18N
        add(BackgroundFindByPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, 240, 140));

        BackgroundInventoryNumber.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Inventory number", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 15), new java.awt.Color(255, 255, 255))); // NOI18N
        add(BackgroundInventoryNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, 240, 260));

        btn_Detail.setForeground(new java.awt.Color(255, 255, 0));
        btn_Detail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Detail.png"))); // NOI18N
        btn_Detail.setText("  Detail");
        btn_Detail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DetailActionPerformed(evt);
            }
        });
        add(btn_Detail, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 140, 110, -1));

        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_Back.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        btn_Add.setForeground(new java.awt.Color(51, 255, 51));
        btn_Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Add.png"))); // NOI18N
        btn_Add.setText(" Add");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });
        add(btn_Add, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 140, 150, 50));

        btn_Edit.setForeground(new java.awt.Color(0, 0, 153));
        btn_Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Edit.png"))); // NOI18N
        btn_Edit.setText("Edit");
        btn_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditActionPerformed(evt);
            }
        });
        add(btn_Edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 140, 100, 50));

        btn_Printer.setForeground(new java.awt.Color(51, 153, 255));
        btn_Printer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Printer.png"))); // NOI18N
        add(btn_Printer, new org.netbeans.lib.awtextra.AbsoluteConstraints(1460, 140, 70, 50));

        btn_Refresh.setForeground(new java.awt.Color(0, 0, 255));
        btn_Refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Refresh.png"))); // NOI18N
        btn_Refresh.setText("  Refresh");
        btn_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefreshActionPerformed(evt);
            }
        });
        add(btn_Refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 740, 150, 50));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor5.jpg"))); // NOI18N
        add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1800, 900));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_BackActionPerformed

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        AddNewItem addNewItem = new AddNewItem(this);
        addNewItem.setVisible(true);
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_SearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SearchMouseClicked
        eventSearchItem();
    }//GEN-LAST:event_btn_SearchMouseClicked

    private void txt_SearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusGained
        CommonFunction.setPlaceHolder(txt_Search, true, "Search");
    }//GEN-LAST:event_txt_SearchFocusGained

    private void txt_SearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusLost
        CommonFunction.setPlaceHolder(txt_Search, false, "Search");
    }//GEN-LAST:event_txt_SearchFocusLost

    private void btn_DetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DetailActionPerformed
        eventShowDetailItems();
    }//GEN-LAST:event_btn_DetailActionPerformed

    private void btn_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditActionPerformed
        eventEditItem();
    }//GEN-LAST:event_btn_EditActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        setButtonStatus(true);
    }//GEN-LAST:event_tableMouseClicked

    private void Rbtn_PriceLtoHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rbtn_PriceLtoHActionPerformed
        Collections.sort(this.itemsList.getItemslist(), Items.ItemPriceCompareASC);
        loadDataTable(itemsList);
    }//GEN-LAST:event_Rbtn_PriceLtoHActionPerformed

    private void Rbtn_PriceHtoLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rbtn_PriceHtoLActionPerformed
        Collections.sort(this.itemsList.getItemslist(), Items.ItemPriceCompareDES);
        loadDataTable(itemsList);
    }//GEN-LAST:event_Rbtn_PriceHtoLActionPerformed

    private void Rbtn_AZ_SortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rbtn_AZ_SortActionPerformed
        Collections.sort(this.itemsList.getItemslist(), Items.ItemNameCompareASC);
        loadDataTable(itemsList);
    }//GEN-LAST:event_Rbtn_AZ_SortActionPerformed

    private void Rbtn_ZA_SortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rbtn_ZA_SortActionPerformed
        Collections.sort(this.itemsList.getItemslist(), Items.ItemNameCompareDES);
        loadDataTable(itemsList);
    }//GEN-LAST:event_Rbtn_ZA_SortActionPerformed

    private void btn_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefreshActionPerformed
        eventRefresh();
    }//GEN-LAST:event_btn_RefreshActionPerformed

    private void Rbtn_AllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rbtn_AllActionPerformed
        loadDataFromFile();
    }//GEN-LAST:event_Rbtn_AllActionPerformed

    private void Rbtn_Inv_OverAmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rbtn_Inv_OverAmActionPerformed
        eventFillterItemByInventoryNumber1(true);
    }//GEN-LAST:event_Rbtn_Inv_OverAmActionPerformed

    private void Rbtn_Inv_UnderAmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rbtn_Inv_UnderAmActionPerformed
        eventFillterItemByInventoryNumber1(false);
    }//GEN-LAST:event_Rbtn_Inv_UnderAmActionPerformed

    private void Rbtn_Inv_InStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rbtn_Inv_InStockActionPerformed
        eventFillterItemByInventoryNumber2(true);
    }//GEN-LAST:event_Rbtn_Inv_InStockActionPerformed

    private void Rbtn_Inv_OutStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rbtn_Inv_OutStockActionPerformed
        eventFillterItemByInventoryNumber2(false);
    }//GEN-LAST:event_Rbtn_Inv_OutStockActionPerformed

    private void txt_SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyReleased
        eventSearchItem();
    }//GEN-LAST:event_txt_SearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BacgroundFindProduct;
    private javax.swing.JLabel Background;
    private javax.swing.JLabel BackgroundFindByPrice;
    private javax.swing.JLabel BackgroundInventoryNumber;
    private javax.swing.JRadioButton Rbtn_AZ_Sort;
    private javax.swing.JRadioButton Rbtn_All;
    private javax.swing.JRadioButton Rbtn_Inv_InStock;
    private javax.swing.JRadioButton Rbtn_Inv_OutStock;
    private javax.swing.JRadioButton Rbtn_Inv_OverAm;
    private javax.swing.JRadioButton Rbtn_Inv_UnderAm;
    private javax.swing.JRadioButton Rbtn_PriceHtoL;
    private javax.swing.JRadioButton Rbtn_PriceLtoH;
    private javax.swing.JRadioButton Rbtn_ZA_Sort;
    private javax.swing.ButtonGroup btnGroup_Sort;
    private app.swing.Button btn_Add;
    private app.swing.Button btn_Back;
    private app.swing.Button btn_Detail;
    private app.swing.Button btn_Edit;
    private app.swing.Button btn_Printer;
    private app.swing.Button btn_Refresh;
    private javax.swing.JLabel btn_Search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_AmountInventory_Search;
    private javax.swing.JTextField txt_Search;
    // End of variables declaration//GEN-END:variables
}

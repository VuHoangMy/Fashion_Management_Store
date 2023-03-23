package app.GUI;

import app.BUS.ItemsList;
import app.BUS.ProductsList;
import app.CommonFuncion.ChangeColorCellTable;
import app.CommonFuncion.CommonFunction;
import app.CommonFuncion.QRCodeGenerator;

import app.DTO.Items;
import app.DTO.Products;
import app.component.ItemsForm;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class AddNewItem extends javax.swing.JFrame {

    private File[] files; // array choose picture files
    private ItemsList TMP;
    private String header[] = {"ID", "Name", "Color", "Material", "Size", "Price"};
    private DefaultTableModel defaultTableModel = new DefaultTableModel(header, 0);

    private ItemsForm itemForm;
    private boolean isEdit = false;
    private ProductsList productsList;

    public AddNewItem(ItemsForm itemForm) { // Add new Item
        this.itemForm = itemForm;
        setStyleForm();
        initInstance();
        setEnableStatusButton(false); // btn romve ,edit
    }

    public AddNewItem(ItemsForm itemForm, Items item) { // Edit Items

        this.itemForm = itemForm;
        this.isEdit = true;
        setStyleForm();
        initInstance();
        this.lbl_Title.setText("Edit Items");
        this.TMP.addNewItem(item);
        loadDataTable(TMP);

        setEnableStatusButton(false);// btn romve ,edit
    }

    private void setEnableStatusButton(boolean isEnable) { // work here

        boolean checkList = this.TMP.getItemslist().isEmpty();

        if (isEdit) {
            this.remove(this.btn_Add);
            this.remove(this.btn_RemoveAll);
        } else {
            this.btn_Add.setEnabled(!isEnable);
            this.btn_RemoveAll.setEnabled(!checkList);
        }

        this.btn_Edit.setEnabled(isEnable);
        this.btn_Remove.setEnabled(isEnable);

        this.btn_Save.setEnabled(!checkList);

        checkList = panelItems.getComponentCount() < 1; // if panelItems don't have picture
        this.btn_ClearPicture.setEnabled(!checkList);
    }

    private void loadDataToComponent() {
        String ID = table.getValueAt(table.getSelectedRow(), 0).toString();
        Items i = this.TMP.findItemsByID(ID);

        this.cbox_PName.setSelectedItem(i.getName());
        this.cbox_Material.setSelectedItem(i.getMaterial());
        this.cbox_Size.setSelectedItem(i.getSize());
        this.lbl_Color.setBackground(i.getColor());
        this.txt_Price.setText(String.valueOf(i.getPrice()));
        if (i.getImageList() != null) {
            for (File file : i.getImageList()) {
                JLabel lbl = new JLabel(resizeImage(new ImageIcon(file.getAbsolutePath())));
                panelItems.add(lbl);
            }
            revalidate();
            repaint();
        }

    }

    private void initInstance() {
        this.productsList = new ProductsList();
        this.productsList.initFile();
        this.productsList.readFromFile();
        this.TMP = new ItemsList();

        loadDataProduct();

    }

    private void setStyleForm() {
        initComponents();
        CommonFunction.initScrollbar(scrollPane);
        CommonFunction.updateScrollBar(this.cbox_Material);
        this.panelItems.setLayout(new MigLayout("wrap 4, inset 30", "[fill, 100]10[fill, 100]", "[]10[]"));
        table.setModel(defaultTableModel);

        table.getColumnModel().getColumn(2).setMaxWidth(50); // set width of cell Color
    }

    private boolean validateDataInput() {
        if (this.txt_Price.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please input Price!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private ImageIcon resizeImage(ImageIcon icon) {
//        ImageIcon icon = new ImageIcon(filename);
        Image image = icon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        icon = new ImageIcon(newimg);  // transform it back
        return icon;
    }

    private void chooserPicture() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Image", "jpg", "png");
        chooser.setFileFilter(extensionFilter);
        chooser.setDialogTitle("Choose Pictures");
        chooser.setMultiSelectionEnabled(true);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            files = chooser.getSelectedFiles();
            for (File file : files) {
                JLabel lbl = new JLabel(resizeImage(new ImageIcon(file.getAbsolutePath())));
                panelItems.add(lbl);
            }
            revalidate();
            repaint();
        }
    }

    private Items createNewItem() {
        String name = this.cbox_PName.getSelectedItem().toString();
        String material = this.cbox_Material.getSelectedItem().toString();
        String size = this.cbox_Size.getSelectedItem().toString();
        Color color = this.lbl_Color.getBackground();
        double price = Double.parseDouble(this.txt_Price.getText());
        ArrayList<File> pictureList = null;
        if (files != null) {
            pictureList = new ArrayList<>(Arrays.asList(files));
        }
        Items items = new Items(this.TMP.createItemID(), name, price, size, color, material, pictureList);
        System.out.println("New Items: " + items);
        return items;
    }

    private void addData() {
        try {

            Items items = createNewItem();

            TMP.addNewItem(items);

            loadDataTable(TMP);
        } catch (Exception e) {
//            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Kiem tra du lieu nhap!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void loadDataTable(ItemsList TMP) {
        try {
            defaultTableModel.setRowCount(0);
            for (Items i : TMP.getItemslist()) {
                Vector<Object> v = addToVector(i);
                defaultTableModel.addRow(v);
            }
            new ChangeColorCellTable().changeTable(table, 2, 0, TMP); // set color of cell
            table.setModel(defaultTableModel);

        } catch (Exception e) {
        }
        setEnableStatusButton(false);
    }

    private void loadDataProduct() {
        this.cbox_PName.removeAllItems();
        for (Products product : this.productsList.getProductsList()) {
            this.cbox_PName.addItem(product.getName());
        }
        this.cbox_PName.setSelectedIndex(0);
    }

    private Vector addToVector(Items items) {
        Vector<Object> v = new Vector<>();
        v.add(items.getID());
        v.add(items.getName());
        v.add("");
        v.add(items.getMaterial());
        v.add(items.getSize());
        v.add(CommonFunction.formatMoney(items.getPrice()));

        return v;
    }

    private void removeTMPItems(boolean isRemoveAll) {
        if (isRemoveAll) {
            this.TMP.getItemslist().removeAll(this.TMP.getItemslist());
            loadDataTable(TMP);
            return;
        }
        try {
            int row = table.getSelectedRow();
            int col = 0;
            String ID = table.getValueAt(row, col).toString();

            this.TMP.getItemslist().remove(this.TMP.findItemsByID(ID));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui long chon san pham de xoa!", "Canh Bao", JOptionPane.WARNING_MESSAGE);
        }

        loadDataTable(TMP);
    }

    private void eventEditItem() {
        try {
            String ID = table.getValueAt(table.getSelectedRow(), 0).toString();
            this.TMP.removeItemByID(ID);
            Items items = createNewItem();
            items.setID(ID);
            this.TMP.addNewItem(items);
            loadDataTable(TMP);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Chon san pham!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        this.cbox_Material.setSelectedIndex(0);
        this.cbox_PName.setSelectedIndex(0);
        this.cbox_Size.setSelectedIndex(0);
        this.lbl_Color.setBackground(Color.WHITE);
        this.txt_Price.setText("");
        eventClearAllPicture();
        setEnableStatusButton(false);
        table.clearSelection();
    }

    private void eventSaveItem() {
        ItemsList allItems = new ItemsList();
        allItems.initFile();
        allItems.readFromFile();

        if (this.isEdit) {
            String ID = this.TMP.getItemslist().get(0).getID();
            Items newItem = this.TMP.getItemslist().get(0);
            Items oldItem = allItems.findItemsByID(ID);

            oldItem.setName(newItem.getName());
            oldItem.setColor(newItem.getColor());
            oldItem.setMaterial(newItem.getMaterial());
            oldItem.setSize(newItem.getSize());
            oldItem.setImageList(newItem.getImageList());
            oldItem.setPrice(newItem.getPrice());

            allItems.writeToFile();
            this.itemForm.loadDataFromFile();
            dispose();
            return;
        }
        for (Items i : this.TMP.getItemslist()) {
            i.setID(allItems.createItemID());
            allItems.appendAItemsIntoFile(i);
        }
        
        //generator QRcode
        addNewQRCodeForItems(this.TMP);

        removeTMPItems(true);
        this.itemForm.loadDataFromFile(); // Update data in ItenForm Table
    }

    private void addNewQRCodeForItems(ItemsList itemsList) {
        try {
            for (Items items : itemsList.getItemslist()) {
                String ID = items.getID();
                String path = "src/app/QRCodeIMG/" + ID + ".jpg";
                QRCodeGenerator.generatorQRCode(ID, path);
            }
        } catch (Exception e) {
        }
    }

    private void eventClearAllPicture() {
        this.panelItems.removeAll();
        revalidate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbox_PName = new javax.swing.JComboBox<>();
        cbox_Size = new javax.swing.JComboBox<>();
        lbl_Title = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbox_Material = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_Price = new javax.swing.JTextField();
        panelItems = new javax.swing.JPanel();
        btn_ChooserImg = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        btn_Save = new app.swing.Button();
        btn_RemoveAll = new app.swing.Button();
        btn_Cancel = new app.swing.Button();
        btn_ChooseColor = new app.swing.Button();
        btn_Add = new app.swing.Button();
        btn_Edit = new app.swing.Button();
        btn_ClearPicture = new app.swing.Button();
        btn_Remove = new app.swing.Button();
        lbl_Color = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();
        BackgroundImgChooser = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1100, 750));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbox_PName.setBackground(new java.awt.Color(102, 102, 255));
        cbox_PName.setEditable(true);
        cbox_PName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        cbox_PName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbox_PName.setOpaque(false);
        getContentPane().add(cbox_PName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 160, 30));

        cbox_Size.setBackground(new java.awt.Color(102, 102, 255));
        cbox_Size.setEditable(true);
        cbox_Size.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        cbox_Size.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "S", "M", "L", "X", "XXL" }));
        cbox_Size.setBorder(null);
        cbox_Size.setOpaque(false);
        getContentPane().add(cbox_Size, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, 160, 30));

        lbl_Title.setFont(new java.awt.Font("Yu Gothic UI Semibold", 2, 24)); // NOI18N
        lbl_Title.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Title.setText("New Item");
        getContentPane().add(lbl_Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 150, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name Of Product");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        cbox_Material.setEditable(true);
        cbox_Material.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cotton", "Denim", "Canvas", "Linen", "Silk", "Leather", "Suede", "Velvet", "Wool", "Fur", "Polyester" }));
        cbox_Material.setAutoscrolls(true);
        cbox_Material.setOpaque(false);
        getContentPane().add(cbox_Material, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 170, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Price");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Size");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Color");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Material");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, -1, -1));

        txt_Price.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_Price.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_Price.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 153, 255)));
        txt_Price.setOpaque(false);
        getContentPane().add(txt_Price, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, 195, 30));

        panelItems.setBackground(new java.awt.Color(102, 102, 102,10));
        panelItems.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        panelItems.setAutoscrolls(true);

        javax.swing.GroupLayout panelItemsLayout = new javax.swing.GroupLayout(panelItems);
        panelItems.setLayout(panelItemsLayout);
        panelItemsLayout.setHorizontalGroup(
            panelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 466, Short.MAX_VALUE)
        );
        panelItemsLayout.setVerticalGroup(
            panelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 266, Short.MAX_VALUE)
        );

        getContentPane().add(panelItems, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 470, 270));

        btn_ChooserImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/AddImg.png"))); // NOI18N
        btn_ChooserImg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ChooserImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ChooserImgMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ChooserImgMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ChooserImgMouseExited(evt);
            }
        });
        getContentPane().add(btn_ChooserImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, -1, -1));

        table.setBackground(new java.awt.Color(204, 204, 204));
        table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Giay Convert", "M", "Vai", "Blue", "15000000"}
            },
            new String [] {
                "Name Product", "Size", "Material", "Color", "Price"
            }
        ));
        table.setIntercellSpacing(new java.awt.Dimension(5, 5));
        table.setOpaque(false);
        table.setRowHeight(25);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(table);

        getContentPane().add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 470, 770, 210));

        jLabel7.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Picture");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 90, -1, -1));

        btn_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Save.png"))); // NOI18N
        btn_Save.setText("Save");
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });
        getContentPane().add(btn_Save, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 470, 90, -1));

        btn_RemoveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/DeleteAll.png"))); // NOI18N
        btn_RemoveAll.setText("Remove All");
        btn_RemoveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RemoveAllActionPerformed(evt);
            }
        });
        getContentPane().add(btn_RemoveAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 610, 130, 50));

        btn_Cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/x.png"))); // NOI18N
        btn_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelActionPerformed(evt);
            }
        });
        getContentPane().add(btn_Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 10, 40, 40));

        btn_ChooseColor.setText("Choose Color");
        btn_ChooseColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChooseColorActionPerformed(evt);
            }
        });
        getContentPane().add(btn_ChooseColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, -1, -1));

        btn_Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/plus.png"))); // NOI18N
        btn_Add.setText(" Add");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });
        getContentPane().add(btn_Add, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 120, 110, -1));

        btn_Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/EditHover.png"))); // NOI18N
        btn_Edit.setText("Edit");
        btn_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditActionPerformed(evt);
            }
        });
        getContentPane().add(btn_Edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 290, 130, -1));

        btn_ClearPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Clear.png"))); // NOI18N
        btn_ClearPicture.setText("Clear Picture");
        btn_ClearPicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ClearPictureActionPerformed(evt);
            }
        });
        getContentPane().add(btn_ClearPicture, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 210, -1, -1));

        btn_Remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Delete.png"))); // NOI18N
        btn_Remove.setText("Remove");
        btn_Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RemoveActionPerformed(evt);
            }
        });
        getContentPane().add(btn_Remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 540, 130, -1));

        lbl_Color.setBackground(new java.awt.Color(255, 255, 255));
        lbl_Color.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        lbl_Color.setOpaque(true);
        getContentPane().add(lbl_Color, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 40, 30));

        Background.setBackground(new java.awt.Color(0, 0, 0, 80));
        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor4.png"))); // NOI18N
        getContentPane().add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 750));

        BackgroundImgChooser.setBackground(new java.awt.Color(102, 102, 102,10));
        BackgroundImgChooser.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 2, true));
        BackgroundImgChooser.setOpaque(true);
        getContentPane().add(BackgroundImgChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 560, 270));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ChooserImgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ChooserImgMouseEntered
        btn_ChooserImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/AddImgHover.png"))); // NOI18N
    }//GEN-LAST:event_btn_ChooserImgMouseEntered

    private void btn_ChooserImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ChooserImgMouseExited
        btn_ChooserImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/AddImg.png"))); // NOI18N
    }//GEN-LAST:event_btn_ChooserImgMouseExited

    private void btn_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelActionPerformed
        dispose();
    }//GEN-LAST:event_btn_CancelActionPerformed

    private void btn_ChooseColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChooseColorActionPerformed
        ColorChooser colorChooser = new ColorChooser(lbl_Color);
        colorChooser.setVisible(true);
    }//GEN-LAST:event_btn_ChooseColorActionPerformed

    private void btn_ChooserImgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ChooserImgMouseClicked
        chooserPicture();
    }//GEN-LAST:event_btn_ChooserImgMouseClicked

    private void btn_ClearPictureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ClearPictureActionPerformed
        eventClearAllPicture();
    }//GEN-LAST:event_btn_ClearPictureActionPerformed

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        if (validateDataInput()) {
            addData();
        }
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RemoveActionPerformed
        removeTMPItems(false);
    }//GEN-LAST:event_btn_RemoveActionPerformed

    private void btn_RemoveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RemoveAllActionPerformed
        removeTMPItems(true);
    }//GEN-LAST:event_btn_RemoveAllActionPerformed

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        eventSaveItem();
    }//GEN-LAST:event_btn_SaveActionPerformed

    private void btn_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditActionPerformed
        eventEditItem();
    }//GEN-LAST:event_btn_EditActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        loadDataToComponent();
        setEnableStatusButton(true);
    }//GEN-LAST:event_tableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JLabel BackgroundImgChooser;
    private app.swing.Button btn_Add;
    private app.swing.Button btn_Cancel;
    private app.swing.Button btn_ChooseColor;
    private javax.swing.JLabel btn_ChooserImg;
    private app.swing.Button btn_ClearPicture;
    private app.swing.Button btn_Edit;
    private app.swing.Button btn_Remove;
    private app.swing.Button btn_RemoveAll;
    private app.swing.Button btn_Save;
    private javax.swing.JComboBox<String> cbox_Material;
    private javax.swing.JComboBox<String> cbox_PName;
    private javax.swing.JComboBox<String> cbox_Size;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lbl_Color;
    private javax.swing.JLabel lbl_Title;
    private javax.swing.JPanel panelItems;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_Price;
    // End of variables declaration//GEN-END:variables
}


// Chu y: neu mau bi loi, coi lai ID cua Items

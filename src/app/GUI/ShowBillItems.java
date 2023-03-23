package app.GUI;

import app.BUS.ItemsList;
import app.CommonFuncion.ChangeColorCellTable;
import app.CommonFuncion.CommonFunction;
import app.DTO.BillImportGoods;
import app.DTO.Items;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class ShowBillItems extends javax.swing.JFrame {

    private String header[] = {"ID", "Name", "Material", "Size", "Color", "Amount", "Import Price"};
    private DefaultTableModel defaultTableModel = new DefaultTableModel(header, 0);
    private ArrayList<ImageIcon> imageIcons = new ArrayList<>();
    private ArrayList<File> imageList;
    private int currentindex = 0;

    public ShowBillItems(BillImportGoods bill) {
        initComponents();
        CommonFunction.initScrollbar(scrollPane);
        loadDataTable(bill.getItems(), bill.getAmountImport(), bill.getPriceImport());

        imageList = bill.getItems().getImageList();
        loadImgItems();
    }

    private void loadDataTable(Items i, int amount, double importPrice) {
        defaultTableModel.setRowCount(0);

        Vector<Object> v = addToVector(i, amount, importPrice);
        defaultTableModel.addRow(v);
        ItemsList ilist = new ItemsList();
        ilist.addNewItem(i);

        table.setModel(defaultTableModel);
        new ChangeColorCellTable().changeTable(table, 4, 1, ilist);
        table.getColumnModel().getColumn(4).setMaxWidth(50);
        
        // set lbl Total Price
        String result = String.valueOf(amount * importPrice) + " VND.";
        lbl_TotalPrice.setText(result);
    }

    private void loadImgItems() {
        if (this.imageList != null) {
            for (File file : this.imageList) {
                this.imageIcons.add(resizeImage(new ImageIcon(file.getAbsolutePath())));
            }
        }
        else{
            this.imageIcons.add(resizeImage(new ImageIcon("src/app/Images/No IMG.png")));
        }
        showIMG(0);
    }
    
    private void showIMG(int index){
        lbl_ShowIMG.setIcon(imageIcons.get(index));
    }

    private ImageIcon resizeImage(ImageIcon icon) {
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(550, 370, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        icon = new ImageIcon(newimg);
        return icon;
    }

    private Vector addToVector(Items items, int amount, double price) {
        Vector<Object> v = new Vector<>();
        v.add(items.getID());
        v.add(items.getName());
        v.add(items.getMaterial());
        v.add(items.getSize());
        v.add("");
        v.add(amount);
        v.add(price + " VND");
        return v;
    }
    
    private void changeListIMG(){
        String ID = table.getValueAt(table.getSelectedRow(), 0).toString();
        
    }

    private void hasNext() {
        int sizeArray = imageIcons.size() - 1;
        currentindex += 1;
        if (currentindex <= sizeArray) {
            showIMG(currentindex);
        } else {
            currentindex = 0;
            showIMG(currentindex);
        }
    }

    private void hasPrevious() {
        currentindex -= 1;
        if (currentindex < 0) {
            currentindex = imageIcons.size() - 1;
            showIMG(currentindex);
        } else {
            showIMG(currentindex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        button1 = new app.swing.Button();
        btn_Cancel = new app.swing.Button();
        panel = new javax.swing.JPanel();
        btn_Pre = new app.swing.Button();
        btn_Next = new app.swing.Button();
        lbl_ShowIMG = new javax.swing.JLabel();
        lbl_Total = new javax.swing.JLabel();
        lbl_TotalPrice = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1310, 420));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1310, 420));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.setOpaque(false);
        table.setRowHeight(30);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(table);

        getContentPane().add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 690, 140));

        button1.setText("Printer");
        getContentPane().add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 240, -1, -1));

        btn_Cancel.setText("Cancel");
        btn_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelActionPerformed(evt);
            }
        });
        getContentPane().add(btn_Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 240, -1, -1));

        panel.setBackground(new java.awt.Color(102, 102, 102));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Pre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/btn_L.png"))); // NOI18N
        btn_Pre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PreActionPerformed(evt);
            }
        });
        panel.add(btn_Pre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        btn_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/btn_R.png"))); // NOI18N
        btn_Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NextActionPerformed(evt);
            }
        });
        panel.add(btn_Next, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, -1, -1));
        panel.add(lbl_ShowIMG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 370));

        getContentPane().add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 550, 370));

        lbl_Total.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        lbl_Total.setText("Total:");
        getContentPane().add(lbl_Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 190, 90, -1));

        lbl_TotalPrice.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        lbl_TotalPrice.setForeground(new java.awt.Color(255, 51, 51));
        lbl_TotalPrice.setText("0 VND.");
        getContentPane().add(lbl_TotalPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 190, 310, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelActionPerformed
        this.imageIcons = null;
        this.imageList = null;
        dispose();  
    }//GEN-LAST:event_btn_CancelActionPerformed

    private void btn_PreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PreActionPerformed
       hasPrevious();
    }//GEN-LAST:event_btn_PreActionPerformed

    private void btn_NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NextActionPerformed
        hasNext();
    }//GEN-LAST:event_btn_NextActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        
    }//GEN-LAST:event_tableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.swing.Button btn_Cancel;
    private app.swing.Button btn_Next;
    private app.swing.Button btn_Pre;
    private app.swing.Button button1;
    private javax.swing.JLabel lbl_ShowIMG;
    private javax.swing.JLabel lbl_Total;
    private javax.swing.JLabel lbl_TotalPrice;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}

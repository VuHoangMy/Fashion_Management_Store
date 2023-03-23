
package app.GUI;

import app.CommonFuncion.CommonFunction;
import app.DTO.Items;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class ItemsDetail extends javax.swing.JFrame {

    private ArrayList<ImageIcon> imageIcons = new ArrayList<>();
    private ArrayList<File> imageList;
    private int currentindex = 0;
    private final int widthIMG = 380, heightIMG = 240;
    
    public ItemsDetail() {
        initComponents();
    }
    
    public ItemsDetail(Items item) {
        initComponents();
        this.imageList = item.getImageList();
        loadData(item);
    }
    
    private void loadData(Items item){
        this.lbl_ItemID.setText(item.getID());
        this.lbl_Color.setBackground(item.getColor());
        this.lbl_Material.setText(item.getMaterial());
        this.lbl_Name.setText(item.getName());
        this.lbl_Price.setText(CommonFunction.formatMoney(item.getPrice()));
        this.lbl_Size.setText(item.getSize());
        this.imageList = item.getImageList();
        initImageIcons();
    }
    
     private void initImageIcons() {
         
        if (this.imageList != null) {
            for (File file : this.imageList) {
                this.imageIcons.add(CommonFunction.resizeImage(new ImageIcon(file.getAbsolutePath()), widthIMG, heightIMG));
            }
        }
        else{
            this.imageIcons.add(CommonFunction.resizeImage(new ImageIcon("src/app/Images/No IMG.png"), widthIMG, heightIMG));
        }
        showIMG(0);
    }
    
    private void showIMG(int index){
        slideImg.setIcon(imageIcons.get(index));
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
    
//    public void setLbl_Price(string )

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        btn_Pre = new app.swing.Button();
        btn_Next = new app.swing.Button();
        slideImg = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_Price = new javax.swing.JLabel();
        lbl_ItemID = new javax.swing.JLabel();
        lbl_Color = new javax.swing.JLabel();
        lbl_Size = new javax.swing.JLabel();
        lbl_Name = new javax.swing.JLabel();
        lbl_Material = new javax.swing.JLabel();
        button1 = new app.swing.Button();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 400));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Pre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/btn_L.png"))); // NOI18N
        btn_Pre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PreActionPerformed(evt);
            }
        });
        panel.add(btn_Pre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 50));

        btn_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/btn_R.png"))); // NOI18N
        btn_Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NextActionPerformed(evt);
            }
        });
        panel.add(btn_Next, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, -1, 50));

        slideImg.setBackground(new java.awt.Color(204, 204, 204));
        slideImg.setOpaque(true);
        panel.add(slideImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 240));

        getContentPane().add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 380, 240));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Chi Tiet San Pham");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Ma SP: ");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Ten SP: ");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 100, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Gia SP: ");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 270, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Mau Sac: ");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Kich Thuoc:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Chat Lieu:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 190, -1, -1));

        lbl_Price.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbl_Price.setForeground(new java.awt.Color(255, 51, 51));
        lbl_Price.setText("Price");
        getContentPane().add(lbl_Price, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 270, -1, -1));

        lbl_ItemID.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbl_ItemID.setText("ID");
        getContentPane().add(lbl_ItemID, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, -1, -1));

        lbl_Color.setBackground(new java.awt.Color(255, 204, 204));
        lbl_Color.setOpaque(true);
        getContentPane().add(lbl_Color, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 180, 40, 30));

        lbl_Size.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbl_Size.setText("Size");
        getContentPane().add(lbl_Size, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 270, -1, -1));

        lbl_Name.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbl_Name.setText("Name");
        getContentPane().add(lbl_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 100, -1, -1));

        lbl_Material.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbl_Material.setText("Material");
        getContentPane().add(lbl_Material, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 190, -1, -1));

        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/x.png"))); // NOI18N
        button1.setText("\n");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        getContentPane().add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 10, -1, -1));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor4.png"))); // NOI18N
        getContentPane().add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 400));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
       dispose();
    }//GEN-LAST:event_button1ActionPerformed

    private void btn_PreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PreActionPerformed
        hasPrevious();
    }//GEN-LAST:event_btn_PreActionPerformed

    private void btn_NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NextActionPerformed
        hasNext();
    }//GEN-LAST:event_btn_NextActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ItemsDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ItemsDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ItemsDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ItemsDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ItemsDetail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private app.swing.Button btn_Next;
    private app.swing.Button btn_Pre;
    private app.swing.Button button1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lbl_Color;
    private javax.swing.JLabel lbl_ItemID;
    private javax.swing.JLabel lbl_Material;
    private javax.swing.JLabel lbl_Name;
    private javax.swing.JLabel lbl_Price;
    private javax.swing.JLabel lbl_Size;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel slideImg;
    // End of variables declaration//GEN-END:variables
}

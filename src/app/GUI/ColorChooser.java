
package app.GUI;

import java.awt.Color;
import javax.swing.JLabel;

public class ColorChooser extends javax.swing.JFrame {

    private JLabel lbl;
    
    
    public ColorChooser() {
        initComponents();
    }
    
    public ColorChooser(JLabel lbl) {
        initComponents();
        this.lbl = lbl;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        ColorChooser = new javax.swing.JColorChooser();
        btn_Chooser = new app.swing.Button();
        button2 = new app.swing.Button();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 2, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("Color Chooser");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 280, 30));
        getContentPane().add(ColorChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 530, 280));

        btn_Chooser.setText("Chooser");
        btn_Chooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChooserActionPerformed(evt);
            }
        });
        getContentPane().add(btn_Chooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 90, -1));

        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/x.png"))); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        getContentPane().add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 40, 30));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor 2.jpg"))); // NOI18N
        background.setMinimumSize(new java.awt.Dimension(523, 344));
        background.setPreferredSize(new java.awt.Dimension(523, 344));
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 280));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChooserActionPerformed
        this.lbl.setBackground(this.ColorChooser.getColor());
        dispose();
    }//GEN-LAST:event_btn_ChooserActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        dispose();
    }//GEN-LAST:event_button2ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ColorChooser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JColorChooser ColorChooser;
    private javax.swing.JLabel background;
    private app.swing.Button btn_Chooser;
    private app.swing.Button button2;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}

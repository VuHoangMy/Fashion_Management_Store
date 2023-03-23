package app.component;

import app.CommonFuncion.CommonFunction;
import app.event.EventMenu;
import app.swing.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuStaffForm extends javax.swing.JPanel {

    private EventMenu event;

    public MenuStaffForm() {
        initComponents();
        setActionForButton(btn_Staff, 0);
        setActionForButton(btn_Timekeeping, 1);
        setActionForButton(btn_Payroll, 2);

        btn_Staff.setFont(new Font("Yu Gothic", Font.PLAIN, 18));
        btn_Timekeeping.setFont(new Font("Yu Gothic", Font.PLAIN, 18));
        btn_Payroll.setFont(new Font("Yu Gothic", Font.PLAIN, 18));

        this.btn_Staff.setBackgroundColorBottom(new Color(0, 0, 0, 100));
        this.btn_Timekeeping.setBackgroundColorBottom(new Color(0, 0, 0, 100));
        this.btn_Payroll.setBackgroundColorBottom(new Color(0, 0, 0, 100));
    }

    public void initMenu(EventMenu event) {
        this.event = event;
    }

    public void setActionForButton(Button b, int index) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event.selectMenu(index);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_Payroll = new app.swing.Button();
        btn_Back = new app.swing.Button();
        btn_Timekeeping = new app.swing.Button();
        btn_Staff = new app.swing.Button();
        jLabel1 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1800, 900));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Payroll.setForeground(new java.awt.Color(255, 255, 0));
        btn_Payroll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/money.png"))); // NOI18N
        btn_Payroll.setText("  Payroll");
        add(btn_Payroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 490, 205, 120));

        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        btn_Timekeeping.setForeground(new java.awt.Color(102, 255, 255));
        btn_Timekeeping.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Count.png"))); // NOI18N
        btn_Timekeeping.setText("  Timekeeping");
        add(btn_Timekeeping, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 490, 220, 120));

        btn_Staff.setForeground(new java.awt.Color(255, 153, 0));
        btn_Staff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/staffIcon.png"))); // NOI18N
        btn_Staff.setText("  Employee");
        add(btn_Staff, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 340, 490, 120));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Employee manager");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_BackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.swing.Button btn_Back;
    private app.swing.Button btn_Payroll;
    private app.swing.Button btn_Staff;
    private app.swing.Button btn_Timekeeping;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}

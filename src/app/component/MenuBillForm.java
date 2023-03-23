package app.component;

import app.CommonFuncion.CommonFunction;
import app.event.EventMenu;
import app.swing.Button;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBillForm extends javax.swing.JPanel {

    private EventMenu event;

    public MenuBillForm() {
        initComponents();
        setActionForButton(btn_ImportGoods, 0);
        setActionForButton(btn_Sell, 1);
        
        // setting font for button menu
        this.btn_ImportGoods.setFont(new Font("Yu Gothic", Font.PLAIN, 18));
        this.btn_Sell.setFont(new Font("Yu Gothic", Font.PLAIN, 18));
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

        btn_Back = new app.swing.Button();
        formName = new javax.swing.JLabel();
        btn_ImportGoods = new app.swing.Button();
        btn_Sell = new app.swing.Button();

        setMinimumSize(new java.awt.Dimension(1800, 900));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 80, -1));

        formName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        formName.setForeground(new java.awt.Color(255, 255, 255));
        formName.setText("Bill Management");
        add(formName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, -1, -1));

        btn_ImportGoods.setForeground(new java.awt.Color(153, 0, 51));
        btn_ImportGoods.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/SupplierIcon.png"))); // NOI18N
        btn_ImportGoods.setText("Import Invoice");
        btn_ImportGoods.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_ImportGoods.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add(btn_ImportGoods, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 190, 110));

        btn_Sell.setForeground(new java.awt.Color(255, 255, 0));
        btn_Sell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/BillIcon.png"))); // NOI18N
        btn_Sell.setText("Sales Invoice");
        btn_Sell.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Sell.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add(btn_Sell, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 490, 190, 110));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_BackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.swing.Button btn_Back;
    private app.swing.Button btn_ImportGoods;
    private app.swing.Button btn_Sell;
    private javax.swing.JLabel formName;
    // End of variables declaration//GEN-END:variables
}

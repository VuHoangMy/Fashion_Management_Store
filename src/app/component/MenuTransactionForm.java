package app.component;

import app.CommonFuncion.CommonFunction;
import app.event.EventMenu;
import app.swing.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuTransactionForm extends javax.swing.JPanel {

    private EventMenu event;

    public MenuTransactionForm() {
        initComponents();
        setOpaque(false);
        setActionForButton(btn_Sell, 0);
        setActionForButton(btn_Import, 1);
        setActionForButton(btn_Report, 2);
        
        this.btn_Sell.setFont(new Font("Yu Gothic", Font.PLAIN, 18));
        this.btn_Import.setFont(new Font("Yu Gothic", Font.PLAIN, 18));
        this.btn_Report.setFont(new Font("Yu Gothic", Font.PLAIN, 18));

        this.btn_Sell.setBackgroundColorBottom(new Color(0,0,0,100));
        this.btn_Import.setBackgroundColorBottom(new Color(0,0,0,100));
        this.btn_Report.setBackgroundColorBottom(new Color(0,0,0,100));
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

        btn_Import = new app.swing.Button();
        btn_Sell = new app.swing.Button();
        btn_Report = new app.swing.Button();
        btn_Back = new app.swing.Button();
        formName = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Import.setForeground(new java.awt.Color(0, 255, 0));
        btn_Import.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/import.png"))); // NOI18N
        btn_Import.setText("Import");
        btn_Import.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Import.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add(btn_Import, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 445, 180, 150));

        btn_Sell.setForeground(new java.awt.Color(255, 255, 0));
        btn_Sell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/SaleIcon.png"))); // NOI18N
        btn_Sell.setText("Sell");
        btn_Sell.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Sell.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add(btn_Sell, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 303, 180, 150));

        btn_Report.setForeground(new java.awt.Color(255, 0, 0));
        btn_Report.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/ReportIcon.png"))); // NOI18N
        btn_Report.setText("Report");
        btn_Report.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Report.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add(btn_Report, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 597, 180, 150));

        btn_Back.setForeground(new java.awt.Color(51, 51, 51));
        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 65, -1, 36));

        formName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        formName.setForeground(new java.awt.Color(255, 255, 255));
        formName.setText("Transaction Management");
        add(formName, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 49, -1, 60));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed

        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_BackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.swing.Button btn_Back;
    private app.swing.Button btn_Import;
    private app.swing.Button btn_Report;
    private app.swing.Button btn_Sell;
    private javax.swing.JLabel formName;
    // End of variables declaration//GEN-END:variables
}

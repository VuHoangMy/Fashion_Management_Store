
package app.swing;

public class ButtonClose extends javax.swing.JPanel {

    public ButtonClose() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_Close = new javax.swing.JLabel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(24, 24));

        btn_Close.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_Close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/x.png"))); // NOI18N
        btn_Close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_CloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_CloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_CloseMouseExited(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_Close)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_Close)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_CloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_CloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btn_CloseMouseClicked

    private void btn_CloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_CloseMouseEntered
        btn_Close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/x_hover.png"))); // NOI18N
    }//GEN-LAST:event_btn_CloseMouseEntered

    private void btn_CloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_CloseMouseExited
        btn_Close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/x.png"))); // NOI18N
    }//GEN-LAST:event_btn_CloseMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_Close;
    // End of variables declaration//GEN-END:variables
}

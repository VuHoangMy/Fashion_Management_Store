package app.swing;

import java.awt.Color;
import javax.swing.JFrame;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Message extends javax.swing.JDialog {

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    private boolean ok;
    private final Animator animator;
    private boolean show = true;

    public Message(JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        setOpacity(0f);
        getContentPane().setBackground(Color.WHITE);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (show) {
                    setOpacity(fraction);
                } else {
                    setOpacity(1f - fraction);
                }
            }

            @Override
            public void end() {
                if (show == false) {
                    setVisible(false);
                }
            }

        };
        animator = new Animator(200, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
    }

    public void showMessage(String message) {
        lbMessage.setText(message);
        animator.start();
        setVisible(true);
    }
    
    private void closeMenu() {
        if (animator.isRunning()) {
            animator.stop();
        }
        show = false;
        animator.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_OK = new app.swing.Button();
        btn_Cancel = new app.swing.Button();
        jLabel1 = new javax.swing.JLabel();
        lbMessage = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(550, 170));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_OK.setForeground(new java.awt.Color(51, 255, 51));
        btn_OK.setText("OK");
        btn_OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_OKActionPerformed(evt);
            }
        });
        add(btn_OK, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, 160, -1));

        btn_Cancel.setText("Cancel");
        btn_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelActionPerformed(evt);
            }
        });
        add(btn_Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, 80, -1));

        jLabel1.setBackground(new java.awt.Color(0, 204, 204));
        jLabel1.setOpaque(true);
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 550, 60));

        lbMessage.setFont(new java.awt.Font("Yu Gothic UI", 2, 18)); // NOI18N
        lbMessage.setForeground(new java.awt.Color(153, 255, 51));
        lbMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/information.png"))); // NOI18N
        lbMessage.setText("jLabel3");
        add(lbMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(76, 44, 421, 41));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_OKActionPerformed
        ok = true;
        closeMenu();
    }//GEN-LAST:event_btn_OKActionPerformed

    private void btn_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelActionPerformed
        closeMenu();
    }//GEN-LAST:event_btn_CancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.swing.Button btn_Cancel;
    private app.swing.Button btn_OK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbMessage;
    // End of variables declaration//GEN-END:variables
}

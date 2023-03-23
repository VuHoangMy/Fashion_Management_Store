package app.GUI;

import app.BUS.AccountList;
import app.BUS.StaffList;
import app.CommonFuncion.CommonFunction;
import app.CommonFuncion.CurrentUserLogin;
import app.DTO.Staff;
import javax.swing.JOptionPane;

public class ChangePassword extends javax.swing.JFrame {

    private Staff staff;

    public ChangePassword() {
        this.staff = CurrentUserLogin.getCurrentStaff().getCurrentuserLogin();
        initComponents();
        this.txt_Username.setText(this.staff.getName());
    }

    private boolean isValidateNewPWD() {
        
        return String.valueOf(this.txt_NewPWD.getPassword()).equals(String.valueOf(this.txt_ConfirmPWD.getPassword()));
    }

    private boolean isCorrectOldPWD() {
        return String.valueOf(this.txt_OldPWD.getPassword()).equals(staff.getAccount().getPassword());
    }

    private void eventChangePassword() {
        
        if (!isValidateNewPWD() || !isCorrectOldPWD()) {
            JOptionPane.showMessageDialog(null, "Kiem tra lai mat khau!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean result = CommonFunction.isConfirmDialog("WARNING", "Xac nhan thay do mat khau!");
        if(!result){
            return;
        }

        StaffList staffList = new StaffList();
        staffList.initFile();
        staffList.readStaffFromFile();

        AccountList accountList = new AccountList();
        accountList.initFile();
        accountList.readAccountFromFile();

        String newPWD = String.valueOf(this.txt_NewPWD.getPassword());

        accountList.findAccount(staff.getAccount().getUsername(), String.valueOf(this.txt_OldPWD.getPassword())).setPassword(newPWD);
        staffList.findStaffByID(staff.getID()).getAccount().setPassword(String.valueOf(newPWD));

        staffList.writeStaffIntoFile();
        accountList.writeAcountIntoFile();
        dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pictureBox1 = new app.swing.PictureBox();
        jLabel1 = new javax.swing.JLabel();
        btn_Confirm = new app.swing.Button();
        btn_Cancel = new app.swing.Button();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_ConfirmPWD = new javax.swing.JPasswordField();
        txt_NewPWD = new javax.swing.JPasswordField();
        txt_OldPWD = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        txt_Username = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 615));
        setUndecorated(true);

        pictureBox1.setImage(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor 2.jpg"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Change Password");
        pictureBox1.add(jLabel1);
        jLabel1.setBounds(170, 50, 250, 32);

        btn_Confirm.setForeground(new java.awt.Color(255, 255, 0));
        btn_Confirm.setText("Confirm");
        btn_Confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ConfirmActionPerformed(evt);
            }
        });
        pictureBox1.add(btn_Confirm);
        btn_Confirm.setBounds(340, 520, 90, 40);

        btn_Cancel.setForeground(new java.awt.Color(204, 0, 0));
        btn_Cancel.setText("Cancel");
        btn_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelActionPerformed(evt);
            }
        });
        pictureBox1.add(btn_Cancel);
        btn_Cancel.setBounds(450, 520, 90, 40);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Confirm Password");
        pictureBox1.add(jLabel2);
        jLabel2.setBounds(110, 430, 170, 22);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("New Password");
        pictureBox1.add(jLabel3);
        jLabel3.setBounds(110, 330, 114, 22);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Old Password");
        pictureBox1.add(jLabel4);
        jLabel4.setBounds(110, 230, 140, 30);

        txt_ConfirmPWD.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_ConfirmPWD.setForeground(new java.awt.Color(0, 51, 204));
        txt_ConfirmPWD.setText("jPasswordField1");
        txt_ConfirmPWD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_ConfirmPWD.setOpaque(false);
        pictureBox1.add(txt_ConfirmPWD);
        txt_ConfirmPWD.setBounds(110, 460, 380, 40);

        txt_NewPWD.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_NewPWD.setForeground(new java.awt.Color(0, 51, 204));
        txt_NewPWD.setText("jPasswordField1");
        txt_NewPWD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_NewPWD.setOpaque(false);
        pictureBox1.add(txt_NewPWD);
        txt_NewPWD.setBounds(110, 360, 380, 40);

        txt_OldPWD.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_OldPWD.setForeground(new java.awt.Color(0, 51, 204));
        txt_OldPWD.setText("jPasswordField1");
        txt_OldPWD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_OldPWD.setOpaque(false);
        pictureBox1.add(txt_OldPWD);
        txt_OldPWD.setBounds(110, 270, 380, 40);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Username");
        pictureBox1.add(jLabel5);
        jLabel5.setBounds(110, 130, 140, 30);

        txt_Username.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Username.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txt_Username.setForeground(new java.awt.Color(0, 51, 204));
        txt_Username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_Username.setOpaque(false);
        pictureBox1.add(txt_Username);
        txt_Username.setBounds(110, 180, 380, 40);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pictureBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pictureBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelActionPerformed
        dispose();
    }//GEN-LAST:event_btn_CancelActionPerformed

    private void btn_ConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ConfirmActionPerformed
        eventChangePassword();
    }//GEN-LAST:event_btn_ConfirmActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.swing.Button btn_Cancel;
    private app.swing.Button btn_Confirm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private app.swing.PictureBox pictureBox1;
    private javax.swing.JPasswordField txt_ConfirmPWD;
    private javax.swing.JPasswordField txt_NewPWD;
    private javax.swing.JPasswordField txt_OldPWD;
    private javax.swing.JTextField txt_Username;
    // End of variables declaration//GEN-END:variables
}

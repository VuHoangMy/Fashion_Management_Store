package app.GUI;

import app.BUS.AccountList;
import app.BUS.StaffList;
import app.CommonFuncion.CurrentUserLogin;
import app.DTO.Account;
import app.DTO.Staff;
import app.main.Main;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();
        btn_Login.setFont(new Font("Segoe UI", Font.BOLD, 20));
        setIconImage(new ImageIcon(new File("src\\app\\Icons\\AppFashionIcon.png").getAbsolutePath()).getImage());
    }

    private boolean isValidateDataInput(String userName, String pwd) {
        if (userName.isEmpty() || pwd.isEmpty()) {
            String text = userName.isEmpty() ? "Please enter username." : "Please enter password.";
            lbl_Notificate.setText(text);
            return false;
        }
        return true;
    }
    
    private boolean isAccountExist(String userName, String pwd){
        AccountList accountList = new AccountList();
        accountList.initFile();
        accountList.readAccountFromFile();
        Account result = accountList.findAccount(userName, pwd);
        return result == null ? false : true;
    }
    
    private Staff initCurrentUser(String userName, String pwd){
        StaffList staffList = new StaffList();
        staffList.initFile();
        staffList.readStaffFromFile();
        Staff currentStaff = staffList.findStaffByAccount(new Account(userName, pwd));
        
        
        
        CurrentUserLogin.getCurrentStaff().setCurrentUserLogin(currentStaff);
        if(!currentStaff.getStatus() || currentStaff.getRole().equals("Seller")){
            lbl_Notificate.setText("Dang nhap that bai.");
            return null;
        }
        return currentStaff;
    }

    private void eventLogin() {
        String userName = this.txt_Username.getText();
        String pwd = String.valueOf(this.txt_pwd.getPassword());
        if (!isValidateDataInput(userName, pwd) || !isAccountExist(userName, pwd)) {
            lbl_Notificate.setText("Login failed.");
            return;
        }
        
        Staff result = initCurrentUser(userName, pwd);
        if(result != null){
            eventSwitchToMainPage();
        }
        
    }
    
    private void eventSwitchToMainPage(){
        Main main = new Main();
        main.initMain();
        main.setVisible(true);
        dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_Login = new app.swing.Button();
        btn_Exit = new javax.swing.JLabel();
        txt_Username = new javax.swing.JTextField();
        txt_pwd = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbl_Notificate = new javax.swing.JLabel();
        lbl_NotifiUsername = new javax.swing.JLabel();
        BackgroundLayer = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(new ImageIcon(new File("app/Icons/AppFashionIcon.png").getAbsolutePath()).getImage());
        setLocationByPlatform(true);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1200, 650));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Login.setForeground(new java.awt.Color(94, 101, 188));
        btn_Login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/login.png"))); // NOI18N
        btn_Login.setText("   Login");
        btn_Login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_LoginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_LoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_LoginMouseExited(evt);
            }
        });
        getContentPane().add(btn_Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 480, 160, 60));

        btn_Exit.setBackground(new java.awt.Color(0, 0, 0,1));
        btn_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/x.png"))); // NOI18N
        btn_Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ExitMouseExited(evt);
            }
        });
        getContentPane().add(btn_Exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 30, -1, -1));

        txt_Username.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Username.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_Username.setForeground(new java.awt.Color(255, 255, 255));
        txt_Username.setText("Username");
        txt_Username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 255)));
        txt_Username.setOpaque(false);
        txt_Username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_UsernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_UsernameFocusLost(evt);
            }
        });
        getContentPane().add(txt_Username, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 290, 390, 50));

        txt_pwd.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_pwd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_pwd.setForeground(new java.awt.Color(255, 255, 255));
        txt_pwd.setText("Password");
        txt_pwd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 255)));
        txt_pwd.setOpaque(false);
        txt_pwd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_pwdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_pwdFocusLost(evt);
            }
        });
        getContentPane().add(txt_pwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 390, 390, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/profile_64px.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 280, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/lock_64px.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 380, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/store.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 130, -1));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Fashion Store");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, -1, -1));

        lbl_Notificate.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        lbl_Notificate.setForeground(new java.awt.Color(255, 51, 51));
        getContentPane().add(lbl_Notificate, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 490, 210, 40));

        lbl_NotifiUsername.setForeground(new java.awt.Color(255, 51, 51));
        getContentPane().add(lbl_NotifiUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 360, 260, 10));

        BackgroundLayer.setBackground(new java.awt.Color(0, 153, 255));
        BackgroundLayer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/Background1.png"))); // NOI18N
        getContentPane().add(BackgroundLayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btn_ExitMouseClicked

    private void btn_ExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ExitMouseEntered
        btn_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/x_hover.png"))); // NOI18N
    }//GEN-LAST:event_btn_ExitMouseEntered

    private void btn_ExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ExitMouseExited
        btn_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/x.png"))); // NOI18N
    }//GEN-LAST:event_btn_ExitMouseExited

    private void txt_UsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_UsernameFocusGained
        if (this.txt_Username.getText().equals("Username")) {
            this.txt_Username.setText("");
        }
        this.txt_Username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(46, 201, 254)));
    }//GEN-LAST:event_txt_UsernameFocusGained

    private void txt_pwdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_pwdFocusGained
        if (new String(this.txt_pwd.getPassword()).equals("Password")) {
            this.txt_pwd.setText("");
        }
        this.txt_pwd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(46, 201, 254)));
    }//GEN-LAST:event_txt_pwdFocusGained

    private void txt_UsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_UsernameFocusLost
        if (this.txt_Username.getText().equals("")) {
            this.txt_Username.setText("Username");
        }
        txt_Username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 255)));
    }//GEN-LAST:event_txt_UsernameFocusLost

    private void txt_pwdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_pwdFocusLost
        if (new String(this.txt_pwd.getPassword()).equals("")) {
            this.txt_pwd.setText("Password");
        }
        txt_pwd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 255)));
    }//GEN-LAST:event_txt_pwdFocusLost


    private void btn_LoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_LoginMouseEntered
        setButtonHover(btn_Login, "LoginHover");
        setForegroundButton(btn_Login, new Color(7, 235, 254));
    }//GEN-LAST:event_btn_LoginMouseEntered

    private void btn_LoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_LoginMouseExited
        setButtonHover(btn_Login, "Login");
        setForegroundButton(btn_Login, new Color(94, 101, 188));
    }//GEN-LAST:event_btn_LoginMouseExited

    private void btn_LoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_LoginMouseClicked
        eventLogin();
        
    }//GEN-LAST:event_btn_LoginMouseClicked

    private void setButtonHover(JButton button, String iconName) {
        button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/" + iconName + ".png"))); // NOI18N
    }

    private void setForegroundButton(JButton button, Color color) {
        button.setForeground(color);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackgroundLayer;
    private javax.swing.JLabel btn_Exit;
    private app.swing.Button btn_Login;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lbl_NotifiUsername;
    private javax.swing.JLabel lbl_Notificate;
    private javax.swing.JTextField txt_Username;
    private javax.swing.JPasswordField txt_pwd;
    // End of variables declaration//GEN-END:variables
}

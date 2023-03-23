package app.component;

import app.BUS.StaffList;
import app.CommonFuncion.CommonFunction;
import app.CommonFuncion.CurrentUserLogin;
import app.DTO.Staff;
import app.GUI.ChangePassword;
import java.awt.Font;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ProfileForm extends javax.swing.JPanel {

    private Staff curentStaff;
    private File imageProfile = null;

    public ProfileForm() {
        initComponents();
        setStyleButton();
    }

    public void loadDataToComponent(){
        this.curentStaff = CurrentUserLogin.getCurrentStaff().getCurrentuserLogin();
        this.txt_Address.setText(curentStaff.getAddress());
        this.txt_Email.setText(curentStaff.getEmail());
        this.txt_Name.setText(curentStaff.getName());
        this.txt_Phonenumber.setText(curentStaff.getPhonenumber());
        this.txt_Salary.setText(CommonFunction.formatMoney(curentStaff.getSalary()));
        this.txt_Username.setText(curentStaff.getAccount().getUsername());
        if (curentStaff.getGender().equals("Male")) {
            this.radio_Male.setSelected(true);
        } else {
            this.radio_Female.setSelected(true);
        }

        try {
            this.DChooser_Birthday.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(curentStaff.getBirthday().toString()));
        } catch (Exception e) {}
        this.txt_TimeIn.setText(CommonFunction.formatLocalTimeToString(curentStaff.getInWorkAt()));;
        this.txt_TimeOut.setText(CommonFunction.formatLocalTimeToString(curentStaff.getGetsOffAt()));
        
        this.txt_Role.setText(curentStaff.getRole());
        String filePath = curentStaff.getImageProfile().getAbsolutePath();
        this.lbl_PictureProfile.setIcon(new ImageIcon(filePath));
    }
    
    private void setStyleButton(){
        this.btn_ClearAll.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Refresh.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Update.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_EditAccount.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
    }

    private void chooserPicture() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Image", "jpg", "png");
        chooser.setFileFilter(extensionFilter);
        chooser.setDialogTitle("Choose Pictures");
        chooser.setMultiSelectionEnabled(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            this.imageProfile = file;
            lbl_PictureProfile.setIcon(new ImageIcon(file.getAbsolutePath()));
            revalidate();
            repaint();
        }
    }
    
    private boolean isValidateDataInput(){
        if(this.txt_Name.getText().isEmpty() || this.txt_Phonenumber.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Yeu cau nhap du thong tin can thiet!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void eventRefresh(){
        loadDataToComponent();
    }
    
    private void eventUpdateInfo(){
        boolean result = CommonFunction.isConfirmDialog("Confirm", "Xac nhan thay doi du lieu!");
        if(!result){
            return;
        }
        
        StaffList staffList = new StaffList();
        staffList.initFile();
        staffList.readStaffFromFile();
        
        staffList.getStaffList().remove(staffList.findStaffByID(curentStaff.getID()));
        
        
        
        String gender = this.radio_Male.isSelected() ? "Male" : "Female";
        LocalDate birthday = CommonFunction.convertToLocalDateViaSqlDate(this.DChooser_Birthday.getDate());
        Staff staffUpdate = new Staff(curentStaff.getID(), curentStaff.getRole(), this.txt_Name.getText(), gender,
                birthday, this.txt_Address.getText(), this.txt_Phonenumber.getText(), this.txt_Email.getText(), 
                curentStaff.getSalary(), curentStaff.getStatus(), curentStaff.getAccount());
        
        if(this.imageProfile != null){
            staffUpdate.setImageProfile(this.imageProfile);
        }else{
            staffUpdate.setImageProfile(curentStaff.getImageProfile());
        }
        
        staffUpdate.setBillImportGoodses(curentStaff.getBillImportGoodses());
        
        staffList.addNewStaff(staffUpdate);
        
        Collections.sort(staffList.getStaffList(), Staff.staffIDCompareASC);
        
        staffList.writeStaffIntoFile();
        CurrentUserLogin.getCurrentStaff().setCurrentUserLogin(staffUpdate);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnG_Gender = new javax.swing.ButtonGroup();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_Name = new javax.swing.JTextField();
        txt_Email = new javax.swing.JTextField();
        txt_Address = new javax.swing.JTextField();
        txt_Phonenumber = new javax.swing.JTextField();
        txt_pwd = new javax.swing.JLabel();
        txt_Username = new javax.swing.JLabel();
        radio_Male = new javax.swing.JRadioButton();
        radio_Female = new javax.swing.JRadioButton();
        DChooser_Birthday = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        verticalbar = new javax.swing.JLabel();
        btn_Back = new app.swing.Button();
        btn_EditAccount = new app.swing.Button();
        btn_Update = new app.swing.Button();
        btn_ChoosePicture = new app.swing.Button();
        btn_Refresh = new app.swing.Button();
        btn_ClearAll = new app.swing.Button();
        lbl_PictureProfile = new app.swing.ImageAvatar();
        txt_Role = new javax.swing.JLabel();
        txt_TimeIn = new javax.swing.JLabel();
        txt_TimeOut = new javax.swing.JLabel();
        txt_Salary = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("Name(*)");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 200, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("Role");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 490, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Gender");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 490, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("Phonenumber(*)");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 350, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 153));
        jLabel7.setText("Email");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 610, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 153));
        jLabel8.setText("Salary");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 600, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 153));
        jLabel9.setText("Birthday");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 190, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 153));
        jLabel10.setText("Time Out");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1330, 280, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 153));
        jLabel6.setText("Username");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 470, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 153));
        jLabel11.setText("Password");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 600, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 51, 153));
        jLabel16.setText("Address");
        add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 340, -1, -1));

        txt_Name.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Name.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        txt_Name.setForeground(new java.awt.Color(255, 255, 255));
        txt_Name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
        txt_Name.setOpaque(false);
        add(txt_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 240, 260, 30));

        txt_Email.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Email.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        txt_Email.setForeground(new java.awt.Color(255, 255, 255));
        txt_Email.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
        txt_Email.setOpaque(false);
        add(txt_Email, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 650, 260, 30));

        txt_Address.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Address.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        txt_Address.setForeground(new java.awt.Color(255, 255, 255));
        txt_Address.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
        txt_Address.setOpaque(false);
        add(txt_Address, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 400, 260, 30));

        txt_Phonenumber.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Phonenumber.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        txt_Phonenumber.setForeground(new java.awt.Color(255, 255, 255));
        txt_Phonenumber.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
        txt_Phonenumber.setOpaque(false);
        add(txt_Phonenumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 400, 260, 30));

        txt_pwd.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        txt_pwd.setForeground(new java.awt.Color(255, 255, 255));
        txt_pwd.setText("*****************");
        txt_pwd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
        add(txt_pwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 660, 260, -1));

        txt_Username.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        txt_Username.setForeground(new java.awt.Color(255, 255, 255));
        txt_Username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
        add(txt_Username, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 520, 260, 30));

        btnG_Gender.add(radio_Male);
        radio_Male.setSelected(true);
        radio_Male.setText("Male");
        add(radio_Male, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 540, -1, -1));

        btnG_Gender.add(radio_Female);
        radio_Female.setText("Female");
        add(radio_Female, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 540, -1, -1));

        DChooser_Birthday.setOpaque(false);
        add(DChooser_Birthday, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 240, 220, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Choose Picture");
        add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Working Time");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1310, 140, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Edit Account");
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1340, 400, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Edit Staff");
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, 40));

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 51, 153));
        jLabel14.setText("Time In");
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1330, 190, -1, -1));

        verticalbar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 2, 0, 0, new java.awt.Color(0, 0, 0)));
        add(verticalbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 190, 20, 450));

        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        btn_EditAccount.setText("Change Account");
        btn_EditAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditAccountActionPerformed(evt);
            }
        });
        add(btn_EditAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 740, 160, 50));

        btn_Update.setForeground(new java.awt.Color(255, 255, 0));
        btn_Update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/EditHover.png"))); // NOI18N
        btn_Update.setText("Update");
        btn_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UpdateActionPerformed(evt);
            }
        });
        add(btn_Update, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 740, 140, 50));

        btn_ChoosePicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/plus.png"))); // NOI18N
        btn_ChoosePicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChoosePictureActionPerformed(evt);
            }
        });
        add(btn_ChoosePicture, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 430, -1, -1));

        btn_Refresh.setForeground(new java.awt.Color(0, 255, 255));
        btn_Refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Refresh.png"))); // NOI18N
        btn_Refresh.setText("Refresh");
        btn_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefreshActionPerformed(evt);
            }
        });
        add(btn_Refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 740, 130, -1));

        btn_ClearAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/DeleteAll.png"))); // NOI18N
        btn_ClearAll.setText("Clear All");
        btn_ClearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ClearAllActionPerformed(evt);
            }
        });
        add(btn_ClearAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 740, 120, -1));

        lbl_PictureProfile.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        add(lbl_PictureProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 260, 190));

        txt_Role.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_Role.setForeground(new java.awt.Color(255, 255, 255));
        txt_Role.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
        add(txt_Role, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 530, 260, 30));

        txt_TimeIn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_TimeIn.setForeground(new java.awt.Color(0, 204, 0));
        txt_TimeIn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
        add(txt_TimeIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1330, 220, 260, 30));

        txt_TimeOut.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_TimeOut.setForeground(new java.awt.Color(0, 204, 0));
        txt_TimeOut.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
        add(txt_TimeOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(1330, 320, 260, 30));

        txt_Salary.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_Salary.setForeground(new java.awt.Color(255, 255, 0));
        txt_Salary.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txt_Salary.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
        add(txt_Salary, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 646, 260, 30));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor5.jpg"))); // NOI18N
        add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ChoosePictureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChoosePictureActionPerformed
        chooserPicture();
    }//GEN-LAST:event_btn_ChoosePictureActionPerformed

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_BackActionPerformed

    private void btn_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UpdateActionPerformed
        eventUpdateInfo();
    }//GEN-LAST:event_btn_UpdateActionPerformed

    private void btn_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefreshActionPerformed
        if(isValidateDataInput()){
            eventRefresh();
        }
    }//GEN-LAST:event_btn_RefreshActionPerformed

    private void btn_ClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ClearAllActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ClearAllActionPerformed

    private void btn_EditAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditAccountActionPerformed
        ChangePassword form = new ChangePassword();
        form.setVisible(true);
    }//GEN-LAST:event_btn_EditAccountActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private com.toedter.calendar.JDateChooser DChooser_Birthday;
    private javax.swing.ButtonGroup btnG_Gender;
    private app.swing.Button btn_Back;
    private app.swing.Button btn_ChoosePicture;
    private app.swing.Button btn_ClearAll;
    private app.swing.Button btn_EditAccount;
    private app.swing.Button btn_Refresh;
    private app.swing.Button btn_Update;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private app.swing.ImageAvatar lbl_PictureProfile;
    private javax.swing.JRadioButton radio_Female;
    private javax.swing.JRadioButton radio_Male;
    private javax.swing.JTextField txt_Address;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_Name;
    private javax.swing.JTextField txt_Phonenumber;
    private javax.swing.JLabel txt_Role;
    private javax.swing.JLabel txt_Salary;
    private javax.swing.JLabel txt_TimeIn;
    private javax.swing.JLabel txt_TimeOut;
    private javax.swing.JLabel txt_Username;
    private javax.swing.JLabel txt_pwd;
    private javax.swing.JLabel verticalbar;
    // End of variables declaration//GEN-END:variables

}

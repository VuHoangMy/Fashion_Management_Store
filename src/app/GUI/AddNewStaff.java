package app.GUI;

import app.BUS.AccountList;
import app.BUS.StaffList;
import app.CommonFuncion.CommonFunction;
import app.CommonFuncion.QRCodeGenerator;
import app.DTO.Account;
import app.DTO.Staff;
import app.component.StaffForm;
import java.awt.Font;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddNewStaff extends javax.swing.JFrame {

    private StaffForm frame;
    private AccountList accountLists;
    private File profilePicture = new File("src/app/Images/Account IMG.png");

    private final int widthProfilePic = 180, heightProFilePic = 180;

    private Staff staffEdit;

    public AddNewStaff() { // CanDelete
        initComponents();
        loadDefaultProfilePicture();
    }

    public AddNewStaff(Staff staff, StaffForm frame) { // Use for Edit Staff | Edit Event
        initComponents();
        this.staffEdit = staff;
        this.frame = frame;
        loadDataToComponent(staff); // load data to Component
        setStyleComponentEditForm();
        setStyleButton();
    }

    public AddNewStaff(StaffForm frame) { // Use for UpDate Table in StaffForm | Add event
        initComponents();
        this.staffEdit = null;
        this.frame = frame;
        remove(this.btn_Update);
        loadDefaultProfilePicture();
        setStyleButton();
    }

    private void setStyleComponentEditForm() {
        remove(this.btn_Add);
        this.txt_Pwd.setEditable(false);
        this.txt_Username.setEditable(false);
    }

    private boolean isAccExist(String username, String pwd) {
        accountLists = new AccountList();
        accountLists.initFile();
        accountLists.readAccountFromFile();
        return accountLists.findAccount(username, pwd) == null ? false : true;
    }

    private void loadDefaultProfilePicture() {
        this.lbl_ImageProfile.setIcon(CommonFunction.resizeImage(new ImageIcon("src/app/Images/Account IMG.png"), widthProfilePic, heightProFilePic));
    }

    private void setStyleButton() {
        this.btn_Add.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Cancel.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Update.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Clear.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
    }

    private void clearAll() {
        this.txt_Address.setText("");
        this.txt_Email.setText("");
        this.txt_Name.setText("");
        this.txt_Phonenumber.setText("");
        this.txt_Pwd.setText("");
        this.txt_Salary.setText("");
        this.txt_Username.setText("");
        this.radio_Male.setSelected(true);
        this.radio_StillWork.setSelected(true);
        this.cbox_Role.setSelectedIndex(0);
    }

    private void loadDataToComponent(Staff editStaff) {
        this.txt_Address.setText(editStaff.getAddress());
        this.txt_Email.setText(editStaff.getEmail());
        this.txt_Name.setText(editStaff.getName());
        this.txt_Phonenumber.setText(editStaff.getPhonenumber());
        this.txt_Pwd.setText(editStaff.getAccount().getPassword());
        this.txt_Salary.setText(CommonFunction.formatMoney(editStaff.getSalary()));
        this.txt_Username.setText(editStaff.getAccount().getUsername());
        if (editStaff.getGender().equals("Male")) {
            this.radio_Male.setSelected(true);
        } else {
            this.radio_Female.setSelected(true);
        }

        if (editStaff.getStatus()) {
            this.radio_StillWork.setSelected(true);
        } else {
            this.radio_isQuit.setSelected(true);
        }

        this.cbox_Role.setSelectedItem(editStaff.getRole());
        File filePicture = editStaff.getImageProfile();
        this.lbl_ImageProfile.setIcon(CommonFunction.resizeImage(new ImageIcon(filePicture.getAbsolutePath()), widthProfilePic, heightProFilePic));
    }

    private Staff createNewStaff() {
        String name = this.txt_Name.getText();
        LocalDate birthday = CommonFunction.convertToLocalDateViaSqlDate(this.dateChooser.getDate());
        String username = this.txt_Username.getText();
        String pwd = this.txt_Pwd.getText();
        String email = this.txt_Email.getText().isEmpty() ? "" : this.txt_Email.getText();
        double salary = CommonFunction.formatStringToMoney(this.txt_Salary.getText());
        String phonenumber = this.txt_Phonenumber.getText();
        String address = this.txt_Address.getText().isEmpty() ? "" : this.txt_Address.getText();
        String gender = radio_Male.isSelected() ? "Male" : "Female";
        String role = this.cbox_Role.getSelectedItem().toString();
        boolean status = this.radio_StillWork.isSelected() ? true : false;
        File profilePicture = this.profilePicture;

        LocalTime inWorkAt = LocalTime.parse(this.txt_TimeGetIn.getText());
        LocalTime getsOffAt = LocalTime.parse(this.txt_TimeGetOut.getText());

        System.out.println("Check exist: " + isAccExist(username, pwd));
        if (isAccExist(username, pwd)) {

            return null;
        }

        Account acc = new Account(username, pwd);
        accountLists.appendAccountIntoFile(acc);
        Staff staff = new Staff("", role, name, gender, birthday, address, phonenumber, email, salary, status, acc);
        staff.setImageProfile(profilePicture);
        staff.setInWorkAt(inWorkAt);
        staff.setGetsOffAt(getsOffAt);
        return staff;

    }

    private void eventSaveData() {
        if (!validateDataInput()) {
            return; // if data input not validate
        }
        Staff newS = createNewStaff();
        if (newS == null) {
            JOptionPane.showMessageDialog(this, "Account Exist", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            StaffList staffLists = new StaffList();

            staffLists.initFile();
            staffLists.readStaffFromFile();

            newS.setID(staffLists.createID());

            generatorQRCode(newS.getID());

            staffLists.appendStaffIntoFile(newS);

            JOptionPane.showMessageDialog(this, "Add Succsess", "Add New Staff", JOptionPane.INFORMATION_MESSAGE);

            this.frame.loadDataFromFile();

            clearAll(); //clear all data in component
        } catch (Exception e) {
        }

    }

    private void generatorQRCode(String ID) {
        try {
            String path = "src/app/QRCodeIMG/" + ID + ".jpg";
            QRCodeGenerator.generatorQRCode(ID, path);
        } catch (Exception e) {
        }
    }

    private boolean validateDataInput() {
        if (this.txt_Name.getText().isEmpty()
                || this.txt_Phonenumber.getText().isEmpty()
                || this.txt_Salary.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui Long nhap du thong tin.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (CommonFunction.formatStringToMoney(this.txt_Salary.getText()) < 0) {
            JOptionPane.showMessageDialog(this, "Vui long nhap lai muc luong!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if ((this.txt_Pwd.getText().isEmpty()|| this.txt_Username.getText().isEmpty()) 
                && !this.cbox_Role.getSelectedObjects().toString().equals("Seller")) {
                return false;
        }
        return true;
    }

    private void eventUpdateData() {
        if (!validateDataInput()) {
            return;
        }
        if (this.staffEdit == null) {
            return;
        }
        StaffList staffList = new StaffList();
        staffList.initFile();
        staffList.readStaffFromFile();

        Staff staff = staffList.findStaffByID(this.staffEdit.getID());
        staff.setName(this.txt_Name.getText());
        staff.setPhonenumber(this.txt_Phonenumber.getText());
        staff.setAddress(this.txt_Address.getText());
        staff.setBirthday(CommonFunction.convertToLocalDateViaSqlDate(this.dateChooser.getDate()));
        staff.setEmail(this.txt_Email.getText());
        staff.setRole(this.cbox_Role.getSelectedItem().toString());
        staff.setSalary(CommonFunction.formatStringToMoney(this.txt_Salary.getText()));
        staff.setGender(radio_Male.isSelected() ? "Male" : "Female");
        staff.setStatus(this.radio_StillWork.isSelected());
        staff.setInWorkAt(LocalTime.parse(this.txt_TimeGetIn.getText()));
        staff.setGetsOffAt(LocalTime.parse(this.txt_TimeGetOut.getText()));

        staffList.writeStaffIntoFile();
        this.frame.loadDataFromFile();
        dispose();
    }

    private void choosePicture() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Image", "jpg", "png");
        chooser.setFileFilter(extensionFilter);
        chooser.setDialogTitle("Choose Pictures");
        chooser.setMultiSelectionEnabled(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            this.profilePicture = file;
            this.lbl_ImageProfile.setIcon(CommonFunction.resizeImage(new ImageIcon(file.getAbsolutePath()), widthProfilePic, heightProFilePic));
            revalidate();
            repaint();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_Group = new javax.swing.ButtonGroup();
        btn_StatusGroup = new javax.swing.ButtonGroup();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_Name = new javax.swing.JTextField();
        txt_Username = new javax.swing.JTextField();
        txt_Email = new javax.swing.JTextField();
        txt_Salary = new javax.swing.JTextField();
        txt_Pwd = new javax.swing.JTextField();
        txt_Phonenumber = new javax.swing.JTextField();
        radio_Male = new javax.swing.JRadioButton();
        radio_Female = new javax.swing.JRadioButton();
        btn_ChoosePicture = new app.swing.Button();
        cbox_Role = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        dateChooser = new com.toedter.calendar.JDateChooser();
        lbl_ImageProfile = new javax.swing.JLabel();
        txt_Address = new javax.swing.JTextArea();
        btn_Cancel = new app.swing.Button();
        btn_Add = new app.swing.Button();
        btn_Clear = new app.swing.Button();
        btn_Update = new app.swing.Button();
        txt_TimeGetOut = new javax.swing.JTextField();
        txt_TimeGetIn = new javax.swing.JTextField();
        radio_isQuit = new javax.swing.JRadioButton();
        radio_StillWork = new javax.swing.JRadioButton();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(840, 550));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(840, 550));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Role");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 270, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Gender");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Phonenumber");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Username");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Email");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Password");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 340, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Birthday");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Address");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 120, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Time In");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 340, -1, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Time Out");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 400, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Salary");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 410, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Status");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 400, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Profile Picture");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, -1, -1));

        txt_Name.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Name.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        txt_Name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_Name.setOpaque(false);
        getContentPane().add(txt_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 195, 30));

        txt_Username.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Username.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        txt_Username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_Username.setOpaque(false);
        getContentPane().add(txt_Username, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, 195, 30));

        txt_Email.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Email.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        txt_Email.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_Email.setOpaque(false);
        getContentPane().add(txt_Email, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, 195, 30));

        txt_Salary.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Salary.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        txt_Salary.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_Salary.setOpaque(false);
        txt_Salary.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_SalaryKeyReleased(evt);
            }
        });
        getContentPane().add(txt_Salary, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 430, 195, 30));

        txt_Pwd.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Pwd.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        txt_Pwd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_Pwd.setOpaque(false);
        getContentPane().add(txt_Pwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 360, 195, 30));

        txt_Phonenumber.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Phonenumber.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        txt_Phonenumber.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_Phonenumber.setOpaque(false);
        getContentPane().add(txt_Phonenumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 195, 30));

        btn_Group.add(radio_Male);
        radio_Male.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        radio_Male.setSelected(true);
        radio_Male.setText("Male");
        getContentPane().add(radio_Male, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, -1, -1));

        btn_Group.add(radio_Female);
        radio_Female.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        radio_Female.setText("Female");
        getContentPane().add(radio_Female, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, -1, -1));

        btn_ChoosePicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/plus.png"))); // NOI18N
        btn_ChoosePicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChoosePictureActionPerformed(evt);
            }
        });
        getContentPane().add(btn_ChoosePicture, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 270, 150, -1));

        cbox_Role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Owner", "Seller", "Cashier" }));
        getContentPane().add(cbox_Role, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 290, 150, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("New Staff");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));
        getContentPane().add(dateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 80, 195, -1));

        lbl_ImageProfile.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));
        getContentPane().add(lbl_ImageProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 180, 180));

        txt_Address.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Address.setColumns(20);
        txt_Address.setRows(5);
        txt_Address.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));
        txt_Address.setOpaque(false);
        getContentPane().add(txt_Address, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 150, 200, -1));

        btn_Cancel.setForeground(new java.awt.Color(204, 0, 51));
        btn_Cancel.setText("Cancel");
        btn_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelActionPerformed(evt);
            }
        });
        getContentPane().add(btn_Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 480, 100, 40));

        btn_Add.setForeground(new java.awt.Color(153, 255, 153));
        btn_Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Add.png"))); // NOI18N
        btn_Add.setText("  Add");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });
        getContentPane().add(btn_Add, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 480, 130, 40));

        btn_Clear.setForeground(new java.awt.Color(102, 102, 255));
        btn_Clear.setText("Clear");
        btn_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ClearActionPerformed(evt);
            }
        });
        getContentPane().add(btn_Clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 480, 60, 40));

        btn_Update.setForeground(new java.awt.Color(255, 255, 153));
        btn_Update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/UpArrow.png"))); // NOI18N
        btn_Update.setText("  Update");
        btn_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UpdateActionPerformed(evt);
            }
        });
        getContentPane().add(btn_Update, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 480, 130, 40));

        txt_TimeGetOut.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_TimeGetOut.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        txt_TimeGetOut.setForeground(new java.awt.Color(0, 153, 0));
        txt_TimeGetOut.setText("21:00:00");
        txt_TimeGetOut.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_TimeGetOut.setOpaque(false);
        getContentPane().add(txt_TimeGetOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 430, 180, 30));

        txt_TimeGetIn.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_TimeGetIn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        txt_TimeGetIn.setForeground(new java.awt.Color(0, 153, 0));
        txt_TimeGetIn.setText("09:00:00");
        txt_TimeGetIn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_TimeGetIn.setOpaque(false);
        getContentPane().add(txt_TimeGetIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 360, 180, 30));

        btn_StatusGroup.add(radio_isQuit);
        radio_isQuit.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        radio_isQuit.setText("Quit");
        getContentPane().add(radio_isQuit, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 430, -1, -1));

        btn_StatusGroup.add(radio_StillWork);
        radio_StillWork.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        radio_StillWork.setSelected(true);
        radio_StillWork.setText("Working");
        getContentPane().add(radio_StillWork, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, -1, -1));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor5.jpg"))); // NOI18N
        Background.setPreferredSize(new java.awt.Dimension(600, 490));
        getContentPane().add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 550));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelActionPerformed
        dispose();
    }//GEN-LAST:event_btn_CancelActionPerformed


    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        eventSaveData();
//        accountLists.showAllAccount();
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ClearActionPerformed
        clearAll();
    }//GEN-LAST:event_btn_ClearActionPerformed

    private void btn_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UpdateActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Xac nhan thay doi!.", "Xac Nhan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            eventUpdateData();
        }
    }//GEN-LAST:event_btn_UpdateActionPerformed

    private void btn_ChoosePictureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChoosePictureActionPerformed
        choosePicture();
    }//GEN-LAST:event_btn_ChoosePictureActionPerformed

    private void txt_SalaryKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SalaryKeyReleased
        if (this.txt_Salary.getText().isEmpty() || CommonFunction.formatStringToMoney(this.txt_Salary.getText()) < 0) {
            this.txt_Salary.setText(CommonFunction.formatMoney(0));
            return;
        }
        this.txt_Salary.setText(CommonFunction.formatMoney(CommonFunction.formatStringToMoney(this.txt_Salary.getText())));
    }//GEN-LAST:event_txt_SalaryKeyReleased

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddNewStaff().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private app.swing.Button btn_Add;
    private app.swing.Button btn_Cancel;
    private app.swing.Button btn_ChoosePicture;
    private app.swing.Button btn_Clear;
    private javax.swing.ButtonGroup btn_Group;
    private javax.swing.ButtonGroup btn_StatusGroup;
    private app.swing.Button btn_Update;
    private javax.swing.JComboBox<String> cbox_Role;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lbl_ImageProfile;
    private javax.swing.JRadioButton radio_Female;
    private javax.swing.JRadioButton radio_Male;
    private javax.swing.JRadioButton radio_StillWork;
    private javax.swing.JRadioButton radio_isQuit;
    private javax.swing.JTextArea txt_Address;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_Name;
    private javax.swing.JTextField txt_Phonenumber;
    private javax.swing.JTextField txt_Pwd;
    private javax.swing.JTextField txt_Salary;
    private javax.swing.JTextField txt_TimeGetIn;
    private javax.swing.JTextField txt_TimeGetOut;
    private javax.swing.JTextField txt_Username;
    // End of variables declaration//GEN-END:variables
}

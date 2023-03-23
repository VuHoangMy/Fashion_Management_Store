package app.component;

import app.BUS.StaffList;
import app.CommonFuncion.CommonFunction;
import app.DTO.Fines;
import app.DTO.Payroll;
import app.DTO.Staff;
import app.DTO.Timekeeping;
import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PayrollForm extends javax.swing.JPanel {

    private StaffList staffList;
    private String header[] = {"ID", "Ten Nhan Vien", "So Ngay nghi", "So lan di muon", "So lan ve som", "Tong Luong"};
    private DefaultTableModel defaultTableModel = new DefaultTableModel(header, 0);

    private double feeQuitDay;
    private double feeGoInLate;
    private double feeGetOutSoon;

    public PayrollForm() {
        initComponents();
        setStyleButton();
        CommonFunction.initScrollbar(scrollPane);
    }

    public void loadDataFromFile() {
        this.staffList = new StaffList();
        this.staffList.initFile();
        this.staffList.readStaffFromFile();

        Fines fines = new Fines();
        fines.initFile();
        fines.readAccountFromFile();

        this.txt_GetInLate.setText(CommonFunction.formatMoney(fines.getaFineOfGoLate()));
        this.txt_GetOutSoon.setText(CommonFunction.formatMoney(fines.getaFineOfLeaveEarly()));
        this.txt_QuitDay.setText(CommonFunction.formatMoney(fines.getaFineOfDayOff()));

//        payRoll();
        loadDataTable(staffList);
    }
    
    private void setStyleButton(){
        this.btn_Fillter.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Update.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Edit.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
    }

    private Vector addToVector(String ID, String staffName, Payroll payroll) {
        Vector<Object> v = new Vector<>();
        v.add(ID);
        v.add(staffName);
        v.add(payroll.getNumberOfDaysOff());
        v.add(payroll.getNumberOfGoesLate());
        v.add(payroll.getNumberOfLeaveEarly());
        v.add(CommonFunction.formatMoney(payroll.getTotalSalary()));
        return v;
    }

    public void loadDataTable(StaffList staffList) {

        int monthHChooser = this.monthChooser.getMonth() + 1;
        int yearChoosre = this.yearChooser.getYear();
        try {
            defaultTableModel.setRowCount(0);
            for (Staff staff : staffList.getStaffList()) {
                if (staff.getPayrollLists().isEmpty()) {
                    continue;
                }

                String staffName = staff.getName();
                String ID = staff.getID();
                for (Payroll payroll : staff.getPayrollLists()) {
                    boolean isEquals = (payroll.getDays().getMonthValue() == monthHChooser)
                            && (payroll.getDays().getYear() == yearChoosre);
                    if (isEquals) {
                        Vector<Object> v = addToVector(ID, staffName, payroll);
                        defaultTableModel.addRow(v);
                    }
                }
            }
            table.setModel(defaultTableModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // for payroll
    private boolean isBeginOfMonth() {
        return LocalDate.now().getDayOfMonth() == 1;
    }

    private boolean validateDataInput() {
        if (CommonFunction.isNumeric(this.txt_GetOutSoon.getText())
                || CommonFunction.isNumeric(this.txt_GetInLate.getText())
                || CommonFunction.isNumeric(this.txt_QuitDay.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lai nhap lai khoan phi phat!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private double caculateFineForEmployee(int numberOfGoLate, int numberOfALeaveEarly, int numberOfDayOff) {
        return (this.feeGetOutSoon * numberOfALeaveEarly) + (this.feeGoInLate * numberOfGoLate) + (this.feeQuitDay * numberOfDayOff);
    }

    private double caculateTotalSalary(double salary, double totalFine) {
        return salary - totalFine;
    }

    private void payRoll() {
        if (!isBeginOfMonth()) { // if today is not 1st of month
            return;
        }
        if (!validateDataInput()) {
            return;
        }
        feeQuitDay = CommonFunction.formatStringToMoney(this.txt_QuitDay.getText());
        feeGoInLate = CommonFunction.formatStringToMoney(txt_GetInLate.getText());
        feeGetOutSoon = CommonFunction.formatStringToMoney(this.txt_GetOutSoon.getText());

        int amountDayOfLastMonth = LocalDate.now().minusMonths(1).lengthOfMonth();
        int lastMonth = LocalDate.now().minusMonths(1).getMonthValue();
        int currentYear = LocalDate.now().getYear();

        for (Staff staff : this.staffList.getStaffList()) {
            int amountWorkDay = 0;
            int amountQuitDay = 0;
            int amountGetInLate = 0;
            int amountGetOutSoon = 0;

            LocalTime timeStartPrescribed = staff.getInWorkAt();
            LocalTime timeEndPrescribed = staff.getGetsOffAt();

            if (staff.getTimekeepingLists().isEmpty()) {
                continue;
            }

            for (Timekeeping timekeeping : staff.getTimekeepingLists()) {
                if (timekeeping.getDays().getMonthValue() == lastMonth && timekeeping.getDays().getYear() == currentYear) {
                    if (timekeeping.getTimeStart().isAfter(timeStartPrescribed)) {
                        amountGetInLate += 1;
                    }
                    if (timekeeping.getTimeEnd().isBefore(timeEndPrescribed)) {
                        amountGetOutSoon += 1;
                    }
                    amountWorkDay += 1;
                }
            }
            amountQuitDay = amountDayOfLastMonth - amountWorkDay;

            double totalFine = caculateFineForEmployee(amountGetInLate, amountGetOutSoon, amountQuitDay);
            double totalSalay = caculateTotalSalary(staff.getSalary(), totalFine);
            Fines fines = new Fines(feeGoInLate, feeGetOutSoon, feeQuitDay);
            Payroll payroll = new Payroll(amountGetInLate, amountGetOutSoon, amountQuitDay, LocalDate.now().minusMonths(1), fines, totalSalay);

            this.staffList.findStaffByID(staff.getID()).addNewPayroll(payroll);
        }

        this.staffList.writeStaffIntoFile();

    }

    private void eventFillterByDate() {
        loadDataTable(this.staffList);
    }

    private void eventEdit() {
        boolean isEnable = this.txt_GetInLate.isEnabled();
        if (!isEnable) {
            boolean result = CommonFunction.isConfirmDialog("Confirm", "Ban muon cap nhap lai mux phat?");
            if (!result) {
                return;
            }
        }

        setEnableComponent(!isEnable);
    }

    private boolean isValidateDataInput() {
        if (this.txt_GetInLate.getText().isEmpty() || !CommonFunction.isNumeric(this.txt_GetInLate.getText())) {
            this.txt_GetInLate.setText("0");
            return false;
        }
        if (this.txt_GetOutSoon.getText().isEmpty() || !CommonFunction.isNumeric(this.txt_GetOutSoon.getText())) {
            this.txt_GetOutSoon.setText("0");
            return false;
        }
        if (this.txt_QuitDay.getText().isEmpty() || !CommonFunction.isNumeric(this.txt_QuitDay.getText())) {
            this.txt_QuitDay.setText("0");
            return false;
        }
        return true;
    }
    
    private void setEnableComponent(boolean isEnable){
        this.txt_GetInLate.setEnabled(isEnable);
        this.txt_GetOutSoon.setEnabled(isEnable);
        this.txt_QuitDay.setEnabled(isEnable);
        this.cbox_ChooseDay.setEnabled(isEnable);
        this.btn_Update.setEnabled(isEnable);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_Back = new app.swing.Button();
        btn_Home = new app.swing.Button();
        formName = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        pictureBox1 = new app.swing.PictureBox();
        monthChooser = new com.toedter.calendar.JMonthChooser();
        btn_Fillter = new app.swing.Button();
        yearChooser = new com.toedter.calendar.JYearChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pictureBox2 = new app.swing.PictureBox();
        jLabel3 = new javax.swing.JLabel();
        txt_QuitDay = new javax.swing.JTextField();
        txt_GetInLate = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_GetOutSoon = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cbox_ChooseDay = new javax.swing.JSpinner();
        btn_Edit = new app.swing.Button();
        btn_Update = new app.swing.Button();
        lbl_Background = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1800, 900));
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        btn_Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/home.png"))); // NOI18N
        btn_Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HomeActionPerformed(evt);
            }
        });
        add(btn_Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        formName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        formName.setForeground(new java.awt.Color(255, 255, 255));
        formName.setText("Payrolls");
        add(formName, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        scrollPane.setBorder(null);

        table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Name", "Type", "Description", "Status"
            }
        ));
        table.setOpaque(false);
        table.setRowHeight(25);
        table.setRowMargin(5);
        scrollPane.setViewportView(table);

        add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 220, 1180, 610));

        pictureBox1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fillter By Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Black", 0, 15), new java.awt.Color(255, 255, 255))); // NOI18N

        monthChooser.setForeground(new java.awt.Color(255, 255, 255));
        monthChooser.setOpaque(false);
        pictureBox1.add(monthChooser);
        monthChooser.setBounds(30, 60, 111, 30);

        btn_Fillter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/filter.png"))); // NOI18N
        btn_Fillter.setText("Fillter");
        btn_Fillter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FillterActionPerformed(evt);
            }
        });
        pictureBox1.add(btn_Fillter);
        btn_Fillter.setBounds(160, 110, 140, 52);

        yearChooser.setOpaque(false);
        pictureBox1.add(yearChooser);
        yearChooser.setBounds(200, 60, 100, 30);

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Month");
        pictureBox1.add(jLabel2);
        jLabel2.setBounds(30, 30, 60, 21);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Year");
        pictureBox1.add(jLabel1);
        jLabel1.setBounds(200, 30, 50, 21);

        add(pictureBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 320, 180));

        pictureBox2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fines", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Black", 0, 15), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Day Off");
        pictureBox2.add(jLabel3);
        jLabel3.setBounds(40, 50, 80, 30);

        txt_QuitDay.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        txt_QuitDay.setText("150000");
        txt_QuitDay.setEnabled(false);
        txt_QuitDay.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_QuitDayFocusLost(evt);
            }
        });
        pictureBox2.add(txt_QuitDay);
        txt_QuitDay.setBounds(120, 50, 160, 30);

        txt_GetInLate.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        txt_GetInLate.setText("100000");
        txt_GetInLate.setEnabled(false);
        txt_GetInLate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_GetInLateFocusLost(evt);
            }
        });
        pictureBox2.add(txt_GetInLate);
        txt_GetInLate.setBounds(120, 120, 160, 27);

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Go Late");
        pictureBox2.add(jLabel4);
        jLabel4.setBounds(40, 120, 80, 30);

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Leave Soon");
        pictureBox2.add(jLabel5);
        jLabel5.setBounds(30, 190, 80, 30);

        txt_GetOutSoon.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        txt_GetOutSoon.setText("100000");
        txt_GetOutSoon.setEnabled(false);
        txt_GetOutSoon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_GetOutSoonFocusLost(evt);
            }
        });
        pictureBox2.add(txt_GetOutSoon);
        txt_GetOutSoon.setBounds(120, 190, 160, 27);

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Ngay Tinh Luong");
        pictureBox2.add(jLabel6);
        jLabel6.setBounds(40, 250, 130, 21);

        cbox_ChooseDay.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        cbox_ChooseDay.setModel(new javax.swing.SpinnerNumberModel(1, 1, 30, 1));
        cbox_ChooseDay.setEnabled(false);
        pictureBox2.add(cbox_ChooseDay);
        cbox_ChooseDay.setBounds(180, 250, 100, 28);

        btn_Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/EditHover.png"))); // NOI18N
        btn_Edit.setText("Edit");
        btn_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditActionPerformed(evt);
            }
        });
        pictureBox2.add(btn_Edit);
        btn_Edit.setBounds(168, 306, 77, 50);

        btn_Update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/UpArrow.png"))); // NOI18N
        btn_Update.setText("Update");
        btn_Update.setEnabled(false);
        btn_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UpdateActionPerformed(evt);
            }
        });
        pictureBox2.add(btn_Update);
        btn_Update.setBounds(35, 306, 110, 50);

        add(pictureBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 430, 320, 390));

        lbl_Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor5.jpg"))); // NOI18N
        add(lbl_Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1800, 900));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        CommonFunction.backToStaffMenu();
    }//GEN-LAST:event_btn_BackActionPerformed

    private void btn_HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HomeActionPerformed
        CommonFunction.backToStaffMenu();
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_HomeActionPerformed

    private void btn_FillterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FillterActionPerformed
        eventFillterByDate();
    }//GEN-LAST:event_btn_FillterActionPerformed

    private void btn_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditActionPerformed
        eventEdit();
    }//GEN-LAST:event_btn_EditActionPerformed

    private void txt_QuitDayFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_QuitDayFocusLost
        if (this.txt_QuitDay.getText().isEmpty() || CommonFunction.formatStringToMoney(this.txt_GetOutSoon.getText()) < 0) {
            this.txt_QuitDay.setText(CommonFunction.formatMoney(0));
            return;
        }
        this.txt_QuitDay.setText(CommonFunction.formatMoney(CommonFunction.formatStringToMoney(this.txt_QuitDay.getText())));
    }//GEN-LAST:event_txt_QuitDayFocusLost

    private void txt_GetInLateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_GetInLateFocusLost
        if (this.txt_GetInLate.getText().isEmpty() || CommonFunction.formatStringToMoney(this.txt_GetInLate.getText()) < 0) {
            this.txt_GetInLate.setText(CommonFunction.formatMoney(0));
            return;
        }
        this.txt_GetInLate.setText(CommonFunction.formatMoney(CommonFunction.formatStringToMoney(this.txt_GetInLate.getText())));
    }//GEN-LAST:event_txt_GetInLateFocusLost

    private void txt_GetOutSoonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_GetOutSoonFocusLost
        if (this.txt_GetOutSoon.getText().isEmpty() || CommonFunction.formatStringToMoney(this.txt_GetOutSoon.getText()) < 0) {
            this.txt_GetOutSoon.setText(CommonFunction.formatMoney(0));
            return;
        }
        this.txt_GetOutSoon.setText(CommonFunction.formatMoney(CommonFunction.formatStringToMoney(this.txt_GetOutSoon.getText())));
    }//GEN-LAST:event_txt_GetOutSoonFocusLost

    private void btn_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UpdateActionPerformed
        boolean result = CommonFunction.isConfirmDialog("Confirm", "Xac nhan Cap nhap du lieu!");
        if (!result) {
            return;
        }
        Fines fines = new Fines(CommonFunction.formatStringToMoney(this.txt_GetInLate.getText()), CommonFunction.formatStringToMoney(this.txt_GetOutSoon.getText()), CommonFunction.formatStringToMoney(this.txt_QuitDay.getText()));
        fines.initFile();
        fines.writeAcountIntoFile();
        
        setEnableComponent(false);
    }//GEN-LAST:event_btn_UpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.swing.Button btn_Back;
    private app.swing.Button btn_Edit;
    private app.swing.Button btn_Fillter;
    private app.swing.Button btn_Home;
    private app.swing.Button btn_Update;
    private javax.swing.JSpinner cbox_ChooseDay;
    private javax.swing.JLabel formName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lbl_Background;
    private com.toedter.calendar.JMonthChooser monthChooser;
    private app.swing.PictureBox pictureBox1;
    private app.swing.PictureBox pictureBox2;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_GetInLate;
    private javax.swing.JTextField txt_GetOutSoon;
    private javax.swing.JTextField txt_QuitDay;
    private com.toedter.calendar.JYearChooser yearChooser;
    // End of variables declaration//GEN-END:variables
}

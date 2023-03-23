package app.component;

import app.BUS.StaffList;
import app.CommonFuncion.CommonFunction;
import app.CommonFuncion.CurrentUserLogin;
import app.DTO.Staff;
import app.GUI.AddNewStaff;
import java.awt.Font;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class StaffForm extends javax.swing.JPanel {

    private StaffList staffLists;
    private String header[] = {"ID", "Fullname", "Gender", "Role", "Birthday", "Address", "Email", "Phonenumber", "Salary", "Status"};
    private DefaultTableModel defaultTableModel = new DefaultTableModel(header, 0);

    public StaffForm() {
        initComponents();
        setDisableButton(false);
        setStyleButton();
        CommonFunction.initScrollbar(scrollPane);
    }
    
    private void setStyleButton() {
        this.btn_Add.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Edit.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Remove.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Printer.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
    }

    private void setDisableButton(boolean s) {
        this.btn_Remove.setEnabled(s);
        this.btn_Edit.setEnabled(s);
    }

    public void loadDataTable(StaffList staffLists) {

        defaultTableModel.setRowCount(0);
        for (Staff staff : staffLists.getStaffList()) {
            Vector<Object> v = addToVector(staff);
            defaultTableModel.addRow(v);
        }
        table.setModel(defaultTableModel);
    }

    public void loadDataFromFile() {
        staffLists = new StaffList();
        staffLists.initFile();
        staffLists.readStaffFromFile();
        staffLists.getStaffList().remove(CurrentUserLogin.getCurrentStaff().getCurrentuserLogin());
        loadDataTable(staffLists);
    }

    private void searchNameAndSetTable(String name) {
        StaffList TMPlist = this.staffLists.fillterStaffListByName(name);
        if (TMPlist.getStaffList().size() > 0) {
            loadDataTable(TMPlist);
        }
    }

    private Vector addToVector(Staff staff) {
        Vector<Object> v = new Vector<>();
        v.add(staff.getID());
        v.add(staff.getName());
        v.add(staff.getGender());
        v.add(staff.getRole());
        v.add(staff.getBirthday());
        v.add(staff.getAddress());
        v.add(staff.getEmail());
        v.add(staff.getPhonenumber());
        v.add(CommonFunction.formatMoney(staff.getSalary()));
        if (staff.getStatus()) {
            v.add("Working");
        } else {
            v.add("Quit");
        }
        return v;
    }

    private void eventRemoveStaff() {
        int column = 0;
        int row = table.getSelectedRow();
        String ID = table.getValueAt(row, column).toString();
        boolean result = CommonFunction.isConfirmDialog("Delete", "Confirm Delete Staff ID: " + ID + " ?");
        if(!result){
            return;
        }
        staffLists.getStaffList().remove(staffLists.findStaffByID(ID));
        staffLists.writeStaffIntoFile();
        loadDataTable(staffLists);
    }

    private void eventEditStaff() {
        int column = 0;
        int row = table.getSelectedRow();
        String ID = table.getModel().getValueAt(row, column).toString();
        Staff s = staffLists.findStaffByID(ID);

        if (s != null) {
            AddNewStaff newStaff = new AddNewStaff(s, this);
            newStaff.setVisible(true);
        }
    }
    
    private void eventAddNewStaff(){
        AddNewStaff addNewStaff = new AddNewStaff(this);
        addNewStaff.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_Add = new app.swing.Button();
        btn_Edit = new app.swing.Button();
        btn_Back = new app.swing.Button();
        btn_Printer = new app.swing.Button();
        btn_Remove = new app.swing.Button();
        formName = new javax.swing.JLabel();
        txt_Search = new javax.swing.JTextField();
        btn_Search = new javax.swing.JLabel();
        btn_Home = new app.swing.Button();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        Background = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));
        setDoubleBuffered(false);
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Add.png"))); // NOI18N
        btn_Add.setText(" Add");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });
        add(btn_Add, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 150, 120, 60));

        btn_Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Edit.png"))); // NOI18N
        btn_Edit.setText("Edit");
        btn_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditActionPerformed(evt);
            }
        });
        add(btn_Edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 150, 120, 60));

        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 70, 40));

        btn_Printer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Printer.png"))); // NOI18N
        btn_Printer.setText(" Printer");
        add(btn_Printer, new org.netbeans.lib.awtextra.AbsoluteConstraints(1310, 150, 120, 60));

        btn_Remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Delete.png"))); // NOI18N
        btn_Remove.setText(" Delete");
        btn_Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RemoveActionPerformed(evt);
            }
        });
        add(btn_Remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 150, 120, 60));

        formName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        formName.setForeground(new java.awt.Color(255, 255, 255));
        formName.setText("Employee");
        add(formName, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, -1, -1));

        txt_Search.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_Search.setFont(new java.awt.Font("Segoe UI Light", 1, 15)); // NOI18N
        txt_Search.setForeground(new java.awt.Color(204, 204, 255));
        txt_Search.setText("Search by Name");
        txt_Search.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        txt_Search.setOpaque(false);
        txt_Search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_SearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_SearchFocusLost(evt);
            }
        });
        txt_Search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_SearchKeyReleased(evt);
            }
        });
        add(txt_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 350, 40));

        btn_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Search.png"))); // NOI18N
        btn_Search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_SearchMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_SearchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_SearchMouseExited(evt);
            }
        });
        add(btn_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 160, -1, 40));

        btn_Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/home.png"))); // NOI18N
        btn_Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HomeActionPerformed(evt);
            }
        });
        add(btn_Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 70, 40));

        table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Tên", "Chức vụ", "SDT", "Ngày Sinh", "Lương", "Địa Chỉ", "Email", "Giới Tính"
            }
        ));
        table.setOpaque(false);
        table.setRowHeight(25);
        table.setRowMargin(5);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(table);

        add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, 1680, 260));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor 2.jpg"))); // NOI18N
        Background.setMinimumSize(new java.awt.Dimension(0, 0));
        Background.setPreferredSize(new java.awt.Dimension(1800, 900));
        add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1800, 900));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SearchMouseEntered
        btn_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/SearchHover.png"))); // NOI18N
    }//GEN-LAST:event_btn_SearchMouseEntered

    private void btn_SearchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SearchMouseExited
        btn_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Search.png"))); // NOI18N
    }//GEN-LAST:event_btn_SearchMouseExited

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        eventAddNewStaff();
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        CommonFunction.backToStaffMenu();
    }//GEN-LAST:event_btn_BackActionPerformed

    private void btn_RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RemoveActionPerformed
        eventRemoveStaff();
    }//GEN-LAST:event_btn_RemoveActionPerformed

    private void btn_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditActionPerformed
        eventEditStaff();
    }//GEN-LAST:event_btn_EditActionPerformed

    private void txt_SearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusGained
        if (this.txt_Search.getText().equals("Search by Name")) {
            this.txt_Search.setText("");
        }
    }//GEN-LAST:event_txt_SearchFocusGained

    private void txt_SearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusLost
        if (this.txt_Search.getText().isEmpty()) {
            this.txt_Search.setText("Search by Name");
        }
    }//GEN-LAST:event_txt_SearchFocusLost

    private void btn_SearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SearchMouseClicked
        String searchName = this.txt_Search.getText();
        StaffList tmpLsts = this.staffLists.fillterStaffListByName(searchName);
        loadDataTable(tmpLsts);
    }//GEN-LAST:event_btn_SearchMouseClicked

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        setDisableButton(true);
    }//GEN-LAST:event_tableMouseClicked

    private void btn_HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HomeActionPerformed
        CommonFunction.backToStaffMenu();
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_HomeActionPerformed

    private void txt_SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyReleased
        String searchName = this.txt_Search.getText();
        searchNameAndSetTable(searchName);
    }//GEN-LAST:event_txt_SearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private app.swing.Button btn_Add;
    private app.swing.Button btn_Back;
    private app.swing.Button btn_Edit;
    private app.swing.Button btn_Home;
    private app.swing.Button btn_Printer;
    private app.swing.Button btn_Remove;
    private javax.swing.JLabel btn_Search;
    private javax.swing.JLabel formName;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_Search;
    // End of variables declaration//GEN-END:variables
}

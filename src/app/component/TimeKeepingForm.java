package app.component;

import app.BUS.StaffList;
import app.CommonFuncion.CommonFunction;
import app.DTO.Staff;
import app.DTO.Timekeeping;
import java.awt.Font;
import java.time.LocalDate;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class TimeKeepingForm extends javax.swing.JPanel {

    private StaffList staffList;
    private String header[] = {"ID", "Employee", "Date", "Day of Week", "Time In", "Time Out", "Status"};
    private DefaultTableModel defaultTableModel = new DefaultTableModel(header, 0);

    public TimeKeepingForm() {
        initComponents();
        
        setStyleButton();
        CommonFunction.initScrollbar(scrollPane);
    }

    private Vector addToVectorBillTable(Staff s, Timekeeping t) {
        Vector<Object> v = new Vector<>();
        v.add(s.getID());
        v.add(s.getName());
        v.add(t.getDays());
        v.add(t.getDays().getDayOfWeek());
        v.add(t.getTimeStart());
        v.add(t.getTimeEnd());
        String resultGetIn = "";
        String resultGetOut = "";
        if(t.getTimeStart() != null){
           resultGetIn = t.getTimeStart().isAfter(s.getInWorkAt()) ? "Di tre, " : "";
        }
        if(t.getTimeEnd() != null){
            resultGetOut = t.getTimeEnd().isBefore(s.getGetsOffAt()) ? "Ve Som" : "";
        }
        v.add(resultGetIn + resultGetOut);
        
        return v;
    }

    public void loadDataFromFile() {
        this.staffList = new StaffList();
        this.staffList.initFile();
        this.staffList.readStaffFromFile();

        loadDataToTable(staffList);
    }
    
    private void setStyleButton(){
        this.btn_Filter.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_Refresh.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
    }

    private void loadDataToTable(StaffList staffList) {
        defaultTableModel.setRowCount(0);
        for (Staff staff : staffList.getStaffList()) {
            for (Timekeeping timekeeping : staff.getTimekeepingLists()) {
                Vector<Object> v = addToVectorBillTable(staff, timekeeping);
                defaultTableModel.addRow(v);
            }
        }
        table.setModel(defaultTableModel);
    }

    private void eventFillterByDate() {
        LocalDate date = CommonFunction.convertToLocalDateViaSqlDate(DateChooser.getDate());
        defaultTableModel.setRowCount(0);
        for (Staff staff : staffList.getStaffList()) {
            for (Timekeeping timekeeping : staff.getTimekeepingLists()) {
                if (date.isEqual(timekeeping.getDays())) {
                    Vector<Object> v = addToVectorBillTable(staff, timekeeping);
                    defaultTableModel.addRow(v);
                }
            }
        }
        table.setModel(defaultTableModel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        formName = new javax.swing.JLabel();
        btn_Back = new app.swing.Button();
        btn_Filter = new app.swing.Button();
        btn_Refresh = new app.swing.Button();
        btn_Home = new app.swing.Button();
        DateChooser = new com.toedter.calendar.JDateChooser();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        Background = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1800, 900));
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        formName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        formName.setForeground(new java.awt.Color(255, 255, 255));
        formName.setText("Timekepping");
        add(formName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, 60));

        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 20, -1, -1));

        btn_Filter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/filter.png"))); // NOI18N
        btn_Filter.setText("Filter");
        btn_Filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FilterActionPerformed(evt);
            }
        });
        add(btn_Filter, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 130, -1));

        btn_Refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Refresh.png"))); // NOI18N
        btn_Refresh.setText("Refresh");
        btn_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefreshActionPerformed(evt);
            }
        });
        add(btn_Refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 130, 50));

        btn_Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/home.png"))); // NOI18N
        btn_Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HomeActionPerformed(evt);
            }
        });
        add(btn_Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 60, 50));

        DateChooser.setMinimumSize(new java.awt.Dimension(120, 20));
        DateChooser.setOpaque(false);
        DateChooser.setPreferredSize(new java.awt.Dimension(120, 20));
        add(DateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 280, 40));

        table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.setOpaque(false);
        table.setRowHeight(25);
        table.setRowMargin(5);
        scrollPane.setViewportView(table);

        add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 150, 1330, 660));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor 2.jpg"))); // NOI18N
        add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1800, 900));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        CommonFunction.backToStaffMenu();
    }//GEN-LAST:event_btn_BackActionPerformed

    private void btn_HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HomeActionPerformed
        CommonFunction.backToStaffMenu();
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_HomeActionPerformed

    private void btn_FilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FilterActionPerformed
        eventFillterByDate();
    }//GEN-LAST:event_btn_FilterActionPerformed

    private void btn_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefreshActionPerformed
        loadDataFromFile();
    }//GEN-LAST:event_btn_RefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private com.toedter.calendar.JDateChooser DateChooser;
    private app.swing.Button btn_Back;
    private app.swing.Button btn_Filter;
    private app.swing.Button btn_Home;
    private app.swing.Button btn_Refresh;
    private javax.swing.JLabel formName;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}

package app.component;

import app.BUS.StaffList;
import app.CommonFuncion.CommonFunction;
import app.DTO.BillImportGoods;
import app.DTO.Staff;
import app.GUI.ItemsDetail;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class BillImportGoodsForm extends javax.swing.JPanel {

    private StaffList staffList;
    private String header[] = {"ID", "Nhan Vien", "Nha Cung Cap", "Thoi Gian", "SDT", "So Luong", "Gia Nhap", "Tong", "Ghi Chu"};
    private DefaultTableModel defaultTableModel = new DefaultTableModel(header, 0);

    public BillImportGoodsForm() {
        initComponents();
        setStyleTable();
    }

    public void loadDataFromFile() {
        this.staffList = new StaffList();
        this.staffList.initFile();
        this.staffList.readStaffFromFile();

        loadDataTable(this.staffList);
        setButtonStatus(false);
    }

    private void loadDataTable(StaffList staffList) {
        defaultTableModel.setRowCount(0);
        for (Staff staff : staffList.getStaffList()) {
            if (staff.getBillImportGoodses() == null) {
                continue;
            }
            for (BillImportGoods bill : staff.getBillImportGoodses()) {
                Vector<Object> v = addToVectorBillTable(staff, bill);
                defaultTableModel.addRow(v);
            }
        } 
        table.setModel(defaultTableModel);

    }

    private void setStyleTable() {
        CommonFunction.initScrollbar(this.scrollPane);
        table.getColumnModel().getColumn(0).setMaxWidth(100);
        table.getColumnModel().getColumn(5).setMaxWidth(100);
    }

    private void setButtonStatus(boolean isEnable) {
        this.btn_Detail.setEnabled(isEnable);
    }

    private Vector addToVectorBillTable(Staff staff, BillImportGoods billImportGoods) {
        Vector<Object> v = new Vector<>();
        v.add(billImportGoods.getID());
        v.add(staff.getName());
        v.add(billImportGoods.getSupplier().getName());

        String formatDateTime = CommonFunction.formatLocalDateTime(billImportGoods.getDateImportGoods(), "dd-MM-yyyy HH:mm");
        v.add(formatDateTime);

        v.add(billImportGoods.getSupplier().getPhonenumber());

        v.add(billImportGoods.getAmountImport());
        v.add(CommonFunction.formatMoney(billImportGoods.getPriceImport()));

        double total = (double)billImportGoods.getAmountImport() * billImportGoods.getPriceImport();
        v.add(CommonFunction.formatMoney(total));
        v.add(billImportGoods.getNote());

        return v;
    }

    private void eventFilterBillByDate() {
        LocalDate FromDate = CommonFunction.convertToLocalDateViaSqlDate(this.DChooser_FromDate.getDate());
        LocalDate ToDate = CommonFunction.convertToLocalDateViaSqlDate(this.DChooser_ToDate.getDate());
        StaffList result = new StaffList();

        for (Staff staff : this.staffList.getStaffList()) {
            if (staff.getBillImportGoodses() == null) {
                continue;
            }
            Staff tmpStaff = new Staff();
            for (BillImportGoods bill : staff.getBillImportGoodses()) {
                LocalDate tmp = bill.getDateImportGoods().toLocalDate();
                if (tmp.isBefore(FromDate) || tmp.isAfter(ToDate)) {
                    continue;
                }
                tmpStaff.addNewBillImportItems(bill);
            }
            if (!tmpStaff.getBillImportGoodses().isEmpty()) {
                result.addNewStaff(tmpStaff);
            }
        }
        loadDataTable(result);

    }

    private void eventSearch() {
        defaultTableModel.setRowCount(0);
        String txtSearch = txt_Search.getText();
        for (Staff staff : this.staffList.getStaffList()) {
            if (staff.getBillImportGoodses() == null) {
                continue;
            }
            for (BillImportGoods bill : staff.getBillImportGoodses()) {
                if (bill.getID().contains(txtSearch)) {
                    Vector<Object> v = addToVectorBillTable(staff, bill);
                    defaultTableModel.addRow(v);
                }
            }
        }
        table.setModel(defaultTableModel);
    }

    private void showBillDetail() {
        String ID = table.getValueAt(table.getSelectedRow(), 0).toString();
        for (Staff staff : this.staffList.getStaffList()) {
            if (staff.getBillImportGoodses() != null) {
                for (BillImportGoods bill : staff.getBillImportGoodses()) {
                    if (bill.getID().equals(ID)) {
                        ItemsDetail form = new ItemsDetail(bill.getItems());
                        form.setVisible(true);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button1 = new app.swing.Button();
        formName = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        DChooser_FromDate = new com.toedter.calendar.JDateChooser();
        btn_Filter = new app.swing.Button();
        btn_Refresh = new app.swing.Button();
        txt_Search = new javax.swing.JTextField();
        btn_Search = new app.swing.Button();
        btn_Home = new app.swing.Button();
        button5 = new app.swing.Button();
        btn_Detail = new app.swing.Button();
        jLabel3 = new javax.swing.JLabel();
        DChooser_ToDate = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 32, 50, 40));

        formName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        formName.setForeground(new java.awt.Color(255, 255, 255));
        formName.setText("Import Invoice");
        add(formName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, -1, -1));

        table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Importer", "Supplier", "Date", "Phonenumber", "Total", "Note"
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

        add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 240, 1340, 543));

        DChooser_FromDate.setBackground(new java.awt.Color(0, 0, 0,1));
        DChooser_FromDate.setOpaque(false);
        add(DChooser_FromDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 250, 30));

        btn_Filter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/filter.png"))); // NOI18N
        btn_Filter.setText("Filter");
        btn_Filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FilterActionPerformed(evt);
            }
        });
        add(btn_Filter, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, 110, 50));

        btn_Refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Refresh.png"))); // NOI18N
        btn_Refresh.setText("Refresh");
        btn_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefreshActionPerformed(evt);
            }
        });
        add(btn_Refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 390, 110, 50));

        txt_Search.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_Search.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 17)); // NOI18N
        txt_Search.setForeground(new java.awt.Color(0, 51, 153));
        txt_Search.setText("Search");
        txt_Search.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
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
        add(txt_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, 820, 50));

        btn_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Search.png"))); // NOI18N
        btn_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SearchActionPerformed(evt);
            }
        });
        add(btn_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 150, 90, 50));

        btn_Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/home.png"))); // NOI18N
        btn_Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HomeActionPerformed(evt);
            }
        });
        add(btn_Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 50, 40));

        button5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Printer.png"))); // NOI18N
        add(button5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 150, 80, 50));

        btn_Detail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Detail.png"))); // NOI18N
        btn_Detail.setText("Detail");
        btn_Detail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DetailActionPerformed(evt);
            }
        });
        add(btn_Detail, new org.netbeans.lib.awtextra.AbsoluteConstraints(1450, 150, 110, 50));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("To Date");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 120, 20));

        DChooser_ToDate.setBackground(new java.awt.Color(0, 0, 0,1));
        DChooser_ToDate.setOpaque(false);
        add(DChooser_ToDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 250, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("From Date");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 120, 20));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor5.jpg"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1800, 900));
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        CommonFunction.backToBillMenu();
    }//GEN-LAST:event_button1ActionPerformed

    private void btn_FilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FilterActionPerformed
        eventFilterBillByDate();

    }//GEN-LAST:event_btn_FilterActionPerformed

    private void btn_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefreshActionPerformed
        loadDataTable(this.staffList);
        txt_Search.setText("");
    }//GEN-LAST:event_btn_RefreshActionPerformed

    private void txt_SearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusGained
        CommonFunction.setPlaceHolder(txt_Search, true, "Search");
    }//GEN-LAST:event_txt_SearchFocusGained

    private void txt_SearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusLost
        CommonFunction.setPlaceHolder(txt_Search, false, "Search");
    }//GEN-LAST:event_txt_SearchFocusLost

    private void btn_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SearchActionPerformed
        eventSearch();
    }//GEN-LAST:event_btn_SearchActionPerformed

    private void btn_DetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DetailActionPerformed
        showBillDetail();
    }//GEN-LAST:event_btn_DetailActionPerformed

    private void btn_HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HomeActionPerformed
        CommonFunction.backToBillMenu();
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_HomeActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        setButtonStatus(true);
    }//GEN-LAST:event_tableMouseClicked

    private void txt_SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyReleased
         eventSearch();
    }//GEN-LAST:event_txt_SearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DChooser_FromDate;
    private com.toedter.calendar.JDateChooser DChooser_ToDate;
    private app.swing.Button btn_Detail;
    private app.swing.Button btn_Filter;
    private app.swing.Button btn_Home;
    private app.swing.Button btn_Refresh;
    private app.swing.Button btn_Search;
    private app.swing.Button button1;
    private app.swing.Button button5;
    private javax.swing.JLabel formName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_Search;
    // End of variables declaration//GEN-END:variables
}

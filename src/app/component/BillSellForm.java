package app.component;

import app.BUS.BillSellList;
import app.CommonFuncion.CommonFunction;
import app.DTO.BillSell;
import java.time.LocalDate;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class BillSellForm extends javax.swing.JPanel {

    private BillSellList billSellList;
    private String header[] = {"ID", "Nguoi Ban", "Thoi Gian", "Ten Khach Hang", "SDT Khach Hang", "Total", "Note"};
    private DefaultTableModel defaultTableModel = new DefaultTableModel(header, 0);

    public BillSellForm() {
        initComponents();
        setStyleTable();
    }

    private void loadDataTable(BillSellList billSellList) {
        defaultTableModel.setRowCount(0);
        for (BillSell bill : billSellList.getBillSellList()) {
            Vector<Object> v = addToVectorBillTable(bill);
            defaultTableModel.addRow(v);
        }
        table.setModel(defaultTableModel);
    }

    private Vector addToVectorBillTable(BillSell bill) {
        Vector<Object> v = new Vector<>();
        v.add(bill.getID());
        v.add(bill.getStaff().getName());

        String formatDateTime = CommonFunction.formatLocalDateTime(bill.getDateSell(), "dd-MM-yyyy HH:mm");
        v.add(formatDateTime);
        if (bill.getCustomer() == null) {
            v.add("Khach vang lai");
            v.add("");
        } else {
            v.add(bill.getCustomer().getName());
            v.add(bill.getCustomer().getPhonenumber());
        }

        v.add(CommonFunction.formatMoney(bill.getTotalInvoice()));

        v.add(bill.getNote());

        return v;
    }

    private void setStyleTable() {
        CommonFunction.initScrollbar(scrollPane);
    }

    public void loadDataFromFile() {
        this.billSellList = new BillSellList();
        this.billSellList.initFile();
        this.billSellList.readFromFile();
        loadDataTable(billSellList);
    }
    
    // Event

    private void eventShowDetailBill() {
        String ID = table.getValueAt(table.getSelectedRow(), 0).toString();
        BillSell b = this.billSellList.findBillByID(ID);

    }

    private void eventSearchBill(String txtSearch) {
        BillSellList tmpList = new BillSellList();
        for (BillSell billSell : this.billSellList.getBillSellList()) {
            if (billSell.getID().contains(txtSearch)) {
                tmpList.addNewBillSell(billSell);
            }
        }
        loadDataTable(tmpList);
    }
    
    private void eventRefresh(){
        loadDataFromFile();
        this.txt_Search.setText("Search");
    }
    
    private void eventFilterBillByDate() {
        LocalDate FromDate = CommonFunction.convertToLocalDateViaSqlDate(this.DChooser_FromDate.getDate());
        LocalDate ToDate = CommonFunction.convertToLocalDateViaSqlDate(this.DChooser_ToDate.getDate());
        BillSellList result = new BillSellList();

        for (BillSell bill : this.billSellList.getBillSellList()) {
            LocalDate dateSell = bill.getDateSell().toLocalDate();
            if(dateSell.isBefore(FromDate) || dateSell.isAfter(ToDate)){
                continue;
            }
            result.addNewBillSell(bill);
        }
        loadDataTable(result);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_Back = new app.swing.Button();
        formName = new javax.swing.JLabel();
        txt_Search = new javax.swing.JTextField();
        btn_Search = new app.swing.Button();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        DChooser_FromDate = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        btn_Printer = new app.swing.Button();
        btn_Home = new app.swing.Button();
        btn_Detail = new app.swing.Button();
        btn_Fillter = new app.swing.Button();
        btn_Refresh = new app.swing.Button();
        jLabel2 = new javax.swing.JLabel();
        DChooser_ToDate = new com.toedter.calendar.JDateChooser();
        BackGround_SortByDate = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

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

        formName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        formName.setForeground(new java.awt.Color(255, 255, 255));
        formName.setText("Sales Invoice");
        add(formName, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

        txt_Search.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Search.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txt_Search.setForeground(new java.awt.Color(255, 255, 255));
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
        add(txt_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 510, 40));

        btn_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Search.png"))); // NOI18N
        btn_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SearchActionPerformed(evt);
            }
        });
        add(btn_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 180, -1, 50));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nguoi Ban", "Thoi Gian", "Ten Khac Hang", "SDT Khach Hang", "Note", "Tong Tien"
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

        add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 1130, 430));

        DChooser_FromDate.setBackground(new java.awt.Color(0, 0, 0,1));
        DChooser_FromDate.setOpaque(false);
        add(DChooser_FromDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1500, 270, 210, 30));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("From Date");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 270, 100, 30));

        btn_Printer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Printer.png"))); // NOI18N
        add(btn_Printer, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 180, 60, 50));

        btn_Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/home.png"))); // NOI18N
        btn_Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HomeActionPerformed(evt);
            }
        });
        add(btn_Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        btn_Detail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Detail.png"))); // NOI18N
        btn_Detail.setEnabled(false);
        btn_Detail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DetailActionPerformed(evt);
            }
        });
        add(btn_Detail, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 180, 90, -1));

        btn_Fillter.setForeground(new java.awt.Color(102, 255, 255));
        btn_Fillter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/filter.png"))); // NOI18N
        btn_Fillter.setText("Fillter");
        btn_Fillter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FillterActionPerformed(evt);
            }
        });
        add(btn_Fillter, new org.netbeans.lib.awtextra.AbsoluteConstraints(1620, 360, 90, -1));

        btn_Refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Refresh.png"))); // NOI18N
        btn_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefreshActionPerformed(evt);
            }
        });
        add(btn_Refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 180, 90, -1));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("To Date");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 320, -1, 30));

        DChooser_ToDate.setBackground(new java.awt.Color(0, 0, 0,1));
        DChooser_ToDate.setOpaque(false);
        add(DChooser_ToDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1500, 320, 210, 30));

        BackGround_SortByDate.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fillter By Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semibold", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        add(BackGround_SortByDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1330, 230, 420, 210));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor5.jpg"))); // NOI18N
        add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1800, 900));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        CommonFunction.backToBillMenu();
    }//GEN-LAST:event_btn_BackActionPerformed

    private void btn_HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HomeActionPerformed
        CommonFunction.backToBillMenu();
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_HomeActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        this.btn_Detail.setEnabled(true);
    }//GEN-LAST:event_tableMouseClicked

    private void btn_DetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DetailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_DetailActionPerformed

    private void btn_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SearchActionPerformed
        eventSearchBill(this.txt_Search.getText());
    }//GEN-LAST:event_btn_SearchActionPerformed

    private void txt_SearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusGained
        CommonFunction.setPlaceHolder(txt_Search, true, "Search");
    }//GEN-LAST:event_txt_SearchFocusGained

    private void txt_SearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusLost
        CommonFunction.setPlaceHolder(txt_Search, false, "Search");
    }//GEN-LAST:event_txt_SearchFocusLost

    private void btn_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefreshActionPerformed
        eventRefresh();
    }//GEN-LAST:event_btn_RefreshActionPerformed

    private void btn_FillterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FillterActionPerformed
        eventFilterBillByDate();
    }//GEN-LAST:event_btn_FillterActionPerformed

    private void txt_SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyReleased
        eventSearchBill(this.txt_Search.getText());
    }//GEN-LAST:event_txt_SearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackGround_SortByDate;
    private javax.swing.JLabel Background;
    private com.toedter.calendar.JDateChooser DChooser_FromDate;
    private com.toedter.calendar.JDateChooser DChooser_ToDate;
    private app.swing.Button btn_Back;
    private app.swing.Button btn_Detail;
    private app.swing.Button btn_Fillter;
    private app.swing.Button btn_Home;
    private app.swing.Button btn_Printer;
    private app.swing.Button btn_Refresh;
    private app.swing.Button btn_Search;
    private javax.swing.JLabel formName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_Search;
    // End of variables declaration//GEN-END:variables
}

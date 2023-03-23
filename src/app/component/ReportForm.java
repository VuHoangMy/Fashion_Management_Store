package app.component;

import app.BUS.BillSellList;
import app.BUS.ItemsList;
import app.BUS.StaffList;
import app.CommonFuncion.CommonFunction;
import app.CommonFuncion.CurrentUserLogin;
import app.DTO.BillImportGoods;
import app.DTO.BillSell;
import app.DTO.Items;
import app.DTO.Staff;
import java.awt.Font;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class ReportForm extends javax.swing.JPanel {

    private StaffList staffList;
    private BillSellList billSellList;

    private LocalDate today = LocalDate.now();

    public ReportForm() {
        initComponents();

        setStyleOfScrollPanel();
    }

    private void setStyleOfScrollPanel() {
        CommonFunction.initScrollbar(ScrollPane1);
        CommonFunction.initScrollbar(ScrollPane2);
        CommonFunction.initScrollbar(ScrollPane3);
        
        this.btn_FillterByDate.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        this.btn_FillterByDate2.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
    }

    public void loadDataFromFile() {
        this.staffList = new StaffList();
        staffList.initFile();
        staffList.readStaffFromFile();

        this.billSellList = new BillSellList();
        billSellList.initFile();
        billSellList.readFromFile();
        loadDataSaleTodayTable();
        if (CurrentUserLogin.getCurrentStaff().getCurrentuserLogin().getRole().equals("Cashier")) {

            this.btn_FillterByDate.setEnabled(false);
            this.btn_FillterByDate2.setEnabled(false);
            return;
        }
        loadDataTurnoverTable(this.yearChooser.getYear());
        loadDataToSalesTable(this.monthChooser.getMonth() + 1, this.yearChooser2.getYear());
    }

    private Vector addToVectorBillTable(BillSell bill, int amount) {
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

        v.add(amount);

        v.add(CommonFunction.formatMoney(bill.getTotalInvoice()));

        return v;
    }

    private int totalAmountSalesInMonth;
    private double totalMoneySalesInMonth;

    private void getTotalAmountAndTotalMoneySalesInMonth(int month, int year) {
        this.totalAmountSalesInMonth = 0;
        this.totalMoneySalesInMonth = 0;
        for (BillSell billSell : this.billSellList.getBillSellList()) {
            if (billSell.getDateSell().getMonthValue() == month
                    && billSell.getDateSell().getYear() == year) {
                for (Items item : billSell.getItemsList()) {
                    this.totalAmountSalesInMonth += item.getInventoryNumber();
                }
                this.totalMoneySalesInMonth += billSell.getTotalInvoice();
            }
        }
    }

    private int totalMoneyImportInMonth;

    private void getTotalMoneyImportGoodsInMonth(int month, int year) {
        this.totalMoneyImportInMonth = 0;
        for (Staff staff : staffList.getStaffList()) {
            if (staff.getBillImportGoodses() == null) {
                continue;
            }
            for (BillImportGoods bill : staff.getBillImportGoodses()) {
                if (bill.getDateImportGoods().getMonthValue() == month && bill.getDateImportGoods().getYear() == year) {
                    this.totalMoneyImportInMonth += bill.getPriceImport();
                }
            }
        }
    }

    private String headerTurnOver[] = {"Month", "Amount products sold", "Total Sales", "Total Import", "Total Discount", "Total Turnoverl"};
    private DefaultTableModel defaultTableModelTurnover = new DefaultTableModel(headerTurnOver, 0);

    private void loadDataTurnoverTable(int year) {
        this.lbl_Title2.setText("Turnover of - " + String.valueOf(today.getYear()));
        defaultTableModelTurnover.setRowCount(0);

        for (int month = 1; month <= 12; month++) {
            getTotalAmountAndTotalMoneySalesInMonth(month, year);
            getTotalMoneyImportGoodsInMonth(month, year);

            Vector<Object> v = new Vector<>();
            v.add(month);
            v.add(this.totalAmountSalesInMonth);
            v.add(CommonFunction.formatMoney(this.totalMoneySalesInMonth));
            v.add(CommonFunction.formatMoney(this.totalMoneyImportInMonth));
            v.add(0);
            double totalTurnover = this.totalMoneySalesInMonth - this.totalMoneyImportInMonth;
            v.add(CommonFunction.formatMoney(totalTurnover));
            defaultTableModelTurnover.addRow(v);
        }
        this.tbl_Turnover.setModel(defaultTableModelTurnover);
    }

    private String headerTurnoverToday[] = {"ID", "Employee", "Time", "Customer", "Phonenumber", "Amount", "Total"};
    private DefaultTableModel defaultTMTurnoverToday = new DefaultTableModel(headerTurnoverToday, 0);

    private void loadDataSaleTodayTable() {
        this.lbl_Title.setText("Report Import - " + today);
        int amountSaleToday = 0;
        double totalSaleToday = 0;

        defaultTMTurnoverToday.setRowCount(0);
        Collections.sort(billSellList.getBillSellList(), BillSell.billSellDateCompareASC);
        for (BillSell bill : billSellList.getBillSellList()) {
            if (!bill.getDateSell().toLocalDate().isEqual(LocalDate.now())) {
                continue;
            }
            int tmpAmount = 0;
            for (Items item : bill.getItemsList()) {
                amountSaleToday += item.getInventoryNumber();
                tmpAmount += item.getInventoryNumber();
            }
            totalSaleToday += bill.getTotalInvoice();

            Vector<Object> v = addToVectorBillTable(bill, tmpAmount);
            defaultTMTurnoverToday.addRow(v);
        }
        this.tbl_SalesToday.setModel(defaultTMTurnoverToday);

        this.lbl_TotalSaleToday.setText(CommonFunction.formatMoney(totalSaleToday) + ".");
        this.lbl_AmountSaleToday.setText(String.valueOf(amountSaleToday) + ".");
    }

    private String headerSalesTable[] = {"ID", "Name", "Amount"};
    private DefaultTableModel defaultTMSalesTable = new DefaultTableModel(headerSalesTable, 0);

    private void loadDataToSalesTable(int month, int year) {
        this.lbl_Title3.setText("Report Sale - " + year + " - " + month);
        ItemsList tmpList = new ItemsList();
        BillSellList billSellList = new BillSellList();
        billSellList.initFile();
        billSellList.readFromFile();
        for (BillSell bill : billSellList.getBillSellList()) {
            if (bill.getDateSell().toLocalDate().getMonthValue() != month || bill.getDateSell().toLocalDate().getYear() != year) {
                continue;
            }
            for (Items item : bill.getItemsList()) {
                if (tmpList.getItemslist().isEmpty() || tmpList.findItemsByID(item.getID()) == null) {
                    tmpList.addNewItem(item);
                } else {
                    int oldInventory = tmpList.findItemsByID(item.getID()).getInventoryNumber();
                    int newInventory = item.getInventoryNumber() + oldInventory;            
                    tmpList.findItemsByID(item.getID()).setInventoryNumber(newInventory);
                }
            }
        }

        defaultTMSalesTable.setRowCount(0);
        for (Items item : tmpList.getItemslist()) {
            Vector<Object> v = new Vector<>();
            v.add(item.getID());
            v.add(item.getName());
            v.add(item.getInventoryNumber());
            defaultTMSalesTable.addRow(v);
        }
        this.tbl_Sales.setModel(defaultTMSalesTable);
    }

    private void eventBackToPreviousPage() {
        if (CurrentUserLogin.getCurrentStaff().getCurrentuserLogin().getRole().equals("Cashier")) {
            CommonFunction.backToMainMenu();
            return;
        }
        CommonFunction.backToTransactionMenu();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Title = new javax.swing.JLabel();
        TabbedPane = new javax.swing.JTabbedPane();
        panel_ReportDay = new javax.swing.JPanel();
        ScrollPane1 = new javax.swing.JScrollPane();
        tbl_SalesToday = new javax.swing.JTable();
        lbl_Title = new javax.swing.JLabel();
        lbl_AmountSaleToday = new javax.swing.JLabel();
        lbl_TotalSaleToday = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panel_ReportTurnover = new javax.swing.JPanel();
        ScrollPane2 = new javax.swing.JScrollPane();
        tbl_Turnover = new javax.swing.JTable();
        yearChooser = new com.toedter.calendar.JYearChooser();
        btn_FillterByDate = new app.swing.Button();
        lbl_Title2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        panel_ReportSales = new javax.swing.JPanel();
        ScrollPane3 = new javax.swing.JScrollPane();
        tbl_Sales = new javax.swing.JTable();
        yearChooser2 = new com.toedter.calendar.JYearChooser();
        monthChooser = new com.toedter.calendar.JMonthChooser();
        btn_FillterByDate2 = new app.swing.Button();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbl_Title3 = new javax.swing.JLabel();
        btn_Home = new app.swing.Button();
        btn_Back = new app.swing.Button();
        Background = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1800, 900));
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Title.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Title.setForeground(new java.awt.Color(255, 255, 255));
        Title.setText("Reporting");
        add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, -1));

        TabbedPane.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));

        panel_ReportDay.setBackground(new java.awt.Color(204, 204, 204,1));
        panel_ReportDay.setOpaque(false);
        panel_ReportDay.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_SalesToday.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tbl_SalesToday.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_SalesToday.setOpaque(false);
        tbl_SalesToday.setRowHeight(25);
        tbl_SalesToday.setRowMargin(5);
        ScrollPane1.setViewportView(tbl_SalesToday);

        panel_ReportDay.add(ScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 1040, -1));

        lbl_Title.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        lbl_Title.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Title.setText("Money");
        panel_ReportDay.add(lbl_Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, -1, -1));

        lbl_AmountSaleToday.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        lbl_AmountSaleToday.setForeground(new java.awt.Color(255, 255, 153));
        lbl_AmountSaleToday.setText("0.");
        panel_ReportDay.add(lbl_AmountSaleToday, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 580, -1, 50));

        lbl_TotalSaleToday.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        lbl_TotalSaleToday.setForeground(new java.awt.Color(204, 0, 0));
        lbl_TotalSaleToday.setText("0 VND.");
        panel_ReportDay.add(lbl_TotalSaleToday, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 640, -1, 50));

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Total Turnover Today: ");
        panel_ReportDay.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 640, -1, 50));

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("The number of product sales today: ");
        panel_ReportDay.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 580, 330, 50));

        TabbedPane.addTab("Day revenue report", panel_ReportDay);

        panel_ReportTurnover.setOpaque(false);
        panel_ReportTurnover.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_Turnover.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tbl_Turnover.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Month", "Amount products sold", "Total Sales", "Total Import", "Total Discount", "Total Turnover"
            }
        ));
        tbl_Turnover.setOpaque(false);
        tbl_Turnover.setRowHeight(25);
        tbl_Turnover.setRowMargin(5);
        ScrollPane2.setViewportView(tbl_Turnover);

        panel_ReportTurnover.add(ScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 190, 1110, -1));
        panel_ReportTurnover.add(yearChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 80, 30));

        btn_FillterByDate.setForeground(new java.awt.Color(153, 255, 255));
        btn_FillterByDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/filter.png"))); // NOI18N
        btn_FillterByDate.setText("Fillter");
        btn_FillterByDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FillterByDateActionPerformed(evt);
            }
        });
        panel_ReportTurnover.add(btn_FillterByDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, -1, -1));

        lbl_Title2.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        lbl_Title2.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Title2.setText("Report Turnover");
        panel_ReportTurnover.add(lbl_Title2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, -1, -1));

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Year");
        panel_ReportTurnover.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, -1, -1));

        jLabel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fillter By Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Comic Sans MS", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        panel_ReportTurnover.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 260, 180));

        TabbedPane.addTab("Turnover", panel_ReportTurnover);

        panel_ReportSales.setOpaque(false);
        panel_ReportSales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_Sales.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tbl_Sales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Amount"
            }
        ));
        tbl_Sales.setOpaque(false);
        tbl_Sales.setRowHeight(25);
        tbl_Sales.setRowMargin(5);
        ScrollPane3.setViewportView(tbl_Sales);

        panel_ReportSales.add(ScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, 880, -1));

        yearChooser2.setOpaque(false);
        panel_ReportSales.add(yearChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 240, 107, 30));

        monthChooser.setOpaque(false);
        panel_ReportSales.add(monthChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, -1, 30));

        btn_FillterByDate2.setForeground(new java.awt.Color(102, 204, 255));
        btn_FillterByDate2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/filter.png"))); // NOI18N
        btn_FillterByDate2.setText("Fillter");
        btn_FillterByDate2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FillterByDate2ActionPerformed(evt);
            }
        });
        panel_ReportSales.add(btn_FillterByDate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 290, 107, 48));

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Year");
        panel_ReportSales.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 40, -1));

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Month");
        panel_ReportSales.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 60, -1));

        jLabel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fillter By Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Comic Sans MS", 1, 15), new java.awt.Color(255, 255, 255))); // NOI18N
        panel_ReportSales.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 420, 200));

        lbl_Title3.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        lbl_Title3.setForeground(new java.awt.Color(255, 255, 0));
        lbl_Title3.setText("Report Sale");
        panel_ReportSales.add(lbl_Title3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, -1, -1));

        TabbedPane.addTab("Sales", panel_ReportSales);

        add(TabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 1620, 750));

        btn_Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/home.png"))); // NOI18N
        btn_Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HomeActionPerformed(evt);
            }
        });
        add(btn_Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, -1, -1));

        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor 2.jpg"))); // NOI18N
        add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1800, 900));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        eventBackToPreviousPage();
    }//GEN-LAST:event_btn_BackActionPerformed

    private void btn_HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HomeActionPerformed
        eventBackToPreviousPage();
        if (CurrentUserLogin.getCurrentStaff().getCurrentuserLogin().getRole().equals("Cashier")) {
            return;
        };
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_HomeActionPerformed

    private void btn_FillterByDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FillterByDateActionPerformed
        loadDataTurnoverTable(this.yearChooser.getYear());
    }//GEN-LAST:event_btn_FillterByDateActionPerformed

    private void btn_FillterByDate2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FillterByDate2ActionPerformed
        loadDataToSalesTable(this.monthChooser.getMonth() + 1, this.yearChooser2.getYear());
    }//GEN-LAST:event_btn_FillterByDate2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JScrollPane ScrollPane1;
    private javax.swing.JScrollPane ScrollPane2;
    private javax.swing.JScrollPane ScrollPane3;
    private javax.swing.JTabbedPane TabbedPane;
    private javax.swing.JLabel Title;
    private app.swing.Button btn_Back;
    private app.swing.Button btn_FillterByDate;
    private app.swing.Button btn_FillterByDate2;
    private app.swing.Button btn_Home;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lbl_AmountSaleToday;
    private javax.swing.JLabel lbl_Title;
    private javax.swing.JLabel lbl_Title2;
    private javax.swing.JLabel lbl_Title3;
    private javax.swing.JLabel lbl_TotalSaleToday;
    private com.toedter.calendar.JMonthChooser monthChooser;
    private javax.swing.JPanel panel_ReportDay;
    private javax.swing.JPanel panel_ReportSales;
    private javax.swing.JPanel panel_ReportTurnover;
    private javax.swing.JTable tbl_Sales;
    private javax.swing.JTable tbl_SalesToday;
    private javax.swing.JTable tbl_Turnover;
    private com.toedter.calendar.JYearChooser yearChooser;
    private com.toedter.calendar.JYearChooser yearChooser2;
    // End of variables declaration//GEN-END:variables
}

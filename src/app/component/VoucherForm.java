package app.component;

import app.BUS.VoucherList;
import app.CommonFuncion.CommonFunction;
import app.DTO.Voucher;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VoucherForm extends javax.swing.JPanel {

    private VoucherList voucherList;
    private String header[] = {"ID", "Name", "From Date", "To Date", "Total Condition", "Note"};
    private DefaultTableModel defaultTableModel = new DefaultTableModel(header, 0);

    public VoucherForm() {
        initComponents();
        initTable();
    }

    public void initTable() {
        table.setModel(defaultTableModel);
        CommonFunction.initScrollbar(scrollPane);
        loadDataFromFile();
    }

    public void loadDataFromFile() {
        voucherList = new VoucherList();
        voucherList.initFile();
        voucherList.readFromFile();
        loadDataTable(voucherList);
        setStatusButton(false);
    }

    private boolean isValidateDataInput() {
        if (this.txt_Name.getText().isEmpty() || this.txt_TotalMoney.getText().isEmpty() || this.txt_DiscountPercent.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long nhap du thong tin.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private Vector addToVector(Voucher vou) {
        Vector<Object> v = new Vector<>();
        v.add(vou.getID());
        v.add(vou.getName());
        v.add(vou.getFromDate());
        v.add(vou.getToDate());
        v.add(CommonFunction.formatMoney(vou.getTotalMoneyCondition()));
        v.add(vou.getNote());
        return v;
    }

    public void loadDataTable(VoucherList voucherList) {

        defaultTableModel.setRowCount(0);
        for (Voucher vou : voucherList.getVoucherList()) {
            Vector<Object> v = addToVector(vou);
            defaultTableModel.addRow(v);
        }
        table.setModel(defaultTableModel);

        this.btn_Clear.setEnabled(false);
        setStatusButton(false);
        if (table.getRowCount() > 0) {
            this.btn_Clear.setEnabled(true);
        }
    }

    private void setStatusButton(boolean isEnable) {
        this.btn_Edit.setEnabled(isEnable);
        this.btn_Remove.setEnabled(isEnable);
    }

    private Voucher createNewVoucher() {
        String name = this.txt_Name.getText();
        String note = this.txt_Note.getText();
        double cMoney = Double.parseDouble(this.txt_TotalMoney.getText());
        LocalDate fromDate = CommonFunction.convertToLocalDateViaSqlDate(this.DChooser_FromDate.getDate());
        LocalDate toDate = CommonFunction.convertToLocalDateViaSqlDate(this.DChooser_ToDate.getDate());
        double discountPercent = Double.parseDouble(this.txt_DiscountPercent.getText());
        return new Voucher(voucherList.createVoucherID(), name, fromDate, toDate, note, cMoney, discountPercent);

    }

    private void setContainOfComponent(String name, String note, double totalMoney, LocalDate fromDate, LocalDate toDate) throws ParseException {
        this.txt_Name.setText(name);
        this.txt_Note.setText(note);
        this.txt_TotalMoney.setText(String.valueOf(totalMoney));
        this.DChooser_FromDate.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate.toString()));
        this.DChooser_ToDate.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(toDate.toString()));
    }

    // CRUD
    private void eventSearchVoucher(String txtSearch) {
        VoucherList v = this.voucherList.findListVoucherByIDOrName(txtSearch);
        loadDataTable(v);
    }

    private void eventAddNewVoucher() {
//        Voucher voucher = new Voucher(voucherList.createVoucherID(), "Summer happy", 
//                LocalDate.now(), LocalDate.now(), "", 1200000,30);
        Voucher voucher = createNewVoucher();
        this.voucherList.addNewVoucher(voucher);
        this.voucherList.appendAVoucherIntoFile(voucher);
        this.loadDataTable(voucherList);
    }

    private void eventRemoveVoucher() {
        boolean result = CommonFunction.isConfirmDialog("Remove", "Xac nhan xoa voucher !");
        if (result) {
            int column = 0;
            int row = table.getSelectedRow();
            String ID = table.getModel().getValueAt(row, column).toString();
            this.voucherList.getVoucherList().remove(this.voucherList.findVoucherByID(ID));
            this.voucherList.writeToFile();
            loadDataTable(voucherList);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        txt_Search = new javax.swing.JTextField();
        btn_Search = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_Name = new javax.swing.JTextField();
        btn_Remove = new app.swing.Button();
        jLabel7 = new javax.swing.JLabel();
        btn_Clear = new app.swing.Button();
        btn_Edit = new app.swing.Button();
        btn_Printer = new app.swing.Button();
        txt_Note = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_TotalMoney = new javax.swing.JTextField();
        btn_Add = new app.swing.Button();
        btn_Back = new app.swing.Button();
        DChooser_ToDate = new com.toedter.calendar.JDateChooser();
        DChooser_FromDate = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_DiscountPercent = new javax.swing.JTextField();
        BackgroundCreateNewProduct = new javax.swing.JLabel();
        BackGround = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Voucher");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, -1, -1));

        table.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "From Date", "To Date", "Total Condition", "Note"
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

        add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 1090, 230));

        txt_Search.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Search.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txt_Search.setForeground(new java.awt.Color(204, 204, 204));
        txt_Search.setText("Search");
        txt_Search.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 153, 255)));
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
        add(txt_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 510, 40));

        btn_Search.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Search.png"))); // NOI18N
        add(btn_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, -1, 60));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Voucher");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 300, 120, 30));

        txt_Name.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_Name.setFont(new java.awt.Font("Segoe UI Historic", 0, 15)); // NOI18N
        txt_Name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 204)));
        txt_Name.setOpaque(false);
        add(txt_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 300, 260, 30));

        btn_Remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Delete.png"))); // NOI18N
        btn_Remove.setText("Remove");
        btn_Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RemoveActionPerformed(evt);
            }
        });
        add(btn_Remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(1480, 730, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("To Date");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 650, -1, -1));

        btn_Clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Clear.png"))); // NOI18N
        btn_Clear.setText("Clear All");
        btn_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ClearActionPerformed(evt);
            }
        });
        add(btn_Clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(1600, 730, 110, -1));

        btn_Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Edit.png"))); // NOI18N
        btn_Edit.setText("Edit");
        add(btn_Edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 730, 100, -1));

        btn_Printer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Printer.png"))); // NOI18N
        btn_Printer.setText("Printer");
        add(btn_Printer, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 140, -1, -1));

        txt_Note.setBackground(new java.awt.Color(0, 0, 0,1));
        txt_Note.setColumns(20);
        txt_Note.setFont(new java.awt.Font("Segoe UI Historic", 0, 15)); // NOI18N
        txt_Note.setRows(5);
        txt_Note.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 153, 204)));
        txt_Note.setOpaque(false);
        add(txt_Note, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 370, 260, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Discount %");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 580, 100, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("From Date");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 650, -1, -1));

        txt_TotalMoney.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_TotalMoney.setFont(new java.awt.Font("Segoe UI Historic", 0, 15)); // NOI18N
        txt_TotalMoney.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 204)));
        txt_TotalMoney.setOpaque(false);
        add(txt_TotalMoney, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 500, 260, 30));

        btn_Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Add.png"))); // NOI18N
        btn_Add.setText("Add");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });
        add(btn_Add, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 730, 110, -1));

        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Back.png"))); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });
        add(btn_Back, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 70, -1));

        DChooser_ToDate.setOpaque(false);
        add(DChooser_ToDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1550, 650, 160, -1));

        DChooser_FromDate.setOpaque(false);
        add(DChooser_FromDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 650, 160, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Note");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 400, 60, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Total Money");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 500, 100, 30));

        txt_DiscountPercent.setBackground(new java.awt.Color(0, 0, 0, 1));
        txt_DiscountPercent.setFont(new java.awt.Font("Segoe UI Historic", 0, 15)); // NOI18N
        txt_DiscountPercent.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 204)));
        txt_DiscountPercent.setOpaque(false);
        add(txt_DiscountPercent, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 580, 260, 30));

        BackgroundCreateNewProduct.setToolTipText("");
        BackgroundCreateNewProduct.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "New Voucher", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 15), new java.awt.Color(255, 255, 255))); // NOI18N
        add(BackgroundCreateNewProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 250, 550, 600));

        BackGround.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor5.jpg"))); // NOI18N
        add(BackGround, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
//        if (!isValidateDataInput()) {
//            return;
//        }
        eventAddNewVoucher();
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RemoveActionPerformed
        eventRemoveVoucher();
    }//GEN-LAST:event_btn_RemoveActionPerformed

    private void btn_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ClearActionPerformed
        try {
            setContainOfComponent("", "", 0, LocalDate.now(), LocalDate.now());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btn_ClearActionPerformed

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        CommonFunction.backToMainMenu();
    }//GEN-LAST:event_btn_BackActionPerformed

    private void txt_SearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusGained
        CommonFunction.setPlaceHolder(txt_Search, true, "Search");
    }//GEN-LAST:event_txt_SearchFocusGained

    private void txt_SearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SearchFocusLost
        CommonFunction.setPlaceHolder(txt_Search, false, "Search");
    }//GEN-LAST:event_txt_SearchFocusLost

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        setStatusButton(true);
    }//GEN-LAST:event_tableMouseClicked

    private void txt_SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyReleased
       eventSearchVoucher(this.txt_Search.getText());
    }//GEN-LAST:event_txt_SearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackGround;
    private javax.swing.JLabel BackgroundCreateNewProduct;
    private com.toedter.calendar.JDateChooser DChooser_FromDate;
    private com.toedter.calendar.JDateChooser DChooser_ToDate;
    private app.swing.Button btn_Add;
    private app.swing.Button btn_Back;
    private app.swing.Button btn_Clear;
    private app.swing.Button btn_Edit;
    private app.swing.Button btn_Printer;
    private app.swing.Button btn_Remove;
    private javax.swing.JLabel btn_Search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_DiscountPercent;
    private javax.swing.JTextField txt_Name;
    private javax.swing.JTextArea txt_Note;
    private javax.swing.JTextField txt_Search;
    private javax.swing.JTextField txt_TotalMoney;
    // End of variables declaration//GEN-END:variables
}

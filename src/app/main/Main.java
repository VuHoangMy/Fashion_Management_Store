package app.main;

import app.CommonFuncion.CommonFunction;
import app.CommonFuncion.CurrentUserLogin;
import app.DTO.Staff;
import app.component.BillForm;
import app.component.CustomerForm;
import app.component.Menu;
import app.event.EventMenu;
//import app.slideshow.Slideshow;
import java.awt.Color;

import app.component.ItemsForm;
import app.component.ProductForm;
import app.component.ProfileForm;
import app.component.ReportForm;
import app.component.SellForm;
import app.component.StaffManagerForm;
import app.component.SupplierForm;
import app.component.TransactionForm;

import app.component.VoucherForm;
import java.awt.Component;
import java.io.File;
import javax.swing.ImageIcon;

public class Main extends javax.swing.JFrame {

    private Staff currentStaff;
    private String[] listMenu;
    private Component[] coms;

    //Form
    private StaffManagerForm staffForm;
    private ProductForm productForm;
    private ItemsForm itemsForm;
    private TransactionForm transactionForm;
    private BillForm billForm;
    private VoucherForm voucherForm;
    private CustomerForm customer;
    private ProfileForm profileForm;
    private SupplierForm supplierForm;
    private SellForm sellForm;
    private ReportForm reportForm;

    public Main() {
        initComponents();
        setIconImage(new ImageIcon(new File("src\\app\\Icons\\AppFashionIcon.png").getAbsolutePath()).getImage());
        getContentPane().setBackground(new Color(236, 236, 236));
        MainProgram.getInstance().setSlideshow(slideshow);
    }

    public void initMain() {
        this.currentStaff = CurrentUserLogin.getCurrentStaff().getCurrentuserLogin();
        setLabelUserInfo(currentStaff.getName(), currentStaff.getRole());
        this.imageAvatar.setIcon(new ImageIcon(currentStaff.getImageProfile().getAbsolutePath()));
        initMenu();
    }

    private void initMenu() {
        EventMenu event = new EventMenu() {
            @Override
            public void selectMenu(int index) {
                if (index == 1000) { // LogOut
                    logOut();
                } else {
                    slideshow.slideTo(index + 1);
                    loadData(coms[slideshow.getCurrentIndex()].getClass().getName());
                }

            }
        };
        Menu menu = new Menu();
        initForm();
        setFuncForRole(this.currentStaff, menu); // take function can use for current user
        menu.setListMenu(listMenu); //Send lists name of Menu to Menu form
        menu.initMenu(event);
        slideshow.initSlideshow(this.coms);
    }

    private void setFuncForRole(Staff currentStaff, Menu menu) {
        switch (currentStaff.getRole()) {
            case "Owner":
                this.listMenu = new String[]{"Staff", "Products", "Items", "Transactions", "Bill", "Voucher", "Customer", "Profile", "Supplier"};
                this.coms = new Component[]{menu, this.staffForm, productForm, itemsForm,
                    this.transactionForm, this.billForm, this.voucherForm, this.customer, this.profileForm, this.supplierForm};
                break;
            case "Cashier":
                this.listMenu = new String[]{"Sale", "Report", "Profile"};
                this.coms = new Component[]{menu, this.sellForm, this.reportForm, this.profileForm};
                break;
        }
    }

    private void setLabelUserInfo(String name, String Role) {
        this.lbl_User.setText(name);
        this.lbl_Role.setText("| " + Role);
    }

    private void initForm() {
        if (this.currentStaff.getRole().equals("Owner")){
            this.staffForm = new StaffManagerForm();
            this.productForm = new ProductForm();
            this.itemsForm = new ItemsForm();
            this.transactionForm = new TransactionForm();
            this.billForm = new BillForm();
            this.voucherForm = new VoucherForm();
            this.customer = new CustomerForm();
            this.supplierForm = new SupplierForm();
        } else if (this.currentStaff.getRole().equals("Cashier")) {
            this.sellForm = new SellForm();
            this.reportForm = new ReportForm();
        }
        this.profileForm = new ProfileForm();
    }

    private void loadData(String menu) {
        if (menu.equals(this.profileForm.getClass().getName())) {
            try {
                this.profileForm.loadDataToComponent();
            } catch (Exception e) {
            }
            return;
        }

        if (this.currentStaff.getRole().equals("Cashier")) {
            if (menu.equals(this.sellForm.getClass().getName())) {
                this.sellForm.loadDataFromFile();
            } else if (menu.equals(this.reportForm.getClass().getName())) {
                this.reportForm.loadDataFromFile();
            }
            return;
        }

        if (menu.equals(this.staffForm.getClass().getName())) {

            if (!this.staffForm.getIsLoaded()) {
                this.staffForm.loadComponentAndMenu();
                this.staffForm.setIsLoaded(true);
            }
        } else if (menu.equals(this.productForm.getClass().getName())) {
            this.productForm.loadDataFromFile();
        } else if (menu.equals(this.itemsForm.getClass().getName())) {
            this.itemsForm.loadDataFromFile();
        } else if (menu.equals(this.transactionForm.getClass().getName())) {
            if (!this.transactionForm.getIsLoaded()) {
                this.transactionForm.loadComponentAndMenu();
                this.transactionForm.setIsLoaded(true);
            }
        } else if (menu.equals(this.billForm.getClass().getName())) {
            if (!this.billForm.getIsLoaded()) {
                this.billForm.loadComponentAndMenu();
                this.billForm.setIsLoaded(true);
            }
        } else if (menu.equals(this.voucherForm.getClass().getName())) {

        } else if (menu.equals(this.supplierForm.getClass().getName())) {
            this.supplierForm.initTable();
        } else if (menu.equals(this.customer.getClass().getName())) {
            this.customer.initTable();
        } else {
            System.out.println("Some Thing Wrong (Main-loadData())");
        }

    }

    private void logOut() {
        boolean result = CommonFunction.isConfirmDialog("Logout", "Ban Chac Chan muon dang xuat ?");
        if (!result) {
            return;
        }
        CommonFunction.logout(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonClose1 = new app.swing.ButtonClose();
        imageAvatar = new app.swing.ImageAvatar();
        lbl_Role = new javax.swing.JLabel();
        lbl_User = new javax.swing.JLabel();
        pictureBox = new app.swing.PictureBox();
        slideshow = new app.slideshow.Slideshow();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusCycleRoot(false);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(buttonClose1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1760, 20, -1, -1));

        imageAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/profile IMG.jpg"))); // NOI18N
        getContentPane().add(imageAvatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1680, 20, 60, 50));

        lbl_Role.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        lbl_Role.setForeground(new java.awt.Color(0, 204, 204));
        lbl_Role.setText("| Manager");
        getContentPane().add(lbl_Role, new org.netbeans.lib.awtextra.AbsoluteConstraints(1580, 30, 100, -1));

        lbl_User.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        lbl_User.setForeground(new java.awt.Color(204, 255, 0));
        lbl_User.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_User.setText("Name");
        lbl_User.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(lbl_User, new org.netbeans.lib.awtextra.AbsoluteConstraints(1380, 30, 190, 30));

        pictureBox.setImage(new javax.swing.ImageIcon(getClass().getResource("/app/Images/Background2.jpg"))); // NOI18N
        pictureBox.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pictureBox.add(slideshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1810, 900));

        getContentPane().add(pictureBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, -5, 1805, 900));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Main().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.swing.ButtonClose buttonClose1;
    private app.swing.ImageAvatar imageAvatar;
    private javax.swing.JLabel lbl_Role;
    private javax.swing.JLabel lbl_User;
    private app.swing.PictureBox pictureBox;
    private app.slideshow.Slideshow slideshow;
    // End of variables declaration//GEN-END:variables
}

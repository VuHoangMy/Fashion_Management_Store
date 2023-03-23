package app.GUI;

import app.BUS.ItemsList;
import app.BUS.StaffList;
import app.DTO.Items;
import app.DTO.Staff;
import app.DTO.Timekeeping;
import app.component.SellForm;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.JOptionPane;

public class ScanQRCodeForm extends javax.swing.JFrame implements Runnable, ThreadFactory {

    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);

    private int chooseOption;
    private SellForm sellForm;

    private StaffList staffLists;
    private ItemsList itemsList;

    public ScanQRCodeForm(int index, SellForm sellForm) {
        initComponents();
        initWebcam();
        loadDataFromFile();

        this.chooseOption = index;
        this.sellForm = sellForm;
        if (index == 0) {
            this.lbl_Title.setText("Timekeeping.");
        } else {
            this.lbl_Title.setText("Scan Items.");
        }
    }

    private void initWebcam() {
        Dimension size = WebcamResolution.QVGA.getSize();
        this.webcam = Webcam.getWebcams().get(0);
        this.webcam.setViewSize(size);

        this.panel = new WebcamPanel(webcam);
        this.panel.setPreferredSize(size);
        this.panel.setFPSDisplayed(true);

        webcam_Panel.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 300));
        this.webcam_Panel.repaint();
        this.webcam_Panel.revalidate();
        executor.execute(this);
    }

    private void loadDataFromFile() {
        this.staffLists = new StaffList();
        staffLists.initFile();
        staffLists.readStaffFromFile();

        itemsList = new ItemsList();
        itemsList.initFile();
        itemsList.readFromFile();
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
//                Logger.getLogger(ScanQRCodeForm.class.getName()).log(Level.SEVERE, null, ex);
            }

            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                result = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException ex) {
//                Logger.getLogger(ScanQRCodeForm.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result != null) {
                String ID = result.getText();
                lbl_Result.setText(ID);

                // 0. Scan Staff
                // 1. Scan Items
                if (this.chooseOption == 0) {
                    ScanIDStaff(ID);
                } else {
                    scanIDItems(ID);
                }

            }

        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }

    private void closeNotificate() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
            @Override
            public void run() {
                // write code here
            }
        },
                5000
        );
    }

    private void ScanIDStaff(String ID) {
        try {

            Staff staff = staffLists.findStaffByID(ID);

            LocalDate today = LocalDate.now();
            ArrayList<Timekeeping> timekeepingLists = staff.getTimekeepingLists();
            int indexLastTimekeeping = timekeepingLists.size() - 1;
            boolean isHaveTimeEnd = false;
            boolean isToday = false;

            if (!timekeepingLists.isEmpty()) {
                isHaveTimeEnd = timekeepingLists.get(indexLastTimekeeping).getTimeEnd() != null;
                isToday = timekeepingLists.get(indexLastTimekeeping).getDays().isEqual(today);
            }

            // if has check all timekeeping
            if (isToday && isHaveTimeEnd) {
                JOptionPane.showMessageDialog(null, "Staff ID: " + ID + " da cham cong cho ngay hom nay.", "QRCode", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            boolean check = timekeepingLists.isEmpty() ? false : isToday && !isHaveTimeEnd;

            if (check) {
                // if had get in
                timekeepingLists.get(indexLastTimekeeping).setTimeEnd(LocalTime.now());
            } else {
                Timekeeping newTimekeeping = new Timekeeping(today, LocalTime.now(), null);
                staff.addNewTimekeeping(newTimekeeping);
            }
            System.out.println("Timekeping Lists Final : " + staff.getID() + " - " + timekeepingLists);

            staffLists.writeStaffIntoFile();
            JOptionPane.showMessageDialog(null, "Staff ID: " + ID, "QRCode", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
//            System.out.println("Some thing wrong when sacn timekeeping");
            JOptionPane.showMessageDialog(null, "Ma QR khong hop le!", "QRCode ERROR", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void scanIDItems(String ID) {
        Items items = itemsList.findItemsByID(ID);
        if (items == null) {
            JOptionPane.showMessageDialog(null, "Item ID: " + ID + " Khong Tim thay san pham nay", "QRCode", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (items.getInventoryNumber() < 1) {
            JOptionPane.showMessageDialog(null, "Item ID: " + ID + " Da het hang.", "QRCode", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (this.sellForm.isItemHaveInCart(ID) && this.sellForm.isOutOfStock(ID)) {
            JOptionPane.showMessageDialog(null, "Item ID: " + ID + " Da het hang.", "QRCode", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        this.sellForm.addToTMPList(ID);

        String format = String.format(" Add success Item have ID: " + ID);
        JOptionPane.showMessageDialog(null, format, "QRCode", JOptionPane.INFORMATION_MESSAGE);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        webcam_Panel = new javax.swing.JPanel();
        btn_Close = new app.swing.Button();
        Logo = new javax.swing.JLabel();
        lbl_Title = new javax.swing.JLabel();
        lbl_Result = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(570, 405));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(570, 405));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        webcam_Panel.setBackground(new java.awt.Color(153, 204, 255));
        webcam_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(webcam_Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 500, 300));

        btn_Close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/x.png"))); // NOI18N
        btn_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CloseActionPerformed(evt);
            }
        });
        getContentPane().add(btn_Close, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, -1, -1));

        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Icons/Scan.png"))); // NOI18N
        getContentPane().add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        lbl_Title.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lbl_Title.setForeground(new java.awt.Color(255, 255, 102));
        lbl_Title.setText("Title Here");
        getContentPane().add(lbl_Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 180, 20));

        lbl_Result.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lbl_Result.setForeground(new java.awt.Color(51, 255, 51));
        lbl_Result.setText("Result");
        getContentPane().add(lbl_Result, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 420, 30));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/Images/BackgroundColor 2.jpg"))); // NOI18N
        getContentPane().add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 410));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CloseActionPerformed
        dispose();
        webcam.close();
    }//GEN-LAST:event_btn_CloseActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ScanQRCodeForm(0, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JLabel Logo;
    private app.swing.Button btn_Close;
    private javax.swing.JLabel lbl_Result;
    private javax.swing.JLabel lbl_Title;
    private javax.swing.JPanel webcam_Panel;
    // End of variables declaration//GEN-END:variables
}

package app.CommonFuncion;

import app.GUI.Login;
import app.main.MainProgram;
import app.scrollbar.ScrollBarCustom;
import java.awt.Color;
import java.awt.Image;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CommonFunction {

    public static void backToMainMenu() {
        MainProgram.getInstance().getSlideshow().slideTo(0);
    }

    public static void backToTransactionMenu() {
        MainProgram.getInstanceTransactionForm().getSlideshow().slideTo(0);
    }

    public static void backToBillMenu() {
        MainProgram.getInstanceBillForm().getSlideshow().slideTo(0);
    }

    public static void backToStaffMenu() {
        MainProgram.getInstanceStaffForm().getSlideshow().slideTo(0);
    }

    public static void setBackgroundButton(JLabel lbl, int r, int g, int b, int t) {
        lbl.setBackground(new java.awt.Color(r, g, b, t));
    }

    public static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void setPlaceHolder(JTextField txt_Search, boolean isFocus, String text) {
        if (isFocus) {
            if (txt_Search.getText().equals("Search")) {
                txt_Search.setText("");
            }
        } else {
            if (txt_Search.getText().isEmpty()) {
                txt_Search.setText("Search");
            }
        }
    }

    public static void initScrollbar(JScrollPane scrollPane) {
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setVerticalScrollBar(new ScrollBarCustom());
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        scrollPane.setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    public static void updateScrollBar(JComboBox box) {
        Object comp = box.getUI().getAccessibleChild(box, 0);

        if (comp instanceof JPopupMenu) {
            JPopupMenu popup = (JPopupMenu) comp;
            JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);
            initScrollbar(scrollPane);
        }
    }

    public static void logout(JFrame main) {
        MainProgram.setNullAllInstance();
        main.dispose();
        Login login = new Login();
        login.setVisible(true);
    }

    public static ImageIcon resizeImage(ImageIcon icon, int w, int h) {
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        icon = new ImageIcon(newimg);
        return icon;
    }

    public static String formatMoney(double money) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        return currencyVN.format(money);
    }

    public static double formatStringToMoney(String money) {
        String r = money.replace("đ", "").replace(".", "").replace(" ", "");
        if (isNumeric(r)) {
            return Double.parseDouble(r);
        }
        return -1000;
    }

    public static String formatLocalDateTime(LocalDateTime dateTime, String formatType) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatType);
        String formatDateTime = dateTime.format(formatter);
        return formatDateTime;
    }

    public static String formatLocalTimeToString(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        return time.format(formatter);
    }

    /*
    * Return true if format phonenumber is validate
    * Else return false
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.length() == 10;
    }

    public static boolean isConfirmDialog(String title, String message) {
        int respone = JOptionPane.showConfirmDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respone == JOptionPane.NO_OPTION || respone == JOptionPane.CLOSED_OPTION) {
            return false;
        }
        return true;
    }

}

//private void setEventTable() {
//        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent event) {
//                // do some actions here, for example
//                // print first column value from selected row
//                
//            }
//
//        });
//    }

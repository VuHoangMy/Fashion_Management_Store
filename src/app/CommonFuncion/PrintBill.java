package app.CommonFuncion;

import app.DTO.BillSell;
import app.DTO.Items;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class PrintBill {

    private Double totalAmount = 0.0;
    private Double cash = 0.0;
    private Double balance = 0.0;
    private Double bHeight = 0.0;

    private ArrayList<String> itemName;
    private ArrayList<String> quantity;
    private ArrayList<String> itemPrice;
    private ArrayList<String> subtotal;



    public PrintBill(BillSell billSell) {
        itemName = new ArrayList<>();
        quantity = new ArrayList<>();
        itemPrice = new ArrayList<>();
        subtotal = new ArrayList<>();

        for (Items items : billSell.getItemsList()) {
            itemName.add(items.getName());
            quantity.add(String.valueOf(items.getInventoryNumber()));
            itemPrice.add(CommonFunction.formatMoney(items.getPrice()));
            double subTT = items.getInventoryNumber() * items.getPrice();
            subtotal.add(CommonFunction.formatMoney(subTT));
        }

        bHeight = Double.valueOf(itemName.size());
        this.totalAmount = billSell.getTotalInvoice();
        this.cash = billSell.getMoneyCustomerGive();
        this.balance = Math.abs((this.totalAmount - this.cash));

        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new BillPrintable(), getPageFormat(pj));
        try {
            pj.print();

        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }

    public PageFormat getPageFormat(PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double bodyHeight = bHeight;
        double headerHeight = 5.0;
        double footerHeight = 5.0;
        double width = convert_CM_To_PPI(8);
        double height = convert_CM_To_PPI(headerHeight + bodyHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - convert_CM_To_PPI(1));

        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        return pf;
    }

    protected static double convert_CM_To_PPI(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }

    public class BillPrintable implements Printable {

        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                throws PrinterException {

            int r = itemName.size();
            ImageIcon icon = new ImageIcon("D:\\Code\\Java\\Java Project\\Code\\FashionManagerApplication\\src\\app\\ImageItems\\Shose6.jpg");
            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {

                Graphics2D g2d = (Graphics2D) graphics;
                double width = pageFormat.getImageableWidth();
                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

                //  FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
                try {
                    int y = 20;
                    int yShift = 10;
                    int headerRectHeight = 15;
                    // int headerRectHeighta=40;

                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    g2d.drawImage(icon.getImage(), 50, 20, 90, 30, null);
                    y += yShift + 30;
                    g2d.drawString("-------------------------------------", 12, y);
                    y += yShift;
                    g2d.drawString("         Fashion Store        ", 12, y);
                    y += yShift;
                    g2d.drawString("   "+LocalDate.now()+" ", 12, y);
                    y += yShift;
                    g2d.drawString("   Phù Đổng Thiên Vương, P8, TP Đà Lạt ", 12, y);
                    y += yShift;
                    g2d.drawString("   www.facebook.com/TrongQuynh.Han05 ", 12, y);
                    y += yShift;
                    g2d.drawString("        +94700000000      ", 12, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 12, y);
                    y += headerRectHeight;

                    g2d.drawString(" Item Name                  Price   ", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += headerRectHeight;

                    for (int s = 0; s < r; s++) {
                        g2d.drawString(" " + itemName.get(s) + "                            ", 10, y);
                        y += yShift;
                        g2d.drawString("      " + quantity.get(s) + " * " + itemPrice.get(s), 10, y);
                        g2d.drawString(subtotal.get(s), 160, y);
                        y += yShift;

                    }

                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    String ttAmount = CommonFunction.formatMoney(totalAmount);
                    g2d.drawString(" Total amount:               " + ttAmount + "   ", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    String strCash = CommonFunction.formatMoney(cash);
                    g2d.drawString(" Cash      :                 " + strCash + "   ", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    String strBalance = CommonFunction.formatMoney(balance);
                    g2d.drawString(" Balance   :                 " + strBalance + "   ", 10, y);
                    y += yShift;

                    g2d.drawString("*************************************", 10, y);
                    y += yShift;
                    g2d.drawString("       THANK YOU COME AGAIN            ", 10, y);
                    y += yShift;
                    g2d.drawString("*************************************", 10, y);
                    y += yShift;
                    g2d.drawString("       SOFTWARE BY:TrongQuynh          ", 10, y);
                    y += yShift;
                    g2d.drawString("   CONTACT: Fashion Store       ", 10, y);
                    y += yShift;

                } catch (Exception e) {
                    e.printStackTrace();
                }

                result = PAGE_EXISTS;
            }
            return result;
        }
    }

}

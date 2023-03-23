package app.component;

import app.event.EventMenu;
import app.main.MainProgram;
import java.awt.Color;
import java.awt.Component;

public class BillForm extends javax.swing.JPanel {

    private Component[] coms;
    private BillImportGoodsForm importGoodsForm;
    private BillSellForm billSellForm;
    
    private boolean isLoaded;

    public BillForm() {
        this.isLoaded = false;
    }

    public void loadComponentAndMenu() {
        initComponents();
        this.setBackground(new Color(236, 236, 236));
        MainProgram.getInstanceBillForm().setSlideshow(slideshow);

        EventMenu event = new EventMenu() {
            @Override
            public void selectMenu(int index) {
                slideshow.slideTo(index + 1);
                loadData(coms[slideshow.getCurrentIndex()].getClass().getName());
            }
        };

        MenuBillForm menu = new MenuBillForm();
        menu.initMenu(event);

        this.importGoodsForm = new BillImportGoodsForm();
        this.billSellForm = new BillSellForm();

        this.coms = new Component[]{menu, this.importGoodsForm, this.billSellForm};

        slideshow.initSlideshow(this.coms);
    }

    private void loadData(String menu) {
        if (menu.equals(this.importGoodsForm.getClass().getName())) {
            this.importGoodsForm.loadDataFromFile();
        }
        else if(menu.equals(this.billSellForm.getClass().getName())){
             this.billSellForm.loadDataFromFile();
        }
    }

    public boolean getIsLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pictureBox1 = new app.swing.PictureBox();
        slideshow = new app.slideshow.Slideshow();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pictureBox1.setImage(new javax.swing.ImageIcon(getClass().getResource("/app/Images/Background3.jpg"))); // NOI18N
        pictureBox1.setPreferredSize(new java.awt.Dimension(1800, 900));

        slideshow.setMinimumSize(new java.awt.Dimension(0, 0));
        slideshow.setPreferredSize(new java.awt.Dimension(1800, 900));
        pictureBox1.add(slideshow);
        slideshow.setBounds(0, 0, 1800, 900);

        add(pictureBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1800, 900));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.swing.PictureBox pictureBox1;
    private app.slideshow.Slideshow slideshow;
    // End of variables declaration//GEN-END:variables
}

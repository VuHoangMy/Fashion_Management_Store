package app.component;

import app.event.EventMenu;
import app.main.MainProgram;
import java.awt.Color;
import java.awt.Component;

public class TransactionForm extends javax.swing.JPanel {

    private Component[] coms;
    private SellForm sellForm;
    private ImportGoodsForm importGoodsForm;
    private ReportForm reportForm;
    
    private boolean isLoaded;
    
    public TransactionForm() {
        isLoaded = false;
    }
    
    private void initForm(){
        this.sellForm = new SellForm();
        this.importGoodsForm = new ImportGoodsForm();
        this.reportForm = new ReportForm();
    }
    
    public void loadComponentAndMenu(){
        initComponents();
        this.setBackground(new Color(236, 236, 236));
        MainProgram.getInstanceTransactionForm().setSlideshow(slideshow2);
        
        EventMenu event = new EventMenu() {
            @Override
            public void selectMenu(int index) {
                System.out.println(index);
                slideshow2.slideTo(index + 1);
                loadData(coms[slideshow2.getCurrentIndex()].getClass().getName());
            }
        };
        
        MenuTransactionForm menu = new MenuTransactionForm();
        menu.initMenu(event);
        
        initForm();

        this.coms = new Component[]{menu, this.sellForm, this.importGoodsForm, this.reportForm};
        
        slideshow2.initSlideshow(this.coms);
    }
    
    private void loadData(String menu){
        if(this.sellForm.getClass().getName().equals(menu)){
            this.sellForm.loadDataFromFile();
        }else if(this.importGoodsForm.getClass().getName().equals(menu)){
            this.importGoodsForm.loadDataFromFile();
        }else{
            this.reportForm.loadDataFromFile();
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
        slideshow2 = new app.slideshow.Slideshow();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pictureBox1.setImage(new javax.swing.ImageIcon(getClass().getResource("/app/Images/Background3.jpg"))); // NOI18N
        pictureBox1.setPreferredSize(new java.awt.Dimension(1800, 900));

        slideshow2.setMinimumSize(new java.awt.Dimension(0, 0));
        slideshow2.setPreferredSize(new java.awt.Dimension(1800, 900));
        pictureBox1.add(slideshow2);
        slideshow2.setBounds(0, 0, 1810, 900);

        add(pictureBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1800, 900));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.swing.PictureBox pictureBox1;
    private app.slideshow.Slideshow slideshow2;
    // End of variables declaration//GEN-END:variables
}

package app.component;

import app.event.EventMenu;
import app.main.MainProgram;
import java.awt.Color;
import java.awt.Component;

public class StaffManagerForm extends javax.swing.JPanel {

    private Component[] coms;
    private StaffForm staffForm;
    private TimeKeepingForm timeKeepingForm;
    private PayrollForm payrollForm;

    private boolean isLoaded;

    public StaffManagerForm() {
        isLoaded = false;
    }

    public void loadComponentAndMenu() {
        initComponents();
        this.setBackground(new Color(236, 236, 236));

        MainProgram.getInstanceStaffForm().setSlideshow(slideshow);

        EventMenu event = new EventMenu() {
            @Override
            public void selectMenu(int index) {
                System.out.println(index);
                slideshow.slideTo(index + 1);
                loadData(coms[slideshow.getCurrentIndex()].getClass().getName());
            }
        };

        MenuStaffForm menu = new MenuStaffForm();
        menu.initMenu(event);

        initForm();

        this.coms = new Component[]{menu, this.staffForm, this.timeKeepingForm, this.payrollForm};
        slideshow.initSlideshow(coms);
    }

    private void initForm() {
        this.staffForm = new StaffForm();
        this.timeKeepingForm = new TimeKeepingForm();
        this.payrollForm = new PayrollForm();
    }

    private void loadData(String menu) {
        if (this.staffForm.getClass().getName().equals(menu)) {
            this.staffForm.loadDataFromFile();
        } else if (this.timeKeepingForm.getClass().getName().equals(menu)) {
            this.timeKeepingForm.loadDataFromFile();
        }else{
            this.payrollForm.loadDataFromFile();
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

        setMinimumSize(new java.awt.Dimension(1800, 900));
        setPreferredSize(new java.awt.Dimension(1800, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pictureBox1.setImage(new javax.swing.ImageIcon(getClass().getResource("/app/Images/Background3.jpg"))); // NOI18N
        pictureBox1.add(slideshow);
        slideshow.setBounds(0, 0, 1800, 900);

        add(pictureBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1800, 900));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.swing.PictureBox pictureBox1;
    private app.slideshow.Slideshow slideshow;
    // End of variables declaration//GEN-END:variables
}

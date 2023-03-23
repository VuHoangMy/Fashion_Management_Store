package app.component;

import app.event.EventMenu;
import app.swing.Button;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

public class Menu extends javax.swing.JPanel {

    private EventMenu event;
    private String[] listMenu;

    public Menu() {
        initComponents();
        setOpaque(false);
//        setLayout(new MigLayout("wrap 2, inset 30", "[fill, width]horizontal[fill, 100]", "[]verhical[]"));
        setLayout(new MigLayout("wrap 2, inset 30", "[fill, 120]40[fill, 100]", "[]40[]"));
    }

    public void initMenu(EventMenu event) {
        this.event = event;
//        addMenu(new ImageIcon(getClass().getResource("/app/Icons/staffIcon.png")), "Staff", 0);
       
        for (String menu : this.listMenu) {
            if (menu.equals("Products")) {
                addMenu(new ImageIcon(getClass().getResource("/app/Icons/StaffIcon.png")), menu, Arrays.asList(listMenu).indexOf(menu));
                continue;
            }
            addMenu(new ImageIcon(getClass().getResource("/app/Icons/" + menu + "Icon.png")), menu, Arrays.asList(listMenu).indexOf(menu));
        }
        addMenu(new ImageIcon(getClass().getResource("/app/Icons/logoutIcon.png")), "Logout", 1000); // Logout button
    }

    public void addMenu(Icon icon, String name, int index) {
        Button menu = new Button();
        menu.setHorizontalTextPosition(SwingConstants.CENTER);
        menu.setVerticalTextPosition(SwingConstants.BOTTOM);
        menu.setText(name);
        menu.setIcon(icon);
        menu.setFont(new Font("Yu Gothic", Font.PLAIN, 16));

        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event.selectMenu(index);
            }
        });
        add(menu);
    }

    public void setListMenu(String... listMenu) {
        this.listMenu = listMenu;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

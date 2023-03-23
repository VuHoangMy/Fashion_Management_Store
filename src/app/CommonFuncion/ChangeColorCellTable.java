package app.CommonFuncion;

import app.BUS.ItemsList;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ChangeColorCellTable {

    public void changeTable(JTable table, int column_index, int row_index, ItemsList itemsList) {
        table.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                try {

                    String ID = table.getValueAt(row, 0).toString();

                    if (row == row_index) {
                        c.setBackground(itemsList.findItemsByID(ID).getColor());
                    } else {
                        c.setBackground(itemsList.findItemsByID(ID).getColor());
                    }

                } catch (Exception e) {
                }
                return c;
            }
        });
    }

}

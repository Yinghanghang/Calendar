
package newcalendar;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class CalendarRenderer extends DefaultTableCellRenderer {
    @Override public Component getTableCellRendererComponent (
            JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6){ //Weekend
                setBackground(new Color(230, 230, 255));
            }
            else{ //Weekday
                setBackground(new Color(255, 255, 255));
            }
            Date date = (Date) value; // convert value into a Date class
            if (date != null){
                if (date.isToday()){ 
                    setBackground(new Color(255, 200, 200));
                }
            }

            setForeground(Color.black);
            return this;
        }
    
    @Override 
    protected void setValue(Object value) {
        Date date = (Date) value;
        setText((date == null) ? "" : Integer.toString(date.getDay()));//a String object representing the specified integer
    }
}

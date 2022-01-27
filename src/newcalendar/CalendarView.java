
package newcalendar;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class CalendarView {
    private JFrame frameMain;
    private Container container;
    private final JPanel panelCalendar;
    private JLabel labelMonth;
    private JButton btnPrevMonth, btnNextMonth,btnPrevYear,btnNextYear,btnToday,btnNotes;    
    private JComboBox comboYear;
    private JTextField textField;
    private final JTable tableCalendar;
    private final DefaultTableModel modelTableCalendar; //Table model
    private final JScrollPane scrollTableCalendar;  
    private JFileChooser fileChooser;
    private Date currentDate;
    private final int maxYear;
    private final int minYear;    
    private Notes myNotes = null;
    
    public CalendarView() {        
        frameMain = new JFrame (); 
        frameMain.setTitle("Calendar");
        frameMain.setSize(330, 400); 
        container = frameMain.getContentPane(); //create a container
        frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMain.setResizable(false);
        frameMain.setVisible(true); 
        
        //Create components
        labelMonth = new JLabel ("January");
        comboYear = new JComboBox();
        textField = new JTextField();
        btnPrevMonth = new JButton ("<");
        btnNextMonth = new JButton (">");
        btnPrevYear = new JButton ("<<");
        btnNextYear = new JButton (">>");
        btnToday = new JButton ("Current Day");
        btnNotes = new JButton ("Import Notes");
        modelTableCalendar = new DefaultTableModel(){@Override public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};//cell edit not allowed
        tableCalendar = new JTable(modelTableCalendar);
        scrollTableCalendar = new JScrollPane(tableCalendar);// bundle table to scrollpane
        panelCalendar = new JPanel();
        panelCalendar.setLayout(null);
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));  // set current directory to user's home
        
        //Add components to container
        container.add(panelCalendar);
        panelCalendar.add(labelMonth);
        panelCalendar.add(comboYear);
        panelCalendar.add(btnPrevMonth);
        panelCalendar.add(btnPrevYear);
        panelCalendar.add(btnNextMonth);
        panelCalendar.add(btnNextYear);
        panelCalendar.add(btnToday);
        panelCalendar.add(btnNotes);
        panelCalendar.add(textField);
        panelCalendar.add(scrollTableCalendar);

        //Set bounds
        panelCalendar.setBounds(0, 0, 310, 330);
        scrollTableCalendar.setBounds(15, 50, 300, 250);
        labelMonth.setBounds(100, 25, 180, 25);
        comboYear.setBounds(164, 27, 82, 20);
        btnPrevMonth.setBounds(48, 25, 50, 25);
        btnNextMonth.setBounds(235, 25, 50, 25);
        btnPrevYear.setBounds(10, 25, 50, 25);
        btnNextYear.setBounds(272, 25, 50, 25);
        btnToday.setBounds(10, 340, 104, 25);
        btnNotes.setBounds(215, 340, 109, 25);
        textField.setBounds(13,310,304,23);
        
        //Add dayNames
        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; 
        for (int i=0; i<7; i++){
            modelTableCalendar.addColumn(dayNames[i]);
        }
        
        
        //Resize/reorder not allowed
        tableCalendar.getTableHeader().setReorderingAllowed(false);
        tableCalendar.getTableHeader().setResizingAllowed(false);
        
        
        //Set table row/column 
        tableCalendar.setRowHeight(38);        
        modelTableCalendar.setRowCount(6);
        modelTableCalendar.setColumnCount(7);
        
        int currentYear = new GregorianCalendar().get(GregorianCalendar.YEAR);
        minYear = currentYear - 100;
        maxYear = currentYear + 100;
        
        // add items to combo box
        for (int i=minYear; i<=maxYear; i++){
            comboYear.addItem(String.valueOf(i));
        }   
        
        //Apply renderer to the table by calling setDefaultRenderer method
        tableCalendar.setDefaultRenderer(tableCalendar.getColumnClass(0), 
                new CalendarRenderer()); 
       
        
        comboYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboYear.getSelectedItem() != null){
                    String b = comboYear.getSelectedItem().toString();
                    int currentYear = Integer.parseInt(b);
                    setDate(new Date(currentYear, currentDate.getMonth(), currentDate.getDay()));
                    updateButtonStatus();
                }
            }
        });
        
        btnPrevMonth.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDate.lastMonth();
                updateButtonStatus();                      
                setDate(currentDate);
            }
        });
        btnNextMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDate.nextMonth();
                updateButtonStatus();               
                setDate(currentDate);
            }
        });
        btnPrevYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDate.lastYear();
                updateButtonStatus();              
                setDate(currentDate);
            }
        });
        btnNextYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDate.nextYear();
                updateButtonStatus();
                setDate(currentDate);
            }
        });
        
        btnToday.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {        
            setDate(Date.today());
            }
        });
        
        btnNotes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {        
                int result = fileChooser.showOpenDialog(frameMain);
                if (result == JFileChooser.APPROVE_OPTION)//open the file
                {
                    File selectedFile = fileChooser.getSelectedFile();
                    myNotes = new Notes(selectedFile.getAbsolutePath());
                    setDate(currentDate);
                }
            }
        });
    }
    
    private void updateButtonStatus() {
        btnPrevYear.setEnabled(!(currentDate.getYear() == minYear));                  
        btnNextYear.setEnabled(!(currentDate.getYear() == maxYear));  
        btnNextMonth.setEnabled(!(
                        currentDate.getYear() == maxYear &&
                        currentDate.getMonth() == 11));
        btnPrevMonth.setEnabled(!(
                        currentDate.getYear() == minYear &&
                        currentDate.getMonth() == 0));                
    }
    

    public void setDate(Date date) {
        currentDate = date;

        String[] monthNames =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int numberOfDays, startOfMonth;        
        int month = date.getMonth();
        int year = date.getYear();
        
        if (date.isTooEarly()){
            btnPrevMonth.setEnabled(false);
            btnPrevYear.setEnabled(false);            
        } 
        
        if (date.isTooLate()){
            btnNextMonth.setEnabled(false);
            btnNextYear.setEnabled(false);
        } 
        
        labelMonth.setText(monthNames[month]); //update the month label 
        labelMonth.setBounds(100, 25, 180, 25); 
        comboYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
            
        
        //Get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        startOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);
        numberOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        
        //Clear calendar
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
            modelTableCalendar.setValueAt(null, i, j);
        }
    }

        //Draw calendar
        for (int i= 1; i<= numberOfDays; i++){
            int row = (i + startOfMonth - 2) / 7;
            int column  =  (i + startOfMonth - 2) % 7;
            Object theValue = new Date(date.getYear(), date.getMonth(), i);
            modelTableCalendar.setValueAt(theValue, 
                    row, column);
        }     
        
        if (myNotes != null) {
            textField.setText(myNotes.getNotes(date));
        }
    }
}

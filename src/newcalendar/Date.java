
package newcalendar;

import java.util.GregorianCalendar;


public class Date {
    private int year;
    private int month;// month from 0 to 11 
    private int day;
    
    public Date(int year, int month, int day) { // constructor one
        this.year = year;
        this.month = month;
        this.day = day;
    }
    
    public Date(String dateString) { // constructor two
        String[] parts = dateString.split("/"); // split string to an array
        this.month = Integer.parseInt(parts[0]) - 1;
        this.day = Integer.parseInt(parts[1]);
    }
    
    public int getYear() {
        return year;
    }
    
    public int getMonth() {
        return month;
    }
    
    public int getDay() {
        return day;
    }
    
    public void lastYear() {
        year = year - 1;
    }
    
    public void lastMonth() {
        month = month - 1;
        if (month < 0) {
            month = 11;
            year = year - 1;
        }
    }
    
    public void nextMonth() {
        month = month + 1;
        if (month > 11) {
            month = 0;
            year = year + 1;
        }
    }
    
    public void nextYear() {
        year = year + 1;
    }
    
    public boolean isTooEarly() {
        return month == 0 && year <= today().getYear() - 100;
    }
    
    public boolean isTooLate() {
        return month == 11 && year >= today().getYear() + 100;
    }
    
    public static Date today() { // static method today
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        int year = cal.get(GregorianCalendar.YEAR); //Get year
        int month = cal.get(GregorianCalendar.MONTH); //Get month
        int day = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day        
        return new Date(year, month, day);        
    }
    
    public boolean isToday() {
        Date today = today();
        return year == today.getYear() && month == today.getMonth()
                && day == today.getDay();
    }
}

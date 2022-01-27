/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newcalendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class Notes {
    private final Map<Integer, String> notes = new HashMap<>();
    
    // read the text file and store all |notes|
    public Notes(String filename) {
        try {
            Scanner scan = new Scanner(new File(filename));
            
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                String[] parts = line.split(" ");
                Date date = new Date(parts[0]); // call constructor two
                notes.put(date.getMonth(), line);  // return value associated with key
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Notes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Find if there's any notes for a given date. Return the note if found, 
    // otherwise return null
    public String getNotes(Date date) {
        return notes.get(date.getMonth());//return the value to which the specified key is mapped
    }
}

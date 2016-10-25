import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BufferedReader and Scanner can be used to read line by line from any File or
 * console in Java.
 * This Java program demonstrate line by line reading using BufferedReader in Java
 *
 */

public class BufferedReaderClass {   

    public static void main(String args[]) {
      
        //reading file line by line in Java using BufferedReader       
        FileInputStream fis = null;
        BufferedReader reader = null;
      
        try {
         //home
            //fis = new FileInputStream("C:\\Users\\Ryan\\OneDrive\\Workspace\\TimesheetDayCalculator\\Sampleday.txt");
         //work
         //fis = new FileInputStream("C:\\Users\\rcerankowski\\OneDrive\\Workspace\\TimesheetDayCalculator\\Sampleday.txt");
         //fis = new FileInputStream("C:\\Users\\rcerankowski\\Documents\\GitHub\\TimesheetDayCalculator\\Sampleday.txt");
         fis = new FileInputStream("Sampleday.txt");
            reader = new BufferedReader(new InputStreamReader(fis));
          
            System.out.println("Reading File line by line using BufferedReader");
          
            DayCalculator dc = new DayCalculator();
            
            //String line = reader.readLine();
            String timeLine = reader.readLine();
            String taskLine = reader.readLine();
            while(timeLine != null){             
             dc.parseText(timeLine, taskLine, dc);
          dc.root.calculateTime();
          
          timeLine = reader.readLine();
             taskLine = reader.readLine();
             
                //System.out.println(line);
                //line = reader.readLine();
            }
            
            dc.calculateTotalHours();
            //dc.root.calculateTime();
            dc.printResults();
            //DayCalculator.calculateTotalHours(dc);
            
      //DayCalculator.printResults(dc);
          
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BufferedReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BufferedReader.class.getName()).log(Level.SEVERE, null, ex);
          
        } finally {
            try {
                reader.close();
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(BufferedReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
  }    
}
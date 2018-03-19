package marginafterdecomplie;

//import java.net.URL;
import com.ruiz.utils.DateUtils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import static javax.swing.JOptionPane.showMessageDialog;
import static marginafterdecomplie.MarginTest.rSetting;
public class MainRun
{
     public static String filePath; 
  public static MarginTest marginW = new MarginTest();  
  //public static HashSet hashdata = new HashSet(); 
  
   public static BufferedReader br1;
   public static  ArrayList chipHead= new ArrayList();
   public static  ArrayList ChipType= new ArrayList();
   public static  Logger logger;
   
   // private static String filePath;
  public static void main(String[] args) throws FileNotFoundException {      
            br1=cin_txt.getBr();   
             filePath = MyPath.getProjectPath();
             //showMessageDialog(null,filePath);
         try {
            cin_txt.ChipTypeToMap(br1,chipHead,  ChipType);
         } catch (IOException ex) {
             Logger.getLogger(MainRun.class.getName()).log(Level.SEVERE, null, ex);
         }
        logger = Logger.getLogger("marginafterdecomplie");//鏃ュ織澶勭悊鍣�
        logger.setLevel(Level.ALL);
        
        FileHandler fileHandler = null;					
      try {
          fileHandler = new FileHandler("test.txt");
      } catch (IOException | SecurityException ex) {
          Logger.getLogger(MainRun.class.getName()).log(Level.INFO, null, ex);
      }
LogRecord lr = new LogRecord(Level.INFO, "This is a text log.");
//SimpleFormatter sf = new SimpleFormatter();
     fileHandler.setFormatter(new SimpleFormatter() {//定义一个匿名类
                @Override
                public String format(LogRecord record) {
                    String nowtime = DateUtils.getCurrentTimeStr();                   
                    return "@"+nowtime+":  "+record.getLevel() + ":" + record.getMessage() +"\n";
                }
            });

logger.addHandler(fileHandler);
logger.log(lr);
logger.info("info");
marginW.setTitle("RFID Toolkit");
marginW.setVisible(true); }  

}
  
  


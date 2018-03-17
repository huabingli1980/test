package marginafterdecomplie;

//import java.net.URL;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
public class MainRun
{
     public static String filePath; 
  public static MarginTest marginW = new MarginTest();  
  //public static HashSet hashdata = new HashSet(); 
  
   public static BufferedReader br1;
   public static  ArrayList chipHead= new ArrayList();
   public static  ArrayList ChipType= new ArrayList();
   
   // private static String filePath;
  public static void main(String[] args) throws FileNotFoundException {      
            br1=cin_txt.getBr();       
         try {
             //鏃ュ織璁板綍鍣�
             //ToarrayList(BufferedReader br1, ArrayList Chiphead, ArrayList Chiptype) throws IOException
             cin_txt.ToarrayList(br1,chipHead,  ChipType);
         } catch (IOException ex) {
             Logger.getLogger(MainRun.class.getName()).log(Level.SEVERE, null, ex);
         }
Logger logger = Logger.getLogger("marginafterdecomplie");//鏃ュ織澶勭悊鍣�
FileHandler fileHandler = null;
 filePath = MyPath.getProjectPath();
      try {
          fileHandler = new FileHandler("test.txt");
      } catch (IOException | SecurityException ex) {
          Logger.getLogger(MainRun.class.getName()).log(Level.INFO, null, ex);
      }

//闇�瑕佽褰曠殑鏃ュ織娑堟伅

LogRecord lr = new LogRecord(Level.INFO, "This is a text log.");
//涓哄鐞嗗櫒璁剧疆鏃ュ織鏍煎紡锛欶ormatter
SimpleFormatter sf = new SimpleFormatter();
fileHandler.setFormatter(sf);
//娉ㄥ唽澶勭悊鍣�
logger.addHandler(fileHandler);
//璁板綍鏃ュ織娑堟伅
logger.log(lr);
      marginW.setVisible(true); }
  
  public static void testRenameFile(String fileN,String Nfile) {  
    //String filePath = "D:/test/鎴戞槸.conf";  
    
    try {  
        File src = new File(fileN);  
       //String Nfile =fileN.replace(" ", "");  
        File des = new File(Nfile);  
        if (des.exists()) {  
            boolean res = des.delete();  
            if (!res) {  
                System.out.println("Failed to delete file");  
            }  
        }  
        if (!src.renameTo(des)) {  
            System.out.println("Failed to renameTo file");  
        }  
    } catch (Exception e) {  
        System.out.println(e.getMessage());  
    }  
            }
}
  
  


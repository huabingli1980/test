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
             //日志记录器
             //ToarrayList(BufferedReader br1, ArrayList Chiphead, ArrayList Chiptype) throws IOException
             cin_txt.ToarrayList(br1,chipHead,  ChipType);
         } catch (IOException ex) {
             Logger.getLogger(MainRun.class.getName()).log(Level.SEVERE, null, ex);
         }
Logger logger = Logger.getLogger("marginafterdecomplie");//日志处理器
FileHandler fileHandler = null;
 filePath = MyPath.getProjectPath();
      try {
          fileHandler = new FileHandler("C:\\Development\\java impinj\\MarginAfterDecomplie\\test.txt");
      } catch (IOException | SecurityException ex) {
          Logger.getLogger(MainRun.class.getName()).log(Level.INFO, null, ex);
      }

//需要记录的日志消息

LogRecord lr = new LogRecord(Level.INFO, "This is a text log.");
//为处理器设置日志格式：Formatter
SimpleFormatter sf = new SimpleFormatter();
fileHandler.setFormatter(sf);
//注册处理器
logger.addHandler(fileHandler);
//记录日志消息
logger.log(lr);
      marginW.setVisible(true); }
  
  public static void testRenameFile(String fileN,String Nfile) {  
    //String filePath = "D:/test/我是.conf";  
    
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
  
  


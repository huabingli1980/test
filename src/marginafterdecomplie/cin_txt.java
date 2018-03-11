/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marginafterdecomplie;

/**
 *
 * @author huabing li
 */
    import java.io.File;  
    import java.io.InputStreamReader;  
    import java.io.BufferedReader;  
    import java.io.FileInputStream;  
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

    public  class cin_txt {  
       
    
                
        
         String filePath = MyPath.getProjectPath();
       static   String typeTidHead;
        // System.out.println(filePath);
        // javax.swing.JOptionPane.showMessageDialog(null, filePath);
        public static String getChipType(String TID,BufferedReader br1) {  
            try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  
                /* 读入TXT文件 */                 
               // String line = "";  
              br1.mark(0);
              String  line = br1.readLine();  
              typeTidHead="";
                while (line != null) {  
                    line = br1.readLine(); // 一次读入一行数据  
                    String chiptype= line.split(" ")[0];
                    String head1= line.split(" ")[1];
                    String TIDhead=TID.substring(0,8);
                   if (head1.equals(TIDhead))
                           {
                               typeTidHead=chiptype;                               
                              br1.reset();
                               break;
                           }
                  
                    
                }  

                /* 写入Txt文件  
                File writename = new File(".\\result\\en\\output.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
                writename.createNewFile(); // 创建新文件  
                BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
                out.write("我会写入文件啦\r\n"); // \r\n即为换行  
                out.flush(); // 把缓存区内容压入文件  
                out.close(); // 最后记得关闭文件  
               */ 
            } catch (IOException e) {  
                
            }  
             return typeTidHead;
        }  
        
        static BufferedReader getBr() throws FileNotFoundException
        {
              String filePath = MyPath.getProjectPath();      
                String pathname = filePath+"\\ChipType.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径  
                File filename = new File(pathname); // 要读取以上路径的input。txt文件  
                InputStreamReader reader = new InputStreamReader(  
                 new FileInputStream(filename)); // 建立一个输入流对象reader  
                BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言 
                return br;
        }
        
        static void ToarrayList(BufferedReader br1, ArrayList Chiphead, ArrayList Chiptype) throws IOException
        {
            // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
            /* 读入TXT文件 */
            // String line = "";          
            String  line = br1.readLine();
            typeTidHead="";
            while (!"".equals(line) && null != line) {
                // 一次读入一行数据
                System.out.println("---"+line+"---");
                String chiptype= line.split(",")[0];
                String head1= line.split(",")[1];
               // String TIDhead=TID.substring(0,8);
               Chiphead.add(head1);
               Chiptype.add(chiptype);
              line = br1.readLine();
  
            }
            
            br1.close();
          
            
        }
        
     
    }  

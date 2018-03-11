/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marginafterdecomplie;
import java.io.File;  
import java.io.FileReader;  
import java.util.List;   
import com.opencsv.CSVReader;  
import java.util.Arrays;
import java.util.Optional;

public class  ReadCSV {  
    @SuppressWarnings("ConvertToTryWithResources")
    public static List getlistbyCSV () throws Exception {      
        
       // IntStream.range(0, 10).forEach(value -> System.out.println(value));
        File file = new File("C:\\Development\\java impinj\\abc.csv");          
        // InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");  
       FileReader fReader = new FileReader(file);         
       // CSVReader csvReader = new CSVReader(fReader);         
      CSVReader csvReader = new CSVReader(fReader);          
        String[] strs = csvReader.readNext();  
        if(strs != null && strs.length > 0){  
            for(String str : strs)  
                if(null != str && !str.equals(""))  
                    System.out.print(str + " , ");  
            System.out.println("\n---------------");  
        }  
        List<String[]> list = csvReader.readAll();
      
        for(String[] ss : list){  
            for(String s : ss)  
                if(null != s && !s.equals(""))  
                    System.out.print(s + " , ");  
            System.out.println();  
        }  
        csvReader.close();  
        return list;
   
     }  
     public void findAny() {
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree =
                someNumbers.stream()
                        .map(x -> x * x)
                        .filter(x -> x % 3 == 0)
                        .findAny(); // 9
        System.out.println(firstSquareDivisibleByThree.get());
    }
    
     
    
}  
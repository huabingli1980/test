/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marginafterdecomplie;

import com.impinj.octane.OctaneSdkException;
import java.util.ArrayList;

/**
 *
 * @author Huabing Li
 */
public class EPC2Barcode {
    private String epc;
    private String barcode;
    private String prefix;
    String[] To24bin;
            
   public void EPC2Barcode(String EPC)
    {
    
        epc=EPC;
    }
    
    String getBarcode(String EPC)
    {
           To24bin= new String[32];// if 24 epc length then 24
         // String EPC= this.epc;
           for (int i=0;i<=EPC.length()-1;i++)
           {
              if (i< EPC.length()-1)
                      {
                          
                          if (i==23){
                             int tt=i; 
                          }
               String s=EPC.substring(i, i+2);
               To24bin[i]=hex2bin(s);
                      }
              else
              {
             String s=EPC.substring(i, i+1);
             To24bin[i]=hex2bin(EPC.substring(i, i+1));
              
              }
           }
           
           StringBuilder ss;
           ss=new StringBuilder("");
           ss.append(To24bin[2].substring(6,8));
           ss.append(To24bin[4]);
            ss.append(To24bin[6]);
             ss.append(To24bin[8].substring(0, 6));
             
           
           prefix=ss.toString();
           ss=null;
            ss=new StringBuilder("");
            
             ss.append(To24bin[8].substring(6,8));
           ss.append(To24bin[10]);
            ss.append(To24bin[12]);
             ss.append(To24bin[14].substring(0, 2));
            String itemRef=ss.toString();
           // String yy= "12908978".substring(7, 8);
           int DecPre=Integer.valueOf(prefix,2);
           int DecItemR=Integer.valueOf(itemRef,2);
           String digit=getCheckDigit(String.format("%07d",DecPre)+String.format("%05d",DecItemR));
           barcode=String.format("%07d",DecPre)+String.format("%05d",DecItemR)+digit;
           
        return barcode;
    }
    
    String hex2bin(String hexString)
    {
         String bString = "", tmp;     
        try
        {
          if (hexString == null || hexString.length()== 0)
          { return null;}
      
        for (int i = 0; i < hexString.length(); i++)
        {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(hexString
                            .substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
            
        }
        }catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }
        
        return bString;
    }
    
    String Bin2Dec(String BinText)
    {
        String Dec = null;
        return Dec;
        
    }
    
    String getCheckDigit(String b)
    {
     /*
      int st  = MOD(10-MOD((MID(b,12,1)+MID(b,10,1)+
                MID(b,8,1)+MID(b,6,1)+MID(b,4,1)+
                MID(b,2,1))*3+MID(b,11,1)+MID(b,9,1)+
                MID(b,7,1)+MID(b,5,1)+MID(b,3,1)+MID(b,1,1),10),10)
        */
        
         int st1  = (Integer.parseInt(b.substring(11, 12))+Integer.parseInt(b.substring(9, 10))+Integer.parseInt(b.substring(7, 8))+Integer.parseInt(b.substring(5, 6))+Integer.parseInt(b.substring(3, 4))+Integer.parseInt(b.substring(1, 2)))*3;
         int st0=Integer.parseInt(b.substring(10, 11))+Integer.parseInt(b.substring(8, 9))+Integer.parseInt(b.substring(6, 7))+Integer.parseInt(b.substring(4, 5))+Integer.parseInt(b.substring(2, 3))+Integer.parseInt(b.substring(0, 1));
           int st3  = st1+st0;
          // int u=st3%10;
           int st4=10- st3%10;
           int st5= st4 %10;
           


        return st5+"";
    }
    
  public static void main(String[] args){
        EPC2Barcode e2b= new EPC2Barcode();
       // e2b.EPC2Barcode("AB1");
      System.out.println(e2b.getBarcode("00B07A12D40489C800000EF0"));
      // System.out.println(String.format("%07d","93+0));
         System.out.println("checkDigit is:" + e2b.getCheckDigit("2000053046473"));
        String uuu;
        uuu=Integer.valueOf("000111101000010010110101",2).toString();
        
      
        
        
    }
  
  



}

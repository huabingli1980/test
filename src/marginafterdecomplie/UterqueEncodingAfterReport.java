package marginafterdecomplie;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.MemoryBank;
import com.impinj.octane.PcBits;
import com.impinj.octane.QtGetConfigResultStatus;
import com.impinj.octane.ReadResultStatus;
import com.impinj.octane.SequenceState;
import com.impinj.octane.Tag;
import com.impinj.octane.TagBlockPermalockOpResult;
import com.impinj.octane.TagData;
import com.impinj.octane.TagKillOpResult;
import com.impinj.octane.TagLockOp;
import com.impinj.octane.TagLockOpResult;
import com.impinj.octane.TagLockState;
import com.impinj.octane.TagOp;
import com.impinj.octane.TagOpCompleteListener;
import com.impinj.octane.TagOpReport;
import com.impinj.octane.TagOpResult;
import com.impinj.octane.TagOpSequence;
import com.impinj.octane.TagQtGetOpResult;
import com.impinj.octane.TagQtSetOpResult;
import com.impinj.octane.TagReadOp;
import com.impinj.octane.TagReadOpResult;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;
import com.impinj.octane.TagWriteOp;
import com.impinj.octane.TagWriteOpResult;
import com.impinj.octane.TargetTag;
import com.impinj.octane.WordPointers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import static marginafterdecomplie.MainRun.filePath;
import static marginafterdecomplie.MarginTest.codingForm;
import static marginafterdecomplie.Sqlite.conn;
import static marginafterdecomplie.Sqlite.stmt;


public class UterqueEncodingAfterReport
        implements TagReportListener,TagOpCompleteListener {
    public Sqlite sqlite= new Sqlite();
 final DefaultTableModel tableModel = (DefaultTableModel) codingForm.ListT.getModel();
    MarginTest marginW = MainRun.marginW;
     //Calendar cal = Calendar.getInstance();
    public static Set<MyTag1> Myhashdata = new HashSet();
    public String MyMarginResult;
    public String c;
    public MyTag1 pt;
    public String HexEpc;
    public  ArrayList Mepclist = new ArrayList();
    public  ArrayList Mtidlist = new ArrayList();
    public ArrayList epclist = new ArrayList();
    public static ArrayList tidlist = new ArrayList();
    public ArrayList tidepc = new ArrayList();
    public String tidepcT;
    public static int idCounter = 1;
    public int times = 0;
 public ResultSet rscsv=null;
     private ResultSet rs = null;
     public static short PC_OP_ID = 122;
  public static short EPC_OP_ID = 123;
        public static short TID_OP_ID = 124;
        public static short AccessPw_OP_ID=125;
        public static short UserM_OP_ID=126;
        public static short Lock_OP_ID=127;
        
        public static String PC_OP_rst = "";
          public static String EPC_OP_rst = "";
        public static String TID_OP_rst = "";
        public static String AccessPw_OP_rst="";
        public static String UserM_OP_rst="";
        public static String Lock_OP_rst="";
    private Date date;
 public static String  newEpc;

    @Override
      public void onTagOpComplete(ImpinjReader reader, TagOpReport results) {
        System.out.println("TagOpComplete: ");
        for (TagOpResult t : results.getResults()) {
            System.out.print("  EPC: " + t.getTag().getEpc().toHexString());
            if (t instanceof TagReadOpResult) {
                TagReadOpResult tr = (TagReadOpResult) t;
                System.out.print(" READ: id: " + tr.getOpId());
                System.out.print(" sequence: " + tr.getSequenceId());
                System.out.print(" result: " + tr.getResult().toString());
                if (tr.getResult() == ReadResultStatus.Success) {
                    System.out.print(" data: " + tr.getData().toHexWordString());
                }
            }

            if (t instanceof TagWriteOpResult) {
                TagWriteOpResult tw = (TagWriteOpResult) t;
                
                //result---- Success, TagMemoryOverrunError, TagMemoryLockedError, InsufficientPower,
                //NonspecificTagError, NoResponseFromTag, NonspecificReaderError
                 System.out.println(" Tag Tid: " + tw.getTag().getTid());
                if (tw.getOpId()==EPC_OP_ID )
                {
                    System.out.print(" WRITE EPC: id: " + tw.getOpId()); 
                    EPC_OP_rst = tw.getResult().toString();
                }else if (tw.getOpId()==AccessPw_OP_ID){
                    System.out.print(" WRITE AccessPw: id: " + tw.getOpId());
                    AccessPw_OP_rst=tw.getResult().toString();
                }else if (tw.getOpId()==UserM_OP_ID){
                    System.out.print(" WRITE user: id: " + tw.getOpId());
                   UserM_OP_rst=tw.getResult().toString();
                }else if (tw.getOpId()==PC_OP_ID) {
                   System.out.print(" WRITE PC: id: " + tw.getOpId());  
                    PC_OP_rst=tw.getResult().toString();
                }else 
                   System.out.print(" WRITE nameless: id: " + tw.getOpId());                 
                 System.out.print(" WRITE: id: " + tw.getClass().getName());
                System.out.print(" sequence: " + tw.getSequenceId());
                System.out.print(" result: " + tw.getResult().toString());
                System.out.print(" words_written: " + tw.getNumWordsWritten());
            }

            if (t instanceof TagKillOpResult) {
                TagKillOpResult tk = (TagKillOpResult) t;
                System.out.print(" KILL: id: " + tk.getOpId());
                System.out.print(" sequence: " + tk.getSequenceId());
                System.out.print(" result: " + tk.getResult().toString());
            }

            if (t instanceof TagLockOpResult) {
                
                /*
                 public static String PC_OP_rst = "";
          public static String EPC_OP_rst = "";
        public static String TID_OP_rst = "";
        public static String AccessPw_OP_rst="";
        public static String UserM_OP_rst="";
        public static String Lock_OP_rst="";
                */
                TagLockOpResult tl = (TagLockOpResult) t;                
                 if (tl.getOpId()==Lock_OP_ID )System.out.print(" LockOP: id: " + tl.getOpId());               
                else System.out.print(" Lock nameless: id: " + tl.getOpId());               
               Lock_OP_rst=tl.getResult().toString();
                System.out.print(" sequence: " + tl.getSequenceId());
                System.out.print(" Tag Tid: " + tl.getTag().getTid());
                //System.out.print(" TargetTag: " +tl. );
                System.out.print(" result: " + tl.getResult().toString());
                
                if (EPC_OP_rst.equals("Success")&AccessPw_OP_rst.equals("Success")&UserM_OP_rst.equals("Success")
                        &Lock_OP_rst.equals("Success"))
                {
                     Calendar cal1 = Calendar.getInstance();
                     date = cal1.getTime();
                     System.out.println(tl.getTag().getTid().toHexString());
                     //String newEpc=tl.getTag().getEpc().toHexString();
                     int rowN1=tidlist.indexOf(tl.getTag().getTid().toHexString());
                       
                    try {
                        SimpleDateFormat pretime = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
                        Date  date1 =pretime.parse(tableModel.getValueAt(rowN1, 6).toString());
                         tableModel.setValueAt((date.getTime()-date1.getTime()), rowN1, 7);   
                    } catch (ParseException ex) {
                        Logger.getLogger(UterqueEncodingAfterReport.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String sql="UPDATE warehouses SET EncodeStatus = 'Success' WHERE EPC ='"+ newEpc+"'";
                    sqlite.RunSql(sql);    
                   tableModel.setValueAt(newEpc, rowN1, 3);  
                 tableModel.setValueAt("Success", rowN1, 5);  
                
                }
                
            }

            if (t instanceof TagBlockPermalockOpResult) {
                TagBlockPermalockOpResult tbp = (TagBlockPermalockOpResult) t;
                System.out.print(" BLOCK_PERMALOCK id: " + tbp.getOpId());
                System.out.print(" sequence: " + tbp.getSequenceId());
                System.out.print(" result: " + tbp.getResult().toString());
            }

            if (t instanceof TagQtGetOpResult) {
                TagQtGetOpResult tqt = (TagQtGetOpResult) t;
                System.out.print(" QT_GET id: " + tqt.getOpId());
                System.out.print(" sequence: " + tqt.getSequenceId());
                System.out.print(" result: " + tqt.getResult().toString());
                if (tqt.getResult() == QtGetConfigResultStatus.Success) {
                    System.out.print(" mode: "
                            + tqt.getDataProfile().toString());
                    System.out.print(" range: "
                            + tqt.getAccessRange().toString());
                }
            }

            if (t instanceof TagQtSetOpResult) {
                TagQtSetOpResult tqt = (TagQtSetOpResult) t;
                System.out.print(" QT_SET id: " + tqt.getOpId());
                System.out.print(" sequence: " + tqt.getSequenceId());
                System.out.print(" result: " + tqt.getResult().toString());
            }

            System.out.println("");
        }
    }


 

    public class MyTag1 {
        private String epc;
      

        public String getEpc() {
            return epc;
        }

        @Override
        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + (tid == null ? 0 : tid.hashCode());
            return result;

        }

        //@Override
        @Override
        public boolean equals(Object obj) {
            MyTag1 other = (MyTag1) obj;
            return  tid.equals(other.tid);
            // return other.equals(this);
        }

        public void setEpc(String epc) {
            this.epc = epc;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        private UterqueEncodingAfterReport getOuterType() {
            return UterqueEncodingAfterReport.this;
        }

        private String tid;
    }

    @Override
    public synchronized void onTagReported(ImpinjReader reader, TagReport report) {
        //reader.setTagOpCompleteListener(new MarginEpc());
         reader.setTagOpCompleteListener(new UterqueEncodingAfterReport());  
        List<Tag> tags = report.getTags();
        if (tags.size() > 1) {
            javax.swing.JOptionPane.showMessageDialog(null, "2 tags or above");
        }
        for (Tag tag : tags) {
            MyTag1 mytag = new MyTag1();
            mytag.setEpc(tag.getEpc().toHexString());
            mytag.setTid(tag.getTid().toHexString());
            tidepcT = mytag.tid +"---"+ mytag.epc;
            if (!Myhashdata.contains(mytag)) // int te= tidepc.indexOf(tidepcT);
            // if (te==-1)
            {
                System.out.println(tidepcT);
                Myhashdata.add(mytag);
                tidepc.add(tidepcT);
                tidlist.add(mytag.tid);
                EncodeTag  encodeTag = new EncodeTag ();
                String currentEpc = mytag.getEpc();                
                 String currentTid = mytag.getTid();
                String newTid = marginW.Codehex.getText();
                short pc = tag.getPcBits();
                  Calendar cal = Calendar.getInstance();
                                 date = cal.getTime();
                                String nowtime = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(date);
                
          tableModel.addRow(new String[]{codingForm.ListT.getRowCount() + 1 + "", currentTid, currentEpc,"", "", "", nowtime,""});
                              //  System.out.println(times + "--" + epc + "--" + result + "---" + times + "times");
                try {
                     rscsv=getEpcListFromCSVdb(codingForm.SKU.getSelectedItem().toString());
                     String accessPW=rscsv.getString("ACCESSPASSWORD");
                     String userM=rscsv.getString("USERMEMORY");
                     newEpc = rscsv.getString("EPC");  
                     String accessPWLock=rscsv.getString("ACCESSPASSWORDLOCKCODE"); 
                     String epcLock=rscsv.getString("EPCLOCKCODE"); 
                      String userLock=rscsv.getString("USERMEMORYLOCKCODE"); 
                      String killPWlock=rscsv.getString("KILLPASSWORDLOCKCODE"); 
               // programTag(String currentEpc,
               // short currentPC, String newEpc,String TID,
              //  String UserM,String AccessPW,String accessPWLock,
               // String epcLock,String KillLock,String userLock, ImpinjReader reader)
              //  throws Exception
              rscsv.close();
              String sql="UPDATE warehouses SET EncodeStatus = 'used' WHERE EPC ='"+ newEpc+"'";
                    sqlite.RunSql(sql);  
              
                    encodeTag.programTag(currentEpc, pc, newEpc,currentTid,userM,accessPW,accessPWLock,epcLock,killPWlock,userLock, reader);
                             } catch (Exception ex) {
                    Logger.getLogger(UterqueEncodingAfterReport.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {          
                String cepc = tag.getEpc().toHexString();
                int Tablerow = epclist.indexOf(cepc);   
                break;
            }
        }
    }
                      public ResultSet getEpcListFromCSVdb(String lookupkey) {
                         
                String url = "jdbc:sqlite:" + filePath + "/fromCsv.db";
                String sql1 = "select *  from warehouses where LOOKUPKEY='"+lookupkey +"' and EncodeStatus<>'Success'and EncodeStatus<>'used'";
                
                try 
                {
                  conn = DriverManager.getConnection(url);
                      stmt = conn.createStatement();                       
                      rs = stmt.executeQuery(sql1);                    

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }finally{
         
            
        }
                System.out.println("Got data from Fromcsv");
                return  rs;
            } 
    private class EncodeTag  {
        int outstanding = 0;  

        void programTag(String currentEpc,
                short currentPC, String newEpc,String TID,
                String UserM,String AccessPW,String accessPWLock,
                String epcLock,String KillLock,String userLock, ImpinjReader reader)
                throws Exception {

            if ((currentEpc.length() % 4 != 0) || (newEpc.length() % 4 != 0)) {
                throw new Exception("EPCs must be a multiple of 16- bits: "
                        + currentEpc + "  " + newEpc);
            }

            if (outstanding > 0) {
                return;
            }
            
                PC_OP_rst = "";
       EPC_OP_rst = "";
        TID_OP_rst = "";
         AccessPw_OP_rst="";
        UserM_OP_rst="";
       Lock_OP_rst="";
            
            
            

            System.out.println("Programming Tag Tag");
            System.out.println("   EPC " + currentEpc + " to " + newEpc);
            TagOpSequence seq = new TagOpSequence();
            seq.setOps(new ArrayList<>());
            seq.setExecutionCount((short) 1); // delete after one time
            seq.setState(SequenceState.Active);
            seq.setId(idCounter++);
            seq.setTargetTag(new TargetTag());
            seq.getTargetTag().setBitPointer((short) 0);
            seq.getTargetTag().setMemoryBank(MemoryBank.Tid);
            seq.getTargetTag().setData(TID);
           
                     if (currentEpc.length() != newEpc.length()) {
                // keep other PC bits the same.
                String currentPCString = PcBits.toHexString(currentPC);

                short newPC = PcBits.AdjustPcBits(currentPC,
                        (short) (newEpc.length() / 4));
                String newPCString = PcBits.toHexString(newPC);

                System.out.println("   PC bits to establish new length: "
                        + newPCString + " " + currentPCString);

                TagWriteOp pcWrite = new TagWriteOp();
                // pcWrite.Id = PC_BITS_OP_ID;
                pcWrite.setMemoryBank(MemoryBank.Epc);
                pcWrite.setWordPointer(WordPointers.PcBits);
                pcWrite.setData(TagData.fromHexString(newPCString));
                pcWrite.Id=PC_OP_ID;
                seq.getOps().add(pcWrite);
            } 
            
            
            
            
            TagWriteOp epcWrite = new TagWriteOp();
            epcWrite.Id = EPC_OP_ID;
            epcWrite.setMemoryBank(MemoryBank.Epc);
            epcWrite.setWordPointer(WordPointers.Epc);
            epcWrite.setData(TagData.fromHexString(newEpc));
          
            
             TagWriteOp AccesPWWrite = new TagWriteOp();
            AccesPWWrite.Id = AccessPw_OP_ID;
            AccesPWWrite.setMemoryBank(MemoryBank.Reserved);
            AccesPWWrite.setWordPointer(WordPointers.AccessPassword);
            AccesPWWrite.setData(TagData.fromHexString(AccessPW));
           //AccesPWWrite.setData(TagData.fromHexString("00000001"));
            
            TagWriteOp UserMWrite = new TagWriteOp();
            UserMWrite.Id = UserM_OP_ID;
            UserMWrite.setMemoryBank(MemoryBank.User);
            UserMWrite.setWordPointer((short)0);
           UserMWrite.setData(TagData.fromHexString(UserM));
           
          
             
            TagLockOp lockOp = new TagLockOp();          
           // lockOp.setAccessPassword(TagData.fromHexString(AccessPW));
            lockOp.Id = Lock_OP_ID;               
               lockOp.setEpcLockType(getLockType(epcLock));
               // lockOp.setTidLockType(TagLockState.Permalock);
               lockOp.setUserLockType(getLockType(userLock));
                 lockOp.setAccessPasswordLockType(getLockType(accessPWLock));
                lockOp.setKillPasswordLockType(getLockType(KillLock));
                
               // TagReadOp readReservedMem = new TagReadOp();
                //readReservedMem.setMemoryBank(MemoryBank.Reserved);
               // readReservedMem.setWordPointer(WordPointers.AccessPassword);
                
                 seq.getOps().add(epcWrite);
                 seq.getOps().add(AccesPWWrite);             
                 seq.getOps().add(UserMWrite); 
                 seq.getOps().add(lockOp);
                
            // add to the list
           
            // have to program the PC bits if these are not the same
  

            outstanding++;
            reader.removeTagOpCompleteListener();
            reader.addOpSequence(seq);

        }
   
       TagLockState getLockType(String LockType){
           
              switch (LockType.toUpperCase()){
                case "LOCK":  return TagLockState.Lock;                                              
                case "PERMALOCK" :return TagLockState.Permalock;               
                case "":return TagLockState.None;                 
                 case "PERMAUNLOCK": return TagLockState.Permaunlock;                
                  case "UNLOCK":return TagLockState.Unlock;
                               
                }
           return null;
           
           /*
             switch (KillLock.toUpperCase()){
                case "LOCK":
                        lockOp.setKillPasswordLockType(TagLockState.Lock);  
                case "PERMALOCK" :
              lockOp.setKillPasswordLockType(TagLockState.Permalock);  
                case "":
                 lockOp.setKillPasswordLockType(TagLockState.None); 
                 case "PERMAUNLOCK":
                 lockOp.setKillPasswordLockType(TagLockState.Permaunlock); 
                  case "UNLOCK":
                 lockOp.setKillPasswordLockType(TagLockState.Unlock);                  
                }*/
           
       }


    }
}

package marginafterdecomplie;

import com.impinj.octane.BitPointers;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.MarginReadMask;
import com.impinj.octane.MemoryBank;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.PcBits;
import com.impinj.octane.SequenceState;
import com.impinj.octane.Tag;
import com.impinj.octane.TagData;
import com.impinj.octane.TagLockOpResult;
import com.impinj.octane.TagMarginReadOp;
import com.impinj.octane.TagMarginReadOpResult;
import com.impinj.octane.TagOpCompleteListener;
import com.impinj.octane.TagOpReport;
import com.impinj.octane.TagOpResult;
import com.impinj.octane.TagOpSequence;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;
import com.impinj.octane.TagWriteOp;
import com.impinj.octane.TagWriteOpResult;
import com.impinj.octane.TargetTag;
import com.impinj.octane.WordPointers;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import marginafterdecomplie.TagReptLisImplandMarginA.MyTag;
import static marginafterdecomplie.UterqueEncodingAfterReport.EPC_OP_ID;
import static marginafterdecomplie.UterqueEncodingAfterReport.TID_OP_ID;
import static marginafterdecomplie.cin_txt.typeTidHead;

public class TagReptLisImplandMarginA
        implements TagReportListener {

    MarginTest marginW = MainRun.marginW;
    public static Set<MyTag> Myhashdata = new HashSet();
    public String MyMarginResult;
    public String c;
    public MyTag pt;
    public String HexEpc;
    public static ArrayList Mepclist = new ArrayList();
    public static ArrayList Mtidlist = new ArrayList();
    public static  ArrayList epclist = new ArrayList();
    public static ArrayList tidlist = new ArrayList();
    public static ArrayList tidepc = new ArrayList();
     public static ArrayList Resulttidepc = new ArrayList();
    public static String tidepcT;
    public static int idCounter = 1;
    public int times = 0;
     int  InsufficientPower1=0;
     String preType="?";
     EPC2Barcode barcode= new EPC2Barcode();
    //cin_txt txt1= new cin_txt();

    public TagReptLisImplandMarginA() {
    }
    int MarginPoint = 0;

    public class MyTag {

        private String epc;
        private String tid;
        private String ctype;
           public String getCtype() {
            return tid;
        }

        public void setCtype(String tid) {
            this.tid = tid;
        }

        MyTag() {
        }

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
            MyTag other = (MyTag) obj;
            return tid.equals(other.tid) && epc.equals(other.epc);
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

        private TagReptLisImplandMarginA getOuterType() {
            return TagReptLisImplandMarginA.this;
        }

    }

    @Override
    public synchronized void onTagReported(ImpinjReader reader, TagReport report) {
        //reader.setTagOpCompleteListener(new MarginEpc());
        reader.setTagOpCompleteListener(new MarginEpc());
        List<Tag> tags = report.getTags();
        if (tags.size() > 1) {
            javax.swing.JOptionPane.showMessageDialog(null, "2 tags or above");
        }
        for (Tag tag : tags) {
            MyTag mytag = new MyTag();
            mytag.setEpc(tag.getEpc().toHexString());
            mytag.setTid(tag.getTid().toHexString());
            tidepcT = mytag.tid + "---" + mytag.epc;
            if (!Myhashdata.contains(mytag)) // int te= tidepc.indexOf(tidepcT);
            // if (te==-1)
            {
                     String currentEpc = mytag.getEpc();
                 String currentTid = mytag.getTid();
          DefaultTableModel tableModel = (DefaultTableModel) marginW.ListT.getModel();
            Calendar cal = Calendar.getInstance();
                 Date date = cal.getTime();
                    String nowtime = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(date);
           String chipType = getChipType(currentTid);
           String Barcode=barcode.getBarcode(currentEpc);
              
         tableModel.addRow(new String[]{marginW.ListT.getRowCount() + 1 + "", currentTid, currentEpc, "", "", chipType, Barcode, nowtime});        
                System.out.println(tidepcT);
                Myhashdata.add(mytag);
                Resulttidepc.add(currentTid+currentEpc);
                tidepc.add(tidepcT);
                MarginEpc marginepc = new MarginEpc();           
                String newEpc = marginW.Codehex.getText();               
                String newTid = marginW.Codehex.getText();
                short pc = tag.getPcBits();
                // WriteChip writechip= new WriteChip();
                try {
                    if (null != marginW.RorW.getSelectedItem().toString()) {
                        switch (marginW.RorW.getSelectedItem().toString()) {
                            case "WriteEpc":
                                marginepc.programEpc(currentEpc, pc, newEpc, reader);
                                break;
                            case "WriteTid":
                                marginepc.programTid(currentTid, newTid, currentEpc, reader);
                                //void programTid(String currentTid,  String newTid,String CurrentEpc, ImpinjReader reader)
                                break;
                            case "LockOnly":
                             
                                LockEpcMemory.LockEpcMemory(currentEpc, reader);
                                //void programTid(String currentTid,  String newTid,String CurrentEpc, ImpinjReader reader)
                                break;
                            case "UnLock":
                               
                                //LockEpcMemory.UnLockMemory("8AF843B8", currentTid, reader);
                                LockEpcMemory.UnLockMemory("00000001", currentEpc, reader);
                                //void programTid(String currentTid,  String newTid,String CurrentEpc, ImpinjReader reader)
                                break;
                            case "ReadMargin":
                                try {
                                    marginepc.GetMarginresult(mytag, reader);
                                } catch (OctaneSdkException ex) {
                                    Logger.getLogger(TagReptLisImplandMarginA.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            default:
                                break;
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Failed To program EPC: " + e.toString());
                }
            } else {
                //reader.queryStatus().getIsSingulating();
                //if (epclist.size()==0) return;
                if (!marginW.RorW.getSelectedItem().toString().equals("ReadMargin")) {
                    return;
                }
                String cepc = tag.getEpc().toHexString();
                String ctid = tag.getTid().toHexString();
                int Tablerow = Resulttidepc.indexOf(ctid+cepc);
                try {
                    DefaultTableModel tableModel = (DefaultTableModel) marginW.ListT.getModel();
                    marginW.ListT.setRowSelectionInterval(Tablerow, Tablerow);
                    System.out.println(Tablerow + "height" + marginW.ListT.getHeight());
                    Rectangle rect = marginW.ListT.getCellRect(Tablerow, 0, true);
                    marginW.ListT.scrollRectToVisible(rect);
                    tableModel.fireTableDataChanged();
                    marginW.ListT.setRowSelectionInterval(Tablerow, Tablerow);
                    System.out.println(tag.getEpc());
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    private class MarginEpc implements TagOpCompleteListener {

        //cin_txt txt1= new cin_txt();
        int outstanding = 0;

        public void GetMarginresult(TagReptLisImplandMarginA.MyTag m, ImpinjReader reader) throws OctaneSdkException {
            String srEPC = m.getEpc();
            String srTID = m.getTid();
            pt = m;
            TagOpSequence seq = new TagOpSequence();
            seq.setOps(new ArrayList());
            seq.setExecutionCount((short) 1);
            seq.setState(SequenceState.Active);
            seq.setId(TagReptLisImplandMarginA.idCounter++);
            TargetTag targetTag = new TargetTag();
            seq.setTargetTag(targetTag);
            TagMarginReadOp marginReadOp = new TagMarginReadOp();
            marginReadOp.setMarginMask(new MarginReadMask());
            if ("TID".equals(marginW.TIDEPC.getSelectedItem().toString())) {
                //mask is Tid here
                seq.getTargetTag().setBitPointer((short) 0);
                seq.getTargetTag().setMemoryBank(MemoryBank.Tid);
                seq.getTargetTag().setData(srTID);
                marginReadOp.getMarginMask().setMaskFromHexString(srTID);
                marginReadOp.setBitPointer(0);
                marginReadOp.setMemoryBank(MemoryBank.Tid);
            } else {
                seq.getTargetTag().setBitPointer((short) 32);
                seq.getTargetTag().setMemoryBank(MemoryBank.Epc);
                seq.getTargetTag().setData(srEPC);
                marginReadOp.getMarginMask().setMaskFromHexString(srEPC);
                marginReadOp.setBitPointer(32);
                marginReadOp.setMemoryBank(MemoryBank.Epc);

            }
            MarginPoint++;
            seq.getOps().add(marginReadOp);
            reader.addOpSequence(seq);

        }

        void programEpc(String currentEpc, short currentPC, String newEpc, ImpinjReader reader)
                throws Exception {

            if ((currentEpc.length() % 4 != 0) || (newEpc.length() % 4 != 0)) {
                throw new Exception("EPCs must be a multiple of 16- bits: "
                        + currentEpc + "  " + newEpc);
            }

            if (outstanding > 0) {
                return;
            }

            System.out.println("Programming Tag EPC");
            System.out.println("   EPC " + currentEpc + " to " + newEpc);
            TagOpSequence seq = new TagOpSequence();
            seq.setOps(new ArrayList<>());
            seq.setExecutionCount((short) 1); // delete after one time
            seq.setState(SequenceState.Active);
            seq.setId(idCounter++);
            seq.setTargetTag(new TargetTag());
            seq.getTargetTag().setBitPointer(BitPointers.Epc);
            seq.getTargetTag().setMemoryBank(MemoryBank.Epc);
            seq.getTargetTag().setData(currentEpc);
            TagWriteOp epcWrite = new TagWriteOp();
            epcWrite.Id = EPC_OP_ID;
            epcWrite.setMemoryBank(MemoryBank.Epc);
            epcWrite.setWordPointer(WordPointers.Epc);
            epcWrite.setData(TagData.fromHexString(newEpc));

            // add to the list
            seq.getOps().add(epcWrite);

            // have to program the PC bits if these are not the same
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
                seq.getOps().add(pcWrite);
            }

            outstanding++;
            reader.removeTagOpCompleteListener();
            reader.addOpSequence(seq);

            // reader.setTagOpCompleteListener(new WriteChip());
        }

        void programTid(String currentTid, String newTid, String CurrentEpc, ImpinjReader reader)
                throws Exception {

            if ((currentTid.length() % 4 != 0) || (newTid.length() % 4 != 0)) {
                throw new Exception("EPCs must be a multiple of 16- bits: "
                        + currentTid + "  " + newTid);
            }

            if (outstanding > 0) {
                return;
            }

            System.out.println("Programming Tag Tid ");
            System.out.println("   Tid: " + currentTid + " to " + newTid);
            TagOpSequence seq = new TagOpSequence();
            seq.setOps(new ArrayList<>());
            seq.setExecutionCount((short) 1); // delete after one time
            seq.setState(SequenceState.Active);
            seq.setId(idCounter++);
            seq.setTargetTag(new TargetTag());
            // seq.getTargetTag().setBitPointer(BitPointers.Epc);
            // seq.getTargetTag().setMemoryBank(MemoryBank.Epc);
            //   seq.getTargetTag().setData(CurrentEpc);

            seq.getTargetTag().setBitPointer((short) 0);
            seq.getTargetTag().setMemoryBank(MemoryBank.Tid);
            seq.getTargetTag().setData(currentTid);

            TagWriteOp tidWrite = new TagWriteOp();
            tidWrite.Id = TID_OP_ID;
            tidWrite.setMemoryBank(MemoryBank.Tid);
            tidWrite.setWordPointer((short) 0);
            tidWrite.setData(TagData.fromHexString(newTid));

            // add to the list
            seq.getOps().add(tidWrite);

            // have to program the PC bits if these are not the same
            outstanding++;
            //reader.removeTagOpCompleteListener();
            reader.addOpSequence(seq);

            // reader.setTagOpCompleteListener(new WriteChip());
        }

        @Override
        public synchronized void onTagOpComplete(ImpinjReader reader, TagOpReport results) {
            System.out.println("TagOpComplete: ");
            results.getResults().forEach((TagOpResult t) -> {
                if ((t instanceof TagMarginReadOpResult)) {
                    TagMarginReadOpResult mrResult = (TagMarginReadOpResult) t;
                    MyMarginResult = (mrResult.getResult() + "");
                    System.out.println("Margin Read Complete (" + mrResult
                            .getTag().getEpc() + ")--- " + mrResult.getResult());
                    final String result = MyMarginResult + "";
                    final DefaultTableModel tableModel = (DefaultTableModel) marginW.ListT.getModel();
                    Tag tag = t.getTag();
                    final String epc = tag.getEpc().toHexString();
                    final String tid = tag.getTid().toHexString();
                    
                    if ("InsufficientPower".equals(result)) {
                        Mepclist.add(epc);
                        times = Collections.frequency(Mepclist, epc);
                        TagReptLisImplandMarginA.MyTag myTag = new TagReptLisImplandMarginA.MyTag();
                        myTag.setEpc(epc);
                        myTag.setTid(tid);
                        tidepcT = myTag.tid + myTag.epc;
                        InsufficientPower1++;
                        if  (InsufficientPower1>5){;
                        int Mepcrow = Resulttidepc.indexOf(tid+epc);
                        tableModel.setValueAt(result, Mepcrow, 4);
                        }else
                        {
                            boolean ok = TagReptLisImplandMarginA.Myhashdata.remove(myTag);
                            System.out.println("removing ..." + epc + " - " + ok);
                        }
                    } else {
                        new Thread(() -> {
                            epclist.add(epc);
                            tidlist.add(tid);
                            // Resulttidepc.add(tid+epc);
                            Calendar cal = Calendar.getInstance();
                            Date date = cal.getTime();
                            int Mepcrow = Resulttidepc.indexOf(tid+epc);
                            tableModel.setValueAt(result, Mepcrow, 4);
                            // tableModel.addRow(new String[]{marginW.ListT.getRowCount() + 1 + "", tid, epc, "", result + "---" + times + "times", chipType, nowtime});
                            System.out.println(times + "--" + epc + "--" + result + "---" + times + "times");
                            times = 0;
                            int rowN = marginW.ListT.getRowCount() - 1;
                            if (Collections.frequency(epclist, epc) > 1) {
                                
                                tableModel.setValueAt("Duplicate EPC with row:" + (rowN + 1), Mepcrow, 3);
                                tableModel.setValueAt("Duplicate EPC with row:" + (Mepcrow + 1), rowN, 3);
                            }
                            if (Collections.frequency(tidlist, tid) > 1) {
                                Mepcrow = tidlist.indexOf(tid);
                                marginW.ListT.setRowSelectionInterval(Mepcrow, Mepcrow);
                                tableModel.setValueAt("Duplicate TID with row:" + (rowN + 1), Mepcrow, 3);
                                tableModel.setValueAt("Duplicate TID with row:" + (Mepcrow + 1), rowN, 3);
                            }
                            marginW.Total1.setText(Resulttidepc.size() + "");
                            Rectangle rect = new Rectangle(0, marginW.ListT.getHeight(), 25, 25);
                            marginW.ListT.scrollRectToVisible(rect);
                            marginW.ListT.setRowSelectionInterval(marginW.ListT.getRowCount() - 1, marginW.ListT
                                    .getRowCount() - 1);
                            marginW.ListT.grabFocus();
                            marginW.ListT.changeSelection(marginW.ListT.getRowCount() - 1, 0, false, true);
                            MarginPoint++;
                        })
                                .start();
                    }
                } 
                else  if (t instanceof TagLockOpResult) {
                TagLockOpResult tl = (TagLockOpResult) t;
                System.out.print(" LOCK: id: " + tl.getOpId());
                System.out.print(" sequence: " + tl.getSequenceId());
                System.out.print(" result: " + tl.getResult().toString());
            }
                
                else {
                   // System.out.println("TagOpComplete-epcwrite: ");
                    System.out.print("  EPC: " + t.getTag().getEpc().toHexString());
                    if (t instanceof TagWriteOpResult) {
                        TagWriteOpResult tr = (TagWriteOpResult) t;
                        
                        if (tr.getOpId() == EPC_OP_ID) {
                            System.out.print("  Write to EPC Complete: ");
                        } else if (tr.getOpId() == TID_OP_ID) {
                            System.out.print("  Write to TID Complete: ");
                        }
                        System.out.println(" result: " + tr.getResult().toString()
                                + " words_written: " + tr.getNumWordsWritten());
                        outstanding--;
                    }
                    
                }
            });
        }

     

    }
     public static String getChipType(String TID)
     {  
          
              typeTidHead="";
              String TIDhead=TID.substring(0,8);
              int tt= MainRun.chipHead.indexOf(TIDhead);
              typeTidHead=MainRun.ChipType.get(tt).toString();
          
     
             return typeTidHead;
        }  
}

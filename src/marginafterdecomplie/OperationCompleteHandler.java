package marginafterdecomplie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Tag;
import com.impinj.octane.TagKillOpResult;
import com.impinj.octane.TagLockOpResult;
import com.impinj.octane.TagOpCompleteListener;
import com.impinj.octane.TagOpReport;
import com.impinj.octane.TagOpResult;
import com.impinj.octane.TagQtGetOpResult;
import com.impinj.octane.TagQtSetOpResult;
import com.impinj.octane.TagReadOpResult;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;
import com.impinj.octane.TagWriteOpResult;
import com.ruiz.constant.MainTableColumn;
import com.ruiz.constant.ReaderOpConsts;
import com.ruiz.model.domain.OpCompleteHandler;
import com.ruiz.model.entity.AfterEncodingTidEpcIdMatch;
import com.ruiz.model.entity.ChipInfo;
import com.ruiz.utils.ContextManager;
import static com.ruiz.utils.ContextManager.tidList;
import com.ruiz.utils.ReaderManager;
import java.awt.Rectangle;
import javax.swing.table.DefaultTableModel;
import static marginafterdecomplie.MarginTest.codingForm;
//Result Message could be the followings:
// Success, 
// TagMemoryOverrunError,
// TagMemoryLockedError, 
// InsufficientPower,
// NonspecificTagError, 
// NoResponseFromTag,
// NonspecificReaderError
public class OperationCompleteHandler implements TagReportListener, TagOpCompleteListener {
	public static ArrayList Mepclist = new ArrayList();
	public static ArrayList Mtidlist = new ArrayList();
	public static ArrayList epclist = new ArrayList();
       // public static ArrayList tidList = new ArrayList();	
	public static ArrayList<String> tidEpcList = new ArrayList<String>();
	public String tidepcT;	
	//public static Map<String, Map<String, String>> epcTagInfoMap = new HashMap<String, Map<String, String>>();
        // Store the TID, EPC and encoding source ID map
        public static Map< String,AfterEncodingTidEpcIdMatch> TidEpcMatchID = new HashMap< String,AfterEncodingTidEpcIdMatch>();
	public static int ID=0;
       public static ChipInfo chipInfo;
       DefaultTableModel tableModel = (DefaultTableModel) codingForm.ListT.getModel();	
	OpCompleteHandler opCompleteHandler = new OpCompleteHandler();	
	@Override
	public void onTagOpComplete(ImpinjReader reader, TagOpReport results) {
		System.out.println("TagOpComplete: ");		
		String pcOPResultMsg = "";
		String epcOpResultMsg = "";
		String TID_OP_rst = "";
		String accessPwOPResultMsg = "";
		String userMemOPResultMsg = "";
		String lockOPResultMsg = "";
		ReaderManager.outstanding--;
            for (TagOpResult t : results.getResults()) {
              //  System.out.print("  EPC: " + t.getTag().getEpc().toHexString());
                if (t instanceof TagReadOpResult) {
                    opCompleteHandler.onReadCompletion(t);
                } else if (t instanceof TagWriteOpResult) {
                    TagWriteOpResult tw = (TagWriteOpResult) t;                   
                       if ((!tw.getResult().toString().equals("Success") && tw.getResult().toString().equals("TagMemoryLockedError")) ) {
                  int TieRow=ContextManager.indexOfTidList(tw.getTag().getTid().toHexString());
                           String status=tableModel.getValueAt(TieRow, MainTableColumn.COLUMN_STATUS).toString();
                     if (!"Success".equals(status))  tableModel.setValueAt("TagLocked", TieRow, MainTableColumn.COLUMN_STATUS);
                      TidEpcMatchID.get(tw.getTag().getTid().toHexString()).getchipinfo().setProgstatus("TagMemoryLockedError");
                     
                }  
                    
                    System.out.println(" Tag Tid: " + tw.getTag().getTid().toHexString());
                    if (tw.getOpId() == ReaderOpConsts.EPC_OP_ID) {
                        System.out.print(" WRITE EPC: id: " + tw.getOpId());
                        epcOpResultMsg = tw.getResult().toString();
                    } else if (tw.getOpId() == ReaderOpConsts.AccessPw_OP_ID) {
                        System.out.print(" WRITE AccessPw: id: " + tw.getOpId());
                        accessPwOPResultMsg = tw.getResult().toString();
                    } else if (tw.getOpId() == ReaderOpConsts.UserM_OP_ID) {
                        System.out.print(" WRITE user: id: " + tw.getOpId());
                        userMemOPResultMsg = tw.getResult().toString();
                    } else if (tw.getOpId() == ReaderOpConsts.PC_OP_ID) {
                        System.out.print(" WRITE PC: id: " + tw.getOpId());
                        pcOPResultMsg = tw.getResult().toString();
                    } else {
                        System.out.print(" WRITE nameless: id: " + tw.getOpId());
                    }
                    
                    System.out.print(" WRITE: id: " + tw.getClass().getName());
                    System.out.print(" sequence: " + tw.getSequenceId());
                    System.out.print(" result: " + tw.getResult().toString());
                    System.out.println(" words_written: " + tw.getNumWordsWritten());
                }else if (t instanceof TagKillOpResult) {
                    opCompleteHandler.onKillCompletion(t);
                }else if (t instanceof TagLockOpResult) {
                    opCompleteHandler.onLockCompletion(epcOpResultMsg, accessPwOPResultMsg, userMemOPResultMsg, t,reader);
             //   }else if (t instanceof TagBlockPermalockOpResult) {
                   // opCompleteHandler.onBlockPermLockCompletion(t);
                }else if (t instanceof TagQtGetOpResult) {
                    opCompleteHandler.onQtGetCompletion(t);
                }else if (t instanceof TagQtSetOpResult) {
                    opCompleteHandler.onQtSetCompletion(t);
                }
            }
	}

	@Override
	public void onTagReported(ImpinjReader reader, TagReport report) {	           
           // if (ReaderManager.outstanding>0)return;           
		List<Tag> tags = report.getTags();
		if (tags.size() > 1) {
			javax.swing.JOptionPane.showMessageDialog(null, "Cant not handler more than two tags read at the same time!");
			return;
		}	
                  
		Tag tag = tags.get(0);
		String epc = tag.getEpc().toHexString();
		String tid = tag.getTid().toHexString();
                /*
                   codingForm.Total.setText(tidList.size() + "");
                  Rectangle rect = new Rectangle(0, codingForm.ListT.getHeight(), 25, 25);
                  codingForm.ListT.scrollRectToVisible(rect);
                  codingForm.ListT.setRowSelectionInterval(ContextManager.indexOfTidList(tid),
                  codingForm.ListT.getRowCount() - 1);
                  codingForm.ListT.grabFocus();
                  codingForm.ListT.changeSelection(codingForm.ListT.getRowCount() - 1, 0, false, true);
                */
                
                  if (tidEpcList.isEmpty()) codingForm.ChipType.setText(cin_txt.getChipType(tid));                 
		 chipInfo = new ChipInfo();                
		chipInfo.setEpc(epc);
		chipInfo.setTid(tid);		
           if(ContextManager.isProcessed(chipInfo)) return;
           if (TidEpcMatchID.size()>0)
           {
               String ifsuccess="";
            AfterEncodingTidEpcIdMatch After1=TidEpcMatchID.get(tid);   
          if (After1!=null) ifsuccess=After1.getchipinfo().getProgstatus();
           if ("Success".equals(ifsuccess)||"TagMemoryLockedError".equals(ifsuccess)) return; 
           }
           
		ContextManager.addChipInfo(chipInfo);		
		String tidepcT = chipInfo.getTid() + "---" + chipInfo.getEpc();
		tidEpcList.add(tidepcT);
                //tidList.add(tid);
		opCompleteHandler.onReportHandler(reader, tag, epc, chipInfo);
	}

	
}

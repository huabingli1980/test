package com.ruiz.model.domain;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.QtGetConfigResultStatus;
import com.impinj.octane.ReadResultStatus;
import com.impinj.octane.Tag;
import com.impinj.octane.TagBlockPermalockOpResult;
import com.impinj.octane.TagKillOpResult;
import com.impinj.octane.TagLockOpResult;
import com.impinj.octane.TagOpResult;
import com.impinj.octane.TagQtGetOpResult;
import com.impinj.octane.TagQtSetOpResult;
import com.impinj.octane.TagReadOpResult;
import com.ruiz.constant.MainTableColumn;
import com.ruiz.constant.ReaderOpConsts;
import com.ruiz.model.entity.AfterEncodingTidEpcIdMatch;
import com.ruiz.model.entity.ChipInfo;
import com.ruiz.model.entity.ProgramInfo;
import com.ruiz.utils.ContextManager;
import static com.ruiz.utils.ContextManager.tidList;
import com.ruiz.utils.DateUtils;
import com.ruiz.utils.ReaderManager;
//import static com.ruiz.utils.ReaderManager.newEpc;
import java.awt.Rectangle;
import static marginafterdecomplie.MarginTest.codingForm;

import marginafterdecomplie.OperationCompleteHandler;
import static marginafterdecomplie.OperationCompleteHandler.ID;
import static marginafterdecomplie.OperationCompleteHandler.TidEpcMatchID;
//import static marginafterdecomplie.OperationCompleteHandler.TidMatchID;
import marginafterdecomplie.Repository;
import marginafterdecomplie.Sqlite;
//import static marginafterdecomplie.TagReptLisImplandMarginA.tidlist;
import marginafterdecomplie.WriteChip;
import marginafterdecomplie.cin_txt;

public class OpCompleteHandler {
    int good=0;
	DefaultTableModel tableModel = (DefaultTableModel) codingForm.ListT.getModel();;
	Sqlite sqlite = new Sqlite();
	Repository repository = new Repository();
	ReaderManager readerManager = new ReaderManager();
       public static int workingRow=0; 
	public void onReportHandler(ImpinjReader reader, Tag tag, String epc, ChipInfo chipInfo) {
		/**
		 * Add a row with the initial information of the tag to be acted on to the table
		 */
		String currentEpc=chipInfo.getEpc();
		String currentTidR=chipInfo.getTid();
		String nowtime = DateUtils.getCurrentTimeStr();
		String rowNumber = codingForm.ListT.getRowCount() + 1 + "";		
	if (ContextManager.indexOfTidList(currentTidR)==-1){
            tableModel.addRow(new String[] { 
				rowNumber, 
				currentTidR, 
				currentEpc, 
				"",
				"", 
				"", 
				nowtime, 
				"" 
		});
            ContextManager.addTid(chipInfo.getTid());
        if (!cin_txt.getChipType(currentTidR).equals(codingForm.ChipType.getText())){
            tableModel.setValueAt("TidHeadMissMatch", ContextManager.indexOfTidList(currentTidR), MainTableColumn.COLUMN_STATUS);
            return;}
        }   
        
             if (TidEpcMatchID.get(currentTidR)==null)  ID++;
		System.out.println("\\nOn tag report...."+ID+"---tid:"+currentTidR);
                codingForm.Total.setText(tidList.size() + "");
                  Rectangle rect = new Rectangle(0, codingForm.ListT.getHeight(), 25, 25);
                  codingForm.ListT.scrollRectToVisible(rect);
                  codingForm.ListT.setRowSelectionInterval(ContextManager.indexOfTidList(currentTidR),
                  codingForm.ListT.getRowCount() - 1);
                  codingForm.ListT.grabFocus();
                  codingForm.ListT.changeSelection(codingForm.ListT.getRowCount() - 1, 0, false, true);
                  //MarginPoint++;
		
		try {
			//sqlite.markUsedByEPC(epc);			
			short pcBit = tag.getPcBits();
			ProgramInfo programInfo;
                        if (TidEpcMatchID.get(currentTidR)!=null){ 
                          int  pID=TidEpcMatchID.get(currentTidR).getid();
                           programInfo = repository.getProgramInfoByID(pID);
                        }else{ programInfo = repository.getProgramInfoByID(ID);
                                    }
                       String ToencodeEpc=programInfo.getEpc();     
                        tableModel.setValueAt(ToencodeEpc, ContextManager.indexOfTidList(currentTidR), MainTableColumn.COLUMN_NEWEPC);
                    AfterEncodingTidEpcIdMatch  afterEncodingTidEpcIdMatch=new AfterEncodingTidEpcIdMatch(currentTidR,ToencodeEpc,chipInfo,ID);
                        
                        TidEpcMatchID.put(currentTidR,afterEncodingTidEpcIdMatch);
			readerManager.programTag(currentEpc, pcBit, currentTidR, programInfo, reader);
			
		} catch (Exception ex) {
			Logger.getLogger(OperationCompleteHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void onQtSetCompletion(TagOpResult t) {
		TagQtSetOpResult tqt = (TagQtSetOpResult) t;
		System.out.print(" QT_SET id: " + tqt.getOpId());
		System.out.print(" sequence: " + tqt.getSequenceId());
		System.out.print(" result: " + tqt.getResult().toString());
	}

	public void onQtGetCompletion(TagOpResult t) {
		TagQtGetOpResult tqt = (TagQtGetOpResult) t;
		System.out.print(" QT_GET id: " + tqt.getOpId());
		System.out.print(" sequence: " + tqt.getSequenceId());
		System.out.print(" result: " + tqt.getResult().toString());
		if (tqt.getResult() == QtGetConfigResultStatus.Success) {
			System.out.print(" mode: " + tqt.getDataProfile().toString());
			System.out.print(" range: " + tqt.getAccessRange().toString());
		}
	}

	public void onBlockPermLockCompletion(TagOpResult t) {
		TagBlockPermalockOpResult tbp = (TagBlockPermalockOpResult) t;
		System.out.print(" BLOCK_PERMALOCK id: " + tbp.getOpId());
		System.out.print(" sequence: " + tbp.getSequenceId());
		System.out.println(" result: " + tbp.getResult().toString());
	}

	public void onLockCompletion(String epcOpResultMsg, String accessPwOPResultMsg, String userMemOPResultMsg,
		TagOpResult t,ImpinjReader reader) {
		String lockOPResultMsg;
		TagLockOpResult tl = (TagLockOpResult) t;
                String currentTid=t.getTag().getTid().toHexString();
                  String currentEpc=t.getTag().getEpc().toHexString();
                  short pcBit=t.getTag().getPcBits();
		if (tl.getOpId() == ReaderOpConsts.LOCK_OP_ID) {
			System.out.print(" LockOP: id: " + tl.getOpId());
		} else {
			System.out.print(" Lock nameless: id: " + tl.getOpId());
		}
		
		lockOPResultMsg = tl.getResult().toString();
		
		System.out.print(" sequence: " + tl.getSequenceId());
		System.out.print(" Tag Tid: " + tl.getTag().getTid());
		System.out.println(" result: " + tl.getResult().toString());
                
                if ((!epcOpResultMsg.equals("Success")||!accessPwOPResultMsg.equals("Success") 
				    || !userMemOPResultMsg.equals("Success")
				        || !lockOPResultMsg.equals("Success") && !epcOpResultMsg.equals("TagMemoryLockedError")) ) {
                    if (lockOPResultMsg.equals("NoResponseFromTag")) try {
                        WriteChip.programAccessPW(currentTid,reader );
                    } catch (Exception ex) {
                        Logger.getLogger(OpCompleteHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ProgramInfo programInfo = repository.getProgramInfoByID(TidEpcMatchID.get(currentTid).getid());
                    try {
                        readerManager.programTag(currentEpc, pcBit, currentTid, programInfo, reader);
                    } catch (Exception ex) {
                        Logger.getLogger(OpCompleteHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ContextManager.tagsProcessed.remove(TidEpcMatchID.get(currentTid).getchipinfo());
                    
                }

		if (epcOpResultMsg.equals("Success") 
				&& accessPwOPResultMsg.equals("Success") 
				    && userMemOPResultMsg.equals("Success")
				        && lockOPResultMsg.equals("Success")) {			
			String tid = tl.getTag().getTid().toHexString();			
			 workingRow = ContextManager.indexOfTidList(tid);
			try {
				String timeStart = tableModel.getValueAt(workingRow, MainTableColumn.COLUMN_TIME_START).toString();				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
				Date startDate = sdf.parse(timeStart);				
				long timeSpent = DateUtils.getTimeElapsedInMillisec(startDate.getTime());
				tableModel.setValueAt(timeSpent, workingRow, MainTableColumn.COLUMN_TIME_SPENT);				
			} catch (ParseException ex) {
				String name = OperationCompleteHandler.class.getName();
				Logger.getLogger(name).log(Level.SEVERE, null, ex);
			}
			String encodedEpc=TidEpcMatchID.get(currentTid).getEpc();
                        TidEpcMatchID.get(currentTid).getchipinfo().setProgstatus("Success");
                        good++;
                        codingForm.Good.setText(good + "");
			sqlite.markSuccessByEPC(encodedEpc);			
			tableModel.setValueAt(encodedEpc, workingRow, MainTableColumn.COLUMN_NEWEPC);
			tableModel.setValueAt("Success", workingRow, MainTableColumn.COLUMN_STATUS);
		}
	}

	public void onKillCompletion(TagOpResult t) {
		TagKillOpResult tk = (TagKillOpResult) t;
		System.out.print(" KILL: id: " + tk.getOpId());
		System.out.print(" sequence: " + tk.getSequenceId());
		System.out.print(" result: " + tk.getResult().toString());
	}

	public void onReadCompletion(TagOpResult t) {
		TagReadOpResult tr = (TagReadOpResult) t;
		System.out.print(" READ: id: " + tr.getOpId());
		System.out.print(" sequence: " + tr.getSequenceId());
		System.out.print(" result: " + tr.getResult().toString());
		if (tr.getResult() == ReadResultStatus.Success) {
			System.out.print(" data: " + tr.getData().toHexWordString());
		}
	}

}

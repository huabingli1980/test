package com.ruiz.model.domain;

import static marginafterdecomplie.MarginTest.codingForm;

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
import com.ruiz.model.entity.ChipInfo;
import com.ruiz.model.entity.ProgramInfo;
import com.ruiz.utils.ContextManager;
import com.ruiz.utils.DateUtils;
import com.ruiz.utils.ReaderManager;

import marginafterdecomplie.OperationCompleteHandler;
import marginafterdecomplie.Repository;
import marginafterdecomplie.Sqlite;

public class OpCompleteHandler {
	DefaultTableModel tableModel = (DefaultTableModel) codingForm.ListT.getModel();;
	Sqlite sqlite = new Sqlite();
	Repository repository = new Repository();
	ReaderManager readerManager = new ReaderManager();
	
	public void onReportHandler(ImpinjReader reader, Tag tag, String epc, ChipInfo chipInfo) {
		/**
		 * Add a row with the initial information of the tag to be acted on to the table
		 */
		String currentEpc = chipInfo.getEpc();
		String currentTid = chipInfo.getTid();
		String nowtime = DateUtils.getCurrentTimeStr();
		String rowNumber = codingForm.ListT.getRowCount() + 1 + "";
		
		tableModel.addRow(new String[] { 
				rowNumber, 
				currentTid, 
				currentEpc, 
				"",
				"", 
				"", 
				nowtime, 
				"" 
		});
		
		try {
			sqlite.markUsedByEPC(epc);
			
			short pcBit = tag.getPcBits();
			ProgramInfo programInfo = repository.getProgramInfoByEPC(currentEpc);
			readerManager.programTag(currentEpc, pcBit, currentTid, programInfo, reader);
			
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
		System.out.print(" result: " + tbp.getResult().toString());
	}

	public void onLockCompletion(String epcOpResultMsg, String accessPwOPResultMsg, String userMemOPResultMsg,
			TagOpResult t) {
		String lockOPResultMsg;
		TagLockOpResult tl = (TagLockOpResult) t;
		if (tl.getOpId() == ReaderOpConsts.LOCK_OP_ID) {
			System.out.print(" LockOP: id: " + tl.getOpId());
		} else {
			System.out.print(" Lock nameless: id: " + tl.getOpId());
		}
		
		lockOPResultMsg = tl.getResult().toString();
		
		System.out.print(" sequence: " + tl.getSequenceId());
		System.out.print(" Tag Tid: " + tl.getTag().getTid());
		System.out.print(" result: " + tl.getResult().toString());

		if (epcOpResultMsg.equals("Success") 
				&& accessPwOPResultMsg.equals("Success") 
				    && userMemOPResultMsg.equals("Success")
				        && lockOPResultMsg.equals("Success")) {
			
			String tid = tl.getTag().getTid().toHexString();
			String newEpc = tl.getTag().getEpc().toHexString();
			int workingRow = ContextManager.indexOfTidList(tid);

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
			
			sqlite.markSuccessByEPC(newEpc);
			
			tableModel.setValueAt(newEpc, workingRow, MainTableColumn.COLUMN_EPC);
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

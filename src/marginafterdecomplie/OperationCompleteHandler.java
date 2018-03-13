package marginafterdecomplie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Tag;
import com.impinj.octane.TagBlockPermalockOpResult;
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
import com.ruiz.constant.ReaderOpConsts;
import com.ruiz.model.domain.OpCompleteHandler;
import com.ruiz.model.entity.ChipInfo;
import com.ruiz.utils.ContextManager;
//Result Message could be the followings:
// Success, 
// TagMemoryOverrunError,
// TagMemoryLockedError, 
// InsufficientPower,
// NonspecificTagError, 
// NoResponseFromTag,
// NonspecificReaderError
public class OperationCompleteHandler implements TagReportListener, TagOpCompleteListener {

	public ArrayList Mepclist = new ArrayList();
	public ArrayList Mtidlist = new ArrayList();
	public ArrayList epclist = new ArrayList();
	
	public ArrayList<String> tidEpcList = new ArrayList<String>();
	public String tidepcT;
	
	public static Map<String, Map<String, String>> epcTagInfoMap = new HashMap<String, Map<String, String>>();
	
	
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
		
		for (TagOpResult t : results.getResults()) {
			System.out.print("  EPC: " + t.getTag().getEpc().toHexString());
			
			if (t instanceof TagReadOpResult) {
				opCompleteHandler.onReadCompletion(t);
			} else if (t instanceof TagWriteOpResult) {
				TagWriteOpResult tw = (TagWriteOpResult) t;
				
				System.out.println(" Tag Tid: " + tw.getTag().getTid());
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
				System.out.print(" words_written: " + tw.getNumWordsWritten());
			}else if (t instanceof TagKillOpResult) {
				opCompleteHandler.onKillCompletion(t);
			}else if (t instanceof TagLockOpResult) {
				opCompleteHandler.onLockCompletion(epcOpResultMsg, accessPwOPResultMsg, userMemOPResultMsg, t);
			}else if (t instanceof TagBlockPermalockOpResult) {
				opCompleteHandler.onBlockPermLockCompletion(t);
			}else if (t instanceof TagQtGetOpResult) {
				opCompleteHandler.onQtGetCompletion(t);
			}else if (t instanceof TagQtSetOpResult) {
				opCompleteHandler.onQtSetCompletion(t);
			}
		}
	}

	@Override
	public synchronized void onTagReported(ImpinjReader reader, TagReport report) {
		
		List<Tag> tags = report.getTags();
		if (tags.size() > 1) {
			javax.swing.JOptionPane.showMessageDialog(null, "Cant not handler more than two tags read at the same time!");
			return;
		}
		
		Tag tag = tags.get(0);
		String epc = tag.getEpc().toHexString();
		String tid = tag.getTid().toHexString();
			
		ChipInfo chipInfo = new ChipInfo();
		chipInfo.setEpc(epc);
		chipInfo.setTid(tid);
		
		if(ContextManager.isProcessed(chipInfo)) return;
		
		ContextManager.addChipInfo(chipInfo);
		
		String tidepcT = chipInfo.getTid() + "---" + chipInfo.getEpc();
		tidEpcList.add(tidepcT);
		
		ContextManager.addTid(chipInfo.getTid());
		
		opCompleteHandler.onReportHandler(reader, tag, epc, chipInfo);
	}

	
}

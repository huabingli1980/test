package com.ruiz.utils;

import java.util.ArrayList;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.MemoryBank;
import com.impinj.octane.PcBits;
import com.impinj.octane.SequenceState;
import com.impinj.octane.TagData;
import com.impinj.octane.TagLockOp;
import com.impinj.octane.TagLockState;
import com.impinj.octane.TagOpSequence;
import com.impinj.octane.TagWriteOp;
import com.impinj.octane.TargetTag;
import com.impinj.octane.WordPointers;
import com.ruiz.constant.ReaderOpConsts;
import com.ruiz.model.entity.ProgramInfo;

public class ReaderManager {	
	public static int idCounter = 1;
	public static String pcOPResultMsg = "";
	public static String epcOpResultMsg = "";
	public static String TID_OP_rst = "";
	public static String accessPwOPResultMsg = "";
	public static String userMemOPResultMsg = "";
	public static String lockOPResultMsg = "";	
	public static int outstanding = 0;
        public static String newEpc=null;
		void WriteLockTag(String currentEpc, short currentPC, String newEpc, String TID, String UserM, String AccessPW,
				String accessPWLock, String epcLock, String KillLock, String userLock, ImpinjReader reader)
				throws Exception {
			if ((currentEpc.length() % 4 != 0) || (newEpc.length() % 4 != 0)) {
				throw new Exception("EPCs must be a multiple of 16- bits: " + currentEpc + "  " + newEpc);
			}
			//if (outstanding > 0) {
			//	return;
			//}

			pcOPResultMsg = "";
			epcOpResultMsg = "";
			TID_OP_rst = "";
			accessPwOPResultMsg = "";
			userMemOPResultMsg = "";
			lockOPResultMsg = "";

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

				short newPC = PcBits.AdjustPcBits(currentPC, (short) (newEpc.length() / 4));
				String newPCString = PcBits.toHexString(newPC);

				System.out.println("   PC bits to establish new length: " + newPCString + " " + currentPCString);

				TagWriteOp pcWrite = new TagWriteOp();
				// pcWrite.Id = PC_BITS_OP_ID;
				pcWrite.setMemoryBank(MemoryBank.Epc);
				pcWrite.setWordPointer(WordPointers.PcBits);
				pcWrite.setData(TagData.fromHexString(newPCString));
				pcWrite.Id = ReaderOpConsts.PC_OP_ID;
				seq.getOps().add(pcWrite);
			}

			TagWriteOp epcWrite = new TagWriteOp();
			epcWrite.Id = ReaderOpConsts.EPC_OP_ID;
			epcWrite.setMemoryBank(MemoryBank.Epc);
			epcWrite.setWordPointer(WordPointers.Epc);
			epcWrite.setData(TagData.fromHexString(newEpc));

			TagWriteOp AccesPWWrite = new TagWriteOp();
			AccesPWWrite.Id = ReaderOpConsts.AccessPw_OP_ID;
			AccesPWWrite.setMemoryBank(MemoryBank.Reserved);
			AccesPWWrite.setWordPointer(WordPointers.AccessPassword);
			AccesPWWrite.setData(TagData.fromHexString(AccessPW));
			// AccesPWWrite.setData(TagData.fromHexString("00000001"));

			TagWriteOp UserMWrite = new TagWriteOp();
			UserMWrite.Id = ReaderOpConsts.UserM_OP_ID;
			UserMWrite.setMemoryBank(MemoryBank.User);
			UserMWrite.setWordPointer((short) 0);
			UserMWrite.setData(TagData.fromHexString(UserM));

			TagLockOp lockOp = new TagLockOp();
			// lockOp.setAccessPassword(TagData.fromHexString(AccessPW));
			lockOp.Id = ReaderOpConsts.LOCK_OP_ID;
			lockOp.setEpcLockType(getLockType(epcLock));
			// lockOp.setTidLockType(TagLockState.Permalock);
			lockOp.setUserLockType(getLockType(userLock));
			lockOp.setAccessPasswordLockType(getLockType(accessPWLock));
			lockOp.setKillPasswordLockType(getLockType(KillLock));

			// TagReadOp readReservedMem = new TagReadOp();
			// readReservedMem.setMemoryBank(MemoryBank.Reserved);
			// readReservedMem.setWordPointer(WordPointers.AccessPassword);

			seq.getOps().add(epcWrite);
			seq.getOps().add(AccesPWWrite);
			seq.getOps().add(UserMWrite);
			seq.getOps().add(lockOp);

			// add to the list

			// have to program the PC bits if these are not the same

			outstanding++;
			//reader.removeTagOpCompleteListener();
                        
                        System.out.println("op submitted for TID:"+TID);
			reader.addOpSequence(seq);
		}
		public void programTag(String currentEpc, short pcBit, String currentTid,
				ProgramInfo programInfo, ImpinjReader reader) throws Exception {
                    newEpc=programInfo.getEpc();
			WriteLockTag(currentEpc, pcBit, programInfo.getEpc(), currentTid, 
					programInfo.getUserM(), programInfo.getAccessPW(), programInfo.getAccessPWLock(),
					programInfo.getEpcLock(),
					programInfo.getKillPWlock(),
					programInfo.getUserLock(),
					reader
					);
		}

		TagLockState getLockType(String LockType) {

			switch (LockType.toUpperCase()) {
			case "LOCK":
				return TagLockState.Lock;
			case "PERMALOCK":
				return TagLockState.Permalock;
			case "":
				return TagLockState.None;
			case "PERMAUNLOCK":
				return TagLockState.Permaunlock;
			case "UNLOCK":
				return TagLockState.Unlock;

			}
			return null;
		}

	}
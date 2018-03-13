/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marginafterdecomplie;

import com.impinj.octane.BitPointers;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.MemoryBank;
import com.impinj.octane.PcBits;
import com.impinj.octane.SequenceState;
import com.impinj.octane.TagData;
import com.impinj.octane.TagOp;
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
import java.util.ArrayList;
import static marginafterdecomplie.OperationCompleteHandler.EPC_OP_ID;

/**
 *
 * @author huabing li
 */
public class WriteChip implements  TagOpCompleteListener {
      public static int idCounter = 1;
    
       static int outstanding = 0;
     void programEpc(String currentEpc, short currentPC, String newEpc,ImpinjReader reader,int outstanding)
            throws Exception {
         
        if ((currentEpc.length() % 4 != 0) || (newEpc.length() % 4 != 0)) {
            throw new Exception("EPCs must be a multiple of 16- bits: "
                    + currentEpc + "  " + newEpc);
        }

        if (outstanding > 1) {
            return;
        }

        System.out.println("Programming Tag ");
        System.out.println("   EPC " + currentEpc + " to " + newEpc);
        TagOpSequence seq = new TagOpSequence();
        seq.setOps(new ArrayList<TagOp>());
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
         
         reader.setTagOpCompleteListener(new WriteChip());
        
       
    }

   

    @Override
    public void onTagOpComplete(ImpinjReader reader, TagOpReport results) {       
          System.out.println("TagOpComplete-epcwrite: ");
        for (TagOpResult t : results.getResults()) {
            System.out.print("  EPC: " + t.getTag().getEpc().toHexString());
            if (t instanceof TagWriteOpResult) {
                TagWriteOpResult tr = (TagWriteOpResult) t;

                if (tr.getOpId() == EPC_OP_ID) {
                    System.out.print("  Write to EPC Complete: ");
                }
                System.out.println(" result: " + tr.getResult().toString()
                        + " words_written: " + tr.getNumWordsWritten());
                outstanding--;
            }
        }
    
    
    
    }
    
    
    
    
    
}

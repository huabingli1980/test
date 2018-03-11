package marginafterdecomplie;

import com.impinj.octane.*;

import java.util.ArrayList;
import java.util.Scanner;


public class LockEpcMemory {
    
    public static void UnLockMemory(String accessPW,String targetTid,ImpinjReader reader) throws OctaneSdkException{
         try {  
             TagOpSequence seq = new TagOpSequence();
              seq.setTargetTag(new TargetTag());
              //  seq.getTargetTag().setBitPointer((short)0);
              //  seq.getTargetTag().setMemoryBank(MemoryBank.Tid);
              
               seq.getTargetTag().setBitPointer((short)32);
               seq.getTargetTag().setMemoryBank(MemoryBank.Epc);
                seq.getTargetTag().setData(targetTid);
           // seq.setOps(new ArrayList<>());
            // only execute this one time since it will fail the second time due
            // to password lock
            seq.setExecutionCount((short) 1);
            seq.setState(SequenceState.Active);
            seq.setId(12);
            // write a new access password
              TagWriteOp writeOp = new TagWriteOp();
            writeOp.setMemoryBank(MemoryBank.Reserved);
            // access password starts at an offset into reserved memory
            writeOp.setAccessPassword(TagData.fromHexString(accessPW));
            writeOp.setWordPointer(WordPointers.AccessPassword);          
           writeOp.setData(TagData.fromHexString("00000000"));
            
               TagLockOp lockOp = new TagLockOp(); 
               System.out.println("accesspassword:"+accessPW);
         //lockOp.setKillPasswordLockType(TagLockState.Unlock);
                lockOp.Id=12345;
                lockOp.setAccessPasswordLockType(TagLockState.Unlock);
               // lockOp.setAccessPassword(TagData.fromHexString(accessPW));
                lockOp.setAccessPassword(TagData.fromHexString("00000000"));
                //lockOp.setEpcLockType(TagLockState.Unlock);
               // lockOp.setTidLockType(TagLockState.Unlock);
               // lockOp.setUserLockType(TagLockState.Unlock);
           
            // add to the list
           // seq.getOps().add(writeOp);           
            System.out.println("Target TID data:"+seq.getTargetTag().getData());
            seq.getOps().add(writeOp);
              seq.getOps().add(lockOp);
              reader.addOpSequence(seq);        
            //System.out.println("Done");
          System.out.println("Done");
        } catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
        
        
        
    }
    
    

    public static void LockEpcMemory(String targetEpc,ImpinjReader reader) {
        try {           
            // create the reader op sequence
            TagOpSequence seq = new TagOpSequence();
            seq.setOps(new ArrayList<>());
            // only execute this one time since it will fail the second time due
            // to password lock
            seq.setExecutionCount((short) 1);
            seq.setState(SequenceState.Active);
            seq.setId(12);
            // write a new access password
            TagWriteOp writeOp = new TagWriteOp();
            writeOp.setMemoryBank(MemoryBank.Reserved);
            // access password starts at an offset into reserved memory
            //writeOp.setAccessPassword(TagData.fromHexString("abcd0123"));
           // writeOp.setWordPointer(WordPointers.AccessPassword);
           // writeOp.setData(TagData.fromHexString("abcd0123"));
            // no access password at this point
            TagLockOp lockOp = new TagLockOp();
            // lock the access password so it can't be changed
            // since we have a password set, we have to use it
         //  lockOp.setAccessPassword(TagData.fromHexString("00000000"));
            //lockOp.setEpcLockType(TagLockState.Permalock);   
             lockOp.setKillPasswordLockType (TagLockState.Permalock);
                lockOp.setAccessPasswordLockType(TagLockState.Permalock);
                lockOp.setEpcLockType(TagLockState.Permalock);
                lockOp.setTidLockType(TagLockState.Permalock);
                lockOp.setUserLockType(TagLockState.Permalock);
           
            // add to the list
             seq.getOps().add(lockOp);
            if (targetEpc != null) {
                seq.setTargetTag(new TargetTag());
                seq.getTargetTag().setBitPointer(BitPointers.Epc);
                seq.getTargetTag().setMemoryBank(MemoryBank.Epc);
                seq.getTargetTag().setData(targetEpc);
            } else {
                // or just send NULL to apply to all tags
                seq.setTargetTag(null);
            }
            System.out.println("Target EPC data:"+seq.getTargetTag().getData());
//  seq.getOps().add(writeOp);
           
            // add to the reader. The reader supports multiple sequences
            reader.addOpSequence(seq);

            // set up listeners to hear stuff back from SDK
            // reader.setTagReportListener(new
            // TagReportListenerImplementation());
           // reader.setTagOpCompleteListener(new TagOpCompleteListenerImplementation());
                    

            // Start the reader
           // reader.start();
            System.out.println("Done");
        } catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ruiz.utils;

import com.ruiz.model.entity.ChipInfo;
import static com.ruiz.utils.ContextManager.tagsProcessed;
  import java.util.Date;
import java.util.Iterator;
  import java.util.Timer;
  import java.util.TimerTask;
//import static marginafterdecomplie.OperationCompleteHandler.TidEpcMatchID;
//import static marginafterdecomplie.Repository.epcTagInfoMap;
public class TimerDelNonSuccess {
     public TimerDelNonSuccess() {
         Timer timer = new Timer();
         Task task = new Task();
         timer.schedule(task, new Date(), 300);
     }
 }
 
 class Task extends TimerTask{
 
     @Override
     public void run() {
   
        Iterator entries = tagsProcessed.iterator();  
while (entries.hasNext()) {     
   ChipInfo entry=  (ChipInfo) entries.next();  
  if ((!"TidHeadMissMatch".equals(entry.getProgstatus())&&!"Success".equals(entry.getProgstatus())&&!"TagMemoryLockedError".equals(entry.getProgstatus()))||entry.getProgstatus()==null )
  {
   entries.remove();
   
   System.out.println(entry.getProgstatus()+"Timer Removing....:"+entry.getTid());      
         }
     }
     }
 }
 
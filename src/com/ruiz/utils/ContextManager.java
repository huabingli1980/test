package com.ruiz.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import com.ruiz.model.entity.ChipInfo;
public class ContextManager {	
	public static ArrayList<String> tidList = new ArrayList<String>();
	public static Set<ChipInfo> tagsProcessed = new HashSet<ChipInfo>();	
	public static void addChipInfo(ChipInfo chipInfo){
		tagsProcessed.add(chipInfo);
               
	}	
	public static boolean isProcessed(ChipInfo chipInfo){
		return tagsProcessed.contains(chipInfo);
                //                                      tagsProcessed.
	}	
	private static String newEpc;	
	public static String getNewEpc() {
		return newEpc;
	}

	public static void setNewEpc(String newEpc) {
		ContextManager.newEpc = newEpc;
	}

	public static void addTid(String tid){
		tidList.add(tid);
	}
	
	public static int indexOfTidList(String tid){
		return tidList.indexOf(tid);
	}

}

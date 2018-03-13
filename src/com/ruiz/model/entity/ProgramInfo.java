package com.ruiz.model.entity;

public class ProgramInfo {
	
	    private String epc;
		public String getEpc() {
			return epc;
		}
		public void setEpc(String epc) {
			this.epc = epc;
		}
		private String userM;
    	private String accessPW;
    	private String accessPWLock;
    	private String epcLock;
    	private String userLock;
    	private String killPWlock;
    	
    	
		public String getAccessPW() {
			return accessPW;
		}
		public void setAccessPW(String accessPW) {
			this.accessPW = accessPW;
		}
		public String getUserM() {
			return userM;
		}
		public void setUserM(String userM) {
			this.userM = userM;
		}
		public String getAccessPWLock() {
			return accessPWLock;
		}
		public void setAccessPWLock(String accessPWLock) {
			this.accessPWLock = accessPWLock;
		}
		public String getEpcLock() {
			return epcLock;
		}
		public void setEpcLock(String epcLock) {
			this.epcLock = epcLock;
		}
		public String getUserLock() {
			return userLock;
		}
		public void setUserLock(String userLock) {
			this.userLock = userLock;
		}
		public String getKillPWlock() {
			return killPWlock;
		}
		public void setKillPWlock(String killPWlock) {
			this.killPWlock = killPWlock;
		}
    }
package com.ruiz.model.entity;

/**
 * 
 * @author 0114804
 *
 */
public class ChipInfo {
	
		private String epc;
		private String tid;
                
                   private String Progstatus;        
        	public String getProgstatus() {
			return Progstatus;
		}
		public void setProgstatus(String status) {
			this.Progstatus = status;
		} 
		
		public String getEpc() {
			return epc;
		}
		public void setEpc(String epc) {
			this.epc = epc;
		}
		
		public String getTid() {
			return tid;
		}
		public void setTid(String tid) {
			this.tid = tid;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			//result = prime * result + ((epc == null) ? 0 : epc.hashCode());
			result = prime * result + ((tid == null) ? 0 : tid.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
		ChipInfo other1 = (ChipInfo) obj;
			return tid.equals(other1.tid);
		}

	}
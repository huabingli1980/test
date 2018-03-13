package com.ruiz.model.entity;

/**
 * 
 * @author 0114804
 *
 */
public class ChipInfo {
	
		private String epc;
		private String tid;
		
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
			result = prime * result + ((epc == null) ? 0 : epc.hashCode());
			result = prime * result + ((tid == null) ? 0 : tid.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ChipInfo other = (ChipInfo) obj;
			if (epc == null) {
				if (other.epc != null)
					return false;
			} else if (!epc.equals(other.epc))
				return false;
			if (tid == null) {
				if (other.tid != null)
					return false;
			} else if (!tid.equals(other.tid))
				return false;
			return true;
		}

	}
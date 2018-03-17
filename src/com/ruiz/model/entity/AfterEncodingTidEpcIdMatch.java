package com.ruiz.model.entity;

/**
 * 
 * @author 0114804
 *
 */
public final class AfterEncodingTidEpcIdMatch{	
		private String Encodedepc;                
		private String tid;
                private int ID;
                private ChipInfo chipinfo;
 
		public AfterEncodingTidEpcIdMatch(String TID,String ToEncodeEPC,ChipInfo Chipinfo,int id)
                {
                    setTid(TID);                  
                    setEpc(ToEncodeEPC);
                    setid(id);
                    setchipinfo(Chipinfo);
                }
                
                
                  	public ChipInfo getchipinfo() {
			return chipinfo;
		}
		public void setchipinfo(ChipInfo Chipinfo) {
			this.chipinfo = Chipinfo;
		}
                
          
		public String getEpc() {
			return Encodedepc;
		}
		public void setEpc(String epc) {
			this.Encodedepc = epc;
		}
		
		public String getTid() {
			return tid;
		}
		public void setTid(String tid) {
			this.tid = tid;
		}
               public int getid() {
			return ID;
		}
		public void setid(int id) {
			this.ID = id;
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
		AfterEncodingTidEpcIdMatch other1 = (AfterEncodingTidEpcIdMatch) obj;
			return tid.equals(other1.tid);
		}

	}
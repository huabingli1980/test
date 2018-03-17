package marginafterdecomplie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.ruiz.model.entity.ProgramInfo;

public final class Repository {
    //store datasource info
	public static Map<Integer, ProgramInfo> epcTagInfoMap = new HashMap<>();
	public Repository() {
		loadProgramInfos();
	}

	public ProgramInfo getProgramInfoByID(int ID) {
		return epcTagInfoMap.get(ID);
	}

	public void loadProgramInfos() {

		String url = "jdbc:sqlite:" + MainRun.filePath + "/fromCsv.db";
		String sql1 = "select * from warehouses where EncodeStatus<>'Success'and EncodeStatus<>'used'";
		ResultSet rscsv = null;
		try {
			Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			rscsv = stmt.executeQuery(sql1);
                      int i=1;
			while (rscsv.next()) {                                
				String accessPW = rscsv.getString("ACCESSPASSWORD");
				String userM = rscsv.getString("USERMEMORY");
				String newEpc = rscsv.getString("EPC");
				String accessPWLock = rscsv.getString("ACCESSPASSWORDLOCKCODE");
				String epcLock = rscsv.getString("EPCLOCKCODE");
				String userLock = rscsv.getString("USERMEMORYLOCKCODE");
				String killPWlock = rscsv.getString("KILLPASSWORDLOCKCODE");
				ProgramInfo programInfo = new ProgramInfo();
                                programInfo.setId(i);
				programInfo.setAccessPW(accessPW);
				programInfo.setUserM(userM);
				programInfo.setEpc(newEpc);
				programInfo.setAccessPWLock(accessPWLock);
				programInfo.setEpcLock(epcLock);
				programInfo.setUserLock(userLock);
				programInfo.setKillPWlock(killPWlock);                               
				epcTagInfoMap.put(i, programInfo);
                                System.out.println(i);
                                 i++;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				rscsv.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}

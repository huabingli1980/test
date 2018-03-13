/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marginafterdecomplie;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import static marginafterdecomplie.MainRun.filePath;

/**
 *
 * @author sqlitetutorial.net
 */
public class Sqlite {
    /**
     * Connect to a sample database
     */
    public static String url = "";
    public static Connection conn;
    public static Statement stmt;
    void Sqlite() {
        // SQLite connection string
        url = "jdbc:sqlite:" + filePath + "/fromCsv.db";
       // String sql2 = "drop TABLE IF EXISTS warehouses";
        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("created a table");
    }

    public static void createNewDatabase(String fileName) {
        url = "jdbc:sqlite:" + filePath + fileName;
        //String url = "jdbc:sqlite:C:\\Development\\java impinj\\MarginAfterDecomplie\\" + fileName;
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int n = JOptionPane.showConfirmDialog(null,e.getMessage()+ ",go ahead?", "Error",JOptionPane.YES_NO_OPTION);//i=0/1  
        } finally {
            try {
                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(Sqlite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void createNewTable() {
        // SQLite connection string
         url = "jdbc:sqlite:" + filePath + "/tests.db";
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n"
                + "ENCODINGIDENTIFIER text,\n"
                + "LOOKUPKEY text NOT NULL,\n"
                + "SECONDARYIDENTIFIER,\n"
                + "EPC,\n"
                + "EPCLOCKCODE,\n"
                + "ACCESSPASSWORD,\n"
                + "ACCESSPASSWORDLOCKCODE,\n"
                + "KILLPASSWORD,\n"
                + "USERMEMORY,\n"
                + "USERMEMORYLOCKCODE,\n"
                + "IDMODE,\n"
                + "MASKBITLENGTH\n"
                + "EncodeStatus\n"
                + ");";

        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(Sqlite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("created a table");
    }

    public static void createNewTable(ResultSetMetaData data, String filePath, Boolean DeleteOrNot, String Status, String TableN) throws SQLException {
        // SQLite connection string
         url = "jdbc:sqlite:" + filePath + "/fromCsv.db";

        StringBuilder sql = new StringBuilder().append("CREATE TABLE IF NOT EXISTS ").append(TableN).append("(");
        int Ccount = data.getColumnCount();
        for (int a = 1; a <= Ccount; a++) {
            if (a >= Ccount) {
                sql.append("'").append(data.getColumnName(a)).append("'");
            } else {
                sql.append("'").append(data.getColumnName(a)).append("',");
            }
        }
        if (!"".equals(Status)) {
            sql.append(",").append(Status);
        }
        sql.append(")");
        String sql1 = sql.toString();

        String sql2 = "drop TABLE IF EXISTS warehouses";

        try 
        {
            conn = DriverManager.getConnection(url);
                stmt = conn.createStatement();
            // create a new table
            if (DeleteOrNot) {
                stmt.execute(sql2);
            }

            System.out.println(sql1);
            stmt.execute(sql1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(Sqlite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("created a table");
    }
public void RunSql(String sql)
{
         url = "jdbc:sqlite:" + filePath + "/fromCsv.db";
       // String sql2 = "drop TABLE IF EXISTS warehouses";
       
        try {
            conn = DriverManager.getConnection(url);            
           conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            //stmt.execute(sql);
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Sqlite.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             try {
                 stmt.close();
                 conn.close();
             } catch (SQLException ex) {
                 Logger.getLogger(Sqlite.class.getName()).log(Level.SEVERE, null, ex);
             }
            
        }
}
  
    public static void main(String[] args) {
        filePath = MyPath.getProjectPath();
        createNewDatabase("test.db");

    }
    
    public  static void insetToLogReport(String SourceTalble, String DentTable)
    {
        
    }

	public void markUsedByEPC(String newEpc) {
		String sql = "UPDATE warehouses SET EncodeStatus = 'used' WHERE EPC ='" + newEpc + "'";
		RunSql(sql);
	}

	public void markSuccessByEPC(String newEpc) {
		String sql = "UPDATE warehouses SET EncodeStatus = 'Success' WHERE EPC ='" + newEpc + "'";
		RunSql(sql);
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marginafterdecomplie;

/**
 *
 * @author Huabing Li
 */
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static marginafterdecomplie.MainRun.filePath;
//import static marginafterdecomplie.Sqlite.filePath;

public class JDBCcsvANDsqlite {

    public static void main(String[] args) {
        try {
            String fname = args[0];
            String[] path1 = fname.split("\\\\");
            String csvpathname = fname.replace(path1[path1.length - 1], "");
            String csvnfilename = path1[path1.length - 1];
            csvnfilename = csvnfilename.split("\\.")[0];
            // load the driver into memory        
            Class.forName("org.relique.jdbc.csv.CsvDriver");
            // String csvpathname=path2; //csv path    
            // create a connection. The first command line parameter is assumed to
            //  be the directory in which the .csv files are held
            Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + csvpathname);
            // create a Statement object to execute the query with
            Statement stmt = conn.createStatement();         
            System.out.println("SELECT * FROM '" + csvnfilename + "'");
            ResultSet results = stmt.executeQuery("SELECT * FROM " + csvnfilename);
            System.out.println(results.getStatement());
            insertTable(results, "");
            results.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Oops-> " + e);
        }
    }

    public static void insertTable(ResultSet results, String tableN) throws SQLException {
        // SQLite connection string
        String url = "jdbc:sqlite:" + filePath + "\\fromCsv.db";
        // SQL statement for creating a new table
        Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        Sqlite.createNewTable(results.getMetaData(), filePath, true, "EncodeStatus", "warehouses");
        try {
            conn.setAutoCommit(false);
            while (results.next()) {
                String t1 = results.getString(2);
                String t2 = results.getString(3);
                String sql = "insert into warehouses values('" + results.getString(1) + "'"
                        + ",'" + t1 + "'"
                        + ",'" + t2 + "'"
                        + ",'" + results.getString(4) + "'"
                        + ",'" + results.getString(5) + "'"
                        + ",'" + results.getString(6) + "'"
                        + ",'" + results.getString(7) + "'"
                        + ",'" + results.getString(8) + "'"
                        + ",'" + results.getString(9) + "'"
                        + ",'" + results.getString(10) + "'"
                        + ",'" + results.getString(11) + "'"
                        + ",'" + results.getString(12) + "'"
                        + ",'" + results.getString(13) + "'"
                        + ",'" + results.getString(14) + "'"
                        + ",'" + "" + "'"
                        + ")";
                // stmt.addBatch(sql);
                // System.out.println(sql);
                stmt.execute(sql);
                conn.commit();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            stmt.close();
            conn.close();
        }

        System.out.println("inserted");
    }

}

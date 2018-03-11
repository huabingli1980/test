/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marginafterdecomplie;
import com.impinj.octane.OctaneSdkException;
import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import static marginafterdecomplie.ConnectOp1.reader;
import static marginafterdecomplie.MainRun.filePath;
import org.dom4j.DocumentException;

/**
 *
 * @author Huabing Li
 */
public class CodingForm extends javax.swing.JFrame {
    public PreparedStatement ps=null;
    Point p; //保存对话盒上次弹出的位置
    Dimension d; //保持对话盒上次的大小
    public java.util.HashSet Myhashdata;
    public BufferedReader br;
    private ResultSet rs = null;
    public String ReportN;
   

    /**
     * Creates new form MarginTest
     */
    public CodingForm() {
        initComponents();
   
        Codehex.setText("AAAAAAAAAAAAAAAAAAAAAAAA");
        ListT.getColumnModel().getColumn(0).setPreferredWidth(60);
        ListT.getColumnModel().getColumn(0).setMaxWidth(60);
        // ListT.getColumnModel().getColumn(0).setHeaderValue("suuuu");
        ListT.getColumnModel().getColumn(1).setPreferredWidth(120);
        ListT.getColumnModel().getColumn(2).setPreferredWidth(120);
        ListT.getColumnModel().getColumn(3).setPreferredWidth(120);
        filePath = MyPath.getProjectPath();
       /* try {
            br = cin_txt.getBr();
            //URL url = this.class.getProtectionDomain().getCodeSource().getLocation();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CodingForm.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        xmlF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    SKU.removeAllItems();
                    PONum.removeAllItems();
                    if (xmlORcsv.getSelectedItem() == "xml") {
                         XMLParse.XMLread(xmlF.getText(),PONum,SKU);
                    } else if (xmlORcsv.getSelectedItem() == "csv") {
                       
                        String[] args = new String[]{xmlF.getText()};
                        JDBCcsvANDsqlite.main(args);
                        try {
                            String fname=xmlF.getText();
                              String[] path1=fname.split("\\\\");
       // String csvpathname=fname.replace(path1[path1.length-1],"");
      String  csvnfilename=path1[path1.length-1];
      ReportN=csvnfilename.split("\\.")[0];
                            createlogTableFromCSV();
                        } catch (SQLException ex) {
                            Logger.getLogger(CodingForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        getinfoFROMcsvdb();
                        getSkuDetailsFromCSVdb(SKU.getSelectedItem().toString());
                       
                    }

                } catch (DocumentException | SQLException ex) {
                    Logger.getLogger(CodingForm.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            void getinfoFROMcsvdb() {
                String url = "jdbc:sqlite:" + filePath + "/fromCsv.db";
             String sql1 = "select distinct LOOKUPKEY from warehouses";
              //  String sql1 = "select LOOKUPKEY from warehouses";
                try (Connection conn = DriverManager.getConnection(url);
                  Statement stmt = conn.createStatement()) {                  
                   // stmt.execute(sql1);                    
                    rs = stmt.executeQuery(sql1);  
                   // rs.absolute(1);
                    while (rs.next()) {
                     SKU.addItem(rs.getString(1));
                    }

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Got skudata from Fromcsv");
            }
            
                        void getSkuDetailsFromCSVdb(String lookupkey) throws SQLException {
                String url = "jdbc:sqlite:" + filePath + "/fromCsv.db";
                String sql1 = "select count(LOOKUPKEY) as count from warehouses where LOOKUPKEY='"+lookupkey +"'";
               
                Connection conn = DriverManager.getConnection(url);
                try 
                {
                    
                        Statement stmt = conn.createStatement();                         
                      rs = stmt.executeQuery(sql1);                      
                     int rowCount = rs.getInt(1);//得到当前行号，也就是记录数
                    TagQty.setText(rowCount+"");

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }finally{
                    conn.close();
                   // conn= null;
                }
                System.out.println("Got data from Fromcsv");
            }
                        
             
                        

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        Total = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListT = new javax.swing.JTable();
        RorW = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        Codehex = new javax.swing.JTextField();
        GetXml = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        xmlF = new javax.swing.JTextArea();
        PONum = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        GetOrderL = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        SKU = new javax.swing.JComboBox<>();
        xmlORcsv = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        TagQty = new javax.swing.JTextField();
        Encoded = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton2.setText("Start");
        jButton2.setName("jButton3"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Clear");
        jButton3.setName("ClearL"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField2.setName("Good"); // NOI18N

        jTextField3.setName("Bad"); // NOI18N

        Total.setName("Total"); // NOI18N

        ListT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "seq", "Tid", "OriginalEPC", "NewEpc", "Result", "MarginResult", "time", "ProcessTime"
            }
        ));
        jScrollPane1.setViewportView(ListT);

        RorW.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Read", "WriteEpc", "WriteTid", "LockOnly" }));
        RorW.setName("TIDEPC"); // NOI18N
        RorW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RorWActionPerformed(evt);
            }
        });

        jLabel3.setText("Operation Type:");

        Codehex.setName("Good"); // NOI18N

        GetXml.setText("...");
        GetXml.setName("ClearL"); // NOI18N
        GetXml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GetXmlActionPerformed(evt);
            }
        });

        xmlF.setColumns(20);
        xmlF.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        xmlF.setLineWrap(true);
        xmlF.setRows(5);
        xmlF.setWrapStyleWord(true);
        jScrollPane2.setViewportView(xmlF);

        PONum.setName("TIDEPC"); // NOI18N
        PONum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PONumActionPerformed(evt);
            }
        });

        jLabel5.setText("PONumber:");

        GetOrderL.setText("Get SKU");
        GetOrderL.setName("ClearL"); // NOI18N
        GetOrderL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GetOrderLActionPerformed(evt);
            }
        });

        jLabel6.setText("SKU:");

        SKU.setName("TIDEPC"); // NOI18N
        SKU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SKUActionPerformed(evt);
            }
        });

        xmlORcsv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "xml", "csv" }));
        xmlORcsv.setName("TIDEPC"); // NOI18N
        xmlORcsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xmlORcsvActionPerformed(evt);
            }
        });

        jLabel7.setText("TagQty:");

        TagQty.setName("Total1"); // NOI18N

        Encoded.setName("Total1"); // NOI18N

        jLabel8.setText("Encoded:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(28, 28, 28))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(RorW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(23, 23, 23)
                                        .addComponent(jButton3))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(Codehex, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Total, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                                .addComponent(xmlORcsv, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(PONum, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(GetOrderL)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(GetXml, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2))
                                .addGap(27, 27, 27))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(SKU, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TagQty, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Encoded, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(xmlORcsv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel5)
                        .addGap(9, 9, 9)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(RorW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                    .addComponent(jButton3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Codehex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(GetXml, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PONum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GetOrderL)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(Encoded, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(SKU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7)
                                .addComponent(TagQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        UterqueRead  Ur = new UterqueRead ();        
        Ur.Uread(ConnectOp1.reader);
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Myhashdata = MainRun.hashdata;
        javax.swing.table.DefaultTableModel tableModel = (javax.swing.table.DefaultTableModel) ListT.getModel();
        tableModel.setRowCount(0);
        //MainRun.hashdata.clear();
        try {
            ConnectOp1.reader.stop();
        } catch (OctaneSdkException ex) {
            Logger.getLogger(CodingForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ConnectOp1.reader.stop();
        } catch (com.impinj.octane.OctaneSdkException ex) {
            java.util.logging.Logger.getLogger(MarginTest1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Total.setText("0");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void RorWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RorWActionPerformed
        /*
        TableColumnModel columnModel = ListT.getColumnModel();
        Enumeration<TableColumn> tableColumns = columnModel.getColumns();
        if (RorW.getSelectedItem().toString() == "Read") {

            for (Enumeration t = tableColumns; t.hasMoreElements();) {

                TableColumn a = (TableColumn) t.nextElement();
                if ("Original EPC".equals(a.getHeaderValue().toString()) || "Original TID".equals(a.getHeaderValue().toString())) {
                    columnModel.removeColumn(a);
                }

            }

        } else if (RorW.getSelectedItem().toString() == "WriteEpc") {
            for (Enumeration t = tableColumns; t.hasMoreElements();) {

                TableColumn a = (TableColumn) t.nextElement();
                if ("Original EPC".equals(a.getHeaderValue().toString()) || "Original TID".equals(a.getHeaderValue().toString())) {
                    columnModel.removeColumn(a);
                }

            }
            TableColumn aColumn = new TableColumn(6, 120);
            aColumn.setHeaderValue("Original EPC");
            ListT.getColumnModel().addColumn(aColumn);
            ListT.getColumnModel().moveColumn(6, 2);
        } else if (RorW.getSelectedItem().toString() == "WriteTid") {
            for (Enumeration t = tableColumns; t.hasMoreElements();) {

                TableColumn a = (TableColumn) t.nextElement();
                if ("Original EPC".equals(a.getHeaderValue().toString()) || "Original TID".equals(a.getHeaderValue().toString())) {
                    columnModel.removeColumn(a);
                }

            }
            TableColumn aColumn = new TableColumn(6, 120);
            aColumn.setHeaderValue("Original TID");
            ListT.getColumnModel().addColumn(aColumn);
            ListT.getColumnModel().moveColumn(6, 1);
        }
         */
    }//GEN-LAST:event_RorWActionPerformed

    private void GetXmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GetXmlActionPerformed
        //super.mouseClicked(arg0);            
        // KFileChooser chooser = new KFileChooser();
        KFileChooser chooser = new KFileChooser();
        //设置对话盒的位置和大小     
        if (p == null) {
            p = new Point();

        }
        if (d == null) {
            d = new Dimension();
            d.width = 1200;
            d.height = 500;

        }

        //保存对话盒的位置和大小
        //  System.out.println("the selected file is " + chooser.getSelectedFile());        
        String Folder = "C:\\";
        Preferences pref = Preferences.userRoot().node(
                this.getClass().getName());
        String lastPath = pref.get("lastPath", "");
        // p= pref.get(p, p);
        // JFileChooser fc = null;
        if (!lastPath.equals("")) {
            //  chooser = new KFileChooser(lastPath);
            chooser.setCurrentDirectory(new File(lastPath));
        } else {
            //  chooser = new JFileChooser(Folder);
            chooser.setCurrentDirectory(new File(Folder));
        }

        if (xmlORcsv.getSelectedItem() == "xml") {
            chooser.setDialogTitle("请选择xml文件...");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("xml文件(*.xml)", "xml");
            chooser.setFileFilter(filter);
        } else if (xmlORcsv.getSelectedItem() == "csv") {
            chooser.setDialogTitle("请选择csv文件...");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("csv文件(*.csv)", "csv");
            chooser.setFileFilter(filter);
        }

        int result = chooser.showOpenDialog(this, p, d);
        chooser.getLastLocationAndSize(p, d);
        Folder = chooser.getSelectedFile().getPath();
        // Folder = chooser.
        String filePath = chooser.getSelectedFile().getAbsolutePath();
         String[] path1=filePath.split("\\\\");
        String csvpathname=filePath.replace(path1[path1.length-1],"");
      String  csvnfilename=path1[path1.length-1];
      csvnfilename=csvnfilename.split("\\.")[0];
        
       String Nname=csvnfilename; 
if (csvnfilename.contains(" ")){
         Nname=csvnfilename.replace(" ","");
         MainRun.testRenameFile(filePath, csvpathname+Nname+".csv");
         xmlF.setText(csvpathname+Nname+".csv");
}else xmlF.setText(filePath);
        pref.put("lastPath", Folder);
       // xmlF.setText(filePath);
    }//GEN-LAST:event_GetXmlActionPerformed

    private void PONumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PONumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PONumActionPerformed

    private void GetOrderLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GetOrderLActionPerformed
        // XPathDemo xmlp= new XPathDemo();
        try {
             XMLParse.XMLread(xmlF.getText(),PONum,SKU);
        } catch (Exception ex) {
            Logger.getLogger(CodingForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_GetOrderLActionPerformed

    private void SKUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SKUActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SKUActionPerformed

    private void xmlORcsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xmlORcsvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_xmlORcsvActionPerformed
  void createlogTableFromCSV() throws SQLException{
           String fname=filePath+"\\KRReport_Sample.csv";
        String[] path1=fname.split("\\\\");
        String csvpathname=fname.replace(path1[path1.length-1],"");
      String  csvnfilename=path1[path1.length-1];
      csvnfilename=csvnfilename.split("\\.")[0];
        try {
            // load the driver into memory
            Class.forName("org.relique.jdbc.csv.CsvDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CodingForm.class.getName()).log(Level.SEVERE, null, ex);
        }

      Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + csvpathname );
      // create a Statement object to execute the query with
      Statement stmt = conn.createStatement();
      // Select the ID and NAME columns from sample.csv
      ResultSet results = stmt.executeQuery("SELECT * FROM "+csvnfilename);
      Sqlite.createNewTable(results.getMetaData(), filePath, false,"","'Report_"+ReportN+"'");
  }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CodingForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField Codehex;
    public javax.swing.JTextField Encoded;
    private javax.swing.JButton GetOrderL;
    public javax.swing.JButton GetXml;
    public javax.swing.JTable ListT;
    public javax.swing.JComboBox<String> PONum;
    public javax.swing.JComboBox<String> RorW;
    public javax.swing.JComboBox<String> SKU;
    public javax.swing.JTextField TagQty;
    public javax.swing.JTextField Total;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextField jTextField2;
    public javax.swing.JTextField jTextField3;
    public javax.swing.JTextArea xmlF;
    public javax.swing.JComboBox<String> xmlORcsv;
    // End of variables declaration//GEN-END:variables
class EPCfromCsv{
    private String EPC;
    private String getepc(){return EPC;}
    private void setepc(String value1)
    {
        this.EPC=value1;
    }
    
    @Override
        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + (EPC == null ? 0 : EPC.hashCode());
            return result;

        }

        @Override
        public boolean equals(Object obj) {
           EPCfromCsv other = (EPCfromCsv) obj;
            return EPC.equals(other.EPC);
            // return other.equals(this);
        }

        private Object getOuterType() {
          return CodingForm.this;
        }
    
}



}

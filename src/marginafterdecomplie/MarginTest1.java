package marginafterdecomplie;



import javax.swing.UIManager;

public class MarginTest1 extends javax.swing.JFrame
{
  public java.util.HashSet Myhashdata;
  public javax.swing.JTextField Bad;
  public javax.swing.JButton ClearL;
  public javax.swing.JTextField Good;
  public javax.swing.JTable ListT;
  public javax.swing.JTextField My_ip;
  public javax.swing.JTextField Total1;
  public javax.swing.JButton jButton1;
  private javax.swing.JButton jButton3;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane2;
  
  public MarginTest1()
  {
    initComponents();
  }
  






  private void initComponents()
  {
    jPanel1 = new javax.swing.JPanel();
    jButton1 = new javax.swing.JButton();
    My_ip = new javax.swing.JTextField();
    jLabel2 = new javax.swing.JLabel();
    jLabel1 = new javax.swing.JLabel();
    jButton3 = new javax.swing.JButton();
    ClearL = new javax.swing.JButton();
    jScrollPane2 = new javax.swing.JScrollPane();
    ListT = new javax.swing.JTable();
    Good = new javax.swing.JTextField();
    Bad = new javax.swing.JTextField();
    Total1 = new javax.swing.JTextField();
    
    setDefaultCloseOperation(3);
    
    jButton1.setLabel("Connect");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        MarginTest1.this.jButton1ActionPerformed(evt);
      }
      
    });
    My_ip.setText("169.254.1.2");
    My_ip.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        MarginTest1.this.My_ipActionPerformed(evt);
      }
      
    });
    jLabel2.setText("IP");    
    jLabel1.setText("EPC+TID");    
    jButton3.setText("Start");
    jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        MarginTest1.this.jButton3MouseClicked(evt);
      }
    });
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        MarginTest1.this.jButton3ActionPerformed(evt);
      }
      
    });
    ClearL.setText("Clear");
    ClearL.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        MarginTest1.this.ClearLActionPerformed(evt);
      }
      
    });
    ListT.setModel(new javax.swing.table.DefaultTableModel(new Object[0][], new String[] { "seq", "Tid", "EPC", "Result", "MarginResult", "Time" }));
    






    ListT.setAutoscrolls(isShowing());
    jScrollPane2.setViewportView(ListT);
    
    Good.setFont(new java.awt.Font("Tahoma", 0, 18));
    Good.setText("0");
    Good.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        MarginTest1.this.GoodActionPerformed(evt);
      }
      
    });
    Bad.setFont(new java.awt.Font("Tahoma", 0, 18));
    Bad.setText("0");
    Bad.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        MarginTest1.this.BadActionPerformed(evt);
      }
      
    });
    Total1.setFont(new java.awt.Font("Tahoma", 0, 18));
    Total1.setText("0");
    Total1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        MarginTest1.this.Total1ActionPerformed(evt);
      }
      
    });
    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(jPanel1Layout
      .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
      .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
      .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
      .addGroup(jPanel1Layout.createSequentialGroup()
      .addContainerGap()
      .addComponent(jLabel1)
      .addGap(169, 169, 169))
      .addGroup(jPanel1Layout.createSequentialGroup()
      .addGap(19, 19, 19)
      .addComponent(jLabel2)
      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
      .addComponent(My_ip, -2, 170, -2)
      .addGap(18, 18, 18)))
      .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
      .addComponent(jButton3)
      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
      .addComponent(ClearL))
      .addComponent(jButton1))
      .addGap(105, 105, 105)
      .addComponent(Good, -2, 72, -2)
      .addGap(37, 37, 37)
      .addComponent(Bad, -2, 72, -2)
      .addGap(30, 30, 30)
      .addComponent(Total1, -2, 72, -2))
      .addComponent(jScrollPane2, -2, 825, -2))
      .addContainerGap(-1, 32767)));
    
    jPanel1Layout.setVerticalGroup(jPanel1Layout
      .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
      .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
      .addContainerGap()
      .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
      .addComponent(My_ip, -2, -1, -2)
      .addComponent(jLabel2)
      .addComponent(jButton1))
      .addGap(8, 8, 8)
      .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
      .addComponent(jLabel1)
      .addComponent(jButton3)
      .addComponent(ClearL))
      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, 32767))
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
      .addContainerGap(-1, 32767)
      .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
      .addComponent(Good, -2, 33, -2)
      .addComponent(Bad, -2, 33, -2)
      .addComponent(Total1, -2, 33, -2))
      .addGap(18, 18, 18)))
      .addComponent(jScrollPane2, -2, 303, -2)
      .addGap(22, 22, 22)));
    

    My_ip.getAccessibleContext().setAccessibleName("My_ip");
    
    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout
      .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
      .addContainerGap()
      .addComponent(jPanel1, -2, -1, -2)
      .addContainerGap(-1, 32767)));
    
    layout.setVerticalGroup(layout
      .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
      .addContainerGap()
      .addComponent(jPanel1, -1, -1, -2)));
    

    pack();
  }
  
  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    ConnectOp1 Conn = new ConnectOp1();
    
    Conn.ConnectHost(My_ip.getText(),My_ip);
   
  }
  
  private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)
  {
    MarginReadTags1 Mr = new MarginReadTags1();
    
    Mr.Mread(ConnectOp1.reader);
  }
  


  private void My_ipActionPerformed(java.awt.event.ActionEvent evt) {}
  

  private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {}
  

  private void ClearLActionPerformed(java.awt.event.ActionEvent evt)
  {
   // Myhashdata = MainRun.hashdata;
    javax.swing.table.DefaultTableModel tableModel = (javax.swing.table.DefaultTableModel)ListT.getModel();
    tableModel.setRowCount(0);
    Myhashdata.clear();
    try {
      ConnectOp1.reader.stop();
    } catch (com.impinj.octane.OctaneSdkException ex) {
      java.util.logging.Logger.getLogger(MarginTest1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    Total1.setText("0");
  }
  




  private void GoodActionPerformed(java.awt.event.ActionEvent evt) {}
  




  private void BadActionPerformed(java.awt.event.ActionEvent evt) {}
  




  private void Total1ActionPerformed(java.awt.event.ActionEvent evt) {}
  



  public static void main(String[] args)
  {
    try
    {
      for (javax.swing.UIManager.LookAndFeelInfo info :UIManager.getInstalledLookAndFeels() ) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(MarginTest1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(MarginTest1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(MarginTest1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(MarginTest1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    


    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new MarginTest1().setVisible(true);
      }
    });
  }
}

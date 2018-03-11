package marginafterdecomplie;

import com.impinj.octane.ImpinjReader;

public class UterqueRead {
  public java.util.HashSet Myhashdata;
  
  //public MarginReadTags1() {}
  
  public void Uread(ImpinjReader MyReader) {
    try {
      Boolean Conned = MyReader.isConnected();      
      if (!Conned) {
        javax.swing.JOptionPane.showMessageDialog(null, "Out of connection!");
        throw new Exception("Must specify the '' property");
      }
         com.impinj.octane.Settings settings = MyReader.queryDefaultSettings();
      settings.setSearchMode(com.impinj.octane.SearchMode.DualTarget);
      settings.setSession(1);
      com.impinj.octane.ReportConfig report = settings.getReport();
      report.setIncludeFastId(true);
      report.setIncludeAntennaPortNumber(true);
      report.setIncludePcBits(true);
      report.setMode(com.impinj.octane.ReportMode.Individual);      
      settings.setReaderMode(com.impinj.octane.ReaderMode.AutoSetDenseReader);   
      com.impinj.octane.AntennaConfigGroup antennas = settings.getAntennas();
      antennas.disableAll();
      antennas.enableById(new short[] { 1 });
      antennas.getAntenna((short)1).setIsMaxRxSensitivity(false);
      antennas.getAntenna((short)1).setIsMaxTxPower(false);
      antennas.getAntenna((short)1).setTxPowerinDbm(30.0D);
      antennas.getAntenna((short)1).setRxSensitivityinDbm(-70.0D);
      MyReader.setTagReportListener(new UterqueEncodingAfterReport());
      System.out.println("Applying Settings");
      MyReader.applySettings(settings);      
      System.out.println("Starting");
      //Myhashdata = MainRun.hashdata;
     // Myhashdata.clear();
      MyReader.stop();
      MyReader.start();
    }
    catch (com.impinj.octane.OctaneSdkException ex) {
      System.out.println(ex.getMessage());
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      ex.printStackTrace(System.out);
    }
  }
}

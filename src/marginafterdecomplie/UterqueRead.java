package marginafterdecomplie;

import javax.swing.JOptionPane;

import com.impinj.octane.AntennaConfigGroup;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.ReaderMode;
import com.impinj.octane.ReportConfig;
import com.impinj.octane.ReportMode;
import com.impinj.octane.SearchMode;
import com.impinj.octane.Settings;
import com.ruiz.utils.TimerDelNonSuccess;

public class UterqueRead {

	public void Uread(ImpinjReader reader) {
		try {
			Boolean isConnected = reader.isConnected();
			if (!isConnected) {
				JOptionPane.showMessageDialog(null, "Out of connection!");
				throw new Exception("Must specify the '' property");
			}
			
			Settings settings = reader.queryDefaultSettings();
			settings.setSearchMode(SearchMode.DualTarget);
			settings.setSession(1);
			settings.setReaderMode(ReaderMode.AutoSetDenseReader);
			
			ReportConfig report = settings.getReport();
			report.setIncludeFastId(true);
			report.setIncludeAntennaPortNumber(true);
			report.setIncludePcBits(true);
			report.setMode(ReportMode.Individual);
			
			AntennaConfigGroup antennas = settings.getAntennas();
			antennas.disableAll();
			antennas.enableById(new short[] { 1 });
			antennas.getAntenna((short) 1).setIsMaxRxSensitivity(false);
			antennas.getAntenna((short) 1).setIsMaxTxPower(false);
			antennas.getAntenna((short) 1).setTxPowerinDbm(30.0D);
			antennas.getAntenna((short) 1).setRxSensitivityinDbm(-70.0D);			
			OperationCompleteHandler operationCompleteHandler = new OperationCompleteHandler();
			reader.setTagReportListener(operationCompleteHandler);
			reader.setTagOpCompleteListener(operationCompleteHandler);			
			System.out.println("Applying Settings");
			reader.applySettings(settings);
			System.out.println("Starting");
                        TimerDelNonSuccess timerDelNonSuccess=new TimerDelNonSuccess();
                       
			reader.stop();
			reader.start();
		} catch (com.impinj.octane.OctaneSdkException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		}
	}
}

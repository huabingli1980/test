package marginafterdecomplie;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ConnectOp1 {
	public ConnectOp1() {
	}

	static ImpinjReader reader = new ImpinjReader();

	public void ConnectHost(String hostname, JTextField Field) {
		try {
			String S = "";
			boolean b = hostname.equals(S);
			if (b) {
				Field.setFocusable(true);
				Field.requestFocus();

				throw new Exception("Error:hostname is blank");
			}
			
			if (MainRun.marginW.jButton1.getText().equals("Connect")) {
				System.out.println("Connecting to " + hostname);
				reader.connect(hostname);
				Thread.sleep(1000L);
				if (reader.isConnected()) {
					MainRun.marginW.jButton1.setText("DisConnect");
					MainRun.marginW.jButton1.setBackground(Color.GREEN);
				} else {
					throw new Exception("Wrong Disconnection!");
				}

			} else {
				System.out.println("DisConnecting to " + hostname);
                                reader.queryTags();
                                //reader.
				reader.disconnect();
				Thread.sleep(1000L);
				if (reader.isConnected()) {
					javax.swing.JOptionPane.showMessageDialog(null, "DisConnect to host failed");
					throw new Exception("wrong connection!");
				} else {
					MainRun.marginW.jButton1.setText("Connect");
                                        MainRun.marginW.jButton1.setBackground(Color.RED);
				}
                                

			}

		} catch (OctaneSdkException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Connect to " + hostname + " failed! \n" + ex.getMessage());
			MainRun.marginW.jButton1.setBackground(Color.red);
			ex.printStackTrace(System.out);
		}
	}
}

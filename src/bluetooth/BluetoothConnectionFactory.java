package bluetooth;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo;

public class BluetoothConnectionFactory {

	private NXTInfo nxtInfo;
	private NXTConnector connector;
	private boolean connection;
	private OutputStream outputStream;
	private DataOutputStream dataOutputStream;
	
	public BluetoothConnectionFactory(String name, String address) {
		nxtInfo = new NXTInfo(NXTCommFactory.BLUETOOTH, name, address);
		connector = new NXTConnector();
		do{
			connection = connector.connectTo(nxtInfo.name ,nxtInfo.deviceAddress, NXTCommFactory.BLUETOOTH);
			
		} while (!connection);
		outputStream = connector.getOutputStream();
		dataOutputStream = new DataOutputStream(outputStream);
	}
	
	public void runRobot(String speedMotorA, String speedMotorB) {
		byte[] control = new byte[12];
		
		if (speedMotorA.length() == 2) {
			speedMotorA = "0" + speedMotorA;
		}
		
		if (speedMotorB.length() == 2) {
			speedMotorB = "0" + speedMotorB;
		}
		control = (speedMotorA + speedMotorB).getBytes();
		
		try {
			dataOutputStream.write(control);
			dataOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

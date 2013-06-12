package main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import lejos.nxt.remote.NXTCommand;
import lejos.nxt.remote.RemoteMotor;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTCommOutputStream;
import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo;
import lejos.pc.tools.*;
import lejos.nxt.Motor;

/*
 * A class to test bluetooth - dunno if it works correctly
 * TODO: make bluetooth connection work without help from Eclipse
 */
public class BluetoothTest {

	public static void main(String[] args) {
		// BLUETOOTH
		//Motor.A.setPower(100);
		//Motor.A.forward();
		try {
			System.out.println("Started");
			OutputStream stream;
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			NXTInfo nxtInfo = new NXTInfo(NXTCommFactory.BLUETOOTH, "MaxPower", "0016530A6E9D");
			//new NXTInfo
			NXTConnector connector = new NXTConnector();
			NXTInfo ddd = /*nxtComm.search("MaxPower")[0]*/null;
			boolean connectionEst = false;
			//System.out.println(ddd.deviceAddress);
			do{
				//NXTCommOutputStream a = (NXTCommOutputStream) nxtComm.getOutputStream();
				//stream = nxtComm.getOutputStream();
				//connectionEst = nxtComm.open(/*nxtInfo*/ddd);
				connectionEst = connector.connectTo(nxtInfo.name ,nxtInfo.deviceAddress, NXTCommFactory.BLUETOOTH);
				
			} while (!connectionEst);
			System.out.println("connection establised");
			//OutputStream outputStream = nxtComm.getOutputStream();
			OutputStream outputStream = connector.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			try {
				for(int i = 0; i < 50; i++){
					Thread.sleep(2000);
					dataOutputStream.writeInt(5 + i);
					dataOutputStream.flush();
					System.out.println("Sends number " + (i+5));
				}
				dataOutputStream.close();
				connector.close();
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//NXTCommand command = new NXTCommand(nxtComm);
			//RemoteMotor engine = new RemoteMotor(command, 0 /*Port.A*/);
			//engine.setSpeed(100);
			//engine.backward();
		} catch (NXTCommException e) {
			System.out.println("Fanget\n");
			e.printStackTrace();
		}
	}
}
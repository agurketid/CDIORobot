package main;

import lejos.nxt.Motor;
import lejos.nxt.remote.NXTCommand;
import lejos.nxt.remote.RemoteMotor;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

/*
 * A class to test bluetooth - dunno if it works correctly
 * TODO: make bluetooth connection work without help from Eclipse
 */
public class BluetoothTest {

	public static void main(String[] args) {
		// BLUETOOTH
		try {
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			NXTInfo nxtInfo = new NXTInfo(NXTCommFactory.BLUETOOTH, "MaxPower", "00:16:53:0a:6e:9d");
			nxtComm.open(nxtInfo);
			NXTCommand command = new NXTCommand(nxtComm);
			RemoteMotor engine = new RemoteMotor(command, 0 /*Port.A*/);
			engine.setSpeed(100);
			engine.backward();
		} catch (NXTCommException e) {
			e.printStackTrace();
		}
	}

}

package main;

import java.io.OutputStream;

import lejos.nxt.remote.NXTCommand;
import lejos.nxt.remote.RemoteMotor;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTCommOutputStream;
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
		Motor.A.setPower(100);
		Motor.A.forward();
		try {
			OutputStream stream;
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			NXTInfo nxtInfo = new NXTInfo(NXTCommFactory.BLUETOOTH, "MaxPower", "0016530a6e9d");
			boolean connectionEst = false;
			do{
				//NXTCommOutputStream a = (NXTCommOutputStream) nxtComm.getOutputStream();
				//stream = nxtComm.getOutputStream();
				connectionEst = nxtComm.open(nxtInfo);
			} while (!connectionEst);
			NXTCommand command = new NXTCommand(nxtComm);
			RemoteMotor engine = new RemoteMotor(command, 0 /*Port.A*/);
			engine.setSpeed(100);
			engine.backward();
		} catch (NXTCommException e) {
			System.out.println("Fanget\n");
			e.printStackTrace();
		}
	}
}
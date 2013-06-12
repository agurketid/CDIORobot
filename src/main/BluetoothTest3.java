package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class BluetoothTest3 {
	private static NXTComm nxtComm;
	private static NXTInfo[] nxtInfo = null;
	private static InputStream is;
	private static OutputStream os;
	private static DataInputStream inDat;
	private static DataOutputStream outDat;
	private static ArrayBlockingQueue<Integer> blockingQueue;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		connect();
		outDat.writeByte(5);
		System.out.println("heste");
		while(true);
	}
	private static boolean connect() {
		try {
			nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
		} catch (NXTCommException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			nxtInfo = nxtComm.search("MaxPower");
		} catch (NXTCommException e) {
			System.out.println("Exception in search");
		}
		
		if (nxtInfo.length == 0) {
			System.out.println("No NXT Found");
			System.exit(1);
		}

		try {
			nxtComm.open(nxtInfo[0]);
		} catch (NXTCommException e) {
			System.out.println("Exception in open");
			return false;
		}
		
		is = nxtComm.getInputStream();
		os = nxtComm.getOutputStream();
		inDat = new DataInputStream(is);
		outDat = new DataOutputStream(os);
		//while(true);
		return true;
		
	}

}

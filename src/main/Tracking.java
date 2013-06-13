package main;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;


import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber.Exception;

import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.avcodec.AVHWAccel.Start_frame;
import com.googlecode.javacv.cpp.opencv_legacy.CvBlobDetector;
import com.googlecode.javacv.cpp.opencv_core.*;

public class Tracking {

	Boolean running = true;
	Boolean trackingThreadFinished = false;
	GUI gui;
	CvRect rect;
	OpenCVFrameGrabber grapper;
	IplImage image,hsv,thresh;
	CvMemStorage storage = CvMemStorage.create();
	ArrayList<ArrayList<Position>> dataList = new ArrayList<ArrayList<Position>>();
	
	public Tracking() {
		gui = new GUI();
		new Thread(gui).run();
		setupCamera();
		try {
			capture();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setupCamera() {
		grapper = new OpenCVFrameGrabber(0);
		try {
			grapper.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void capture() throws com.googlecode.javacv.FrameGrabber.Exception {
		/* Source feed/image */
		image = grapper.grab();

		/* HSV image */
		hsv = cvCreateImage(cvGetSize(image), 8, 3);

		/* HSV output / filter image */
		thresh = cvCreateImage(cvGetSize(image), 8, 1);

		/* Frame for filter */
		CanvasFrame cfFilter = new CanvasFrame("Filtered feed");
		cfFilter.setCanvasSize(image.width(), image.height());
		cfFilter.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		/* Frame for source */
		CanvasFrame cfSource = new CanvasFrame("Source feed");
		cfSource.setCanvasSize(image.width(), image.height());
		cfSource.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		/* Image processing */
		while (running) {
			CvSeq contours = new CvSeq();

			image = grapper.grab();
			// image = cvLoadImage("spectrum.bmp");
			// cvSmooth(image, image, CV_GAUSSIAN, 1);

			/* FILTERS APPLIED */
			cvThreshold(thresh, thresh, 64, 255, CV_THRESH_BINARY);
			if(gui.getReady()){
				running = false; 
				break;
			}
			else{
				cvDilate(image, image, null, gui.getDilate());
				cvErode(image, image, null, gui.getErode());
			}
			cvSmooth(image, image, CV_GAUSSIAN, gui.getSmooth());

			/* Isolating a color range from the source image */
			thresh = getThresholdedImage(image, hsv, thresh, gui.getR_min(), gui.getG_min(), gui.getB_min(), gui.getR_max(), gui.getG_max(), gui.getB_max());
			
			/* Finding contours in the image */
			cvFindContours(thresh, storage, contours,
					Loader.sizeof(CvContour.class), CV_RETR_LIST,
					CV_CHAIN_APPROX_SIMPLE/* , cvPoint(10, 10) */);

			CvPoint p1 = cvPoint(0, 0);
			CvPoint p2 = cvPoint(0, 0);

			while (contours != null && !contours.isNull()) {
				rect = cvBoundingRect(contours, 0);

				p1.x(rect.x());
				p2.x(rect.x() + rect.width());
				p1.y(rect.y());
				p2.y(rect.y() + rect.height());

				if (((rect.width() > gui.getSize()) || (rect.height() > gui
						.getSize()))
						&& ((rect.width() < gui.getSMAX()) || (rect.height() < gui
								.getSMAX()))) {
					// 2, 8
					cvRectangle(image, p1, p2, CV_RGB(0, 0, 255), 1, CV_AA, 0);

					// System.out.println("Midpoint : ("+rect.x()+rect.width()/2+","+rect.y()+rect.height()/2+")");
					cvCircle(
							image,
							cvPoint(rect.x() + rect.width() / 2, rect.y()
									+ rect.height() / 2), 1, CV_RGB(0, 0, 255),
							2, 8, 0);

					cvDrawContours(thresh, contours, CvScalar.BLUE,
							CV_RGB(248, 18, 18), 1, -1, 8);
					//Add the found blocks to a list if satisfied with the found blocks
				}
				contours = contours.h_next();
			}
//			blocklist.addAll(blocks_temp);
//			blocks_temp.clear();
			gui.setGreen_OK(false);
			gui.setRed_OK(false);

			cfFilter.showImage(thresh);
			cfSource.showImage(image);
			cvWaitKey(0);
			// image = null;
			cvClearMemStorage(storage);
//			System.out.println(blocklist.toString());
		}
		//If all settings are final check all settings
		
		imageProcessing();
	}
	
	public void imageProcessing() throws Exception {
		image = grapper.grab();
		dataList.clear();
		for(Settings setting : gui.getColorSettings()) {
			System.out.println(setting.toString());
			ArrayList<Position> tmp_position = new ArrayList<Position>();
			CvSeq contours = new CvSeq();

//			try {
//				image = grapper.grab();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}

			/* FILTERS APPLIED */
			cvThreshold(thresh, thresh, 64, 255, CV_THRESH_BINARY);
			cvDilate(image, image, null, setting.getDilate_lvl());
			cvErode(image, image, null, setting.getErode_lvl());
			
			cvSmooth(image, image, CV_GAUSSIAN, setting.getSmooth_lvl());

			/* Isolating a color range from the source image */
			thresh = getThresholdedImage(image, hsv, thresh, setting.getR_min(), setting.getG_min(), setting.getB_min(), setting.getR_max(), setting.getG_max(), setting.getB_max());

			/* Finding contours in the image */
			cvFindContours(thresh, storage, contours,
					Loader.sizeof(CvContour.class), CV_RETR_LIST,
					CV_CHAIN_APPROX_SIMPLE/* , cvPoint(10, 10) */);

			CvPoint p1 = cvPoint(0, 0);
			CvPoint p2 = cvPoint(0, 0);

			while (contours != null && !contours.isNull()) {
				rect = cvBoundingRect(contours, 0);

				p1.x(rect.x());
				p2.x(rect.x() + rect.width());
				p1.y(rect.y());
				p2.y(rect.y() + rect.height());

				if (((rect.width() > setting.getS_min()) || (rect.height() > setting.getS_min()))
						&& ((rect.width() < setting.getS_max()) || (rect.height() < setting.getS_max()))) {
							
					tmp_position.add(new Position(rect.x() + rect.width() / 2, rect.y()
									+ rect.height() / 2));
				}
				contours = contours.h_next();
			}
			System.out.println("List Content:"+tmp_position);
			
			dataList.add(new ArrayList<Position>(tmp_position));
			System.out.println("Final list:"+dataList.toString());
			tmp_position.clear();
			gui.setGreen_OK(false);
			gui.setRed_OK(false);
			cvWaitKey(0);
			// image = null;
			cvClearMemStorage(storage);
//			System.out.println(blocklist.toString());
		}
		trackingThreadFinished = true;
		
	}
	
	public IplImage getThresholdedImage(IplImage img, IplImage imgHSV,
			IplImage imgThreshed, int r_min, int g_min, int b_min, int r_max, int g_max, int b_max) {
		cvCvtColor(img, imgHSV, CV_BGR2HSV);
		cvInRangeS(imgHSV,
				cvScalar(b_min, g_min, r_min, 0),
				cvScalar(b_max, g_max, r_max, 0),
				imgThreshed);
		return imgThreshed;
	}


	
/*	public synchronized void putInto(Color color, ArrayList<Block> blocks_temp){
		Iterator<Block> i = blocklist.iterator();
		while(i.hasNext()) {
			Block b = i.next();
			if(b.getColor().equals(color)){
				i.remove();
			}
		}
		// blocklist.clear();
		Position pos1 = new Position(rect.x(), rect.y());
		Position pos2 = new Position(rect.x() + rect.width(),
				rect.y());
		Position pos3 = new Position(rect.x(), rect.y()
				+ rect.height());
		Position pos4 = new Position(rect.x() + rect.width(),
				rect.y() + rect.height());

		blocks_temp.add(new Block(pos1, pos2, pos3, pos4,
				color));
	}*/
	
	public ArrayList<ArrayList<Position>> getData(){
		return dataList;
 	}

}

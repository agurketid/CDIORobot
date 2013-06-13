package main;

import java.util.ArrayList;

import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.cpp.avfilter.AVFilterPad.Get_audio_buffer;

import lejos.nxt.Motor;

public class RobotLogic {

	static ArrayList<Position> redBlocks = new ArrayList<Position>();
	static ArrayList<Position> greenBlocks = new ArrayList<Position>();
	static Position robotFront = new Position();
	static Position robotBack = new Position();
	static ArrayList<Port> ports = new ArrayList<Port>();
	static ArrayList<Position> route = new ArrayList<Position>();
	static ArrayList<ArrayList<Position>> listOfPositions = new ArrayList<ArrayList<Position>>();
	static Robot robot = new Robot();
	static String robotMovement;
	static int speedDifference;
	static final int robotSpeed = 200;

	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		Tracking track = new Tracking();
		new Thread(track).run();
		//track.run();
		// TODO: CALL CONSTRUCTOR OF HSV_IMAGE_ANALYSIS CLASS

		while (track.trackingThreadFinished != false) {
			System.out.println("FINALDATA, TEST: " + track.getData().toString());
			listOfPositions.addAll(track.getData());
			// TODO: call function in hsv_image_class
			// SHOULD RETURN A LIST OF LISTS WITH DATA
			// I.E. A LIST OF POSITIONS OF RED BLOCKS, GREEN BLOCKS, POSITION OF ROBOT FRONT AND BACK
			// redBlocks, greenBlocks, robotFront, robotBack
			// fetch_data_from_hsv_image_class();
			
			// TODO: listOfPositions = fetch_data_from_hsv_image_class();
			
			// get data from video feed (hsv_image_class)
			// center positions of all red blocks
			redBlocks = listOfPositions.get(0);
			System.out.println("REDBLOCKS, TEST: "+ redBlocks.toString());
			// center positions for all green blocks
			greenBlocks = listOfPositions.get(1);
			System.out.println("GREENBLOCKS, TEST: "+ greenBlocks.toString());
			// center position for the robot's front square
			System.out.println("ROBOTFRONT, TEST: " +listOfPositions.get(2).toString());
			robotFront = listOfPositions.get(2).get(0);//TODO
			// center position for the robot's back square
			robotBack = (listOfPositions.get(3)).get(0);
			
			System.out.println(redBlocks.toString());
			System.out.println(greenBlocks.toString());
			System.out.println(robotFront.toString());
			System.out.println(robotBack.toString());
			try {
				track.imageProcessing();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// initialize robot
//			robot.robotInit(robotFront, robotBack);
//			// map ports
//			ports = mapPorts(redBlocks, greenBlocks);
//			// map route
//			route = mapRoute(ports, robot);
//			// calculate robot movement - left/right
//			robotMovement = calculateRobotMovement(robot, route);
//			// calculate speed difference on wheel
//			speedDifference = calculateRobotSpeed(robot, route);
//			
//			// send movement signals to the Robot's wheels
//			if (robotMovement.equals("RIGHT")) {
//				Motor.A.setSpeed(robotSpeed);
//				Motor.B.setSpeed(robotSpeed-speedDifference);
//				Motor.A.backward();
//				Motor.B.backward();
//			} else { // robotMovement.equals("LEFT")
//				Motor.B.setSpeed(robotSpeed);
//				Motor.A.setSpeed(robotSpeed-speedDifference);
//				Motor.B.backward();
//				Motor.A.backward();
//			}
//			
//			long middle = System.currentTimeMillis();
//			long middleTime = middle - startTime;
//			System.out.println("Calculation time: " + middleTime);
		}
	}
	
	/*+-----------------------------------------------------------------------+
	  | Maps the red and green Blocks closest to each other together as ports |
	  +-----------------------------------------------------------------------+*/
	public static ArrayList<Port> mapPorts(ArrayList<Position> redBlocks, ArrayList<Position> greenBlocks) {
		// temporary ArrayList to store Ports in
		ArrayList<Port> portsTemp = new ArrayList<Port>();
		// the index of the closest green Block - from the red Block
		int index = 0;
		// variables to store distance measurements
		int d0, d1;

		// find the closest green Block for all red Blocks and create a Port
		for(int i = 0; i < redBlocks.size(); i++) {
			// distance between the red Block and the first green Block in the list - base case
			d0 = redBlocks.get(i).calculateDistance(greenBlocks.get(0));
			// index of the first element
			index = 0;
			
			// finds the index of the green Block closest to the red Block
			for (int j = 1; j < greenBlocks.size(); j++) {
				// distance between the red Block and the next green Block
				d1 = redBlocks.get(i).calculateDistance(greenBlocks.get(j));

				// if the next (jth) green Block is closer to the red Block, than the (until now) closest green Block
				// save this green Block's index into the ArrayList - otherwise do nothing
				if (d1 < d0) {
					d0 = d1;
					index = j;
				}
			}
			// the red and green Blocks closest to each other are mapped together as a Port
			portsTemp.add(new Port(redBlocks.get(i),greenBlocks.get(index)));
		}
		return portsTemp;
	}

	/*+--------------------------------------------------------------------------------+
	  | Calculates to Robot's route between the different Ports (in and out Positions) |
	  +--------------------------------------------------------------------------------+*/
	// TODO: determine if it is necessary to map the whole route, or if 2-3 ports is enough
	public static ArrayList<Position> mapRoute(ArrayList<Port> ports, Robot robot) {
		// temporary ArrayList
		ArrayList<Position> route = new ArrayList<Position>();
		// Create copy of port array
		ArrayList<Port> portsTemp = new ArrayList<Port>();
		portsTemp.addAll(ports);
		// Add start position to the "route list"
		route.add(robot.getMiddle());
		// variables to store distance measurements 
		int d0, d1; 
		// index on the array of the closest point
		int index = 0; 

		for (int i = 0; i < ports.size(); i++) {
			// distance between latest added position, and first in position in the portTemp array - base case
			d0 = route.get(route.size()-1).calculateDistance(portsTemp.get(0).getIn());
			// index of the first element
			index = 0;

			// find the index of the closest in position
			for(int j = 1; j < portsTemp.size(); j++) {
				// distance between the latest added position in the route list and next in position in the portsTemp array
				d1 = route.get(route.size()-1).calculateDistance(portsTemp.get(j).getIn());

				if (d1 < d0) {
					d0 = d1;
					index = j;
				}
			}

			// add the in position and out position to the "route list"
			route.add(portsTemp.get(index).getIn());
			route.add(portsTemp.get(index).getOut());
			// remove the port (in and out) from the list - it should only appear in the "route list" once
			portsTemp.remove(index);
		}
		return route;
	}

	/*+-----------------------------------------------------------------+
	  | Calculates the Robot's movement based on position and direction |
	  +-----------------------------------------------------------------+*/
	public static String calculateRobotMovement(Robot robot, ArrayList<Position> route) {
		// criterion to determine whether the robot should turn left or right depending on its position 
		boolean front = robot.getFront().getX() > robot.getBack().getX();
		boolean dir = robot.getDirection() > robot.getMiddle().calculateSlope(route.get(1));
		boolean left = robot.getMiddle().getX() < route.get(1).getX();
		boolean up = robot.getMiddle().getY() < route.get(1).getY();

		// the logic for the calculations
		if ((front && !dir && !left && !up) || (!front && dir && !left && !up) || (front && dir && left && !up) 
				|| (front && dir && left && up) || (!front && !dir && left && up) || (!front && dir && !left && up)
				|| (!front && dir && !left && up) || (!front && dir && !left && !up)) {
			return "LEFT";
		}
		return "RIGHT";
	}

	/*+---------------------------------------------------------------+
	  | Calculates the difference in speed between the Robot's motors |
	  +---------------------------------------------------------------+*/
	// TODO: determine the right "factor" for the difference in speed
	public static int calculateRobotSpeed(Robot robot, ArrayList<Position> route) {
		// center of robot
		Position p0 = new Position(robot.getMiddle().getX(), robot.getMiddle().getY());
		// in point of next port
		Position p1 = new Position(route.get(1).getX(), route.get(1).getY());
		// slope of line made from the robot's front and back position
		double a = robot.getBack().calculateSlope(robot.getFront());
		// intersection with the y-axis
		double b = p0.getY() - (a*p0.getX());
		// distance between robot and in point of next port
		int distRobotPort = robot.getMiddle().calculateDistance(route.get(1));
		// x and y values for the new point
		// on the line of the Robot's current direction, with a distance as 'distRobotPort' from the Robot's middle
		int x = (int) (p0.getX() + Math.abs(distRobotPort / a));
		int y = (int) ((a * x) + b);
		// new Position based on the x and y values
		Position pos = new Position(x,y);
		// distance between the new point and the in point of first port
		// this determines the how "off course" the Robot is
		int dist = p1.calculateDistance(pos);

		return dist;    	
	}
}

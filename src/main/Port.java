package main;

public class Port {
	
	private Position red; // red block
	private Position green; // green block
	private Position in = new Position(); 
	private Position out = new Position();
	
	public Port(Position p1, Position p2) {
		this.red = p1;
		this.green = p2;
		calculateInAndOut();
	}
	
	// calculates the entry (in) and exit (out) points of a port
	private void calculateInAndOut() {
		int vX = (-1*(green.getY() - red.getY())) / 2;
		int vY = (green.getX() - red.getX()) / 2;
		int centerX = (red.getX() + green.getX()) / 2;
		int centerY = (red.getY() + green.getY()) / 2;
		
		// the in and out should match the intended direction
		if (green.getX() < red.getX()) {
			in.setX(centerX - vX);
			in.setY(centerY - vY);
			out.setX(centerX + vX);
			out.setY(centerY + vY);
		} else {
			in.setX(centerX - vX);
			in.setY(centerY - vY);
			out.setX(centerX + vX);
			out.setY(centerY + vY);
		}
	}
	
	public Position getRed() {
		return red;
	}
	
	public Position getGreen() {
		return green;
	}
	
	public Position getIn() {
		return in;
	}
	
	public Position getOut() {
		return out;
	}
}

package imageProcessing;

public class Settings {
	//Setting identifiers for lookup
	String name;
	String color;
	//Set RGB-Min values 
	int r_min;
	int g_min;
	int b_min;
	//Set RGB-Max values
	int r_max;
	int g_max;
	int b_max;
	//Set Size-Min/Max values
	int s_min;
	int s_max;
	//Set different filter values
	int smooth_lvl;
	int erode_lvl;
	int Dilate_lvl;
	
	public Settings(String name, String color, int r_min, int g_min, int b_min, int r_max, int g_max, int b_max,
			int s_min, int s_max, int smooth_lvl, int erode_lvl, int Dilate_lvl) {
		this.name = name;
		this.color = color;
		
		this.r_min = r_min;
		this.g_min = g_min;
		this.b_min = b_min;
		
		this.r_max = r_max;
		this.g_max = g_max;
		this.b_max = b_max;
		
		this.s_min = s_min;
		this.s_max = s_max;
		
		this.smooth_lvl = smooth_lvl;
		this.erode_lvl = erode_lvl;
		this.Dilate_lvl = Dilate_lvl;
	}
	
	public String getName() {
		return name;
	}

	public int getR_min() {
		return r_min;
	}

	public int getG_min() {
		return g_min;
	}

	public int getB_min() {
		return b_min;
	}

	public int getR_max() {
		return r_max;
	}

	public int getG_max() {
		return g_max;
	}

	public int getB_max() {
		return b_max;
	}

	public int getS_min() {
		return s_min;
	}

	public int getS_max() {
		return s_max;
	}

	public int getSmooth_lvl() {
		return smooth_lvl;
	}

	public int getErode_lvl() {
		return erode_lvl;
	}

	public int getDilate_lvl() {
		return Dilate_lvl;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setR_min(int r_min) {
		this.r_min = r_min;
	}

	public void setG_min(int g_min) {
		this.g_min = g_min;
	}

	public void setB_min(int b_min) {
		this.b_min = b_min;
	}

	public void setR_max(int r_max) {
		this.r_max = r_max;
	}

	public void setG_max(int g_max) {
		this.g_max = g_max;
	}

	public void setB_max(int b_max) {
		this.b_max = b_max;
	}

	public void setS_min(int s_min) {
		this.s_min = s_min;
	}

	public void setS_max(int s_max) {
		this.s_max = s_max;
	}

	public void setSmooth_lvl(int smooth_lvl) {
		this.smooth_lvl = smooth_lvl;
	}

	public void setErode_lvl(int erode_lvl) {
		this.erode_lvl = erode_lvl;
	}

	public void setDilate_lvl(int dilate_lvl) {
		Dilate_lvl = dilate_lvl;
	}

	public String toString(){
		return "Name: "+this.name+"Color: "+this.color+"\nG_min: "+this.g_min;
	}
	
}

package openstreetcraft;

public class RGBColor {
	
	private int r;
	private int g;
	private int b;
	
	public RGBColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public void setRGB(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}	

}

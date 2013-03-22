package generators;

public class Calque {
	
	private int[][] v;
	private int[] taille;
	private double persistance;
	
	public Calque(int[] taille, double persistance) {		
		this.v = new int[taille[0]][taille[1]];		
		this.taille = taille;
		this.persistance = persistance;
	}

	public int getV(int x, int y) {
		return v[x][y];
	}

	public void setV(int x, int y, int v) {
		this.v[x][y] = v;
	}

	public int[] getTaille() {
		return taille;
	}

	public void setTaille(int[] taille) {
		this.taille = taille;
	}

	public double getPersistance() {
		return persistance;
	}

	public void setPersistance(double persistance) {
		this.persistance = persistance;
	}

}

package openstreetcraft;

public class Location implements Cloneable {
	
	private int x;
	private int y;
	private int z;	
	
	/**
	 * Initialise l'objet à la position (0,0,0).
	 */
	public Location() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	/**
	 * Initialise l'objet à la position (x,0,z).
	 * @param x coordonnée en x.
	 * @param z coordonnée en z.
	 */
	public Location(int x,int z){
		
		this.x = x;
		this.z = z;
		this.y = 0;
	}
	/**
	 * Initialise l'objet à la position passée en paramètres.
	 * @param x coordonnée en x.
	 * @param y coordonnée en y.
	 * @param z coordonnée en z.
	 */
	public Location(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	/**
	 * Minimise les coordonnée de l'objet tout en maximisant celui passé en paramètre.
	 * Utile pour obtenir des coordonnées extrémales.
	 * @param loc un autre Location dont coordonnées vont être maximisées
	 */
	public void minimise(Location loc){
		int temp;
		if(loc.getX()<this.getX()){
			temp=loc.getX();loc.setX(this.getX());this.setX(temp);
		}
		if(loc.getY()<this.getY()){
			temp=loc.getY();loc.setY(this.getY());this.setY(temp);
		}
		if(loc.getZ()<this.getZ()){
			temp=loc.getZ();loc.setZ(this.getZ());this.setZ(temp);
		}
	}
	/**
	 * Clone l'objet.
	 * @return un objet Location identique.
	 */
	public Object clone(){
		Object o = null;
    	try {
      		o = super.clone();
    	} catch(CloneNotSupportedException e) {
      		e.printStackTrace(System.err);
	    }
	    return o;
	}
	/**
	 * Retourne la coordonée en x.
	 * @return position en x.
	 */
	public int getX() {
		return x;
	}
	/**
	 * Modifie la valeur de x.
	 * @param x la nouvelle valeur de x.
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Retourne la coordonée en y.
	 * @return position en y. 
	 */
	public int getY() {
		return y;
	}
	/**
	 * Modifie la valeur de y.
	 * @param y la nouvelle valeur de y.
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * Retourne la coordonée en z.
	 * @return position en z.
	 */
	public int getZ() {
		return z;
	}
	/**
	 * Modifie la valeur de z.
	 * @param z la nouvelle valeur de z.
	 */
	public void setZ(int z) {
		this.z = z;
	}

}

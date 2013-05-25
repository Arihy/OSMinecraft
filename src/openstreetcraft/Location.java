package openstreetcraft;

public class Location implements Cloneable {
	
	private int x;
	private int y;
	private int z;	
	
	/**
	 * Initialise l'objet � la position (0,0,0).
	 */
	public Location() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	/**
	 * Initialise l'objet � la position (x,0,z).
	 * @param x coordonn�e en x.
	 * @param z coordonn�e en z.
	 */
	public Location(int x,int z){
		
		this.x = x;
		this.z = z;
		this.y = 0;
	}
	/**
	 * Initialise l'objet � la position pass�e en param�tres.
	 * @param x coordonn�e en x.
	 * @param y coordonn�e en y.
	 * @param z coordonn�e en z.
	 */
	public Location(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	/**
	 * Minimise les coordonn�e de l'objet tout en maximisant celui pass� en param�tre.
	 * Utile pour obtenir des coordonn�es extr�males.
	 * @param loc un autre Location dont coordonn�es vont �tre maximis�es
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
	 * Retourne la coordon�e en x.
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
	 * Retourne la coordon�e en y.
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
	 * Retourne la coordon�e en z.
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

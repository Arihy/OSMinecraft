package generators;

public class Location implements Cloneable {
	
	private int x;
	private int y;
	private int z;	
	
	public Location() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Location(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	//Minimise les coordonnée de l'objet tout en maximisant celui passé en paramètre. Utile pour obtenir des coordonnées extrémales.
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
	
	public Object clone(){
		Object o = null;
    	try {
      		o = super.clone();
    	} catch(CloneNotSupportedException e) {
      		e.printStackTrace(System.err);
	    }
	    return o;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

}

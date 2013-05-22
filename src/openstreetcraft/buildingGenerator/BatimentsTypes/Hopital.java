package openstreetcraft.buildingGenerator.BatimentsTypes;

import java.util.Vector;

import openstreetcraft.Location;
import openstreetcraft.buildingGenerator.Batiment;
import openstreetcraft.buildingGenerator.Constructeur;

public class Hopital extends Batiment {

	public Hopital(Vector<Location> points, int indexFacade) {
		super(points,indexFacade,(short)155,Constructeur.blockID(35, 3),15);
		this.ajouterCroix();
	}
	
	public void ajouterCroix(){
		if (this.getTailleX() >= 5) {
			int x = this.getTailleX() / 2, z = 0, y = this.getTailleY() - 4;
			short id = Constructeur.blockID(35, 14);
			while (this.getBlock(x, z, y) == 0) {
				z++;
			}
			this.setBlock(x + 1, z, y, id);
			this.setBlock(x - 1, z, y, id);
			this.setBlock(x, z, y + 1, id);
			this.setBlock(x, z, y - 1, id);
			this.setBlock(x, z, y, id);
		}
	}

}

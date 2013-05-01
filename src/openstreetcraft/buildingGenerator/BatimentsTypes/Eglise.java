package openstreetcraft.buildingGenerator.BatimentsTypes;

import java.util.Vector;

import openstreetcraft.Location;
import openstreetcraft.buildingGenerator.Batiment;
import openstreetcraft.buildingGenerator.Constructeur;

public class Eglise extends Batiment {

	public Eglise(Vector<Location> points, int hauteurMin) {
		super(points, Constructeur.blockID(98, 3), (short)43, hauteurMin);
		this.ajouterVitraux();
	}

	private void ajouterVitraux() {
		if (this.getTailleX() >= 6) {
			int x = this.getTailleX() / 2, z = 0, y = this.getTailleY() - 5;
			short id = (short)89;
			while (this.getBlock(x, z, y) == 0) {
				z++;
			}
			for(int i=x-1;i<=x+2;i++){
				this.setBlock(i, z, y, id);
				this.setBlock(i, z, y - 1, id);
			}
			for(int i=y-2;i<=y+1;i++){
				this.setBlock(x, z, i, id);
				this.setBlock(x+1, z, i, id);
			}
		}
	}

}

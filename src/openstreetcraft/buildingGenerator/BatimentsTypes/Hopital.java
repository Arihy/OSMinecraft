package openstreetcraft.buildingGenerator.BatimentsTypes;

import java.util.Vector;
import openstreetcraft.IDBlock;
import openstreetcraft.Location;
import openstreetcraft.buildingGenerator.Batiment;

public class Hopital extends Batiment {

	/**
	 * Cr�er un hopital.
	 * @param points les Location d�limitant la surface de l'h�pital.
	 * @param indexFacade le num�ro du premier point de la fa�ade.
	 */
	public Hopital(Vector<Location> points, int indexFacade) {
		super(points,indexFacade,IDBlock.QUARTZ,IDBlock.blockData(IDBlock.LAINE, 3),15);
		this.ajouterCroix();
	}
	/**
	 * Cr�e une croix rouge et l'ajoute sur la fa�ade de l'h�pital.
	 */
	public void ajouterCroix(){
		if (this.getTailleX() >= 5) {
			int x = this.getTailleX() / 2, z = 0, y = this.getTailleY() - 4;
			short id = IDBlock.blockData(IDBlock.LAINE, 14);
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

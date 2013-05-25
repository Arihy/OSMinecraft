package openstreetcraft.buildingGenerator.BatimentsTypes;

import java.util.Vector;
import openstreetcraft.IDBlock;
import openstreetcraft.Location;
import openstreetcraft.buildingGenerator.Batiment;

public class Hopital extends Batiment {

	/**
	 * Créer un hopital.
	 * @param points les Location délimitant la surface de l'hôpital.
	 * @param indexFacade le numéro du premier point de la façade.
	 */
	public Hopital(Vector<Location> points, int indexFacade) {
		super(points,indexFacade,IDBlock.QUARTZ,IDBlock.blockData(IDBlock.LAINE, 3),15);
		this.ajouterCroix();
	}
	/**
	 * Crée une croix rouge et l'ajoute sur la façade de l'hôpital.
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

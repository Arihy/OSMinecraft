package openstreetcraft.buildingGenerator.BatimentsTypes;

import java.util.Vector;
import openstreetcraft.IDBlock;
import openstreetcraft.Location;
import openstreetcraft.buildingGenerator.Batiment;

public class Eglise extends Batiment {

	/**
	 * Créer une Eglise.
	 * @param points les Location délimitant la surface de l'eglise.
	 * @param indexFacade le numéro du premier point de la façade.
	 * @param hauteurMin la hauteur minimale de l'église..
	 */
	public Eglise(Vector<Location> points, int indexFacade, int hauteurMin) {
		super(points,indexFacade, IDBlock.blockData(IDBlock.TAILLEE, 3), IDBlock.DBLDALLE, hauteurMin);
		this.ajouterVitraux();
	}
	/**
	 * Crée un vitrail et l'ajoute en façade.
	 */
	private void ajouterVitraux() {
		if (this.getTailleX() >= 6) {
			int x = this.getTailleX() / 2, z = 0, y = this.getTailleY() - 5;
			short id = IDBlock.LUMINITE;
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

package openstreetcraft.buildingGenerator;

import java.util.Vector;

import openstreetcraft.IDBlock;
import openstreetcraft.Location;

public class Batiment extends Structure{
	
	private Vector<Location> points;
	private int hauteur;
	private short matiereExt;
	private short matiereInt;
	private int indexFacade;

	/**
	 * Constructeur de la classe batiment
	 * @param points les Location délimitant la surface du bâtiment
	 * @param indexFacade l'indice du premier point de la façade du batiment.
	 */
	//Construction du batiment en pierre taillée et bois à partir des points
	public Batiment(Vector<Location> points, int indexFacade) {
		super();
		this.points = points;
		this.matiereExt=IDBlock.TAILLEE;
		this.matiereInt=IDBlock.PLANCHE;
		this.indexFacade=indexFacade;
		Location d=Constructeur.pointMin(points), f=Constructeur.pointMax(points);
		this.hauteur = 4 + (int)(Math.log(((f.getX()-d.getX()+1)*(f.getZ()-d.getZ()+1)))/Math.log(3) *(double)(Math.random()+1));
		this.setTaille(f.getX()-d.getX()+1,getHauteur(),f.getZ()-d.getZ()+1);
		this.dessinerStructure();
	}
	/**
	 * Constructeur de la classe batiment
	 * @param points les Location délimitant la surface du bâtiment.
	 * @param indexFacade l'indice du premier point de la façade du batiment.
	 * @param matiereExt la matière des murs.
	 * @param matiereInt la matière du sol.
	 * @param hauteurMin la hauteur minimale du batiment.
	 */
	//Construction du batiment à partir des points et des matériaux Extérieur et Intérieurs en paramètres
	public Batiment(Vector<Location> points, int indexFacade, short matiereExt, short matiereInt,int hauteurMin) {
		super();
		this.points = points;
		this.matiereExt=matiereExt;
		this.matiereInt=matiereInt;
		this.indexFacade=indexFacade;
		Location d=Constructeur.pointMin(points), f=Constructeur.pointMax(points);
		this.hauteur = (int)(hauteurMin*(double)(Math.random()+1));
		this.setTaille(f.getX()-d.getX()+1,getHauteur(),f.getZ()-d.getZ()+1);
		this.dessinerStructure();
	}
	/**
	 * Construit le bâtiment.
	 */
	private void dessinerStructure() {
		for(int i=0;i<points.size();i++){
			this.dessinerMur(points.get(i), points.get((i+1)%points.size()), (i==indexFacade));	
		}
		this.dessinerSol();
		this.dessinerToit();
	}
	/**
	 * Construire un mu entre deux Location.
	 * @param pDep première extrémité du mur.
	 * @param pFin seconde extrémité du mur.
	 * @param facade indique si le mur à construire est une façade ou non.
	 */
	private void dessinerMur(Location pDep, Location pFin, boolean facade){
		Location d=Constructeur.pointMin(this.points), p1, p2;
		double coef;
		int Larg, Long, curX, curY, curZ, distance, curLong, longueur;
		int distX=Math.abs(pFin.getX()-pDep.getX())+1, distZ=Math.abs(pFin.getZ()-pDep.getZ())+1;
		boolean axeZ=(distZ>distX);
		
		if( axeZ ){
			if(pDep.getZ()<pFin.getZ()){p1=pDep;p2=pFin;}
			else{p2=pDep;p1=pFin;}			
			coef=(double)(p2.getX()-p1.getX())/(double)(distZ);
			longueur=distZ;
		}	
		else{
			if(pDep.getX()<pFin.getX()){p1=pDep;p2=pFin;}
			else{p2=pDep;p1=pFin;}			
			coef=(double)(p2.getZ()-p1.getZ())/(double)(distX);
			longueur=distX;
		}
		
		for (Long = 0; Long < longueur; Long++) {
			Larg = (int) (coef * Long);
			if (coef > 0)
				Larg++;
			for (int y = 0; y < this.getHauteur(); y++) {
				if(axeZ){
					curX = p1.getX() + Larg - d.getX();
					curZ = p1.getZ() + Long - d.getZ();
					distance=this.getTailleZ();curLong=curZ;
				}else{
					curX = p1.getX() + Long - d.getX();
					curZ = p1.getZ() + Larg - d.getZ();
					distance=this.getTailleX();curLong=curX;
				}
				curY = p1.getY() + y - d.getY();
				if (curX < this.getTailleX() && curZ < this.getTailleZ() && curY < this.getTailleY()) {
					if (facade && (curY==1 || curY==2) && curLong==(distance/2)) {
						if(curY==1){this.setBlock(curX, curZ, curY, IDBlock.PORTE);}
						else if(curY==2){this.setBlock(curX, curZ, curY, IDBlock.blockData(IDBlock.PORTE, 8));}
					} else if (curLong < (distance - 1) && (curLong % 3) == 2
							&& curY < (this.getTailleY() - 1) && (curY % 5) > 1 && (curY % 5) < 4) {
						if (coef == 0) {
							this.setBlock(curX, curZ, curY, IDBlock.VITRE);
						} else {
							this.setBlock(curX, curZ, curY, IDBlock.VERRE);
						}
					} else {
						this.setBlock(curX, curZ, curY, this.matiereExt);
					}
				}
			}
		}
	}
	
	/**
	 * Remplit le sol du bâtiment.
	 */
	private void dessinerSol(){
		for(int x=0;x<this.getTailleX();x++){
			for(int z=0;z<this.getTailleZ();z++){
				if(this.getBlock(x, z, 0)==0){
					this.setBlock(x, z, 0, this.matiereInt);
				}
			}
		}
		for(int x=0;x<this.getTailleX();x++){
			remplirZone(x,0,0,(short)0,this.matiereInt);
			remplirZone(x,this.getTailleZ()-1,0,(short)0,this.matiereInt);
		}
		for(int z=0;z<this.getTailleZ();z++){
			remplirZone(0,z,0,(short)0,this.matiereInt);
			remplirZone(this.getTailleX()-1,z,0,(short)0,this.matiereInt);
		}			
	}
	/**
	 * Construit le toit du batiment.
	 */
	private void dessinerToit(){
		short matiere = (short)((this.getTailleY()<10 || matiereExt%256==IDBlock.PLANCHE || matiereExt%256==IDBlock.BOIS)? IDBlock.blockData(5,1) : (getHauteur()<24)? IDBlock.DBLDALLE : IDBlock.ROCHE);
		short dalle = (short)((this.getTailleY()<10 || matiereExt%256==IDBlock.PLANCHE || matiereExt%256==IDBlock.BOIS)? IDBlock.blockData(IDBlock.DALLEBOIS,1) : IDBlock.DALLE);
		int h = getHauteur()-1;
		for(int x=0;x<this.getTailleX();x++){
			for(int z=0;z<this.getTailleZ();z++){
				if(this.getBlock(x, z, h)==0){
					this.setBlock(x, z, h, matiere);
				}
				else this.setBlock(x, z, h, dalle);
			}
		}
		for(int x=0;x<this.getTailleX();x++){
			remplirZone(x,0,h,(short)0,matiere);
			remplirZone(x,this.getTailleZ()-1,h,(short)0,matiere);
		}
		for(int z=0;z<this.getTailleZ();z++){
			remplirZone(0,z,h,(short)0,matiere);
			remplirZone(this.getTailleX()-1,z,h,(short)0,matiere);
		}	
	}
	/**
	 * Remplir récursivement une zone à partir d'un point.
	 * @param x coordonnée en x du point de départ.
	 * @param z coordonnée en z du point de départ.
	 * @param y coordonnée en y du point de départ.
	 * @param matiereEntrante la matière qui remplit ou remplace.
	 * @param matiereSortante la matière qui est remplacée.
	 */
	private void remplirZone(int x, int z, int y, short matiereEntrante, short matiereSortante){
		if(x>=0 && y>=0 && z>=0 && x<this.getTailleX()
				&& y<this.getTailleY() && z<this.getTailleZ()
				&& this.getBlock(x, z, y)==matiereSortante){
			this.setBlock(x, z, y, matiereEntrante);
			remplirZone(x+1,z,y,matiereEntrante,matiereSortante);
			remplirZone(x,z+1,y,matiereEntrante,matiereSortante);
			remplirZone(x-1,z,y,matiereEntrante,matiereSortante);
			remplirZone(x,z-1,y,matiereEntrante,matiereSortante);
		}
	}
	/**
	 * Retourne le nombre de Locations.
	 * @return le nombre de Locations.
	 */
	public int getSize(){
		return points.size();
	}
	/**
	 * Retourne la hauteur du batiment.
	 * @return la hauteur du batiment
	 */
	public int getHauteur(){
		return this.hauteur;
	}
	/**
	 * Retourne l'indice du premier point de la façade.
	 * @return l'indice du premier point de la façade.
	 */
	protected int getIndexFacade() {
		return indexFacade;
	}
	

}

package openstreetcraft.buildingGenerator;

import java.util.Vector;
import openstreetcraft.Location;
//import map.Map;

public class Batiment extends Structure{
	
	private Vector<Location> points;
	private int hauteur;
	private short matiereExt;
	private short matiereInt;

	//Construction du batiment en pierre taillée et bois à partir des points
	public Batiment(Vector<Location> points) {
		super();
		this.points = points;
		this.matiereExt=98;
		this.matiereInt=5;
		Location d=Constructeur.pointMin(points), f=Constructeur.pointMax(points);
		this.hauteur = 4 + (int)(((f.getX()-d.getX()+1)*(f.getZ()-d.getZ()+1)/25) *(double)(Math.random()+1));
		this.setTaille(f.getX()-d.getX()+1,getHauteur(),f.getZ()-d.getZ()+1);
		this.dessinerStructure();
	}
	
	//Construction du batiment à partir des points et des matériaux Extérieur et Intérieurs en paramètres
	public Batiment(Vector<Location> points, short matiereExt, short matiereInt,int hauteurMin) {
		super();
		this.points = points;
		this.matiereExt=matiereExt;
		this.matiereInt=matiereInt;
		Location d=Constructeur.pointMin(points), f=Constructeur.pointMax(points);
		this.hauteur = (int)(hauteurMin*(double)(Math.random()+1));
		this.setTaille(f.getX()-d.getX()+1,getHauteur(),f.getZ()-d.getZ()+1);
		this.dessinerStructure();
	}
	
	private void dessinerStructure() {
		for(int i=0;i<points.size();i++){
			this.dessinerMur(points.get(i), points.get((i+1)%points.size()), true);	
		}
		this.dessinerSol();
		this.dessinerToit();
	}
	
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
					if (facade && coef == 0 && (curY==1 || curY==2) && curLong==(distance/2)) {
						if(curY==1){this.setBlock(curX, curZ, curY, (short)64);}
						else if(curY==2){this.setBlock(curX, curZ, curY, Constructeur.blockID(64, 8));}
					} else if (curLong < (distance - 1) && (curLong % 3) == 2
							&& curY < (this.getTailleY() - 1) && (curY % 5) > 1 && (curY % 5) < 4) {
						if (coef == 0) {
							this.setBlock(curX, curZ, curY, (short) 102);
						} else {
							this.setBlock(curX, curZ, curY, (short) 20);
						}
					} else {
						this.setBlock(curX, curZ, curY, this.matiereExt);
					}
				}
			}
		}
	}
	
	
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
	
	private void dessinerToit(){
		short matiere = (short)((this.getTailleY()<10 || matiereExt%256==5 || matiereExt%256==17)? 256+5 : (getHauteur()<24)? 43 : 1);
		short dalle = (short)((this.getTailleY()<10 || matiereExt%256==5 || matiereExt%256==17)? (256+126) : 44);
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

	public int getSize(){
		return points.size();
	}
	
	public int getHauteur(){
		return this.hauteur;
	}

}

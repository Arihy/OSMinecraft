package openstreetcraft.buildingGenerator;

import java.util.Vector;
import openstreetcraft.Location;
import map.Map;

public class Batiment {
	
	private Vector<Location> points;
	private Structure struct;
	private int hauteur;
	private short matiereExt;
	private short matiereInt;

	//Construction du batiment en pierre taill�e et bois � partir des points
	public Batiment(Vector<Location> points) {
		this.points = points;
		this.matiereExt=98;
		this.matiereInt=5;
		Location d=this.pointDepart(), f=this.pointFinal();
		this.hauteur = 4 + (int)(((f.getX()-d.getX()+1)*(f.getZ()-d.getZ()+1)/25) *(double)(Math.random()+1));
		System.out.println(this.hauteur);
		struct = new Structure(f.getX()-d.getX()+1,getHauteur(),f.getZ()-d.getZ()+1);
		this.dessinerStructure();
	}
	
	//Construction du batiment � partir des points et des mat�riaux Ext�rieur et Int�rieurs en param�tres
	public Batiment(Vector<Location> points, short matiereExt, short matiereInt) {
		this.points = points;
		this.matiereExt=matiereExt;
		this.matiereInt=matiereInt;
		Location d=this.pointDepart(), f=this.pointFinal();
		this.hauteur = 4 + (int)(((f.getX()-d.getX()+1)*(f.getZ()-d.getZ()+1)/25)*(double)(Math.random()+1));
		struct = new Structure(f.getX()-d.getX()+1,getHauteur(),f.getZ()-d.getZ()+1);
		this.dessinerStructure();
	}
	
	private void dessinerStructure() {
		for(int i=0;i<points.size();i++){
			this.dessinerMur(points.get(i), points.get((i+1)%points.size()), true);	
		}
		this.dessinerSol();
		this.dessinerToit();
	}
	
	private void dessinerMur(Location pDep, Location pFin, boolean placerPorte){
		Location d=this.pointDepart(), p1, p2;
		double coef;
		int Larg, Long, curX, curY, curZ, distance, curLong, longueur;
		int distX=Math.abs(pFin.getX()-pDep.getX())+1, distZ=Math.abs(pFin.getZ()-pDep.getZ())+1;
		boolean longZ=(distZ>distX);
		
		if( longZ ){
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
				if(longZ){
					curX = p1.getX() + Larg - d.getX();
					curZ = p1.getZ() + Long - d.getZ();
					distance=struct.getTailleZ();curLong=curZ;
				}else{
					curX = p1.getX() + Long - d.getX();
					curZ = p1.getZ() + Larg - d.getZ();
					distance=struct.getTailleX();curLong=curX;
				}
				curY = p1.getY() + y - d.getY();
				if (curX < struct.getTailleX() && curZ < struct.getTailleZ() && curY < struct.getTailleY()) {
					if (placerPorte && coef == 0 && (curY==1 || curY==2) && curLong==(distance/2)) {
						if(curY==1){struct.setBlock(curX, curZ, curY, (short)(0*256+64));}
						else if(curY==2){struct.setBlock(curX, curZ, curY, (short)(8*256+64));}
					} else if (curLong < (distance - 1) && (curLong % 3) == 2
							&& curY < (struct.getTailleY() - 1) && (curY % 5) > 1 && (curY % 5) < 4) {
						if (coef == 0) {
							struct.setBlock(curX, curZ, curY, (short) 102);
						} else {
							struct.setBlock(curX, curZ, curY, (short) 20);
						}
					} else {
						struct.setBlock(curX, curZ, curY, this.matiereExt);
					}
				}
			}
		}
	}
	
	
	private void dessinerSol(){
		for(int x=0;x<struct.getTailleX();x++){
			for(int z=0;z<struct.getTailleZ();z++){
				if(struct.getBlock(x, z, 0)==0){
					struct.setBlock(x, z, 0, this.matiereInt);
				}
			}
		}
		for(int x=0;x<struct.getTailleX();x++){
			remplirZone(x,0,0,(short)0,this.matiereInt);
			remplirZone(x,struct.getTailleZ()-1,0,(short)0,this.matiereInt);
		}
		for(int z=0;z<struct.getTailleZ();z++){
			remplirZone(0,z,0,(short)0,this.matiereInt);
			remplirZone(struct.getTailleX()-1,z,0,(short)0,this.matiereInt);
		}			
	}
	
	private void dessinerToit(){
		short matiere = (short)((getHauteur()<8)? 45 : (getHauteur()<16)? 1 : (getHauteur()<24)? 43 : 4);
		int h = getHauteur()-1;
		for(int x=0;x<struct.getTailleX();x++){
			for(int z=0;z<struct.getTailleZ();z++){
				if(struct.getBlock(x, z, h)==0){
					struct.setBlock(x, z, h, matiere);
				}
				else struct.setBlock(x, z, h, (short)44);
			}
		}
		for(int x=0;x<struct.getTailleX();x++){
			remplirZone(x,0,h,(short)0,matiere);
			remplirZone(x,struct.getTailleZ()-1,h,(short)0,matiere);
		}
		for(int z=0;z<struct.getTailleZ();z++){
			remplirZone(0,z,h,(short)0,matiere);
			remplirZone(struct.getTailleX()-1,z,h,(short)0,matiere);
		}	
	}
	
	private void remplirZone(int x, int z, int y, short matiereEntrante, short matiereSortante){
		if(x>=0 && y>=0 && z>=0 && x<struct.getTailleX()
				&& y<struct.getTailleY() && z<struct.getTailleZ()
				&& struct.getBlock(x, z, y)==matiereSortante){
			struct.setBlock(x, z, y, matiereEntrante);
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
	
	public Location pointDepart(){
		Location point;
		int X,Y,Z,n;
		
		X=points.firstElement().getX();
		Y=points.firstElement().getY();
		Z=points.firstElement().getZ();
		for(n=1;n<this.getSize();n++){
			if(points.get(n).getX()<X)
				X=points.get(n).getX();
			if(points.get(n).getY()<Y)
				Y=points.get(n).getY();
			if(points.get(n).getZ()<Z)
				Z=points.get(n).getZ();
		}
		point = new Location(X,Y,Z);
		
		return point;
	}
	
	public Location pointFinal(){
		Location point;
		int X,Y,Z,n;
		
		X=points.firstElement().getX();
		Y=points.firstElement().getY();
		Z=points.firstElement().getZ();
		for(n=1;n<this.getSize();n++){
			if(points.get(n).getX()>X)
				X=points.get(n).getX();
			if(points.get(n).getY()>Y)
				Y=points.get(n).getY();
			if(points.get(n).getZ()>Z)
				Z=points.get(n).getZ();
		}
		point = new Location(X,Y,Z);
		
		return point;
	}
	
	public void construire(Map monde){
		Location l = this.pointDepart();
		this.struct.place(monde, l.getX(), l.getZ(), l.getY(),false);
	}

}

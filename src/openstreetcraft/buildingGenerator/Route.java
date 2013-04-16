package openstreetcraft.buildingGenerator;

import java.util.Vector;

import openstreetcraft.Location;
import map.Map;
import map.exceptions.BadStateException;


public class Route {
	
	private Vector<Location> points;
	private short matiere;
	private Structure struct;
	private int epaisseur;
	
	public Route(Vector<Location> points,short mat,int ep){
		epaisseur=ep;
		this.points = points;
		Location d=this.pointDepart(), f=this.pointFinal();
		struct = new Structure(f.getX()-d.getX()+1+2*(epaisseur-1),1,f.getZ()-d.getZ()+1+2*(epaisseur-1));
		matiere=mat;
	}
	
	private void traceRouteDroite(Location pDep,Location pFin, Map map) throws BadStateException
	{
		Location d=this.pointDepart(), p1, p2;
		double coef;
		int x, z, curX, curZ, distX=Math.abs(pFin.getX()-pDep.getX())+1, distZ=Math.abs(pFin.getZ()-pDep.getZ())+1;
		
		if( distZ > distX ){
			if(pDep.getZ()<pFin.getZ()){p1=pDep;p2=pFin;}
			else{p2=pDep;p1=pFin;}
			
			coef=(double)(p2.getX()-p1.getX())/(double)(distZ);
			for(z=0;z<distZ;z++){
				x=(int)(coef*z);if(coef>0)x++;
				curX=p1.getX()+x-d.getX(); curZ=p1.getZ()+z-d.getZ();
				createRoute(curX+epaisseur-1,curZ+epaisseur-1,epaisseur,map);
			}
		}
		else{
			if(pDep.getX()<pFin.getX()){p1=pDep;p2=pFin;}
			else{p2=pDep;p1=pFin;}
			
			coef=(double)(p2.getZ()-p1.getZ())/(double)(distX);
			for(x=0;x<distX;x++){
				z=(int)(coef*x);if(coef>0)z++;
				curX=p1.getX()+x-d.getX(); curZ=p1.getZ()+z-d.getZ();
				createRoute(curX+epaisseur-1,curZ+epaisseur-1,epaisseur,map);
			}
		}
	}
	
	public void createRoute(int x,int z,int ep,Map map){
		try{
		struct.setBlock(x, z, 0, this.matiere);
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		if(ep>1){
			createRoute(x+1,z,ep-1,map);
			createRoute(x-1,z,ep-1,map);
			createRoute(x,z+1,ep-1,map);
			createRoute(x+1,z-1,ep-1,map);
		}
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
	
	public int getSize(){
		return points.size();
	}
		
	
	public void construire(Map monde){
		for(int i=0;i<points.size()-1;i++){
			try {
				traceRouteDroite(points.get(i),points.get(i+1),monde);
			} catch (BadStateException e) {
				e.printStackTrace();
			}
		}
		Location l = this.pointDepart();
		this.struct.place(monde, l.getX()-epaisseur+1, l.getZ()-epaisseur+1, l.getY(),false);
	}
}

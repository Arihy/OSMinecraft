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
	private int lgEspace=0;
	private int lgTrait=0;
	
	/**
	 * Constructeur de la classe Route.
	 * @param points la liste des points de la route.
	 * @param mat la matière de la route.
	 * @param ep l'épaisseur de la route.
	 */
	public Route(Vector<Location> points,short mat,int ep){
		epaisseur=ep;
		this.points = points;
		Location d=Constructeur.pointMin(this.points), f=Constructeur.pointMax(this.points);
		struct = new Structure(f.getX()-d.getX()+1+2*(epaisseur-1),1,f.getZ()-d.getZ()+1+2*(epaisseur-1));
		matiere=mat;
	}
	/**
	 * Constructeur de la classe Route en pointillé
	 * @param points la liste des points de la route.
	 * @param mat la matière de la route.
	 * @param ep l'épaisseur de la route.
	 * @param lgTrait longueur d'un pointillé.
	 * @param lgEspace longueur d'un espace entre deux pointillés.
	 */
	public Route(Vector<Location> points,short mat,int ep,int lgTrait,int lgEspace){
		epaisseur=ep;
		this.points = points;
		this.lgEspace = lgEspace;
		this.lgTrait = lgTrait;
		Location d=Constructeur.pointMin(this.points), f=Constructeur.pointMax(this.points);
		struct = new Structure(f.getX()-d.getX()+1+2*(epaisseur-1),1,f.getZ()-d.getZ()+1+2*(epaisseur-1));
		matiere=mat;
	}
	/**
	 * Construit une route droite entre deux Location
	 * @param pDep la Location de départ.
	 * @param pFin la Location d'arrivée.
	 * @param map la Map de construction.
	 * @throws BadStateException si Map non initialisée.
	 */
	private void traceRouteDroite(Location pDep,Location pFin, Map map) throws BadStateException
	{
		Location d=Constructeur.pointMin(this.points), p1, p2;
		double coef;
		int x, z, curX, curZ, distX=Math.abs(pFin.getX()-pDep.getX())+1, distZ=Math.abs(pFin.getZ()-pDep.getZ())+1;
		
		if( distZ > distX ){
			if(pDep.getZ()<pFin.getZ()){p1=pDep;p2=pFin;}
			else{p2=pDep;p1=pFin;}
			
			coef=(double)(p2.getX()-p1.getX())/(double)(distZ);
			for(z=0;z<distZ;z++){
				if((lgEspace==0&&lgTrait==0)||(lgTrait>z%(lgEspace+lgTrait))){
					x=(int)(coef*z);if(coef>0)x++;
					curX=p1.getX()+x-d.getX(); curZ=p1.getZ()+z-d.getZ();
					createRoute(curX+epaisseur-1,curZ+epaisseur-1,epaisseur,map);
				}
			}
		}
		else{
			if(pDep.getX()<pFin.getX()){p1=pDep;p2=pFin;}
			else{p2=pDep;p1=pFin;}
			
			coef=(double)(p2.getZ()-p1.getZ())/(double)(distX);
			for(x=0;x<distX;x++){
				if((lgEspace==0&&lgTrait==0)||(lgTrait>x%(lgEspace+lgTrait))){
					z=(int)(coef*x);if(coef>0)z++;
					curX=p1.getX()+x-d.getX(); curZ=p1.getZ()+z-d.getZ();
					createRoute(curX+epaisseur-1,curZ+epaisseur-1,epaisseur,map);
				}		
			}
		}
	}
	/**
	 * Construit un morceau de route sur toute l'épaisseur.
	 * @param x coordonnée en x.
	 * @param z coordonnée en z.
	 * @param ep épaisseur de la route.
	 * @param map la Map sur laquelle se construit la route.
	 */
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
	/**
	 * Construit entièrement la route.
	 * @param monde la Map où construire la route.
	 */
	public void construire(Map monde){
		for(int i=0;i<points.size()-1;i++){
			try {
				traceRouteDroite(points.get(i),points.get(i+1),monde);
			} catch (BadStateException e) {
				e.printStackTrace();
			}
		}
		Location l=Constructeur.pointMin(this.points);
		this.struct.place(monde, l.getX()-epaisseur+1, l.getZ()-epaisseur+1, l.getY(),false);
	}
	
}

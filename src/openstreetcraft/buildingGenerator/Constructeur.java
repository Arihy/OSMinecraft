package openstreetcraft.buildingGenerator;

import java.util.Vector;
import openstreetcraft.Location;
import openstreetcraft.IDBlock;
import openstreetcraft.RGBColor;
import openstreetcraft.buildingGenerator.BatimentsTypes.Eglise;
import openstreetcraft.buildingGenerator.BatimentsTypes.Hopital;
import map.Map;
import map.exceptions.BadStateException;

public abstract class Constructeur {
	
	/**
	 * Construire un batiment à partir d'un tag et d'une liste de points.
	 * Ne construit rien mais faire appel à la classe correspondant au tag.
	 * @param value la valeur du tag.
	 * @param points la liste de points du contour du batiment.
	 * @param monde la Map où construire le batiment.
	 * @param indexFacade l'indice du premier point de la façade du batiment.
	 * @return une couleur pour l'affichage.
	 */
	public static RGBColor construireBatiment(String value, Vector<Location> points, Map monde, int indexFacade){
		
		Structure struct;
		Location position = pointMin(points);
		boolean creuser = false;
		RGBColor color = new RGBColor(128,128,128);
		
		if(value.equals("house")){
			struct = new Batiment(points,indexFacade,IDBlock.BRIQUE,IDBlock.blockData(IDBlock.PLANCHE, 2),4);
			color.setRGB(155, 86, 67);
		}
		else if(value.equals("terrace")){
			struct = new Batiment(points,indexFacade,IDBlock.blockData(IDBlock.GRES, 2),IDBlock.blockData(IDBlock.LAINE, 4),4);
			color.setRGB(221, 212, 162);
		}
		else if(value.equals("apartments")){
			struct = new Batiment(points,indexFacade,IDBlock.NETHERBRIQUE,IDBlock.blockData(IDBlock.LAINE, 12),20);
			color.setRGB(56, 26, 31);
		}
		else if(value.equals("hotel") || value.equals("dormitory")){
			struct = new Batiment(points,indexFacade,IDBlock.ROCHE,IDBlock.LAINE,15);
		}
		else if(value.equals("industrial")){
			struct = new Batiment(points,indexFacade,IDBlock.FER,IDBlock.PIERRE,10);
			color.setRGB(230, 230, 230);
		}
		else if(value.equals("commercial")){
			struct = new Batiment(points,indexFacade,IDBlock.BOIS,IDBlock.PLANCHE,10);
			color.setRGB(100, 80, 50);
		}
		else if(value.equals("hospital")){
			struct = new Hopital(points,indexFacade);
			color.setRGB(255, 255, 255);
		}
		else if(value.equals("church")){
			struct = new Eglise(points,indexFacade,15);
		}
		else if(value.equals("chapel")){
			struct = new Eglise(points,indexFacade,10);
		}
		else if(value.equals("cathedral")){
			struct = new Eglise(points,indexFacade,20);
		}
		else{
			struct = new Batiment(points,indexFacade);
		}
		
		construireTrottoir(position,pointMax(points),monde);
		struct.place(monde,position,creuser);
		return color;
		
	}
	/**
	 * Appelle les constructeurs de la route avec les matériaux requis.
	 * @param points la suite de points de la route.
	 * @param monde la Map sur laquelle se construit la route.
	 */
	public static void construireRoute(Vector<Location> points, Map monde){
		Route r = new Route(points,IDBlock.OBSIDIENNE,3);
		Route l = new Route(points,IDBlock.FER,1,3,2);
		r.construire(monde);
		l.construire(monde);
	}
	/**
	 * Construit un trottoir carré autour du batiment.
	 * @param depart le point minimal du batiment.
	 * @param fin le point maximal du batiment.
	 * @param monde la Map où se construit le trottoir.
	 */
	public static void construireTrottoir(Location depart, Location fin, Map monde) {
		int y=depart.getY();
		int depassement = 5;
		
		for(int x=depart.getX()-depassement;x<fin.getX()+depassement;x++){
			for(int z=depart.getZ()-depassement;z<fin.getZ()+depassement;z++){
				short matiere;
				try {
					matiere = monde.getBlock(x, z, y);
					if(matiere==0 || matiere==IDBlock.HERBE || matiere==IDBlock.TERRE){
						mettreNiveau(new Location(x,y,z),monde);
						monde.setBlock(x, z, y, IDBlock.TAILLEE);
					}
				} catch (BadStateException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * Réajuste le sol au niveau d'un bloc.
	 * Utile combler le trottoir en cas de Perlin incohérent.
	 * @param point l'endroit où il faut réajuster.
	 * @param monde la Map dont le sol doit être réajusté.
	 */
	public static void mettreNiveau(Location point, Map monde){
		try {
			int x=point.getX(), y=point.getY(), z=point.getZ();
			while(monde.getBlock(x, z, y)==0){
				monde.setBlock(x, z, y, IDBlock.TERRE);
				y--;
			}
			y=point.getY()+1;
			while(monde.getBlock(x, z, y)==IDBlock.HERBE || monde.getBlock(x, z, y)==IDBlock.TERRE){
				monde.setBlock(x, z, y, (short)0);
				y++;
			}
		} catch (BadStateException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Le point théorique de coordonnées minimales entre les Locations.
	 * @param points la liste des Locations
	 * @return un point ayant les coordonnées les plus basses de la liste.
	 */
	public static Location pointMin(Vector<Location> points){
		Location point;
		int X,Y,Z,n;
		
		X=points.firstElement().getX();
		Y=points.firstElement().getY();
		Z=points.firstElement().getZ();
		for(n=1;n<points.size();n++){
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
	/**
	 * Le point théorique de coordonnées maximales entre les Locations.
	 * @param points la liste des Locations
	 * @return un point ayant les coordonnées les plus hautes de la liste.
	 */
	public static Location pointMax(Vector<Location> points){
		Location point;
		int X,Y,Z,n;
		
		X=points.firstElement().getX();
		Y=points.firstElement().getY();
		Z=points.firstElement().getZ();
		for(n=1;n<points.size();n++){
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

}

package openstreetcraft.buildingGenerator;

import java.util.Vector;
import openstreetcraft.Location;
import openstreetcraft.buildingGenerator.BatimentsTypes.Eglise;
import openstreetcraft.buildingGenerator.BatimentsTypes.Hopital;
import map.Map;
import map.exceptions.BadStateException;

public abstract class Constructeur {
	
	public static RGBColor construireBatiment(String value, Vector<Location> points, Map monde, int indexFacade){
		
		Structure struct;
		Location position = pointMin(points);
		boolean creuser = false;
		RGBColor color = new RGBColor(128,128,128);
		
		if(value=="house"){
			struct = new Batiment(points,indexFacade,(short)45,Constructeur.blockID(5, 2),4);
			color.setRGB(155, 86, 67);
		}
		else if(value=="terrace"){
			struct = new Batiment(points,indexFacade,Constructeur.blockID(24, 2),Constructeur.blockID(35, 4),4);
			color.setRGB(221, 212, 162);
		}
		else if(value=="apartments"){
			struct = new Batiment(points,indexFacade,(short)112,blockID(35, 12),20);
			color.setRGB(56, 26, 31);
		}
		else if(value=="hotel" || value=="dormitory"){
			struct = new Batiment(points,indexFacade,(short)1,(short)35,15);
		}
		else if(value=="industrial"){
			struct = new Batiment(points,indexFacade,(short)42,(short)4,10);
			color.setRGB(230, 230, 230);
		}
		else if(value=="commercial"){
			struct = new Batiment(points,indexFacade,(short)17,(short)5,10);
			color.setRGB(100, 80, 50);
		}
		else if(value=="hospital"){
			struct = new Hopital(points,indexFacade);
			color.setRGB(255, 255, 255);
		}
		else if(value=="church"){
			struct = new Eglise(points,indexFacade,15);
		}
		else if(value=="chapel"){
			struct = new Eglise(points,indexFacade,10);
		}
		else if(value=="cathedral"){
			struct = new Eglise(points,indexFacade,20);
		}
		else{
			struct = new Batiment(points,indexFacade);
		}
		
		construireTrottoir(position,pointMax(points),monde);
		struct.place(monde,position,creuser);
		return color;
		
	}
	
	public static void construireTrottoir(Location depart, Location fin, Map monde) {
		int y=depart.getY();
		
		for(int x=depart.getX()-5;x<fin.getX()+5;x++){
			for(int z=depart.getZ()-5;z<fin.getZ()+5;z++){
				short matiere;
				try {
					matiere = monde.getBlock(x, z, y);
					if(matiere==0 || matiere==2 || matiere==3){
						mettreNiveau(new Location(x,y,z),monde);
						monde.setBlock(x, z, y, (short)98);
					}
				} catch (BadStateException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void mettreNiveau(Location point, Map monde){
		try {
			int x=point.getX(), y=point.getY(), z=point.getZ();
			while(monde.getBlock(x, z, y)==0){
				monde.setBlock(x, z, y, (short)3);
				y--;
			}
			y=point.getY()+1;
			while(monde.getBlock(x, z, y)==2 || monde.getBlock(x, z, y)==3){
				monde.setBlock(x, z, y, (short)0);
				y++;
			}
		} catch (BadStateException e) {
			e.printStackTrace();
		}
	}

	public static short blockID(int ID, int data){
		return (short)((ID%256)+256*(data%256));
	}
	
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

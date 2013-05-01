package openstreetcraft.buildingGenerator;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import openstreetcraft.Location;
import openstreetcraft.buildingGenerator.BatimentsTypes.Eglise;
import openstreetcraft.buildingGenerator.BatimentsTypes.Hopital;
import map.Map;

public abstract class Constructeur {
	
	public static void construireBatiment(String value, Vector<Location> points, Map monde){
		
		Structure struct;
		Location position = pointMin(points);
		boolean creuser = false;
		
		if(value=="house"){
			struct = new Batiment(points,(short)45,Constructeur.blockID(5, 2),4);
		}
		else if(value=="terrace"){
			try {
				struct = new Structure("Structures"+File.separator+"maison"+".struct");
			} catch (IOException e) {
				struct = new Structure();
			}
		}
		else if(value=="apartments"){
			struct = new Batiment(points,(short)112,blockID(35, 12),20);
		}
		else if(value=="hotel" || value=="dormitory"){
			struct = new Batiment(points,(short)1,(short)35,15);
		}
		else if(value=="industrial"){
			struct = new Batiment(points,(short)42,(short)4,10);
		}
		else if(value=="commercial"){
			struct = new Batiment(points,(short)17,(short)5,10);
		}
		else if(value=="hospital"){
			struct = new Hopital(points);
		}
		else if(value=="church"){
			struct = new Eglise(points,15);
		}
		else if(value=="chapel"){
			struct = new Eglise(points,10);
		}
		else if(value=="cathedral"){
			struct = new Eglise(points,20);
		}
		else{
			struct = new Batiment(points);
		}
		
		struct.place(monde,position,creuser);
		
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

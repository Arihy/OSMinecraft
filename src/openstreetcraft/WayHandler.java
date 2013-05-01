package openstreetcraft;

import java.util.Hashtable;
import java.util.Vector;

import map.Map;

//import openstreetcraft.buildingGenerator.Batiment;
import openstreetcraft.buildingGenerator.Constructeur;
import openstreetcraft.buildingGenerator.Route;

public class WayHandler {
	
	private Hashtable<String,String> tags;
	private Vector<Location> locations;
	
	public WayHandler() {
		tags = new Hashtable<String,String>();
		locations = new Vector<Location>();
	}
	
	public void addTag(String key, String value){
		tags.put(key, value);
	}
	
	public void addLocation(Location loc){
		locations.add(loc);
	}
	
	public void generateWay(Map monde){
		if(tags.containsKey("building")){
			Constructeur.construireBatiment("NoValue", locations, monde);
			System.out.println("Bâtiment généré");
		}else if(tags.containsKey("highway")){
			Route r = new Route(locations,(short)12,3);
			System.out.println("Route générée");
			r.construire(monde);
		}else{
			System.out.println("Structure non connue");
		}
	}
	
}

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
			if(tags.containsKey("amenity")){
				Constructeur.construireBatiment(tags.get("amenity"), locations, monde, 0);
			}else{
				Constructeur.construireBatiment(tags.get("building"), locations, monde, 0);
			}
			System.out.println("Batiment genere");
		}else if(tags.containsKey("highway")){
			Constructeur.construireRoute(locations,monde);
			System.out.println("Route genere");
		}else if(tags.containsKey("waterway")){
			Route r = new Route(locations,(short)8,4);
			System.out.println("Cours d'eau genere");
			r.construire(monde);
		}else{
			System.out.println("Structure non connue");
		}
	}
	
	public void prepareMap(Map monde){
		
	}
	
}

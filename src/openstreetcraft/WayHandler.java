package openstreetcraft;

import java.util.Hashtable;
import java.util.Vector;

import map.Map;

//import openstreetcraft.buildingGenerator.Batiment;
import openstreetcraft.buildingGenerator.Constructeur;

public class WayHandler {
	
	private Hashtable<String,String> tags;
	private Vector<Location> locations;
	
	/**
	 * Initialise sans param�tre.
	 */
	public WayHandler() {
		tags = new Hashtable<String,String>();
		locations = new Vector<Location>();
	}
	/**
	 * Ajoute un tag � la collection.
	 * @param key la cl� du tag � ajouter.
	 * @param value la valeur du tag � ajouter.
	 */
	public void addTag(String key, String value){
		tags.put(key, value);
	}
	/**
	 * Ajoute une Location � la liste.
	 * @param loc la Location � ajouter.
	 */
	public void addLocation(Location loc){
		locations.add(loc);
	}
	/**
	 * G�n�re b�timents et routes.
	 * @param monde la Map o� l'objet sera g�n�r�.
	 */
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
			Constructeur.construireEau(locations,monde);
			System.out.println("Cours d'eau genere");
		}else{
			System.out.println("Structure non connue");
		}
	}
	
	public void prepareMap(Map monde){
		
	}
	
}

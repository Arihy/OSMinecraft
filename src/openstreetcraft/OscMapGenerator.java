package openstreetcraft;

import java.io.IOException;
import java.util.Hashtable;

import map.Map;
import map.exceptions.BadStateException;

public class OscMapGenerator {
	
	public static int size_x;
	public static int size_z;
	public static int size_y;
	private static double minlat;
	private static double minlon; 
	private static double maxlat;
	private static double maxlon; 
	public static int nvZoom=100000;
	public static Map map;
	private static Hashtable<String,Location> nodes;
	public static WayHandler way;
	
	
	public static void createMap(double minLat,double minLon,double maxLat,double maxLon){
		map = new Map();
		minlat = minLat;
		minlon = minLon;
		maxlat = maxLat;
		maxlon = maxLon;
		System.out.println("Taille de la carte : "+getDist(minlon,maxlon)+"x"+getDist(minlat,maxlat));
		map.create("SerializedWorld0", getDist(minlon,maxlon), getDist(minlat,maxlat), 128);
		nodes=new Hashtable<String,Location>();
	}
	
	public static int getDist(double minCoord,double maxCoord){
		return (int) Math.floor(((maxCoord-minCoord)*nvZoom));
	}
	
	public static Location getLocation(double lon, double lat){
		Location l = new Location(getDist(minlon,lon),0,getDist(minlat,lat));
		return l;
	}
	
	public static void addNode(String key, Location value){
		nodes.put(key, value);
		System.out.println("Noeud ajouté.");
	}
	
	public static Location getNodeLocation(String key){
		return nodes.get(key);
	}
	
	public static void createNewWay(){
		way = new WayHandler();
	}
	
	public static void saveMap(){
		try {
			map.save();
		} catch (BadStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	//----------------------------------------------------------
	
	public static void main(String[] args)
	{
		
            try {
                @SuppressWarnings("unused")
				OscSaxParser parser = new OscSaxParser("map.osm");
            } catch (Throwable t) {
                t.printStackTrace();
            }
	}
	
}

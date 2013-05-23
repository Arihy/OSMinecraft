package openstreetcraft;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import generators.Perlin;
import map.Map;
import map.exceptions.BadStateException;

public class OscMapGenerator {
	
	public static int size_x;
	public static int size_z;
	public static int size_y;
	private static int hauteurSol;
	private static double minlat;
	private static double minlon; 
	private static double maxlat;
	private static double maxlon; 
	public static int nvZoom=110000;
	public static Map map;
	private static Hashtable<String,Location> nodes;
	public static WayHandler way=null;
	public static Vector<WayHandler> ways;
	public static String[][] map2d;
	
	
	public static void createMap(double minLat,double minLon,double maxLat,double maxLon){
		map = new Map();
		hauteurSol=64;
		minlat = minLat;
		minlon = minLon;
		maxlat = maxLat;
		maxlon = maxLon;
		size_x =  getDist(minlon,maxlon);
		size_z = getDist(minlat,maxlat);
		size_y =256;
		System.out.println("Taille de la carte : "+getDist(minlon,maxlon)+"x"+getDist(minlat,maxlat));
		map.create("SerializedWorld0", size_x,size_z,size_y);
		map2d = new String[size_x][size_z];
		nodes=new Hashtable<String,Location>();
		ways=new Vector<WayHandler>();
		for (int i=0;i<size_x;i++){
			for(int j=0;j<size_z;j++){
				map2d[i][j]="";
			}
		}
		Perlin.appliquePerlin(map, 0);
	}
	
	public static int getDist(double minCoord,double maxCoord){
		return (int) Math.floor(((maxCoord-minCoord)*nvZoom));
	}
	
	public static Location getLocation(double lon, double lat){
		Location l=null;
		try {
			l = new Location(getDist(minlon,lon),hauteurSol,getDist(lat,minlat)+map.getSize()[1]);
		} catch (BadStateException e) {
			e.printStackTrace();
		}
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
	
	public static void finishWay(){
		ways.add(way);
		way=null;
	}
	
	public static void generateWays(){
		for(WayHandler w : ways){
			w.generateWay(map);
		}
	}
	
	public static void saveMap(){
		try {
			map.setSpawn(0, 0, 120);
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

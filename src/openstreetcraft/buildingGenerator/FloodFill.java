package openstreetcraft.buildingGenerator;

import map.Map;
import map.exceptions.BadStateException;
import openstreetcraft.Location;

public class FloodFill {
	public static void fill(Location bStart, short bTarget, short bType, Map map) throws BadStateException
	{
		if(bStart.getX() >= 0 && bStart.getZ() >= 0 && bStart.getY() >= 0 
				&& bStart.getX() < map.getSize()[0] && bStart.getZ() < map.getSize()[1] && bStart.getY() < map.getSize()[2]
						&& map.getBlock(bStart.getX(), bStart.getZ(), bStart.getY()) == bTarget)
		{
			map.setBlock(bStart.getX(), bStart.getZ(), bStart.getY(), bType);
			
			Location bNorth = new Location(bStart.getX(), bStart.getY(), bStart.getZ()+1);
			//System.out.println("X   "+bStart.getX()+"   Z   "+(bStart.getZ()+1));
			//if(map.getBlock(bNorth.getX(), bNorth.getZ(), bNorth.getY()) != 0)
				fill(bNorth, bTarget, bType, map); //err
			
			Location bEast = new Location(bStart.getX()+1, bStart.getY(), bStart.getZ());
			//System.out.println("X   "+(bStart.getX()+1)+"   Z   "+bStart.getZ());
			//if(map.getBlock(bEast.getX(), bEast.getZ(), bEast.getY()) != 0)
				fill(bEast, bTarget, bType, map);
			
			Location bSouth = new Location(bStart.getX(), bStart.getY(), bStart.getZ()-1);
			//System.out.println("X   "+bStart.getX()+"   Z   "+(bStart.getZ()-1));
			//if(map.getBlock(bSouth.getX(), bSouth.getZ(), bSouth.getY()) != 0)
				fill(bSouth, bTarget, bType, map); //err
			
			Location bWest = new Location(bStart.getX()-1, bStart.getY(), bStart.getZ());
			//System.out.println("X   "+(bStart.getX()-1)+"   Z   "+bStart.getZ());
			//if(map.getBlock(bWest.getX(), bWest.getZ(), bWest.getY()) != 0)
				fill(bWest, bTarget, bType, map);
		}
	}

}

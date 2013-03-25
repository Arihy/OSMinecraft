package openstreetcraft.buildingGenerator;

import map.Map;
import map.exceptions.BadStateException;


public class RoadGenerator {
	public static void traceRouteDroite(int x1, int z1, int x2, int z2, int blockY, Map map, int sizeX, int sizeZ) throws BadStateException
	{
		int x, y;
		double a, b;
		
		a = (double) (z2 - z1) / (x2 - x1);
		b = z1 - a * x1;
		
		for(x = x1; x <= x2; x++)
		{
			y = (int) (a * x + b);
			//tester si la valeur du block voisin n'est pas pas null (ou air)
			//alr placer un block.
			if(map.getBlock(x, y-2, blockY) != 0)
			{
				map.setBlock(x, y-2, blockY, (short) 35);
			}
			if(map.getBlock(x, y-1, blockY) != 0)
			{
				map.setBlock(x, y-1, blockY, (short) 35);
			}
			
			map.setBlock(x, y, blockY, (short) 49);
			
			if(map.getBlock(x, y+1, blockY) != 0 && y+1 < sizeZ)
			{
				//System.out.println("y = "+(y+1)+" x = "+x);
				map.setBlock(x, y+1, blockY, (short) 35);
			}
			if(map.getBlock(x, y+2, blockY) != 0 && y+2 < sizeZ)
			{
				//System.out.println("y = "+(y+1)+" x = "+x);
				map.setBlock(x, y+2, blockY, (short) 35);
			}
		}
	}
	
	public static void traceRoute(int x1, int z1, int x2, int z2, int blockY, Map map, int sizeX, int sizeZ) throws BadStateException
	{
		traceRouteDroite(x1, z1, ((x1+x2)/2)+3, (z1+z2)/2, blockY, map, sizeX, sizeZ);
		traceRouteDroite(((x1+x2)/2)+3, (z1+z2)/2, x2, z2, blockY, map, sizeX, sizeZ);
	}
}

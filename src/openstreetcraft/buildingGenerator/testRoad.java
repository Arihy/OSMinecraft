package openstreetcraft.buildingGenerator;

import java.io.IOException;


import map.Map;
import map.exceptions.BadStateException;

public class testRoad {
	@SuppressWarnings("unused")
	private static void testCreate() throws BadStateException, IOException
	{
		int sizeX = 50;
		int sizeY = 50;
		int sizeZ = 50;
		Map map = new Map();

		map.create("/home/arihy/Minecraft/serveurmc/plugins/Mondes/SerializedWorld3", sizeX, sizeZ, sizeY);
		for (int i = 0; i < sizeX; i++)
		{
			for (int j = 0; j < sizeZ; j++)
			{
				for (int k = 0; k < 4; k++)
				{
					map.setBlock(i, j, k, (short) 2);
				}
			}
		}
		
		int pointDx = 2, pointDy = 2, pointFx = sizeX-1, pointFy = sizeZ-1;
		
		//RoadGenerator.traceRouteDroite(pointDx, pointDy, pointFx, pointFy, 3, map, sizeX, sizeZ);
		RoadGenerator.traceRoute(pointDx, pointDy, pointFx, pointFy, 3, map, sizeX, sizeZ);
		
		map.setSpawn(0, 0, 70);
		map.save();
		System.out.println("save complete!");
	}
	
	/*public static void main(String[] args)
	{
		try
		{
			testCreate();
		}
		catch (BadStateException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}*/

}

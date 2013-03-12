package roadGenerator;

import map.Map;
import map.exceptions.BadStateException;


public class RoadGenerator {
	public static void traceRoute(int x1, int z1, int x2, int z2, int blockY, Map map) throws BadStateException
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
			map.setBlock(x, y, blockY, (short) 49);
		}
	}
}
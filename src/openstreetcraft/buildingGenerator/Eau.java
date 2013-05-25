package openstreetcraft.buildingGenerator;

import java.util.Vector;

import openstreetcraft.IDBlock;
import openstreetcraft.Location;

public class Eau extends Route{

	public Eau(Vector<Location> points, int ep) {
		super(points, IDBlock.EAU, ep);
	}

}

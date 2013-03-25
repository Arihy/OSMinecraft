package map;

import java.io.Serializable;

public class MapSerial implements Serializable
{
	private static final long serialVersionUID = 1L;
	public short[] blocks;
	public String URL;
	public int[] size;
	public int[] spawn;
}

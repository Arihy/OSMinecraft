package map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import map.exceptions.BadStateException;

public class Map
{
	private MapSerial mMap; // données de la map actuelle

	/**
	 * recupere la taille de la map actuelle sous forme de tableau simple
	 * [0] : Taille sur l'axe X
	 * [1] : Taille sur l'axe Z
	 * [2] : Taille sur l'axe Y
	 * @return un tableau simple de la taille de la map
	 * @throws BadStateException si aucune map n'est chargé
	 */
	public int[] getSize() throws BadStateException
	{
		checkLoaded();
		return mMap.size;
	}
	/**
	 * recupere la position du spawn sous forme de tableau simple
	 * [0] : position X
	 * [1] : position Z
	 * [2] : position Y
	 * @return un tableau simple de la position du spawn
	 * @throws BadStateException
	 */
	public int[] getSpawn() throws BadStateException
	{
		checkLoaded();
		return mMap.spawn;
	}
	/**
	 * recupere l'url associé a la map
	 * @return le path complet de la sauvegarde
	 * @throws BadStateException si aucune map n'est chargé
	 */
	public String getURL() throws BadStateException
	{
		checkLoaded();
		return mMap.URL;
	}
	/**
	 * Creer une nouvelle map
	 * @param mapURL path complet pour la sauvegarde (position+nom+extension)
	 * @param blocksX Nombre de blocks sur l'axe X
	 * @param blocksZ Nombre de blocks sur l'axe Z
	 * @param mapHeight Nombre de blocks en hauteur
	 */
	public void create(String mapURL, int blocksX,int blocksZ, int mapHeight)
	{
		mMap = new MapSerial();
		mMap.size = new int[3];
		mMap.size[0] = blocksX;
		mMap.size[1] = blocksZ;
		mMap.size[2] = mapHeight;
		mMap.URL = mapURL;
		mMap.blocks = new short[blocksX*blocksZ*mapHeight];
		setSpawn(0, 0, 0);
	}
	/**
	 * definit un spawn
	 * @param x Position bloc X
	 * @param z Position bloc Z
	 * @param y Position bloc Y
	 */
	public void setSpawn(int x,int z, int y)
	{
		mMap.spawn = new int[3];
		mMap.spawn[0] = x;
		mMap.spawn[1] = z;
		mMap.spawn[2] = y;
	}
	/**
	 * Sauvegarde la map actuelle par serialisation
	 * @throws BadStateException Si aucune map n'est chargé
	 * @throws IOException si il est impossible d'ecrire sur le path
	 */
	public void save() throws BadStateException, IOException
	{
		checkLoaded();
		File file = new File(mMap.URL);
		if (file.exists())
			file.delete();
		FileOutputStream fos = new FileOutputStream(file); // creation d'un flux de fichier en ecriture
		ObjectOutputStream oos = new ObjectOutputStream(fos); // creation d'un flux d'objet sur le flux de fichier
		oos.writeObject(mMap); // ecriture de l'objet de type MapSerial sur le flux de fichier
		oos.flush(); // forcer l'ecriture des dernieres donné sur le tempon
		oos.close(); // fermeture du flux d'objet
		fos.close(); // fermeture du flux de fichier
	}
	/**
	 * Charge une map depuis un fichier serialisé
	 * @param mapURL Path complet (position+nom+extension) du fichier
	 * @throws IOException si le fichier n'est pas trouvé
	 */
	public void load(String mapURL) throws IOException
	{
		try
		{
			File file = new File(mapURL);
			FileInputStream fichier = new FileInputStream(file); // Creation d'un flux de fichier en lecture
			ObjectInputStream ois = new ObjectInputStream(fichier); // Creation d'un flux d'objet en lecture sur le flux de fichier
			mMap = (MapSerial) ois.readObject(); // Lecture sur le flux d'objet et cast en MapSerial
			ois.close(); // fermeture du flux d'objet
			fichier.close(); // fermeture du flux de fichier
			mMap.URL = mapURL;
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @return vrai si une map est chargé (par load ou par create)
	 */
	public boolean isLoaded()
	{
		return mMap!=null;
	}
	/**
	 * permet de verifier si une map est bien chargé
	 * @throws BadStateException si aucune map n'est chargé
	 */
	private void checkLoaded() throws BadStateException
	{
		if (!isLoaded())
			throw new BadStateException("No map loaded");
	}
	public int getHeight(int x,int z) throws BadStateException
	{
		if (!isLoaded())
			throw new BadStateException("No map loaded");
		for (int i=mMap.size[2]-1;i>=0;i--)
		{
			if (getBlock(x, z, i) != 0)
				return i+1;

		}
		return 0;
	}
	/**
	 * permet de recuperer un bloc dans la map
	 * (le bloc vaut -1 si il est en dehor de la map)
	 * @param blockX Position X du bloc recherché
	 * @param blockZ Position Z du bloc recherché
	 * @param blockY Position Y du bloc recherché
	 * @return la valeur du bloc recherché
	 * @throws BadStateException
	 */
	public short getBlock(int blockX, int blockZ, int blockY) throws BadStateException
	{
		checkLoaded();
		if (blockX < 0 || blockX >= mMap.size[0] || blockZ < 0 || blockZ >= mMap.size[1] || blockY < 0 || blockY >= mMap.size[2])
			return -1;
		else
			return readMap(mMap.blocks, blockX, blockZ, blockY, mMap.size[1], mMap.size[2]);
	}
	/**
	 * permet de recuperer un chunk entier a la position indique
	 * (si un bloc se trouve sur une positon en dehor de la map il aura pour valeur -1)
	 * @param chunkX position X du chunk recherché
	 * @param chunkZ position Z du chunk recherché
	 * @return un tableau d'un chunk encodé sur un tableau
	 * @throws BadStateException si aucune carte n'est chargé
	 */
	public short[] getChunk(int chunkX,int chunkZ) throws BadStateException
	{
		checkLoaded();
		short[] chunk = new short[16*16*mMap.size[2]];
		for (int i=0;i<16;i++)
		{
			for (int j=0;j<16;j++)
			{
				for (int h=0;h<mMap.size[2];h++)
				{
					if ((chunkX*16)+i < 0 || (chunkX*16)+i > mMap.size[0] || (chunkZ*16)+j < 0 || (chunkZ*16)+j > mMap.size[1] || h < 0 || h > mMap.size[2])
						writeChunk(chunk, i, j, h, (short) -1, mMap.size[2]);
					else
						writeChunk(chunk, i, j, h, readMap(mMap.blocks, i, j, h, mMap.size[1], mMap.size[2]), mMap.size[2]);
				}
			}
		}
		return chunk;
	}
	/**
	 * Modifie un bloc dans la map
	 * @param blockX Position X du bloc a modifier
	 * @param blockZ Position Z du bloc a modifier
	 * @param blockY Position Y du bloc a modifier
	 * @param value Nouvelle valeur du bloc
	 * @throws BadStateException si aucune carte n'est chargé
	 */
	public void setBlock(int blockX,int blockZ,int blockY,short value) throws BadStateException
	{
		checkLoaded();
		writeMap(mMap.blocks, blockX, blockZ, blockY, value, mMap.size[1], mMap.size[2]);
	}

	// Fonctions de manipulation

	/**
	 * 
	 * Permet de lire un bloc dans une carte encodé dans un simple tableau
	 * 
	 * @param map le tableau de la carte
	 * @param bx position X du bloc recherché
	 * @param bz position Z du bloc recherché
	 * @param by position Y du bloc recherché
	 * @param nbBlocksZ Nombre de blocks sur l'axe Z
	 * @param nbBlocksY Nombre de blocks sur l'axe Y
	 * @return la valeur du bloc recherché
	 */
	public static short readMap(short[] map,int bx,int bz,int by,int nbBlocksZ,int nbBlocksY)
	{
		return map[(bx*(nbBlocksZ*nbBlocksY))+(bz*nbBlocksY)+by];
	}
	/**
	 *  Permet d'écrire un bloc dans une carte encodé dans un simple tableau
	 * @param map le tableau de la carte
	 * @param bx Position X du bloc a modifier
	 * @param bz Position Z du bloc a modifier
	 * @param by Position Y du bloc a modifier
	 * @param val Nouvelle valeur du bloc
	 * @param nbBlocksZ Nombre de blocks sur l'axe Z
	 * @param nbBlocksY Nombre de blocks sur l'axe Y (hauteur)
	 */
	public static void writeMap(short[] map,int bx,int bz,int by,short val,int nbBlocksZ,int nbBlocksY)
	{
		//position       axe X              axe Z        axeY
		map[(bx*(nbBlocksZ*nbBlocksY)) + (bz*nbBlocksY) + by] = val;
	}
	/**
	 * Permet de lire un block dans un chunk encodé dans un simple tableau
	 * @param chunk Tableau du chunk
	 * @param bx position X du bloc recherché
	 * @param bz position Z du bloc recherché
	 * @param by position Y du bloc recherché
	 * @param nbBlocksY Nombre de blocks sur l'axe Y (hauteur)
	 * @return La valeur du bloc recherché
	 */
	public static short readChunk(short[] chunk,int bx,int bz,int by,int nbBlocksY)
	{
		return chunk[(bx*(16*nbBlocksY))+(bz*nbBlocksY)+by];
	}
	/**
	 * Permet de modifier un bloc dans un chunk encodé dans un tableau simple
	 * @param chunk Tableau du chunk
	 * @param bx Position X du bloc a modifier
	 * @param bz Position Z du bloc a modifier
	 * @param by Position Y du bloc a modifier
	 * @param val Nouvelle valeur du bloc
	 * @param nbBlocksY Nombre de blocks sur l'axe Y (hauteur)
	 */
	public static void writeChunk(short[] chunk,int bx,int bz,int by,short val,int nbBlocksY)
	{
		chunk[(bx*(16*nbBlocksY))+(bz*nbBlocksY)+by] = val;
	}
}
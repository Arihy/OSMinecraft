package openstreetcraft.buildingGenerator;

import java.io.FileInputStream;
import java.io.IOException;

import openstreetcraft.Location;
import map.Map;
import map.exceptions.BadStateException;

public class Structure {
	
	protected int[] taille;
	protected short[][][] blocks;
	
	/**
	 * Initialisation Structure de taille nulle.
	 */
	public Structure(){
		this.taille = new int[3];
		this.setTailleX(0);
		this.setTailleY(0);
		this.setTailleZ(0);
	}
	/**
	 * Initialisation Structure de taille paramétrée.
	 * @param sizeX taille de la Structure en X.
	 * @param sizeY taille de la Structure en Y.
	 * @param sizeZ taille de la Structure en Z.
	 */
	public Structure(int sizeX, int sizeY, int sizeZ){
		this.taille = new int[3];
		this.setTailleX(sizeX);
		this.setTailleY(sizeY);
		this.setTailleZ(sizeZ);
		blocks=new short[this.getTailleX()][this.getTailleZ()][this.getTailleY()];
	}
	/**
	 * Initialisation Structure à partir d'un fichier.
	 * @param path le chemin vers le fichier à importer.
	 */
	public Structure(String path) throws IOException{
		this.taille = new int[3];
		FileInputStream fichier = new FileInputStream(path);
		
		this.loadStructure(fichier);
		
		fichier.close();		
	}
	/**
	 * Positionne la Structure dans la Map en paramètre.
	 * @param monde la Map où positionner la Structure.
	 * @param x coordonnées en x du premier cube dans monde.
	 * @param y coordonnées en y du premier cube dans monde.
	 * @param z coordonnées en z du premier cube dans monde.
	 * @param creuser si vrai les blocs d'air de la Structure seront aussi placés.
	 */
	public void place(Map monde, int x, int z, int y, boolean creuser){
		for(int i=x;i<x+getTailleX();i++){
			for(int j=z;j<z+getTailleZ();j++){
				for(int k=y;k<y+getTailleY();k++){
					if(creuser || getBlock(i-x, j-z, k-y)!=0){
						try {
							if(i>=0 && j>=0 && k>=0 && i<monde.getSize()[0] && j<monde.getSize()[1] && k<monde.getSize()[2]){
								monde.setBlock(i, j, k, getBlock(i-x, j-z, k-y));
							}
						} catch (BadStateException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	/**
	 * Positionne la Structure dans la Map en paramètre.
	 * @param monde la Map où positionner la Structure.
	 * @param loc position où placer la Structure dans monde.
	 * @param creuser si vrai les blocs d'air de la Structure seront aussi placés.
	 */
	public void place(Map monde, Location loc, boolean creuser){
		this.place(monde, loc.getX(), loc.getZ(), loc.getY(), creuser);
	}
	/**
	 * Place la Structure en origine du monde.
	 * @param monde la Map où placer la Structure.
	 */
	public void construire(Map monde){
		this.place(monde, 0, 0, 0, false);
	}
	/**
	 * Récupère le block aux coordonnées.
	 * @param x coordonnées en x.
	 * @param y coordonnées en y.
	 * @param z coordonnées en z.
	 * @return retourne l'ID du block.
	 */
	public short getBlock(int x, int z, int y) {
		return blocks[x][z][y];
	}
	/**
	 * Récupère le block aux coordonnées.
	 * @param position les coordonnées du block sous la forme (x,z,y).
	 * @return retourne l'ID du block.
	 */
	public short getBlock(int[] position) {
		return blocks[position[0]][position[1]][position[2]];
	}
	/**
	 * Retourne taille de la Structure en x.
	 * @return taille de la Structure en x.
	 */
	public int getTailleX() {
		return taille[0];
	}
	/**
	 * Retourne taille de la Structure en y.
	 * @return taille de la Structure en y.
	 */
	public int getTailleY() {
		return taille[2];
	}
	/**
	 * Retourne taille de la Structure en z.
	 * @return taille de la Structure en z.
	 */
	public int getTailleZ() {
		return taille[1];
	}
	/**
	 * Retourne taille de la Structure sous forme [x,z,y].
	 * @return taille de la Structure sous forme [x,z,y].
	 */
	public int[] getTaille() {
		return taille;
	}
	/**
	 * Modifie l'ID du block.
	 * @param x coordonnée en x.
	 * @param y coordonnée en y.
	 * @param z coordonnée en z.
	 * @param id le nouvel ID du block.
	 */
	public void setBlock(int x, int z, int y, short id) {
		this.blocks[x][z][y] = id;
	}
	/**
	 * Modifie l'ID du block.
	 * @param position du block sous la forme [x,z,y].
	 * @param id le nouvel ID du block.
	 */
	public void setBlock(int[] position, short id) {
		this.blocks[position[0]][position[1]][position[2]] = id;
	}
	/**
	 * Modifier taille de Structure en x.
	 * @param taille nouvelle taille de x.
	 */
	private void setTailleX(int taille) {
		if(this.taille==null) this.taille = new int[3];
		this.taille[0] = taille;
	}
	/**
	 * Modifier taille de Structure en x.
	 * @param taille nouvelle taille de y.
	 */
	private void setTailleY(int taille) {
		if(this.taille==null) this.taille = new int[3];
		this.taille[2] = taille;
	}
	/**
	 * Modifier taille de Structure en x.
	 * @param taille nouvelle taille de z.
	 */
	private void setTailleZ(int taille) {
		if(this.taille==null) this.taille = new int[3];
		this.taille[1] = taille;
	}
	/**
	 * Modifier taille de Structure.
	 * @param sixeX nouvelle taille de x.
	 * @param sixeY nouvelle taille de y.
	 * @param sixeZ nouvelle taille de z.
	 */
	public void setTaille(int sizeX, int sizeY, int sizeZ) {
		if(this.taille==null) this.taille = new int[3];
		this.setTailleX(sizeX);
		this.setTailleY(sizeY);
		this.setTailleZ(sizeZ);
		this.blocks=new short[this.getTailleX()][this.getTailleZ()][this.getTailleY()];
	}
	/**
	 * Utilisé par le constructeur
	 * @param fStream fichier à importer.
	 * @throws IOException si fichier illisible.
	 */
	private void loadStructure(FileInputStream fStream) throws IOException{
		byte[] b =new byte[4];
		
		System.out.print("Chargement de structure de taille");
		
		fStream.read(b);
		this.setTailleX(convertByteToInt(b));System.out.print("("+this.getTailleX());
		fStream.read(b);
		this.setTailleY(convertByteToInt(b));System.out.print(","+this.getTailleY());
		fStream.read(b);
		this.setTailleZ(convertByteToInt(b));System.out.println(","+this.getTailleZ()+")");
		
		blocks=new short[this.getTailleX()][this.getTailleZ()][this.getTailleY()];
		
		b=new byte[2];
		
		for(int x=0;x<this.getTailleX();x++){
			for(int z=0;z<this.getTailleZ();z++){
				for(int y=0;y<this.getTailleY();y++){
					fStream.read(b);
					this.setBlock(x, z, y, convertByteToShort(b));
				}
			}
		}
		
		System.out.println("Chargement de la structure fini.");
	}
	/**
	 * Convertit byte en short.
	 * @param b un byte [2].
	 * @return un short de même valeur.
	 */
	private short convertByteToShort(byte[] b){
		return (short)((b[0]<<8) + b[1]);
	}
	/**
	 * Convertit byte en int.
	 * @param b un byte [2].
	 * @return un int de même valeur.
	 */
	private int convertByteToInt(byte[] b){
		return (int)((b[0]<<24) + (b[1]<<16) + (b[2]<<8) + b[3]);
	}
	

}

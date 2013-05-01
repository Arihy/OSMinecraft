package openstreetcraft.buildingGenerator;

import java.io.FileInputStream;
import java.io.IOException;

import openstreetcraft.Location;
import map.Map;
import map.exceptions.BadStateException;

public class Structure {
	
	protected int[] taille;
	protected short[][][] blocks;
	
	public Structure(){
		this.taille = new int[3];
		this.setTailleX(0);
		this.setTailleY(0);
		this.setTailleZ(0);
	}
	
	public Structure(int sizeX, int sizeY, int sizeZ){
		this.taille = new int[3];
		this.setTailleX(sizeX);
		this.setTailleY(sizeY);
		this.setTailleZ(sizeZ);
		blocks=new short[this.getTailleX()][this.getTailleZ()][this.getTailleY()];
	}
	
	public Structure(String path) throws IOException{
		this.taille = new int[3];
		FileInputStream fichier = new FileInputStream(path);
		
		this.loadStructure(fichier);
		
		fichier.close();		
	}
	
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
	
	public void place(Map monde, Location loc, boolean creuser){
		this.place(monde, loc.getX(), loc.getZ(), loc.getY(), creuser);
	}
	
	public void construire(Map monde){
		this.place(monde, 0, 0, 0, false);
	}

	public short getBlock(int x, int z, int y) {
		return blocks[x][z][y];
	}

	public short getBlock(int[] position) {
		return blocks[position[0]][position[1]][position[2]];
	}

	public int getTailleX() {
		return taille[0];
	}

	public int getTailleY() {
		return taille[2];
	}

	public int getTailleZ() {
		return taille[1];
	}
	
	public int[] getTaille() {
		return taille;
	}

	public void setBlock(int x, int z, int y, short id) {
		this.blocks[x][z][y] = id;
	}

	public void setBlock(int[] position, short id) {
		this.blocks[position[0]][position[1]][position[2]] = id;
	}

	private void setTailleX(int taille) {
		if(this.taille==null) this.taille = new int[3];
		this.taille[0] = taille;
	}
	
	private void setTailleY(int taille) {
		if(this.taille==null) this.taille = new int[3];
		this.taille[2] = taille;
	}
	
	private void setTailleZ(int taille) {
		if(this.taille==null) this.taille = new int[3];
		this.taille[1] = taille;
	}

	public void setTaille(int sizeX, int sizeY, int sizeZ) {
		if(this.taille==null) this.taille = new int[3];
		this.setTailleX(sizeX);
		this.setTailleY(sizeY);
		this.setTailleZ(sizeZ);
		this.blocks=new short[this.getTailleX()][this.getTailleZ()][this.getTailleY()];
	}
	
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
	
	private short convertByteToShort(byte[] b){
		return (short)((b[0]<<8) + b[1]);
	}
	
	private int convertByteToInt(byte[] b){
		return (int)((b[0]<<24) + (b[1]<<16) + (b[2]<<8) + b[3]);
	}
	

}

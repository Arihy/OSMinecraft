package openstreetcraft;

public class IDBlock {
	
	public static final short ROCHE = 1 ;
	public static final short HERBE = 2 ;
	public static final short TERRE = 3 ;
	public static final short PIERRE = 4 ;
	public static final short PLANCHE = 5 ;
	public static final short EAU = 8 ;
	public static final short BOIS = 17 ;
	public static final short VERRE = 20 ;
	public static final short GRES = 24 ;
	public static final short LAINE = 35 ;
	public static final short FER = 42 ;
	public static final short DBLDALLE = 43 ;
	public static final short DALLE = 44 ;
	public static final short BRIQUE = 45 ;
	public static final short OBSIDIENNE = 49;
	public static final short PORTE = 64 ;
	public static final short LUMINITE = 89 ;
	public static final short TAILLEE = 98 ;
	public static final short VITRE = 102 ;
	public static final short NETHERBRIQUE = 112 ;
	public static final short DALLEBOIS = 126 ;
	public static final short QUARTZ = 155 ;
	
	/**
	 * Calcule valeur ID+Data pour block spéciaux.
	 * @param ID l'id du block.
	 * @param data le data du block.
	 * @return l'ID et le data dans un short.
	 */
	public static short blockData(int ID, int data){
		return (short)((ID%256)+256*(data%256));
	}
	
}

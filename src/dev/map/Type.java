package dev.map;

/**
 * The list of types of entities and tiles
 *
 */
public class Type {
	public static final int empty = 0;
	public static final int player = 1;
	public static final int wall = 2;
	public static final int exit = 3;
	public static final int treasure = 4;
	public static final int door = 5;
	public static final int key = 6;
	public static final int boulder = 7;
	public static final int floorswitch = 8;
	public static final int bomb = 9;
	public static final int pit = 10;
	public static final int hunter = 11;
	public static final int strategist = 12;
	public static final int hound = 13;
	public static final int coward = 14;
	public static final int sword = 15;
	public static final int arrow = 16;
	public static final int invincibility = 17;
	public static final int hover = 18;
	public static final int litbomb= 19;
	public static final int explosion = 20;
	public static final int bomber = 21;
	public static final int mage = 22;
	public static final int gamemaster = 23;
	public static final int lich = 24;
	public static final int archer = 25;
	
	public static final int[] enemies = {Type.strategist,Type.hunter,Type.hound,Type.coward};
}

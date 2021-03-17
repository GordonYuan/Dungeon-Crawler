package dev.map;

/**
 * An abstract class for the various win conditions
 *
 */
public abstract class Winall{
	protected Map current;
	public Winall(Map curr) {
		this.setCurrent(curr);
	}
	public Map getCurrent() {
		return current;
	}
	public void setCurrent(Map current) {
		this.current = current;
	}
	public boolean checkWin() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}

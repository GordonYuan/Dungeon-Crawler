package dev.map;

/**
 * Checks to see if all the switchers are turned on
 *
 */
public class WinPush extends Winall{

	public WinPush(Map curr) {
		super(curr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean checkWin() {
		for (int i=0; i < current.getWidth(); i++) {
			for (int k=0; k < current.getHeight(); k++) {
				int[] coordinate = {i,k};
				Tile tile = current.getTile(coordinate[0],coordinate[1]);
				if (tile.getType() == Type.floorswitch) {
					if(!((Switcher) tile).isTriggered()) {
						return false;
					}
				}	
			}
		}
		return true;
	}

}

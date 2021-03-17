package dev.game.entities;
import dev.map.*;
/**
 * Arrow while it's laying on the floor. Can be shot at enemies
 * @author Andy Yu
 *
 */
public class Arrow extends FloorItem {

	public Arrow(int[] coordinate,Map m) {
		super(coordinate,m,Type.arrow);
	}

	@Override
	public void giveToPlayer(Player p) {
		p.giveArrow();
		this.map.delete(this);
	}


	@Override
	public String imagePath() {
		return "dev/game/assets/arrow.png";
	}
}

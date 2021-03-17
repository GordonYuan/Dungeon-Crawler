package dev.game.entities;

import dev.map.Map;
import dev.map.Type;

/**
 * Arrows are enchanted to become flaming bombs which explode upon impact
 * @author Andy Yu
 *
 */

public class Mage extends Player {

	public Mage(int[] coordinate, Map m) {
		super(coordinate, m);
		this.type = Type.mage;
	}
	@Override
	public void shootArrow(char direction) {
		if (this.getArrows()==0) return;
		int i;
		int [] coordinate = new int[2];
		coordinate[0] = this.getCoordinate()[0];
		coordinate[1] = this.getCoordinate()[1];
		if (direction=='w') { //Shoot an arrow down
			for (i=-1;this.getCoordinate()[1]+i>=0;i--) {
				coordinate[1] = this.getCoordinate()[1] + i;
				if(this.shootArrowHelper(coordinate)) {
					coordinate[1]++;
					LitBomb fireball = new LitBomb(coordinate,this.map);
					fireball.explode();
					break;
				}
			}
		}
		else if (direction=='a') { //Shoot an arrow left (x coord decreases, y coord stays same)
			for (i=-1;this.getCoordinate()[0]+i>=0;i--) {
				coordinate[0] = this.getCoordinate()[0] + i;
				if(this.shootArrowHelper(coordinate)) {
					coordinate[0]++;
					LitBomb fireball = new LitBomb(coordinate,this.map);
					fireball.explode();
					break;
				}
			}
		}
		else if (direction=='s') { //Shoot an arrow up (x coord stays same, y coord increases)
			for (i=1;this.getCoordinate()[1]+i<this.map.getHeight();i++) {
				coordinate[1] = this.getCoordinate()[1] + i;
				if(this.shootArrowHelper(coordinate)) {
					coordinate[1]--;
					LitBomb fireball = new LitBomb(coordinate,this.map);
					fireball.explode();
					break;
				}
			}
		}
		else if (direction=='d') { //Shoot an arrow up (y coord stays same, x coord increases)
			for (i=1;this.getCoordinate()[0]+i<this.map.getWidth();i++) {
				coordinate[0] = this.getCoordinate()[0] + i;
				if(this.shootArrowHelper(coordinate)) {
					coordinate[0]--;
					LitBomb fireball = new LitBomb(coordinate,this.map);
					fireball.explode();
					break;
				}
			}
		}
		this.giveArrows(-1);
	}

	@Override
	public boolean shootArrowHelper(int[] coordinate) {
		boolean ret=false;
		if (!this.map.isPassable(coordinate)) ret=true; //Check for wall blocks
		else if (this.map.isBoulder(coordinate)!=null) ret=true; //Check for boulder blocks
		else if (this.map.hasEnemy(coordinate)!=null) { 
			this.map.killEnemy(coordinate); //Check for enemy hits
			ret=true;
		}
		return ret;
	}
	@Override
	public String imagePath() {
		return "dev/game/assets/orc_wizard.png";
	}
	
}

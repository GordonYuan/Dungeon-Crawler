package dev.game.entities;

/**
 * Abstract class for player status effects
 * Similar to state pattern, but we have multiple states active simultaneously
 * @author Andy Yu
 *
 */

public abstract class StatusEffect {
	private boolean activated;

	public boolean isActivated() {
		return activated;
	}

	public StatusEffect() {
		super();
		this.activated = false;
	}

	public void deactivate() {
		this.activated=false;
	}
	public void activate() {
		this.activated=true;
	}
	public void setActivate(boolean b) {
		this.activated = b;
	}
	
}

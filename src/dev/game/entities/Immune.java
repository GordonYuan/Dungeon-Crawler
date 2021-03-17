package dev.game.entities;
import java.time.Duration;
import java.time.Instant;

/**
 * Status effect for when player has drunk an immunity potion
 * @author Andy Yu
 *
 */
public class Immune extends StatusEffect {
	
	private Instant activationTime;
	private Instant deactivationTime;
	public static final long IMMUNITY_TIME = 15;
	public Immune() {
		super();
		this.activationTime = null;
		this.deactivationTime = null;
	}
	
	public boolean shouldDeactivate() {
		if (Instant.now().isAfter(this.deactivationTime)) {
			return true;
		}
		else {
			return false;
		}
	}
	public long immuneTime() {
		Duration d = Duration.between(Instant.now(),this.deactivationTime);
		return d.getSeconds();
	}
	@Override
	public void activate() {
		this.activationTime = Instant.now();
		this.deactivationTime = activationTime.plusSeconds(Immune.IMMUNITY_TIME);
		this.setActivate(true);
	}
}

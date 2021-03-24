package starwars;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.*;

public class Droids extends SWActor {
	
	private Boolean isImmobile;
	private SWActor Owner;
	private Boolean hasOwner;
	

	/**
	 * Create a Droid.  
	 * They do not move
	 * 
	 * @param hitpoints
	 *            the number of hit points of this Droid. If this
	 *            decreases to below zero, the Droid will become immobile.

	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>Droid</code> belongs to
	 * @param actor
	 *            the <code>actor</code> which owns this droid
	 * 
	 */
	public Droids (int hitpoints, MessageRenderer m, SWWorld world, SWActor actor){
		super(actor.getTeam(),hitpoints,m,world);
		this.sethasMind(false);
		this.hasOwner = true;
		this.isImmobile = false;
		this.addAffordance(new Own(this,m));
		this.addAffordance(new Disown(this,m));
		this.addAffordance(new Disassemble(this,m));
		
	}
	
	/**
	 * Create a Droid.  
	 * They do not move
	 * 
	 * @param hitpoints
	 *            the number of hit points of this Droid. If this
	 *            decreases to below zero, the Droid will become immobile.

	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *  
	 */
	
	public Droids (int hitpoints, MessageRenderer m, SWWorld world){
		super(Team.NEUTRAL,hitpoints,m,world);
		this.sethasMind(false);
		this.hasOwner = false;
		this.isImmobile = false;
		this.addAffordance(new Own(this,m));
		this.addAffordance(new Disown(this,m));
		this.addAffordance(new Disassemble(this,m));
		
	}
	
	

	/**
	 * droids are never dead. They just become immobile.
	 */
	@Override
	public boolean isDead() {
		return false;
	}
	
	/**
	 * A getter method if immobile or not
	 * 
	 * @return 	Boolean if immobile or not
	 */
	public boolean getisImmobile(){
		return this.isImmobile;
	}
	
	
	/**
	 * A setter method to set mobility
	 * 
	 */
	public void setisImmobile(Boolean val) {
		this.isImmobile = val;
	}
	
	/**
	 * A getter method to check if has owner or not
	 * 
	 * @return 	Boolean if owner or not
	 */
	public boolean gethasOwner(){
		return this.hasOwner;
	}
	
	/**
	 * A setter method to set if has owner or not
	 * 
	 */
	public void sethasOwner(Boolean val){
		this.hasOwner = val;
	}
	
	/**
	 * A setter method to set owner of a specific droid
	 * 
	 */
	public void setOwner(SWActor actor){
		this.Owner = actor;
	}
	
	/**
	 * doesnt do anything for now
	 * 
	 */
	public void act(){
		
		if (hasOwner == true){
			return;
		}
	}
	
	/**
	 * A method which reduces hitpoints when in badlands.
	 * 
	 */
	public void reduceHitpoints(){
		this.setHitpoints(this.getHitpoints() - 20);
		this.say(this.getShortDescription() + " is in badlands and its hitpoints has decreased to " + this.getHitpoints());
	}
	
	

}

package starwars.entities.actors;

import java.util.ArrayList;
import java.util.List;
import edu.monash.fit2099.simulator.matter.Entity;
import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Droids;
import starwars.SWActor;
import starwars.SWEntity;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.UseOnDroids;
import starwars.actions.Disassemble;
import starwars.actions.*;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.Patrol;
import starwars.entities.*;

public class R2D2 extends Droids {
	
	/**
	*This is the patrol path of R2D2 which it follows
	 */
	private Patrol path;
	
	/**
	 * Create a R2D2.  
	 * They will move in a specific patrol path which is 5 times east and 5 times west.
	 * 
	 * @param hitpoints
	 *            the number of hit points of this Droid. If this
	 *            decreases to below zero, the Droid will become immobile.

	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>Droid</code> belongs to
	 * 
	 */
	
	public R2D2 (int hitpoints, MessageRenderer m, SWWorld world){
		super(200,m,world);
		Direction [] patrolmoves = {CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.EAST,
                CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.WEST,
                CompassBearing.WEST, CompassBearing.WEST,
                CompassBearing.WEST, CompassBearing.WEST};
		this.path = new Patrol(patrolmoves);
		this.setShortDescription("R2D2");
		this.setLongDescription("Bleep Bleep, Bloop");
		
	}
	
	/**
	 * R2D2 will move in a specific path and heal droid on its way. It can heal droid which are immobile using 
	 * its internal reservoir. It can dissassemble droids who are immobile.
	 * It can repair droids who are immobile if it is carrying an droid part.
	 * It will also heal itself if it has less health using oil from its reservoir.
	 * 

	 */
	
	@Override
	public void act(){
		
		if (this.getisImmobile() == true) {
			return;
		}
		
		if (gethasOwner() == true){
			return;
		}

		
		//Finds mobile and immobile droids in its location
		ArrayList<Droids> allDroids = this.getNearbyDroids(this);
		ArrayList<Droids> allMobileDroids = this.getNearbyMobileDroids(this);
		
		
		//If immobile droids in location and carries droid parts, then use droid parts on immobile droids.
		if (allDroids.size() != 0){
			Droids randomDroid = allDroids.get((int) (Math.floor(Math.random() * allDroids.size())));
			Affordance ac = null;
			if (this.getItemCarried() != null && this.getItemCarried() instanceof DroidParts){

				SWEntityInterface item = this.getItemCarried();
				for (Affordance a : item.getAffordances()) {
					if (a instanceof UseOnDroids) {
						ac = a;
						break;
					}	
				}

			}
			// if immobile droids in location but not carrying any droid parts, then disassemble immobile droid.
			if (this.getItemCarried() == null){

				for (Affordance a : randomDroid.getAffordances()) {
					if (a instanceof Disassemble) {
						ac = a;
						break;
					}	
				}

			}
			scheduler.schedule(ac,this,1);
			return;
		}
		
		//If mobile droids in location and health of those droids is less, use oil from internal oil reservoir.
		if (allMobileDroids.size() != 0){
			Droids randomDroid = allMobileDroids.get((int) (Math.floor(Math.random() * allMobileDroids.size())));
			if (randomDroid.getHitpoints() < randomDroid.getMaxHitpoints()){
				this.useOilFromInternalReservoir(randomDroid);
				this.say(this.getShortDescription() + " has used oil from its internal reservoir on droid named " + randomDroid.getShortDescription());
				return;
			}
		}
		
		
		//If droid parts in the same location, and not carrying anything, pick the droid parts from map.
		SWWorld world = this.getWorld();
		EntityManager em = world.getEntitymanager();
		Location loc = em.whereIs(this);
		List<SWEntityInterface> entities = em.contents(loc);		

		SWEntityInterface item = null;
		//Affordances of the Entities in the same location
		for (SWEntityInterface e: entities) {
			if (e instanceof DroidParts) { //don't add the affordances if they belong to the actor itself
				item =  e;
				break;
			}
		}

		if (item!= null){

			for (Affordance aff: item.getAffordances()) {
				if (aff instanceof Take){
					scheduler.schedule(aff,this,1);
					return;
				}
			}
		}

		//if own health is less than max, use oil from internal reservoir on itself
		if (this.getHitpoints() < this.getMaxHitpoints()/2){
			this.useOilFromInternalReservoir(this);
			this.say(this.getShortDescription() + " is using oil from its internal reservoir on itself ");
			this.say(this.getShortDescription() + " 's health has increased to " + this.getHitpoints());

		}

		say(describeLocation());
		
		//move in the next direction
		Direction newdirection = path.getNext();
		say(getShortDescription() + " moves " + newdirection);
		Move myMove = new Move(newdirection, messageRenderer, world);
		scheduler.schedule(myMove, this, 1);

			

		



	}
	
	private ArrayList<Droids> getNearbyDroids(SWActor a){
		
		SWWorld world = a.getWorld();
		EntityManager em = world.getEntitymanager();
		Location loc = em.whereIs(a);
				
		List<SWEntityInterface> entities = em.contents(loc);
		
		ArrayList<Droids> allDroids = new ArrayList<Droids>();
		
		for (SWEntityInterface e : entities) {
			// Figure out if we should be attacking this entity
			if( e != a && e instanceof Droids && ((Droids) e).getisImmobile() == true){
				allDroids.add((Droids) e);
			}
			}
		
		return allDroids;
		
	}
	
	/**
	 * This method is used to find all immobile droids in the same location as R2D2. It
	 * 
	 * @param a actor of which you want to find droids in the same location. In this case its R2D2 itself.
	 * @return 	array of immobile droids.
	 */
	private ArrayList<Droids> getNearbyMobileDroids(SWActor a){
		
		SWWorld world = a.getWorld();
		EntityManager em = world.getEntitymanager();
		Location loc = em.whereIs(a);
				
		List<SWEntityInterface> entities = em.contents(loc);
		
		ArrayList<Droids> allDroids = new ArrayList<Droids>();
		
		for (SWEntityInterface e : entities) {
			// Figure out if we should be attacking this entity
			if( e != a && e instanceof Droids && ((Droids) e).getisImmobile() == false){
				allDroids.add((Droids) e);
			}
			}
		
		return allDroids;
	}
		
	/**
	 * This method will describe, 
	 * <ul>
	 * 	<li>the  <code>R2D2</code>'s location</li>
	 * 	<li>ITS hitpoints</li>
	 * <ul>
	 * <p>
	 * 
	 */
	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
	
	/**
	 *This method will take an actor as argument and increase its hitpoints by 10. The actor may be the R2D2 itself.
	 */
	
	private void useOilFromInternalReservoir(SWActor a){
		a.setHitpoints(a.getHitpoints() + 10);
	}

}

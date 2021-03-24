package starwars.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.WeakMinded;
import starwars.actions.CallForBackup;
import starwars.actions.Move;
import starwars.entities.Blaster;
import starwars.entities.LightSaber;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;

public class Stormtrooper extends WeakMinded {
	
	/*
	 * Constructing a Stormtrooper and carries a <code>Blaster</code>
	 * 
	 * @param hitpoints
	 *            the number of hit points of the Stormtrooper

	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>Stormtrooper</code> belongs to
	 *
	 * @see starwars.entities.Blaster
	 * 
	 */
	public Stormtrooper(int hitpoints, MessageRenderer m, SWWorld world) {
		super(Team.EVIL, 100, m, world);
		// TODO Auto-generated constructor stub
		Blaster stormweapon = new Blaster(m);		
		setItemCarried(stormweapon);				//blaster added as weapon
		this.setShortDescription("Stormtrooper");
		this.setLongDescription("Stormtrooper, an elite soldier of the galactic empire!");
		
		
	}
	
	/*
	 * They move randomly and will attack anyone who is not on <code>Team</code> EVIL.
	 * They have a good percentage (75%) of missing attacks and sometimes (5%) they will 
	 * radio for backup instead of moving, creating an instance of a new Stormtrooper in
	 * the same location
	 * 
	 * @see starwars.WeakMinded#act()
	 */
	@Override
	public void act() {
		if (isDead()) {
			return;
		}
		
		say(describeLocation());
		
		//gets attackable characters
		AttackInformation attack = AttackNeighbours.attackLocals(this, this.world, true, false);
		if (attack != null) {
			//will display the miss statement 75% of the time and attack 25% of the time
			if (Math.random() > 0.75){
				say(getShortDescription() + " has attacked " + attack.entity.getShortDescription());
				scheduler.schedule(attack.affordance, this, 1);
			}
			else{
				say(getShortDescription() + " widely misses target and shoots at open space!");
			}
		}
		else{
			//will move randomly 95% of the time and call for backup the other 5%
			if (Math.random() > 0.95){
				scheduler.schedule(new CallForBackup(this,messageRenderer), this, 1);
			}
			else{
				
				ArrayList<Direction> possibledirections = new ArrayList<Direction>();

				// build a list of available directions
				for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
					if (SWWorld.getEntitymanager().seesExit(this, d)) {
						possibledirections.add(d);
					}
				}

				Direction heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
				say(getShortDescription() + " is heading " + heading + " next.");
				Move myMove = new Move(heading, messageRenderer, world);

				scheduler.schedule(myMove, this, 1);
			}
		}
		

		}
	/**
	 * This method will display the <code>Stormtrooper</code>'s 
	 * <ul>
	 * 	<li>location</li>
	 * 	<li>hitpoints</li>
	 * <ul>
	 * <p>
	 * 
	 */
	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}

}
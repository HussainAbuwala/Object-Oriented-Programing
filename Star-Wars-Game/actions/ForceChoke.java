package starwars.actions;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.entities.DroidParts;
import starwars.entities.actors.BenKenobi;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import starwars.*;
import edu.monash.fit2099.simulator.time.*;

/**
 * Command to Force Choke entities.
 * 
 * This affordance is attached to all <code>SWActor</code>
 * except for <code>Droids</code>
 * 
 * @author WENGER_OUT
 */

public class ForceChoke extends SWAffordance{

	/**
	 * Constructor for the <code>ForceChoke</code> Class. Will initialize the message renderer, the target.
	 * 
	 * @param theTarget
	 * @param m the message renderer to display messages
	 */
	public ForceChoke(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Determine whether a particular <code>SWActor a</code> can use Force Choke on the target.
	 * 
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if any <code>SWActor</code> is not dead
	 */
	@Override
	public boolean canDo(SWActor a) {

		if(((SWActor) target).isDead()) {
			return false;
		} 
		return true;
	}

	
	/**
	 * Perform the <code>Force</code> command on an WeakMinded entity.
	 * <p>
	 * This method is only applicable if,
	 * <ul>
	 * 	<li>The target of the <code>Force</code> are not <code>Droids</code> </li>
	 * </ul>
	 * <p>
	 * This will cause the target to lose 50 hitpoints
	 * 
	 * 
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code> who is using Force
	 * @pre 	this method should only be called if the <code>SWActor a</code> is alive
	 * @pre		an <code>Force</code> must not be performed on a dead <code>SWActor</code>
	 * @see		starwars.SWActor
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		//sets target's hitpoints less by 50
		((SWActor) target).setHitpoints(((SWActor) target).getHitpoints() - 50);
		a.say(a.getShortDescription() + " used Force Choke on " + target.getShortDescription());
	}

	/**
	 * A String describing what this <code>ForceChoke</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "Force Choke " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "Force Choke " + this.target.getShortDescription();
	}
	
}
package starwars;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Move;
import edu.monash.fit2099.simulator.space.Direction;
/**
 * People with the ability to use Force
 * 
 * @author WENGER_OUT
 *
 */
public abstract class PeopleWithForce extends SWActor {
	/**
	 * Extends from SWActor, these are people who have the ability to use force.
	 * <p>
	 * Ranging from 25 to 100. 25-75 denotes people with less force and 100 denotes lots of force
	 * @param team
	 * @param hitpoints
	 * @param m
	 * @param world
	 */
	public PeopleWithForce(Team team, int hitpoints, MessageRenderer m, SWWorld world){
		super(team,hitpoints,m,world);
		this.setForceAbility(25);
		
	}
	
	public void forceWeakMinded(WeakMinded weakP, Direction direc){
		
		if (this.getForceAbility() == 100){
			
			Move myMove = new Move(direc, messageRenderer, world);

			scheduler.schedule(myMove, weakP, 1);
			
		}
		
	}
	

}

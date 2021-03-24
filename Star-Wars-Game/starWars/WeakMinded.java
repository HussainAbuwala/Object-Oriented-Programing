package starwars;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.*;
/**
 * People who have no Force Abilities 
 * 
 * @author WENGER_OUT
 *
 */
public class WeakMinded extends SWActor  {
	/**
	 * Denotes people who are weak minded,i.e, cannot use force and can be controlled by people with lots of force
	 * <p>
	 * These people also cannot wield a Lightsaber
	 * @param team
	 * @param hitpoints
	 * @param m
	 * @param world
	 */
	public WeakMinded(Team team, int hitpoints, MessageRenderer m, SWWorld world){
		super(team,hitpoints,m,world);
		this.setForceAbility(0);
		this.addAffordance(new Force(this,m));
	}

	@Override
	public void act() {
		return;		
	}
	
}
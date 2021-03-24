package starwars;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

/**
 * People with Force who belong to the GOOD Team
 * 
 * @author WENGER_OUT
 *
 */

public abstract class JEDI extends PeopleWithForce {
	/**
	 * These are usually PeopleWithForce who belong to the GOOD Team.
	 * <p>
	 * Added for extendibility issues.
	 * @param team
	 * @param hitpoints
	 * @param m
	 * @param world
	 */
	public JEDI(Team team, int hitpoints, MessageRenderer m, SWWorld world){
		super(team,hitpoints,m,world);
		
	}
}

package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWLegend;
import starwars.SWWorld;
import starwars.Team;

/**
 * Admiral Ackbar.  
 * 
 * He does have any special abilities here.
 * He just has a 10% probability of saying "It's a trap" each turn.
 * 
 * Note that you can only create ONE Admiral Ackbar, like all SWLegends.
 * @author WENGER_OUT
 *
 */
public class AdmiralAckbar extends SWLegend {

	private static AdmiralAckbar admiral = null; // yes, it is OK to return the static instance!

	
	protected AdmiralAckbar(int hitpoints, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, hitpoints, m, world);
		this.setHitpoints(100);
		this.setShortDescription("Admiral Ackbar");
		this.setLongDescription("Admiral Ackbar, a veteran commander of the Rebel Alliance");
	}

	public static AdmiralAckbar getAdmiralAckbar(int hitpoints,MessageRenderer m, SWWorld world) {
		admiral = new AdmiralAckbar(hitpoints,m, world);
		admiral.activate();
		return admiral;
	}
	
	@Override
	protected void legendAct() {
		// TODO Auto-generated method stub
		
		//Select random number of 1 to 10
		int randomNumber = (int) (Math.random() * 10 + 1);
		
		//If number from 1 to 10 is 5, which obviously has a probability of 10%, choose a random quote and say
		if (randomNumber == 5){
			
			String quote = "It's a trap!";
			this.say(this.getShortDescription() + " says " + quote);
			
			}
	}

}

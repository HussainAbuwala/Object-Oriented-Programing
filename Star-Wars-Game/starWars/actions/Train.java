package starwars.actions;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.entities.actors.BenKenobi;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import starwars.*;
import starwars.entities.*;
/**
 * <code>SWAction</code> that lets a <code>BenKenobi</code> train <code>Player</code>.
 * 
 * @author WENGER_OUT
 */
public class Train extends SWAffordance {
	
	/**
	 * Constructor for the <code>Train</code> Class. Will initialize the message renderer, the target.
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being taken
	 * @param m the message renderer to display messages
	 */
	public Train(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Returns if or not this <code>Train</code> can be performed by <code>BenKenobi</code>.
	 * <p>
	 * This method returns true if <code>Player</code> is in the same location as <code>BenKenobi</code>
	 * and <code>Player</code>'s forceAbility is less than 100.
	 *  
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is <code>BenKenobi</code> and can train <code>Player</code> 
	 * @see		{@link starwars.SWActor)}
	 */
	@Override
	public boolean canDo(SWActor a) {

		ArrayList<BenKenobi> ben = this.getNearbyBen(a);
		if (ben.size() != 0){
			if (a.getForceAbility() < 100){
				return true;
			}
		}
		return false;
	}

	/**
	 * Perform the <code>Train</code> action by increasing the forceAbility of <code>Player</code> by 25.
	 * <p>
	 * This method should only be called if the <code>BenKenobi</code> is there and alive.
	 * 
	 * @author WENGER_OUT
	 * @param 	a the <code>SWActor</code> that is taking the target
	 * @see 	{@link #target}
	 * @see		{@link starwars.SWActor}
	 * @see		{@link starwars.BenKenobi}
	 * @see		{@link starwars.Player}
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub

		a.say(a.getShortDescription() + "'s force ability has increased from " + a.getForceAbility() + " to " + (a.getForceAbility() + 25));
		a.setForceAbility(a.getForceAbility() + 25);

		if (a.getForceAbility() == 100){
			a.say(target.getShortDescription() + " says, You have reached your maximum force potential," + a.getShortDescription() + ". No further training is required");
			
			if (a.getItemCarried()!= null && a.getItemCarried() instanceof LightSaber){
				((LightSaber) a.getItemCarried()).addWeaponCapability();
				a.say(target.getShortDescription() + " says, As you have reached your maximum force potential, you can now wield your lightsabre and use it as a weapon");
			}
			
		}

	}

	@Override
	public int compareTo(ActionInterface o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author ram
	 * @return String comprising "Get Force Training from " and the short description of <code>BenKenobi</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Get Force Training from " + target.getShortDescription();
	}

	private ArrayList<BenKenobi> getNearbyBen(SWActor a){

		SWWorld world = a.getWorld();
		EntityManager em = world.getEntitymanager();
		Location loc = em.whereIs(a);

		List<SWEntityInterface> entities = em.contents(loc);

		ArrayList<BenKenobi> ben = new ArrayList<BenKenobi>();

		for (SWEntityInterface e : entities) {
			// Figure out if we should be attacking this entity
			if( e != a && e instanceof BenKenobi){
				ben.add((BenKenobi) e);
			}
		}

		return ben;

	}

}

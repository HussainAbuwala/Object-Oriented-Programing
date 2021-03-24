package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.entities.*;
import starwars.SWAffordance;

/**
 * <code>SWAction</code> that lets a <code>SWActor</code> drink from an object.
 * 
 * @author WENGER_OUT
 */

public class Drink extends SWAffordance {

	/**
	 * Constructor for the <code>Drink</code> Class. Will initialize the message renderer, the target.
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being taken
	 * @param m the message renderer to display messages
	 */
	public Drink(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns if or not this <code>Drink</code> can be performed by the <code>SWActor a</code> on item carried.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying a canteen and it has capability DRINKABLE.
	 *  
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is can take this item, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		SWEntityInterface item = a.getItemCarried();
		if (item != null){
			if (item.hasCapability(Capability.DRINKABLE)){
				if (item instanceof Canteen && ((Canteen) item).getCurrentLevel() != 0 && a.getHitpoints() < a.getMaxHitpoints()){
					return true;
				}
			}
		}
	return false;
}

	/**
	 * Perform the <code>Drink</code> action by healing HP of the <code>SWActor</code>
	 * and lowering the level of canteen.
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> has less HP than maximum.
	 * 
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code> that is taking the target
	 * @see 	{@link starwars.entities.Canteen}
	 * @see		{@link starwars.SWActor}
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		if (target instanceof Canteen){
			a.say(a.getShortDescription() + " is Drinking from " + target.getShortDescription());
			if (a.getHitpoints() + ((Canteen) target).getHealpoints() < a.getMaxHitpoints()){
				a.say(a.getShortDescription() + " health has increased from " + a.getHitpoints() + " to " + (a.getHitpoints() + ((Canteen) target).getHealpoints()));
			}
			else{
				a.say(a.getShortDescription() + " health has increased from " + a.getHitpoints() + " to " + a.getMaxHitpoints());				
			}
			a.setHitpoints(a.getHitpoints() + ((Canteen) target).getHealpoints());
			((Canteen) target).reduceCapacity();
		}
		
		
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author WENGER_OUT
	 * @return String comprising "Drink water from " and the short description of the target of this <code>Drink</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Drink water from " + target.getShortDescription();
	}
	
}

package starwars.actions;
import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.DroidParts;
import starwars.SWWorld;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import starwars.Droids;
/**
 * Action that lets a SWActor own a droid.
 * 
 * This makes the droid follow the owner
 * 
 * @author WENGER_OUT
 * 
 */
public class Own extends SWAffordance {
	
	/**
	 * Constructor for the <code>Own</code> Class. Will initialize the message renderer, the target.
	 * 
	 * @param theTarget a <code>Droids</code> that is being owned
	 * @param m the message renderer to display messages
	 */
	
	public Own(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Returns if or not this <code>Own</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> does not own a mobile droid.
	 *  
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is can own the <code>Droids</code>
	 * @see		{@link starwars.SWActor
	 * @see		starwars.Droids
	 */
	@Override
	public boolean canDo(SWActor a) {
		
		if (((Droids) target).gethasOwner() == false && ((Droids) target).getisImmobile()== false && a.gethasDroid() == false){
			return true ;
		}
		return false;
	}

	/**
	 * Perform the <code>Own</code> action by setting the <code>Droids</code> to be owned by the <code>SWActor</code>
	 * <p>
	 * Owning a droid makes the droid follow the owner and this is shown in the Player class linked below
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code> that is owning the target droid
	 * @see 	{@link #target}
	 * @see		{@link starwars.SWActor}
	 * @see		{@link starwars.actors.Player}
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		a.addFollowers((Droids)target);
		((Droids) target).setOwner(a);
		((Droids) target).sethasOwner(true);
		a.setDroid((Droids) target);
		a.sethasDroid(true);
		((Droids) target).setTeam(a.getTeam());
		a.say(a.getShortDescription() + " currently owns " + " a droid named " + target.getShortDescription() + "!");
	}

	@Override
	public int compareTo(ActionInterface o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author WENGER_OUT
	 * @return String comprising "Own " and the short description of the target of this <code>Own</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Own " + target.getShortDescription();
	}
	
	

}

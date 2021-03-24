package starwars.actions;

import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.entities.DroidParts;
import starwars.SWWorld;
import starwars.Team;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import starwars.Droids;
import starwars.SWAction;
/**
 * Action that lets a SWActor disown a droid.
 * 
 * This stops the droid from following the owner
 * 
 * @author WENGER_OUT
 * 
 */

public class Disown extends SWAffordance {
	
	/**
	 * Constructor for the <code>Disown</code> Class. Will initialize the message renderer, the target.
	 * 
	 * @param theTarget a <code>Droids</code> that is being disowned
	 * @param m the message renderer to display messages
	 */
	
	public Disown(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Returns if or not this <code>Disown</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>SWACtor a</code> owns a droid.
	 *  
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> owns a <code>Droids</code>
	 * @see		{@link starwars.SWActor
	 * @see		starwars.Droids
	 */
	@Override
	public boolean canDo(SWActor a) {
		
		if (((Droids) target).gethasOwner() == true ){
			return true ;
		}
		return false;
	}

	
	/**
	 * Performs the <code>Disown</code> action by setting the  droid owned by the <code>SWActor</code> free
	 * <p>
	 * Disowning a droid makes the droid stop following the owner
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code> that is disowning the target droid
	 * @see 	{@link #target}
	 * @see		{@link starwars.SWActor}
	 * @see		{@link starwars.actors.Player}
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		a.removeFollowers((Droids)target);
		((Droids) target).setOwner(null);
		((Droids) target).sethasOwner(false);
		a.setDroid(null);
		a.sethasDroid(false);
		((Droids) target).setTeam(Team.NEUTRAL);
		a.say(a.getShortDescription() + " has just left " + " a droid named " + target.getShortDescription() + "!");
		
		EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();
		entityManager.setLocation((SWEntityInterface)target, entityManager.whereIs(a));

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
	 * @return String comprising "Disown " and the short description of the target of this <code>Disown</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Disown " + target.getShortDescription();
	}
	
	

}




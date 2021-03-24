package starwars.actions;

import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.entities.actors.Stormtrooper;

/**
 * Creates instance of Stormtrooper
 * 
 * @author WENGER_OUT
 */
public class CallForBackup extends SWAffordance{

	/**
	 * Constructor for the <code>CallForBackup</code> Class. Will initialize the message renderer, the target.
	 * 
	 * @param theTarget
	 * @param m the message renderer to display messages
	 */
	public CallForBackup(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Creates a new Stormtrooper in the same location.
	 * This is only called by a Stormtrooper
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		
		//gets location of actor
		SWWorld world = ((SWActor) target).getWorld();
		EntityManager em = world.getEntitymanager();
		Location loc = em.whereIs(target);
		
		a.say(a.getShortDescription() + " has called for backup!");
		
		//creates new Stormtrooper
		Stormtrooper ST = new Stormtrooper(100,messageRenderer,world);

		//sets it in the same location
		ST.setSymbol("S");
		em.setLocation(ST, loc);
		return;
		
		
	}

	@Override
	public int compareTo(ActionInterface o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * A String describing what this <code>Fly</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "" and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	} 

}

package starwars.actions;

import starwars.SWAffordance;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.entities.DroidParts;
import starwars.SWWorld;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import starwars.Droids;
/**
 * <code>SWAction</code> that lets a <code>SWActor</code> disassemble <code>Droids</code>.
 * 
 * @author WENGER_OUT
 */

public class Disassemble extends SWAffordance {

	/**
	 * Constructor for the <code>Train</code> Class. Will initialize the message renderer, the target.
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being taken
	 * @param m the message renderer to display messages
	 */
	public Disassemble(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns if or not this <code>Disassemble</code> can be performed by <code>SWActor</code>.
	 * <p>
	 * This method returns true if <code>SWActor</code> is in the same location as immobilized
	 * <code>Droids</code>.
	 *  
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> can disassemble local immobile <code>Droids</code> 
	 * @see		{@link starwars.SWActor}
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		//if (a.hasCapability(Capability.REPAIR)){
			ArrayList<Droids> allDroids = this.getNearbyDroids(a);
			if (allDroids.size() == 0){
				return false;
			}
			return true;
	}


	/**
	 * Perform the <code>Disassemble</code> action by removing immobile <code>Droids</code> from map 
	 * and replacing with <code>DroidParts</code>.
	 * <p>
	 * This method should only be called if the <code>Droids</code> are immobile.
	 * 
	 * @author WENGER_OUT
	 * @param 	a the <code>SWActor</code> that is taking the target
	 * @see 	{@link #target}
	 * @see		{@link starwars.Droids}
	 * @see		{@link starwars.DroidParts}
	 * @see		{@link starwars.SWActor}
	 */
	@Override
	public void act(SWActor a) {
		
		SWWorld world = a.getWorld();
		EntityManager em = world.getEntitymanager();
		Location loc = em.whereIs(a);
		
		ArrayList<Droids> allDroids = this.getNearbyDroids(a);

		Droids randomDroid = allDroids.get((int) (Math.floor(Math.random() * allDroids.size())));
		
		a.say(a.getShortDescription() + " is Diassembling " + " a droid named " + target.getShortDescription() + "!");
		em.remove(randomDroid);
		DroidParts part = new DroidParts(messageRenderer);
		em.setLocation(part, loc);
		
	}
	
	
	private ArrayList<Droids> getNearbyDroids(SWActor a){
		
		SWWorld world = a.getWorld();
		EntityManager em = world.getEntitymanager();
		Location loc = em.whereIs(a);
				
		List<SWEntityInterface> entities = em.contents(loc);
		
		ArrayList<Droids> allDroids = new ArrayList<Droids>();
		
		for (SWEntityInterface e : entities) {
			// Figure out if we should be attacking this entity
			if( e != a && e instanceof Droids && ((Droids) e).getisImmobile() == true){
				allDroids.add((Droids) e);
			}
			}
		
		return allDroids;
		
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
	 * @return String comprising "Disassemble An Immobile Droid " and the short description of the target of this <code>Disassemble</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Disassemble An Immobile Droid " + target.getShortDescription();
	}
	
}

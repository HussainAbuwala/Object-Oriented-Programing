package starwars.actions;

import starwars.SWAffordance;

import java.util.ArrayList;
import java.util.List;
import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.entities.DroidParts;
import starwars.entities.OILCAN;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Location;
import starwars.Droids;
/**
 * <code>SWAction</code> that lets a <code>DroidParts</code> or <code>OILCAN</code> be used on <code>Droids</code>.
 * 
 * @author WENGER_OUT
 */

public class UseOnDroids extends SWAffordance  {
	
	/**
	 * Perform the <code>UseOnDroids</code> action by using <code>DroidParts</code> or <code>OILCAN</code>
	 * on <code>Droids</code>
	 * 
	 * 
	 * @author 	WENGER_OUT
	 * @param 	a the <code>Droids</code> that is taking the target
	 * @see 	{@link #theTarget}
	 * @see		{@link starwars.Droids()}
	 */
	
	public UseOnDroids(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns if or not this <code>UseOnDroid</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if <code>a</code> can use it on Droid.
	 *  
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code>can use it on <code>Droids</code>
	 * @see		{@link starwars.SWActor
	 * @see		starwars.Droids
	 * @see		starwars.DroidParts
	 * @see		starwars.OILCAN
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		SWEntityInterface item = a.getItemCarried();
		
		if (item!= null) {
			
			ArrayList<Droids> allImmobileDroids = this.getNearbyImmobileDroids(a);
			ArrayList<Droids> allMobileDroids = this.getNearbyMobileDroids(a);
		
			
			if (target instanceof DroidParts && allImmobileDroids.size()!=0){
				return true;
			}
			
			if (target instanceof OILCAN && allMobileDroids.size()!=0){
				return true;
			}

		}
		return false;
	}

	/**
	 * Perform the <code>UseOnDroid</code> on <code>Droids</code>
	 * <p>
	 * Used to revive a immobilized droid by using droid parts or heal a droid with less HP using oilcan
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code> that is using it on <code>Droids</code>
	 * @see 	{@link #target}
	 * @see		{@link starwars.SWActor}
	 * @see		{@link starwars.actors.Droids}
	 * @see		starwars.DroidParts
	 * @see		starwars.OILCAN
	 */
	@Override
	public void act(SWActor a) {
		SWEntityInterface item = a.getItemCarried();
		ArrayList<Droids> allDroids = null;
		if (target instanceof DroidParts){
			for (Affordance aff: item.getAffordances()) {
				if (aff instanceof Leave){
					target.removeAffordance(aff);
					break;
				}
			}	
			a.setItemCarried(null);
			allDroids = this.getNearbyImmobileDroids(a);
		}
		else if (target instanceof OILCAN){
			allDroids = this.getNearbyMobileDroids(a);
		}
		
		a.say(a.getShortDescription() + " is Using " + target.getShortDescription() + " On a Droid!");
		Droids randomDroid = allDroids.get((int) (Math.floor(Math.random() * allDroids.size())));
				
		if (target instanceof OILCAN){
			randomDroid.setHitpoints(((OILCAN) target).getHealpoints() + randomDroid.getHitpoints());
		}
		if (target instanceof DroidParts){
			randomDroid.setisImmobile(false);
			randomDroid.setHitpoints(((DroidParts) target).getHealpoints());
			a.say(randomDroid.getShortDescription() + " has become mobile again");
		}
		
		a.say(randomDroid.getShortDescription() + " health has increased to " + randomDroid.getHitpoints());
		
	}
		
	private ArrayList<Droids> getNearbyImmobileDroids(SWActor a){
		
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
	
	private ArrayList<Droids> getNearbyMobileDroids(SWActor a){
		
		SWWorld world = a.getWorld();
		EntityManager em = world.getEntitymanager();
		Location loc = em.whereIs(a);
				
		List<SWEntityInterface> entities = em.contents(loc);
		
		ArrayList<Droids> allDroids = new ArrayList<Droids>();
		
		for (SWEntityInterface e : entities) {
			// Figure out if we should be attacking this entity
			if( e != a && e instanceof Droids && ((Droids) e).getisImmobile() == false){
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
	 * @return String comprising "Use " and the short description of the target of this <code>Disassemble</code> and " On Droid !..."
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Use " + target.getShortDescription() + " On Droid !...";
	}

}


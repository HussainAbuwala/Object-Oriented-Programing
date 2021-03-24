package starwars.actions;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.entities.DroidParts;
import starwars.entities.actors.BenKenobi;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import starwars.*;
import edu.monash.fit2099.simulator.time.*;

/**
 * An Affordance for controlling movement of weak-minded organics by people with lots of force.
 * This action is also connected with wielding a Lightsaber (not implemented in this class but when player picks it up)
 * <p>
 * The affordance is offered by weak-minded organics and can only be
 * applied in partnership with an <code>starwars</code> that is  <code>PeopleWithForce</code>
 * and has ForceAbility equal to 100
 * 
 * @author WENGER_OUT
 * @see {@link starwars.PeopleWithForce}
 * @see {@link starwars.WeakMinded}
 * @see {@link starwars.entities.LightSaber}
 */

public class Force extends SWAffordance{

	public Force(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Determine whether a particular <code>SWActor a</code> can use Force on the target.
	 * 
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if any <code>SWActor</code> has lots of force (forceAbility = 100) and
	 * 			an instance of <code>WeakMinded</code> is present in same location
	 */
	@Override
	public boolean canDo(SWActor a) {

		if(((SWActor) target).isDead()) {
			return false;
		} 
		ArrayList<WeakMinded> weakpeoples = this.getNearbyWeakMinded(a);
		if (weakpeoples.size() != 0){
			if (a.getForceAbility() == 100){
				return true;
			}
		}
		return false;
	}

	
	/**
	 * Perform the <code>Force</code> command on an WeakMinded entity.
	 * <p>
	 * This method is only applicable if,
	 * <ul>
	 * 	<li>The target of the <code>Force</code> is WeakMinded (forceAbility = 0) </li>
	 * 	<li>The the <code>SWActor a</code> has lots of force (forceAbility = 100) </li>
	 * </ul>
	 * <p>
	 * This will cause the target to move in the direction desired by the player for one turn
	 * 
	 * 
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code> who is using Force
	 * @pre 	this method should only be called if the <code>SWActor a</code> is alive
	 * @pre		an <code>Force</code> must not be performed on a dead <code>SWActor</code>
	 * @see		starwars.SWActor#isDead()
	 * @see 	starwars.Move
	 * @see		starwars.WeakMinded
	 * @see		starwars.PeopleWithForce
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		//sets entity manager from SWWorld to em
		SWWorld world = a.getWorld();
		EntityManager em = world.getEntitymanager();
		//sets location of SWActor to loc
		Location loc = em.whereIs(a);
		
		//stores all possible directions in a list
		Direction [] allmoves = {CompassBearing.EAST,
                CompassBearing.SOUTH,
                CompassBearing.NORTH,
                CompassBearing.WEST, CompassBearing.SOUTHWEST,
                CompassBearing.SOUTHEAST,
                CompassBearing.NORTHEAST, CompassBearing.NORTHWEST};
		
		
		ArrayList<Direction> validdirections = new ArrayList<Direction>();
		
		//loops through all directions
		for (Direction directions: allmoves){
			
			//checks if going to that direction is possible 
			if (world.canMove(a, directions)){
				validdirections.add(directions);
			}
			
		}
		
		int i = 0;
		messageRenderer.render("Force " + target.getShortDescription() + " In which direction Please Choose An Option: ");
		for (Direction directions: validdirections){
			
			messageRenderer.render(i + " : " +directions);
			i+=1;
			
		}
		
		Scanner sc = new Scanner(System.in);
		int userInput = sc.nextInt();
		
		while (userInput < 0 && userInput >= i){
			
			userInput = sc.nextInt();

			
		}

		
		//Direction dir = validdirections.get((int) (Math.floor(Math.random() * validdirections.size())));
		
		((PeopleWithForce)a).forceWeakMinded(((WeakMinded)target), validdirections.get(userInput));

		a.say(target.getShortDescription() + " is being forced to move to " + validdirections.get(userInput));
		
		

	}

	@Override
	public int compareTo(ActionInterface o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * A String describing what this <code>Force</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "Use Force to move " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Use Force to move " + target.getShortDescription() ;
	}
	
	private ArrayList<WeakMinded> getNearbyWeakMinded(SWActor a){

		//gets the the entity manager from the world
		SWWorld world = a.getWorld();
		EntityManager em = world.getEntitymanager();
		//gets location of SWActor a 
		Location loc = em.whereIs(a);

		//creates a list of SWEntityInterfaces in the location of SWActor a
		List<SWEntityInterface> entities = em.contents(loc);
		
		//creates an arraylist of WeakMinded in the location of SWActor a
		ArrayList<WeakMinded> weakpeoples = new ArrayList<WeakMinded>();

		for (SWEntityInterface e : entities) {
			// Figure out if we should be using force on this entity
			if( e != a && e instanceof WeakMinded){
				weakpeoples.add((WeakMinded) e);
			}
		}

		//returns a list of the WeakMinded people in location of SWActor a
		return weakpeoples;

	}
	
}
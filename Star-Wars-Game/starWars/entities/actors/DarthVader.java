package starwars.entities.actors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Droids;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLegend;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Attack;
import starwars.actions.ForceChoke;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.AttackInformation;


/**
 * Darth Vader (aka Anakin Skywalker).  
 * 
 * At this stage, he's an extremely strong critter with a <code>LightSaber</code>
 * who wanders around randomly and 50% chance of using <code>ForceChoke</code> 
 * on any other <code>SWActor</code> (regardless of team) except for Droids.
 * 
 * He has a special action when he meets Luke, where he has a 50% chance of trying to 
 * convince him to join <code>Team</code> EVIL and the other 50% to attack Luke with
 * his <code>LightSaber</code>
 * 
 * Note that you can only create ONE Darth Vader, like all SWLegends.
 * @author WENGER_OUT
 *
 */

public class DarthVader extends SWLegend {

	private static DarthVader darthVader = null; // yes, it is OK to return the static instance!
	
	/**
	 * @param hitpoints
	 *            the number of hit points of this <code>DarthVader</code>

	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which
	 *            <code>DarthVader</code> belongs to
	 */
	
	protected DarthVader(MessageRenderer m, SWWorld world) {
		super(Team.EVIL, 10000, m, world);
		this.setForceAbility(100);
		this.setShortDescription("Darth Vader");
		this.setLongDescription("Darth Vader, aka Anakin Skywalker, once believed by some to be the prophesied Chosen One who would bring balance to the Force.");
		
		// TODO Auto-generated constructor stub
	}
	
	public static DarthVader getDarthVader(MessageRenderer m, SWWorld world) {
		darthVader = new DarthVader(m, world);
		darthVader.activate();
		return darthVader;
	}
	
	
		
	/**
	 * 50% probability of force choking other <code>SWActor</code>
	 * He has a special action when he meets Luke, where he has a 50% chance of trying to 
	 * convince him to join <code>Team</code> EVIL and the other 50% to attack Luke with
	 * his <code>LightSaber</code>
	 * @see starwars.SWLegend#legendAct()
	 */
	
	@Override
	protected void legendAct() {
		// TODO Auto-generated method stub
		
		//if dead, nothing happens
		if (isDead()) {
			return;
		}
		
		say(describeLocation());
		
		
		SWWorld world = this.getWorld();
		EntityManager em = world.getEntitymanager();
		Location loc = em.whereIs(this);
		
		List<SWEntityInterface> entities = em.contents(loc);
		
		SWActor luke = null;
		
		for (SWEntityInterface e : entities) {
			// checks if Luke present in entity manager
			if( e != this && e instanceof Player){
				luke = (SWActor)e;
				break;
			}
		}
		
		
		if (luke!= null){
			if (Math.random() < 0.5){
				say(getShortDescription() + " says, 'Obi-Wan never told you what happened to your father' ");
				say(luke.getShortDescription() + " says, 'He told me enough. He told me you killed him!' ");
				say(getShortDescription() + " says, 'No, I am your father!!' ");
				say(getShortDescription() + " is trying to convince " + luke.getShortDescription() + " to join the dark side!");
				if (luke.getForceAbility() > 25){
					if (Math.random() < 0.75){
						say(getShortDescription() + " has convinced " + luke.getShortDescription() + " to join the dark side!!!");
						luke.setTeam(Team.EVIL);
					}
					else{
						say(luke.getShortDescription() + " has resisted the temptation of The Dark Side!");
					}
				}
				else{
					say(getShortDescription() + " has convinced " + luke.getShortDescription() + " to join the dark side!!!");
					luke.setTeam(Team.EVIL);
				}	
			
			}
			else{
				say(getShortDescription() + " is attacking " + luke.getShortDescription());	
			}
							//attacks Luke

			for (Affordance a : luke.getAffordances()) {
				if (a instanceof Attack) {
					scheduler.schedule(a,this,1);
					return;
				}
			}
		
			
				}
		else{
						//gets list of actors			

			ArrayList<SWActor> actors = this.getNearbyActors(this);
						//checks if actors is empty

			if (actors.size() != 0){
								//if not empty, there is a 50% chance of force choking a random actor in the location

				if (Math.random() < 0.5){
					SWActor target = actors.get((int) (Math.floor(Math.random() * actors.size())));
					scheduler.schedule(new ForceChoke(target,messageRenderer),this,1);
					return;
				}
				
				}
			
			ArrayList<Direction> possibledirections = new ArrayList<Direction>();

			// build a list of available directions
			for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
				if (SWWorld.getEntitymanager().seesExit(this, d)) {
					possibledirections.add(d);
				}
			}
			//code for moving randomly on the map

			Direction heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
			say(getShortDescription() + "is heading " + heading + " next.");
			Move myMove = new Move(heading, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
			
			return;
		}
			}
	
	
	private ArrayList<SWActor> getNearbyActors(SWActor a){

		//gets the the entity manager from the world
		SWWorld world = a.getWorld();
		EntityManager em = world.getEntitymanager();
		//gets location of SWActor a 
		Location loc = em.whereIs(a);

		//creates a list of SWEntityInterfaces in the location of SWActor a
		List<SWEntityInterface> entities = em.contents(loc);
		
		//creates an arraylist of WeakMinded in the location of SWActor a
		ArrayList<SWActor> actors = new ArrayList<SWActor>();

		for (SWEntityInterface e : entities) {
			// Figure out if we should be using force on this entity
			if( e != a && !(e instanceof Droids) && !((SWActor) e).isDead()){
				actors.add((SWActor) e);
			}
		}

		//returns a list of the WeakMinded people in location of SWActor a
		return actors;

	}
	
	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
		}
		
	



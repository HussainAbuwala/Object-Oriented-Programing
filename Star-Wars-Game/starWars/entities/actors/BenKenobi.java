package starwars.entities.actors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWEntityInterface;
import starwars.SWLegend;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.*;
import starwars.actions.Take;
import starwars.entities.*;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;
import starwars.entities.actors.behaviors.Patrol;

/**
 * Ben (aka Obe-Wan) Kenobi.  
 * 
 * At this stage, he's an extremely strong critter with a <code>Lightsaber</code>
 * who wanders around in a fixed pattern and neatly slices any Actor not on his
 * team with his lightsaber.
 * 
 * Note that you can only create ONE Ben, like all SWLegends.
 * @author rober_000
 *
 */
public class BenKenobi extends SWLegend {

	private static BenKenobi ben = null; // yes, it is OK to return the static instance!
	private Patrol path;
	SWEntityInterface bensitem = null;
	Location bensitemloc = null;
	
	private BenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		super(Team.GOOD, 1000, m, world);
		path = new Patrol(moves);
		this.setHitpoints(1000);
		this.setForceAbility(100);
		this.setShortDescription("Ben Kenobi");
		this.setLongDescription("Ben Kenobi, an old man who has perhaps seen too much");
		LightSaber bensweapon = new LightSaber(m);		//this is done to 
		setItemCarried(bensweapon);
		this.addAffordance(new Train(this,m));
	}

	public static BenKenobi getBenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		ben = new BenKenobi(m, world, moves);
		ben.activate();
		return ben;
	}
	
	@Override
	protected void legendAct() {


		if(isDead()) {
			return;
		}
		
	

		

		SWWorld world = this.getWorld();
		EntityManager em = world.getEntitymanager();
		EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();
		Location loc = em.whereIs(this);
		List<SWEntityInterface> entities = em.contents(loc);

		
		ArrayList<Player> luke = new ArrayList<Player>();
		
		// Figure out if there is luke in the same location
		for (SWEntityInterface e : entities) {
			// Figure out if we should be attacking this entity
			if( e != this && e instanceof Player){
				luke.add((Player) e);
			}
			}
		
		// If luke is found, and his force ability is < 100, then he can be trained.
		if (luke.size() != 0){
			if (luke.get((int) (Math.floor(Math.random() * luke.size()))).getForceAbility() < 100){
				return;
			}
		}
		
		//If ben's item is still on the floor, pick it up
		if (this.getItemCarried()== null && entityManager.whereIs(bensitem) != null && entityManager.whereIs(bensitem).equals(bensitemloc)){
			
			for (Affordance aff: bensitem.getAffordances()) {
				if (aff instanceof Take){
					scheduler.schedule(aff,this,1);
					bensitem = null;
					bensitemloc = null;
					return;
				}	
			}
			
		}
		
		//If canteen is empty or ben's hitpoint is max, leave the canteen
		if (this.getItemCarried()!= null && this.getItemCarried() instanceof Canteen){
				if ( this.getHitpoints() == this.getMaxHitpoints() || ((Canteen) this.getItemCarried()).isEmpty()){
			
					for (Affordance aff: this.getItemCarried().getAffordances()) {
						if (aff instanceof Leave){
							scheduler.schedule(aff,this,1);
							return;
						}	
					}
				}
			
			
		}
		
		//If ben is holding a canteen and his hitpoints is not max, drink from the canteen.
		if (this.getItemCarried()!= null && this.getItemCarried() instanceof Canteen && this.getHitpoints()< this.getMaxHitpoints()){
			for (Affordance aff: this.getItemCarried().getAffordances()) {
				if (aff instanceof Drink){
					scheduler.schedule(aff,this,1);
					return;
				}	
			}

		}

		//if ben's hitpoints is less and there is a canteen on the same location, pick it up
		if (this.getHitpoints() < this.getMaxHitpoints()){

			SWEntityInterface item = null;
			//Affordances of the Entities in the same location
			for (SWEntityInterface e: entities) {
				if (e instanceof Canteen) { //don't add the affordances if they belong to the actor itself
					item =  e;
					break;
				}
			}

			if (item!= null){

				
				
				if (this.getItemCarried() != null){

					for (Affordance aff: this.getItemCarried().getAffordances()) {
						if (aff instanceof Take){
							this.getItemCarried().removeAffordance(aff);
						}
					}

					bensitem = this.getItemCarried();
					
					//Leave what ever is in bens hand, leave it as he needs to pick up the canteen.
					this.getItemCarried().addAffordance(new Leave(bensitem,messageRenderer));
					for (Affordance aff: bensitem.getAffordances()) {
						if (aff instanceof Leave){
							scheduler.schedule(aff,this,1);
							//entityManager.setLocation(bensitem, entityManager.whereIs(this));
							return;
						}
					}
					

				}
				
				bensitemloc = entityManager.whereIs(bensitem);
				for (Affordance aff: item.getAffordances()) {
					if (aff instanceof Take){
						scheduler.schedule(aff,this,1);
						return;
					}
				}
			}
		}


		AttackInformation attack;
		attack = AttackNeighbours.attackLocals(ben,  ben.world, true, true);

		if (attack != null) {
			say(getShortDescription() + " suddenly looks sprightly and attacks " +
					attack.entity.getShortDescription());
			scheduler.schedule(attack.affordance, ben, 1);
		}
		else {
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
	}

}

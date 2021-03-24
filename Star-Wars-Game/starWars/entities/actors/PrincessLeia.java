package starwars.entities.actors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Droids;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLegend;
import starwars.SWWorld;
import starwars.Team;

/**
 * Princess Leia Organa.  
 * 
 * She starts as a prisoner at DeathStar. Once Luke reaches her location, she starts
 * following Luke.
 * 
 * Death of Princess Leia causes the game to be lost.
 * 
 * 
 * Note that you can only create ONE Princess Leia, like all SWLegends.
 * @author WENGER_OUT
 *
 */

public class PrincessLeia extends SWLegend {

	private static PrincessLeia leia = null; // yes, it is OK to return the static instance!

	/**
	 * @param hitpoints
	 *            the number of hit points of this <code>PrincessLeia</code>

	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which
	 *            <code>PrincessLeia</code> belongs to
	 */
	protected PrincessLeia(int hitpoints, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, hitpoints, m, world);
		this.setShortDescription("Princess Leia Organa");
		this.setLongDescription("Princess Leia Organa, one of the Rebel Alliance’s greatest leaders");
		// TODO Auto-generated constructor stub
	}
	
	public static PrincessLeia getPrincessLeia(MessageRenderer m, SWWorld world) {
		leia = new PrincessLeia(100,m, world);
		leia.activate();
		return leia;
	}

	/**
	 * She will stay still until Luke arrives at her position.
	 * When Luke arrives, she will automatically follow him.
	 * @see starwars.SWLegend#legendAct()
	 */
	@Override
	protected void legendAct() {
			// TODO Auto-generated method stub
		
		//checks if she is already following
		if (this.getIsFollowing() == true){
			return;
		}
		
		//gets location of Leia
		SWWorld world = this.getWorld();
		EntityManager em = world.getEntitymanager();
		Location loc = em.whereIs(this);
		
		List<SWEntityInterface> entities = em.contents(loc);
		
		SWActor luke = null;
		
		for (SWEntityInterface e : entities) {
			//checks if Luke present in Entity Manager
			if( e != this && e instanceof Player){
				luke = (SWActor)e;
				break;
			}
			
			
		}
		//if Luke is present
		if (luke!=null){
			//Leia is added to Luke's follower list
			luke.addFollowers(this);
			//Leia's IsFollowing is set to true
			this.setIsFollowing(true);
			return;
		}
		
	}

}

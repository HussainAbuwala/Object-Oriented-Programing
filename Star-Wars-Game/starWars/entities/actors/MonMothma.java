package starwars.entities.actors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Droids;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWGrid;
import starwars.SWLegend;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;

/**
 * Mon Mothma.  
 * 
 * She just stays at YavinIV and checks that when Luke arrives in YavinIV, whether he
 * is accompanied by both <code>R2D2</code> and <code>PrincessLeia</code>. If not, 
 * she says, "What are you doing here, farmboy? Bring us General Organa and the plans!"
 * 
 * Successfully bringing both characters to Mon Mothma's location causes the player to
 * win the game.
 * 
 * Note that you can only create ONE Mon Mothma, like all SWLegends.
 * @author WENGER_OUT
 *
 */

public class MonMothma extends SWLegend {

	private static MonMothma monMothma = null; // yes, it is OK to return the static instance!
	private Boolean hasSaid = false;
	private SWLocation previousLoc;
	
	/**
	 * @param hitpoints
	 *            the number of hit points of this <code>MonMothma</code>

	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which
	 *            <code>MonMothma</code> belongs to
	 */
	protected MonMothma(int hitpoints, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, hitpoints, m, world);
		// TODO Auto-generated constructor stub
		this.setShortDescription("MonMothma");
		this.setLongDescription("Leader of the Galactic Senate's Loyalist faction,");
		
	}
	
	public static MonMothma getMonMothma(int hitpoints,MessageRenderer m, SWWorld world) {
		monMothma = new MonMothma(hitpoints,m, world);
		monMothma.activate();
		return monMothma;
	}

	/**
	 * Mon Mothma constantly checks if Luke has arrived in YavinIV.
	 * If he does, she checks if both <code>R2D2 </code> and
	 * <code>PrincessLeia</code> is with him, if not she outputs
	 * a quote. 
	 */
	@Override
	protected void legendAct() {
		
		SWWorld world = this.getWorld();
		SWGrid  grid = world.getYavinIV();
		EntityManager em = world.getEntitymanager();
		
		SWLocation loc;
		
		
		//checks if Luke has left Yavin IV after displaying quote
		if (grid.getIsLukeHere() == false && this.hasSaid == true){
			this.hasSaid = false;
			return;
		}
		
		//checks if Luke is still there are after displaying quote
		if (grid.getIsLukeHere() == true && this.hasSaid == true){
			return;
		}
		
		loc = grid.getLocationByCoordinates(0,0);
		
		List<SWEntityInterface> entities = em.contents(loc);
		
		ArrayList<SWActor> checkActors = new ArrayList<SWActor>();
		
		//checks if Luke, R2D2 and PrincessLeia present
		for (SWEntityInterface e : entities) {
			if( e != this && e instanceof R2D2){
				checkActors.add((SWActor) e);
			}
			else if ( e != this && e instanceof PrincessLeia){
				checkActors.add((SWActor) e);
			}
			else if ( e != this && e instanceof Player){
				checkActors.add((SWActor) e);
			}
			}
		
		//displays quote
		if (checkActors.size() == 2 || checkActors.size() == 1){
			this.say( this.getShortDescription() + " says, What are you doing here, farmboy? Bring us General Organa and the plans!");
			this.hasSaid = true;
		}

		
		
	}

}

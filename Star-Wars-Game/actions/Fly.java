package starwars.actions;

import java.util.ArrayList;
import java.util.Scanner;

import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWGrid;
import starwars.SWLocation;
import starwars.SWWorld;

/**
 * Command to Fly to other <code>SWGrid</code> 
 * 
 * This affordance is attached to all <code>MillenniumFalcon</code>
 * 
 * @author WENGER_OUT
 */

public class Fly extends SWAffordance {
	
	/**
	 * Constructor for the <code>Fly</code> Class. Will initialize the message renderer, the target.
	 * 
	 * @param theTarget
	 * @param m the message renderer to display messages
	 */
	public Fly(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Perform the <code>Fly</code> command
	 * <p>
	 * This will teleport the <code>Player</code> and other <code>SWActor</code>
	 * following the <code>Player</code> to the desired <code>SWGrid</code>
	 * 
	 * @author 	WENGER_OUT
	 * @param 	a the <code>SWActor</code>, in this case the <code>Player</code>
	 * @pre 	this method should only be called if the <code>SWActor a</code> is alive
	 * @see		starwars.entities.MillenniumFalcon
	 * @see		starwars.SWGrid
	 */
	@Override
	public void act(SWActor a) {
		
		//gets world
		SWWorld world = a.getWorld();
		EntityManager em = world.getEntitymanager();
		
		//gets grid
		SWLocation where = (SWLocation)em.whereIs(a); // requires a cast for no reason I can discern
		SWGrid  grid;
		
		//adds in the options ArrayList the other grids that can be visited 
		ArrayList<String> options = new ArrayList<String>();
		if (where.getWhichGrid().equals("Tatooine")){
			grid = world.getTatooine();
			grid.setIsLukeHere(false);
			options.add("YavinIV");
			options.add("DeathStar");
		}
		else if (where.getWhichGrid().equals("DeathStar")){
			grid = world.getDeathStar();
			grid.setIsLukeHere(false);
			options.add("YavinIV");
			options.add("Tatooine");
		}
		else{
			grid = world.getYavinIV();
			grid.setIsLukeHere(false);
			options.add("DeathStar");
			options.add("Tatooine");
		}
		
		//Renders the options in display
		for (int y = 0; y < options.size(); y++){
			messageRenderer.render(y + " : Fly to " +options.get(y));
		}
		
		//takes input to determine which grid to go/display
		int i = 0;
		Scanner sc = new Scanner(System.in);
		int userInput = sc.nextInt();
		
		while (userInput < 0 && userInput > options.size() - 1){
			userInput = sc.nextInt();			
		}
		
		//sets the grid according to the option chosen
		//setIsLukeHere of the chosen grid is set to True
		if (options.get(userInput).equals("Tatooine")){
			grid = world.getTatooine();
			grid.setIsLukeHere(true);
			
		}
		else if (options.get(userInput).equals("DeathStar")){
			grid = world.getDeathStar();
			grid.setIsLukeHere(true);

		}
		else{
			grid = world.getYavinIV();
			grid.setIsLukeHere(true);
		}
		
		//sets the characters following Luke to the same position in the new grid as well
		SWLocation loc = null;
		ArrayList<SWActor> cloneFollowers = a.clonefollowers();
		for (SWActor actors : cloneFollowers){
			loc = grid.getLocationByCoordinates(0,0);
			em.setLocation(actors, loc);
		}
		loc = grid.getLocationByCoordinates(0,0);
		em.setLocation(a, loc);

	}

	@Override
	public int compareTo(ActionInterface o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * A String describing what this <code>Fly</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "FLY: " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "FLY: ";
	}

}

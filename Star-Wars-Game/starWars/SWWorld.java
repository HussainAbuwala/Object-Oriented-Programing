package starwars;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.space.World;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Take;
import starwars.entities.*;
import starwars.entities.actors.*;

/**
 * Class representing a world in the Star Wars universe. 
 * 
 * @author ram
 */
/*
 * Change log
 * 2017-02-02:  Render method was removed from Middle Earth
 * 				Displaying the Grid is now handled by the TextInterface rather 
 * 				than by the Grid or MiddleWorld classes (asel)
 */
public class SWWorld extends World {
	
	/**
	 * <code>SWGrid</code> of this <code>SWWorld</code>
	 */
	private SWGrid Tatooine;
	private SWGrid DeathStar;
	private SWGrid YavinIV;
	
	private Player luke;
	private DarthVader dvader;
	private R2D2 r2d2;
	private PrincessLeia leia;
	
	/**The entity manager of the world which keeps track of <code>SWEntities</code> and their <code>SWLocation</code>s*/
	private static final EntityManager<SWEntityInterface, SWLocation> entityManager = new EntityManager<SWEntityInterface, SWLocation>();
	
	/**
	 * Constructor of <code>SWWorld</code>. This will initialize the <code>SWLocationMaker</code>
	 * and the grid.
	 */
	public SWWorld() {
		SWLocation.SWLocationMaker factory = SWLocation.getMaker();
		Tatooine = new SWGrid(10,10,factory);
		DeathStar = new SWGrid(10,10,factory);
		YavinIV = new SWGrid(2,2,factory);
		
	}
		/**This method returns the Tatooine grid.*/

	public SWGrid getTatooine(){
		return Tatooine;
	}
		/**This method returns the DeathStar grid.*/

	public SWGrid getDeathStar(){
		return DeathStar;
	}
		/**This method returns the YavinIV grid.*/

	public SWGrid getYavinIV(){
		return YavinIV;
	}
		/**This method checks if darthVader is dead or not.*/

	public Boolean checkIfDarthVaderIsDead() {
		return this.dvader.isDead();
	}
		/**This method checks if princess leia is dead or not*/

	public Boolean checkIfPrincessLeiaIsDead(){
		return this.leia.isDead();
	}
		/**This method checks if luke is dead or not*/

	public Boolean checkIfLukeIsDead(){
		return this.luke.isDead();
	}
		/**This method check if luke has turned to the dark side or not.*/

	public Boolean checkIfLukeEvil(){
		if (this.luke.getTeam() == Team.EVIL){
			return true;
		}
		return false;
	}
		/**This method checks if r2d2 is disassembled*/

	public Boolean checkIfr2d2IsDissassembled(){
		
		Location loc = this.entityManager.whereIs(this.r2d2);
		if (loc == null){
			return true;
		}
		return false;
	}
	
	
	
	

	/** 
	 * Returns the height of the <code>Grid</code>. Useful to the Views when rendering the map.
	 * 
	 * @author ram
	 * @return the height of the grid
	 */
	public int height(SWGrid space) {
		return space.getHeight();
	}
	
	/** 
	 * Returns the width of the <code>Grid</code>. Useful to the Views when rendering the map.
	 * 
	 * @author ram
	 * @return the height of the grid
	 */
	public int width(SWGrid space) {
		return space.getWidth();
	}
	
	/**
	 * Set up the world, setting descriptions for locations and placing items and actors
	 * on the grid.
	 * 
	 * @author 	ram
	 * @param 	iface a MessageRenderer to be passed onto newly-created entities
	 */
	public void initializeWorld(MessageRenderer iface) {
		SWLocation loc;
		// Set default location string
		for (int row=0; row < height(Tatooine); row++) {
			for (int col=0; col < width(Tatooine); col++) {
				loc = Tatooine.getLocationByCoordinates(col, row);
				loc.setLongDescription("Tatooine (" + col + ", " + row + ")");
				loc.setShortDescription("Tatooine (" + col + ", " + row + ")");
				loc.setSymbol('.');
				loc.setWhichGrid("Tatooine");
			}
		}
		
		
		// BadLands
		for (int row = 5; row < 8; row++) {
			for (int col = 4; col < 7; col++) {
				loc = Tatooine.getLocationByCoordinates(col, row);
				loc.setLongDescription("Tatooine's Badlands (" + col + ", " + row + ")");
				loc.setShortDescription("Tatooine's Badlands (" + col + ", " + row + ")");
				loc.setSymbol('b');
			}
		}
		
		//Ben's Hut
		loc = Tatooine.getLocationByCoordinates(5, 6);
		loc.setLongDescription("Ben's Hut");
		loc.setShortDescription("Ben's Hut");
		loc.setSymbol('H');
		
		
		
		Direction [] patrolmoves = {CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.SOUTH,
                CompassBearing.WEST, CompassBearing.WEST,
                CompassBearing.SOUTH,
                CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.NORTHWEST, CompassBearing.NORTHWEST};
		
		BenKenobi ben = BenKenobi.getBenKenobi(iface, this, patrolmoves);
		ben.setSymbol("B");
		loc = Tatooine.getLocationByCoordinates(4,  5);
		entityManager.setLocation(ben, loc);
		
		
		loc = Tatooine.getLocationByCoordinates(9,0);
		
		/*
		 * 
		 * 		// Luke
		
		Player luke = new Player(Team.GOOD, 100, iface, this);
		luke.setShortDescription("Luke");
		entityManager.setLocation(luke, loc);
		//luke.addCapability(Capability.REPAIR);
		luke.resetMoveCommands(loc);
		 
		 */

		
		
		// Beggar's Canyon 
		for (int col = 3; col < 8; col++) {
			loc = Tatooine.getLocationByCoordinates(col, 8);
			loc.setShortDescription("Tatooine's Beggar's Canyon (" + col + ", " + 8 + ")");
			loc.setLongDescription("Tatooine's Beggar's Canyon  (" + col + ", " + 8 + ")");
			loc.setSymbol('C');
			loc.setEmptySymbol('='); // to represent sides of the canyon
		}
		
		// Moisture Farms
		for (int row = 0; row < 10; row++) {
			for (int col = 8; col < 10; col++) {
				loc = Tatooine.getLocationByCoordinates(col, row);
				loc.setLongDescription("Tatooine's Moisture Farm (" + col + ", " + row + ")");
				loc.setShortDescription("Tatooine's Moisture Farm (" + col + ", " + row + ")");
				loc.setSymbol('F');
				
				// moisture farms have reservoirs
				entityManager.setLocation(new Reservoir(iface), loc);				
			}
		}
		
		WeakMinded UncleOwen = new WeakMinded(Team.GOOD,10,iface, this);
		UncleOwen.setShortDescription("Uncle Owen");
		UncleOwen.setSymbol("U");
		loc = Tatooine.getLocationByCoordinates(9,0);
		entityManager.setLocation(UncleOwen, loc);
		
		WeakMinded AuntBeru = new WeakMinded(Team.GOOD,10,iface, this);
		AuntBeru.setShortDescription("Aunt Beru");
		AuntBeru.setSymbol("A");
		loc = Tatooine.getLocationByCoordinates(9,0);
		entityManager.setLocation(AuntBeru, loc);
		
		// Ben Kenobi's hut
		/*
		 * Scatter some other entities and actors around
		 */
		// a canteen
		loc = Tatooine.getLocationByCoordinates(6,5);
		SWEntity canteen = new Canteen(iface, 20,20);
		canteen.setSymbol("o");
		canteen.setHitpoints(500);
		entityManager.setLocation(canteen, loc);
		//canteen.addAffordance(new Take(canteen, iface));
		
		
		loc = Tatooine.getLocationByCoordinates(9,2);
		SWEntity ck = new Canteen(iface, 20,19);
		ck.setSymbol("x");
		ck.setHitpoints(500);
		entityManager.setLocation(ck, loc);
		
		// a lightsaber
		LightSaber lightSaber = new LightSaber(iface);
		loc = Tatooine.getLocationByCoordinates(5,5);
		entityManager.setLocation(lightSaber, loc);
		
		// A blaster 
		Blaster blaster = new Blaster(iface);
		loc = Tatooine.getLocationByCoordinates(3, 4);
		entityManager.setLocation(blaster, loc);
		
		// A Tusken Raider 1
		TuskenRaider tim = new TuskenRaider(10, "Tim1", iface, this);
		
		tim.setSymbol("T");
		loc = Tatooine.getLocationByCoordinates(9,0);
		entityManager.setLocation(tim, loc);
		
		//----------------------------------------CHANGES MADE (start)------------------------------------//------
		//Tusken Raider 2
		TuskenRaider tim2 = new TuskenRaider(10, "Tim2", iface, this);
		
		tim2.setSymbol("H");
		loc = Tatooine.getLocationByCoordinates(4,1);
		entityManager.setLocation(tim2, loc);
		
		//Tusken Raider 3
		TuskenRaider tim3 = new TuskenRaider(10, "Tim3", iface, this);
		
		tim3.setSymbol("M");
		loc = Tatooine.getLocationByCoordinates(4,9);
		entityManager.setLocation(tim3, loc);
		
		//Tusken Raider 4
		TuskenRaider tim4 = new TuskenRaider(10, "Tim4", iface, this);
				
		tim4.setSymbol("Y");
		loc = Tatooine.getLocationByCoordinates(1,1);
		entityManager.setLocation(tim4, loc);
		
		
		R2D2 r2d2 = new R2D2(50,iface,this);
		r2d2.setSymbol("D");
		loc = Tatooine.getLocationByCoordinates(0,0);
		entityManager.setLocation(r2d2, loc);
		
		C3PO c3po = new C3PO(50,iface,this);
		c3po.setSymbol("Z");
		loc = Tatooine.getLocationByCoordinates(1,9);
		entityManager.setLocation(c3po, loc);
		
		Droids droid1 = new Droids(50,iface,this);
		droid1.setSymbol("I");
		droid1.setisImmobile(true);
		droid1.setShortDescription("BB-8");
		loc = Tatooine.getLocationByCoordinates(5,7);
		entityManager.setLocation(droid1, loc);
		
		Droids droid2 = new Droids(50,iface,this);
		droid2.setisImmobile(true);
		droid2.setSymbol("O");
		droid2.setShortDescription("IG-88");
		loc = Tatooine.getLocationByCoordinates(8,7);
		entityManager.setLocation(droid2, loc);
		
		Droids droid3 = new Droids(50,iface,this);
		droid3.setSymbol("P");
		droid3.setShortDescription("MM-47");
		loc = Tatooine.getLocationByCoordinates(8,0);
		entityManager.setLocation(droid3, loc);
		
		Droids droid4 = new Droids(50,iface,this);
		droid4.setSymbol("V");
		droid4.setisImmobile(true);
		droid4.setShortDescription("UU-100");
		loc = Tatooine.getLocationByCoordinates(9,2);
		entityManager.setLocation(droid4, loc);
		
		Droids droid5 = new Droids(50,iface,this);
		droid5.setSymbol("L");
		droid5.setisImmobile(true);
		droid5.setShortDescription("UU-100");
		loc = Tatooine.getLocationByCoordinates(9,4);
		entityManager.setLocation(droid5, loc);
		
		// a droid part
		DroidParts part = new DroidParts(iface);
		loc = Tatooine.getLocationByCoordinates(8,8);
		entityManager.setLocation(part, loc);
		
		// an oil can
		OILCAN oilcan = new OILCAN(iface);
		loc = Tatooine.getLocationByCoordinates(1,3);
		entityManager.setLocation(oilcan, loc);
		
		//----------------------------------------CHANGES MADE (end)------------------------------------//------

		
		
		//DEATH STAR
		// Set default location string
		for (int row=0; row < height(DeathStar); row++) {
			for (int col=0; col < width(DeathStar); col++) {
				loc = DeathStar.getLocationByCoordinates(col, row);
				loc.setLongDescription("DeathStar (" + col + ", " + row + ")");
				loc.setShortDescription("DeathStar (" + col + ", " + row + ")");
				loc.setSymbol('_');				
				loc.setWhichGrid("DeathStar");
			}
		}
		
		loc = DeathStar.getLocationByCoordinates(2,0);
		Stormtrooper ST1 = new Stormtrooper(100,iface,this);
		ST1.setSymbol("S");
		entityManager.setLocation(ST1, loc);
		
		
		loc = DeathStar.getLocationByCoordinates(5,6);
		Stormtrooper ST2 = new Stormtrooper(100,iface,this);
		ST2.setSymbol("S");
		entityManager.setLocation(ST2, loc);
		
		loc = DeathStar.getLocationByCoordinates(5,6);
		Stormtrooper ST3 = new Stormtrooper(100,iface,this);
		ST3.setSymbol("S");
		entityManager.setLocation(ST3, loc);
		
		loc = DeathStar.getLocationByCoordinates(6,5);
		Stormtrooper ST4 = new Stormtrooper(100,iface,this);
		ST4.setSymbol("S");
		entityManager.setLocation(ST4, loc);
		
		loc = DeathStar.getLocationByCoordinates(7,5);
		Stormtrooper ST5 = new Stormtrooper(100,iface,this);
		ST5.setSymbol("S");
		entityManager.setLocation(ST5, loc);
		 
		
		// Luke
		
		loc = Tatooine.getLocationByCoordinates(0,0);
		Tatooine.setIsLukeHere(true);
		
		Player luke = new Player(Team.GOOD, 100, iface, this);
		luke.setShortDescription("Luke");
		entityManager.setLocation(luke, loc);
		//luke.addCapability(Capability.REPAIR);
		luke.resetMoveCommands(loc);
		
		
		
		//YAVIN IV
		// Set default location string
		for (int row=0; row < height(YavinIV); row++) {
			for (int col=0; col < width(YavinIV); col++) {
				loc = YavinIV.getLocationByCoordinates(col, row);
				loc.setLongDescription("YavinIV (" + col + ", " + row + ")");
				loc.setShortDescription("YavinIV (" + col + ", " + row + ")");
				loc.setSymbol('!');		
				loc.setWhichGrid("YavinIV");
			}
		}
		
		// A MFT 
		MillenniumFalcon mfT = new MillenniumFalcon(iface);
		loc = Tatooine.getLocationByCoordinates(0,0);
		entityManager.setLocation(mfT, loc);
		
		MillenniumFalcon mfD = new MillenniumFalcon(iface);
		loc = DeathStar.getLocationByCoordinates(0,0);
		entityManager.setLocation(mfD, loc);
		
		MillenniumFalcon mfY = new MillenniumFalcon(iface);
		loc = YavinIV.getLocationByCoordinates(0,0);
		entityManager.setLocation(mfY, loc);
		
		
		loc = YavinIV.getLocationByCoordinates(0,1);
		AdmiralAckbar admiral = AdmiralAckbar.getAdmiralAckbar(100,iface, this);
		entityManager.setLocation(admiral, loc);
		admiral.setSymbol("A");
		
		loc = YavinIV.getLocationByCoordinates(1,1);
		MonMothma monMothma = MonMothma.getMonMothma(100,iface, this);
		entityManager.setLocation(monMothma, loc);
		monMothma.setSymbol("M");
		
		loc = DeathStar.getLocationByCoordinates(5,5);
		DarthVader darthVader = DarthVader.getDarthVader(iface, this);
		entityManager.setLocation(darthVader, loc);
		darthVader.setSymbol("D");

		
		loc = DeathStar.getLocationByCoordinates(9,0);
		PrincessLeia leia = PrincessLeia.getPrincessLeia(iface, this);
		entityManager.setLocation(leia, loc);
		leia.setSymbol("P");
		
		this.dvader = darthVader;
		this.leia = leia;
		this.luke = luke;
		this.r2d2 = r2d2;
				
		
		
	}
	

	/*
	 * Render method was removed from here
	 */
	
	/**
	 * Determine whether a given <code>SWActor a</code> can move in a given direction
	 * <code>whichDirection</code>.
	 * 
	 * @author 	ram
	 * @param 	a the <code>SWActor</code> being queried.
	 * @param 	whichDirection the <code>Direction</code> if which they want to move
	 * @return 	true if the actor can see an exit in <code>whichDirection</code>, false otherwise.
	 */
	public boolean canMove(SWActor a, Direction whichDirection) {
		SWLocation where = (SWLocation)entityManager.whereIs(a); // requires a cast for no reason I can discern
		return where.hasExit(whichDirection);
	}
	
	/**
	 * Accessor for the grid.
	 * 
	 * @author ram
	 * @return the grid
	 */
	public SWGrid getGrid() {
		
		//WILL RETURN ONLY THE GRID WHERE LUKE IS CURRENTLY INSIDE
		SWLocation where = (SWLocation)entityManager.whereIs(luke); // requires a cast for no reason I can discern
		if (where.getWhichGrid().equals("Tatooine")){
			return Tatooine;
		}
		else if (where.getWhichGrid().equals("DeathStar")){
			return DeathStar;
		}
		else{
			return YavinIV;
		}
		
	}

	/**
	 * Move an actor in a direction.
	 * 
	 * @author ram
	 * @param a the actor to move
	 * @param whichDirection the direction in which to move the actor
	 */
	public void moveEntity(SWActor a, Direction whichDirection) {
		
		//get the neighboring location in whichDirection
		Location loc = entityManager.whereIs(a).getNeighbour(whichDirection);
		
		// Base class unavoidably stores superclass references, so do a checked downcast here
		if (loc instanceof SWLocation)
			//perform the move action by setting the new location to the the neighboring location
			entityManager.setLocation(a, (SWLocation) entityManager.whereIs(a).getNeighbour(whichDirection));
	}

	/**
	 * Returns the <code>Location</code> of a <code>SWEntity</code> in this grid, null if not found.
	 * Wrapper for <code>entityManager.whereIs()</code>.
	 * 
	 * @author 	ram
	 * @param 	e the entity to find
	 * @return 	the <code>Location</code> of that entity, or null if it's not in this grid
	 */
	public Location find(SWEntityInterface e) {
		return entityManager.whereIs(e); //cast and return a SWLocation?
	}

	/**
	 * This is only here for compliance with the abstract base class's interface and is not supposed to be
	 * called.
	 */

	@SuppressWarnings("unchecked")
	public EntityManager<SWEntityInterface, SWLocation> getEntityManager() {
		return SWWorld.getEntitymanager();
	}

	/**
	 * Returns the <code>EntityManager</code> which keeps track of the <code>SWEntities</code> and
	 * <code>SWLocations</code> in <code>SWWorld</code>.
	 * 
	 * @return 	the <code>EntityManager</code> of this <code>SWWorld</code>
	 * @see 	{@link #entityManager}
	 */
	public static EntityManager<SWEntityInterface, SWLocation> getEntitymanager() {
		return entityManager;
	}
}

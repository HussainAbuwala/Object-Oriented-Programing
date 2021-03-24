package starwars.entities;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.*;

/**
 * Droid Parts  
 * 
 * This is used to fix immobile droids.
 * Actors who are holding these parts can repair immobile droids.
 * It has an UseOnDroids Affordance attached to it and a DROIDUSABLE capability attached to it.
 * 
 * 
 * @author WENGER_OUT
 *
 */


public class DroidParts extends SWEntity {
	
	/**
	 * Get heal points of this droid parts. This represents by how may points will a droid be healed after
	 * using the droid parts.
	 */
	private int healPoints;
	
	
	/**
	 * Constructor for the <code>DroidParts</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>DroidParts</code></li>
	 * 	<li>Set the short description of this <code>DroidParts</code>>
	 * 	<li>Set the long description of this <code>DroidParts</code> 
	 * 	<li>Add a <code>Take</code> affordance to this <code>DroidParts</code> so it can be taken</li> 
	 * </ul>
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	
	 */
	
	public DroidParts(MessageRenderer m) {
		super(m);
		
		this.shortDescription = "Droid Part";
		this.longDescription = "you have all these squisy parts";
		this.healPoints = 70;
		this.addAffordance(new Take(this, m));//add the take affordance so that the LightSaber can be taken by SWActors
		this.capabilities.add(Capability.DROIDUSABLE);// it's a weapon.  
		this.addAffordance(new UseOnDroids(this,m));
	}
	/**
	 * A symbol that is used to represent the DroidParts on a text based user interface
	 * 
	 * @return 	A String containing a single character.
	 * @see 	{@link starwars.SWEntityInterface#getSymbol()}
	 */
	@Override
	public String getSymbol() {
		return "*";
	}
	
	/**
	 * Get heal points of this droid parts.
	 * 
	 * @return 	integer of the heal points
	 */
	public int getHealpoints(){
		return this.healPoints;
	}

}

package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Fly;
import starwars.actions.Take;
import starwars.actions.UseOnDroids;
/**
 * The Millenium Falcon is located at the (0,0) co-ordinate of every grid.
 * Luke can use the Millenium Falcon to travel between grids and other 
 * characters following Luke can join him in the journey.
 * 
 *  @author WENGER_OUT
 *  @see {@link starwars.actions.Fly}
 * 
 */
public class MillenniumFalcon extends SWEntity {
	
	public MillenniumFalcon(MessageRenderer m) {
		super(m);
		this.shortDescription = "Milennium Falcon";
		this.longDescription = "FLY FLY FLY!";
		this.addAffordance(new Fly(this,m));
	}
	
	/**
	 * A symbol that is used to represent the Millenium Falcon on a text based user interface
	 * 
	 * @return 	A String containing a single character.
	 * @see 	{@link starwars.SWEntityInterface#getSymbol()}
	 */
	@Override
	public String getSymbol() {
		return ">";
	}
	
}

package starwars.entities.actors;

import starwars.*;
import java.util.ArrayList;
import java.util.Random;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.*;

public class C3PO extends Droids {
	
	/**
	 * This is the arraylist of quotes C3PO has so that it can speak at 10% chance
	 */
	private ArrayList<String> C3POquotes = new ArrayList<String>();
	
	
	/**
	 * Create a C3PO.  
	 * It doesn't move and stays in the same location. It just speaks and it has a 10% chance of saying something at each turn.
	 * 
	 * @param hitpoints
	 *            the number of hit points of this Droid. If this
	 *            decreases to below zero, the Droid will become immobile.

	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>Droid</code> belongs to
	 * 
	 */
	public C3PO (int hitpoints, MessageRenderer m, SWWorld world){
		super(200,m,world);
		this.setShortDescription("C3PO");
		this.setLongDescription("Bleep Bleep, Bloop");
		C3POquotes.add("Sir, the possibility of successfully navigating an asteroid field is approximately 3,720 to 1.");
		C3POquotes.add("Artoo says that the chances of survival are 725 to 1. Actually Artoo has been known to make mistakes... from time to time... Oh dear...");
		C3POquotes.add("Sand is very hot");
		C3POquotes.add("R2 says the chances of survival are 725... to one");
		C3POquotes.add("R2-D2, where are you?");
		C3POquotes.add("What a lovely day!");

}
	
	
	/**
	*It doesn't move and stays in the same location.
	*It just speaks.
	*It has a 10% chance of saying something at each turn.
	 */
	@Override
	public void act(){
		
		//If immobile, dont move
		if (this.getisImmobile() == true) {
			return;
		}
		
		//Select random number of 1 to 10
		int randomNumber = (int) (Math.random() * 10 + 1);
		
		//If number from 1 to 10 is 5, which obviously has a probability of 10%, choose a random quote and say
		if (randomNumber == 5){
			
			String quote = C3POquotes.get((int) (Math.floor(Math.random() * C3POquotes.size())));
			this.say(this.getShortDescription() + " says " + quote);
			
			}
		}
		
}

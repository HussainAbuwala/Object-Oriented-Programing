package starwars;

import edu.monash.fit2099.simulator.space.Decider;
import edu.monash.fit2099.simulator.space.GameOver;

/**
 * This class basically extends the Decider class and passes an SWGameOver object to its parent class.
 * This class basically decides if the game is over or not using the help of the SWGame over class.
 * The SWDecider can also override the canDecide() method in the decider parent class to make their own
 * canDecide method. Also the default banners for win and loss can be overriden to display custom banners for the
 * star wars game.
 * 
 * */

public class SWDecider extends Decider {

	SWDecider(SWGameOver over) {
		super(over);
		// TODO Auto-generated constructor stub
	}

}

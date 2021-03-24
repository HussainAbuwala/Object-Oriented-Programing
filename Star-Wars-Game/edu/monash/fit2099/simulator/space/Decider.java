package edu.monash.fit2099.simulator.space;

/** 
 * This class provides a mechanism for a client to know if their game is over or not. This class is used in
 * relation with the GameOver class. The decider constructor takes a GameOver object as parameter in constructor.
 * A client can actually make a decider for their own game and extend this class. Also they can make their own GameOver
 * and pass it to their decider. 
 * 
 * This class has a canDecide method which the client can loop on until the decider can decide if the game is over or
 * not. If decider can decide, it will return true else it returns false. It decides by using the win() and lose()
 * method in the gameOver class.
 * 
 * This class help's the client to easily develop a win and lose situation for any game. All the client needs to do
 * is implement the win and lose methods and use the decider class to know when the game is decided or not.
 * 
 * To make full use of this class, the client needs to use this class in relation with the GameOver class.
 * @see {@link edu.monash.fit2099.simulator.space.GameOver}
 */

public class Decider {

	GameOver gameOver;
	Boolean showLoseBanner = false;
	Boolean showWinBanner = false;
	
	

	/**
	 * Constructor for an <code>Decider</code> .
	 * 
	 * @param over gameOver object that the decider uses to decide if the game is over or not.
	 */
	
	
	public Decider (GameOver over){
		this.gameOver = over;
	}
	

	/**
	 *This method is called when the game is lost and then a banner is displayed for losing of the game. 
	 */
	
	private void LoseBanner(){
		
		String [] lines = { 
						"",
						"",
						"		 ######      ###    ##     ## ########              #######  ##     ## ######## ########  ",
						"		##    ##    ## ##   ###   ### ##                   ##     ## ##     ## ##       ##     ## ",
						"		##         ##   ##  #### #### ##                   ##     ## ##     ## ##       ##     ## ",
						"		##   #### ##     ## ## ### ## ######               ##     ## ##     ## ######   ########  ",
						"		##    ##  ######### ##     ## ##                   ##     ##  ##   ##  ##       ##   ##   ",
						"		##    ##  ##     ## ##     ## ##                   ##     ##   ## ##   ##       ##    ##  ",
						"		 ######   ##     ## ##     ## ########              #######     ###    ######## ##     ## ",
						};

		for(String line: lines) {
			System.out.println(line);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			}
	

	/**
	 *This method is called when the game is won and then a banner is displayed for winning of the game. 
	 */
	
	
	private void WinBanner(){

		String [] lines = { 
				"",
				"",
				"		 ######      ###    ##     ## ########       ##      ## #### ##    ## ",
				"		##    ##    ## ##   ###   ### ##             ##  ##  ##  ##  ###   ## ",
				"		##         ##   ##  #### #### ##             ##  ##  ##  ##  ####  ## ",
				"		##   #### ##     ## ## ### ## ######         ##  ##  ##  ##  ## ## ## ",
				"		##    ##  ######### ##     ## ##             ##  ##  ##  ##  ##  #### ",
				"		##    ##  ##     ## ##     ## ##             ##  ##  ##  ##  ##   ### ",
				"		 ######   ##     ## ##     ## ########        ###  ###  #### ##    ## "
				};
		
		for(String line: lines) {
			System.out.println(line);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 *This method displays the win banner or the lose banner according to which banner variable is set to true. It
	 *also displays the reason for which the game is over.
	 */
	public void showBanner(){
		
		if (this.showWinBanner == true){
			this.WinBanner();
			System.out.println();
			System.out.println("					" + this.gameOver.getReasonForGameOver());

			return;
		}
		
		this.LoseBanner();
		System.out.println();
		System.out.println("					" + this.gameOver.getReasonForGameOver());

		
		return;
		
		
	}
	/**
	* This class has a canDecide method which the client can loop on until the decider can decide if the game is over or
	* not. If decider can decide, it will return true else it returns false. It decides by using the win() and lose()
	* method in the gameOver class.
	*/
	public Boolean canDecide(){
		
		Boolean checkWin;
		Boolean checkLoss;
		
		checkWin = this.gameOver.win();
		checkLoss = this.gameOver.lose();
		
		if (checkWin == true){
			this.showWinBanner = true;
			return true;
		}
		else if (checkLoss == true){
			this.showLoseBanner = true;
			return true;
		}
		
		return false;
		
	}


}


package edu.monash.fit2099.simulator.space;

import starwars.SWWorld;

/** 
 * This class basically gives a mechanism so that when each client game uses this engine, they can extend or use this
 * class to write ways in which their game is won or lost. By default the win() and lose() method returns false.
 * If any client game wants to write their own code for how their game will be won or lost, they need to create a new 
 * class which extends this class and override the win() and lose() methods. Also they need to specify the reason for 
 * the game over .
 * 
 * To make full use of this class, the client needs to use this class in relation with the Decider class.
 * @see {@link edu.monash.fit2099.simulator.space.Decider}
 */

public class GameOver {

	
	/**
	 * This stores the <code>ReasonForGameOver</code>
	 */
	
	
	private String ReasonForGameOver;
	
	/**
	 * This stores the world for which the gameOver will be over.
	 */
	
	protected World world;
	
	/**
	 * Constructor for an <code>GameOver</code> .
	 * 
	 * @param world This is the world for which the gameOver methods are determined
	 */
	
	public GameOver(World world){
		this.world = world;
	}
	
	/**
	 * This method needs to determine how the game is won in its worlds and return true if game is won
	 * or return false if the game is not won yet.. By default it returns false.
	 */
	
	public Boolean win(){
		return false;
	
	/**
	 * This method needs to determine how the game is lost in its worlds and return true if game is lost
	 * or return false if the game is not lost yet. By default it returns false.
	 */
	
	}
	public Boolean lose(){
		return false;
	}
	
	/**
	 * This method sets the reason for game over..
	 * 
	 * @param reason The reason for game over is set.
	 */
	
	public void setReasonForGameOver(String reason){
		this.ReasonForGameOver = reason;
	}
	
	/**
	 * This method returns the reason for game over
	 */
	
	public String getReasonForGameOver(){
		return this.ReasonForGameOver;
	}
	
	
	
}

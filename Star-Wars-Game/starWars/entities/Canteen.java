package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.*;

/**
 * A canteen that can be used to contain water.
 * 
 * It can be filled at a Reservoir, or any other Entity
 * that has a Dip affordance.
 * 
 * Please note that drinking from the canteen is currently 
 * unimplemented
 * 
 * 
 * @author Robert Merkel
 * 
 */
public class Canteen extends SWEntity implements Fillable {

	private int capacity;
	private int level;
	private int healPoints;
	
	public Canteen(MessageRenderer m, int capacity, int initialLevel)  {
		super(m);
		this.shortDescription = "a canteen";
		this.longDescription = "a slightly battered aluminium canteen";
		this.healPoints = 20;
		this.capacity = capacity;
		this.level= initialLevel;
		capabilities.add(Capability.FILLABLE);
		capabilities.add(Capability.DRINKABLE);
		this.addAffordance(new Fill(this, m));
		this.addAffordance(new Take(this,m));
		this.addAffordance(new Drink(this,m));
	}

	public Boolean isFull(){
		
		if (this.level == this.capacity){
			return true;
		}
		return false;
	}
	
	public void fill() {
	
		level = capacity;
	}
	
	public int getCurrentLevel(){
		return this.level;
	}
	
	@Override 
	public String getShortDescription() {
		return shortDescription + " [" + level + "/" + capacity + "]";
	}
	
	@Override
	public String getLongDescription () {
		return longDescription + " [" + level + "/" + capacity + "]";
	}
	
	public void reduceCapacity(){
		this.level = this.level - 1;
	}
	
	public int getHealpoints(){
		return this.healPoints;
	}
	
	public Boolean isEmpty(){
		
		if (this.level == 0){
			return true;
		}
		return false;
	}
}

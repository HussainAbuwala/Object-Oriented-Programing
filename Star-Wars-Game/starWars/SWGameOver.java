package starwars;

import java.util.List;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.GameOver;
import edu.monash.fit2099.simulator.space.Location;
import starwars.entities.actors.Player;
import starwars.entities.actors.PrincessLeia;
import starwars.entities.actors.R2D2;

/**
 * This class extends the GameOver class and overrides the win and lose method to determine when the starwars game
 * is won or lost.
 * 
 * */

public class SWGameOver extends GameOver {
	
	protected SWWorld world;
	
	public SWGameOver(SWWorld world){
		super(world);
		this.world = world;
	}
	
	
	/**
	 * The game is won when and true is returned when any one of the following is true,
	 * 						-Luke has successfully taken Leia and R2-D2 to Mon Mothma on Yavin IV
	 * 						-Darth Vader is Dead
	 * else false returned.
	 * */
	
	@Override
	public Boolean win() {
			
		SWGrid yavinIV = this.world.getYavinIV();
		
		EntityManager em = world.getEntitymanager();
		Location loc = yavinIV.getLocationByCoordinates(1, 1);
		List<SWEntityInterface> entities = em.contents(loc);
		
		int y = 0;
		
		for (SWEntityInterface e : entities) {
			// Figure out if we should be attacking this entity
			if( e != this && e instanceof R2D2){
				y+=1;
			}
			else if ( e != this && e instanceof PrincessLeia){
				y+=1;
			}
			else if ( e != this && e instanceof Player){
				y+=1;
			}
			} 
		
		if (y == 3){
			this.setReasonForGameOver("Luke has successfully taken Leia and R2-D2 to Mon Mothma on Yavin IV");
			return true;
		}
		
		if (this.world.checkIfDarthVaderIsDead()){
			this.setReasonForGameOver("Darth Vader is Dead");
			return true;
		}
		return false;
	}
	
	
	/**
	 * The game is lost when and true is returned when any one of the following is true,
	 * 						-Luke has died
	 * 						-Princess Leia has dies
	 * 						-R2D2 has been dissassembled
	 * 						-Luke has turned to the dark side
	 * else false returned.
	 * */
	
	@Override
	public Boolean lose() {
		
		if (this.world.checkIfLukeIsDead()){
			this.setReasonForGameOver("Luke has died");
			return true;
		}
		else if (this.world.checkIfPrincessLeiaIsDead()){
			this.setReasonForGameOver("Princess Leia has dies");
			return true;

		}
		else if (this.world.checkIfr2d2IsDissassembled()){
			this.setReasonForGameOver("R2D2 has been dissassembled");
			return true;

		}
		else if(this.world.checkIfLukeEvil()){
			this.setReasonForGameOver("Luke has turned to the dark side");
			return true;
		}

		return false;
	}
	

}

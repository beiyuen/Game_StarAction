package charas.enemies;

import static constants.ImageConstants.*;
import static constants.MathConstants.*;

import charas.blocks.AbstractBlock;
import charas.blocks.ClearBlock;
import charas.blocks.GoalBlock;
import charas.blocks.WorldClearBlock;
import star_action.Model;
/**
 * sin波を描くように移動する敵
 * 
 * @author kitahara
 *
 */
public class GhostEnemy extends AbstractEnemy {
	private double ysin;

	public GhostEnemy(int x, int y) {
		super(x, y, 30, 35, IMAGE_ENEMY_GHOST);
		ysin = 0.0;
		imageDrawWidth = 30;
		imageDrawHeight = 35;
		imageColumn = 2;
	}
	
	@Override
	public void calcAcceleration() {

		calcYAcceleration();
		calcXAcceleration();
		isHitBlock();
		checkDeath();
		if (hitRight) {
			changeXSpeed();
		}
		if (hitLeg) {
			changeYSpeed();
		}

	}
	
	@Override
	public void isHitBlock() {
		boolean hitSide = false;
		boolean hitLength = false;
		for (AbstractBlock b : Model.getBlockList()) {
			if (b instanceof GoalBlock || b instanceof ClearBlock || b instanceof WorldClearBlock) {
			}  else {
				if(b.isHitMove(this)){
					double angle = Math.atan2(yPosition - b.getyPosition(), xPosition - b.getxPosition());
					if(Math.abs(Math.sin(angle)) > 0.5){
						hitLength = true;
						if(yPosition > b.getyPosition()){
							setyPosition(b.getyPosition() + BLOCK_SIZE/2 + height/2);
						}
						else{
							setyPosition(b.getyPosition() - BLOCK_SIZE/2 - height/2);
						}
						
					}
					else{
						hitSide = true;
						if(xPosition > b.getxPosition()){
							setxPosition(b.getxPosition() + BLOCK_SIZE/2 + width/2);
						}
						else{
							setxPosition(b.getxPosition() - BLOCK_SIZE/2 - width/2);
						}
					}
				}
			}

		}
		for (AbstractBlock b : Model.getPlaceBlockList()) {
			if(b.isHitMove(this)){
				double angle = Math.atan2(yPosition - b.getyPosition(), xPosition - b.getxPosition());
				if(Math.abs(Math.sin(angle)) > 0.5){
					hitLength = true;
					if(yPosition > b.getyPosition()){
						setyPosition(b.getyPosition() + BLOCK_SIZE/2 + height/2);
					}
					else{
						setyPosition(b.getyPosition() - BLOCK_SIZE/2 - height/2);
					}
				}
				else{
					hitSide = true;
					if(xPosition > b.getxPosition()){
						setxPosition(b.getxPosition() + BLOCK_SIZE/2 + width/2);
					}
					else{
						setxPosition(b.getxPosition() - BLOCK_SIZE/2 - width/2);
					}
				}
			}
		}
		
		hitHead = hitLength ? true:false;
		hitRight = hitSide ? true:false;
	}
	
	public void calcXAcceleration() {
		if (xSpeed == 0) {
			xSpeed++;
		}
		if (xSpeed > 0) {
			imageKind = 1;
		} else {
			imageKind = 0;
		}
	}

	public void calcYAcceleration() {
		ySpeed = 5 * Math.sin(ysin);
		ysin += 0.1;

	}

	@Override
	public void changeYSpeed() {
		ysin *= -1;
	}

}

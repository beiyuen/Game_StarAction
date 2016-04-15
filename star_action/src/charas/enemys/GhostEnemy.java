package charas.enemys;

import static constants.ImageConstants.*;

public class GhostEnemy extends AbstractEnemy {
	private double ysin;
	public GhostEnemy(int x, int y) {
		super(x, y,30,30,IMAGE_ENEMY_GHOST);
		ysin = 0.0;
		imageDrawWidth = 24;
		imageDrawHeight = 32;
		imageColumn = 2;
	}

	public void calcXAcceleration(double a) {
	/*	for (Block b : Model.getBlockList()){
			if (//b.hitx(this) &&
				Math.abs(Math.cos((Math.atan2(yPosition-b.yPosition, xPosition-b.xPosition)))) >1/Math.sqrt(2.0))
				//敵とブロックの角度が(-45度~45度)なら
				xSpeed *= -1;
		}*/

		if (xSpeed == 0){
			xSpeed++;
		}
		if(xSpeed > 0){
			imageKind = 1;
		}
		else {
			imageKind = 0;
		}
			

	}

	public void calcYAcceleration() {
		/*for (Block b : Model.getBlockList())
			if (//b.hity(this)&&
				Math.abs(Math.cos((Math.atan2(yPosition-b.yPosition, xPosition-b.xPosition)))) <= 1/Math.sqrt(2.0))
				//敵とブロックの角度が(45度~135度 or 225度~315度)なら
				ysin *= -1;
*/
		ySpeed = 5*Math.sin(ysin);
		ysin += 0.1;

	}

	

	@Override
	public void changeYSpeed() {
		ysin *= -1;
	}

}

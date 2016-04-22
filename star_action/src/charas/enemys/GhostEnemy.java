package charas.enemys;

import static constants.ImageConstants.*;
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

	public void calcXAcceleration(double a) {
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

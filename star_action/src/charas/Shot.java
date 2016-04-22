package charas;

import static constants.CharaConstants.*;
import static constants.ImageConstants.*;
import static constants.MathConstants.*;

import java.awt.Graphics;

import charas.enemys.AbstractEnemy;

/**
 * 敵が撃つ弾です
 * 
 * @author kitahara
 *
 */
public class Shot extends AbstractEnemy {

	// x,yは座標（発射主体に合わせるため）
	public Shot(int x, int y, double spd, double angle) {
		super(0, 0, 10, 10, IMAGE_ENEMY_BULLET);
		xPosition = x;
		yPosition = y;
		xSpeed = spd * Math.cos(angle);
		ySpeed = spd * Math.sin(angle);
	}

	@Override
	public int isHitPlayerChara(PlayerChara c) {
		if (isHit(c)) {
			return HIT_MISS;
		} else {
			return HIT_NOT;
		}
	}

	@Override
	public void calcAcceleration() {
	}

	// 移動定義
	@Override
	public void calcXAcceleration(double a) {

	}

	@Override
	public void calcYAcceleration() {
	}

	public boolean isOutOfFrame() {
		return xPosition > GAME_WIDTH || xPosition < 0 || yPosition > GAME_HEIGHT || yPosition < 0;
	}

	public void draw(Graphics g) {
		g.drawImage(image, (int) (xPosition - width / 2), (int) (yPosition - height / 2), (int) width, (int) height,
				this);
	}

}

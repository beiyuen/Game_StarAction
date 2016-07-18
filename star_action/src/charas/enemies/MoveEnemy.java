package charas.enemies;

import static constants.ImageConstants.*;
import static constants.MathConstants.*;

import charas.PlayerChara;
import enums.HitPlayer;
import star_action.Model;
/**
 * 左右移動し、プレイヤーが近づくとジャンプする敵。左右移動の速度が0なら、
 * 左右移動せず制止する
 *
 * @author kitahara
 *
 */
public class MoveEnemy extends AbstractEnemy {
	private boolean jump;

	public MoveEnemy(int x, int y, double xs) {
		super(x, y, 30, 40, IMAGE_ENEMY_MOVE);
		xSpeed = xs;
		imageLine = 2;
		imageColumn = 5;
		imageDrawWidth = 30;
		imageDrawHeight = 40;
		jump = false;
		hitLeg = true;
	}
	
	public AbstractEnemy init(int x, int y, int xs){
		xPosition = (x + 0.5) * BLOCK_SIZE;
		yPosition = (y + 0.5) * BLOCK_SIZE;
		initX = xPosition;
		initY = yPosition;
		xSpeed = xs;
		death = false;
		using = true;
		return this;
	}

	// 呼び出され用
	public void calcAcceleration() {
		if (Model.getPlayerChara().isDeath()) {
			setJump(false);
		}
		calcXAcceleration();
		calcYAcceleration();
		isHitBlock();
		checkDeath();
		if (hitLeft || hitRight) {
			changeXSpeed();
		} else if (hitHead || hitLeg) {
			changeYSpeed();
		}
		if (jump) {
			jump();
		}



	}

	public HitPlayer isHitPlayerChara(PlayerChara c) {
		// プレイヤーと接触しているとき
		if (isHit(c)) {
			if (Math.sin((Math.atan2(c.getyPosition() - ySpeed - yPosition, c.getxPosition() - xSpeed - xPosition))) <= -1
					/ Math.sqrt(2.0)) {
				death();
				return HitPlayer.Tread;
			}
			return HitPlayer.Miss;
		}
		// プレイヤーが近づいてきたとき
		else if (Math.sqrt((xPosition - c.getxPosition() - c.getxSpeed()) * (xPosition - c.getxPosition() - c.getxSpeed())
				+ (yPosition - c.getyPosition() - c.getySpeed()) * (yPosition - c.getyPosition() - c.getySpeed())) <= 100) {
			setJump(true);
			jump();
			// System.out.println("近づいた");
		}
		// その他
		else {
			setJump(false);
		}
		return HitPlayer.Not;
	}

	private void setJump(boolean b) {
		jump = b;
	}

	public void calcXAcceleration() {
		if (hitLeg) {
			imageCount++;
			if (xSpeed < 0) {
				imageKind = (imageCount % 28) / 7;// 0,1,2,3番目の画像
			} else if (xSpeed > 0) {
				imageKind = (imageCount % 28) / 7 + 5; // 5,6,7,8番目の画像
			} else {
				// ジャンプ時の画像
				double pcx = Model.getPlayerChara().getxPosition();
				if (xPosition >= pcx) {
					imageKind = 1;
				} else if (xPosition < pcx) {
					imageKind = 5;
				}
			}
		}
	}

	public void jump() {
		// ジャンプ
		// System.out.println("hitLeg :" + hitLeg);
		if (hitLeg) {
			ySpeed = -19;
			// System.out.println(ySpeed);
			hitLeg = false;
			double pcx = Model.getPlayerChara().getxPosition();
			if (xPosition >= pcx) {
				imageKind = 4;
			} else {
				imageKind = 9;
			}
		}

	}

	public void changeXSpeed() {
		super.changeXSpeed();
	}
}

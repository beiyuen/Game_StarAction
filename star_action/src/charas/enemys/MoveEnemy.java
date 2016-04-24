package charas.enemys;

import static constants.CharaConstants.*;
import static constants.ImageConstants.*;

import charas.PlayerChara;
import star_action.Model;
/**
 * 左右移動し、プレイヤーが近づくとジャンプする敵。左右移動の速度が0なら、
 * 左右移動せず制止する
 * 
 * @author kitahara
 *
 */
public class MoveEnemy extends AbstractEnemy {
	public boolean jump;

	public MoveEnemy(int x, int y, double xs) {
		super(x, y, 40, 50, IMAGE_ENEMY_MOVE);
		xSpeed = xs;
		imageLine = 2;
		imageColumn = 5;
		imageDrawWidth = 40;
		imageDrawHeight = 50;
		jump = false;
	}

	// 呼び出され用
	public void calcAcceleration() {
		if (Model.getPlayerChara().isDeath()) {
			setJump(false);
		}
		isHitBlock();
		checkDeath();
		if (hitLeft || hitRight) {
			changeXSpeed();
		} else if (hitHead || hitLeg) {
			changeYSpeed();
			// System.out.println("changeXSpeed:" + ySpeed);
		}
		// System.out.println("ジャンプ前 imageKind:" + imagekind);
		if (jump) {
			jump();
			// System.out.println("ジャンプ後 imageKind:" + imagekind);
		}

		calcXAcceleration(0.7);
		calcYAcceleration();

	}

	public int isHitPlayerChara(PlayerChara c) {
		// プレイヤーと接触しているとき
		if (isHit(c)) {
			if (Math.sin((Math.atan2(c.getyPosition() - ySpeed - yPosition, c.getxPosition() - xSpeed - xPosition))) <= -1
					/ Math.sqrt(2.0)) {
				death();
				return HIT_TREAD;
			}
			return HIT_MISS;
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
		return HIT_NOT;
	}

	private void setJump(boolean b) {
		// TODO 自動生成されたメソッド・スタブ
		jump = b;
	}

	public void calcXAcceleration(double a) {
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

	public void calcYAcceleration() {
		if (!hitLeg)
			ySpeed += 1.3;
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

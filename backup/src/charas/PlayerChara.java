package charas;

import static constants.CharaConstants.*;
import static constants.MathConstants.*;

import charas.enemys.AbstractEnemy;
import star_action.Model;
import util.ReferenceItems;

/**
 * 操作キャラです
 *
 * @author kitahara
 *
 */
public class PlayerChara extends AbstractChara {
	private static final long serialVersionUID = 1L;

	public boolean moveRight = false, moveLeft = false, moveUp = false, dash = false;

	public PlayerChara(int w, int h) {
		image = ReferenceItems.getPlayerCharaImage();
		width = 30;
		height = 40;
		imageDrawWidth = 30;
		imageDrawHeight = 40;
		imageColumn = 5;
		imageLine = 2; // 画像の分割数
		imageKind = 5;
		init();
	}

	// 初期化
	public void init() {
		xPosition = 3 * BLOCK_SIZE;
		yPosition = 10 * BLOCK_SIZE;
		xSpeed = 0;
		ySpeed = 0;
		hitRight = false;
		hitLeft = false;
		hitLeg = false;
		imageCount = 0;
		imageKind = 3;
		death = false;
	}

	public void calcAcceleration() {

		// 敵との当たり判定を計算
		if (Model.getGameStatus() != GAMESTATUS_WORLDCHANGE) {
			for (AbstractEnemy e : Model.getEnemyList()) {
				if (!e.isDeath()) {
					switch (e.isHitPlayerChara(this)) {
					case HIT_TREAD:
						tread();
						break;
					case HIT_MISS:
						Model.death();
						break;
					case HIT_NOT:
						break;
					}
				}
			}
			for (AbstractEnemy e : Model.getPlaceEnemyList()) {
				if (!e.isDeath()) {
					switch (e.isHitPlayerChara(this)) {
					case HIT_TREAD:
						tread();
						break;
					case HIT_MISS:
						Model.death();
						break;
					case HIT_NOT:
						break;
					}
				}
			}
			// トゲとの当たり判定			
			for (Needle n : Model.getNeedleList()) {
				if (!n.isDeath() && n.isHit(this)) {
					Model.death();
				}
			}
			checkDeath();
		}

		// 速度変更
		calcXAcceleration();
		calcYAcceleration();
		moveAngle = Math.atan2(ySpeed, xSpeed);
		// ブロックとの当たり判定をし、hitRight, hitLeft, hitHead, hitLeg を変更
		isHitBlock();
		if (hitLeft || hitRight) {
			changeXSpeed();
		}
		if (hitHead || hitLeg) {
			changeYSpeed();
			
		}

	}
	/**
	 *  下に落ちたか判定し、落ちていたら死亡処理をする
	 */
	private void checkDeath() {
		if (yPosition > GAME_HEIGHT) {
			Model.death();
		}
	}

	
	public void calcXAcceleration() {
		// 右を押していたとき
		if (moveRight /*&& !hitRight*/) {
			if (dash && xSpeed < XSPEED_MAX) {
				xSpeed += 2;
				if(xSpeed > XSPEED_MAX){
					xSpeed = XSPEED_MAX;
				}
			} else if (dash == false && xSpeed <= 6.0) {
				xSpeed += 1.5;
				if(xSpeed > 6.0){
					xSpeed = 6.0;
				}
			}
			imageCount++;
			imageKind = (imageCount % 24) / 6 + 5;// 5,6,7,8番目の画像
		}
		// 左を押していたとき
		else if (moveLeft /*&& !hitLeft*/) {
			if (dash && xSpeed > -XSPEED_MAX) {
				xSpeed -= 2;
				if(xSpeed < -XSPEED_MAX){
					xSpeed = -XSPEED_MAX;
				}
			} else if (dash == false && xSpeed > -6.0) {
				xSpeed -= 1.5;
				if(xSpeed < -6.0){
					xSpeed = -6.0;
				}
			}
			imageCount++;
			imageKind = (imageCount % 24) / 6; // 0,1,2,3番目の画像
		}

		if (!hitLeg && imageKind < 5) {
			imageKind = 4;
		} else if (!hitLeg && imageKind >= 5) {
			imageKind = 9;
		} else if (hitLeg && imageKind == 4) {
			imageKind = (imageCount % 24) / 6;
		} else if (hitLeg && imageKind == 9) {
			imageKind = (imageCount % 24) / 6 + 5;
		}

		if (xPosition + xSpeed - width / 2 < 0) {
			xPosition = width / 2;
			changeXSpeed();

		}

		super.calcXAcceleration();

	}

	// ジャンプ操作と画像の変更
	public void calcYAcceleration() {
		super.calcYAcceleration();
		// 接地しているときのジャンプ処理
		if (moveUp && hitLeg && !hitHead) {
			jump();
			if (imageKind < 5){
				imageKind = 4;
			}		
			else {
				imageKind = 9;
			}
			hitLeg = false;
		// ジャンプして地面についたとき	
		} else if (!moveUp && hitLeg) {
			changeYSpeed();
		}

	}
	
	@Override
	public void move() {
		xPosition += xSpeed;
		if(!isHitLeg() || !isHitHead()){
			yPosition += ySpeed;
		}
		
	}

	/**
	 *  敵を踏んだ時の処理
	 */
	public void tread() {
		if (moveUp) {
			ySpeed = -23;
		} else {
			ySpeed = -6;
		}
	}

	@Override
	public void changeXSpeed() {
		xSpeed = 0.0;
	}

	@Override
	public void changeYSpeed() {
		// TODO 自動生成されたメソッド・スタブ
		ySpeed = 0.0;
	}

	public void death() {
		// TODO 自動生成されたメソッド・スタブ
		death = true;
		xSpeed = 0.0;
		ySpeed = 0.0;
	}

	public boolean isMoveRight() {
		return moveRight;
	}

	public void setMoveRight(boolean moveRight) {
		this.moveRight = moveRight;
	}

	public boolean isMoveLeft() {
		return moveLeft;
	}

	public void setMoveLeft(boolean moveLeft) {
		this.moveLeft = moveLeft;
	}

	public boolean isMoveUp() {
		return moveUp;
	}

	public void setMoveUp(boolean moveUp) {
		this.moveUp = moveUp;
	}

	public boolean isDash() {
		return dash;
	}

	public void setDash(boolean dash) {
		this.dash = dash;
	}

}

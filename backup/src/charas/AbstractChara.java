package charas;

import static constants.MathConstants.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import charas.blocks.AbstractBlock;
import charas.blocks.GoalBlock;
import charas.blocks.WorldClearBlock;
import star_action.Model;
/**
 * ブロック、トゲ、プレイヤーキャラ、敵、ボスの基礎となるクラスです
 *
 * @author kitahara
 *
 */
public abstract class AbstractChara extends JPanel {
	protected double xPosition, yPosition; // 位置
	protected double initX, initY;			// ステージ開始時の初期位置
	protected double xSpeed, ySpeed; 		// スピード
	protected double moveAngle;				// 移動する角度
	protected int width, height; 			// 縦横サイズ
	protected boolean hitRight = false;	// キャラの右側がブロックに当たっているか
	protected boolean hitLeft = false;		// キャラの左側がブロックに当たっているか
	protected boolean hitHead = false;		// キャラの上側がブロックに当たっているか
	protected boolean hitLeg = false;		// キャラの下側がブロックに当たっているか
	protected boolean death;				// 死亡判定
	protected Image image; 				// 画像
	protected int imageDrawWidth = 1, imageDrawHeight = 1;	// 画像描画時のサイズ
	protected int imageColumn = 1;			// 画像の行方向の分割数
	protected int imageLine = 1;			// 画像の列方向の分割数
	protected int imageKind = 0;			// 現在表示する画像番号
	protected int imageCount = 0;			// 画像番号を計算するときに必要な値

	public AbstractChara() {}
	/**
	 * ステージ開始時の状態にする
	 */
	public void init() {
		xPosition = initX;
		yPosition = initY;
		death = false;
	}

	/**
	 * 対象キャラ(c)との当たり判定を返す。当たっていたらtrue,当たっていなかったらfalseを返す
	 *
	 * @param c
	 * @return
	 */
	public boolean isHit(AbstractChara c) {
		return Math.abs(c.getxPosition() - xPosition) <= c.getWidth() / 2 + width / 2
				&& Math.abs(c.getyPosition() - yPosition) <= c.getHeight() / 2 + height / 2;
	}

	/**
	 * 点との当たり判定を返す。当たっていたらtrue,当たっていなかったらfalseを返す
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isHitPoint(double x, double y) {
		return (x >= xPosition - width / 2 && x <= xPosition + width / 2 && y >= yPosition - height / 2
				&& y <= yPosition + height / 2);
	}

	/**
	 * 地面についていればジャンプする
	 */
	public void jump() {
		if (hitLeg)
			ySpeed = -YSPEED_MAX - Math.abs(xSpeed) / 5;
	}

	/**
	 * 速度変更、当たり判定処理を行う
	 */
	public void calcAcceleration() {
		// x, y 方向の加速度と速度の計算
		calcYAcceleration();
		calcXAcceleration();
		moveAngle = Math.atan2(ySpeed, xSpeed);
		// ブロックとの衝突判定, 衝突方向により速度変更を行う
		isHitBlock();
		if (hitLeft || hitRight) {
			changeXSpeed();
		}
		if (hitHead || hitLeg) {
			changeYSpeed();
		}

	}

	/**
	 * 減速処理を行う。また、速度が一定以下になったら止まるようにする
	 */
	public void calcXAcceleration() {
		if (xSpeed > 0){
			xSpeed -= DELTA_SPEED;
		}

		if (xSpeed < 0){
			xSpeed += DELTA_SPEED;
		}

		// 振動防止
		if (Math.abs(xSpeed) < DELTA_SPEED){
			xSpeed = 0.0;
		}

	}

	/**
	 * 空中にいるときはy方向の速度が大きくなるようにする
	 */
	public void calcYAcceleration() {
		if (!hitLeg && ySpeed < YSPEED_MAX) {
			ySpeed += 1.3;
		}
		if(ySpeed >= YSPEED_MAX){
			ySpeed = YSPEED_MAX;
		}
	}

	public void isHitBlock() {
		int hitr = 0;
		int hitl = 0;
		int hith = 0;
		int hitd = 0;
		int hitGoal = 0;
		Dimension hx, hy;
		int i = 0;
		for (AbstractBlock b : Model.getBlockList()) {
			if (b instanceof GoalBlock) {
				if (((GoalBlock) b).hitGoal(this)) {
					hitGoal = 1;
				}
			} else if (b instanceof WorldClearBlock) {
				if (((WorldClearBlock) b).hitGoal(this)) {
					hitGoal = 2;
				}
			} else {
				hx = b.hitx(this);
				hy = b.hity(this);

				if (hx.width == 1) {
					hitl = 1;
				}
				if (hx.height == 1) {
					hitr = 1;
				}
				if (hy.width == 1) {
					hith = 1;
				}
				if (hy.height == 1) {
					hitd = 1;
				}
			}
			i+=1;
		}
		for (AbstractBlock b : Model.getPlaceBlockList()) {
			hx = b.hitx(this);
			hy = b.hity(this);

			if (hx.width == 1) {
				hitl = 1;
			}
			if (hx.height == 1) {
				hitr = 1;
			}
			if (hy.width == 1) {
				hith = 1;
			}
			if (hy.height == 1) {
				hitd = 1;
			}
		}
		hitLeft = hitl == 1 ? true : false;
		hitRight = hitr == 1 ? true : false;
		hitHead = hith == 1 ? true : false;
		hitLeg = hitd == 1 ? true : false;
		if (hitGoal == 1) {
			Model.nextStage();
		} else if (hitGoal == 2) {
			Model.nextWorld();
		}
	}

	public void scroll(double speed) {
		xPosition -= speed;
	}

	// 速度変更
	public abstract void changeXSpeed();

	public abstract void changeYSpeed();

	/**
	 * 移動処理を行う。x,yのそれぞれの座標に速度を足す
	 */
	public void move() {
		xPosition += xSpeed;
		yPosition += ySpeed;
	}

	// 描画
	public void draw(Graphics g) {
		double sx, sy;
		if(xPosition < GAME_WIDTH + 100 && xPosition > -100){
			sx = (imageKind % imageColumn) * imageDrawWidth;
			sy = (imageKind / imageColumn) * imageDrawHeight;

			g.drawImage(image, (int) (xPosition - width / 2), (int) (yPosition - height / 2), (int) (xPosition + width / 2),
					(int) (yPosition + height / 2), (int) (sx), (int) (sy), (int) (sx + imageDrawWidth),
					(int) (sy + imageDrawHeight), this);
		}

	}

	/**
	 * xPositionを取得します。
	 *
	 * @return xPosition
	 */
	public double getxPosition() {
		return xPosition;
	}

	/**
	 * xPositionを設定します。
	 *
	 * @param xPosition
	 *            xPosition
	 */
	public void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * yPositionを取得します。
	 *
	 * @return yPosition
	 */
	public double getyPosition() {
		return yPosition;
	}

	/**
	 * yPositionを設定します。
	 *
	 * @param yPosition
	 *            yPosition
	 */
	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	/**
	 * xSpeedを取得します。
	 *
	 * @return xSpeed
	 */
	public double getxSpeed() {
		return xSpeed;
	}

	/**
	 * xSpeedを設定します。
	 *
	 * @param xSpeed
	 *            xSpeed
	 */
	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	/**
	 * ySpeedを取得します。
	 *
	 * @return ySpeed
	 */
	public double getySpeed() {
		return ySpeed;
	}

	/**
	 * ySpeedを設定します。
	 *
	 * @param ySpeed
	 *            ySpeed
	 */
	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}

	/**
	 * widthを取得します。
	 *
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * heightを取得します。
	 *
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	public double getMoveAngle() {
		return moveAngle;
	}
	public void setMoveAngle(double moveAngle) {
		this.moveAngle = moveAngle;
	}
	/**
	 * hitLegを取得します。
	 *
	 * @return hitLeg
	 */
	public boolean isHitLeg() {
		return hitLeg;
	}

	public boolean isHitHead() {
		return hitHead;
	}

	public boolean isHitRight() {
		return hitRight;
	}

	public boolean isHitLeft() {
		return hitLeft;
	}

	/**
	 * deathを取得します。
	 *
	 * @return
	 */
	public boolean isDeath() {
		return death;
	}

	public void setDeath(boolean b) {
		death = b;
	}
}

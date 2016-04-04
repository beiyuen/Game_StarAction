package charas;
import static constants.MathConstants.*;

import java.awt.Dimension;
//----------------------------------------------------
//
//----------------------------------------------------
import java.awt.Graphics;

public class Block extends AbstractChara {
	private static final long serialVersionUID = 1L;
	public boolean removable = true;
	public Dimension hitX,hitY;

	public Block(int x, int y) {
		width = BLOCK_SIZE;
		height = BLOCK_SIZE;
		xPosition = x * BLOCK_SIZE + BLOCK_SIZE / 2;
		yPosition = y * BLOCK_SIZE + BLOCK_SIZE / 2;
		image = getToolkit().createImage("image/block.png");
		hitX = new Dimension(0, 0);
		hitY = new Dimension(0, 0);
	}
	public Block(int x, int y, int w, int h) {
		width = w;
		height = h;
		xPosition = x * BLOCK_SIZE + BLOCK_SIZE / 2;
		yPosition = y * BLOCK_SIZE + BLOCK_SIZE / 2;
	}

	public void calcAcceleration() {
	}

	// 接触判定
	public boolean hit(AbstractChara c) {
		return Math.abs(c.xPosition - xPosition) <= c.width / 2 + width / 2
				&& Math.abs(c.yPosition- yPosition) <= c.height / 2 + height / 2;
	}

	// 接触かつ直前にx方向に接触していない場合に位置、速度を調整
	public Dimension hitx(AbstractChara c) {
		boolean x = hit(c);
		int hitr = 0;
		int hitl = 0;
		if(x){
			if (Math.abs(c.xPosition - xPosition) >= c.width / 2 + width / 2 ) {
				c.xPosition = xPosition - (c.width / 2 + BLOCK_SIZE / 2) * Math.signum(c.xSpeed);
				//if(c instanceof PlayerChara)
				//	c.xSpeed = 0;
				//return true;
			}
			double cos = Math.cos(Math.atan2(c.yPosition-yPosition, c.xPosition-xPosition));
			if(cos >= 1/Math.sqrt(2.0)){
				hitl = 1;
			}
			else if(cos <= -1/Math.sqrt(2.0)){
				hitr = 1;
			}
		}
		hitX.setSize(hitl, hitr);
		/*if(hitl){
			c.hitLeft = true;
		}
		else {
			c.hitLeft = false;
		}
		if(hitr){
			c.hitRight = true;
		}
		else {
			c.hitRight = false;
		}*/

		return hitX;

	}

	public Dimension hity(AbstractChara c) {
		boolean x = hit(c);
		int hith = 0;
		int hitl = 0;
		if(x){

			// ブロックの上にいるとき、着地するようにgroundの値を変更
			double sin = Math.sin(Math.atan2(c.yPosition-yPosition, c.xPosition-xPosition));
			double prey = c.yPosition-c.ySpeed-yPosition;
			if(sin <= -1/Math.sqrt(2.0) || prey <= 0){
				hitl = 1;
				c.hitLeg = true;
				x = true;
			}
			else if(sin >= 1/Math.sqrt(2.0) || prey >= 0){
				hith = 1;
				c.hitHead = true;
				x = true;
			}
			// 貫通防止、前のフレームで空中におり、現在のフレームでブロック内の角度が変な場所にいるとき

			else {
				x = false;
			}
			// ブロックの上下どちらかに接地しているとき、座標を修正
			if ( Math.abs(c.yPosition - yPosition) >= c.height / 2 + height / 2) {
				c.yPosition = yPosition - (c.height / 2 + BLOCK_SIZE / 2) * Math.signum(c.ySpeed);
				c.ySpeed = 0;
							// 上にいた場合接地on
							//if (c.yPosition <= yPosition) {
							//	c.ground = true;
							//}
							//else {c.ground = false;}
							//return true;
			}
			hitY.setSize(hith, hitl);

		}
		return hitY;
		// 位置調整
		/*if (x && Math.abs(c.yPosition - yPosition) >= c.height / 2 + height / 2) {
			c.yPosition = yPosition - (c.height / 2 + BLOCK_SIZE / 2) * Math.signum(c.ySpeed);

			c.ySpeed = 0;
			// 上にいた場合接地on
			if (c.yPosition <= yPosition) {
				c.ground = true;
			}
			//else {c.ground = false;}
			//return true;
		}*/
		//c.ground = false;
		//return false;
		//return x;
	}

	// 描画
	public void draw(Graphics g) {
		g.drawImage(image, (int) (xPosition - width / 2), (int) (yPosition - height / 2),
				(int) width, (int) height, this);
	}
	@Override
	public void changeXSpeed() {
	}
	@Override
	public void changeYSpeed() {
	}

}
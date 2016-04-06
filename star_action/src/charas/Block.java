package charas;
import static constants.MathConstants.*;

import java.awt.Dimension;
//----------------------------------------------------
//
//----------------------------------------------------
import java.awt.Graphics;


/**
 * 通常ゲームでは普通のブロック。プレイヤーが触れても、敵が触れても消えることはない。また、このブロックは消すことはできない。
 * @author kitahara
 *
 */
public class Block extends AbstractChara {
	private static final long serialVersionUID = 1L;
	public boolean removable = false;
	public Dimension hitX,hitY;
	public boolean isHitx;

	public Block(int x, int y) {
		width = BLOCK_SIZE;
		height = BLOCK_SIZE;
		xPosition = x * BLOCK_SIZE + BLOCK_SIZE / 2;
		yPosition = y * BLOCK_SIZE + BLOCK_SIZE / 2;
		initX = xPosition;
		initY = yPosition;
		image = getToolkit().createImage("image/block.png");
		hitX = new Dimension(0, 0);
		hitY = new Dimension(0, 0);
		isHitx = false;
	}
	public Block(int x, int y, int w, int h) {
		width = w;
		height = h;
		xPosition = x * BLOCK_SIZE + BLOCK_SIZE / 2;
		yPosition = y * BLOCK_SIZE + BLOCK_SIZE / 2;
		initX = xPosition;
		initY = yPosition;
	}

	public void calcAcceleration() {
	}

	// 接触判定
	public boolean hit(AbstractChara c) {
		return Math.abs(c.xPosition - xPosition) <= c.width / 2 + width / 2
				&& Math.abs(c.yPosition  - yPosition) <= c.height / 2 + height / 2;
	}

	// 接触かつ直前にx方向に接触していない場合に位置、速度を調整
	public Dimension hitx(AbstractChara c) {
		boolean x = hit(c);
		int hitr = 0;
		int hitl = 0;
		double prex = c.xPosition-c.xSpeed-xPosition;
		if(x){
			double precos = Math.cos(Math.atan2(c.yPosition-c.ySpeed-yPosition, c.xPosition-c.xSpeed-xPosition));
			double cos = Math.cos(Math.atan2(c.yPosition-yPosition, c.xPosition-xPosition));
			if(cos >= Math.cos(30 * Math.PI / 180.0)){
				hitl = 1;
				isHitx = false;
				c.xPosition = xPosition + (c.width / 2 + BLOCK_SIZE / 2);
			//	System.out.println("hitLeft1");
			}

			else if(prex > (c.width/2+BLOCK_SIZE/2) && precos >= Math.cos(60 * Math.PI / 180.0) && c.xSpeed < 0){
				if(!c.hitLeg && !c.hitHead){
					hitl = 1;
					isHitx = true;
					c.xPosition = xPosition + (c.width / 2 + BLOCK_SIZE / 2);
				//	System.out.println("hitLeft2");
				}

			}
			else if(cos <= Math.cos(150 * Math.PI / 180.0)){
				hitr = 1;
				isHitx = false;
				c.xPosition = xPosition - (c.width / 2 + BLOCK_SIZE / 2);
			//	System.out.println("hitRight1");
			}
			else if(prex < -(c.width/2+BLOCK_SIZE/2) && precos <= Math.cos(120 * Math.PI / 180.0) && c.xSpeed > 0){
				if(!c.hitLeg && !c.hitHead){
					hitr = 1;
					isHitx = true;
					c.xPosition = xPosition - (c.width / 2 + BLOCK_SIZE / 2);
				//	System.out.println("hitRight2");
				}
			}
			else {
				isHitx = false;
			}
		}
		else {
			isHitx = false;
		}
		hitX.setSize(hitl, hitr);
		return hitX;

	}

	public Dimension hity(AbstractChara c) {
		boolean x = hit(c);
		int hith = 0;
		int hitl = 0;
		double prey = c.yPosition-c.ySpeed-yPosition;
		double curr = c.yPosition-yPosition;
		if(x){

			// ブロックの上にいるとき、着地するようにgroundの値を変更
			double sin = Math.sin(Math.atan2(c.yPosition-yPosition, c.xPosition-xPosition));
			double presin = Math.sin(Math.atan2(c.yPosition-c.ySpeed-yPosition, c.xPosition-c.xSpeed-xPosition));
			if(sin <= Math.sin(-60 * Math.PI / 180.0)){
					hitl = 1;
					c.yPosition = yPosition - (c.height / 2 + BLOCK_SIZE / 2);
				//	System.out.println("leg1");
			}
			else if(prey <= -(c.height/2+BLOCK_SIZE/2) && presin <= Math.sin(-40 * Math.PI / 180.0) && c.ySpeed >= 2){
				if(!c.hitLeft && !c.hitRight && !c.hitLeg && !isHitx){
					hitl = 1;
					c.yPosition = yPosition - (c.height / 2 + BLOCK_SIZE / 2);
				//	System.out.println("leg2");
				}

			}
			else if(sin >= Math.sin(60 * Math.PI / 180.0) ){//|| (prey > (c.height/2+BLOCK_SIZE/2) && presin >=  Math.sin(60 * Math.PI / 180.0) && ySpeed < 0)){
				hith = 1;
				c.yPosition = yPosition + (c.height / 2 + BLOCK_SIZE / 2);
			//	System.out.println("head1");
			}
			else if (prey > (c.height/2+BLOCK_SIZE/2) && presin >=  Math.sin(35 * Math.PI / 180.0) && c.ySpeed < 0){
				if(!c.hitLeft && !c.hitRight && !isHitx){
					hith = 1;
					c.yPosition = yPosition + (c.height / 2 + BLOCK_SIZE / 2);
				//	System.out.println("head2");
				}
			}
			// 貫通防止、前のフレームで空中におり、現在のフレームでブロック内の角度が変な場所にいるとき
			// ブロックの上下どちらかに接地しているとき、座標を修正
		}
		else {
		}
		hitY.setSize(hith, hitl);
		return hitY;
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
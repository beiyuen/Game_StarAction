package charas.enemys;

import static constants.CharaConstants.*;
import static constants.MathConstants.*;

import java.awt.Graphics;

import charas.Enemy;
import charas.PlayerChara;
import star_action.Model;

public class MoveEnemy extends Enemy {
	private int imagekind = 0, i = 0, tate = 2, yoko = 5;
	public MoveEnemy(int x, int y, double xs) {
		super(x,y,40,50,"image/enemy5.png");
		xSpeed = xs;
		ySpeed = 0;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	// 呼び出され用
	public void calcAcceleration() {
		
		isHitBlock();
		checkDeath();
		if(hitLeft || hitRight){
			changeXSpeed();
			System.out.println("changeXSpeed:" + xSpeed + " " + hitLeft + " " + hitRight);
		}
		else if(hitHead || hitLeg){
			changeYSpeed();
		}
		calcXAcceleration(0.7);
		calcYAcceleration();
		
	}

	public int hit(PlayerChara c) {
		if(hit2(c)){
			if(Math.sin((Math.atan2(c.yPosition-ySpeed-yPosition, c.xPosition-xSpeed-xPosition))) <= -1/Math.sqrt(2.0)) {
				death();
				return HIT_TREAD;
			}
			return HIT_MISS;
		}
		// 近づいたとき
		else if (Math.sqrt((xPosition-c.xPosition-c.xSpeed)*(xPosition-c.xPosition-c.xSpeed)+
				(yPosition-c.yPosition-c.ySpeed)*(yPosition-c.yPosition-c.ySpeed))<=100){
			jump();
			System.out.println("近づいた");
		}
		return HIT_NOT;
	}

	public void calcXAcceleration(double a) {
		if(hitLeg){
			if(xSpeed<0){
				i ++;
				imagekind=(i%20)/5;//0,1,2,3番目の画像
			}
			else if(xSpeed>0){
				i ++;
				imagekind=(i%20)/5+5; //5,6,7,8番目の画像
			}
			else {
				// ジャンプ時の画像
				double pcx = Model.getPlayerChara().getxPosition();
				if(xPosition>=pcx){
					imagekind=0;
				}
				else if(xPosition<pcx){
					imagekind=5;
				}
			}
		}
	}

		public void calcYAcceleration(){
				if(!hitLeg) ySpeed += 1.3;
		}

		public void draw(Graphics g){
			double sx, sy;
			int pwidth = image.getWidth(null)/yoko;
			int pheight = image.getHeight(null)/tate;
	        sx = (imagekind % yoko) * pwidth;
	        sy = (imagekind / yoko) * pheight;

			g.drawImage(image,(int)(xPosition- width / 2),(int)(yPosition- height / 2),
					(int)(xPosition+width/2),(int)(yPosition+height/2),
					(int)(sx),(int)(sy), (int)(sx+pwidth), (int)(sy+pheight),this);
		}

	private void checkDeath() {
		// TODO 自動生成されたメソッド・スタブ
		if(yPosition > GAME_HEIGHT){
			death();
		}
	}

	public void jump(){
		// ジャンプ
		
		if (hitLeg){
			ySpeed = -19;
			hitLeg = false;
			if(imagekind < 4){
				imagekind = 4;
			}
			else{
				imagekind = 9;
			}
		}
		
	}

	public void changeXSpeed(){
		super.changeXSpeed();
	}
}

package charas.enemys;

import static constants.CharaConstants.*;
import static constants.MathConstants.*;

import java.awt.Graphics;

import charas.PlayerChara;
import star_action.Model;

public class MoveEnemy extends AbstractEnemy {
	private int imagekind = 0, i = 0, tate = 2, yoko = 5;
	public boolean jump;
	
	public MoveEnemy(int x, int y, double xs) {
		super(x,y,40,50,IMAGE_ENEMY_MOVE);
		xSpeed = xs;
		jump = false;
	}

	// 呼び出され用
	public void calcAcceleration() {

		isHitBlock();
		checkDeath();
		if(hitLeft || hitRight){
			changeXSpeed();
		}
		else if(hitHead || hitLeg){
			changeYSpeed();
			//System.out.println("changeXSpeed:" + ySpeed);
		}
		//System.out.println("ジャンプ前 imageKind:" + imagekind);
		if(jump){
			jump();
		//	System.out.println("ジャンプ後 imageKind:" + imagekind);
		}

		calcXAcceleration(0.7);
		calcYAcceleration();


	}

	public int isHitPlayerChara(PlayerChara c) {
		// プレイヤーと接触しているとき
		if(isHit(c)){
			if(Math.sin((Math.atan2(c.yPosition-ySpeed-yPosition, c.xPosition-xSpeed-xPosition))) <= -1/Math.sqrt(2.0)) {
				death();
				return HIT_TREAD;
			}
			return HIT_MISS;
		}
		// プレイヤーが近づいてきたとき
		else if (Math.sqrt((xPosition-c.xPosition-c.xSpeed)*(xPosition-c.xPosition-c.xSpeed)+
				(yPosition-c.yPosition-c.ySpeed)*(yPosition-c.yPosition-c.ySpeed))<=100){
			setJump(true);
			jump();
			//System.out.println("近づいた");
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
		if(hitLeg){
			i ++;
			if(xSpeed<0){
				imagekind=(i%28)/7;//0,1,2,3番目の画像
			}
			else if(xSpeed>0){
				imagekind=(i%28)/7+5; //5,6,7,8番目の画像
			}
			else {
				// ジャンプ時の画像
				double pcx = Model.getPlayerChara().getxPosition();
				if(xPosition>=pcx){
					imagekind=1;
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
			int pwidth = 40;
			int pheight = 50;
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
	//	System.out.println("hitLeg :" + hitLeg);
		if (hitLeg){
			ySpeed = -19;
		//	System.out.println(ySpeed);
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

package charas.enemys;

import static constants.CharaConstants.*;

import java.awt.Graphics;

import charas.Block;
import charas.Enemy;
import charas.PlayerChara;
import star_action.Model;

public class WalkEnemy extends Enemy {
	int imagekind = 0, i = 0, tate = 2, yoko = 4;
	boolean isHit;

	public WalkEnemy(int x, int y) {
		super(x, y, 40, 50, "image/enemy4.png");
		ySpeed = 0;
		isHit = false;
	}

	@Override
	public int hit(PlayerChara c) {
		if (hit2(c)) {
			isHit = true;
		}
		else {
			isHit = false;
		}
		return HIT_NOT;
	}
	@Override
	public void calcAcceleration() {
		calcXAcceleration(0.7);
		isHitBlock();
		if(hitLeft || hitRight){
			changeXSpeed();
		}
		else if(hitHead || hitLeg){
			changeYSpeed();
		}
	}
	
	@Override
	public void calcXAcceleration(double a) {
		super.calcXAcceleration(a);
		
		if(isHit){
			for (Block b : Model.getBlockList()) {
				if(b.hit(this) && !b.isDeath()){
					b.setDeath(true);
				}
			}
		}
		
		if (xSpeed < 0) {
			i++;
			imagekind = (i % 32) / 8;// 0,1,2,3番目の画像
		} else if (xSpeed  > 0) {
			i++;
			imagekind = (i % 32) / 8 + 4; // 4,5,6,7番目の画像
		}
	}

	public void calcYAcceleration() {

	}

	public void draw(Graphics g) {
		double sx, sy;
		int pwidth = image.getWidth(null) / yoko;
		int pheight = image.getHeight(null) / tate;
		sx = (imagekind % yoko) * pwidth;
		sy = (imagekind / yoko) * pheight;

		g.drawImage(image, (int) (xPosition - width / 2), (int) (yPosition - height / 2), (int) (xPosition + width / 2),
				(int) (yPosition + height / 2), (int) (sx), (int) (sy), (int) (sx + pwidth), (int) (sy + pheight), this);
	}
}

package charas.enemys;

import static constants.CharaConstants.*;
import static constants.MathConstants.*;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import charas.PlayerChara;
import charas.Shot;
import star_action.Model;

public class ShootEnemy extends AbstractEnemy {
	public ArrayList<Shot> bullet = new ArrayList<Shot>();
	Iterator<Shot> bulletIterator = null;
	int shotCount;
	boolean death2;

	public ShootEnemy(int x, int y) {
		super(x,y,30,30,IMAGE_ENEMY_SHOT);
		xSpeed = 0.0;
		shotCount = 0;
		death2 = false;
	}

	public void init(){
		super.init();
		bullet.clear();
		death2 = false;
	}

	@Override
	public void calcAcceleration() {
		if(!death2){
			super.calcAcceleration();

			shotCount++;
			//弾を出す
			if( shotCount %150 == 100 &&xPosition>0 && xPosition < GAME_WIDTH-40){
				for(int i =0;i<6;i++){
					bullet.add(new Shot( (int)(xPosition+1), (int)(yPosition-13),3.0,
							Math.atan2(yPosition-Model.getPlayerChara().getyPosition(),xPosition-Model.getPlayerChara().getxPosition())+(i*60)*Math.PI/180));
				}

				//Mario.sound("shoot.wav",0.4);
			}
			bulletIterator = bullet.iterator();
			while (bulletIterator.hasNext()) {
				Shot s = bulletIterator.next();
				s.calcAcceleration();
				if(s.xPosition > GAME_WIDTH || s.xPosition < 0 || s.yPosition >GAME_HEIGHT || s.yPosition < 0){
					bulletIterator.remove();
				}
			}
		}

	}


	public int isHitPlayerChara(PlayerChara c){
		for(Shot s: bullet){
			if(s.isHitPlayerChara(c) == HIT_MISS){
				return HIT_MISS;
			}
		}
		if(!death2){
			return super.isHitPlayerChara(c);
		}
		return HIT_NOT;
	}

	@Override
	public void death(){
		death2 = true;
	}

	@Override
	public void calcXAcceleration(double a) {}

	@Override
	public void scroll(double speed) {
		super.scroll(speed);
		for(Shot s: bullet){
			s.scroll(speed);
		}
	}
	@Override
	public void move(){
		if(!death2){
			super.move();
		}

		for(Shot s: bullet){
			s.move();
		}
	}
	@Override
	public void draw(Graphics g) {
		if(!death2){
			g.drawImage(image, (int) (xPosition - width / 2), (int) (yPosition - height / 2),
					(int) width, (int) height, this);
		}
		for(Shot b : bullet){
			b.draw(g);
		}
	}

}

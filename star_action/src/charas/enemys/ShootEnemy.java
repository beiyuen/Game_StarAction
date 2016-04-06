package charas.enemys;

import static constants.CharaConstants.*;
import static constants.MathConstants.*;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import charas.Enemy;
import charas.PlayerChara;
import charas.Shot;
import star_action.Model;

public class ShootEnemy extends Enemy {
	public ArrayList<Shot> bullet = new ArrayList<Shot>();
	Iterator<Shot> bulletIterator = null;
	int count;

	public ShootEnemy(int x, int y) {
		super(x,y,30,"image/enemy3.png");
		count = 0;
	}

	public void init(){
		super.init();
		bullet.clear();
	}

	@Override
	public void calcAcceleration() {
		super.calcAcceleration();

		count++;
	//弾を出す
		if( count %150 == 100 &&xPosition>0 && xPosition < GAME_WIDTH-40){
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

	@Override
	public int hit(PlayerChara c){
		for(Shot s: bullet){
			if(s.hit(c) == HIT_MISS){
				return HIT_MISS;
			}
		}
		return super.hit(c);
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
		super.move();
		for(Shot s: bullet){
			s.move();
		}
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(image, (int) (xPosition - width / 2), (int) (yPosition - height / 2),
				(int) width, (int) height, this);
		for(Shot b : bullet){
			b.draw(g);
		}
	}

}

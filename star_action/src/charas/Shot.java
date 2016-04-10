package charas;
import static constants.CharaConstants.*;

import charas.enemys.AbstractEnemy;
public class Shot extends AbstractEnemy {

	//x,yは座標（発射主体に合わせるため）
		 public Shot(int x, int y, double spd, double angle) {
			super(0,0,10,10,IMAGE_ENEMY_BULLET);
			xPosition = x;
			yPosition = y;
			xSpeed = spd*Math.cos(angle);
			ySpeed = spd*Math.sin(angle);
		}
		 @Override
		public int isHitPlayerChara(PlayerChara c){
			if(isHit(c)){
				return HIT_MISS;
			}
			else {
				return HIT_NOT;
			}
		}

		@Override
		public void calcAcceleration() {
		}

		// 移動定義
		@Override
		public void calcXAcceleration(double a) {

		}
		@Override
		public void calcYAcceleration() {

		}

}

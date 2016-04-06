package charas;
import static constants.CharaConstants.*;
public class Shot extends Enemy {

	//x,yは座標（発射主体に合わせるため）
		 public Shot(int x, int y, double spd, double angle) {
			super(0,0,10,10,"image/bullet0.png");
			xPosition = x;
			yPosition = y;
			xSpeed = spd*Math.cos(angle);
			ySpeed = spd*Math.sin(angle);
		}
		 @Override
		public int hit(PlayerChara c){
			if(hit2(c)){
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

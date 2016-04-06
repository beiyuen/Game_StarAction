package charas;

public class Shot extends Enemy {

	//x,yは座標（発射主体に合わせるため）
		 public Shot(int x, int y, double spd, double angle) {
			super(0,0,10,10,"image/bullet0.png");
			xPosition = x;
			yPosition = y;
			xSpeed = spd*Math.cos(angle);
			ySpeed = spd*Math.sin(angle);
		}


		// 移動定義
		public void calcXAcceleration(double a) {

		}
		public void calcYAcceleration() {

		}

}

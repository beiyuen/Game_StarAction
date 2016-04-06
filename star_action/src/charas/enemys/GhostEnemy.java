package charas.enemys;

import java.awt.Graphics;

import charas.Enemy;

public class GhostEnemy extends Enemy {
	private double ysin;
	public GhostEnemy(int x, int y) {
		super(x, y,30,"image/enemy.png");
		xSpeed = (r.nextInt(5) - 2) * 2;
		ysin = 0.0;
	}
	
	public void calcXAcceleration(double a) {
		/*for (Block b : Model.getBlockList()){
			if (//b.hitx(this) &&
				Math.abs(Math.cos((Math.atan2(yPosition-b.yPosition, xPosition-b.xPosition)))) >1/Math.sqrt(2.0))
				//敵とブロックの角度が(-45度~45度)なら
				xSpeed *= -1;
		}*/

		if (xSpeed == 0)
			xSpeed++;
		
	}

	public void calcYAcceleration() {
		/*for (Block b : Model.getBlockList())
			if (//b.hity(this)&&
				Math.abs(Math.cos((Math.atan2(yPosition-b.yPosition, xPosition-b.xPosition)))) <= 1/Math.sqrt(2.0))
				//敵とブロックの角度が(45度~135度 or 225度~315度)なら
				ysin *= -1;
*/
		ySpeed = 5*Math.sin(ysin);
		ysin += 0.1;
		
	}

	public void draw(Graphics g){
		g.drawImage(image, (int)(xPosition-width/2), (int)(yPosition-height/2), (int)(xPosition+width/2),(int)(yPosition+height/2), (int)(image.getWidth(null)*(Math.signum(xSpeed)+1)/4),0,(int)(image.getWidth(null)*(Math.signum(xSpeed)+3)/4
				),(int)image.getHeight(null),this);
	}

	@Override
	public void changeXSpeed() {
		super.changeXSpeed();
	}

	@Override
	public void changeYSpeed() {
		ysin *= -1;
	}

}

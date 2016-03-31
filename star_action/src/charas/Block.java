package charas;
import static constants.MathConstants.*;

//----------------------------------------------------
//
//----------------------------------------------------
import java.awt.Graphics;

public class Block extends AbstractChara {
	private static final long serialVersionUID = 1L;
	public boolean removable = true;

	public Block(int x, int y) {
		width = BLOCK_SIZE;
		height = BLOCK_SIZE;
		xPosition = x * BLOCK_SIZE + BLOCK_SIZE / 2;
		yPosition = y * BLOCK_SIZE + BLOCK_SIZE / 2;
		image = getToolkit().createImage("image/block.png");
	}
	public Block(int x, int y, int w, int h) {
		width = w;
		height = h;
		xPosition = x * BLOCK_SIZE + BLOCK_SIZE / 2;
		yPosition = y * BLOCK_SIZE + BLOCK_SIZE / 2;
	}

	public void calcAcceleration() {
	}

	// 接触判定
	public boolean hit(AbstractChara c) {
		return Math.abs(c.xPosition + c.xSpeed - xPosition) < c.width / 2 + width / 2
				&& Math.abs(c.yPosition + c.ySpeed - yPosition) < c.height / 2 + height / 2;
	}

	// 接触かつ直前にx方向に接触していない場合に位置、速度を調整
	public boolean hitx(AbstractChara c) {
		boolean x = hit(c);
		if (x && Math.abs(c.xPosition - xPosition) >= c.width / 2 + width / 2 ) {
			c.xPosition = xPosition - (c.width / 2 + BLOCK_SIZE / 2) * Math.signum(c.xSpeed);
			//if(c instanceof PlayerChara)
			//	c.xSpeed = 0;

		}
		return x;
	}

	public boolean hity(AbstractChara c) {
		boolean x = hit(c);
		if (x && Math.abs(c.yPosition - yPosition) >= c.height / 2 + height / 2) {
			c.yPosition = yPosition - (c.height / 2 + BLOCK_SIZE / 2) * Math.signum(c.ySpeed);
		//	c.ySpeed = 0;
			// 上にいた場合接地on
			if (c.yPosition <= yPosition) {
				c.ground = true;
			}
		}
		return x;
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
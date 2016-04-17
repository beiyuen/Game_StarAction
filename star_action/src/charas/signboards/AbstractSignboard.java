package charas.signboards;

import static constants.MathConstants.*;

import java.awt.Graphics;

import charas.AbstractChara;
import util.ReferenceItems;

public abstract class AbstractSignboard extends AbstractChara {

	public AbstractSignboard(int x, int y, int i){
		width = 280;
		height = 200;
		xPosition = x * BLOCK_SIZE + BLOCK_SIZE /2;
		yPosition = y * BLOCK_SIZE + BLOCK_SIZE /2;
		initX = xPosition;
		initY = yPosition;
		image = ReferenceItems.getSignboardImage(i);
		imageDrawWidth = 280;
		imageDrawHeight = 200;
	}
	
	public void isHitBlock() {}

	public void calcAcceleration() {}
	
	@Override
	public void changeXSpeed() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void changeYSpeed() {
		// TODO 自動生成されたメソッド・スタブ

	}
	
	public void draw(Graphics g){
		g.drawImage(image, (int)(xPosition - 140), (int) (yPosition - 175), this);
		//g.drawImage(image, (int) (xPosition - width / 2), (int) (yPosition - height / 2),
		//		(int) width, (int) height, this);
	}

}

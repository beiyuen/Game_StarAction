package charas.signboards;

import static constants.MathConstants.*;

import java.awt.Graphics;

import charas.AbstractChara;

public abstract class AbstractSignboard extends AbstractChara {

	public AbstractSignboard(int x, int y, int i){
		width = 280;
		height = 200;
		xPosition = x * BLOCK_SIZE + BLOCK_SIZE /2;
		yPosition = y * BLOCK_SIZE + BLOCK_SIZE /2;
		initX = xPosition;
		initY = yPosition;
		image = referenceItems.getSignboardImage(i);
		imageDrawWidth = 280;
		imageDrawHeight = 200;
	}
	
	public void isHitBlock() {}

	public void calcAcceleration() {}
	
	@Override
	public void changeXSpeed() {}

	@Override
	public void changeYSpeed() {}
	
	public void draw(Graphics g){
		g.drawImage(image, (int)(xPosition - 140), (int) (yPosition - 175), this);
	}

}

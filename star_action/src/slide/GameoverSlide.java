package slide;

import java.awt.Color;
import java.awt.Graphics;

import util.Text;

/**
 * ゲームオーバー時に表示されるスライドです
 * 
 * @author kitahara
 *
 */
public class GameoverSlide extends AbstractSlide {

	public GameoverSlide() {
		super();
		image = referenceItems.getGameoverImage();
		text = new Text(300, 350, "Press 'R' Key to Start Again", font, Color.BLACK);
	}

	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, this);
		text.draw(g);
	}
}

package slide;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import util.Text;

/**
 * タイトル画面で表示されるスライドです
 * 
 * @author kitahara
 *
 */
public class OpeningSlide extends AbstractSlide {

	public OpeningSlide() {
		font = new Font("serif", Font.BOLD, 30);
		image = referenceItems.getOpeningImage();
		text = new Text(250, 400, "Press 'Enter' Key to Start Game", font, Color.BLACK);
	}

	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, this);
		text.draw(g);
	}
}

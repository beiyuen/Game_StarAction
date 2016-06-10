package slide;

import java.awt.Color;
import java.awt.Graphics;

import util.Text;

/**
 * ゲームクリア時に表示されるスライドです
 * 
 * @author kitahara
 *
 */
public class EndingSlide extends AbstractSlide {

	public EndingSlide() {
		super();
		image = referenceItems.getEndingImage();
		text = new Text(300, 200, "Press 'Enter' Key to Back Title", font, Color.WHITE);
	}

	public void setText(int i) {
		text.setText("STAGE " + String.valueOf(i));
	}

	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, this);
		text.draw(g);
	}
}

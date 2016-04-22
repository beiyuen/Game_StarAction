package slide;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import util.ReferenceItems;
import util.Text;

/**
 * タイトル画面で表示されるスライドです
 * 
 * @author kitahara
 *
 */
public class OpeningSlide extends JPanel {

	private Font font = new Font("serif", Font.BOLD, 30);
	private Text text;
	private Image image = null;

	public OpeningSlide() {
		image = ReferenceItems.getOpeningImage();
		text = new Text(250, 400, "Press 'Enter' Key to Start Game", font, Color.BLACK);
	}

	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, this);
		text.draw(g);
	}
}

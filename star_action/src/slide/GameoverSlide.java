package slide;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import util.ReferenceItems;
import util.Text;

/**
 * ゲームオーバー時に表示されるスライドです
 * 
 * @author kitahara
 *
 */
public class GameoverSlide extends JPanel {

	private Font font = new Font("serif", Font.BOLD, 24);
	private Text text;
	private Image image = null;

	public GameoverSlide() {
		image = ReferenceItems.getGameoverImage();
		text = new Text(300, 350, "Press 'R' Key to Start Again", font, Color.BLACK);
	}

	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, this);
		text.draw(g);
	}
}

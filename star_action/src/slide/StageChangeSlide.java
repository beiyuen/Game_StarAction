package slide;

import static constants.MathConstants.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import util.Text;

/**
 * ステージ間に表示するスライドです
 * 
 * @author kitahara
 *
 */
public class StageChangeSlide extends JPanel {

	private Font font = new Font("serif", Font.BOLD, 24);
	private Text text;

	public StageChangeSlide() {

		text = new Text(300, 200, "STAGE 1", font, Color.WHITE);
	}

	public void setText(int i) {
		text.setText("STAGE " + String.valueOf(i));
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		text.draw(g);
	}
}

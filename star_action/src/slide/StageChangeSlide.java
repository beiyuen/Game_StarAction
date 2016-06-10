package slide;

import static constants.MathConstants.*;

import java.awt.Color;
import java.awt.Graphics;

import util.Text;

/**
 * ステージ間に表示するスライドです
 *
 * @author kitahara
 *
 */
public class StageChangeSlide extends AbstractSlide {

	public StageChangeSlide() {
		super();
		text = new Text(300, 200, "WORLD 1-1", font, Color.WHITE);
	}

	public void setText(int world, int stage) {
		text.setText("WORLD " + String.valueOf(world) + "-" + String.valueOf(stage));
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		text.draw(g);
	}
}

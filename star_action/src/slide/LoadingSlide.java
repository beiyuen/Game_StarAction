package slide;

import static constants.MathConstants.*;

import java.awt.Color;
import java.awt.Graphics;

import util.Text;

public class LoadingSlide extends AbstractSlide {

	public LoadingSlide() {
		super();
		text = new Text(630, 570, "NOW LOADING...", font, Color.WHITE);
	}
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		text.draw(g);
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		text.draw(g);

	}

}

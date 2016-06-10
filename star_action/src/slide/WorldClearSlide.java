package slide;

import static constants.ImageConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import util.Text;

/**
 * ワールドクリア時に表示されるスライドです
 * 
 * @author kitahara
 *
 */
public class WorldClearSlide extends AbstractSlide {
	private int count;

	private Image imageWorld = null;
	private Image imageClear = null;

	private double worldX, worldY, clearX, clearY, sin;
	private double worldXspeed, clearXspeed;

	public WorldClearSlide(int i) {
		super();
		imageWorld = referenceItems.getTexteImage(i);
		imageClear = referenceItems.getTexteImage(IMAGE_TEXT_CLEAR);
		text = new Text(250, 500, "Press 'Enter' Key to Go to the Next World", font, Color.BLACK);
		init();
	}

	public void init() {
		count = 0;
		worldX = -950;
		worldY = 20;
		clearX = 1100;
		clearY = 160;
		worldXspeed = 0;
		clearXspeed = 0;
		sin = 0.0;
	}

	public void calcAnimation() {
		count++;
		if (count < 80) {
			sin += 0.05;
			worldXspeed = 30 * Math.sin(sin);
			clearXspeed = -30 * Math.sin(sin);
		} else {
			worldXspeed = 0;
			clearXspeed = 0;
		}
	}

	public void move() {
		worldX += worldXspeed;
		clearX += clearXspeed;
	}

	public void draw(Graphics g) {
		g.drawImage(imageWorld, (int) (worldX), (int) (worldY), this);
		g.drawImage(imageClear, (int) (clearX), (int) (clearY), this);
		if (count > 50) {
			// g.setColor(Color.BLACK);
			text.draw(g);
		}

	}
}

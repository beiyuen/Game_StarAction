package slide;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import util.ReferenceItems;
import util.Text;

public class OpeningSlide extends JPanel{

	private Font font = new Font("serif", Font.BOLD,24);
	private Text text;
	private Image image = null;
	
	public OpeningSlide(){
		image = ReferenceItems.getOpeningImage();
		text = new Text(300, 200, "Press 'Enter' Key to Start Game", font, Color.BLACK);
	}
	
	public void draw(Graphics g){
		g.drawImage(image, 0, 0, this);
		text.draw(g);
	}
}

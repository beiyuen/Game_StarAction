package slide;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import util.ReferenceItems;
import util.Text;

public class EndingSlide extends JPanel{

	private Font font = new Font("serif", Font.BOLD,24);
	private Text text;
	private Image image = null;
	
	public EndingSlide(){
		image = ReferenceItems.getEndingImage();
		text = new Text(300, 200, "Press 'Enter' Key to Back Title", font, Color.WHITE);
	}
	
	public void setText(int i){
		text.setText("STAGE " + String.valueOf(i));
	}
	
	public void draw(Graphics g){
		g.drawImage(image, 0, 0, this);
		text.draw(g);
	}
}

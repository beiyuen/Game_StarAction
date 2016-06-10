package slide;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import resource.ReferenceItems;
import util.Text;

public abstract class AbstractSlide extends JPanel {
	protected Font font;
	protected Text text;
	protected Image image = null;
	protected ReferenceItems referenceItems = ReferenceItems.getReferenceItems();
	
	public AbstractSlide() {
		font = new Font("serif", Font.BOLD, 24); 
	}
	
	public abstract void draw(Graphics g);

}

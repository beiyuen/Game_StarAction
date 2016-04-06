package util;

import static constants.MathConstants.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import star_action.Model;

public class ClickItem extends JPanel{
	private Font font = new Font("serif", Font.BOLD,32);
	private Text text;
	private Image image = null;
	private int imageKind;
	private int width, height;

	public ClickItem(){
		int place[] = Model.getClickableNum();
		int clickable = place[Model.getplacementMode()];
		text = new Text(GAME_WIDTH-40, 32, String.valueOf(clickable), font, Color.WHITE);
		image = getToolkit().createImage("image/mouse1.png");
		if(image == null) System.err.println("adfa");
		imageKind = 0;

		System.out.println("loadimage");
	}

	public void setText(int i){
		text.setText(String.valueOf(i));

	}

	public void draw(Graphics g){
//		g.drawImage(climg, XMAX-50, 0, XMAX, 50,  this);
	//	g.drawImage(image, GAME_WIDTH-50, 0, GAME_WIDTH, 50,width/3*imageKind, 0, width/3*(imageKind+1), height , this);
		g.drawImage(image, GAME_WIDTH-50, 0, GAME_WIDTH, 50,0, 0, 50, 50 , this);

		text.draw(g);
		System.out.println("w :" + width + ", h :" + height);
	}

	public void setImageKind(int placementMode) {
		// TODO 自動生成されたメソッド・スタブ
		imageKind = placementMode;
	}

	public void setSize() {
		// TODO 自動生成されたメソッド・スタブ
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
}

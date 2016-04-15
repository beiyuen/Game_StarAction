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
		text = new Text(GAME_WIDTH-55, 32, String.valueOf(clickable), font, Color.WHITE);
		image = getToolkit().createImage("image/mouse1.png");
		imageKind = 0;
		width = 150;
		height = 50;
	}

	public void setText(int i){
		text.setText(String.valueOf(i));

	}

	public void draw(Graphics g){
		g.drawImage(image, GAME_WIDTH-65, 0, GAME_WIDTH-15, 50,(int)(width/3*imageKind), 0, (int)(width/3*(imageKind+1)), (int)height , this);
		text.draw(g);
	}

	public void setImageKind(int placementMode) {
		// TODO 自動生成されたメソッド・スタブ
		imageKind = placementMode;
	}

	public void setSize() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}

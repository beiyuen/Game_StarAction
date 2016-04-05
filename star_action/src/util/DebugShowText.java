package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import star_action.Model;

public class DebugShowText {
	Text textX,textY,textGX;
	Font font = new Font("serif", Font.BOLD,24);
	ArrayList<Text> textArray;

	public DebugShowText(){
		textArray = new ArrayList<Text>();
		textX = new Text(700, 20, "x:", font, Color.BLACK);
		textY = new Text(700, 40, "y:", font, Color.BLACK);
		textGX = new Text(700, 60, "gx:", font, Color.BLACK);
		textArray.add(textX);
		textArray.add(textY);
		textArray.add(textGX);
	}
	
	public void run(double x, double y){
		textX.setText("x:" + String.valueOf((int)x));
		textY.setText("y:" + String.valueOf((int)y));
		textGX.setText("gx:" + String.valueOf(Model.getGoalBlock().getxPosition()));
	}
	
	public void draw(Graphics g){
		for (Text text : textArray) {
			text.draw(g);
		}
	}
}

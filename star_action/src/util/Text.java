package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Text extends JPanel{
	int xpos, ypos;
	String txt;
	Font fn;
	Color col;

	//座標、文字列、フォント
	public Text(int x, int y, String t, Font f, Color c) {
		col = c;
		xpos = x;
		ypos = y;
		txt = t;
		fn = f;
	}

	//文字列変更
	public void setText(String t) {
		txt = t;
	}

	//描画
	public void draw(Graphics g) {
		g.setColor(col);
		g.setFont(fn);
		g.drawString(txt, xpos, ypos);
	}
}

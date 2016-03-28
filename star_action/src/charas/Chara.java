package charas;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class Chara extends JPanel {
	public double xpos, ypos; //位置
	double xsp, ysp; //スピード
	int width, height; //縦横サイズ
	Image img;  //画像
	boolean ground = false;  //設置判定
	final static int SIZE = 50; //マップ単位（ブロックサイズ）

Chara(){}

	//x,yはマップ座標
	Chara(int x, int y, int w, int h, String c) {
		xpos = (x + 0.5) * SIZE;
		ypos = (y + 0.5) * SIZE;
		xsp = 0;
		ysp = 0;
		width = w;
		height = h;
		img = getToolkit().createImage(c);
	}

	// 接触判定
	public boolean hit(Chara c) {
		return Math.abs(c.xpos + c.xsp - xpos) < c.width / 2 + width / 2
				&& Math.abs(c.ypos + c.ysp - ypos) < c.height / 2 + height / 2;
	}

	// ジャンプ
	void jump() {
		if (ground)
			ysp -= 25 + Math.abs(xsp) / 5;
	}

	// 呼び出され用
	public void calcAcceleration() {
		yAcceleration();
		xAcceleration(0.7);
	}

	// 減速、衝突 aは加速度相当
	void xAcceleration(double a) {
		if (xsp > 0)
			xsp -= a;
		if (xsp < 0)
			xsp += a;
		if (Math.abs(xsp) < a)	//振動防止
			xsp = 0;

		for (Block b : Mario.s.block)
			b.hitx(this);

		xpos += xsp;
	}

	// 空中の挙動
	void yAcceleration() {
		if(!ground) ysp += 2;

		//穴落下
		if (ypos > Mario.YMAX - height / 2) {
			death();
		}

		//ブロックに接触できていなければgroundをfalseに
		boolean g = true;
		for (Block b : Mario.s.block)
			if (b.hity(this))
				g = false;
		if (g)
			ground = false;


		ypos += ysp;
	}

	//描画
	public void draw(Graphics g) {
		g.drawImage(img, (int) (xpos - width / 2), (int) (ypos - height / 2),
				(int) width, (int) height, this);
	}

	void death(){};
}

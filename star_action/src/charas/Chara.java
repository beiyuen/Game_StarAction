package charas;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class Chara extends JPanel {
	public double xPosition, yPosition; //位置
	double xSpeed, ySpeed; //スピード
	int width, height; //縦横サイズ
	Image img;  //画像
	boolean ground = false;  //設置判定
	final static int SIZE = 50; //マップ単位（ブロックサイズ）

Chara(){}

	//x,yはマップ座標
	Chara(int x, int y, int w, int h, String c) {
		xPosition = (x + 0.5) * SIZE;
		yPosition = (y + 0.5) * SIZE;
		xSpeed = 0;
		ySpeed = 0;
		width = w;
		height = h;
		img = getToolkit().createImage(c);
	}

	// 接触判定
	public boolean hit(Chara c) {
		return Math.abs(c.xPosition + c.xSpeed - xPosition) < c.width / 2 + width / 2
				&& Math.abs(c.yPosition + c.ySpeed - yPosition) < c.height / 2 + height / 2;
	}

	// ジャンプ
	void jump() {
		if (ground)
			ySpeed -= 25 + Math.abs(xSpeed) / 5;
	}

	// 呼び出され用
	public void calcAcceleration() {
		yAcceleration();
		xAcceleration(0.7);
	}

	// 減速、衝突 aは加速度相当
	void xAcceleration(double a) {
		if (xSpeed > 0)
			xSpeed -= a;
		if (xSpeed < 0)
			xSpeed += a;
		if (Math.abs(xSpeed) < a)	//振動防止
			xSpeed = 0;

		for (Block b : Mario.s.block)
			b.hitx(this);

		xPosition += xSpeed;
	}

	// 空中の挙動
	void yAcceleration() {
		if(!ground) ySpeed += 2;

		//穴落下
		if (yPosition > Mario.YMAX - height / 2) {
			death();
		}

		//ブロックに接触できていなければgroundをfalseに
		boolean g = true;
		for (Block b : Mario.s.block)
			if (b.hity(this))
				g = false;
		if (g)
			ground = false;


		yPosition += ySpeed;
	}

	//描画
	public void draw(Graphics g) {
		g.drawImage(img, (int) (xPosition - width / 2), (int) (yPosition - height / 2),
				(int) width, (int) height, this);
	}

	void death(){};
}

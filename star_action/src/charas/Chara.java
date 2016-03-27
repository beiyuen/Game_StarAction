package charas;

import java.awt.Image;

public class Chara {
	public double xpos, ypos; //位置
	double xsp, ysp; //スピード
	int width, height; //縦横サイズ
	Image img;  //画像
	boolean ground = false;  //設置判定
	final static int SIZE = 50; //マップ単位（ブロックサイズ）

	public Chara(){

	}
}

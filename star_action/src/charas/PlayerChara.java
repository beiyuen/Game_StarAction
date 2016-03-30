package charas;
import static constants.MathConstants.*;

import java.awt.Graphics;

import star_action.Model;

// 操作キャラ
public class PlayerChara extends AbstractChara {
	private static final long serialVersionUID = 1L;

	public boolean moveRight = false, moveLeft = false, up = false,dash = false;
	public int death, kill;
	int yoko = 3,tate = 2; //画像の分割数
	int imageNum,imageKind,kabe;//i,imgkind:画像用,kabe:敵用

	public PlayerChara(int w, int h) {
		image = getToolkit().createImage("src/image/otamesi.png");
		width = w;
		height = h;
		init();
	}

	//初期化
	public void init() {
		xPosition = 3*BLOCK_SIZE;
		yPosition = 8*BLOCK_SIZE;
		xSpeed=0;
		ySpeed = 0;
		ground = false;
		imageNum=0;
		imageKind = 0;
	}

	//落下時、敵接触時など


	//衝突処理を追加
	public void calcAcceleration(){
		super.calcAcceleration();
		//敵との当たり判定を計算
		for (Enemy e : Model.getEnemyList()){
			if (e.hit(this)){}
				//death();
		}
		for (Needle n : Model.getNeedleList()){
			if (n.hit(this)){}
				//death();
		}
	}

	// 操作およびhit時の挙動
	public void calcXAcceleration(double a) {
		if (moveRight){
			if(dash&& xSpeed <= 25){
				xSpeed += 2;
			}
			else if(dash==false && xSpeed <= 10){
				xSpeed += 1.5;
			}
			imageNum ++;
			imageKind = (imageNum % 18) / 6;//0,1,2番目の画像
		}
		else if (moveLeft){
			if(dash&& xSpeed >= -25){
				xSpeed -= 2;
			}
			else if(dash==false && xSpeed >= -10){
				xSpeed -= 1.5;
			}
			imageNum ++;
			imageKind = (imageNum % 18) / 6 + 3; //3,4,5番目の画像
		}

		//スクロール 各オブジェクトを動かすことで処理


		//後退スクロール不可

			if (xPosition + xSpeed - width / 2 < 0) {
				xPosition = width / 2;
				changeXSpeed();

	}

		super.calcXAcceleration(a);
	}

	 //ジャンプ操作と画像の変更
	public void calcYAcceleration() {
		super.calcYAcceleration();
		// 接地しているときのジャンプ処理
		if (up == true && ground) {
			jump();
			if(imageKind<3)
				imageKind = 0;
			else
				imageKind = 3;
			ground = false;
		}
	}

	//描画 分割後の画像幅を示すpwidthを設定
	public void draw(Graphics g){
		double sx, sy;
		int pwidth = image.getWidth(null)/yoko;
		int pheight = image.getHeight(null)/tate;
        sx = (imageKind % yoko) * pwidth;
        sy = (imageKind / yoko) * pheight;

		g.drawImage(image,(int)(xPosition- width / 2),(int)(yPosition- height / 2),
				(int)(xPosition+width/2),(int)(yPosition+height/2),
				(int)(sx),(int)(sy), (int)(sx+pwidth), (int)(sy+pheight),this);
	}

	@Override
	public void changeXSpeed() {
		xSpeed = 0;

	}

	@Override
	public void changeYSpeed() {
		// TODO 自動生成されたメソッド・スタブ

	}

}

package charas;
import java.awt.*;
import java.util.*;

import main.*;

// 操作キャラ
public class PlayerChara extends Chara {
	private static final long serialVersionUID = 1L;
	
	public boolean right = false, left = false, up = false,dash = false;
	public int death, kill;
	int yoko = 3,tate = 2; //画像の分割数
	int i,imgkind,kabe;//i,imgkind:画像用,kabe:敵用

	public PlayerChara(int w, int h) {
		img = getToolkit().createImage("src/image/otamesi.png");
		width = w;
		height = h;
		init();
	}

	//初期化
	public void init() {
		xPosition = 3*SIZE;
		yPosition = 8*SIZE;
		xSpeed=0;
		ySpeed = 0;
		ground = false;
		i=0;
		imgkind = 0;
		Mario.clcount=0;
	}
	
	//落下時、敵接触時など
	void death(){
		init();
		death++;
		if(death == 1){
			Mario.slide = 2;
		}
	}

	//衝突処理を追加
	public void sim(){
		super.sim();
		Iterator<Chara> iterator;
		Iterator<Needle> niterator;
		//NPCが使うMarioのイテレータと同期させる
		for (iterator = Mario.iterator = Mario.s.enemy.iterator(); iterator.hasNext();) {
			if (iterator.next().hit(this))
				death();
		}
		for (niterator = Mario.s.needle.iterator(); niterator.hasNext();) {
			if (niterator.next().hit(this))
				death();
		}
	}
	
	// 操作およびhit時の挙動
	void xsim(double a) {
		if (right){
			if(dash&& xSpeed <= 25)
				xSpeed += 2;
			else if(dash==false && xSpeed <= 10)
				xSpeed += 1.5;
		}
		else if (left){
			if(dash&& xSpeed >= -25)
				xSpeed -= 2;
			else if(dash==false && xSpeed >= -10)
				xSpeed -= 1.5;
		}
		if(right){
			i ++;
		imgkind=(i%18)/6;//0,1,2番目の画像
		}
		else if(left){
			i ++;
		imgkind=(i%18)/6+3; //3,4,5番目の画像
		}			
	

		//スクロール 各オブジェクトを動かすことで処理
		if(Mario.s.scroll()){//s.num=4(ボス戦)ではスクロールできない
		if (xPosition + xSpeed + width / 2 > Mario.XMAX - 400) {
			this.xPosition -= xSpeed;
			for (Block b : Mario.s.block)
				b.xPosition -= xSpeed;
			for (Chara e : Mario.s.enemy){
				e.xPosition -= xSpeed;
				if(e instanceof NPCshoot)
					for(Shot s: NPCshoot.bullet)
						s.xpos -= xSpeed;
			}
			for (Needle n: Mario.s.needle)
				n.xPosition -= xSpeed;
		}
		}
		
		//後退スクロール不可
		
			if (xPosition + xSpeed - width / 2 < 0) {
				xPosition = width / 2;
				xSpeed = 0;
			
	}

		super.xsim(a);
	}

	//ジャンプ操作と画像の変更
	void ysim() {
		super.ysim();
		if (up == true && ground) {
			jump();
			if(imgkind<3)
				imgkind = 0;
			else
				imgkind = 3;
			ground = false;
		}
	}
	
	//描画 分割後の画像幅を示すpwidthを設定
	public void draw(Graphics g){
		double sx, sy;
		int pwidth = img.getWidth(null)/yoko;
		int pheight = img.getHeight(null)/tate;
        sx = (imgkind % yoko) * pwidth;
        sy = (imgkind / yoko) * pheight;
        
		g.drawImage(img,(int)(xPosition- width / 2),(int)(yPosition- height / 2),
				(int)(xPosition+width/2),(int)(yPosition+height/2),
				(int)(sx),(int)(sy), (int)(sx+pwidth), (int)(sy+pheight),this);
	}

}

package charas;
import java.util.*;

import main.*;

// 非操作キャラ(敵)
public class Enemy extends Chara {
	private static final long serialVersionUID = 1L;
	
	Random r = new Random(); //移動速度をランダムにしている

	//NPC自身のコンストラクタ
	public Enemy(int x, int y){
		super(x,y,30,30,"src/image/enemy2.png");
		xSpeed = (r.nextInt(5) - 2) * 2;
	}
	
	//height=widthな子クラスのコンストラクタに使用する　
	Enemy(int x, int y, int s, String c){
		super(x,y,s,s,c);
	}
	//上以外の子クラスのコンストラクタに使用する
	Enemy(int x, int y, int w, int h, String c){
		super(x,y,w,h,c);	
	}

	//enemyからこのオブジェクトを除去
	void death() {
		Mario.sound("stamp.wav", 0.6);
		Mario.iterator.remove();
	}

	// 踏まれた時の対応
	public boolean hit(Chara c) {
		if (c instanceof PlayerChara && super.hit(c)
				&& Math.sin((Math.atan2(c.yPosition-yPosition, c.xPosition-xPosition))) <= -1/Math.sqrt(2.0)) {
			death();
			((PlayerChara)c).kill++;
			if(((PlayerChara)c).up)
				((PlayerChara)c).ySpeed=-30;
			else
				((PlayerChara)c).ySpeed=-10;
			return false;
		}
		return super.hit(c);
	}

	// 移動定義
	void xsim(double a) {
		//ブロックにあたったら反転
		for (Block b : Mario.s.block)
			if (b.hitx(this))
				xSpeed *= -1;

		//ランダムで速度0になったときに加速
		if (xSpeed == 0)
			xSpeed++;
		xPosition += xSpeed;
	}

}

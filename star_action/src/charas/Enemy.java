package charas;
import static constants.CharaConstants.*;

import java.util.Random;

// 非操作キャラ(敵)
public class Enemy extends AbstractChara {
	private static final long serialVersionUID = 1L;

	Random r = new Random(); //移動速度をランダムにしている

	//NPC自身のコンストラクタ
	public Enemy(int x, int y){
		super(x,y,30,30,"image/enemy2.png");
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
		death = true;
		System.out.println("enemy.death");
		//Mario.sound("stamp.wav", 0.6);
		//Mario.iterator.remove();
	}

	// PlayerCharaと敵の当たり判定。上から当たったらHIT_TREAD,それ以外の角度から当たったらHIT_MISS,当たっていなかったらHIT_NOT
	public int hit(PlayerChara c) {
		boolean hit = super.hit(c);
		if (hit){
			if(Math.sin((Math.atan2(c.yPosition-yPosition, c.xPosition-xPosition))) <= -1/Math.sqrt(2.0)) {
				// ここから下を変える
				death();
				return HIT_TREAD;
			}
			return HIT_MISS;
		}
		return HIT_NOT;
	}

	// 移動定義
	public void calcXAcceleration(double a) {
		//ブロックにあたったら反転
	//	for (Block b : Mario.s.block)
	//		if (b.hitx(this))
	//			xSpeed *= -1;

		//ランダムで速度0になったときに加速
		if (xSpeed == 0)
			xSpeed = Math.random()*4;

	}

	@Override
	public void changeXSpeed() {
		xSpeed *= -1;
	}

	@Override
	public void changeYSpeed() {
		ySpeed = 0;

	}



}

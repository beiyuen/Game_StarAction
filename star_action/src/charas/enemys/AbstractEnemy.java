package charas.enemys;
import static constants.CharaConstants.*;
import static constants.MathConstants.*;

import java.util.Random;

import charas.AbstractChara;
import charas.PlayerChara;
import util.ReferenceItems;

/**
 * 敵の抽象的なクラス。すべての敵はこのクラスのサブクラス。敵は基本的にプレイヤーが上から踏むことで倒すことができる。
 * @author kitahara
 *
 */
public abstract class AbstractEnemy extends AbstractChara {

	public Random r;

	/**
	 * 
	 * @param x x座標
	 * @param y y座標
	 * @param w 横幅
	 * @param h 高さ
	 * @param c 画像
	 */
	public AbstractEnemy(int x, int y, int w, int h, int c) {
		r = new Random();
		xPosition = (x + 0.5) * BLOCK_SIZE;
		yPosition = (y + 0.5) * BLOCK_SIZE;
		initX = xPosition;
		initY = yPosition;
		xSpeed = (r.nextInt(5) - 2) * 2;
		ySpeed = 0;
		width = w;
		height = h;
		image = ReferenceItems.getEnemyImage(c);
		death = false;
	}

	//enemyからこのオブジェクトを除去
	public void death() {
		death = true;
		System.out.println("enemy.death");
		//Mario.sound("stamp.wav", 0.6);
	}

	/**
	 * PlayerCharaと敵の当たり判定。上から当たったらHIT_TREAD,それ以外の角度から当たったらHIT_MISS,当たっていなかったらHIT_NOT
	 * @param c
	 * @return
	 */
	public int isHitPlayerChara(PlayerChara c) {
		boolean hit = super.isHit(c);
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

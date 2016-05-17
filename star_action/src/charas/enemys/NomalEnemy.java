package charas.enemys;

import static constants.CharaConstants.*;
import static constants.ImageConstants.*;

import charas.PlayerChara;

/**
 * 一番単純な敵。左右の移動のみ行う
 * 
 * @author kitahara
 *
 */
public class NomalEnemy extends AbstractEnemy {

	public NomalEnemy(int x, int y) {
		super(x, y, 30, 20, IMAGE_ENEMY_SLIME);
		imageLine = 2;
		imageColumn = 2;
		imageDrawWidth = 30;
		imageDrawHeight = 20;
	}

	// PlayerCharaと敵の当たり判定。上から当たったらHIT_TREAD,それ以外の角度から当たったらHIT_MISS,当たっていなかったらHIT_NOT
	public int isHitPlayerChara(PlayerChara c) {
		boolean hit = super.isHit(c);
		if (hit) {
			if (Math.sin((Math.atan2(c.getyPosition() - yPosition, c.getxPosition() - xPosition))) <= -1 / Math.sqrt(2.0)) {
				// ここから下を変える
				death();
				return HIT_TREAD;
			}
			return HIT_MISS;
		}
		return HIT_NOT;
	}

	@Override
	public void calcXAcceleration() {
		super.calcXAcceleration();

		if (xSpeed < 0) {
			imageCount++;
			imageKind = (imageCount % 16) / 8;// 0,1番目の画像
		} else if (xSpeed > 0) {
			imageCount++;
			imageKind = (imageCount % 16) / 8 + 2; // 2,3番目の画像
		}
	}

}

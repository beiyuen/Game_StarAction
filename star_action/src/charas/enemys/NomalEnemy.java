package charas.enemys;
import static constants.CharaConstants.*;
import static constants.ImageConstants.*;

import charas.PlayerChara;

/**
 * 一番単純な敵。左右の移動のみ行う。
 * @author kitahara
 *
 */
public class NomalEnemy extends AbstractEnemy {

	public NomalEnemy(int x, int y){
		super(x,y,30,30,IMAGE_ENEMY_SLIME);
	}

	// PlayerCharaと敵の当たり判定。上から当たったらHIT_TREAD,それ以外の角度から当たったらHIT_MISS,当たっていなかったらHIT_NOT
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

}

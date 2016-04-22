package charas.enemys;

import static constants.CharaConstants.*;
import static constants.ImageConstants.*;
import static constants.MathConstants.*;
import static constants.SoundCnstants.*;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import charas.Needle;
import charas.PlayerChara;
import charas.blocks.AbstractBlock;
import star_action.Model;
import util.Sound;

/**
 * 地面を歩く敵。プレイヤーが触れると、その時にこの敵が触れているブロックとトゲを破壊する。この敵はy方向の移動をしないので、
 * ブロックが消えても落ちることはない。また、この敵をプレイヤーは倒せない
 * 
 * @author kitahara
 *
 */
public class WalkEnemy extends AbstractEnemy {
	boolean isHit;

	public WalkEnemy(int x, int y) {
		super(x, y, 40, 50, IMAGE_ENEMY_WALK);
		imageColumn = 4;
		imageLine = 2;
		imageDrawWidth = 40;
		imageDrawHeight = 50;
		isHit = false;
	}

	@Override
	public int isHitPlayerChara(PlayerChara c) {
		if (isHit(c)) {
			isHit = true;
		} else {
			isHit = false;
		}
		return HIT_NOT;
	}

	@Override
	public void calcAcceleration() {
		if (Model.getGameStatus() == GAMESTATUS_DIE) {
			isHit = false;
		}
		calcXAcceleration(0.7);
		isHitBlock();
		if (hitLeft || hitRight) {
			changeXSpeed();
		} else if (hitHead || hitLeg) {
			changeYSpeed();
		}
	}

	@Override
	public void calcXAcceleration(double a) {
		super.calcXAcceleration(a);

		if (isHit) {
			for (AbstractBlock b : Model.getBlockList()) {
				if (b.isHit(this) && !b.isDeath()) {
					b.setDeath(true);
					try {
						Sound.soundSE(SOUND_SE_SURPRISE, 0.2);
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
						e.printStackTrace();
					}
				}
			}
			for (Needle n : Model.getNeedleList()) {
				if (n.isHit(this) && !n.isDeath()) {
					n.setDeath(true);
					try {
						Sound.soundSE(SOUND_SE_SURPRISE, 0.2);
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (xSpeed < 0) {
			imageCount++;
			imageKind = (imageCount % 32) / 8;// 0,1,2,3番目の画像
		} else if (xSpeed > 0) {
			imageCount++;
			imageKind = (imageCount % 32) / 8 + 4; // 4,5,6,7番目の画像
		}
	}

	public void calcYAcceleration() {
	}
}

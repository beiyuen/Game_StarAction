package charas.enemys;

import static constants.ImageConstants.*;
import static constants.MathConstants.*;
import static constants.SoundCnstants.*;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import charas.Needle;
import charas.PlayerChara;
import charas.blocks.AbstractBlock;
import enums.GameStatus;
import enums.HitPlayer;
import star_action.Model;

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
		super(x, y, 30, 40, IMAGE_ENEMY_WALK);
		yPosition = (y+1) * BLOCK_SIZE - height/2;
		initY = yPosition;
		imageColumn = 4;
		imageLine = 2;
		imageDrawWidth = 30;
		imageDrawHeight = 40;
		isHit = false;
	}
	
	public WalkEnemy init(int x, int y){
		xPosition = (x + 0.5) * BLOCK_SIZE;
		yPosition = (y+1) * BLOCK_SIZE - height/2;
		initX = xPosition;
		initY = yPosition;
		death = false;
		isHit = false;
		using = true;
		return this;
	}

	@Override
	public HitPlayer isHitPlayerChara(PlayerChara c) {
		if (isHit(c)) {
			isHit = true;
		} else {
			isHit = false;
		}
		return HitPlayer.Not;
	}

	@Override
	public void calcAcceleration() {
		if (Model.getGameStatus() == GameStatus.Die) {
			isHit = false;
		}
		calcXAcceleration();
		isHitBlock();
		if (hitLeft || hitRight) {
			changeXSpeed();
		} else if (hitHead || hitLeg) {
			changeYSpeed();
		}
	}

	@Override
	public void calcXAcceleration() {
		super.calcXAcceleration();

		if (isHit) {
			for (AbstractBlock b : Model.getBlockList()) {
				if (b.isHit(this) && !b.isDeath()) {
					b.setDeath(true);
					try {
						sound.soundSE(SOUND_SE_SURPRISE, 0.2);
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
						e.printStackTrace();
					}
				}
			}
			for (Needle n : Model.getNeedleList()) {
				if (n.isHit(this) && !n.isDeath()) {
					n.setDeath(true);
					try {
						sound.soundSE(SOUND_SE_SURPRISE, 0.2);
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

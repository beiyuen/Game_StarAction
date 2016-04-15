package charas.bosses;

import static constants.CharaConstants.*;
import static constants.ImageConstants.*;
import static constants.MathConstants.*;
import static constants.SoundCnstants.*;

import java.awt.Graphics;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import charas.PlayerChara;
import charas.blocks.AbstractBlock;
import charas.blocks.GoalBlock;
import charas.enemys.AbstractEnemy;
import star_action.Model;
import util.Sound;

public abstract class AbstractBoss extends AbstractEnemy {

	int treadedNum; // 踏まれ数カウンター
	int state = 0;
			 // 踏まれた時の一時変数,１なら踏んだとき
	int	count = 0; // 動きのタイミングを決める
	int hp;
	boolean goAway;

	public AbstractBoss(int x, int y) {
		super(x, y - 1, 75, 75, IMAGE_ENEMY_KING);
		goAway = false;
		xSpeed = -6;
		treadedNum = 1;
		imageDrawWidth = 75;
		imageDrawHeight = 75;
		imageLine = 2;
		imageColumn = 4;
		hp = 5;
	}

	public void init() {
		super.init();
		//goAway = false;
		count = 0;
		xSpeed = -6;
		state = BOSS1_STATE_1;
		hitLeg = false;
	}

	public void calcAcceleration() {
		isHitBlock();
		if (hitLeg) {
			changeYSpeed();
		}
		calcYAcceleration();
		calcXAcceleration(0.7);
	}

	// enemyからこのオブジェクトを除去
	public void death() {
		Model.getBlockList().add(new GoalBlock(9, 5, 0));
		// Mario.s.block.add(new GameclearBlock(9,5));
		// Mario.sound("surprise.wav",0.6);
		// Mario.iterator.remove();
	}

	public int isHitPlayerChara(PlayerChara c) {

		if (treadedNum < hp // やられる回数を定義
				&& Math.abs(c.xPosition + c.xSpeed - xPosition) < c.width / 2 + width / 2
				&& Math.abs(c.yPosition + c.ySpeed - yPosition) < c.height / 2 + height / 2
				&& Math.sin((Math.atan2(c.yPosition - yPosition, c.xPosition - xPosition))) <= -1 / Math.sqrt(2.0)) {

			if (xPosition < 500) {
				xSpeed = 15;
			}
			else {
				xSpeed = -15;
			}

			// 通常時にプレイヤーに踏まれたら
			if (!goAway ) {
				System.out.println("boss treaded");
				state++;
				treadedNum++;
				try {
					Sound.soundSE(SOUND_SE_TREAD, 0.6);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
			}
			goAway  = true;// 走って壁に逃げる時
			count = 0;
			return HIT_TREAD;
		} else
			return super.isHitPlayerChara(c);
	}

	public void isHitBlock() {
		// 壁に当たった時の反応など
		switch (state) {
		case 0:
		case 1:
			if (xSpeed == 0) {
				xSpeed++;
			}

			if (xPosition + xSpeed < 70 || xPosition + xSpeed > GAME_WIDTH - 70) {
				if (!goAway )
					xSpeed *= -1;
				else if (goAway ) {
					xSpeed = 0;
					goAway  = false;
				}
			}
			break;
		case 2:
		case 3:
			if (xPosition + xSpeed < 70) {
				xPosition = 80;
				xSpeed *= -1;
				if (goAway ) {
					if (state == 3) {
						xSpeed = r.nextInt(4) + 1;
						ySpeed = -5;
					}
					goAway  = false;
				}
			} else if (xPosition + xSpeed > GAME_WIDTH - 70) {
				xPosition = GAME_WIDTH - 80;
				xSpeed *= -1;
				if (goAway ) {
					if (state == 3) {
						xSpeed = r.nextInt(4) - 9;
						ySpeed = -5;
					}
					goAway  = false;
				}
			}
			break;
		case 4:
			if (goAway  && (xPosition < 70 || xPosition > GAME_WIDTH - 70)) {
				for (AbstractBlock b : Model.getBlockList()) {
					b.setDeath(true);
				}
				// Mario.sound("surprise.wav", 0.6);
			}
			break;
		}
		if (yPosition >= GAME_HEIGHT - 84) {
			yPosition = GAME_HEIGHT - 83;
			hitLeg = true;
		} else {
			hitLeg = false;
		}
	}

	public abstract void calcBossAction();


	public void draw(Graphics g) {
		double px = Model.getPlayerChara().getxPosition();
		if (xPosition > px) {
			imageCount++;
			imageKind = (imageCount % 8) / imageLine;// 0,1,2,3番目の画像
		} else if (xPosition <= px) {
			imageCount++;
			imageKind = (imageCount % 8) / imageLine + imageColumn; // 4,5,6,7番目の画像
		}
		super.draw(g);
		double sx, sy;
	}
}

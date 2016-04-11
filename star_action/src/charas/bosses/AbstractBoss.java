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
import charas.enemys.AbstractEnemy;
import star_action.Model;
import util.Sound;

public class AbstractBoss extends AbstractEnemy {

	int treadedNum; // 踏まれ数カウンター
	int i = 0, imagekind = 0, tate = 2, yoko = 4, state = 0, // ボスの動きを決める変数
			isTreadedTemp = 0, // 踏まれた時の一時変数,１なら踏んだとき
			count = 0; // 動きのタイミングを決める
	int hp;

	BossAction action = new BossAction();
	public double imageWidth, imageHeight;

	public AbstractBoss(int x, int y) {
		super(x, y - 1, 75, 75, IMAGE_ENEMY_KING);
		xSpeed = -6;
		treadedNum = 1;
		imageWidth = 300;
		imageHeight = 150;
		tate = 2;
		yoko = 4;
		hp = 5;
	}

	public void init() {
		super.init();
		isTreadedTemp = 0;
		count = 0;
		xSpeed = -6;
		state = 0;
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
			if (isTreadedTemp == 0) {
				System.out.println("boss treaded");
				state++;
				treadedNum++;
				try {
					Sound.soundSE(SOUND_SE_TREAD, 0.6);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
			}
			isTreadedTemp = 1;// 走って壁に逃げる時
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
				if (isTreadedTemp == 0)
					xSpeed *= -1;
				else if (isTreadedTemp == 1) {
					xSpeed = 0;
					isTreadedTemp = 0;
				}
			}
			break;
		case 2:
		case 3:
			if (xPosition + xSpeed < 70) {
				xPosition = 80;
				xSpeed *= -1;
				if (isTreadedTemp == 1) {
					if (state == 3) {
						xSpeed = r.nextInt(4) + 1;
						ySpeed = -5;
					}
					isTreadedTemp = 0;
				}
			} else if (xPosition + xSpeed > GAME_WIDTH - 70) {
				xPosition = GAME_WIDTH - 80;
				xSpeed *= -1;
				if (isTreadedTemp == 1) {
					if (state == 3) {
						xSpeed = r.nextInt(4) - 9;
						ySpeed = -5;
					}
					isTreadedTemp = 0;
				}
			}
			break;
		case 4:
			if (isTreadedTemp == 1 && (xPosition < 70 || xPosition > GAME_WIDTH - 70)) {
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

	// 移動定義
	public void calcXAcceleration(double a) {
		// ブロックにあたったら反転

		///////////// 設定///////////////////
		if (isTreadedTemp == 0) {
			switch (state) {
			case 0:
				action.pattern0(this);
				break;
			case 1:
				action.pattern1(this);
				break;
			case 2:
				action.pattern2(this);
				break;
			case 3:
				action.pattern3(this);
				break;
			case 4:
				action.pattern4(this);
				break;
			}
			count++;
		} else if (isTreadedTemp == 1) {
			action.pattern0(this);
		}
	}

	// xsimここまで
	public void calcYAcceleration() {// 重力関係
		if (isTreadedTemp == 0) {
			switch (state) {
			case 0:
			case 1:
				super.calcYAcceleration();
				break;
			case 2:
				if (yPosition > GAME_HEIGHT - 84) {
					yPosition = GAME_HEIGHT - 87;
					ySpeed = 0;
					count = -40;
					action.flag = 2;
				}
				break;
			case 3:
			case 4:
				if (yPosition > GAME_HEIGHT - 84) {
					yPosition = GAME_HEIGHT - 87;
					ySpeed *= -1;
				} else if (yPosition < 50) {
					yPosition = 55;
					ySpeed *= -1;
				}
				break;
			}
		} else if (isTreadedTemp == 1) {
			super.calcYAcceleration();
			if (yPosition > GAME_HEIGHT - 84)
				yPosition = GAME_HEIGHT - 87;
		}
	}

	public void draw(Graphics g) {
		double px = Model.getPlayerChara().getxPosition();
		if (xPosition > px) {
			i++;
			imagekind = (i % 8) / tate;// 0,1,2,3番目の画像
		} else if (xPosition <= px) {
			i++;
			imagekind = (i % 8) / tate + yoko; // 4,5,6,7番目の画像
		}
		double sx, sy;
		int pwidth = (int) (imageWidth / yoko);
		int pheight = (int) (imageHeight / tate);
		sx = (imagekind % yoko) * pwidth;
		sy = (imagekind / yoko) * pheight;

		g.drawImage(image, (int) (xPosition - width / 2), (int) (yPosition - height / 2), (int) (xPosition + width / 2),
				(int) (yPosition + height / 2), (int) (sx), (int) (sy), (int) (sx + pwidth), (int) (sy + pheight),
				this);
	}
}

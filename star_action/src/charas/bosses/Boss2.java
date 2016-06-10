package charas.bosses;

import static constants.CharaConstants.*;
import static constants.MathConstants.*;
import static constants.SoundCnstants.*;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import charas.PlayerChara;
import charas.Shot;
import charas.blocks.AbstractBlock;
import charas.blocks.WorldClearBlock;
import enums.HitPlayer;
import star_action.Model;
/**
 * ワールド2のボス。様々な行動をする
 *
 * @author kitahara
 *
 */
public class Boss2 extends AbstractBoss {

	public ArrayList<Shot> bullet = new ArrayList<Shot>();
	BossAction action = new BossAction();

	public Boss2(int x, int y) {
		super(x, y);
		hp = 5;
		init();
	}

	public void init() {
		super.init();
		death = false;
		bullet.clear();
		goAway = false;
		count = 0;
		xSpeed = -6;
		state = BOSS2_STATE_1;
		hitLeg = false;
		treadedNum = 0;

	}

	private void nextState() {
		state++;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void calcAcceleration() {
		isHitBlock();
		if (hitLeg && state != BOSS2_STATE_4) {
		}
		calcBossAction();
		// ショットの動作
		Iterator<Shot> bulletIterator = bullet.iterator();// ショットを移動させる
		while (bulletIterator.hasNext()) { // 次の要素がある限りループ
			Shot s = bulletIterator.next();// 次の要素を取得
			s.calcAcceleration();
			for (AbstractBlock b : Model.getPlaceBlockList()) {
				if (b.isHit(s)) {
					bulletIterator.remove();
					break;
				}
			}

			if (s.isOutOfFrame()) {
				bulletIterator.remove();
			}
		}
	}

	public void death() {
		Model.getBlockList().add(new WorldClearBlock(9, 5));
		death = true;
	}

	public HitPlayer isHitPlayerChara(PlayerChara c) {
		for (Shot s : bullet) {
			if (s.isHit(c)) {
				return HitPlayer.Miss;
			}
		}
		// プレイヤーが上から踏みつけたとき
		if (treadedNum < hp
				&& Math.abs(c.getxPosition() + c.getxSpeed() - xPosition) < c.getWidth() / 2 + width / 2
				&& Math.abs(c.getyPosition() + c.getySpeed() - yPosition) < c.getHeight() / 2 + height / 2
				&& Math.sin((Math.atan2(c.getyPosition() - yPosition, c.getxPosition() - xPosition))) <= -1 / Math.sqrt(2.0)) {
			// 横に逃げるために速度変化を行う
			if (xPosition < 500) {
				xSpeed = 15;
			} else {
				xSpeed = -15;
			}
			// 踏まれたのが逃げている途中でなければ
			if (!goAway) {
				nextState();
				treadedNum++;// 踏まれた回数が1増える
				// 踏まれた時の効果音
				try {
					sound.soundSE(SOUND_SE_TREAD, 0.6);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
				if (state == BOSS2_STATE_4 || state == BOSS2_STATE_5) {
					bullet.clear();
				}
				// 踏まれた数 == HPなら死亡
				if (treadedNum == hp) {
					death();
				}
			}
			goAway = true;// 走って壁に逃げる時
			count = 0;
			hitLeg = false;
			return HitPlayer.Tread;

		} else {
			return super.isHitPlayerChara(c);
		}

	}

	/**
	 * 画面の両端にぶつかっているかを判定,goAwayによって処理を変える
	 */
	public void isHitBlock() {
		// 壁に当たった時の反応など
		switch (state) {
		case BOSS2_STATE_1:
		case BOSS2_STATE_2:
			if (xPosition + xSpeed < 70 || xPosition + xSpeed > GAME_WIDTH - 120) {
				xPosition = xPosition < 300 ? 80 : GAME_WIDTH - 130;
				if (!goAway){
					xSpeed *= -1;
					}
				else if (goAway) {
					xSpeed = 0;
					goAway = false;
				}
			}
			break;
		case BOSS2_STATE_3:
		case BOSS2_STATE_4:
			if (xPosition + xSpeed < 70) {
				xPosition = 80;
				xSpeed *= -1;
				if (goAway) {
					if (state == BOSS2_STATE_4) {
						xSpeed = r.nextInt(4) + 1;
						ySpeed = -5;
					}
					goAway = false;
				}
			} else if (xPosition + xSpeed > GAME_WIDTH - 120) {
				xPosition = GAME_WIDTH - 130;
				xSpeed *= -1;
				if (goAway) {
					if (state == BOSS2_STATE_4) {
						xSpeed = r.nextInt(4) - 9;
						ySpeed = -5;
					}
					goAway = false;
				}
			}
			break;
		case BOSS2_STATE_5:
			if (goAway && (xPosition < 70 || xPosition > GAME_WIDTH - 120)) {
				for (AbstractBlock b : Model.getBlockList()) {
					b.setDeath(true);
				}
				goAway = false;
				try {
					sound.soundSE(SOUND_SE_SURPRISE, 0.4);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
			}
			break;
		}
	}

	public void calcBossAction() {
		if (!goAway) {
			switch (state) {
			case BOSS2_STATE_1:
				action.pattern1();
				break;
			case BOSS2_STATE_2:
				action.pattern2();
				break;
			case BOSS2_STATE_3:
				action.pattern3();
				break;
			case BOSS2_STATE_4:
				action.pattern4();
				break;
			case BOSS2_STATE_5:
				action.pattern5();
				break;
			}
			count++;
		} else {
			action.patternAway();
		}
		super.calcYAcceleration();
	}

	public void calcXAcceleration() {
	}

	public void calcYAcceleration() {// 重力関係
		// 通常時
		if (!goAway) {
			switch (state) {
			case BOSS2_STATE_1:
			case BOSS2_STATE_2:
				super.calcYAcceleration();
				break;
			case BOSS2_STATE_3:
				if (yPosition > GAME_HEIGHT - 84) {
					yPosition = GAME_HEIGHT - 87;
					ySpeed = 0;
					count = -40;
					action.flag = 2;
				}
				break;
			case BOSS2_STATE_4:
			case BOSS2_STATE_5:
				if (yPosition > GAME_HEIGHT - 84) {
					yPosition = GAME_HEIGHT - 87;
					ySpeed *= -1;
				} else if (yPosition < 50) {
					yPosition = 55;
					ySpeed *= -1;
				}
				break;
			}
			// 避難時
		} else {
			super.calcYAcceleration();
		}
	}

	public void move() {
		super.move();
		for (Shot s : bullet) {
			s.move();
		}
	}

	public void draw(Graphics g) {
		super.draw(g);
		for (Shot shot : bullet) {
			shot.draw(g);
		}
	}

	/**
	 * ボスの動きを定義した内部クラス。hitLegがtrueの時はy方向の動作に対し重力が発生せず、falseの時は重力が発生する
	 *
	 * @author kitahara
	 *
	 */
	class BossAction {
		public int flag = 0;

		public BossAction() {
		}

		public void patternAway() {
			pattern1();
		}

		/**
		 * 横移動をするのみ、床に降りたらhitLegを常にtrueにする
		 *
		 * @param c
		 */
		public void pattern1() {
			if (getyPosition() > GAME_HEIGHT - 84) {
				hitLeg = true;
				setyPosition(GAME_HEIGHT - 87);
				changeYSpeed();
			}
		}

		/**
		 * 地面を横移動し、一定時間経過したら飛び跳ねる。着地後の横移動後の向きはプレイヤーとの距離によって変化
		 *
		 * @param c
		 */
		public void pattern2() {
			if(count == 0){
				setxSpeed(7);
			}
			if (count % 45 == 10) {
				jump();
				hitLeg = false;
			} else if (getyPosition() > GAME_HEIGHT - 84) {
				hitLeg = true;
				setyPosition(GAME_HEIGHT - 87);
				changeYSpeed();
			}
		}

		/**
		 * 空中を横移動しながら主人公に向かって弾を撃つ。一定時間経過するか、プレイヤーのx座標がボスのx座標と近かったら落下
		 *
		 * @param c
		 */
		public void pattern3() {
			double px = Model.getPlayerChara().getxPosition();
			// 最初に壁をよじ登る処理
			if (count == 1) {
				hitLeg = true;
				setxSpeed(0.0);
				setySpeed(-4.0);
				flag = 0;
			}
			// 空中に浮かんだあと、そのy座標上で横移動する
			else if (count % 140 == 40) {
				setySpeed(0.0);
				if (xPosition < px) {
					setxSpeed(2.0);
				} else {
					setxSpeed(-2.0);
				}
			}
			// 一定時間経過するか、プレイヤーのx座標がボスのx座標と近かったら落下
			else if (flag == 0 && ((count % 140 > 40 && xPosition - 75 < px && xPosition + 75 > px) || count == 140)) {
				hitLeg = false;
				flag = 1;// 落下
				setxSpeed(0.0);
			}

			if (getyPosition() > GAME_HEIGHT - 84) {
				hitLeg = true;
				setyPosition(GAME_HEIGHT - 87);
				setCount(-40);
				changeYSpeed();
			}
			// 落下中
			// else if(flag==1){
			// c.ySpeed+=2.2;
			// }

			if (count > 0 && count % 40 == 35) {
				bullet.add(new Shot((int) (xPosition), (int) (yPosition), 1.0,
						Math.atan2(Model.getPlayerChara().getyPosition() - yPosition, px - xPosition)));
				try {
					sound.soundSE(SOUND_SE_SHOT, 0.3);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * x方向、y方向に決められた動きで動き、一定時間ごとに６方向に弾を撃つ
		 *
		 * @param c
		 */
		public void pattern4() {// ショットを打つ
			hitLeg = true;
			if (count % 35 == 20) {
				for (int i = 0; i < 6; i++)
					bullet.add(new Shot((int) (xPosition), (int) (yPosition), 3.0, (count + i * 60) * Math.PI / 180));
				try {
					sound.soundSE(SOUND_SE_SHOT, 0.3);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
			}
			if (yPosition < 50 || yPosition > GAME_HEIGHT - 84) {
				setySpeed(ySpeed * -1);
			}
		}

		/**
		 * 壁沿いで縦移動のみ行う
		 *
		 * @param c
		 */
		public void pattern5() {// 縦移動
			if (count == 0) {
				hitLeg = true;
				setxSpeed(0.0);
				setySpeed(-5);
			}
			if (yPosition < 50 || yPosition > GAME_HEIGHT - 84) {
				setySpeed(ySpeed * -1);
			}
		}
	}

}

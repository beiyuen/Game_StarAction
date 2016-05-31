package charas.bosses;

import static constants.CharaConstants.*;
import static constants.MathConstants.*;
import static constants.SoundCnstants.*;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import charas.PlayerChara;
import charas.blocks.AbstractBlock;
import charas.blocks.WorldClearBlock;
import star_action.Model;
/**
 * ワールド1のボス。踏みつけ攻撃のみ行う
 * 
 * @author kitahara
 *
 */
public class Boss1 extends AbstractBoss {

	BossAction action = new BossAction();
	boolean jumping;

	public Boss1(int x, int y) {
		super(x, y);
		hp = 3;
		init();
	}

	public void init() {
		super.init();
		death = false;
		goAway = false;
		count = 0;
		xSpeed = 0;
		state = BOSS1_STATE_1;
		hitLeg = false;
		treadedNum = 0;
		jumping = false;
	}

	private void nextState() {
		state++;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void calcAcceleration() {
		isHitBlock();
		checkDeath();
		calcBossAction();
	}

	public void death() {
		Model.getBlockList().add(new WorldClearBlock(9, 5));
		death = true;
		System.out.println("enemy.death");
	}

	public int isHitPlayerChara(PlayerChara c) {

		if (treadedNum < hp && Math.abs(c.getxPosition() + c.getxSpeed() - xPosition) < c.getWidth() / 2 + width / 2
				&& Math.abs(c.getyPosition() + c.getySpeed() - yPosition) < c.getHeight() / 2 + height / 2
				&& Math.sin((Math.atan2(c.getyPosition() - yPosition, c.getxPosition() - xPosition))) <= -1 / Math.sqrt(2.0)) {
			if (jumping) {
				return HIT_MISS;
			}
			if (xPosition < 500) {
				xSpeed = 15;
			} else {
				xSpeed = -15;
			}
			// 通常時にプレイヤーに踏まれたら
			if (!goAway) {
				nextState();
				treadedNum++;
				try {
					sound.soundSE(SOUND_SE_TREAD, 0.6);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
				if (treadedNum == hp) {
					death();
				}
			}
			goAway = true;
			setCount(0);
			hitLeg = false;
			return HIT_TREAD;

		} else {
			return super.isHitPlayerChara(c);
		}

	}

	/**
	 * 画面の両端にぶつかっているかを判定,goAwayによって処理を変える
	 */
	public void isHitBlock() {

		if (xPosition + xSpeed < 70 || xPosition + xSpeed > GAME_WIDTH - 120) {
			if (!goAway)
				xSpeed *= -1;
			else if (goAway) {
				xSpeed = 0;
				goAway = false;
			}
		}

	}
	/**
	 * 画面の下端より下にいたら死亡処理を行う
	 */
	public void checkDeath() {
		if (yPosition > GAME_HEIGHT + 50) {
			try {
				sound.soundSE(SOUND_SE_SURPRISE, 0.6);
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				e.printStackTrace();
			}
			death();
		}
	}

	public void calcBossAction() {
		if (!goAway) {
			switch (state) {
			case BOSS1_STATE_1:
				action.pattern1(1.0);
				break;
			case BOSS1_STATE_2:
				action.pattern1(1.2);
				break;
			case BOSS1_STATE_3:
				action.pattern1(1.4);
				break;
			}
			count++;
		} else {
			action.patternAway();
		}
		super.calcYAcceleration();
	}

	/**
	 * ボスの動きを定義した内部クラス。hitLegがtrueの時はy方向の動作に対し重力が発生せず、falseの時は重力が発生する
	 * 
	 * @author kitahara
	 *
	 */
	class BossAction {
		public BossAction() {
		}

		public void patternAway() {
			if (getyPosition() > GAME_HEIGHT - 84) {
				hitLeg = true;
				setyPosition(GAME_HEIGHT - 87);
				changeYSpeed();
			}
		}

		/**
		 * 横移動をするのみ、床に降りたらhitLegを常にtrueにする
		 * 
		 * @param c
		 */
		public void pattern1(double d) {
			if (count == 0) {
				jumping = true;
				setySpeed(-15 * d);
			} 
			else if (count == 55) {
				setxPosition(Model.getPlayerChara().getxPosition());
				setyPosition(-100);
				setySpeed(15 * d);
			}
			else if (yPosition > GAME_HEIGHT - 84 && yPosition < GAME_HEIGHT + 20) {
				boolean ground = false;
				for (AbstractBlock b : Model.getBlockList()) {
					if (b.hity(Boss1.this).height == 1) {
						ground = true;
						break;
					}

				
				}
				//System.out.println(ground + " y :" + yPosition + " ysp :" + ySpeed);
				// 着地点が存在したら
				if (ground) {
					jumping = false;
					hitLeg = true;
					setyPosition(GAME_HEIGHT - 87);
					changeYSpeed();
					setCount(200);
				}

			} else if (count == 240) {
				setCount(-1);
			}
System.out.println(count);
		}

	}

}

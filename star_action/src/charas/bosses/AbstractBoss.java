package charas.bosses;

import static constants.CharaConstants.*;
import static constants.ImageConstants.*;

import java.awt.Graphics;

import charas.PlayerChara;
import charas.enemys.AbstractEnemy;
import star_action.Model;

public abstract class AbstractBoss extends AbstractEnemy {

	int treadedNum; // 踏まれ数カウンター
	int state = 0;
	int count = 0; // 動きのタイミングを決める
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
		count = 0;
		xSpeed = -6;
		state = BOSS2_STATE_1;
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

	public void death() {
	}

	public int isHitPlayerChara(PlayerChara c) {
		return super.isHitPlayerChara(c);
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
	}
}

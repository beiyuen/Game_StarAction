package charas.bosses;

import static constants.CharaConstants.*;
import static constants.ImageConstants.*;

import java.awt.Graphics;

import charas.PlayerChara;
import charas.enemys.AbstractEnemy;
import enums.HitPlayer;
import star_action.Model;

public abstract class AbstractBoss extends AbstractEnemy {

	protected int state = 0;  // 敵の行動パターン
	protected int count = 0;  // 動きのタイミングを決める
	protected int hp;		  // HP
	protected boolean goAway; // 逃げ状態かどうかを表す変数

	public AbstractBoss(int x, int y) {
		super(x, y - 1, 75, 75, IMAGE_ENEMY_KING);
		goAway = false;
		xSpeed = -6;
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
	}

	public void death() {
	}

	public HitPlayer isHitPlayerChara(PlayerChara c) {
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

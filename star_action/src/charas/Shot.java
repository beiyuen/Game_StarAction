package charas;

import static constants.ImageConstants.*;
import static constants.MathConstants.*;

import java.awt.Graphics;

import charas.enemys.AbstractEnemy;
import enums.HitPlayer;

/**
 * 敵が撃つ弾です
 *
 * @author kitahara
 *
 */
public class Shot extends AbstractEnemy {

	// x,yは座標（発射主体に合わせるため）
	public Shot(int x, int y, double spd, double angle) {
		super(0, 0, 10, 10, IMAGE_ENEMY_BULLET);
		xPosition = x;
		yPosition = y;
		xSpeed = spd * Math.cos(angle);
		ySpeed = spd * Math.sin(angle);
	}

	@Override
	public HitPlayer isHitPlayerChara(PlayerChara c) {
		if (isHit(c)) {
			return HitPlayer.Miss;
		} else {
			return HitPlayer.Not;
		}
	}

	/**
	 * 等速直線運動をするので、初期化時に決めたxSpeedとySpeedは変更しない
	 */
	@Override
	public void calcAcceleration() {
	}

	// 移動定義
	@Override
	public void calcXAcceleration() {

	}

	@Override
	public void calcYAcceleration() {
	}
	/**
	 * 弾が画面の外に出ているか判定する。画面外ならtrue
	 * @return
	 */
	public boolean isOutOfFrame() {
		return xPosition > GAME_WIDTH || xPosition < 0 || yPosition > GAME_HEIGHT || yPosition < 0;
	}

	public void draw(Graphics g) {
		g.drawImage(image, (int) (xPosition - width / 2), (int) (yPosition - height / 2), (int) width, (int) height,
				this);
	}

}

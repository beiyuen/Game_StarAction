package charas.enemys;

import static constants.ImageConstants.*;
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
import enums.HitPlayer;
import stages.MapItems;
import star_action.Model;
/**
 * 弾を撃つ敵。打った球はこの敵が倒されても存在し続ける
 *
 * @author kitahara
 *
 */
public class ShootEnemy extends AbstractEnemy {
	private ArrayList<Shot> bullet = new ArrayList<Shot>(); // この敵が放つ弾を保持しておくリスト
	private Iterator<Shot> bulletIterator = null;
	private int shotCount;									// 弾を撃つためのタイミングを表す値
	private boolean death2;									// 死亡判定
	private MapItems mapItems = null;

	public ShootEnemy(int x, int y) {
		super(x, y, 30, 30, IMAGE_ENEMY_SHOT);
		xSpeed = 0.0;
		initXSpeed = xSpeed;
		shotCount = 0;
		death2 = false;
		mapItems = MapItems.getMapItems();
	}

	@Override
	public void calcAcceleration() {
		if (!death2) {
			super.calcAcceleration();
			shotCount++;
			// 弾を出す
			if (shotCount % 150 == 100 && xPosition > 0 && xPosition < GAME_WIDTH - 40) {
				for (int i = 0; i < 6; i++) {
					bullet.add(
							mapItems.getShots().init((int) (xPosition + 1), (int) (yPosition - 13), 3.0, 
							(Math.atan2(yPosition - Model.getPlayerChara().getyPosition(), xPosition - Model.getPlayerChara().getxPosition()) + (i * 60) * Math.PI / 180)));
				}
				// 音鳴らす
				try {
					sound.soundSE(SOUND_SE_SHOT, 0.4);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
			}
			bulletIterator = bullet.iterator();
			while (bulletIterator.hasNext()) {
				Shot s = bulletIterator.next();
				s.calcAcceleration();
				for (AbstractBlock b : Model.getPlaceBlockList()) {
					if (b.isHit(s)) {
						s.setUsing(false);
						bulletIterator.remove();
						break;
					}
				}
				if (s.isOutOfFrame()) {
					s.setUsing(false);
					bulletIterator.remove();
				}
			}
		}

	}

	@Override
	public void calcXAcceleration() {
	}

	@Override
	public void death() {
		death2 = true;
		try {
			sound.soundSE(SOUND_SE_TREAD, 0.6);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics g) {
		if (!death2) {
			g.drawImage(image, (int) (xPosition - width / 2), (int) (yPosition - height / 2), (int) width, (int) height,
					this);
		}
		for (Shot b : bullet) {
			b.draw(g);
		}
	}

	public void init() {
		super.init();
		bullet.clear();
		death2 = false;
	}
	
	public boolean isDeath2(){
		if(!death2){ // 本体が生きているとき
			return false;
		}
		if(bullet.size() == 0){ // 本体がやられていて、描画中の弾が存在しないとき
			return true;
		}
		return false; // 本体がやられていて、描画中の弾が存在するとき
	}

	public HitPlayer isHitPlayerChara(PlayerChara c) {
		for (Shot s : bullet) {
			if (s.isHitPlayerChara(c) == HitPlayer.Miss) {
				return HitPlayer.Miss;
			}
		}
		if (!death2) {
			return super.isHitPlayerChara(c);
		}
		return HitPlayer.Not;
	}

	@Override
	public void move() {
		if (!death2) {
			super.move();
		}

		for (Shot s : bullet) {
			s.move();
		}
	}

	@Override
	public void scroll(double speed) {
		super.scroll(speed);
		for (Shot s : bullet) {
			s.scroll(speed);
		}
	}

}

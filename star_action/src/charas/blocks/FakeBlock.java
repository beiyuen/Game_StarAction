package charas.blocks;

import static constants.MathConstants.*;
import static constants.SoundCnstants.*;

import java.awt.Graphics;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import charas.AbstractChara;
import charas.PlayerChara;

/**
 * プレイヤーが触れると消えるブロック。乗ることはできない。敵が触れても消えることはない。
 * 
 * @author kitahara
 *
 */
public class FakeBlock extends AbstractBlock {
	

	public FakeBlock(int x, int y, int i) {
		super(x, y, i);
		removable = true;
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public FakeBlock init(int x, int y, int i){
		xPosition = x * BLOCK_SIZE + BLOCK_SIZE / 2;
		yPosition = y * BLOCK_SIZE + BLOCK_SIZE / 2;
		initX = xPosition;
		initY = yPosition;
		image = referenceItems.getBlockImage(i);
		isHitx = false;
		using = true;
		death = false;
		return this;
	}

	void death() {
		death = true;
		try {
			sound.soundSE(SOUND_SE_SURPRISE, 0.4);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	/**
	 * キャラがブロックに触れたときの処理。キャラがプレイヤーの場合とその他の場合に分かれる
	 */
	public boolean isHitMove(AbstractChara c) {
		if (c instanceof PlayerChara) {
			return hit((PlayerChara) c);
		} else {
			return super.isHitMove(c);
		}
	}

	/**
	 * プレイヤーがブロックに触れたときの処理
	 * 
	 * @param c
	 * @return
	 */
	private boolean hit(PlayerChara c) {
		if (death)
			return false;
		if (super.isHit(c)) {
			death();
			return false;
		}
		return false;
	}

	public void draw(Graphics g) {
		if (!death) {
			super.draw(g);
		}
	}
}

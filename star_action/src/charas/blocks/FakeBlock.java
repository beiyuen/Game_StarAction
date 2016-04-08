package charas.blocks;

import java.awt.Graphics;

import charas.AbstractChara;
import charas.Block;
import charas.PlayerChara;


/**
 * プレイヤーが触れると消えるブロック。乗ることはできない。敵が触れても消えることはない。
 * @author kitahara
 *
 */
public class FakeBlock extends Block {


	public FakeBlock(int x, int y) {
		super(x, y);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	void death() {
		death = true;
	}

	
	/**
	 * キャラがブロックに触れたときの処理。キャラがプレイヤーの場合とその他の場合に分かれる
	 */
	public boolean hit(AbstractChara c){
		if(c instanceof PlayerChara){
			return hit((PlayerChara) c);
		}
		else {
			return super.hit(c);
		}
	}

	/**
	 * プレイヤーがブロックに触れたときの処理
	 * @param c
	 * @return
	 */
	private boolean hit(PlayerChara c) {
		if(death) return false;
		if (super.hit(c)){
		//	Mario.sound("surprise.wav", 0.6);
			death();
			return false;
		}
		return false;
	}
	
	public void draw(Graphics g){
		if(!death){
			super.draw(g);
		}
	}
}

package charas.blocks;

import java.awt.Graphics;

import charas.AbstractChara;
import charas.Block;
import charas.PlayerChara;

/**
 * 見えないブロック。プレイヤーは触れられないが、敵は触れることができる。空中の床の端に配置することで、
 * 敵は床の端に移動したときにそのまま落ちずに、跳ね返ることができる。
 * @author kitahara
 *
 */
public class ClearBlock extends Block {

	public ClearBlock(int x, int y) {
		super(x, y);
		removable = false;
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	/**
	 * ブロックの当たり判定
	 * @param c
	 * @return
	 */
	public boolean hit(AbstractChara c){
		if(c instanceof PlayerChara){
			return false;
		}
		else {
			return super.hit(c);
		}
	}

	public void draw(Graphics g){
		
	}
}

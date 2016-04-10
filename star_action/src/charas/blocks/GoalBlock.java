package charas.blocks;

import static constants.ImageConstants.*;

import charas.PlayerChara;
import charas.enemys.AbstractEnemy;

public class GoalBlock extends AbstractBlock {

	public GoalBlock(int x, int y) {
		super(x, y, 40, 50, IMAGE_BLOCK_GOAL);
	}

	/**
	 * プレイヤーがゴールに達したかを判定
	 * @param c
	 * @return
	 */
	public boolean hit(PlayerChara c){
		return super.isHit(c);
	}
	/**
	 * 敵がゴールブロックに当たっているか判定。意味がないので常にfalseを返す
	 * @param e
	 * @return
	 */
	public boolean hit(AbstractEnemy e){
		return false;
	}
}

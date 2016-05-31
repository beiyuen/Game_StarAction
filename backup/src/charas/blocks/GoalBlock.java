package charas.blocks;

import static constants.ImageConstants.*;

import charas.AbstractChara;
import charas.PlayerChara;

public class GoalBlock extends AbstractBlock {

	public GoalBlock(int x, int y) {
		super(x, y, 40, 50, IMAGE_BLOCK_GOAL);
	}

	public boolean hitGoal(AbstractChara c) {
		if (c instanceof PlayerChara) {
			return hitGoal((PlayerChara) c);
		}
		return false;
	}

	/**
	 * プレイヤーがゴールに達したかを判定
	 * 
	 * @param c
	 * @return
	 */
	public boolean hitGoal(PlayerChara c) {
		if (super.isHit(c)) {
			return true;
		}
		return false;
	}
}

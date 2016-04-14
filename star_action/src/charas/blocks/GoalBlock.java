package charas.blocks;

import static constants.ImageConstants.*;

import charas.AbstractChara;
import charas.PlayerChara;
import star_action.Model;

public class GoalBlock extends AbstractBlock {

	public GoalBlock(int x, int y) {
		super(x, y, 40, 50, IMAGE_BLOCK_GOAL);
	}
	
	public void hitGoal(AbstractChara c){
		if(c instanceof PlayerChara){
			hitGoal((PlayerChara)c);
		}
	}

	/**
	 * プレイヤーがゴールに達したかを判定
	 * @param c
	 * @return
	 */
	public void hitGoal(PlayerChara c){
		if(super.isHit(c)){
			Model.nextStage();
		}
	}
}

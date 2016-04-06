package charas;

public class GoalBlock extends Block {

	public GoalBlock(int x, int y) {
		super(x, y,40,50);
		image = getToolkit().createImage("image/goal.png");
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * プレイヤーがゴールに達したかを判定
	 * @param c
	 * @return
	 */
	public boolean hit(PlayerChara c){
		return super.hit(c);
	}
	/**
	 * 敵がゴールブロックに当たっているか判定。意味がないので常にfalseを返す
	 * @param e
	 * @return
	 */
	public boolean hit(Enemy e){
		return false;
	}
}

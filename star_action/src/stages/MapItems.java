package stages;
import static constants.CharaConstants.*;

import charas.Needle;
import charas.Shot;
import charas.blocks.ClearBlock;
import charas.blocks.FakeBlock;
import charas.blocks.GoalBlock;
import charas.blocks.HardBlock;
import charas.blocks.NomalBlock;
import charas.blocks.WorldClearBlock;
import charas.enemys.GhostEnemy;
import charas.enemys.MoveEnemy;
import charas.enemys.NomalEnemy;
import charas.enemys.ShootEnemy;
import charas.enemys.WalkEnemy;

/**
 * ステージごとにマップのアイテムをそれぞれnewしなおさなくて済むように、あらかじめオブジェクトプールを作っておくクラス
 * @author kitahara
 *
 */
public class MapItems extends Thread{

	private ClearBlock[] clearBlocks;
	private FakeBlock[] fakeBlocks;
	private GoalBlock[] goalBlocks;
	private HardBlock[] hardBlocks;
	private NomalBlock[] nomalBlocks;
	private WorldClearBlock[] worldClearBlocks;

	private GhostEnemy[] ghostEnemies;
	private MoveEnemy[] moveEnemies;
	private NomalEnemy[] nomalEnemies;
	private ShootEnemy[] shootEnemies;
	private WalkEnemy[] walkEnemies;

	private Needle[] needles;
	private Shot[] shots;

	// シングルトン用
	private static MapItems mapItems = null;

	private MapItems() {}

	public ClearBlock getClearBlocks() {
		for (ClearBlock clearBlock : clearBlocks) {
			if(!clearBlock.isUsing()){ // 現在使用されていないなら、それを返す
				return clearBlock;
			}
		}
		return new ClearBlock(0, 0); // すべて使用中なら新しいオブジェクトを返す
	}

	public FakeBlock getFakeBlocks() {
		for (FakeBlock fakeBlock : fakeBlocks) {
			if(!fakeBlock.isUsing()){ // 現在使用されていないなら、それを返す
				return fakeBlock;
			}
		}
		return new FakeBlock(0, 0, 0); // すべて使用中なら新しいオブジェクトを返す
	}

	public GoalBlock getGoalBlocks() {
		for (GoalBlock goalBlock : goalBlocks) {
			if(!goalBlock.isUsing()){ // 現在使用されていないなら、それを返す
				return goalBlock;
			}
		}
		return new GoalBlock(0, 0); // すべて使用中なら新しいオブジェクトを返す
	}

	public HardBlock getHardBlocks() {
		for (HardBlock hardBlock : hardBlocks) {
			if(!hardBlock.isUsing()){ // 現在使用されていないなら、それを返す
				return hardBlock;
			}
		}
		return new HardBlock(0, 0); // すべて使用中なら新しいオブジェクトを返す
	}

	public NomalBlock getNomalBlocks() {
		for (NomalBlock nomalBlock : nomalBlocks) {
			if(!nomalBlock.isUsing()){ // 現在使用されていないなら、それを返す
				return nomalBlock;
			}
		}
		System.out.println("new NomalBlock");
		return new NomalBlock(0, 0); // すべて使用中なら新しいオブジェクトを返す
	}

	public WorldClearBlock getWorldClearBlocks() {
		for (WorldClearBlock worldClearBlock : worldClearBlocks) {
			if(!worldClearBlock.isUsing()){ // 現在使用されていないなら、それを返す
				return worldClearBlock;
			}
		}
		return new WorldClearBlock(0, 0); // すべて使用中なら新しいオブジェクトを返す
	}

	public GhostEnemy getGhostEnemies() {
		for (GhostEnemy ghostEnemy : ghostEnemies) {
			if(!ghostEnemy.isUsing()){ // 現在使用されていないなら、それを返す
				return ghostEnemy;
			}
		}
		return new GhostEnemy(0, 0); // すべて使用中なら新しいオブジェクトを返す
	}

	public MoveEnemy getMoveEnemies() {
		for (MoveEnemy moveEnemy : moveEnemies) {
			if(!moveEnemy.isUsing()){ // 現在使用されていないなら、それを返す
				return moveEnemy;
			}
		}
		return new MoveEnemy(0, 0, 0); // すべて使用中なら新しいオブジェクトを返す
	}

	public NomalEnemy getNomalEnemies() {
		for (NomalEnemy nomalEnemy : nomalEnemies) {
			if(!nomalEnemy.isUsing()){ // 現在使用されていないなら、それを返す
				return nomalEnemy;
			}
		}
		return new NomalEnemy(0, 0); // すべて使用中なら新しいオブジェクトを返す
	}

	public ShootEnemy getShootEnemies() {
		for (ShootEnemy shootEnemy : shootEnemies) {
			if(!shootEnemy.isUsing()){ // 現在使用されていないなら、それを返す
				return shootEnemy;
			}
		}
		return new ShootEnemy(0, 0); // すべて使用中なら新しいオブジェクトを返す
	}

	public WalkEnemy getWalkEnemies() {
		for (WalkEnemy walkEnemy : walkEnemies) {
			if(!walkEnemy.isUsing()){ // 現在使用されていないなら、それを返す
				return walkEnemy;
			}
		}
		return new WalkEnemy(0, 0); // すべて使用中なら新しいオブジェクトを返す
	}

	public Needle getNeedles() {
		for (Needle needle : needles) {
			if(!needle.isUsing()){ // 現在使用されていないなら、それを返す
				return needle;
			}
		}
		return new Needle(0, 0, 0); // すべて使用中なら新しいオブジェクトを返す
	}

	public Shot getShots() {
		for (Shot shot : shots) {
			if(!shot.isUsing()){ // 現在使用されていないなら、それを返す
				return shot;
			}
		}
		return new Shot(0, 0, 0, 0); // すべて使用中なら新しいオブジェクトを返す
	}

	
	/**
	 * 配列の初期化と、各要素の初期化を行う
	 */
	public void loadItems(){
		clearBlocks = new ClearBlock[PLACE_BLOCK_MAX];
		fakeBlocks = new FakeBlock[PLACE_BLOCK_MAX];
		goalBlocks = new GoalBlock[PLACE_BLOCK_MAX];
		hardBlocks = new HardBlock[PLACE_BLOCK_MAX];
		nomalBlocks = new NomalBlock[PLACE_BLOCK_MAX];
		worldClearBlocks = new WorldClearBlock[PLACE_BLOCK_MAX];

		ghostEnemies = new GhostEnemy[PLACE_ENEMY_MAX];
		moveEnemies = new MoveEnemy[PLACE_ENEMY_MAX];
		nomalEnemies = new NomalEnemy[PLACE_ENEMY_MAX];
		shootEnemies = new ShootEnemy[PLACE_ENEMY_MAX];
		walkEnemies = new WalkEnemy[PLACE_ENEMY_MAX];

		needles = new Needle[PLACE_NEEDLE_MAX];
		shots = new Shot[PLACE_SHOT_MAX];

		for (int i = 0; i < PLACE_BLOCK_MAX; i++) {
			clearBlocks[i] = new ClearBlock(0, 0);
			fakeBlocks[i] = new FakeBlock(0, 0, 0);
			goalBlocks[i] = new GoalBlock(0, 0);
			hardBlocks[i] = new HardBlock(0, 0);
			nomalBlocks[i] = new NomalBlock(0, 0);
			worldClearBlocks[i] = new WorldClearBlock(0, 0);
		}

		for (int i = 0; i < PLACE_ENEMY_MAX; i++) {
			ghostEnemies[i] = new GhostEnemy(0, 0);
			moveEnemies[i] = new MoveEnemy(0, 0, 0);
			nomalEnemies[i] = new NomalEnemy(0, 0);
			shootEnemies[i] = new ShootEnemy(0, 0);
			walkEnemies[i] = new WalkEnemy(0, 0);
		}

		for (int i = 0; i < PLACE_NEEDLE_MAX; i++) {
			needles[i] = new Needle(0, 0, 0);
		}
		
		for (int i = 0; i < PLACE_SHOT_MAX; i++) {
			shots[i] = new Shot(0, 0, 0, 0);
		}
	}




	public static MapItems getMapItems(){
		if (mapItems == null) {
			mapItems = new MapItems();
		}
		return mapItems;
	}


	public void run(){
		loadItems();
	}
}

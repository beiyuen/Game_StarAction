package stages;

import static constants.StageConstants.*;

import java.util.ArrayList;

import charas.Block;
import charas.Enemy;
import charas.GoalBlock;
import charas.Needle;
import charas.blocks.ClearBlock;
import charas.blocks.FakeBlock;
import charas.blocks.NomalBlock;
import charas.enemys.GhostEnemy;
import charas.enemys.MoveEnemy;
import charas.enemys.ShootEnemy;
import charas.enemys.WalkEnemy;
public class Stage {

	public int currentStageNum; //現在のステージ数
	public int currentClickableNum[];
	int clickableNum[][] = {{10,10,10},{5,0,0},{0,0,0},{0,5,0},{6,0,0}}; //クリック可能回数
	public boolean scrollable[] = {true,true,false,true,false};
	public boolean currentScrollable;

	public ArrayList<Block> blockList = new ArrayList<Block>();
	public ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	public ArrayList<Needle> needleList = new ArrayList<Needle>();
	public GoalBlock goalBlock;

	 int[][] map;



	public Stage(){
	}

	public void setStage(int stageNum){
		blockList.clear();
		enemyList.clear();
		needleList.clear();
		map = StageMap.getStageMap(stageNum);

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				switch (map[i][j]) {
					// ブロックの配置
					case h:	//消せないブロック
						blockList.add(new Block(j, i));
						break;
					case n: //右クリックで消せるブロック
						blockList.add(new NomalBlock(j, i));
						break;
					case c: //敵のみが触れられる透明ブロック
						blockList.add(new ClearBlock(j, i));
						break;
					case f: //プレイヤーが触れると消えるブロック
						blockList.add(new FakeBlock(j, i));
						break;
					case g: // ゴール
						goalBlock = new GoalBlock(j, i);
						break;	
						
					// とげの配置	
					case u: // とげ(上向き)
						needleList.add(new Needle(j, i, 0));
						break;
					case r: // とげ(右向き)
						needleList.add(new Needle(j, i, 1));
						break;
					case d: // とげ(下向き)
						needleList.add(new Needle(j, i, 2));
						break;
					case l: // とげ(左向き)
						needleList.add(new Needle(j, i, 3));
						break;
					case U: // とげ+ブロック(上向き)
						blockList.add(new NomalBlock(j, i));
						needleList.add(new Needle(j, i, 0));
						break;
					case R: // とげ+ブロック(右向き)
						blockList.add(new NomalBlock(j, i));
						needleList.add(new Needle(j, i, 1));
						break;
					case D: // とげ+ブロック(下向き)
						blockList.add(new NomalBlock(j, i));
						needleList.add(new Needle(j, i, 2));
						break;
					case L: // とげ+ブロック(左向き)
						blockList.add(new NomalBlock(j, i));
						needleList.add(new Needle(j, i, 3));
						break;	
					// 敵の配置	
					case e: // 敵
						enemyList.add(new Enemy(j, i));
						break;
					case w: // 歩く敵
						enemyList.add(new WalkEnemy(j, i));
						break;
					case G: // 幽霊の敵
						enemyList.add(new GhostEnemy(j, i));
						break;
					case m: // 動いてジャンプする敵
						enemyList.add(new MoveEnemy(j, i, 2));
						break;
					case s: // 弾を撃つ敵
						enemyList.add(new ShootEnemy(j, i));
						break;
					case J: // 静止してジャンプする敵
						enemyList.add(new MoveEnemy(j, i, 0));
						break;	
				}
			}
		}
		System.out.println("Stage loaded");
		currentClickableNum = clickableNum[stageNum-1];
		currentScrollable = scrollable[stageNum-1];
	}

	public boolean isScrollable(){
		return scrollable[currentStageNum];
	}

	public ArrayList<Block> getBlockList(){return blockList;}
	public ArrayList<Enemy> getEnemyList() {return enemyList;}
	public ArrayList<Needle> getNeedleList() {return needleList;}
	public GoalBlock getGoalBlock(){return goalBlock;}
	public int[] getClickableNum(){return currentClickableNum;}

	public boolean getScrollable() {return currentScrollable;}
}

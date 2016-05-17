package stages;

import static constants.ImageConstants.*;
import static constants.StageConstants.*;

import java.util.ArrayList;

import charas.Needle;
import charas.blocks.AbstractBlock;
import charas.blocks.ClearBlock;
import charas.blocks.FakeBlock;
import charas.blocks.GoalBlock;
import charas.blocks.HardBlock;
import charas.blocks.NomalBlock;
import charas.bosses.Boss1;
import charas.bosses.Boss2;
import charas.enemys.AbstractEnemy;
import charas.enemys.GhostEnemy;
import charas.enemys.MoveEnemy;
import charas.enemys.NomalEnemy;
import charas.enemys.ShootEnemy;
import charas.enemys.WalkEnemy;
import charas.signboards.AbstractSignboard;
import charas.signboards.SignBoard;
/**
 * ステージ情報を保持するクラスです
 * @author kitahara
 *
 */
public class Stage {

	private int currentStageNum; 		// 現在のステージ数
	private int currentClickableNum[];	// 現在のステージでのクリック可能回数
	private int clickableNum[][] = {{10,10,10},{15,15,15},{0,0,0},{30,0,0},{6,0,0},{20,5,0},{0,5,0},{16,0,0},{0,0,0},{6,0,0}}; //クリック可能回数
	private boolean scrollable[] = {true,true,false,true,false,true,true,true,true,false}; // 各ステージでのスクロール可能判定
	private boolean currentScrollable;	// 現在のステージのスクロール可能判定

	private ArrayList<AbstractBlock> blockList = new ArrayList<AbstractBlock>();
	private ArrayList<AbstractEnemy> enemyList = new ArrayList<AbstractEnemy>();
	private ArrayList<Needle> needleList = new ArrayList<Needle>();
	private ArrayList<AbstractSignboard> signboardList = new ArrayList<AbstractSignboard>();
	private GoalBlock goalBlock;

	private int[][] map;



	public Stage(){
	}

	public void setStage(int stageNum){
		blockList.clear();
		enemyList.clear();
		needleList.clear();
		signboardList.clear();
		map = StageMap.getStageMap(stageNum);

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				switch (map[i][j]) {
					// ブロックの配置
					case h:	//消せないブロック
						blockList.add(new HardBlock(j, i));
						break;
					case n: //右クリックで消せるブロック
						blockList.add(new NomalBlock(j, i));
						break;
					case c: //敵のみが触れられる透明ブロック
						blockList.add(new ClearBlock(j, i));
						break;
					case f: //プレイヤーが触れると消えるブロック
						blockList.add(new FakeBlock(j, i, IMAGE_BLOCK_HARD));
						break;
					case N: //プレイヤーが触れると消えるブロック
						blockList.add(new FakeBlock(j, i, IMAGE_BLOCK_NOMAL));
						break;	
					case g: // ゴール
						blockList.add(new GoalBlock(j, i));
						//goalBlock = new GoalBlock(j, i);
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
						enemyList.add(new NomalEnemy(j, i));
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
					case k1: // ボス
						enemyList.add(new Boss1(j, i));
						break;
					case k2: // ボス
						enemyList.add(new Boss2(j, i));
						break;
						
					case s1: // 看板
						signboardList.add(new SignBoard(j, i, IMAGE_SIGNBOARD_1));
						break;
					case s2: // 看板
						signboardList.add(new SignBoard(j, i, IMAGE_SIGNBOARD_2));
						break;
					case s3: // 看板
						signboardList.add(new SignBoard(j, i, IMAGE_SIGNBOARD_3));
						break;
					case s4: // 看板
						signboardList.add(new SignBoard(j, i, IMAGE_SIGNBOARD_4));
						break;	
					case s5: // 看板
						signboardList.add(new SignBoard(j, i, IMAGE_SIGNBOARD_5));
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

	public ArrayList<AbstractBlock> getBlockList(){return blockList;}
	public ArrayList<AbstractEnemy> getEnemyList() {return enemyList;}
	public ArrayList<Needle> getNeedleList() {return needleList;}
	public ArrayList<AbstractSignboard> getSignboardList() {return signboardList;}
	public GoalBlock getGoalBlock(){return goalBlock;}
	public int[] getClickableNum(){return currentClickableNum.clone();}

	public boolean getScrollable() {return currentScrollable;}
}

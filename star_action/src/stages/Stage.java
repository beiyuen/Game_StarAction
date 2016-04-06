package stages;

import static constants.StageConstants.*;

import java.util.ArrayList;

import charas.Block;
import charas.Enemy;
import charas.GoalBlock;
import charas.Needle;
public class Stage {

	public int currentStageNum; //現在のステージ数
	public int currentClickableNum[];
	//int clickableNum[][] = {{10,10,10},{5,0,0},{0,0,0},{0,5,0},{6,0,0}}; //クリック可能回数
	int clickableNum[] = {10,10,10}; //クリック可能回数
	boolean scrollable[] = {true,true,false,true,false};

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
					case h:	//ブロック
					case n:
						blockList.add(new Block(j, i));
						break;
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
					case e: // 敵
						enemyList.add(new Enemy(j, i));
						break;
					case g:
						goalBlock = new GoalBlock(j, i);
						break;
				}
			}
		}
		System.out.println("Stage loaded");
		currentClickableNum = clickableNum;
	}

	public boolean isScrollable(){
		return scrollable[currentStageNum];
	}

	public ArrayList<Block> getBlockList(){return blockList;}
	public ArrayList<Enemy> getEnemyList() {return enemyList;}
	public ArrayList<Needle> getNeedleList() {return needleList;}
	public GoalBlock getGoalBlock(){return goalBlock;}
}

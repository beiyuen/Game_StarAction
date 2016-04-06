package stages;

import static constants.CharaConstants.*;

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

	//各文字の定義
	final int h = BLOCK_HARD;
	final int c = BLOCK_CLEAR;
	final int g = GOAL;
	final int f = BLOCK_FAKE;
	final int n = BLOCK_NOMAL;

	final int e = ENEMY_SLIME;
	final int k = ENEMY_KING;
	final int G = ENEMY_GHOST;
	final int w = ENEMY_WALK;
	final int m = ENEMY_MOVE;
	final int j = ENEMY_JUMP;
	final int s = ENEMY_SHOT;

	final int u = NEEDLE_UP;
	final int r = NEEDLE_RIGHT;
	final int d = NEEDLE_DOWN;
	final int l = NEEDLE_LEFT;
	final int U = NEEDLE_BLOCK_UP;
	final int R = NEEDLE_BLOCK_RIGHT;
	final int L = NEEDLE_BLOCK_LEFT;
	final int D = NEEDLE_BLOCK_DOWN;

	 int[][] map = {
		//stage1
			{h,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,h,h,h},
			{h,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,h,h,h},
			{h,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,h,h,h},
			{h,0,0,0,0,0,0,0,c,0,0,0,e,0,0,0,0,c,0,0,0,0,0,0,0,0,0,0,0,0,h,h,h},
			{h,0,0,0,0,0,0,0,0,n,n,n,n,n,n,n,n,0,0,0,0,0,0,0,0,0,0,0,0,0,h,h,h},
			{h,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,G,0,0,0,0,0,0,h,h,h},
			{h,0,0,0,n,n,n,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,g,h,h,h},
			{h,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,h,h,h,h},
			{h,0,0,0,0,0,0,0,0,0,0,u,u,u,u,u,u,0,0,0,0,n,0,0,0,0,0,0,0,0,h,h,h},
			{h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h,h},

	 	};



	public Stage(){
		// enemy,block作成
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				switch (map[i][j]) {
					case h:	//ブロック
					case n:
						blockList.add(new Block(j, i));
						break;
					case u: // とげ
						needleList.add(new Needle(j, i, 0));
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

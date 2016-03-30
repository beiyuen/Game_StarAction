package star_action;

import java.util.ArrayList;

import charas.Block;
import charas.Enemy;
import charas.Needle;

public class Model {
	public static ArrayList<Block> blockList;
	public static ArrayList<Enemy> enemyList;
	public static ArrayList<Needle> needleList;
	public static int gameStatus;

	public static ArrayList<Block> getBlockList() {
		return blockList;
	}
	public static ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
	public static ArrayList<Needle> getNeedleList() {
		return needleList;
	}


	public void death(){
		setGameStatus(2);
	}

	private void setGameStatus(int i){
		gameStatus = i;
	}


}

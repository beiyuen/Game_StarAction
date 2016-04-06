package star_action;

import static constants.MathConstants.*;

import java.util.ArrayList;
import java.util.Iterator;

import charas.AbstractChara;
import charas.Block;
import charas.Enemy;
import charas.GoalBlock;
import charas.Needle;
import charas.PlayerChara;
import charas.enemys.GhostEnemy;
import slide.StageChangeSlide;
import stages.Stage;
import util.ClickItem;
import util.DebugShowText;

public class Model {
	public static ArrayList<Block> blockList = null;
	public static ArrayList<Enemy> enemyList = null;
	public static ArrayList<Needle> needleList = null;
	public static ArrayList<Block> placeBlockList = new ArrayList<Block>();
	public static ArrayList<Enemy> placeEnemyList = new ArrayList<Enemy>();
	public static int gameStatus = GAMESTATUS_STAGECHANGE;
	public static GoalBlock goalBlock = null;

	public static int stageNum = 1;
	public static Stage stage = new Stage();
	public static PlayerChara playerChara = new PlayerChara(40, 50);
	public static DebugShowText debugShowText = new DebugShowText();

	public static StageChangeSlide stageChangeSlide = new StageChangeSlide();

	public static ArrayList<Block> getBlockList() {return blockList;}
	public static ArrayList<Enemy> getEnemyList() {return enemyList;}
	public static ArrayList<Needle> getNeedleList() {return needleList;}
	public static int getGameStatus() {	return gameStatus;}
	public static int getStageNum() {	return stageNum;}
	public static PlayerChara getPlayerChara() {	return playerChara;}
	public static StageChangeSlide getStageChangeSlide() {	return stageChangeSlide;}
	public static GoalBlock getGoalBlock() {return goalBlock;}

	public static int[] clickableNum = null;
	public static boolean scrollable = true;
	public static int placementMode = 0;
	public static ClickItem clickItem;

	public static void setStageNum(int i){
		stageNum = i;
	}

	/**
	 *  プレイヤーがやられた時のゲーム全体の処理
	 */
	public static void death(){
		setGameStatus(GAMESTATUS_DIE);
		playerChara.death();
	}

	public static void setGameStatus(int i){
		gameStatus = i;
	}

	/**
	 * プレイヤーの情報とステージの情報を初期化
	 */
	public static void init(){
		playerChara.init();
		for (Block b : blockList){
			b.init();
		}
		for (AbstractChara e : enemyList) {
			e.init();
		}
		for (Needle n : needleList){
			n.init();
		}
		placeBlockList.clear();
		placeEnemyList.clear();
		goalBlock.init();
		setGameStatus(GAMESTATUS_PLAYING);
		clickableNum = stage.getClickableNum();
		scrollable = stage.getScrollable();
	}

	/**
	 * プレイヤーのx座標が一定以上になったときに右へ移動した場合に画面をスクロール
	 */
	private static void scroll(){
		if(scrollable){//s.num=4(ボス戦)ではスクロールできない
			double xSpeed =  playerChara.getxSpeed();
			double xPosition = playerChara.getxPosition();
			double speed = playerChara.getxSpeed();
			if (xPosition + xSpeed + playerChara.getWidth() / 2 > GAME_WIDTH - 400) {
				playerChara.scroll(speed);
				for (Block b : blockList){
					b.scroll(speed);
				}

				for (Enemy e : enemyList) {
					e.scroll(speed);
				}

				for (Needle n : needleList){
					n.scroll(speed);
				}
				for (Block b : placeBlockList){
					b.scroll(speed);
				}
				for (Enemy e : placeEnemyList) {
					e.scroll(speed);
				}
				goalBlock.scroll(speed);
			}
		}
	}


	/**
	 * 1フレームごとのゲーム全体の処理
	 */
	public static void run(){
		if(!playerChara.isDeath()){
			playerChara.calcAcceleration();
		}

		for (Enemy e : enemyList) {
			if(!e.isDeath()){
				e.calcAcceleration();
				e.move();
			}
		}
		if(!playerChara.isDeath()){
			playerChara.move();
		}
		scroll();

		debugShowText.run(playerChara.xPosition, playerChara.yPosition);
	}
	/**
	 * 次のステージに行くための処理
	 */
	public static void nextStage() {
		// TODO 自動生成されたメソッド・スタブ
		setStageNum(stageNum+1);
		stageChangeSlide.setText(stageNum);
		setGameStatus(GAMESTATUS_STAGECHANGE);
		setStage(stageNum);
		playerChara.init();
	}
	/**
	 * 新しいステージをセット。これにより敵やブロックの位置情報を更新
	 * @param i
	 */
	public static void setStage(int i){
		stage.setStage(i);
		blockList = stage.getBlockList();
		enemyList = stage.getEnemyList();
		needleList = stage.getNeedleList();
		goalBlock = stage.getGoalBlock();
		clickableNum = stage.getClickableNum();
		scrollable = stage.getScrollable();
		clickItem = new ClickItem();
		clickItem.setSize();
	}

	public static int[] getClickableNum(){return clickableNum;}

	public static int getplacementMode(){return placementMode;}
	public static void setplacementMode(int i){
		placementMode = i%3;
		clickItem.setText(clickableNum[placementMode]);
		clickItem.setImageKind(placementMode);
	}
	public static ArrayList<Block> getPlaceBlockList() {
		return placeBlockList;
	}
	public static ArrayList<Enemy> getPlaceEnemyList() {
		return placeEnemyList;
	}
	public static boolean isScrollable() {
		return scrollable;
	}
	public static ClickItem getClickItem() {
		return clickItem;
	}
	/**
	 * 左クリックしたときにブロックや敵を配置する
	 * @param x
	 * @param y
	 */
	public static void placement(int x, int y){
		switch (placementMode) {
		case 0:
			placeBlockList.add(new Block(x,y));
			break;
		case 1:
			placeEnemyList.add(new Enemy(x,y));
			break;
		case 2:
			placeEnemyList.add(new GhostEnemy(x,y));
			break;
		}
		clickableNum[placementMode] -= 1;
		clickItem.setText(clickableNum[placementMode]);
	}

	public static void removeBlock(int x, int y){
		Iterator<Block> blockIterator = blockList.iterator();
		while(blockIterator.hasNext()){
			Block val = blockIterator.next();
			if(val.removable && Math.abs(x - val.xPosition) < 25&& Math.abs(y - val.yPosition) < 25){
				blockIterator.remove(); // イテレータが指す要素を削除
			}
		}
	}
}

package star_action;

import static constants.CharaConstants.*;
import static constants.MathConstants.*;

import java.util.ArrayList;
import java.util.Iterator;

import charas.AbstractChara;
import charas.Needle;
import charas.PlayerChara;
import charas.blocks.AbstractBlock;
import charas.blocks.HardBlock;
import charas.blocks.WorldClearBlock;
import charas.enemys.AbstractEnemy;
import charas.enemys.GhostEnemy;
import charas.enemys.NomalEnemy;
import charas.signboards.AbstractSignboard;
import slide.EndingSlide;
import slide.GameoverSlide;
import slide.OpeningSlide;
import slide.StageChangeSlide;
import slide.WorldClearSlide;
import stages.Stage;
import util.ClickItem;
import util.DebugShowText;

public class Model {
	public static ArrayList<AbstractBlock> blockList = null;
	public static ArrayList<AbstractEnemy> enemyList = null;
	public static ArrayList<Needle> needleList = null;
	public static ArrayList<AbstractBlock> placeBlockList = new ArrayList<AbstractBlock>();
	public static ArrayList<AbstractEnemy> placeEnemyList = new ArrayList<AbstractEnemy>();
	public static ArrayList<AbstractSignboard> signboardList = new ArrayList<AbstractSignboard>();

	public static int gameStatus = GAMESTATUS_OPENING;

	public static int stageNum = 5;
	public static int worldNum = 1;
	public static Stage stage = new Stage();
	public static PlayerChara playerChara = new PlayerChara(40, 50);
	public static DebugShowText debugShowText = new DebugShowText();

	public static StageChangeSlide stageChangeSlide = new StageChangeSlide();
	public static WorldClearSlide worldClearSlide = new WorldClearSlide(worldNum-1);
	public static OpeningSlide openingSlide = new OpeningSlide();
	public static EndingSlide endingSlide = new EndingSlide();
	public static GameoverSlide gameoverSlide = new GameoverSlide();

	public static ArrayList<AbstractBlock> getBlockList() {return blockList;}
	public static ArrayList<AbstractEnemy> getEnemyList() {return enemyList;}
	public static ArrayList<Needle> getNeedleList() {return needleList;}
	public static int getGameStatus() {	return gameStatus;}
	public static int getStageNum() {	return stageNum;}
	public static PlayerChara getPlayerChara() {	return playerChara;}
	public static ArrayList<AbstractSignboard> getSignboardList() {
		return signboardList;
	}
	public static StageChangeSlide getStageChangeSlide() {	return stageChangeSlide;}

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

	public static void gameInit(){
		
	}
	
	
	/**
	 * プレイヤーの情報とステージの情報を初期化
	 */
	public static void init(){
		playerChara.init();
		for(AbstractSignboard s: signboardList){
			s.init();
		}
		Iterator<AbstractBlock> blockIterator = blockList.iterator();
		while (blockIterator.hasNext()) {
			AbstractBlock b = blockIterator.next();
			if(b instanceof WorldClearBlock){
				blockIterator.remove();
				break;
			}
			b.init();
		}
		/*for (AbstractBlock b : ){
			b.init();
		}*/
		for (AbstractChara e : enemyList) {
			e.init();
		}
		for (Needle n : needleList){
			n.init();
		}
		placeBlockList.clear();
		placeEnemyList.clear();
		//goalBlock.init();
		setGameStatus(GAMESTATUS_PLAYING);
		clickableNum = stage.getClickableNum();
		scrollable = stage.getScrollable();
		placementMode = 0;
		clickItem.setImageKind(placementMode);
		clickItem.setText(clickableNum[placementMode]);
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
				for(AbstractSignboard s: signboardList){
					s.scroll(speed);
				}
				
				for (AbstractBlock b : blockList){
					b.scroll(speed);
				}

				for (AbstractEnemy e : enemyList) {
					e.scroll(speed);
				}

				for (Needle n : needleList){
					n.scroll(speed);
				}
				for (AbstractBlock b : placeBlockList){
					b.scroll(speed);
				}
				for (AbstractEnemy e : placeEnemyList) {
					e.scroll(speed);
				}
			}
		}
	}


	/**
	 * 1フレームごとのゲーム全体の処理、各キャラの速度変更、プレイヤーと敵キャラとの当たり判定の実行、各キャラの移動を行う
	 */
	public static void run(){
		if(!playerChara.isDeath()){
			playerChara.calcAcceleration();
		}

		for (AbstractEnemy e : enemyList) {
			if(!e.isDeath()){
				e.calcAcceleration();
				e.move();
			}
		}
		for (AbstractEnemy e : placeEnemyList){
			if(!e.isDeath()){
				e.calcAcceleration();
				e.move();
			}
		}
		if(!playerChara.isDeath()){
			playerChara.move();
		}
		scroll();
		if(Model.getGameStatus() == GAMESTATUS_WORLDCHANGE){
			worldClearSlide.calcAnimation();
			worldClearSlide.move();
		}

		debugShowText.run(playerChara.xPosition, playerChara.yPosition);
	}
	/**
	 * 次のステージに行くための処理
	 */
	public static void nextStage() {
		setStageNum(stageNum+1);
		stageChangeSlide.setText(stageNum);
		setGameStatus(GAMESTATUS_STAGECHANGE);
		setStage(stageNum);
		playerChara.init();
	}
	/**
	 * 次のワールドに行くための処理
	 */
	public static void nextWorld(){
		Iterator<AbstractBlock> blockIterator = blockList.iterator();
		while (blockIterator.hasNext()) {
			AbstractBlock b = blockIterator.next();
			if(b instanceof WorldClearBlock){
				blockIterator.remove();
				break;
			}
			b.init();
		}
		worldClearSlide.init();
		worldNum++;
		setGameStatus(GAMESTATUS_WORLDCHANGE);
		if(stageNum == 10){
			gameClear();
		//	setStage(stageNum);
		}
	}
	
	public static void gameClear(){
		setGameStatus(GAMESTATUS_ENDING);
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
		signboardList = stage.getSignboardList();
		clickableNum = stage.getClickableNum();
		scrollable = stage.getScrollable();
		clickItem = new ClickItem();
		placeBlockList.clear();
		placeEnemyList.clear();
		placementMode = 0;
		clickItem.setText(clickableNum[placementMode]);
	}

	public static int[] getClickableNum(){return clickableNum;}

	public static int getplacementMode(){return placementMode;}
	public static void setplacementMode(int i){
		placementMode = i%3;
		clickItem.setText(clickableNum[placementMode]);
		clickItem.setImageKind(placementMode);
	}
	public static ArrayList<AbstractBlock> getPlaceBlockList() {
		return placeBlockList;
	}
	public static ArrayList<AbstractEnemy> getPlaceEnemyList() {
		return placeEnemyList;
	}
	public static WorldClearSlide getWorldClearSlide() {
		return worldClearSlide;
	}
	public static OpeningSlide getOpeningSlide() {
		return openingSlide;
	}
	public static EndingSlide getEndingSlide() {
		return endingSlide;
	}
	public static GameoverSlide getGameoverSlide() {
		return gameoverSlide;
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
		case PLACEMENT_BLOCK:
			double px = x *  BLOCK_SIZE + BLOCK_SIZE / 2;
			double py = y *  BLOCK_SIZE + BLOCK_SIZE / 2;
			for (AbstractEnemy e : enemyList) {
				if(e.isHitPoint(px, py)){
					return;
				}
			}
			for (AbstractEnemy e : placeEnemyList) {
				if(e.isHitPoint(px, py)){
					return;
				}
			}
			placeBlockList.add(new HardBlock(x,y));
			break;
		case PLACEMENT_SLIME:
			placeEnemyList.add(new NomalEnemy(x,y));
			break;
		case PLACEMENT_GHOST:
			placeEnemyList.add(new GhostEnemy(x,y));
			break;
		}
		clickableNum[placementMode] -= 1;
		clickItem.setText(clickableNum[placementMode]);
	}

	/**
	 * 引数の位置に存在するブロックを削除する。具体的にはdeath変数をtrueにしているだけなので、リストから削除されているわけではない
	 * @param x
	 * @param y
	 */
	public static void removeBlock(int x, int y){
		for (AbstractBlock b : blockList) {
			if(b.isRemovable() && Math.abs(x - b.getxPosition()) < 25 && Math.abs(y - b.getyPosition()) < 25){
				b.setDeath(true);
			}
		}
	}
	
}

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
import enums.GameStatus;
import slide.EndingSlide;
import slide.GameoverSlide;
import slide.OpeningSlide;
import slide.StageChangeSlide;
import slide.WorldClearSlide;
import stages.Stage;
import util.ClickItem;
import util.DebugShowText;
/**
 * ゲーム内のすべてのデータを保持しているクラスです
 *
 * @author kitahara
 *
 */
public class Model {
	public static ArrayList<AbstractBlock> blockList = null;
	public static ArrayList<AbstractEnemy> enemyList = null;
	public static ArrayList<Needle> needleList = null;
	public static ArrayList<AbstractBlock> placeBlockList = new ArrayList<AbstractBlock>();
	public static ArrayList<AbstractEnemy> placeEnemyList = new ArrayList<AbstractEnemy>();
	public static ArrayList<AbstractSignboard> signboardList = new ArrayList<AbstractSignboard>();

	public static GameStatus gameStatus = GameStatus.Opening;

	public static int stageNum = 1;
	public static int worldNum = 1;
	public static Stage stage = new Stage();
	public static PlayerChara playerChara = null;
	public static DebugShowText debugShowText = null;

	public static StageChangeSlide stageChangeSlide = null;
	public static WorldClearSlide worldClearSlide = null;
	public static OpeningSlide openingSlide = null;
	public static EndingSlide endingSlide = null;
	public static GameoverSlide gameoverSlide = null;

	public static int[] clickableNum = null;
	public static boolean scrollable = true;
	public static int placementMode = 0;
	public static ClickItem clickItem = null;


	/**
	 * プレイヤーがやられた時のゲーム全体の処理
	 */
	public static void death() {
		setGameStatus(GameStatus.Die);
		playerChara.death();
	}

	/**
	 * ゲーム起動時に行われる処理(リソースを利用するインスタンスの初期化)
	 */
	public static void firstInit(){
		stageChangeSlide = new StageChangeSlide();
		worldClearSlide = new WorldClearSlide(worldNum - 1);
		openingSlide = new OpeningSlide();
		endingSlide = new EndingSlide();
		gameoverSlide = new GameoverSlide();
		playerChara = new PlayerChara(40, 50);
		debugShowText = new DebugShowText();
		clickItem = new ClickItem();
	}

	/**
	 * 全ステージをクリアしたときに呼ばれるメソッド。gameStatusを変更する
	 */
	public static void gameClear() {
		setGameStatus(GameStatus.Ending);
	}

	/**
	 * オープニング画面からゲーム画面へ遷移するときに呼ばれる。ワールド、ステージの値を初期化する
	 */
	public static void gameInit() {
		setStageNum(1);
		setWorldNum(1);
		setStage();
		stageChangeSlide.setText(worldNum, stageNum);
	}

	public static ArrayList<AbstractBlock> getBlockList() {
		return blockList;
	}

	public static int[] getClickableNum() {
		return clickableNum;
	}

	public static ClickItem getClickItem() {
		return clickItem;
	}

	public static EndingSlide getEndingSlide() {
		return endingSlide;
	}

	public static ArrayList<AbstractEnemy> getEnemyList() {
		return enemyList;
	}

	public static GameoverSlide getGameoverSlide() {
		return gameoverSlide;
	}

	public static GameStatus getGameStatus() {
		return gameStatus;
	}

	public static ArrayList<Needle> getNeedleList() {
		return needleList;
	}

	public static OpeningSlide getOpeningSlide() {
		return openingSlide;
	}

	public static ArrayList<AbstractBlock> getPlaceBlockList() {
		return placeBlockList;
	}

	public static ArrayList<AbstractEnemy> getPlaceEnemyList() {
		return placeEnemyList;
	}

	public static int getplacementMode() {
		return placementMode;
	}

	public static PlayerChara getPlayerChara() {
		return playerChara;
	}

	public static ArrayList<AbstractSignboard> getSignboardList() {
		return signboardList;
	}

	public static StageChangeSlide getStageChangeSlide() {
		return stageChangeSlide;
	}
	public static int getStageNum() {
		return stageNum;
	}

	public static WorldClearSlide getWorldClearSlide() {
		return worldClearSlide;
	}

	/**
	 * プレイヤーの情報とステージの情報を初期化
	 */
	public static void init() {
		playerChara.init();
		for (AbstractSignboard s : signboardList) {
			s.init();
		}
		Iterator<AbstractBlock> blockIterator = blockList.iterator();
		while (blockIterator.hasNext()) {
			AbstractBlock b = blockIterator.next();
			if (b instanceof WorldClearBlock) {
				blockIterator.remove();
				break;
			}
			b.init();
		}
		for (AbstractChara e : enemyList) {
			e.init();
		}
		for (Needle n : needleList) {
			n.init();
		}
		placeBlockList.clear();
		placeEnemyList.clear();
		setGameStatus(GameStatus.Playing);
		clickableNum = stage.getClickableNum();
		scrollable = stage.getScrollable();
		placementMode = 0;
		clickItem.setImageKind(placementMode);
		clickItem.setText(clickableNum[placementMode]);
	}

	public static boolean isScrollable() {
		return scrollable;
	}

	/**
	 * 次のステージに行くための処理
	 */
	public static void nextStage() {
		setStageNum(stageNum + 1);
		stageChangeSlide.setText(worldNum, stageNum);
		setGameStatus(GameStatus.StageChange);
		setStage();
	}

	/**
	 * 次のワールドに行くための処理
	 */
	public static void nextWorld() {
		Iterator<AbstractBlock> blockIterator = blockList.iterator();
		while (blockIterator.hasNext()) {
			AbstractBlock b = blockIterator.next();
			if (b instanceof WorldClearBlock) {
				blockIterator.remove();
				break;
			}
			b.init();
		}
		if (((worldNum - 1) * 5 + stageNum) == 10) {
			gameClear();
			return;
		}
		worldClearSlide.init();
		worldNum++;
		setStageNum(0);
		setGameStatus(GameStatus.WorldChange);

	}

	/**
	 * 左クリックしたときにブロックや敵を配置する
	 *
	 * @param x : 配置位置のx座標(×ブロックサイズ)
	 * @param y : 配置位置のy座標(×ブロックサイズ)
	 */
	public static void placement(int x, int y) {
		switch (placementMode) {
		case PLACEMENT_BLOCK:
			double px = x * BLOCK_SIZE + BLOCK_SIZE / 2;
			double py = y * BLOCK_SIZE + BLOCK_SIZE / 2;
			for (AbstractEnemy e : enemyList) {
				if (e.isHitPoint(px, py)) {
					return;
				}
			}
			for (AbstractEnemy e : placeEnemyList) {
				if (e.isHitPoint(px, py)) {
					return;
				}
			}
			placeBlockList.add(new HardBlock(x, y));
			break;
		case PLACEMENT_SLIME:
			placeEnemyList.add(new NomalEnemy(x, y));
			break;
		case PLACEMENT_GHOST:
			placeEnemyList.add(new GhostEnemy(x, y));
			break;
		}
		clickableNum[placementMode] -= 1;
		clickItem.setText(clickableNum[placementMode]);
	}

	/**
	 * 引数の位置に存在するブロックを削除する。具体的にはdeath変数をtrueにしているだけなので、リストから削除されているわけではない
	 *
	 * @param x : 削除位置のx座標(×ブロックサイズ)
	 * @param y : 配置位置のy座標(×ブロックサイズ)
	 */
	public static void removeBlock(int x, int y) {
		for (AbstractBlock b : blockList) {
			if (b.isRemovable() && Math.abs(x - b.getxPosition()) < 25 && Math.abs(y - b.getyPosition()) < 25) {
				b.setDeath(true);
			}
		}
	}

	/**
	 * 1フレームごとのゲーム全体の処理、各キャラの速度変更、プレイヤーと敵キャラとの当たり判定の実行、各キャラの移動を行う
	 */
	public static void run() {
		if (!playerChara.isDeath()) {
			playerChara.calcAcceleration();
		}

		for (AbstractEnemy e : enemyList) {
			if (!e.isDeath()) {
				e.calcAcceleration();
				e.move();
			}
		}
		for (AbstractEnemy e : placeEnemyList) {
			if (!e.isDeath()) {
				e.calcAcceleration();
				e.move();
			}
		}
		if (!playerChara.isDeath()) {
			playerChara.move();
		}
		scroll();
		if (Model.getGameStatus() == GameStatus.WorldChange) {
			worldClearSlide.calcAnimation();
			worldClearSlide.move();
		}

		//debugShowText.run(playerChara.xPosition, playerChara.yPosition);
	}

	/**
	 * プレイヤーのx座標が一定以上になったときに右へ移動した場合に画面をスクロール。反対方向へはスクロールしない
	 */
	private static void scroll() {
		if (scrollable) {
			double xSpeed = playerChara.getxSpeed();
			double xPosition = playerChara.getxPosition();
			double speed = playerChara.getxSpeed();
			if (xPosition + xSpeed + playerChara.getWidth() / 2 > GAME_WIDTH - 400) {
				playerChara.scroll(speed);
				for (AbstractSignboard s : signboardList) {
					s.scroll(speed);
				}

				for (AbstractBlock b : blockList) {
					b.scroll(speed);
				}

				for (AbstractEnemy e : enemyList) {
					e.scroll(speed);
				}

				for (Needle n : needleList) {
					n.scroll(speed);
				}
				for (AbstractBlock b : placeBlockList) {
					b.scroll(speed);
				}
				for (AbstractEnemy e : placeEnemyList) {
					e.scroll(speed);
				}
			}
		}
	}

	public static void setGameStatus(GameStatus status) {
		gameStatus = status;
	}

	public static void setplacementMode(int i) {
		placementMode = i % 3;
		clickItem.setText(clickableNum[placementMode]);
		clickItem.setImageKind(placementMode);
	}

	/**
	 * 新しいステージをセット。これにより敵やブロックの位置情報を更新
	 *
	 */
	public static void setStage() {
		stage.setStage((worldNum-1) * 5 + stageNum);
		blockList = stage.getBlockList();
		enemyList = stage.getEnemyList();
		needleList = stage.getNeedleList();
		signboardList = stage.getSignboardList();
		playerChara.init();
		clickableNum = stage.getClickableNum();
		scrollable = stage.getScrollable();
		placementMode = 0;
		clickItem.init();
		placeBlockList.clear();
		placeEnemyList.clear();
		clickItem.setText(clickableNum[placementMode]);
	}

	public static void setStageNum(int i) {
		stageNum = i;
	}

	public static void setWorldNum(int worldNum) {
		Model.worldNum = worldNum;
	}

}

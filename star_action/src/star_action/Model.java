package star_action;

import static constants.MathConstants.*;

import java.awt.Graphics;
import java.util.ArrayList;

import charas.AbstractChara;
import charas.Block;
import charas.Enemy;
import charas.Needle;
import charas.PlayerChara;
import stages.Stage;
import util.DebugShowText;

public class Model {
	public static ArrayList<Block> blockList = new ArrayList<Block>();
	public static ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	public static ArrayList<Needle> needleList = new ArrayList<Needle>();
	public static int gameStatus = GAMESTATUS_PLAYING;
	
	public static Stage stage = new Stage(0, blockList, enemyList, needleList);
	public static PlayerChara playerChara = new PlayerChara(40, 50);
	public static DebugShowText debugShowText = new DebugShowText();
	

	public static ArrayList<Block> getBlockList() {
		return blockList;
	}
	public static ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
	public static ArrayList<Needle> getNeedleList() {
		return needleList;
	}

	public static int getGameStatus() {
		return gameStatus;
	}

	public static void death(){
		setGameStatus(GAMESTATUS_DIE);
		playerChara.death();
	}

	private static void setGameStatus(int i){
		gameStatus = i;
	}

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
		setGameStatus(GAMESTATUS_PLAYING);
	}
	
	private static void scroll(){
		if(stage.isScrollable()){//s.num=4(ボス戦)ではスクロールできない
			double xSpeed =  playerChara.getxSpeed();
			double xPosition = playerChara.getxPosition();
			if (xPosition + xSpeed + playerChara.getWidth() / 2 > GAME_WIDTH - 400) {
				playerChara.xPosition -= xSpeed;
				for (Block b : blockList){
					b.xPosition -= xSpeed;
				}
					
				for (Enemy e : enemyList) {
					if(!e.isDeath()){
						e.xPosition -= xSpeed;
					}
				//	if (e instanceof NPCshoot)
				//		for (Shot s : NPCshoot.bullet)
				//			s.xpos -= xSpeed;
				}
				for (Needle n : needleList){
					n.xPosition -= xSpeed;
				}
			}
		}
	}
	
	
	
	public static void run(){
		playerChara.calcAcceleration();
		for (Enemy e : enemyList) {
			if(!e.isDeath()){
				e.calcAcceleration();
				e.move();
			}
		}
		playerChara.move();
		scroll();
		
		debugShowText.run(playerChara.xPosition, playerChara.yPosition);
	}
	
	public static void draw(Graphics g){
	}

}

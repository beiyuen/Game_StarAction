package star_action;

import static constants.MathConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

import charas.Block;
import charas.Enemy;
import charas.Needle;
import charas.PlayerChara;
import util.DebugShowText;

public class ViewPanel extends JPanel {
	public Timer timer;
	public ArrayList<Block> blockList;
	public ArrayList<Enemy> enemyList;
	public ArrayList<Needle> needleList;
	Image image = getToolkit().createImage("image/block.png");
	
	public PlayerChara playerChara = new PlayerChara(40, 50);

	public DebugShowText debugShowText;

	public ViewPanel(){
		//super();
		debugShowText = Model.debugShowText;
		blockList = Model.getBlockList();
		enemyList = Model.getEnemyList();
		needleList = Model.getNeedleList();
		playerChara = Model.playerChara;
		setBackground(Color.GREEN);
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		timer = new Timer(DELAY, e -> {
				switch(Model.getGameStatus()){
					case GAMESTATUS_OPENING:
						break;
					case GAMESTATUS_PLAYING:
						Model.run();
						repaint();
						//System.out.println("timer : run");
						break;
					case GAMESTATUS_DIE:
						break;
					case GAMESTATUS_ENDING:
						break;
					case GAMESTATUS_STAGECHANGE:
						break;
				}
				repaint();
			}
		);
		run();
		setOpaque(false);
	}

	public void run(){
		timer.start();
	}

	// 描画
		public void paintComponent(Graphics g) {
			switch(Model.getGameStatus()){
			case GAMESTATUS_OPENING:
				//open.draw(g);
				break;
				
			case GAMESTATUS_PLAYING:	//各キャラ、ブロック、右上の画像を描画
				playerChara.draw(g);// draw関数が悪い?
				for (Block block : blockList) {
					block.draw(g);
				}
				for (Enemy enemy : enemyList) {
					enemy.draw(g);
				}
				for (Needle needle : needleList) {
					needle.draw(g);
				}
				debugShowText.draw(g);
				break;

			case GAMESTATUS_DIE:
				break;

			case GAMESTATUS_ENDING:
				break;
				
			case GAMESTATUS_STAGECHANGE:
				break;	
			}
		}

}

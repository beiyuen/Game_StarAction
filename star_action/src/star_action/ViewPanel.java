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
	//Image image = getToolkit().createImage("image/block.png");

	public Image gameoverImage =  getToolkit().createImage("image/Gameover.png");

	public PlayerChara playerChara = new PlayerChara(40, 50);

	public DebugShowText debugShowText;

	public ViewPanel(){
		//super();
		debugShowText = Model.debugShowText;
	//	blockList = Model.getBlockList();
	//	enemyList = Model.getEnemyList();
	//	needleList = Model.getNeedleList();
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
						Model.run();
						repaint();
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
				for (Block b : Model.getBlockList()) {
					b.draw(g);
				}
				for (Enemy e : Model.getEnemyList()) {
					if(!e.isDeath()){
						e.draw(g);
					}

				}
				for (Needle n : Model.getNeedleList()) {
					n.draw(g);
				}
				Model.getGoalBlock().draw(g);
				debugShowText.draw(g);
				break;

			case GAMESTATUS_DIE:
				for (Block b : Model.getBlockList()) {
					b.draw(g);
				}
				for (Enemy e : Model.getEnemyList()) {
					if(!e.isDeath()){
						e.draw(g);
					}
				}
				for (Needle n : Model.getNeedleList()) {
					n.draw(g);
				}
				Model.getGoalBlock().draw(g);
				debugShowText.draw(g);
				g.drawImage(gameoverImage, 0,0,1000,500, this);
				break;

			case GAMESTATUS_ENDING:
				break;

			case GAMESTATUS_STAGECHANGE:
				Model.getStageChangeSlide().draw(g);
				break;
			}
		}

}

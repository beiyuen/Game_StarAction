package star_action;

import static constants.MathConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import charas.Needle;
import charas.PlayerChara;
import charas.blocks.AbstractBlock;
import charas.enemys.AbstractEnemy;
import util.DebugShowText;

public class ViewPanel extends JPanel {
	private static ViewPanel viewPanel = null;
	
	public Timer timer;
	private Graphics offScreen;
	private BufferedImage offImage;
	Color blue = new Color(150, 250, 255);

	public Image gameoverImage =  getToolkit().createImage("image/Gameover.png");

	public PlayerChara playerChara = new PlayerChara(40, 50);

	public DebugShowText debugShowText;
	
	private ViewPanel(){
		offImage = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_BGR);
		debugShowText = Model.debugShowText;
		playerChara = Model.playerChara;
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
			offScreen =  offImage.getGraphics();
			switch(Model.getGameStatus()){
			case GAMESTATUS_OPENING:
				//open.draw(g);
				break;

			case GAMESTATUS_PLAYING:	//各キャラ、ブロック、右上の画像を描画
				drawSky(offScreen);
				playerChara.draw(offScreen);// draw関数が悪い?
				for (AbstractBlock b : Model.getBlockList()) {
					b.draw(offScreen);
				}
				for (AbstractEnemy e : Model.getEnemyList()) {
					if(!e.isDeath()){
						e.draw(offScreen);
					}
				}
				for (Needle n : Model.getNeedleList()) {
					if(!n.isDeath()){
						n.draw(offScreen);
					}
				}
				for (AbstractBlock b : Model.getPlaceBlockList()) {
					b.draw(offScreen);
				}
				for (AbstractEnemy e : Model.getPlaceEnemyList()) {
					if(!e.isDeath()){
						e.draw(offScreen);
					}
				}
			//	Model.getGoalBlock().draw(offScreen);
				Model.getClickItem().draw(offScreen);
				debugShowText.draw(offScreen);
				break;

			case GAMESTATUS_DIE:
				drawSky(offScreen);
				for (AbstractBlock b : Model.getBlockList()) {
					b.draw(offScreen);
				}
				for (AbstractEnemy e : Model.getEnemyList()) {
					if(!e.isDeath()){
						e.draw(offScreen);
					}
				}
				for (Needle n : Model.getNeedleList()) {
					if(!n.isDeath()){
						n.draw(offScreen);
					}
				}
				for (AbstractBlock b : Model.getPlaceBlockList()) {
					b.draw(offScreen);
				}
				for (AbstractEnemy e : Model.getPlaceEnemyList()) {
					if(!e.isDeath()){
						e.draw(offScreen);
					}
				}
			//	Model.getGoalBlock().draw(offScreen);
				Model.getClickItem().draw(offScreen);
				debugShowText.draw(offScreen);
				offScreen.drawImage(gameoverImage, 0,0,1000,500, this);
				break;

			case GAMESTATUS_ENDING:
				break;

			case GAMESTATUS_STAGECHANGE:
				Model.getStageChangeSlide().draw(offScreen);
				break;
			}
			g.drawImage(offImage,0,0,this);
		}

	public void drawSky(Graphics g){
		g.setColor(blue);
		g.fillRect(0, 0, GAME_WIDTH, GAME_WIDTH);
	}

	public static ViewPanel getViewPanel() {
		if(viewPanel == null){
			viewPanel = new ViewPanel();
		}
		return viewPanel;
	}
	
	

}

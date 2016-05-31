package star_action;

import static constants.MathConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import charas.Needle;
import charas.blocks.AbstractBlock;
import charas.enemys.AbstractEnemy;
import charas.signboards.AbstractSignboard;
import util.DebugShowText;
/**
 * 画面への描画を行うパネルです
 * 
 * @author kitahara
 *
 */
public class ViewPanel extends JPanel {
	private static ViewPanel viewPanel = null;

	public Timer timer;
	private Graphics offScreen;
	private BufferedImage offImage;
	Color blue = new Color(150, 250, 255);

	public DebugShowText debugShowText;

	private ViewPanel() {
		offImage = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_BGR);
		timer = new Timer(DELAY, e -> {
			switch (Model.getGameStatus()) {
			case GAMESTATUS_OPENING:
				repaint();
				break;
			case GAMESTATUS_PLAYING:
				Model.run();
				repaint();
				break;
			case GAMESTATUS_DIE:
				Model.run();
				repaint();
				break;
			case GAMESTATUS_ENDING:
				break;
			case GAMESTATUS_STAGECHANGE:
				break;
			case GAMESTATUS_WORLDCHANGE:
				Model.run();
				repaint();
				break;
			}
			repaint();
		});
		run();
		setOpaque(false);
	}

	public void run() {
		timer.start();
	}

	// 描画
	public void paintComponent(Graphics g) {
		offScreen = offImage.getGraphics();
		switch (Model.getGameStatus()) {
		case GAMESTATUS_OPENING:
			Model.getOpeningSlide().draw(offScreen);
			break;

		case GAMESTATUS_PLAYING: // 各キャラ、ブロック、右上の画像を描画
			drawSky(offScreen);
			for (AbstractSignboard s : Model.getSignboardList()) {
				s.draw(offScreen);
			}
			Model.getPlayerChara().draw(offScreen);// draw関数が悪い?
			for (AbstractBlock b : Model.getBlockList()) {
				b.draw(offScreen);
			}
			for (AbstractEnemy e : Model.getEnemyList()) {
				if (!e.isDeath()) {
					e.draw(offScreen);
				}
			}
			for (Needle n : Model.getNeedleList()) {
				if (!n.isDeath()) {
					n.draw(offScreen);
				}
			}
			for (AbstractBlock b : Model.getPlaceBlockList()) {
				b.draw(offScreen);
			}
			for (AbstractEnemy e : Model.getPlaceEnemyList()) {
				if (!e.isDeath()) {
					e.draw(offScreen);
				}
			}
			Model.getClickItem().draw(offScreen);
			//debugShowText.draw(offScreen);
			break;

		case GAMESTATUS_DIE:
			drawSky(offScreen);
			for (AbstractSignboard s : Model.getSignboardList()) {
				s.draw(offScreen);
			}
			for (AbstractBlock b : Model.getBlockList()) {
				b.draw(offScreen);
			}
			for (AbstractEnemy e : Model.getEnemyList()) {
				if (!e.isDeath()) {
					e.draw(offScreen);
				}
			}
			for (Needle n : Model.getNeedleList()) {
				if (!n.isDeath()) {
					n.draw(offScreen);
				}
			}
			for (AbstractBlock b : Model.getPlaceBlockList()) {
				b.draw(offScreen);
			}
			for (AbstractEnemy e : Model.getPlaceEnemyList()) {
				if (!e.isDeath()) {
					e.draw(offScreen);
				}
			}
			Model.getClickItem().draw(offScreen);
			//debugShowText.draw(offScreen);
			Model.getGameoverSlide().draw(offScreen);
			break;

		case GAMESTATUS_ENDING:
			Model.getEndingSlide().draw(offScreen);
			break;

		case GAMESTATUS_STAGECHANGE:
			Model.getStageChangeSlide().draw(offScreen);
			break;

		case GAMESTATUS_WORLDCHANGE:
			drawSky(offScreen);
			for (AbstractSignboard s : Model.getSignboardList()) {
				s.draw(offScreen);
			}
			Model.getPlayerChara().draw(offScreen);// draw関数が悪い?
			for (AbstractBlock b : Model.getBlockList()) {
				b.draw(offScreen);
			}
			for (AbstractEnemy e : Model.getEnemyList()) {
				if (!e.isDeath()) {
					e.draw(offScreen);
				}
			}
			for (Needle n : Model.getNeedleList()) {
				if (!n.isDeath()) {
					n.draw(offScreen);
				}
			}
			for (AbstractBlock b : Model.getPlaceBlockList()) {
				b.draw(offScreen);
			}
			for (AbstractEnemy e : Model.getPlaceEnemyList()) {
				if (!e.isDeath()) {
					e.draw(offScreen);
				}
			}
			Model.getClickItem().draw(offScreen);
			//debugShowText.draw(offScreen);
			Model.getWorldClearSlide().draw(offScreen);
			break;
		}
		g.drawImage(offImage, 0, 0, this);
	}

	public void drawSky(Graphics g) {
		g.setColor(blue);
		g.fillRect(0, 0, GAME_WIDTH, GAME_WIDTH);
	}

	public static ViewPanel getViewPanel() {
		if (viewPanel == null) {
			viewPanel = new ViewPanel();
		}
		return viewPanel;
	}

}

package star_action;

import static constants.MathConstants.*;

import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;


public class ViewPanel extends JPanel {
	public static Timer timer;
	private final static int DELAY = 30;



	public ViewPanel(){
		super();

		timer = new Timer(DELAY, e -> {
				switch(Model.gameStatus){
					case GAMESTATUS_OPENING:
						break;
					case GAMESTATUS_PLAYING:
						Model.run();
						
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

	}

	public void run(){
		timer.start();
	}
	
	// 描画
		public void paintComponent(Graphics g) {
			switch(Model.getGameStatus()){
			case 0:
				//open.draw(g);
				break;
				
			case 1:	//各キャラ、ブロック、右上の画像を描画
				Model.draw(g);
				break;
				
			case 2:
				break;
				
			case 3:
				break;	
			}
		}


}

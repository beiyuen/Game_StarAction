package star_action;

import static constants.MathConstants.*;

import javax.swing.JPanel;
import javax.swing.Timer;


public class ViewPanel extends JPanel {
	public static Timer timer;
	private final static int DELAY = 30;



	public ViewPanel(){
		super();

		timer = new Timer(DELAY, e -> {
				switch(gameStatus){
					case GAMESTATUS_OPENING:
						break;
					case GAMESTATUS_PLAYING:
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

}

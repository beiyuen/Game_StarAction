package star_action;

import javax.swing.JPanel;
import javax.swing.Timer;

public class ViewPanel extends JPanel {
	public static Timer timer;
	private final static int DELAY = 30;
	public int gameStatus;
	
	
	public ViewPanel(){
		super();

		timer = new Timer(DELAY, e -> {
				switch(gameStatus){
					case 0:
						break;
					case 1:
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
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

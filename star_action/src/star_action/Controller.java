package star_action;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Controller {

	private static StarActionMouseAdapter mouseAdapter = null;
	private static StarActionKeyAdapter keyAdapter = null;
	
	static class StarActionMouseAdapter extends MouseAdapter{
		public void mouseClicked(MouseEvent evt) {
			int click = evt.getButton();
			if (click == MouseEvent.BUTTON1) {
				System.out.print("click");
			}
		}
	}
	
	static class StarActionKeyAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent evt) {
			if(evt.getKeyChar() == 'a'){
				System.out.print("a");
			}
		}
		public void keyReleased(KeyEvent evt) {
			
		}
	}
	
	private Controller(){
	}
	
	public static MouseAdapter getMouseAdapter(){		
		if(mouseAdapter == null){
			mouseAdapter = new StarActionMouseAdapter();
		}
		return mouseAdapter;
	}
	
	public static KeyAdapter getKeyAdapter(){
		if(keyAdapter == null){
			keyAdapter = new StarActionKeyAdapter();
		}
		return keyAdapter;
	}
}

package star_action;

import static constants.MathConstants.*;

import java.awt.event.InputEvent;
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
			
			int mod = evt.getModifiersEx();
			switch (Model.getGameStatus()) {
			case GAMESTATUS_PLAYING:
				switch (evt.getKeyCode()) {
				case 'd':
				case 'D':
				case KeyEvent.VK_RIGHT:
					if ((mod & InputEvent.SHIFT_DOWN_MASK) != 0) {
						Model.playerChara.up = true;
					}
					Model.playerChara.moveRight = true;
					break;
				case 'a':
				case 'A':
				case KeyEvent.VK_LEFT:
					if ((mod & InputEvent.SHIFT_DOWN_MASK) != 0)
						Model.playerChara.up = true;
					Model.playerChara.moveLeft = true;
					break;
				case 'w':
				case 'W':
				case KeyEvent.VK_SHIFT:
					Model.playerChara.up = true;
					break;
				case 'z':
				case 'Z':
					if ((mod & InputEvent.SHIFT_DOWN_MASK) != 0)
						Model.playerChara.up = true;
					Model.playerChara.dash = true;
					break;
				case 'r':// 自殺用
				case 'R':
					// NPCshoot.bullet.clear();
					Model.playerChara.death = true;
					Model.playerChara.init();
					// int num = s.num;
					// s.remove();
					// s = new Stage(num);//リロード
					break;
				}
				break;
				
			case GAMESTATUS_DIE:
				switch (evt.getKeyCode()) {
				case 'r':
				case 'R':	
					Model.init();
				}
				break;
			}
				
			
		}
		public void keyReleased(KeyEvent evt) {
			switch (evt.getKeyCode()) {
			case KeyEvent.VK_SHIFT:
				Model.playerChara.up = false;
				break;
			case 'd':
			case 'D':
			case KeyEvent.VK_RIGHT:
				Model.playerChara.moveRight = false;
				break;
			case 'a':
			case 'A':
			case KeyEvent.VK_LEFT:
				Model.playerChara.moveLeft = false;
				break;
			case 'z':
			case 'Z':
				Model.playerChara.dash = false;
				break;
			
			}
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

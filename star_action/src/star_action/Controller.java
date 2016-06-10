package star_action;

import static constants.MathConstants.*;

import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import charas.PlayerChara;
import enums.GameStatus;
/**
 * キー操作、マウス操作を行うためのコントローラです
 *
 * @author kitahara
 *
 */
public class Controller {

	private static StarActionMouseAdapter mouseAdapter = null;
	private static StarActionKeyAdapter keyAdapter = null;

	/**
	 * 左クリック、右クリックをした時の処理を定義したクラス
	 * @author kitahara
	 *
	 */
	static class StarActionMouseAdapter extends MouseAdapter {

		public void mouseClicked(MouseEvent evt) {

			int click = evt.getButton();
			GameStatus gameStatus = Model.getGameStatus();
			int mode = Model.getplacementMode();
			int clickX = evt.getX();
			int clickY = evt.getY() - 30;
			if (click == MouseEvent.BUTTON1 && gameStatus == GameStatus.Playing) {
				// モード変更
				if (clickX > GAME_WIDTH - 65 && clickX < GAME_WIDTH - 15 && clickY < 50) {
					Model.setplacementMode(mode + 1);
				}
				// 設置
				else if (Model.getClickableNum()[mode] > 0) {
					Model.placement(clickX / 50, clickY / 50);
				}
			}
			// 右クリックならブロックを削除
			else if (click == MouseEvent.BUTTON3) {
				Model.removeBlock(clickX, clickY);
			}
		}
	}
	/**
	 * ゲーム内でのキー操作に対する処理を定義したクラス
	 * @author kitahara
	 *
	 */
	static class StarActionKeyAdapter extends KeyAdapter {
		PlayerChara playerChara;
		public StarActionKeyAdapter(PlayerChara c){
			playerChara = c;
		}

		public void keyPressed(KeyEvent evt) {

			int mod = evt.getModifiersEx();
			switch (Model.getGameStatus()) {
			// オープニング画面の時
			case Opening:
				switch (evt.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					Model.gameInit();
					Model.setGameStatus(GameStatus.StageChange);
					break;
				}
				break;
			// プレイ画面の時
			case Playing:
				switch (evt.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					if ((mod & InputEvent.SHIFT_DOWN_MASK) != 0) {
						playerChara.setMoveUp(true);
					}
					playerChara.setMoveRight(true);
					break;
				case KeyEvent.VK_LEFT:
					if ((mod & InputEvent.SHIFT_DOWN_MASK) != 0){
						playerChara.setMoveUp(true);
					}
					playerChara.setMoveLeft(true);
					break;
				case KeyEvent.VK_SHIFT:
					playerChara.setMoveUp(true);
					break;
				case 'z':
				case 'Z':
					if ((mod & InputEvent.SHIFT_DOWN_MASK) != 0){
						playerChara.setMoveUp(true);
					}
					playerChara.setDash(true);
					break;
				case 'r':// 自殺用
				case 'R':
					Model.death();
					break;
				}
				break;
			// プレイヤーが死亡したとき
			case Die:
				switch (evt.getKeyCode()) {
				case 'r':
				case 'R':
					Model.init();
				}
				break;
			// ゲームクリアのとき
			case Ending:
				switch (evt.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					Model.setGameStatus(GameStatus.Opening);
					break;
				}
				break;
			// ステージ変更画面のとき
			case StageChange:
				switch (evt.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					Model.setGameStatus(GameStatus.Playing);
					break;
				}
				break;
			// ボスを倒したとき
			case WorldChange:
				switch (evt.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					Model.nextStage();
					break;
				}
				break;

			}

		}

		public void keyReleased(KeyEvent evt) {
			switch (evt.getKeyCode()) {
			case KeyEvent.VK_SHIFT:
				playerChara.setMoveUp(false);
				break;
			case KeyEvent.VK_RIGHT:
				playerChara.setMoveRight(false);
				break;
			case KeyEvent.VK_LEFT:
				playerChara.setMoveLeft(false);
				break;
			case 'z':
			case 'Z':
				playerChara.setDash(false);
				break;

			}
		}
	}

	private Controller() {
	}

	public static MouseAdapter getMouseAdapter() {
		if (mouseAdapter == null) {
			mouseAdapter = new StarActionMouseAdapter();
		}
		return mouseAdapter;
	}

	public static KeyAdapter getKeyAdapter() {
		if (keyAdapter == null) {
			keyAdapter = new StarActionKeyAdapter(Model.getPlayerChara());
		}
		return keyAdapter;
	}
}

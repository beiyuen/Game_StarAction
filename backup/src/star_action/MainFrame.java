package star_action;

import static constants.MathConstants.*;

import java.awt.Graphics;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import util.ReferenceItems;
/**
 * ゲーム本体です
 * @author kitahara
 *
 */
public class MainFrame extends JFrame {

	private ViewPanel viewPanel;

	public MainFrame() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		ReferenceItems.Load();
		if(ReferenceItems.isLoaded()){
			Model.setStage();
			viewPanel = ViewPanel.getViewPanel();
			this.getContentPane().add(viewPanel);
			this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
			this.setTitle("STAR ACTION");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			addMouseListener(Controller.getMouseAdapter());
			addKeyListener(Controller.getKeyAdapter());
			requestFocus();
			this.setResizable(false);
			this.validate();
			this.setVisible(true);
		}
	}

	public void paintComponent(Graphics g) {

	}

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		new MainFrame();

	}

}

package star_action;

import static constants.MathConstants.*;

import java.awt.Graphics;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import util.ReferenceItems;

public class MainFrame extends JFrame {

	private ViewPanel viewPanel;

	public MainFrame() throws IOException, UnsupportedAudioFileException, LineUnavailableException{
		ReferenceItems.Load();
		Model.setStage(Model.getStageNum());
		viewPanel = new ViewPanel();
		this.getContentPane().add(viewPanel);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setTitle("star_action");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseListener(Controller.getMouseAdapter());
		addKeyListener(Controller.getKeyAdapter());
		requestFocus();
		this.validate();
		this.setVisible(true);
		System.out.println("width: " + viewPanel.getWidth() + " height: " + viewPanel.getHeight());
	}

	public void paintComponent(Graphics g) {

	}

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		new MainFrame();

	}

}

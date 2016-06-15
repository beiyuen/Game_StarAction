package star_action;

import static constants.MathConstants.*;

import java.awt.Graphics;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import resource.ReferenceItems;
import slide.LoadingSlide;
import stages.MapItems;
/**
 * ゲーム本体です
 * @author kitahara
 *
 */
public class MainFrame extends JFrame {

	private ViewPanel viewPanel;
	private static LoadingSlide loadingSlide;

	public MainFrame() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		loadingSlide = new LoadingSlide();
		this.getContentPane().add(loadingSlide);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setTitle("STAR ACTION");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		requestFocus();
		this.setResizable(false);
		this.validate();
		this.setVisible(true);
		
		ReferenceItems referenceItems = ReferenceItems.getReferenceItems();
		MapItems mapItems = MapItems.getMapItems();
		// リソースの読み込み
		referenceItems.start();
		referenceItems.join();
		// オブジェクトの作成
		mapItems.start();
		mapItems.join();

		// リソースの読み込みがすべて終了してからゲームを開始する
		Model.firstInit();
		viewPanel = ViewPanel.getViewPanel();
		this.getContentPane().remove(loadingSlide);
		this.getContentPane().add(viewPanel);
		addMouseListener(Controller.getMouseAdapter());
		addKeyListener(Controller.getKeyAdapter());
		requestFocus();
		this.setVisible(true);
		
	}

	public void paintComponent(Graphics g) {

	}

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		new MainFrame();

	}

}

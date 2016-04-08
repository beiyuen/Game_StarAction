package star_action;

import static constants.MathConstants.*;

import java.awt.Graphics;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	private ViewPanel viewPanel;
	
	public MainFrame(){
		Model.setStage(Model.getStageNum());
		viewPanel = new ViewPanel();
		//viewPanel.add(button);
		this.getContentPane().add(viewPanel);
		//model = new Model();
		
		//this.setBackground(Color.black);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setTitle("star_action");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		addMouseListener(Controller.getMouseAdapter());
		addKeyListener(Controller.getKeyAdapter());
		requestFocus();
		this.validate();
		//pack();
		this.setVisible(true);
		System.out.println("width: " + viewPanel.getWidth() + " height: " + viewPanel.getHeight());
		//for (Block b : Model.blockList) {
		//	System.out.println("x :" + b.xPosition + ", y :" + b.yPosition);
		//}
	}
	
	public void paintComponent(Graphics g) {
		
	}

	public static void main(String[] args) {
		new MainFrame();
		
	}

}

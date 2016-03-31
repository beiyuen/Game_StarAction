package star_action;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
	// 画面横・縦
	public static final int XMAX = 1000;
	public static final int YMAX = 520;
	//Model model;
	ViewPanel viewPanel;
	JButton button;
	
	public MainFrame(){
		viewPanel = new ViewPanel();
		button = new JButton("button");
		//viewPanel.add(button);
		this.getContentPane().add(viewPanel);
		//model = new Model();
		this.setBackground(Color.black);
		this.setSize(XMAX, YMAX);
		this.setTitle("star_action");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		addMouseListener(Controller.getMouseAdapter());
		addKeyListener(Controller.getKeyAdapter());
		requestFocus();
		this.validate();
		this.setVisible(true);
		//System.out.println("width: " + viewPanel.getWidth() + " height: " + viewPanel.getHeight());
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

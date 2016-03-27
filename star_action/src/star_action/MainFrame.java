package star_action;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	// 画面横・縦
	public static final int XMAX = 1000;
	public static final int YMAX = 520;
	
	public MainFrame(){
		this.setSize(XMAX, YMAX);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame();
		
	}

}

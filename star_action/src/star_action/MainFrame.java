package star_action;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	// 画面横・縦
	public static final int XMAX = 1000;
	public static final int YMAX = 520;
	
	public MainFrame(){
		add(new ViewPanel());
		setSize(XMAX, YMAX);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addMouseListener(Controller.getMouseAdapter());
		addKeyListener(Controller.getKeyAdapter());
		requestFocus();
	}

	public static void main(String[] args) {
		new MainFrame();
		
	}

}



import javax.swing.*;


public class ElementalFrame extends JFrame {
	private Gameplan gameplan;
	
	public ElementalFrame() {
		gameplan = new Gameplan();
		
		add(gameplan);
		
		setSize(630,460);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ElementalFrame();
	}
}

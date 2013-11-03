

import java.awt.BorderLayout;

import javax.swing.*;


public class ElementalFrame extends JFrame {
	private Gameplan gameplan;
	private JPanel buttonPanel;
	private JPanel northPanel;
	private JPanel westPanel;
	private JPanel eastPanel;
	
	public ElementalFrame() {
		gameplan = new Gameplan();
		buttonPanel = new JPanel();
		northPanel = new JPanel();
		westPanel = new JPanel();
		eastPanel = new JPanel();
		
		buttonPanel.add(new JButton("Trulls"));
		
		add(gameplan);
		add(buttonPanel, BorderLayout.SOUTH);
//		add(northPanel, BorderLayout.NORTH);
//		add(westPanel, BorderLayout.WEST);
//		add(eastPanel, BorderLayout.EAST);
		
		setSize(630,460);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ElementalFrame();
	}
}

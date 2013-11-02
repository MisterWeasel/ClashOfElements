import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

public class Gameplan extends JPanel implements ActionListener {
	private Player player;
	private LinkedList<Monster> monsters;
	private Timer timer;
	
	public Gameplan() {
		timer = new Timer(25, this);
		player = new Player(110, 110);
		monsters = new LinkedList<Monster>();
		
		addKeyListener(k1);
		
		init();
		
		repaint();
		timer.start();
	}
	
	public void init() {
		monsters.add(new Monster(130,120));
		monsters.add(new Monster(110,140));
	}
	
	public void actionPerformed(ActionEvent e) {
		requestFocus();
		
		player.move();

		for (int i = 0 ; i < monsters.size() ; i++)
			monsters.get(i).move();

		for (int i = 0 ; i < monsters.size() ; i++)
			player.monsterInRoomBubble(monsters.get(i));
			
		repaint();
	}
	
	KeyListener k1 = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				if (player.hasGrip())
					player.moveGrippedNorth(true);
				else
					player.goNorth(true);
			}
			else if (e.getKeyCode() == KeyEvent.VK_D) {
				if (player.hasGrip())
					player.moveGrippedEast(true);
				else
					player.goEast(true);
			}
			else if (e.getKeyCode() == KeyEvent.VK_S) {
				if (player.hasGrip())
					player.moveGrippedSouth(true);
				else
					player.goSouth(true);
			}
			else if (e.getKeyCode() == KeyEvent.VK_A) {
				if (player.hasGrip())
					player.moveGrippedWest(true);
				else
					player.goWest(true);
			}
			
			if (e.getKeyCode() == KeyEvent.VK_E) {
				player.createRoomBubble();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_R) {
				if (player.hasGrippableMonsters()) {
					player.setGrip(true);
				}
			}
			
			if (e.getKeyCode() == KeyEvent.VK_T) {
				if (player.hasGrip()) {
					player.gripNextMonster();
				}
			}
			
			if (e.getKeyCode() == KeyEvent.VK_F) {
				player.repellAll();
			}

			repaint();
		}
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				player.goNorth(false);
				player.moveGrippedNorth(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				player.goEast(false);
				player.moveGrippedEast(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				player.goSouth(false);
				player.moveGrippedSouth(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				player.goWest(false);
				player.moveGrippedWest(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_R) {
				player.setGrip(false);
			}
		}
	};
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0,0,600,400);
		
		player.paint(g);
		
		for (int i = 0 ; i < monsters.size() ; i++)
			monsters.get(i).paint(g);
	}
}

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
			if (!monsters.get(i).isCaughtByBlackHole())
				monsters.get(i).move();
		
		player.moveProjectiles();
		player.manageShotCooldown();

		for (int i = 0 ; i < monsters.size() ; i++) {
			player.playableInRoomBubble(monsters.get(i));
			player.playableInBlackHole(monsters.get(i));
		}
			
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
			
			if (e.getKeyCode() == KeyEvent.VK_Y) {
				player.createBlackHole();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_F) {
				if (player.playerInRoomBubble())
					player.repellAll();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_G) {
				player.lightningRush(true);
			}
			
			if (e.getKeyCode() == KeyEvent.VK_Q) {
				if (player.canShoot())
					player.shootProjectile();
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
			
			if (e.getKeyCode() == KeyEvent.VK_G) {
				player.lightningRush(false);
			}
		}
	};
	
	public void paintComponent(Graphics g) {
/**/	g.setColor(Color.blue);
/**/	g.fillRect(0,0,650,600);
		g.setColor(Color.lightGray);
		g.fillRect(10,10,594,366);

		player.paint(g);
		for (int i = 0 ; i < monsters.size() ; i++)
			monsters.get(i).paint(g);

		player.paintProjectiles(g);
		
		g.setColor(Color.black);
		g.fillRect(0,0,10,10); // top left corner
		g.fillRect(10,0,594,10); // top side
		g.fillRect(604,0,10,10); // top right corner
		g.fillRect(0,10,10,366); // left side
		g.fillRect(604,10,10,366); // right side
		g.fillRect(0,376,10,10); // bottom left corner
		g.fillRect(10,376,594,10); // bottom side
		g.fillRect(604,376,10,10); // bottom right corner
		
	}
}

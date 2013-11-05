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

		for (int i = 0 ; i < monsters.size() ; i++) {
			if (!monsters.get(i).isCaughtByBlackHole())
				monsters.get(i).move();
			monsters.get(i).manageNegation();
		}
		
		player.moveProjectiles();
		player.manageShotCooldown();

		for (int i = 0 ; i < monsters.size() ; i++) {
			player.playableInRoomBubble(monsters.get(i));
			player.playableInBlackHole(monsters.get(i));
			if (player.getState() == Player.NEGATER)
				player.playableInNegateGripReach(monsters.get(i));
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
			} else if (e.getKeyCode() == KeyEvent.VK_D) {
				if (player.hasGrip())
					player.moveGrippedEast(true);
				else
					player.goEast(true);
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				if (player.hasGrip())
					player.moveGrippedSouth(true);
				else
					player.goSouth(true);
			} else if (e.getKeyCode() == KeyEvent.VK_A) {
				if (player.hasGrip())
					player.moveGrippedWest(true);
				else
					player.goWest(true);
			}
			
			if (e.getKeyCode() == KeyEvent.VK_E) {
				if (player.getState() == Player.MANIPULATOR)
					player.createRoomBubble();
				else if (player.getState() == Player.DARKNESS)
					player.createBlackHole();
				else if (player.getState() == Player.LIGHT)
					player.fireLaser();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_R) {
				if (player.getState() == Player.MANIPULATOR) {
					if (player.hasGrippableMonsters())
						player.setGrip(true);
				} else if (player.getState() == Player.NEGATER) {
					player.negateGripOpponent(true);
				}
			}
			
			if (e.getKeyCode() == KeyEvent.VK_T) {
				if (player.getState() == Player.MANIPULATOR) {
					if (player.hasGrip())
						player.gripNextMonster();
				} else if (player.getState() == Player.NEGATER) {
					player.negateGripNextMonster();
				}
			}
			
			if (e.getKeyCode() == KeyEvent.VK_F) {
				if (player.playerInRoomBubble())
					player.repellAll();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_G) {
				if (player.getState() == Player.LIGHT)
					player.lightningRush(true);
			}
			
			if (e.getKeyCode() == KeyEvent.VK_Q) {
				if (player.canShoot())
					player.shootProjectile();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_0) {
				player.setState(0);
			} else if (e.getKeyCode() == KeyEvent.VK_1) {
				player.setState(1);
			} else if (e.getKeyCode() == KeyEvent.VK_2) {
				player.setState(2);
			} else if (e.getKeyCode() == KeyEvent.VK_3) {
				player.setState(3);
			} else if (e.getKeyCode() == KeyEvent.VK_4) {
				player.setState(4);
			} else if (e.getKeyCode() == KeyEvent.VK_5) {
				player.setState(5);
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
				if (player.getState() == Player.MANIPULATOR) {
					player.setGrip(false);
				} else if (player.getState() == Player.NEGATER) {
					player.negateGripOpponent(false);
				}
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
		
		// Player-information box
		g.setColor(Color.orange);
		g.fillRect(0,386,200,100);
		// hp-coloring
		g.setColor(Color.green);
		g.fillRect(3, 400, player.getHP(), 10);
		g.setColor(Color.red);
		g.fillRect(3 + player.getHP(), 400, 50 - player.getHP(), 10);
		// mana-coloring
		g.setColor(Color.blue);
		g.fillRect(3, 431, player.getMana() * 50 / player.getMaxMana(), 10);
		g.setColor(Color.lightGray);
		g.fillRect(3 + player.getMana() * 50 / player.getMaxMana(), 431, 50 - player.getMana() * 50 / player.getMaxMana(), 10);
		g.setColor(Color.black);
		g.drawString("Player 1", 2, 398);
		g.drawRect(3, 400, 50, 10); // hp-box
		g.drawRect(3, 431, 50, 10); // mana-box
		g.drawString("HP: " + player.getHP() + "/" + player.getMaxHP(), 2, 424);
		g.drawString("Mana: " + player.getMana() + "/" + player.getMaxMana(), 2, 455);
		g.drawString("State: " + player.getStateString(), 2, 471);
	}
}

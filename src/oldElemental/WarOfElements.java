package oldElemental;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class WarOfElements
extends JFrame
{
	GamePanel gp;
	
	public WarOfElements()
	{
		gp = new GamePanel();
		
		add(gp);
		
		setSize(300,250);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args)
	{
		new WarOfElements();
	}
}
class GamePanel
extends JPanel
implements ActionListener
{
	Timer tim = new Timer (25,this);

	int x, y,
		movement, showPicture;
	boolean moveLeft, moveRight, moveUp, moveDown,
			first;
	Image pictureLeft, pictureRight, pictureUp, pictureDown;
	Image[][] area;

	public GamePanel()
	{
		x = 0; 
		y = 0;
		
		movement = 2;
		
		first = true;
		showPicture = 3;
		
		area = new Image[5][5];
		
		for(int n = 0 ; n < area.length ; n++)
			for(int i = 0 ; i < area[n].length ; i++)
				if((n == 2 && i == 4) || (n == 4 && i == 2))
					area[n][i] = Toolkit.getDefaultToolkit().createImage("Stone.png");
				else
					area[n][i] = Toolkit.getDefaultToolkit().createImage("Grass.png");
		
		pictureLeft = Toolkit.getDefaultToolkit().createImage("Element-vänster.png");
		pictureRight = Toolkit.getDefaultToolkit().createImage("Element-höger.png");
		pictureUp = Toolkit.getDefaultToolkit().createImage("Element-upp.png");
		pictureDown = Toolkit.getDefaultToolkit().createImage("Element-ner.png");
		
		addKeyListener(k1);

		tim.start();

		repaint();
	}
	public void move()
	{
		if(moveLeft)
		{
			x += getLeftBoundings() ? -movement : 0;
			showPicture = 1;
		}
		else if(moveRight)
		{
			x += x < 259 ? movement : 0;
			showPicture = 3;
		}
		else if(moveUp)
		{
			y += y > 0 ? -movement : 0;
			showPicture = 2;
		}
		else if(moveDown)
		{
			y += y < 187 ? movement : 0;
			showPicture = 4;
		}
	}
	public boolean getLeftBoundings()
	{
		if(x > 0)
			return true;
		else
			return false;
	}
	public void actionPerformed(ActionEvent e)
	{
		// =====
		// Ger fokus på spelplanen (för mus och tangenter?)
		// Uppdaterar kartan
		// =====
		requestFocus();
		
		move();
		
		repaint();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if(first)
		{
			g.drawImage(pictureLeft, x, y, 25, 25, null);
			g.drawImage(pictureRight, x, y, 25, 25, null);
			g.drawImage(pictureUp, x, y, 25, 25, null);
			g.drawImage(pictureDown, x, y, 25, 25, null);
			
			first = false;
		}

		for(int n = 0 ; n < area.length ; n++)
			for(int i = 0 ; i < area[n].length ; i++)
				g.drawImage(area[n][i], i*30, n*30, null);
		
		if(showPicture == 1)
			g.drawImage(pictureLeft, x, y, 25, 25, null);
		else if(showPicture == 2)
			g.drawImage(pictureUp, x, y, 25, 25, null);
		else if(showPicture == 3)
			g.drawImage(pictureRight, x, y, 25, 25, null);
		else if(showPicture == 4)
			g.drawImage(pictureDown, x, y, 25, 25, null);
	}
	KeyListener k1 = new KeyAdapter()
	{
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_A)
			{
				moveLeft = true;
				if(!moveRight && !moveUp && !moveDown)
					showPicture = 1;
			}
			else if(e.getKeyCode() == KeyEvent.VK_D)
			{
				moveRight = true;
				if(!moveLeft && !moveUp && !moveDown)
					showPicture = 3;
			}
			else if(e.getKeyCode() == KeyEvent.VK_W)
			{
				moveUp = true;
				if(!moveLeft && !moveRight && !moveDown)
					showPicture = 2;
			}
			else if(e.getKeyCode() == KeyEvent.VK_S)
			{
				moveDown = true;
				if(!moveLeft && !moveRight && !moveUp)
					showPicture = 4;
			}

			repaint();
		}
		public void keyReleased(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_A)
			{
				moveLeft = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_D)
			{
				moveRight = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_W)
			{
				moveUp = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_S)
			{
				moveDown = false;
			}
		}
	};
}
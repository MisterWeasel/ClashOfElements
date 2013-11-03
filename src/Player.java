import java.awt.*;
import java.util.LinkedList;

public class Player extends Playable {
	private int x;
	private int y;
	private int playerDirection;
	private boolean north;
	private boolean east;
	private boolean south;
	private boolean west;
	
	private LinkedList<Projectile> projectiles;
	private int shotCooldown;
	
	private int xBubble;
	private int yBubble;
	private int bubbleRadie;
	private final int bubbleMaxRadie = 200;
	private boolean bubbleRoom;

	private boolean grip;
	private int grippedIndex;
	private LinkedList<Playable> grippableMonsters;
	
	private boolean lightningRushing;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		playerDirection = 0;
		north = false;
		east = false;
		south = false;
		west = false;
		
		projectiles = new LinkedList<Projectile>();
		shotCooldown = 0;
		
		bubbleRoom = false;
		bubbleRadie = 0;

		grip = false;
		grippedIndex = 0;
		grippableMonsters = new LinkedList<Playable>();
		
		lightningRushing = false;
	}
	
	public boolean isNorth() {
		return north;
	}

	public void goNorth(boolean north) {
		this.north = north;
		
		if (north)
			playerDirection = 0;
	}

	public boolean isEast() {
		return east;
	}

	public void goEast(boolean east) {
		this.east = east;

		if (east)
			playerDirection = 1;
	}

	public boolean isSouth() {
		return south;
	}

	public void goSouth(boolean south) {
		this.south = south;

		if (south)
			playerDirection = 2;
	}

	public boolean isWest() {
		return west;
	}

	public void goWest(boolean west) {
		this.west = west;

		if (west)
			playerDirection = 3;
	}
	
	public void moveGrippedNorth(boolean north) {
		if (grippableMonsters.size() > 0)
			grippableMonsters.get(grippedIndex).goNorth(north);
	}

	public void moveGrippedEast(boolean east) {
		if (grippableMonsters.size() > 0)
			grippableMonsters.get(grippedIndex).goEast(east);
	}

	public void moveGrippedSouth(boolean south) {
		if (grippableMonsters.size() > 0)
			grippableMonsters.get(grippedIndex).goSouth(south);
	}

	public void moveGrippedWest(boolean west) {
		if (grippableMonsters.size() > 0)
			grippableMonsters.get(grippedIndex).goWest(west);
	}

	public void move() {
		int movement;
		if (lightningRushing) {
			movement = 10;
		} else {
			movement = 2;
		}

		if (north) {
			y -= movement;
		} else if (east) {
			x += movement;
		} else if (south) {
			y += movement;
		} else if (west) {
			x -= movement;
		}
		
		if (bubbleRoom && bubbleRadie < bubbleMaxRadie) {
			bubbleRadie += 4;
			xBubble -= 2;
			yBubble -= 2;
		}
	}

	public void moveProjectiles() {
		for (int i = 0 ; i < projectiles.size() ; i++)
			projectiles.get(i).move();
	}
	
	public void shootProjectile() {
		if (playerDirection == 0)
			projectiles.add(new Projectile(x+2, y+6, playerDirection, 5));
		else if (playerDirection == 1)
			projectiles.add(new Projectile(x+10, y+4, playerDirection, 5));
		else if (playerDirection == 2)
			projectiles.add(new Projectile(x+4, y+10, playerDirection, 5));
		else if (playerDirection == 3)
			projectiles.add(new Projectile(x-6, y+4, playerDirection, 5));
		else
			return;
		
		shotCooldown = 15;
	}
	
	public void manageShotCooldown() {
		if (shotCooldown > 0)
			shotCooldown--;
	}

	public void createRoomBubble() {
		if (bubbleRoom)
			bubbleRoom = false;
		else {
			bubbleRadie = 0;
			xBubble = x+5;
			yBubble = y+5;
			
			bubbleRoom = true;
		}
	}
	
	public void playableInRoomBubble(Playable playable) {
		if (xBubble <= playable.getX() && playable.getX() <= (xBubble + bubbleRadie) && 
			yBubble <= playable.getY() && playable.getY() <= (yBubble + bubbleRadie) &&
			bubbleRoom) {
			if (!grippableMonsters.contains(playable)) {
				grippableMonsters.add(playable);
				playable.marking(true);
			}
		} else {
			if (grippableMonsters.contains(playable)) {
				if (grippedIndex == grippableMonsters.indexOf(playable)) {
					grippedIndex = (grippedIndex == 0 ? 0 : grippedIndex--);
				}
				grippableMonsters.remove(playable);
				if (grippableMonsters.size() > 0)
					grippableMonsters.get(grippedIndex).grip(true);
				playable.marking(false);
				playable.grip(false);
			}
		}
	}
	
	public void setGrip(boolean grip) {
		this.grip = grip;
		
		if (this.grip) {
			grippableMonsters.get(grippedIndex).grip(true);
		}
		 /**
		  * TODO
		  * fix grippedIndex when monsters leaving bubble
		  */
		if (!(grippedIndex < grippableMonsters.size()))
			grippedIndex = 0;
//			throw new IllegalArgumentException("grippedIndex is not in the acceptable intervall");
		
		if (!grip && grippableMonsters.size() > 0) {
			grippableMonsters.get(grippedIndex).grip(false);
			grippedIndex = 0;
		}
	}
	
	public void gripNextMonster () {
		if (grippableMonsters.size() > 0) {
			grippableMonsters.get(grippedIndex).grip(false);
			grippedIndex = (grippedIndex + 1) % grippableMonsters.size();
			grippableMonsters.get(grippedIndex).grip(true);
		}
	}

	/**
	 * TODO
	 * Fix more verticals
	 */
	public void repellAll() {
		int repellMovement = 4;
		for (int i = 0 ; i < grippableMonsters.size() ; i++) {
			int x = 0;
			int y = 0;

			if (this.x == grippableMonsters.get(i).getX()) {
				x = 0;
				if (this.y > grippableMonsters.get(i).getY())
					y = -repellMovement;
				else
					y = repellMovement;

				grippableMonsters.get(i).repell(x, y);
				continue;
			}
			double k = ((double)this.y - (double)grippableMonsters.get(i).getY()) / ((double)this.x - (double)grippableMonsters.get(i).getX());
			
			if (k >= -0.5 && k < 0.5) {
				x = repellMovement;
				y = 0;
			} else if ((k >= 0.5 && k < 1.7) || (k > -1.7 && k <= -0.5)) {
				x = repellMovement/2;
				y = repellMovement/2;
			} else if (k >= 1.7 || k <= -1.7) {
				x = 0;
				y = repellMovement;
			}
			
			if (this.y > grippableMonsters.get(i).getY())
				y *= -1;
			else
				y *= 1;
			
			if (this.x > grippableMonsters.get(i).getX())
				x *= -1;
			else
				x *= 1;

			System.out.println("#" + i + " - " + k + " x:" + x + " y:" + y);
			grippableMonsters.get(i).repell(x, y);
		}
	}

	public void shambleMovement() {
		if (grip) {
//			grippableMonsters.get(grippedIndex).shambleMovement();
		}
	}
	
	public void lightningRush(boolean rushing) {
		lightningRushing = rushing;
	}

	public boolean playerInRoomBubble() {
		if (xBubble <= x && x <= (xBubble + bubbleRadie) && 
			yBubble <= y && y <= (yBubble + bubbleRadie))
			return true;
		else
			return false;
	}

	public boolean canShoot() {
		if (shotCooldown > 0)
			return false;
		else 
			return true;
	}

	public boolean hasGrippableMonsters() {
		return grippableMonsters.size() > 0;
	}
	
	public boolean hasGrip() {
		return grip;
	}
	
	public void paint(Graphics g) {
		Color lightBlue = new Color(100,200,255);
		if (bubbleRoom) {
			g.setColor(lightBlue);
			g.fillOval(xBubble, yBubble, bubbleRadie, bubbleRadie);
			g.setColor(Color.black);
			g.drawOval(xBubble, yBubble, bubbleRadie, bubbleRadie);
		}

		if (lightningRushing) {
			g.setColor(Color.yellow);
			g.fillRect(x+1, y+1, 8, 1);
			g.fillRect(x+2, y+4, 6, 1);
			g.fillRect(x, y+7, 10, 1);
			g.fillRect(x+3, y+9, 4, 1);
		} else {
			g.setColor(Color.blue);
			g.fillOval(x, y, 10, 10);
			g.setColor(Color.black);
			g.drawOval(x, y, 10, 10);
		}
	}

	public void paintProjectiles(Graphics g) {
		for (int i = 0 ; i < projectiles.size() ; i++)
			projectiles.get(i).paint(g);
	}
}
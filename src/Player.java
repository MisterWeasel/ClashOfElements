import java.awt.*;
import java.util.LinkedList;

public class Player {
	private int x;
	private int y;
	private boolean north;
	private boolean east;
	private boolean south;
	private boolean west;
	
	private int xBubble;
	private int yBubble;
	private int bubbleRadie;
	private final int bubbleMaxRadie = 200;
	private boolean bubbleRoom;

	private boolean grip;
	private int grippedIndex;
	private LinkedList<Monster> grippableMonsters;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		north = false;
		east = false;
		south = false;
		west = false;
		
		bubbleRoom = false;
		bubbleRadie = 0;

		grip = false;
		grippedIndex = 0;
		grippableMonsters = new LinkedList<Monster>();
	}
	
	public boolean isNorth() {
		return north;
	}

	public void goNorth(boolean north) {
		this.north = north;
//		east = false;
//		south = false;
//		west = false;
	}

	public boolean isEast() {
		return east;
	}

	public void goEast(boolean east) {
//		north = false;
		this.east = east;
//		south = false;
//		west = false;
	}

	public boolean isSouth() {
		return south;
	}

	public void goSouth(boolean south) {
//		north = false;
//		east = false;
		this.south = south;
//		west = false;
	}

	public boolean isWest() {
		return west;
	}

	public void goWest(boolean west) {
//		north = false;
//		east = false;
//		south = false;
		this.west = west;
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
		if (north) {
			y -= 2;
		} else if (east) {
			x += 2;
		} else if (south) {
			y += 2;
		} else if (west) {
			x -= 2;
		}
		
		if (bubbleRoom && bubbleRadie < bubbleMaxRadie) {
			bubbleRadie += 4;
			xBubble -= 2;
			yBubble -= 2;
		}
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
	
	public void monsterInRoomBubble(Monster monster) {
		if (xBubble <= monster.getX() && monster.getX() <= (xBubble + bubbleRadie) && 
			yBubble <= monster.getY() && monster.getY() <= (yBubble + bubbleRadie) &&
			bubbleRoom) {
			if (!grippableMonsters.contains(monster)) {
				grippableMonsters.add(monster);
				monster.marking(true);
			}
		} else {
			if (grippableMonsters.contains(monster)) {
				if (grippedIndex == grippableMonsters.indexOf(monster)) {
					grippedIndex = (grippedIndex == 0 ? 0 : grippedIndex--);
				}
				grippableMonsters.remove(monster);
				if (grippableMonsters.size() > 0)
					grippableMonsters.get(grippedIndex).grip(true);
				monster.marking(false);
				monster.grip(false);
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

	public boolean hasGrippableMonsters() {
		return grippableMonsters.size() > 0;
	}
	
	public boolean hasGrip() {
		return grip;
	}
	
	public void paint(Graphics g) {
		if (bubbleRoom) {
			g.setColor(Color.yellow);
			g.fillOval(xBubble, yBubble, bubbleRadie, bubbleRadie);
			g.setColor(Color.black);
			g.drawOval(xBubble, yBubble, bubbleRadie, bubbleRadie);
		}

		g.setColor(Color.blue);
		g.fillOval(x, y, 10, 10);
		g.setColor(Color.black);
		g.drawOval(x, y, 10, 10);
	}

}
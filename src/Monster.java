import java.awt.*;

public class Monster extends Playable {
	private int x;
	private int y;
	private boolean alive;
	
	private boolean north;
	private boolean east;
	private boolean south;
	private boolean west;
	
	private int splittingLength;
	private int splitExtraMove;
	private boolean marked;
	private boolean splitted;
	private boolean gripped;
	
	private int repellX;
	private int repellY;
	private boolean repelling;
	
	private boolean caughtByBlackHole;
	
	private boolean negateGripReach;
	private boolean negateGrip;
	private boolean negated;
	private int negateTimer;
	
	public Monster(int x, int y) {
		this.x = x;
		this.y = y;
		alive = true;

		north = false;
		east = false;
		west = false;
		south = false;
		
		splittingLength = 0;
		splitExtraMove = 0;
		marked = false;
		splitted = false;
		gripped = false;
		
		repellX = 0;
		repellY = 0;
		repelling = false;
		
		caughtByBlackHole = false;
		
		negateGripReach = false;
		negateGrip = false;
		negated = false;
		negateTimer = 0;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void addX(int addX) {
		x += addX;
	}
	
	public void addY(int addY) {
		y += addY;
	}
	
	public void marking(boolean mark) {
		marked = mark;
		
		if (!mark) {
			north = false;
			east = false;
			west = false;
			south = false;
			
			repellX = 0;
			repellY = 0;
			repelling = false;
		}
	}
	
	public void caughtByBlackHole(boolean caught) {
		caughtByBlackHole = caught;
	}
	
	public void split() {
		splitted = true;
	}
	
	public void grip(boolean grip) {
		gripped = grip;
	}
	
	public void move() {
		if (repelling) {
			y += repellY;
			x += repellX;
		} else {
			if (splitted) {
				if (splittingLength < 5) {
					splittingLength++;
				} else if (splitExtraMove < 15) {
					splitExtraMove++;
				}
			}
			
			if (north) {
				y -= 2;
			} else if (east) {
				x += 2;
			} else if (south) {
				y += 2;
			} else if (west) {
				x -= 2;
			}
		}
	}
	
	public void repell(int x, int y) {
		repellX = x;
		repellY = y;
		repelling = true;
	}
	
	public void negateGripReach(boolean negateGripReach) {
		this.negateGripReach = negateGripReach;
	}
	
	public void negateGrip(boolean negateGrip) {
		this.negateGrip = negateGrip;
	}
	
	public void negated() {
		negated = true;
		negateTimer = 240;
	}
	
	public void manageNegation() {
		if (negateTimer > 0) {
			negateTimer--;
			if (negateTimer == 0)
				negated = false;
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isNorth() {
		return north;
	}

	public void goNorth(boolean north) {
		this.north = north;
	}

	public boolean isEast() {
		return east;
	}

	public void goEast(boolean east) {
		this.east = east;
	}

	public boolean isSouth() {
		return south;
	}

	public void goSouth(boolean south) {
		this.south = south;
	}

	public boolean isWest() {
		return west;
	}

	public void goWest(boolean west) {
		this.west = west;
	}

	public boolean isMarked() {
		return marked;
	}
	
	public boolean isCaughtByBlackHole() {
		return caughtByBlackHole;
	}

	public boolean inNegateGripReach() {
		return negateGripReach;
	}

	public void paint(Graphics g) {
		if (alive) {
			if (splitted) {
				g.setColor(Color.green);
				g.fillOval(x-splittingLength, y-splittingLength, 5, 5);
				g.fillOval(x-splittingLength, y+splittingLength+splitExtraMove, 5, 5);
				g.fillOval(x+splittingLength, y-splittingLength+splitExtraMove, 5, 5);
				g.fillOval(x+splittingLength+splitExtraMove, y+splittingLength+splitExtraMove, 5, 5);
				g.setColor(Color.black);
				g.drawOval(x-splittingLength, y-splittingLength, 5, 5);
				g.drawOval(x-splittingLength, y+splittingLength+splitExtraMove, 5, 5);
				g.drawOval(x+splittingLength, y-splittingLength+splitExtraMove, 5, 5);
				g.drawOval(x+splittingLength+splitExtraMove, y+splittingLength, 5, 5);
			} else {
				if (marked) {
					if (gripped)
						g.setColor(Color.blue);
					else
						g.setColor(Color.red);
					g.fillOval(x-2, y-2, 14, 14);
				}
				if (negateGrip) {
					g.setColor(Color.darkGray);
					g.fillOval(x-2, y-2, 14, 14);
				} else if (negateGripReach) {
					g.setColor(Color.darkGray);
					g.drawOval(x-2, y-2, 14, 14);
				}
				g.setColor(Color.green);
				g.fillOval(x, y, 10, 10);
				g.setColor(Color.black);
				g.drawOval(x, y, 10, 10);
				if (negated) {
					g.setColor(Color.darkGray);
					g.drawLine(x-4+5, y-4+5, x+4+5, y+4+5);
					g.drawLine(x-4+5, y+4+5, x+4+5, y-4+5);
				}
			}
		}
	}
}

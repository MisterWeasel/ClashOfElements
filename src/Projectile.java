import java.awt.Graphics;
import java.awt.Color;

public class Projectile {
	private int type;
	private int x;
	private int y;
	private int direction;
	private int movementSpeed;
	
	public Projectile(int type, int x, int y, int direction, int movementSpeed) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.movementSpeed = movementSpeed;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void move() {
		if (direction == 0) {
			y -= movementSpeed;
		} else if (direction == 1) {
			x += movementSpeed;
		} else if (direction == 2) {
			y += movementSpeed;
		} else if (direction == 3) {
			x -= movementSpeed;
		}
	}
	
	public void paint(Graphics g) {
		Color iceBlue = new Color(100,150,255);

		if (type == 0) {
			g.setColor(Color.orange);
			g.fillRect(x, y, 6, 6);
		} else if (type == 1) {
			g.setColor(Color.yellow);
			if (direction%2 == 0)
				g.fillRect(x, y, 4, 16);
			else if (direction%2 == 1)
				g.fillRect(x, y, 16, 4);
		} else if (type == 2) {
			g.setColor(iceBlue);
			if (direction == 0) {
				g.fillRect(x, y+6, 6, 2);
				g.fillRect(x+1, y, 4, 6);
			} else if (direction == 1) {
				g.fillRect(x, y, 2, 6);
				g.fillRect(x+2, y+1, 6, 4);
			} else if (direction == 2) {
				g.fillRect(x, y, 6, 2);
				g.fillRect(x+1, y+2, 4, 6);
			} else if (direction == 3) {
				g.fillRect(x+6, y, 2, 6);
				g.fillRect(x, y+1, 6, 4);
			}
		}
	}
}

import java.awt.Graphics;
import java.awt.Color;

public class Projectile {
	private int x;
	private int y;
	private int direction;
	private int movementSpeed;
	
	public Projectile(int x, int y, int direction, int movementSpeed) {
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
		g.setColor(Color.orange);
		g.fillRect(x, y, 6, 6);
	}
}

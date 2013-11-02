package oldElemental;
import java.awt.Image;
import java.awt.Toolkit;

public class Elemental
{
	public int 	x,	//	coordinate-x-value
				y;	//	coordinate-y-value
	public boolean 	moveLeft,			// moving left
					moveRight,			// moving right
					moveUp,				// moving up
					moveDown,			// moving down
					showPictureLeft,	// show picture to the left
					showPictureRight;	// show picture to the right
	public Image	pictureLeft,		// picture to the left
					pictureRight;		// picture to the right
	
	public Elemental(int type, int x, int y)
	{
		this.x = x;
		this.y = y;

		if(type == 1)
		{
			pictureLeft = Toolkit.getDefaultToolkit().createImage("Element-v�nster.png");
			pictureRight = Toolkit.getDefaultToolkit().createImage("Element-h�ger.png");
		}
		else
		{
			pictureLeft = Toolkit.getDefaultToolkit().createImage("Element-v�nster.png");
			pictureRight = Toolkit.getDefaultToolkit().createImage("Element-h�ger.png");
		}

		showPictureRight = true;
	}
}

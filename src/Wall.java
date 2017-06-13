/*
 * Wall class - represents a wall within the game.
 */
import javax.swing.ImageIcon;// imports library needed to create wall image

public class Wall extends Sprite {
	// Wall constructor - needs the image file name and position within the game
	public Wall(String string, float[] position) {
		super(position, 0.0, new float[] {0, 0}, 0.0, new ImageIcon(string));// calls Sprite constructor
	}// Wall(String, float[]) constructor
}// Wall class


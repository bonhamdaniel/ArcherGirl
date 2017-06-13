/*
 * Arrow class - represents arrows shot be ArcherGirl character
 */
import javax.swing.ImageIcon;// imports library needed to create Arrow image in game

public class Arrow extends Sprite {
	protected float[] trajectory;// represents the trajectory of an arrow's path
	private final int ARROW_SPEED = 3;// represents the speed the arrow can travel
	
	// Arrow constructor - needs position in game, trajectory, and orientation
	public Arrow(float[] position, float[] trajectory, double orientation) {
		super(position, orientation, new float[] {0,0}, 1.0, new ImageIcon("arrow.png"));// calls Sprite
		this.trajectory = trajectory;// sets trajectory
	}// Arrow(float[], int[], double) constructor
	
	// move method - determines how an Arrow moves within the game
	public void move() {
		update(trajectory[0] * ARROW_SPEED, trajectory[1] * ARROW_SPEED);// calls update() in Kinematic class
		// Determines whether an arrow is still active
		if (position[1] < 0)
			setVisible(false);// returns whether Arrow is still active
	}// move() method
}// Arrow class

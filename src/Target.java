/*
 * Target class - represents the rewards the ArcherGirl is to chase after and shoot.
 */
import java.util.ArrayList;// imports library necessary for use of ArrayList
import javax.swing.ImageIcon;// imports library necessary to create Target image

public class Target extends Sprite {
	// Target constructor - starting position position required
	public Target(float[] position) {
		super(position, 1.0, new float[] {0, 0}, 1.0, new ImageIcon("target.png"));//calls Sprite constructor
	}// Target(float[]) constructor
	
	// move method - determines how to move Target through the Game space, calls update
	public void move(ArcherGirl archerGirl, ArrayList<Wall> walls, ArrayList<Spider> spiders, ArrayList<Target> targets) {
		// Calls the wander steering method and updates attributes
		Steering temp = wander(this);
		this.linear = temp.linear;// updates steering linear attributes
		this.angular = temp.angular;// updates steering angular attributes
		this.target = temp.target;;// updates steering temp attributes
		
		// Determines if the Spider is close to ArcherGirl, if so she iz updated to be the Spider target
		if (MovementMath.length(new float[] {this.position[0] - Board.archerGirl.position[0], this.position[1] - Board.archerGirl.position[1]}) < 300) {
			this.target[0] = Board.archerGirl.position[0];// updates x-coord of target to ArcherGirl position
			this.target[1] = Board.archerGirl.position[1];// updates y-coord of target to ArcherGirl position
		}// if (Movement.length < 300)
		
		// Calls the obstacleAvoidance steering method and updates attributes
		temp = obstacleAvoidance(archerGirl, this, walls);
		this.linear = temp.linear;// updates steering linear attributes
		this.angular = temp.angular;// updates steering angular attributes
		this.target = temp.target;;// updates steering temp attributes
		
		// Calls the seek steering method and updates attributes
		temp = flee(this.target, this);// calls method
		this.linear = temp.linear;// updates steering linear attributes
		this.angular = temp.angular;// updates steering angular attributes
		
		// Checks to see if the proposed movement will lead to a collision
		if (noCollisions(targets, spiders, walls))
			update(maxSpeed, 1);// if movement is cleared, update spider position and attributes
	}// move(ArcherGirl, ArrayList<Wall>, ArrayList<Spider>, ArrayList<Target>)
}// Target class

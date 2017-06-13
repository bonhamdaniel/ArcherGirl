/*
 * Spider class - represents the enemy in the game, chases after ArcherGirl
 */
import java.util.ArrayList;// imports library to allow use of ArrayList
import javax.swing.ImageIcon;// imports library needed to create Spider image

public class Spider extends Sprite {
	// Spider constructor - calls Sprite constructor with most information
	public Spider(int x, int y, int maxSpeed, ArcherGirl archerGirl) {
		super(new float[] {x, y}, MovementMath.randomBinomial(), new float[] {(float)MovementMath.randomBinomial(),(float)MovementMath.randomBinomial()}, MovementMath.randomBinomial(), new ImageIcon("spider.png"));
		this.maxSpeed = maxSpeed;// sets initial maxSpeed
	}// Spider(int, int, int, ArcherGirl) constructor
	
	// move method - determines how to move the Spider and then calls update method
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
		temp = seek(this.target, this);// calls method
		this.linear = temp.linear;// updates steering linear attributes
		this.angular = temp.angular;// updates steering angular attributes
		
		// Checks to see if the proposed movement will lead to a collision
		if (noCollisions(targets, spiders, walls))
			update(maxSpeed, 1);// if movement is cleared, update spider position and attributes

		lookWhereYoureGoing(this);// turns spider to face the direction it is moving
	}// move(ArcherGirl, ArrayList<Wall>, ArrayList<Spider>, ArrayList<Target>) method
}// Spider class

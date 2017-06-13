/*
 * Kinematic class - represents movement attributes.
 */
public class Kinematic extends Steering {
	protected float[] position;// holds current position
	protected double orientation;// holds current orientation
	protected float[] velocity;// holds current velocity
	protected double rotation;// holds current rotation
	
	// Kinematic constructor - requires position, orientation, velocity, rotation
	public Kinematic(float[] position, double orientation, float[] velocity, double rotation) {
		super();// calls Steering constructor
		this.position = position;// sets initial position
		this.orientation = orientation;// sets initial orientation
		this.velocity = velocity;// sets initial velocity
		this.rotation = rotation;// sets initial rotation
	}// Kinematic(float[], double, float[] double) constructor
	
	// update method - used by Spider and Target to update movement attributes
	public void update(int maxSpeed, int time) {
		position = new float[] {position[0] + velocity[0] * time, position[1] + velocity[1] * time};// updates position based on velocity
		orientation += rotation * time;// updates orientation based on rotation
		
		velocity = new float[] {velocity[0] + linear[0] * time, velocity[1] + linear[1] * time};// updates velocity based on linear
		rotation += angular * time;// updates rotation based on angular
		
		// Determines whether the velocity is greater than maxSpeed
		if (MovementMath.length(velocity) > maxSpeed) {
			velocity = MovementMath.normalize(velocity);// normalizes the velocity
			velocity[0] *= maxSpeed;// multiplies x-coord of velocity by maxSpeed
			velocity[1] *= maxSpeed;// multiplies y-coord of velocity by maxSpeed
		}// if (velocity > maxSpeed)
	}// update(int, int) method
	
	// update method - used by ArcherGirl and Arrow to update their movement
	public void update(float dx, float dy) {
		position[0] += dx;// adjusts the x-coord of the position
		position[1] += dy;// adjusts the y-coord of the position
	}// update(float, float) method
	
	// setOrientation method - sets a characters orientation based on their velocity
	public void setOrientation() {
		// Determines if the character is moving
		if (velocity[0] != 0 && velocity[1] != 0)
			orientation = Math.atan2(-velocity[0], velocity[1]);// sets the orientation based on the velocity
	}// setOrientation() method
}// Kinematic class
